package io.github.IzumiDIA.windows.builder.impl;

import io.github.IzumiDIA.windows.VirtualHostNameToFolderMapping;
import io.github.IzumiDIA.WebViewWindow.WebMessageListener;
import io.github.IzumiDIA.builder.WebViewBuilder;
import io.github.IzumiDIA.constant.enums.HostResourceAccessKind;
import io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl;
import io.github.IzumiDIA.windows.factory.IUnknownFactory;
import io.github.IzumiDIA.windows.factory.impl.IUnknownFactoryImpl;
import io.github.IzumiDIA.windows.impl.HResult;
import io.github.IzumiDIA.windows.impl.PlatformWindowImpl;
import io.github.IzumiDIA.windows.impl.WebViewWindowImpl;
import io.github.IzumiDIA.windows.impl.WebViewWindowImpl.EventExchangeImpl;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jetbrains.annotations.NotNull;
import org.jextract.*;
import org.jextract.ICoreWebView2WebMessageReceivedEventHandlerVtbl.Invoke;
import org.jextract.LayoutUtils.PointerLayoutHolder;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.IntStream;

public class WebViewBuilderImpl extends WindowsNativeObject implements WebViewBuilder<HResult, EventExchangeImpl> {
	private final WebViewControllerImpl webViewController;
	private final PlatformWindowImpl platformWindow;
	private final MemorySegment webview2_PP;
	private MemorySegment contentString = null, uriString = null;
	private String userDataFolder = null;
	private boolean needsExtension = false,
			enableScript = false,
			enableWebMessage = false,
			enableDevTools = false,
			enableContextMenu = false,
			enabledZoomControl = false,
			enableStatusBar = false,
			enabledDefaultScriptDialogs = false;
	private VirtualHostNameToFolderMapping virtualHostNameToFolderMapping = null;
	private MemorySegment messageReceivedEventHandler = null;
	
	public WebViewBuilderImpl(
			final WebViewControllerImpl webViewController,
			final Arena arena,
			final PlatformWindowImpl platformWindow
	) {
		super(arena);
		this.webViewController = webViewController;
		this.platformWindow = platformWindow;
		this.webview2_PP = this.arena.allocateFrom(
				ICoreWebView2.POINTER_POINTER$LAYOUT,
				this.arena.allocateFrom(
						ICoreWebView2.POINTER$LAYOUT,
						MemorySegment.NULL
				)
		);
	}
	
	@Override
	public WebViewBuilderImpl setUserDataFolder(final Path userDataFolderPath) {
		this.userDataFolder = userDataFolderPath != null ?
				                      userDataFolderPath.toAbsolutePath().toString() : null;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl enableScript(final boolean enabled) {
		this.enableScript = enabled;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl enableWebMessage(final boolean enabled) {
		this.enableWebMessage = enabled;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl enableDevTools(final boolean enabled) {
		this.enableDevTools = enabled;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl enableContextMenu(final boolean enabled) {
		this.enableContextMenu = enabled;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl enabledZoomControl(final boolean enabled) {
		this.enabledZoomControl = enabled;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl enableStatusBar(final boolean enabled) {
		this.enableStatusBar = enabled;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl enabledDefaultScriptDialogs(final boolean enabled) {
		this.enabledDefaultScriptDialogs = enabled;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl setVirtualHostNameToFolderMapping(
			final @NotNull String virtualHostName,
			final Path folderMapping,
			final @NotNull HostResourceAccessKind accessKind
	) {
		this.virtualHostNameToFolderMapping = new VirtualHostNameToFolderMapping(
				this.allocateString(virtualHostName),
				this.allocateString(
						Optional.ofNullable(folderMapping)
								.map(Path::toString)
								.orElseGet(() -> Path.of("").toAbsolutePath().toString())
				),
				accessKind.ordinal()
		);
		this.needsExtension = true;
		return this;
	}
	
	@Override
	public WebViewBuilderImpl setWebMessageListener(final WebMessageListener<HResult, EventExchangeImpl> webMessageListener) {
		@SuppressWarnings("SpellCheckingInspection")
		final var messageReceivedEventHandlerVtbl = new IUnknownFactoryImpl<>(
				this.arena,
				ICoreWebView2WebMessageReceivedEventHandlerVtbl::allocate,
				Invoke::allocate
		).wrapCallback(
				(handlerSelf, iCoreWebView2, webMessageReceivedEventArgs) ->
						webMessageListener.onWebMessageReceived(
								new EventExchangeImpl(
										this.arena,
										iCoreWebView2,
										webMessageReceivedEventArgs
								)
						).value(),
				self ->
						ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerVtbl.AddRef.invoke(
								ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerVtbl.AddRef(
										ICoreWebView2CreateCoreWebView2ControllerCompletedHandler.lpVtbl(self)
								),
								self
						)
		);
		this.messageReceivedEventHandler = ICoreWebView2WebMessageReceivedEventHandler.allocate(this.arena);
		ICoreWebView2WebMessageReceivedEventHandler.lpVtbl(this.messageReceivedEventHandler, messageReceivedEventHandlerVtbl);
		return this;
	}
	
	@Override
	public WebViewBuilderImpl navigate(final @NotNull String content) {
		this.contentString = this.allocateString(content);
		return this;
	}
	
	@Override
	public WebViewBuilderImpl navigate(final @NotNull URI uri) {
		this.uriString = this.allocateString(uri.normalize().toASCIIString());
		return this;
	}
	
	@Override
	public WebViewWindowImpl buildWebView() {
		try (var webView2DLLLoader = new WebView2DLLLoader(this.arena)) {
			final var hResult = webView2DLLLoader.createWebViewEnvironmentWithOptionsInternal(
					0,
					Optional.ofNullable(this.userDataFolder)
							.map(this::allocateString)
							.orElse(MemorySegment.NULL),
					MemorySegment.NULL,
					this.createCoreWebView2EnvironmentCompletedHandler(
							this.createCoreWebView2ControllerCompletedHandler()
					)
			);
			if ( !(hResult instanceof HResult.S_OK) ) {
				throw new RuntimeException(hResult.toString());
			}
		}
		final var webViewWindow = new WebViewWindowImpl(this.arena, this.platformWindow, this.webview2_PP);
		this.webViewController.setCore(webViewWindow);
		return webViewWindow;
	}
	
	private MemorySegment createCoreWebView2ControllerCompletedHandler() {
		final var controllerCompletedHandler = ICoreWebView2CreateCoreWebView2ControllerCompletedHandler.allocate(this.arena);
		ICoreWebView2CreateCoreWebView2ControllerCompletedHandler.lpVtbl(
				controllerCompletedHandler,
				new IUnknownFactoryImpl<IUnknownFactory.Invoke>(
						this.arena,
						ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerVtbl::allocate,
						(f, a) -> ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerVtbl.Invoke.allocate(f::invoke, a)
				).wrapCallback(
						(ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerSelf, errorCode, webView2Controller) ->
								coreWebView2ControllerCompletedHandlerInvoke(ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerSelf, errorCode, webView2Controller).value(),
						self ->
								ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerVtbl.AddRef.invoke(
										ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerVtbl.AddRef(
												ICoreWebView2CreateCoreWebView2ControllerCompletedHandler.lpVtbl(self)
										),
										self
								)
				)
		);
		return controllerCompletedHandler;
	}
	
	private HResult coreWebView2ControllerCompletedHandlerInvoke(
			final MemorySegment ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerSelf,
			final int errorCode,
			final MemorySegment webView2Controller
	) {
		if ( !MemorySegment.NULL.equals(webView2Controller) ) {
			final var hResult = HResult.warpResult(this.getWebView2(webView2Controller));
			if ( !(hResult instanceof HResult.S_OK) ) {
				throw new RuntimeException(hResult.toString());
			}
		}
		final var webview2Pointer = this.getWebview2Pointer();
		if ( this.needsExtension ) {
			final var hResult = this.extendedSetting(webview2Pointer);
			if ( !(hResult instanceof HResult.S_OK) ) {
				return hResult;
			}
		}
		// 获取（可能更新后的）虚表指针
		@SuppressWarnings("SpellCheckingInspection")
		final var webview2lpVtbl = ICoreWebView2.lpVtbl(webview2Pointer);
		var hResult = this.webView2Settings(webview2Pointer, webview2lpVtbl);
		if ( !(hResult instanceof HResult.S_OK) ) return hResult;
		hResult = this.configByController(webView2Controller);
		if ( !(hResult instanceof HResult.S_OK) ) return hResult;
		hResult = this.setWebMessageListener(webview2Pointer, webview2lpVtbl);
		if ( !(hResult instanceof HResult.S_OK) ) return hResult;
		return this.contentString != null ?
				       this.navigateToString(webview2Pointer, webview2lpVtbl)
				       :
				       this.uriString != null ?
						       this.navigate(webview2Pointer, webview2lpVtbl)
						       :
						       HResult.S_OK.SINGLETON;
	}
	
	private HResult extendedSetting(final MemorySegment webview2Pointer) {
		final var webview2_22_PP = this.getICoreWebView2_22(webview2Pointer);
		final var webview2_22Pointer = webview2_22_PP.get(ICoreWebView2_22.POINTER$LAYOUT, 0);
		return this.setVirtualHostNameToFolderMapping(webview2_22Pointer);
	}
	
	private int getWebView2(final MemorySegment controller) {
		this.webViewController.setController(controller);
		@SuppressWarnings("SpellCheckingInspection")
		final var webviewControllerlpVtbl = ICoreWebView2Controller.lpVtbl(controller);
		ICoreWebView2ControllerVtbl.AddRef.invoke(
				ICoreWebView2ControllerVtbl.AddRef(webviewControllerlpVtbl),
				controller
		);
		return ICoreWebView2ControllerVtbl.get_CoreWebView2.invoke(
				ICoreWebView2ControllerVtbl.get_CoreWebView2(webviewControllerlpVtbl),
				controller,
				this.webview2_PP
		);
	}
	
	private HResult configByController(final MemorySegment controller) {
		@SuppressWarnings("SpellCheckingInspection")
		final var webviewControllerlpVtbl = ICoreWebView2Controller.lpVtbl(controller);
		final var bounds = tagRECT.allocate(this.arena);
		return this.platformWindow.getClientRect(bounds) ?
				       HResult.warpResult(
						       ICoreWebView2ControllerVtbl.put_Bounds.invoke(
								       ICoreWebView2ControllerVtbl.put_Bounds(webviewControllerlpVtbl),
								       controller,
								       bounds
						       )
				       )
				       :
				       HResult.E_FAIL.SINGLETON;
	}
	
	private HResult navigate(final MemorySegment webview2Pointer, @SuppressWarnings("SpellCheckingInspection") final MemorySegment webview2lpVtbl) {
		return HResult.warpResult(
				ICoreWebView2Vtbl.Navigate.invoke(
						ICoreWebView2Vtbl.Navigate(
								webview2lpVtbl
						),
						webview2Pointer,
						this.uriString
				)
		);
	}
	
	private HResult navigateToString(final MemorySegment webview2Pointer, @SuppressWarnings("SpellCheckingInspection") final MemorySegment webview2lpVtbl) {
		return HResult.warpResult(
				ICoreWebView2Vtbl.NavigateToString.invoke(
						ICoreWebView2Vtbl.NavigateToString(
								webview2lpVtbl
						),
						webview2Pointer,
						this.contentString
				)
		);
	}
	
	private MemorySegment createCoreWebView2EnvironmentCompletedHandler(final MemorySegment controllerCompletedHandler) {
		@SuppressWarnings("SpellCheckingInspection")
		final var environmentCompletedHandlerVtbl = new IUnknownFactoryImpl<IUnknownFactory.Invoke>(
				this.arena,
				ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandlerVtbl::allocate,
				(f, a) -> ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandlerVtbl.Invoke.allocate(f::invoke, a)
		).wrapCallback(
				(self, hresult, createdEnvironment) ->
						ICoreWebView2EnvironmentVtbl.CreateCoreWebView2Controller.invoke(
								ICoreWebView2EnvironmentVtbl.CreateCoreWebView2Controller(
										ICoreWebView2Environment.lpVtbl(createdEnvironment)
								),
								createdEnvironment,
								this.platformWindow.getHandler(),
								controllerCompletedHandler
						),
				self ->
						ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandlerVtbl.AddRef.invoke(
								ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandlerVtbl.AddRef(
										ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandler.lpVtbl(self)
								),
								self
						)
		);
		final var environmentCompletedHandler = ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandler.allocate(this.arena);
		ICoreWebView2CreateCoreWebView2EnvironmentCompletedHandler.lpVtbl(
				environmentCompletedHandler, environmentCompletedHandlerVtbl
		);
		return environmentCompletedHandler;
	}
	
	private MemorySegment getICoreWebView2_22(final MemorySegment coreWebView2) {
		final var webview2_22_PP = this.arena.allocateFrom(
				ValueLayout.ADDRESS.withTargetLayout(ICoreWebView2_22.POINTER$LAYOUT),
				this.arena.allocateFrom(
						ICoreWebView2_22.POINTER$LAYOUT,
						MemorySegment.NULL
				)
		);
		final var hResult = HResult.warpResult(
				ICoreWebView2Vtbl.QueryInterface.invoke(
						ICoreWebView2Vtbl.QueryInterface(ICoreWebView2.lpVtbl(coreWebView2)),
						coreWebView2,
						ICoreWebView2_22Holder.IID_ICoreWebView2_22,
						webview2_22_PP
				)
		);
		if ( hResult instanceof HResult.S_OK ) return webview2_22_PP;
		else throw new RuntimeException("ICoreWebView2_22 could not be queried. " + hResult);
	}
	
	private HResult webView2Settings(
			final MemorySegment webview2Pointer, @SuppressWarnings("SpellCheckingInspection") final MemorySegment webview2lpVtbl
	) {
		final var settingsTargetLayout = PointerLayoutHolder.I_CORE_WEB_VIEW_2_SETTINGS_POINTER;
		final var settings_PP = arena.allocateFrom(
				ValueLayout.ADDRESS.withTargetLayout(
						settingsTargetLayout
				),
				arena.allocateFrom(
						settingsTargetLayout,
						MemorySegment.NULL
				)
		);
		final var hResult = HResult.warpResult(
				ICoreWebView2Vtbl.get_Settings.invoke(
						ICoreWebView2Vtbl.get_Settings(
								webview2lpVtbl
						),
						webview2Pointer,
						settings_PP
				)
		);
		if ( hResult instanceof HResult.S_OK ) {
			return HResult.warpResult(
					this.putWebView2Settings(
							new ICoreWebView2Settings(
									settings_PP.get(settingsTargetLayout, 0)
							)
					)
			);
		} else return hResult;
	}
	
	private int putWebView2Settings(
			final ICoreWebView2Settings webView2Settings
	) {
		return IntStream.of(
						webView2Settings.scriptEnabled.set(this.enableScript),
						webView2Settings.webMessageEnabled.set(this.enableWebMessage),
						webView2Settings.devToolsEnabled.set(this.enableDevTools),
						webView2Settings.defaultContextMenusEnabled.set(this.enableContextMenu),
						webView2Settings.zoomControlEnabled.set(this.enabledZoomControl),
						webView2Settings.statusBarEnabled.set(this.enableStatusBar),
						webView2Settings.defaultScriptDialogsEnabled.set(this.enabledDefaultScriptDialogs)
				)
				       .dropWhile(hResult -> hResult != HResult.S_OK.SINGLETON.value())
				       .findFirst()
				       .orElse(HResult.S_OK.SINGLETON.value());
	}
	
	private HResult setVirtualHostNameToFolderMapping(final MemorySegment webview2_22Pointer) {
		return this.virtualHostNameToFolderMapping != null ?
				       HResult.warpResult(
						       ICoreWebView2_22Vtbl.SetVirtualHostNameToFolderMapping.invoke(
								       ICoreWebView2_22Vtbl.SetVirtualHostNameToFolderMapping(ICoreWebView2_22.lpVtbl(webview2_22Pointer)),
								       webview2_22Pointer,
								       this.virtualHostNameToFolderMapping.virtualHostName(),
								       this.virtualHostNameToFolderMapping.folderMapping(),
								       this.virtualHostNameToFolderMapping.accessKind()
						       )
				       )
				       :
				       HResult.S_OK.SINGLETON;
	}
	
	private HResult setWebMessageListener(final MemorySegment webview2Pointer, @SuppressWarnings("SpellCheckingInspection") final MemorySegment webview2lpVtbl) {
		return this.messageReceivedEventHandler != null ?
				       HResult.warpResult(
						       ICoreWebView2Vtbl.add_WebMessageReceived.invoke(
								       ICoreWebView2Vtbl.add_WebMessageReceived(webview2lpVtbl),
								       webview2Pointer,
								       this.messageReceivedEventHandler,
								       MemorySegment.NULL
						       )
				       ) : HResult.S_OK.SINGLETON;
	}
	
	private MemorySegment getWebview2Pointer() {
		return this.webview2_PP.get(ICoreWebView2.POINTER$LAYOUT, 0);
	}
	
	@SuppressWarnings("StaticMethodReferencedViaSubclass")
	private static final class ICoreWebView2_22Holder {
		private static final MemorySegment IID_ICoreWebView2_22 = IID.allocate(Arena.ofAuto());
		
		static {
			IID.Data1(IID_ICoreWebView2_22, 0xDB75DFC7);
			IID.Data2(IID_ICoreWebView2_22, (short) 43095);
			IID.Data3(IID_ICoreWebView2_22, (short) 17970);
			IID.Data4(
					IID_ICoreWebView2_22,
					MemorySegment.ofArray(
							new byte[]{
									(byte) 0xA3, (byte) 0x98, 0x69, 0x69, (byte) 0xDD, (byte) 0xE2, 0x6C, 0x0A
							}
					)
			);
		}
	}
}

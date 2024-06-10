package io.github.IzumiDIA.windows.builder.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.VirtualHostNameToFolderMapping;
import io.github.IzumiDIA.WebViewWindow.WebMessageListener;
import io.github.IzumiDIA.builder.WebViewBuilder;
import io.github.IzumiDIA.constant.enums.COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND;
import io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl;
import io.github.IzumiDIA.windows.factory.IUnknownFactory;
import io.github.IzumiDIA.windows.factory.impl.IUnknownFactoryImpl;
import io.github.IzumiDIA.windows.impl.WebViewWindowImpl;
import io.github.IzumiDIA.windows.impl.WebViewWindowImpl.EventExchangeImpl;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jetbrains.annotations.NotNull;
import org.jextract.*;
import org.jextract.ICoreWebView2WebMessageReceivedEventHandlerVtbl.Invoke;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.IntStream;

public class WebViewBuilderImpl extends WindowsNativeObject implements WebViewBuilder {
	public static final int S_OK = 0;
	private static final AddressLayout WEBVIEW_2_POINTER_LAYOUT = ValueLayout.ADDRESS.withTargetLayout(ICoreWebView2.layout());
	private static final AddressLayout WEBVIEW_2_22_POINTER_LAYOUT = ValueLayout.ADDRESS.withTargetLayout(ICoreWebView2_22.layout());
	private final WebViewControllerImpl webViewController;
	private final PlatformWindow platformWindow;
	private final MemorySegment webview2_PP;
	private MemorySegment contentString = null, uriString = null;
	private String userDataFolder = null;
	private boolean needsExtension = false,
			enableScript = false,
			enableWebMessage = false,
			enableDevTools = false,
			enableContextMenu = false,
			enabledZoomControl = false,
			enableStatusBar = false;
	private VirtualHostNameToFolderMapping virtualHostNameToFolderMapping = null;
	private MemorySegment messageReceivedEventHandler = null;
	
	public WebViewBuilderImpl(
			final WebViewControllerImpl webViewController,
			final Arena arena,
			final PlatformWindow platformWindow
	) {
		super(arena);
		this.webViewController = webViewController;
		this.platformWindow = platformWindow;
		this.webview2_PP = this.arena.allocateFrom(
				ValueLayout.ADDRESS.withTargetLayout(
						WEBVIEW_2_POINTER_LAYOUT
				),
				this.arena.allocateFrom(
						WEBVIEW_2_POINTER_LAYOUT,
						MemorySegment.NULL
				)
		);
	}
	
	@Override
	public WebViewBuilderImpl setUserDataFolder(final Path userDataFolderPath) {
		this.userDataFolder = userDataFolderPath.toAbsolutePath().toString();
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
	public WebViewBuilderImpl setVirtualHostNameToFolderMapping(
			final @NotNull String virtualHostName,
			final Path folderMapping,
			final @NotNull COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND accessKind
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
	public WebViewBuilderImpl setWebMessageListener(final WebMessageListener webMessageListener) {
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
						),
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
			webView2DLLLoader.createWebViewEnvironmentWithOptionsInternal(
					0,
					Optional.ofNullable(this.userDataFolder)
							.map(this::allocateString)
							.orElse(MemorySegment.NULL),
					MemorySegment.NULL,
					this.createCoreWebView2EnvironmentCompletedHandler(
							this.createCoreWebView2ControllerCompletedHandler()
					)
			);
		} catch (final Throwable e) {
			throw new RuntimeException(e);
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
						this::coreWebView2ControllerCompletedHandlerInvoke,
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
	
	private int coreWebView2ControllerCompletedHandlerInvoke(
			final MemorySegment ICoreWebView2CreateCoreWebView2ControllerCompletedHandlerSelf,
			final int errorCode,
			final MemorySegment webView2Controller
	) {
		if ( !MemorySegment.NULL.equals(webView2Controller) ) {
			this.getWebView2(webView2Controller);
		}
		final var webview2Pointer = this.getWebview2Pointer();
		if ( this.needsExtension ) {
			final var webview2_22_PP = this.getICoreWebView2_22(webview2Pointer);
			final var webview2_22Pointer = webview2_22_PP.get(WEBVIEW_2_22_POINTER_LAYOUT, 0);
			final var hResult = this.setVirtualHostNameToFolderMapping(webview2_22Pointer);
			if ( hResult != S_OK ) {
				return hResult;
			}
		}
		var hResult = this.putWebView2Settings(webview2Pointer);
		if ( hResult != S_OK ) return hResult;
		hResult = this.configByController(webView2Controller);
		if ( hResult != S_OK ) return hResult;
		hResult = this.setWebMessageListener(webview2Pointer);
		if ( hResult != S_OK ) return hResult;
		return this.contentString != null ?
				       this.navigateToString(webview2Pointer)
				       :
				       this.uriString != null ?
						       this.navigate(webview2Pointer)
						       :
						       S_OK;
	}
	
	private int getWebView2(final MemorySegment controller) {
		this.webViewController.setController(controller);
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
	
	private int configByController(final MemorySegment controller) {
		final var webviewControllerlpVtbl = ICoreWebView2Controller.lpVtbl(controller);
		final var bounds = tagRECT.allocate(this.arena);
		this.platformWindow.getClientRect(bounds);
		return ICoreWebView2ControllerVtbl.put_Bounds.invoke(
				ICoreWebView2ControllerVtbl.put_Bounds(webviewControllerlpVtbl),
				controller,
				bounds
		);
	}
	
	private int navigate(final MemorySegment webview2Pointer) {
		return ICoreWebView2Vtbl.Navigate.invoke(
				ICoreWebView2Vtbl.Navigate(
						ICoreWebView2.lpVtbl(webview2Pointer)
				),
				webview2Pointer,
				this.uriString
		);
	}
	
	private int navigateToString(final MemorySegment webview2Pointer) {
		return ICoreWebView2Vtbl.NavigateToString.invoke(
				ICoreWebView2Vtbl.NavigateToString(
						ICoreWebView2.lpVtbl(webview2Pointer)
				),
				webview2Pointer,
				this.contentString
		);
	}
	
	private MemorySegment createCoreWebView2EnvironmentCompletedHandler(final MemorySegment controllerCompletedHandler) {
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
				ValueLayout.ADDRESS.withTargetLayout(WEBVIEW_2_22_POINTER_LAYOUT),
				this.arena.allocateFrom(
						WEBVIEW_2_22_POINTER_LAYOUT,
						MemorySegment.NULL
				)
		);
		if (
				ICoreWebView2Vtbl.QueryInterface.invoke(
						ICoreWebView2Vtbl.QueryInterface(ICoreWebView2.lpVtbl(coreWebView2)),
						coreWebView2,
						ICoreWebView2_22Holder.IID_ICoreWebView2_22,
						webview2_22_PP
				) == S_OK
		) return webview2_22_PP;
		else throw new RuntimeException("Could not query ICoreWebView2_22.");
	}
	
	private int putWebView2Settings(final MemorySegment webview2Pointer) {
		final var settingsTargetLayout = ValueLayout.ADDRESS.withTargetLayout(ICoreWebView2Settings.layout());
		final var settings_PP = arena.allocateFrom(
				ValueLayout.ADDRESS.withTargetLayout(
						settingsTargetLayout
				),
				arena.allocateFrom(
						settingsTargetLayout,
						MemorySegment.NULL
				)
		);
		ICoreWebView2Vtbl.get_Settings.invoke(
				ICoreWebView2Vtbl.get_Settings(
						ICoreWebView2.lpVtbl(webview2Pointer)
				),
				webview2Pointer,
				settings_PP
		);
		final var settings_P = settings_PP.get(settingsTargetLayout, 0);
		final var settingsVtbl = ICoreWebView2Settings.lpVtbl(settings_P);
		return IntStream.of(
						ICoreWebView2SettingsVtbl.put_IsScriptEnabled.invoke(
								ICoreWebView2SettingsVtbl.put_IsScriptEnabled(settingsVtbl),
								settings_P,
								this.enableScript
						),
						ICoreWebView2SettingsVtbl.put_IsWebMessageEnabled.invoke(
								ICoreWebView2SettingsVtbl.put_IsWebMessageEnabled(settingsVtbl),
								settings_P,
								this.enableWebMessage
						),
						ICoreWebView2SettingsVtbl.put_AreDevToolsEnabled.invoke(
								ICoreWebView2SettingsVtbl.put_AreDevToolsEnabled(settingsVtbl),
								settings_P,
								this.enableDevTools
						),
						ICoreWebView2SettingsVtbl.put_AreDefaultContextMenusEnabled.invoke(
								ICoreWebView2SettingsVtbl.put_AreDefaultContextMenusEnabled(settingsVtbl),
								settings_P,
								this.enableContextMenu
						),
						ICoreWebView2SettingsVtbl.put_IsZoomControlEnabled.invoke(
								ICoreWebView2SettingsVtbl.put_IsZoomControlEnabled(settingsVtbl),
								settings_P,
								this.enabledZoomControl
						),
						ICoreWebView2SettingsVtbl.put_IsStatusBarEnabled.invoke(
								ICoreWebView2SettingsVtbl.put_IsStatusBarEnabled(settingsVtbl),
								settings_P,
								this.enableStatusBar
						)
				)
				       .dropWhile(hResult -> hResult != S_OK)
				       .findFirst()
				       .orElse(S_OK);
	}
	
	private int setVirtualHostNameToFolderMapping(final MemorySegment webview2_22Pointer) {
		if ( this.virtualHostNameToFolderMapping != null ) {
			return ICoreWebView2_22Vtbl.SetVirtualHostNameToFolderMapping.invoke(
					ICoreWebView2_22Vtbl.SetVirtualHostNameToFolderMapping(ICoreWebView2_22.lpVtbl(webview2_22Pointer)),
					webview2_22Pointer,
					this.virtualHostNameToFolderMapping.virtualHostName(),
					this.virtualHostNameToFolderMapping.folderMapping(),
					this.virtualHostNameToFolderMapping.accessKind()
			);
		} else return S_OK;
	}
	
	private int setWebMessageListener(final MemorySegment webview2Pointer) {
		return this.messageReceivedEventHandler != null ?
				       ICoreWebView2Vtbl.add_WebMessageReceived.invoke(
						       ICoreWebView2Vtbl.add_WebMessageReceived(ICoreWebView2.lpVtbl(webview2Pointer)),
						       webview2Pointer,
						       this.messageReceivedEventHandler,
						       MemorySegment.NULL
				       )
				       :
				       S_OK;
	}
	
	private MemorySegment getWebview2Pointer() {
		return this.webview2_PP.get(WEBVIEW_2_POINTER_LAYOUT, 0);
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

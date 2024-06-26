package io.github.IzumiDIA.windows.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.WebViewWindow;
import org.jetbrains.annotations.NotNull;
import org.jextract.ICoreWebView2;
import org.jextract.ICoreWebView2Vtbl;
import org.jextract.ICoreWebView2Vtbl.PostWebMessageAsString;
import org.jextract.ICoreWebView2WebMessageReceivedEventArgsVtbl;
import org.jextract.ICoreWebView2WebMessageReceivedEventHandler;
import org.jextract.MSG;
import org.jextract.Windows;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.concurrent.LinkedTransferQueue;

import static io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl.EXECUTE_SCRIPT;
import static io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl.WM_CLOSE;

public class WebViewWindowImpl extends WindowsNativeObject implements WebViewWindow {
	private static final AddressLayout ADDRESS_LAYOUT = ValueLayout.ADDRESS.withTargetLayout(ICoreWebView2.layout());
	private final PlatformWindow platformWindow;
	private final MemorySegment webview2_PP;
	private final LinkedTransferQueue<String> scriptExecutionQueue = new LinkedTransferQueue<>();
	
	public WebViewWindowImpl(final Arena arena, final PlatformWindow platformWindow, final MemorySegment webview2_PP) {
		super(arena);
		this.platformWindow = platformWindow;
		this.webview2_PP = webview2_PP;
	}
	
	@Override
	public void run() {
		final var message = MSG.allocate(this.arena);
		while ( Windows.GetMessageW(message, MemorySegment.NULL, 0, 0) ) {
			Windows.TranslateMessage(message);
			Windows.DispatchMessageW(message);
		}
	}
	
	@Override
	public int executeScript(final @NotNull String javascript) {
		final var webview2Pointer = this.webview2_PP.get(ADDRESS_LAYOUT, 0);
		return ICoreWebView2Vtbl.ExecuteScript.invoke(
				ICoreWebView2Vtbl.ExecuteScript(ICoreWebView2.lpVtbl(webview2Pointer)),
				webview2Pointer,
				this.allocateString(
						javascript
				),
				MemorySegment.NULL
		);
	}
	
	@Override
	public boolean executeScriptAsync(final @NotNull String javascript) {
		return this.scriptExecutionQueue.offer(javascript) && this.platformWindow.postMessage(EXECUTE_SCRIPT, 0L, 0L);
	}
	
	@Override
	public int consumeScript() {
		return this.executeScript(this.scriptExecutionQueue.remove());
	}
	
	@Override
	public boolean terminate() {
		return this.platformWindow.postMessage(WM_CLOSE, 0L, 0L);
	}
	
	public static class EventExchangeImpl extends WindowsNativeObject implements WebMessageListener.EventExchange<HResult> {
		private final MemorySegment coreWebView2, webMessageReceivedEventArgs;
		
		public EventExchangeImpl(final Arena arena, final MemorySegment coreWebView2, final MemorySegment webMessageReceivedEventArgs) {
			super(arena);
			this.coreWebView2 = coreWebView2;
			this.webMessageReceivedEventArgs = webMessageReceivedEventArgs;
		}
		
		@Override
		public String tryGetWebMessageAsString() {
			final var bufferAddress = this.createBuffer();
			return this.tryGetWebMessageAsString(bufferAddress).isOK() ?
					       this.getString(bufferAddress)
					       :
					       null;
		}
		
		@Override
		public HResult tryGetWebMessageAsString(final @NotNull MemorySegment bufferAddress) {
			return HResult.warpResult(
					ICoreWebView2WebMessageReceivedEventArgsVtbl.TryGetWebMessageAsString.invoke(
							ICoreWebView2WebMessageReceivedEventArgsVtbl.TryGetWebMessageAsString(
									ICoreWebView2WebMessageReceivedEventHandler.lpVtbl(this.webMessageReceivedEventArgs)
							),
							this.webMessageReceivedEventArgs,
							bufferAddress
					)
			);
		}
		
		@Override
		public HResult postWebMessageAsString(final @NotNull String messageToWebView) {
			return this.postWebMessageAsString(this.allocateString(messageToWebView));
		}
		
		@Override
		public HResult postWebMessageAsString(final @NotNull MemorySegment messageToWebView) {
			return HResult.warpResult(
					PostWebMessageAsString.invoke(
							ICoreWebView2Vtbl.PostWebMessageAsString(
									ICoreWebView2.lpVtbl(this.coreWebView2)
							),
							this.coreWebView2,
							messageToWebView
					)
			);
		}
		
		@Override
		public MemorySegment createBuffer() {
			return this.arena.allocateFrom(ValueLayout.ADDRESS, MemorySegment.NULL);
		}
		
		@Override
		public HResult.OK OK() {
			return HResult.S_OK.SINGLETON;
		}
		
		@Override
		public HResult.ABORT abort() {
			return HResult.E_ABORT.SINGLETON;
		}
		
		@Override
		public HResult.FAIL fail() {
			return HResult.E_FAIL.SINGLETON;
		}
		
		@Override
		public HResult.UNEXPECTED unexpected() {
			return HResult.E_UNEXPECTED.SINGLETON;
		}
	}
}

package io.github.IzumiDIA.windows.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.windows.impl.HResult.E_UNEXPECTED;
import io.github.IzumiDIA.windows.impl.HResult.S_OK;
import org.jetbrains.annotations.NotNull;
import org.jextract.ICoreWebView2;
import org.jextract.ICoreWebView2Vtbl;
import org.jextract.ICoreWebView2Vtbl.PostWebMessageAsString;
import org.jextract.ICoreWebView2WebMessageReceivedEventArgs;
import org.jextract.ICoreWebView2WebMessageReceivedEventArgsVtbl;
import org.jextract.LayoutUtils;
import org.jextract.MSG;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedTransferQueue;

import static io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl.EXECUTE_SCRIPT;
import static org.jextract.Windows.WM_CLOSE;

public class WebViewWindowImpl extends WindowsNativeObject implements WebViewWindow {
	/**
	 * A temporary replacement for <a title="Stable Value" href="https://openjdk.org/jeps/502">Stable Value</a>。
	 */
	private static final VarHandle HANDLE;
	private final PlatformWindow platformWindow;
	private final MemorySegment webview2_PP;
	@SuppressWarnings("FieldMayBeFinal")
	private MemorySegment webview2Pointer = null;
	private final LinkedTransferQueue<String> scriptExecutionQueue = new LinkedTransferQueue<>();
	
	static {
		try {
			HANDLE = MethodHandles.lookup().findVarHandle(WebViewWindowImpl.class, "webview2Pointer", MemorySegment.class).withInvokeExactBehavior();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public WebViewWindowImpl(final Arena arena, final PlatformWindow platformWindow, final MemorySegment webview2_PP) {
		super(arena);
		this.platformWindow = platformWindow;
		this.webview2_PP = webview2_PP;
	}
	
	@SuppressWarnings("java:S3252")
	@Override
	public void run() {
		final var message = new MSG(this.arena);
		
		try {
			while ( message.getMessage(MemorySegment.NULL, 0, 0) ) {
				if ( !message.translateMessage() ) {
					message.dispatchMessage();
				}
			}
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	@Override
	public HResult executeScript(final @NotNull String javascript) {
		final var webview2Pointer = this.webview2Pointer();
		return HResult.warpResult(
				ICoreWebView2Vtbl.ExecuteScript.invoke(
						ICoreWebView2Vtbl.ExecuteScript(
								ICoreWebView2.lpVtbl(webview2Pointer)
						),
						webview2Pointer,
						this.allocateString(
								javascript
						),
						MemorySegment.NULL
				)
		);
	}
	
	private MemorySegment webview2Pointer() {
		var res = (MemorySegment) HANDLE.get(this);
		if (res != null) {
			return res;
		}else {
			synchronized (this) {
				res = (MemorySegment) HANDLE.getVolatile(this);
				if ( res == null ) {
					HANDLE.setVolatile(this, res = this.webview2_PP.get(ICoreWebView2.POINTER$LAYOUT, 0));
				}
				return res;
			}
		}
	}
	
	@Override
	public boolean executeScriptAsync(final @NotNull String javascript) {
		return this.scriptExecutionQueue.offer(javascript)
		       &&
		       this.platformWindow.postMessage(EXECUTE_SCRIPT, 0L, 0L);
	}
	
	@Override
	public HResult postWebMessageAsString(final @NotNull String messageToWebView) {
		final var webview2Pointer = this.webview2_PP.get(ICoreWebView2.POINTER$LAYOUT, 0);
		return HResult.warpResult(
				PostWebMessageAsString.invoke(
						ICoreWebView2Vtbl.PostWebMessageAsString(
								ICoreWebView2.lpVtbl(webview2Pointer)
						),
						webview2Pointer,
						this.allocateString(messageToWebView)
				)
		);
	}
	
	@SuppressWarnings("CallToPrintStackTrace")
	@Override
	public HResult consumeScript() {
		try {
			return this.executeScript(this.scriptExecutionQueue.remove());
		}catch (final NoSuchElementException noSuchElementException) {
			noSuchElementException.printStackTrace();
			return E_UNEXPECTED.SINGLETON;
		}
	}
	
	@Override
	public boolean terminate() {
		return this.platformWindow.postMessage(WM_CLOSE, 0L, 0L);
	}
	
	public static final class EventExchangeImpl extends WindowsNativeObject implements WebMessageListener.EventExchange {
		private final MemorySegment coreWebView2, webMessageReceivedEventArgs;
		private final MemorySegment bufferAddress = this.createBuffer();
		private int lastHResult;
		
		public EventExchangeImpl(final Arena arena, final MemorySegment coreWebView2, final MemorySegment webMessageReceivedEventArgs) {
			super(arena);
			this.coreWebView2 = coreWebView2;
			this.webMessageReceivedEventArgs = webMessageReceivedEventArgs;
		}
		
		@Override
		public String tryGetWebMessageAsString() {
			return this.tryGetWebMessageAsString(this.bufferAddress) == S_OK.SINGLETON.value() ?
					       this.getString(this.bufferAddress)
					       :
					       null;
		}
		
		@Override
		public HResult tryGetWebMessageAsStringToBuffer(final @NotNull MemorySegment bufferAddress) {
			return HResult.warpResult(
					this.tryGetWebMessageAsString(bufferAddress)
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
		public HResult getResult() {
			return HResult.warpResult(this.lastHResult);
		}
		
		@Override
		public HResult.S_OK OK() {
			return HResult.S_OK.SINGLETON;
		}
		
		@Override
		public HResult.E_ABORT abort() {
			return HResult.E_ABORT.SINGLETON;
		}
		
		@Override
		public HResult.E_FAIL fail() {
			return HResult.E_FAIL.SINGLETON;
		}
		
		@Override
		public HResult.E_UNEXPECTED unexpected() {
			return HResult.E_UNEXPECTED.SINGLETON;
		}
		
		@Override
		public String getString(final @NotNull MemorySegment bufferAddress) {
			return bufferAddress.get(LayoutUtils.LPWSTR, 0)
					       .getString(0, StandardCharsets.UTF_16LE);
		}
		
		private MemorySegment createBuffer() {
			return this.arena.allocateFrom(LayoutUtils.LPWSTR, MemorySegment.NULL);
		}
		
		private int tryGetWebMessageAsString(final @NotNull MemorySegment bufferAddress) {
			final var tryGetWebMessageAsStringFunctionPointer = ICoreWebView2WebMessageReceivedEventArgsVtbl.TryGetWebMessageAsString(
					ICoreWebView2WebMessageReceivedEventArgs.lpVtbl(this.webMessageReceivedEventArgs)
			);
			return this.lastHResult = ICoreWebView2WebMessageReceivedEventArgsVtbl.TryGetWebMessageAsString.invoke(
					tryGetWebMessageAsStringFunctionPointer,
					this.webMessageReceivedEventArgs,
					bufferAddress
			);
		}
	}
}

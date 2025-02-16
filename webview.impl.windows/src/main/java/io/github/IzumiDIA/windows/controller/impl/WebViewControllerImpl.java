package io.github.IzumiDIA.windows.controller.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.controller.WebViewController;
import io.github.IzumiDIA.listener.WindowOnCloseListener;
import io.github.IzumiDIA.listener.WindowOnDestroyListener;
import io.github.IzumiDIA.windows.impl.HResult;
import io.github.IzumiDIA.windows.impl.WebViewWindowImpl;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jetbrains.annotations.NotNull;
import org.jextract.ICoreWebView2Controller;
import org.jextract.ICoreWebView2ControllerVtbl;
import org.jextract.ICoreWebView2ControllerVtbl.Close;
import org.jextract.ICoreWebView2ControllerVtbl.put_Bounds;
import org.jextract.WNDPROC;
import org.jextract.Windows;
import org.jextract.tagRECT;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

public class WebViewControllerImpl extends WindowsNativeObject implements WebViewController, WNDPROC.Function, AutoCloseable {
	public static final int EXECUTE_SCRIPT = Windows.WM_USER + 1;
	public static final int EXIT_SUCCESS = 0;
	private final MemorySegment bounds;
	private final WindowOnCloseListener windowOnCloseListener;
	private final WindowOnDestroyListener windowOnDestroyListener;
	private WebViewWindowImpl webViewWindow = null;
	
	private MemorySegment webviewController = null;
	
	@SuppressWarnings("SpellCheckingInspection")
	private MemorySegment webviewControllerlpVtbl = null;
	
	public WebViewControllerImpl(final Arena arena, final @NotNull WindowOnCloseListener windowOnCloseListener, final @NotNull WindowOnDestroyListener windowOnDestroyListener) {
		super(arena);
		this.bounds = tagRECT.allocate(this.arena);
		this.windowOnCloseListener = windowOnCloseListener;
		this.windowOnDestroyListener = windowOnDestroyListener;
	}
	
	public boolean isControllerExisting() {
		return this.webviewController != null;
	}
	
	@Override
	public void setController(final @NotNull MemorySegment controller) {
		this.webviewController = controller;
		this.webviewControllerlpVtbl = ICoreWebView2Controller.lpVtbl(this.webviewController);
	}
	
	public void setCore(final @NotNull WebViewWindowImpl webViewWindow) {
		this.webViewWindow = webViewWindow;
	}
	
	@Override
	public HResult putBounds(final @NotNull PlatformWindow platformWindow) {
		if ( this.isControllerExisting() ) {
			platformWindow.getClientRect(this.bounds);
			return HResult.warpResult(
					put_Bounds.invoke(
							ICoreWebView2ControllerVtbl.put_Bounds(this.webviewControllerlpVtbl),
							this.webviewController,
							this.bounds
					)
			);
		}else return HResult.S_OK.SINGLETON;
	}
	
	private int putBounds(final MemorySegment hWnd) {
		Windows.GetClientRect(hWnd, this.bounds);
		return put_Bounds.invoke(
				ICoreWebView2ControllerVtbl.put_Bounds(this.webviewControllerlpVtbl),
				this.webviewController,
				this.bounds
		);
	}
	
	@Override
	public long apply(final MemorySegment hWnd, final int message, final long wParam, final long lParam) {
		switch (message) {
			case Windows.WM_DESTROY -> Windows.PostQuitMessage(0);
			case Windows.WM_SIZE -> {
				return this.webviewController != null ?
						       this.putBounds(hWnd)
						       :
						       Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case Windows.WM_CLOSE -> {
				this.windowOnCloseListener.onClose();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case Windows.WM_QUIT -> {
				this.windowOnDestroyListener.onDestroy();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case EXECUTE_SCRIPT -> {
				return this.webViewWindow.consumeScript().value();
			}
			default -> {
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
		}
		return EXIT_SUCCESS;
	}
	
	public static class BuilderImpl implements Builder {
		private final Arena arena;
		private WindowOnCloseListener windowOnCloseListener;
		private WindowOnDestroyListener windowOnDestroyListener;
		
		public BuilderImpl(final Arena arena) {
			this.arena = arena;
		}
		@Override
		public Builder setOnCloseListener(final WindowOnCloseListener windowOnCloseListener) {
			this.windowOnCloseListener = windowOnCloseListener;
			return this;
		}
		
		@Override
		public Builder setOnDestroyListener(final WindowOnDestroyListener windowOnDestroyListener) {
			this.windowOnDestroyListener = windowOnDestroyListener;
			return this;
		}
		
		@Override
		public WebViewControllerImpl build() {
			return new WebViewControllerImpl(
					this.arena,
					Optional.ofNullable(this.windowOnCloseListener).orElse(() -> {}),
					Optional.ofNullable(this.windowOnDestroyListener).orElse(() -> {})
			);
		}
	}
	
	@Override
	public void close() {
		final var hResult = HResult.warpResult(
				Close.invoke(
						ICoreWebView2ControllerVtbl.Close(this.webviewControllerlpVtbl),
						this.webviewController
				)
		);
		if ( !(hResult instanceof HResult.S_OK) ) throw new RuntimeException(hResult.toString());
	}
}

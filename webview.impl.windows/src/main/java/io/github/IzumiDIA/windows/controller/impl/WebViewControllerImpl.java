package io.github.IzumiDIA.windows.controller.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.controller.WebViewController;
import io.github.IzumiDIA.listener.WindowOnCloseListener;
import io.github.IzumiDIA.listener.WindowOnDestroyListener;
import io.github.IzumiDIA.windows.impl.WebViewWindowImpl;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jetbrains.annotations.NotNull;
import org.jextract.ICoreWebView2Controller;
import org.jextract.ICoreWebView2ControllerVtbl;
import org.jextract.ICoreWebView2ControllerVtbl.put_Bounds;
import org.jextract.WNDPROC;
import org.jextract.Windows;
import org.jextract.tagRECT;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

import static io.github.IzumiDIA.windows.builder.impl.WebViewBuilderImpl.S_OK;

public class WebViewControllerImpl extends WindowsNativeObject implements WebViewController, WNDPROC.Function {
	public static final int
			WM_DESTROY = 0x0002,
			WM_SIZE = 0x0005,
			WM_CLOSE = 0x0010,
			WM_QUIT = 0x0012,
			WM_SIZING = 0x0214,
			WM_USER = 0x0400,
			EXECUTE_SCRIPT = WM_USER + 1,
			EXIT_SUCCESS = 0;
	private final MemorySegment bounds;
	private final WindowOnCloseListener windowOnCloseListener;
	private final WindowOnDestroyListener windowOnDestroyListener;
	private WebViewWindowImpl webViewWindow = null;
	private MemorySegment
			webviewController = null,
			webviewControllerlpVtbl = null;
	
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
	public int putBounds(final @NotNull PlatformWindow platformWindow) {
		if ( this.isControllerExisting() ) {
			platformWindow.getClientRect(this.bounds);
			return put_Bounds.invoke(
					ICoreWebView2ControllerVtbl.put_Bounds(this.webviewControllerlpVtbl),
					this.webviewController,
					this.bounds
			);
		}else return S_OK;
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
			case WM_DESTROY -> Windows.PostQuitMessage(0);
			case WM_SIZE -> {
				return this.webviewController != null ?
						       this.putBounds(hWnd)
						       :
						       Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case WM_CLOSE -> {
				this.windowOnCloseListener.onClose();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case WM_QUIT -> {
				this.windowOnDestroyListener.onDestroy();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case EXECUTE_SCRIPT -> {
				return this.webViewWindow.consumeScript();
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
}

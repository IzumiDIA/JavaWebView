package io.github.IzumiDIA.windows.controller.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.controller.WebViewController;
import io.github.IzumiDIA.listener.WindowOnCloseListener;
import io.github.IzumiDIA.listener.WindowOnDestroyListener;
import io.github.IzumiDIA.listener.WindowOnGainedFocusListener;
import io.github.IzumiDIA.listener.WindowOnLostFocusListener;
import io.github.IzumiDIA.listener.WindowOnMaximizeListener;
import io.github.IzumiDIA.listener.WindowOnMinimizeListener;
import io.github.IzumiDIA.windows.impl.HResult;
import io.github.IzumiDIA.windows.impl.HResult.E_UNEXPECTED;
import io.github.IzumiDIA.windows.impl.WebViewWindowImpl;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jetbrains.annotations.NotNull;
import org.jextract.Constants;
import org.jextract.Constants.SetWindowPosition;
import org.jextract.ICoreWebView2Controller;
import org.jextract.ICoreWebView2ControllerVtbl;
import org.jextract.ICoreWebView2ControllerVtbl.Close;
import org.jextract.ICoreWebView2ControllerVtbl.put_Bounds;
import org.jextract.WNDPROC;
import org.jextract.Windows;
import org.jextract.tagRECT;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Optional;

public class WebViewControllerImpl extends WindowsNativeObject implements WebViewController, WNDPROC.Function, AutoCloseable {
	public static final int TO_FRONT = Constants.WindowMessages.USER + 1,
			EXECUTE_SCRIPT = Constants.WindowMessages.USER + 2;
	public static final int EXIT_SUCCESS = 0;
	private final MemorySegment bounds;
	private final MethodHandle windowGetClientRectHandle;
	private final WindowOnCloseListener windowOnCloseListener;
	private final WindowOnDestroyListener windowOnDestroyListener;
	private final WindowOnMinimizeListener windowOnMinimizeListener;
	private final WindowOnMaximizeListener windowOnMaximizeListener;
	private final WindowOnGainedFocusListener windowOnGainedFocusListener;
	private final WindowOnLostFocusListener windowOnLostFocusListener;
	
	private WebViewWindowImpl webViewWindow = null;
	
	private MemorySegment webviewController = null;
	
	@SuppressWarnings("SpellCheckingInspection")
	private MemorySegment webviewControllerlpVtbl = null;
	private MemorySegment webviewControllerPutBounds = null;
	
	public WebViewControllerImpl(
			final Arena arena,
			final @NotNull WindowOnCloseListener windowOnCloseListener,
			final @NotNull WindowOnDestroyListener windowOnDestroyListener,
			final @NotNull WindowOnMinimizeListener windowOnMinimizeListener,
			final @NotNull WindowOnMaximizeListener windowOnMaximizeListener,
			final @NotNull WindowOnGainedFocusListener windowOnGainedFocusListener,
			final @NotNull WindowOnLostFocusListener windowOnLostFocusListener
	) {
		super(arena);
		this.bounds = tagRECT.allocate(this.arena);
		this.windowGetClientRectHandle = MethodHandles.insertArguments(Windows.GetClientRect$handle(), 1, this.bounds);
		this.windowOnCloseListener = windowOnCloseListener;
		this.windowOnDestroyListener = windowOnDestroyListener;
		this.windowOnMinimizeListener = windowOnMinimizeListener;
		this.windowOnMaximizeListener = windowOnMaximizeListener;
		this.windowOnGainedFocusListener = windowOnGainedFocusListener;
		this.windowOnLostFocusListener = windowOnLostFocusListener;
	}
	
	public boolean isControllerExisting() {
		return this.webviewController != null;
	}
	
	@Override
	public void setController(final @NotNull MemorySegment controller) {
		this.webviewController = controller;
		this.webviewControllerlpVtbl = ICoreWebView2Controller.lpVtbl(this.webviewController);
		this.webviewControllerPutBounds = ICoreWebView2ControllerVtbl.put_Bounds(this.webviewControllerlpVtbl);
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
							this.webviewControllerPutBounds,
							this.webviewController,
							this.bounds
					)
			);
		} else return HResult.S_OK.SINGLETON;
	}
	
	@SuppressWarnings("CallToPrintStackTrace")
	private int putBounds(final MemorySegment hWnd) {
		try {
			if ( !((boolean) this.windowGetClientRectHandle.invokeExact(hWnd)) ) {
				return E_UNEXPECTED.SINGLETON.value();
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			return E_UNEXPECTED.SINGLETON.value();
		}
		return put_Bounds.invoke(
				this.webviewControllerPutBounds,
				this.webviewController,
				this.bounds
		);
	}
	
	@Override
	public long apply(final MemorySegment hWnd, final int message, final long wParam, final long lParam) {
		switch (message) {
			case Constants.WindowMessages.DESTROY -> Windows.PostQuitMessage(0);
			case Constants.WindowMessages.SIZE -> {
				return this.webviewController != null ?
						       this.putBounds(hWnd)
						       :
						       Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case Constants.WindowMessages.WM_SETFOCUS -> {
				this.windowOnGainedFocusListener.onGainedFocus();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case Constants.WindowMessages.WM_KILLFOCUS -> {
				this.windowOnLostFocusListener.onLostFocus();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case Constants.WindowMessages.CLOSE -> {
				this.windowOnCloseListener.onClose();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case Constants.WindowMessages.QUIT -> {
				this.windowOnDestroyListener.onDestroy();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case Constants.WindowMessages.SYSCOMMAND -> {
				if ( wParam == Constants.SystemCommand.MAXIMIZE ) this.windowOnMaximizeListener.onMaximize();
				else if ( wParam == Constants.SystemCommand.MINIMIZE ) this.windowOnMinimizeListener.onMinimize();
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
			case TO_FRONT -> toFront(hWnd);
			case EXECUTE_SCRIPT -> {
				return this.webViewWindow.consumeScript().value();
			}
			default -> {
				return Windows.DefWindowProcW(hWnd, message, wParam, lParam);
			}
		}
		return EXIT_SUCCESS;
	}
	
	private static void toFront(final MemorySegment hWnd) {
		final var allowed = Windows.AllowSetForegroundWindow();
		assert allowed;
		final var setWindowPos = Windows.SetWindowPos(
				hWnd,
				SetWindowPosition.HWND_TOP,
				0, 0, 0, 0,
				SetWindowPosition.SWP_NOMOVE | SetWindowPosition.SWP_NOSIZE | SetWindowPosition.SWP_SHOWWINDOW
		);
		assert setWindowPos;
		final var setForegroundWindow = Windows.SetForegroundWindow(hWnd);
		assert setForegroundWindow;
		final var previous = Windows.SetFocus(hWnd);
		assert previous != MemorySegment.NULL;
	}
	
	public static class BuilderImpl implements Builder {
		private final Arena arena;
		private WindowOnCloseListener windowOnCloseListener;
		private WindowOnDestroyListener windowOnDestroyListener;
		private WindowOnMinimizeListener windowOnMinimizeListener;
		private WindowOnMaximizeListener windowOnMaximizeListener;
		private WindowOnGainedFocusListener windowOnGainedFocusListener;
		private WindowOnLostFocusListener windowOnLostFocusListener;
		
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
		public Builder setWindowOnMinimizeListener(final WindowOnMinimizeListener windowOnMinimizeListener) {
			this.windowOnMinimizeListener = windowOnMinimizeListener;
			return this;
		}
		
		@Override
		public Builder setWindowOnMaximizeListener(final WindowOnMaximizeListener windowOnMaximizeListener) {
			this.windowOnMaximizeListener = windowOnMaximizeListener;
			return this;
		}
		
		@Override
		public Builder setWindowOnGainedFocusListener(final WindowOnGainedFocusListener windowOnGainedFocusListener) {
			this.windowOnGainedFocusListener = windowOnGainedFocusListener;
			return this;
		}
		
		@Override
		public Builder setWindowOnLoseFocusListener(final WindowOnLostFocusListener windowOnLostFocusListener) {
			this.windowOnLostFocusListener = windowOnLostFocusListener;
			return this;
		}
		
		@Override
		public WebViewControllerImpl build() {
			enum EmptyListener implements WindowOnCloseListener,
					                                     WindowOnDestroyListener,
					                                     WindowOnMinimizeListener,
					                                     WindowOnMaximizeListener,
					                                     WindowOnGainedFocusListener,
					                                     WindowOnLostFocusListener {
				SINGLETON;
				@Override
				public void onClose() {
				
				}
				
				@Override
				public void onDestroy() {
				
				}
				
				@Override
				public void onGainedFocus() {
				
				}
				
				@Override
				public void onLostFocus() {
				
				}
				
				@Override
				public void onMaximize() {
				
				}
				
				@Override
				public void onMinimize() {
				
				}
			}
			return new WebViewControllerImpl(
					this.arena,
					Optional.ofNullable(this.windowOnCloseListener).orElse(EmptyListener.SINGLETON),
					Optional.ofNullable(this.windowOnDestroyListener).orElse(EmptyListener.SINGLETON),
					Optional.ofNullable(this.windowOnMinimizeListener).orElse(EmptyListener.SINGLETON),
					Optional.ofNullable(this.windowOnMaximizeListener).orElse(EmptyListener.SINGLETON),
					Optional.ofNullable(this.windowOnGainedFocusListener).orElse(EmptyListener.SINGLETON),
					Optional.ofNullable(this.windowOnLostFocusListener).orElse(EmptyListener.SINGLETON)
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

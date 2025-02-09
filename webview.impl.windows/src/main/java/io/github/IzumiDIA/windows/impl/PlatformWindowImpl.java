package io.github.IzumiDIA.windows.impl;

import io.github.IzumiDIA.PlatformWindow;
import org.jetbrains.annotations.NotNull;
import org.jextract.LayoutUtils;
import org.jextract.Windows;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

public class PlatformWindowImpl extends WindowsNativeObject implements PlatformWindow {
	private final MemorySegment hWnd;
	private final MethodHandle
			getClientRectHandle,
			defaultWindowProcHandle,
			postMessageHandle,
			updateWindowHandle,
			showWindowHandle,
			isWindowHandle,
			destroyWindowHandle,
			moveWindowHandle,
			setFocusHandle,
			setWindowTextHandle,
			getWindowLongPointerHandle;
	
	public PlatformWindowImpl(final Arena arena, final MemorySegment hWnd) {
		super(arena);
		this.hWnd = hWnd;
		this.getClientRectHandle = Windows.GetClientRect$handle().bindTo(this.hWnd);
		this.defaultWindowProcHandle = Windows.DefWindowProcW$handle().bindTo(this.hWnd);
		this.postMessageHandle = Windows.PostMessageW$handle().bindTo(this.hWnd);
		this.updateWindowHandle = Windows.UpdateWindow$handle().bindTo(this.hWnd);
		this.showWindowHandle = Windows.ShowWindow$handle().bindTo(this.hWnd);
		this.isWindowHandle = Windows.IsWindow$handle().bindTo(this.hWnd);
		this.destroyWindowHandle = Windows.DestroyWindow$handle().bindTo(this.hWnd);
		this.moveWindowHandle = Windows.MoveWindow$handle().bindTo(this.hWnd);
		this.setFocusHandle = Windows.SetFocus$handle().bindTo(this.hWnd);
		this.setWindowTextHandle = Windows.SetWindowTextW$handle().bindTo(this.hWnd);
		this.getWindowLongPointerHandle = Windows.GetWindowLongPtrW$handle().bindTo(this.hWnd);
	}
	
	public MemorySegment getHandler() {
		return this.hWnd;
	}
	
	@Override
	public boolean getClientRect(final @NotNull MemorySegment rect) {
		try {
			return (boolean) this.getClientRectHandle.invokeExact(rect);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	@Override
	public boolean postMessage(final int message, final long wParam, final long lParam) {
		try {
			return (boolean) this.postMessageHandle.invokeExact(message, 0L, 0L);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	@Override
	public boolean setFocus() {
		try {
			final var previousWindow = (MemorySegment) this.setFocusHandle.invokeExact();
			return !MemorySegment.NULL.equals(previousWindow);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	@Override
	public boolean setWindowTitle(@NotNull final String titleText) {
		try {
			return (boolean) this.setWindowTextHandle.invokeExact(
					this.arena.allocateFrom(
							LayoutUtils.LPCWSTR,
							this.allocateString(titleText)
					)
			);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	@Override
	public boolean moveWindow(final int x, final int y, final int newWidth, final int newHeight, final boolean repaint) {
		try {
			return (boolean) this.moveWindowHandle.invokeExact(x, y, newWidth, newHeight, repaint);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	public long defaultWindowProc(final int message, final long wParam, final long lParam) {
		try {
			return (long) this.defaultWindowProcHandle.invokeExact(message, wParam, lParam);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	public boolean showWindow(final int nCmdShow) {
		try {
			return (boolean) this.showWindowHandle.invokeExact(nCmdShow);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	public boolean updateWindow() {
		try {
			return (boolean) this.updateWindowHandle.invokeExact();
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	public MemorySegment getWindowLongPointer(final int nIndex) {
		try {
			return (MemorySegment) this.getWindowLongPointerHandle.invokeExact(nIndex);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	public boolean isWindow() {
		try {
			return (boolean) this.isWindowHandle.invokeExact();
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	public boolean destroyWindow() {
		try {
			return (boolean) this.destroyWindowHandle.invokeExact();
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
}

package io.github.IzumiDIA.windows.impl;

import io.github.IzumiDIA.PlatformWindow;
import org.jetbrains.annotations.NotNull;
import org.jextract.Windows;

import java.lang.foreign.MemorySegment;

public class PlatformWindowImpl implements PlatformWindow {
	private final MemorySegment hWnd;
	
	public PlatformWindowImpl(final MemorySegment hWnd) {
		this.hWnd = hWnd;
	}
	
	public MemorySegment getHandler() {
		return this.hWnd;
	}
	
	@Override
	public boolean getClientRect(final @NotNull MemorySegment rect) {
		return Windows.GetClientRect(this.hWnd, rect);
	}
	
	public long defaultWindowProc(final int message, final long wParam, final long lParam) {
		return Windows.DefWindowProcW(this.hWnd, message, wParam, lParam);
	}
	
	@Override
	public boolean postMessage(final int message, final long wParam, final long lParam) {
		return Windows.PostMessageW(this.hWnd, message, 0L, 0L);
	}
}

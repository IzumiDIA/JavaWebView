package io.github.IzumiDIA;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;

public interface PlatformWindow {
	MemorySegment getHandler();
	boolean getClientRect(final @NotNull MemorySegment rect);
	boolean postMessage(int message,
	                    long wParam,
	                    long lParam);
	boolean setFocus();
	boolean setWindowTitle(@NotNull String titleText);
	boolean moveWindow(int x, int y, int newWidth, int newHeight, boolean repaint);
}

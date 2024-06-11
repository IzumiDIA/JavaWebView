package io.github.IzumiDIA;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.util.EventListener;

public interface PlatformWindow {
	MemorySegment getHandler();
	boolean getClientRect(final @NotNull MemorySegment rect);
	boolean postMessage(int message,
	                    long wParam,
	                    long lParam);
}

package io.github.IzumiDIA;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.util.EventListener;

public interface PlatformWindow {
	MemorySegment getHandler();
	boolean getClientRect(final @NotNull MemorySegment rect);
	long defaultWindowProc(int message,
	                       long wParam,
	                       long lParam);
	boolean postMessageW(int message,
	                     long wParam,
	                     long lParam);
	@FunctionalInterface
	interface WindowProcedure<T extends Parameter> extends EventListener {
		long windowProcedure(final @NotNull PlatformWindow windowSelf, final int message, final T parameter);
	}
}

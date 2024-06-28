package io.github.IzumiDIA.controller;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.Result;
import io.github.IzumiDIA.listener.WindowOnCloseListener;
import io.github.IzumiDIA.listener.WindowOnDestroyListener;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.util.EventListener;

public interface WebViewController extends EventListener {
	
	void setController(final @NotNull MemorySegment controller);
	
	Result putBounds(final @NotNull PlatformWindow platformWindow);
	interface Builder {
		Builder setOnCloseListener(final WindowOnCloseListener windowOnCloseListener);
		Builder setOnDestroyListener(final WindowOnDestroyListener windowOnDestroyListener);
		WebViewController build();
	}
}

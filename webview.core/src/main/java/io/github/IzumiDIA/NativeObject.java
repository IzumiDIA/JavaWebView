package io.github.IzumiDIA;

import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;

public abstract class NativeObject {
	protected final Arena arena;
	
	protected NativeObject(final @NotNull Arena arena) {
		this.arena = arena;
	}
	
	protected abstract MemorySegment allocateString(final @NotNull String string);
}

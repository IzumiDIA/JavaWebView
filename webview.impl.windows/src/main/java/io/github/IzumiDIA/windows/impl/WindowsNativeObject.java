package io.github.IzumiDIA.windows.impl;

import io.github.IzumiDIA.NativeObject;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.charset.StandardCharsets;

public abstract class WindowsNativeObject extends NativeObject {
	protected WindowsNativeObject(final Arena arena) {
		super(arena);
	}
	@Override
	protected MemorySegment allocateString(final @NotNull String string) {
		return this.arena.allocateFrom(string, StandardCharsets.UTF_16LE);
	}
	
	@Override
	protected String getString(final @NotNull MemorySegment memorySegment) {
		return memorySegment.getString(0, StandardCharsets.UTF_16LE);
	}
}

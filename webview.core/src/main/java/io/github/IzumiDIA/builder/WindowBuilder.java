package io.github.IzumiDIA.builder;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.Point2D;
import io.github.IzumiDIA.constant.enums.Color;
import io.github.IzumiDIA.controller.WebViewController;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.foreign.MemorySegment;

public interface WindowBuilder<T extends WebViewController> {
	WindowBuilder<T> setStyle(final int style);
	
	WindowBuilder<T> setController(final @NotNull T webViewController);
	
	WindowBuilder<T> setHandleIcon(final @NotNull MemorySegment handleIcon);
	
	WindowBuilder<T> setHandleCursor(final @NotNull MemorySegment handleCursor);
	
	WindowBuilder<T> setLpSzClassName(final @NotNull String windowClassName);
	
	WindowBuilder<T> setHandleIconSmall(final @NotNull MemorySegment handleIconSmall);
	
	WindowBuilder<T> setExtendStyle(final int extendStyle);
	
	WindowBuilder<T> setWindowName(final @NotNull String windowName);
	
	WindowBuilder<T> setPosition(final @NotNull Point2D position);
	
	WindowBuilder<T> setDimension(final @NotNull Dimension dimension);
	
	default WindowBuilder<T> setDimension(final int width, final int height) {
		if ( width >= 0 && height >= 0 ) return this.setDimension(new Dimension(width, height));
		else throw new IllegalArgumentException("Width and height must both be non-negative.");
	}
	
	PlatformWindow buildWindow();
}

package io.github.IzumiDIA.builder;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.Point2D;
import io.github.IzumiDIA.controller.WebViewController;
import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;
import java.nio.file.Path;

public interface WindowBuilder<T extends WebViewController> {
	
	WindowBuilder<T> setResizable(final boolean resizable);
	
	WindowBuilder<T> setController(final @NotNull T webViewController);
	
	WindowBuilder<T> setHandleIcon(final @NotNull Path iconFilePath);
	
	WindowBuilder<T> setClassName(final @NotNull String windowClassName);
	
	WindowBuilder<T> setHandleIconSmall(final @NotNull Path smallIconFilePath);
	
	WindowBuilder<T> setWindowName(final @NotNull String windowName);
	
	WindowBuilder<T> setPosition(final @NotNull Point2D position);
	
	WindowBuilder<T> setDimension(final @NotNull Dimension dimension);
	
	default WindowBuilder<T> setDimension(final int width, final int height) {
		if ( width >= 0 && height >= 0 ) return this.setDimension(new Dimension(width, height));
		else throw new IllegalArgumentException("Width and height must both be non-negative.");
	}
	
	PlatformWindow buildWindow();
}

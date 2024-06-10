package io.github.IzumiDIA.factory;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.builder.WebViewBuilder;
import io.github.IzumiDIA.builder.WindowBuilder;
import io.github.IzumiDIA.controller.WebViewController;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;

public interface WebViewFactory<F extends WebViewController> {
	WebViewController.Builder createControllerBuilder(final @NotNull Arena arena);
	
	WindowBuilder<F> createPlatformWindowBuilder(final @NotNull Arena arena);
	
	WebViewBuilder createWebViewBuilder(final @NotNull Arena arena, final @NotNull PlatformWindow platformWindow);
}

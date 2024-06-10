package io.github.IzumiDIA.windows.factory.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.windows.builder.impl.WebViewBuilderImpl;
import io.github.IzumiDIA.windows.builder.impl.WindowBuilderImpl;
import io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl;
import io.github.IzumiDIA.factory.WebViewFactory;
import io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl.BuilderImpl;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.Arena;

public class WebViewFactoryImpl implements WebViewFactory<WebViewControllerImpl> {
	private WebViewControllerImpl webViewController = null;
	
	@Override
	public BuilderImpl createControllerBuilder(final @NotNull Arena arena) {
		return new BuilderImpl(arena) {
			@Override
			public WebViewControllerImpl build() {
				return WebViewFactoryImpl.this.webViewController = super.build();
			}
		};
	}
	
	@Override
	public WindowBuilderImpl createPlatformWindowBuilder(final @NotNull Arena arena) {
		return new WindowBuilderImpl(arena);
	}
	
	@Override
	public WebViewBuilderImpl createWebViewBuilder(final @NotNull Arena arena, final @NotNull PlatformWindow platformWindow) {
		return new WebViewBuilderImpl(
				this.webViewController,
				arena,
				platformWindow
		);
	}
}

package example;

import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.constant.enums.Color;

import java.lang.foreign.Arena;
import java.net.URI;
import java.nio.file.Path;

final class TestBinding {
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindow = webViewFactory.createPlatformWindowBuilder(arena)
					                           .setController(webViewFactory.createControllerBuilder(arena).build())
					                           .setBrushBackground(Color.ACTIVEBORDER)
					                           .setLpSzClassName("Sample Window Class")
					                           .setWindowName("Binding URI WebView Window")
					                           .setDimension((int) Math.floor(1920 * 0.75D), (int) Math.floor(1080 * 0.75D))
					                           .buildWindow();
			
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .enableScript(true)
					                          .enableContextMenu(true)
					                          .enableStatusBar(true)
					                          .setUserDataFolder(Path.of(""))
					                          .navigate(URI.create("https://docs.oracle.com/en/"))
					                          .buildWebView();
			
			webViewWindow.run();
		}
	}
}

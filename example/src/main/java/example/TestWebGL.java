package example;

import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.constant.enums.HostResourceAccessKind;

import java.lang.foreign.Arena;
import java.net.URI;
import java.nio.file.Path;

final class TestWebGL {
	
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindowBuilder = webViewFactory.createPlatformWindowBuilder(arena);
			
			final var platformWindow = platformWindowBuilder
					                           .setController(webViewFactory.createControllerBuilder(arena).build())
					                           .setHandleIcon(Path.of("assets", "Icon.ico").toAbsolutePath())
					                           .setWindowName("WebGL2 WebView Window")
					                           .setDimension(1920 >>> 1, (int) Math.floor(1080 * 0.75D))
					                           .setResizable(true)
					                           .buildWindow();
			
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .enableScript(true)
					                          .enableDevTools(true)
					                          .setUserDataFolder(Path.of(""))
					                          .setVirtualHostNameToFolderMapping(
							                          "izumi",
							                          Path.of("assets").toAbsolutePath(),
							                          HostResourceAccessKind.ALLOW
					                          )
					                          .navigate(URI.create("https://izumi/Sakura.html"))
					                          .buildWebView();
			
			webViewWindow.run();
		}
	}
}

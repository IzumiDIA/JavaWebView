package example;

import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.constant.enums.COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND;

import java.lang.foreign.Arena;
import java.net.URI;
import java.nio.file.Path;

final class TestWebGPU {
	
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindowBuilder = webViewFactory.createPlatformWindowBuilder(arena);
			
			final var platformWindow = platformWindowBuilder
					                           .setController(webViewFactory.createControllerBuilder(arena).build())
					                           .setHandleIcon(Path.of("assets", "Icon.ico").toAbsolutePath())
					                           .setWindowName("WebGPU WebView Window")
					                           .setDimension(1920 >>> 1, (int) Math.floor(1080 * 0.75D))
					                           .buildWindow();
			
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .enableScript(true)
					                          .enableDevTools(true)
					                          .setUserDataFolder(Path.of(""))
					                          .setVirtualHostNameToFolderMapping(
													  "izumi",
							                          Path.of("assets").toAbsolutePath(),
							                          COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND.ALLOW
					                          )
					                          .navigate(URI.create("https://izumi/TestWebGPU.html"))
					                          .buildWebView();
			
			webViewWindow.run();
		}
	}
}

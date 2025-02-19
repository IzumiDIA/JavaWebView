package example;

import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.constant.enums.HostResourceAccessKind;

import java.lang.foreign.Arena;
import java.nio.file.Path;

final class TestVirtualHostNameToFolderMapping {
	@SuppressWarnings("InconsistentTextBlockIndent")
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindowBuilder = webViewFactory.createPlatformWindowBuilder(arena);
			
			final var platformWindow = platformWindowBuilder
					                           .setController(webViewFactory.createControllerBuilder(arena).build())
					                           .setHandleIcon(Path.of("assets", "Icon.ico").toAbsolutePath())
					                           .setClassName("Sample Window Class")
					                           .setWindowName("Virtual Host Name Mapping To Folder WebView Window")
					                           .setDimension(1920 >>> 1, 1080 >>> 1)
					                           .buildWindow();
			
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .setVirtualHostNameToFolderMapping(
							                          "izumi-ryuu",
							                          Path.of("assets").toAbsolutePath(),
							                          HostResourceAccessKind.ALLOW
					                          )
					                          .enableDevTools(true)
					                          .setUserDataFolder(Path.of(""))
					                          .navigate(
							                          """
													  <head><title>Java WebView</title></head>
													  <body>
													  <h1>This is a Java WebView!</h1>
													  <p>Thanks for using webview!</p>
													  <img src="https://izumi-ryuu/俺の图图呢？.webp" title="俺の图图呢？" alt="Not Found." style="inline-size: 18rem; object-fit: cover;">
													  </body>"""
					                          )
					                          .buildWebView();
			
			webViewWindow.run();
		}
	}
}

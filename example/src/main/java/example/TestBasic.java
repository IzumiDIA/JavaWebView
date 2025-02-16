package example;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.Result;
import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.WebViewWindow.WebMessageListener.EventExchange;
import io.github.IzumiDIA.controller.WebViewController;
import io.github.IzumiDIA.factory.WebViewFactory;

import java.lang.foreign.Arena;
import java.nio.file.Path;

final class TestBasic {
	
	@SuppressWarnings("InconsistentTextBlockIndent")
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindow = createPlatformWindow(webViewFactory, arena);
			
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .enabledZoomControl(true)
					                          .enableDevTools(true)
					                          .setUserDataFolder(Path.of(""))
					                          .navigate(
							                          """
							                          <head><title>Java WebView</title></head>
							                          <body style="align-content: center; text-align: center;">
							                          <h1>This is a Java WebView!</h1>
							                          <p>Thanks for using webview!</p>
							                          <p>这是由 100% Java 语言编写的 WebView 程序。</p>
							                          </body>"""
					                          )
					                          .buildWebView();
			
			webViewWindow.run();
		}
	}
	
	private static PlatformWindow createPlatformWindow(final WebViewFactory<WebViewController, Result, EventExchange> webViewFactory, final Arena arena) {
		return webViewFactory.createPlatformWindowBuilder(arena)
				       .setController(webViewFactory.createControllerBuilder(arena).build())
				       .setHandleIcon(Path.of("assets", "Icon.ico").toAbsolutePath())
				       .setWindowName("Basic WebView Window")
				       .setDimension(1920 >>> 1, 1080 >>> 1)
				       .setHandleIconSmall(Path.of("assets", "SmallIcon.ico").toAbsolutePath())
				       .buildWindow();
	}
}

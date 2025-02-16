package example;

import io.github.IzumiDIA.WebViewWindow;

import java.lang.foreign.Arena;
import java.nio.file.Path;

final class TestPostMessageToBackend {
	@SuppressWarnings("InconsistentTextBlockIndent")
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindow = webViewFactory.createPlatformWindowBuilder(arena)
					                           .setController(webViewFactory.createControllerBuilder(arena).build())
					                           .setHandleIcon(Path.of("assets", "Icon.ico").toAbsolutePath())
					                           .setClassName("Sample Window Class")
					                           .setWindowName("WebMessage Posting WebView Window")
					                           .setDimension(1920 >>> 1, 1080 >>> 1)
					                           .buildWindow();
			
			@SuppressWarnings("JSUnresolvedReference")
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .enableScript(true)
					                          .enableWebMessage(true)
					                          .setWebMessageListener(
							                          eventExchange -> {
								                          final var webMessage = eventExchange.tryGetWebMessageAsString();
								                          final var hResult = eventExchange.getResult();
								                          if ( hResult.isOK() ) {
									                          System.out.println(webMessage);
									                          return eventExchange.OK();
								                          } else return hResult;
							                          }
					                          )
					                          .setUserDataFolder(Path.of(""))
					                          .navigate(
							                          // language="HTML"
							                          """
							                          <head><title>Java WebView</title></head>
							                          <body>
							                          <h1>This is a Java WebView!</h1>
							                          <p>Thanks for using webview!</p>
							                          <form onsubmit="return (() => {window.chrome.webview.postMessage(webMessageInput.value); return false;})();">
							                          <label>
							                          <input type="text" id="webMessageInput">
							                          <button type="submit">向Java后端发送消息</button>
							                          <button type="reset">重置输入框</button>
							                          </label>
							                          </form>
							                          </body>"""
					                          )
					                          .buildWebView();
			
			webViewWindow.run();
		}
	}
}

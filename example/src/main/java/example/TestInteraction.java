package example;

import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.constant.enums.Color;

import java.lang.foreign.Arena;
import java.nio.file.Path;

final class TestInteraction {
	@SuppressWarnings({"InconsistentTextBlockIndent", "preview"})
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindow = webViewFactory.createPlatformWindowBuilder(arena)
					                           .setController(webViewFactory.createControllerBuilder(arena).build())
					                           .setBrushBackground(Color.ACTIVEBORDER)
					                           .setLpSzClassName("Sample Window Class")
					                           .setWindowName("Full Duplex Communication WebView Window")
					                           .setDimension(1920 >>> 1, 1080 >>> 1)
					                           .buildWindow();
			
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .enableScript(true)
					                          .enableWebMessage(true)
					                          .setWebMessageListener(
							                          eventExchange -> {
								                          final var stringFromWeb = eventExchange.tryGetWebMessageAsString();
								                          System.out.println(stringFromWeb);
								                          return eventExchange.postWebMessageAsString(
										                          STR."后端进程已接收到数据：\{stringFromWeb}"
								                          );
							                          }
					                          )
					                          .setUserDataFolder(Path.of(""))
					                          .navigate(
							                          """
							                          <head>
							                          <title>Java WebView</title>
							                          <script type="module">
							                          window.chrome.webview.addEventListener("message", event => {
							                           document.querySelector("body").insertAdjacentHTML(
							                            "beforeend",
							                            `<p><em>${event.data}</em></p>`
							                           )
							                          });
							                          </script>
							                          </head>
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

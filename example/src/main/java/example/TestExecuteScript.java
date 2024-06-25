package example;

import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.constant.enums.COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND;

import java.lang.foreign.Arena;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

final class TestExecuteScript {
	
	@SuppressWarnings({"InconsistentTextBlockIndent", "LanguageMismatch"})
	void main() {
		try (var arena = Arena.ofConfined()) {
			
			final var webViewFactory = WebViewWindow.createFactory();
			
			final var platformWindow = webViewFactory.createPlatformWindowBuilder(arena)
					                           .setController(webViewFactory.createControllerBuilder(arena).build())
					                           .setLpSzClassName("Sample Window Class")
					                           .setWindowName("Script Executed WebView Window")
					                           .setDimension(1920 >>> 1, 1080 >>> 1)
					                           .buildWindow();
			
			final var webViewWindow = webViewFactory.createWebViewBuilder(arena, platformWindow)
					                          .enableScript(true)
					                          .enableDevTools(true)
					                          .enabledDefaultScriptDialogs(true)
					                          .setVirtualHostNameToFolderMapping(
							                          "izumi-ryuu",
							                          Path.of("..", ".."),
							                          COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND.ALLOW
					                          )
					                          .setUserDataFolder(Path.of(""))
					                          .navigate(
							                          """
							                          <head><title>Java WebView</title></head>
							                          <body>
							                          <h1>This is a Java WebView!</h1>
							                          <p>Thanks for using webview!</p>
							                          <img src="https://izumi-ryuu/俺の图图呢？.jpg" title="俺の图图呢？" alt="Not Found." style="inline-size: 18rem; object-fit: cover;">
							                          </body>"""
					                          )
					                          .buildWebView();
			Thread.ofPlatform()
					.daemon(true)
					.start(
							() -> {
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e) {
									throw new RuntimeException(e);
								}
								webViewWindow.executeScriptAsync(
										"""
										const timeString = new Date().toISOString();
										document.querySelector("body").insertAdjacentHTML(
										 "beforeend",
										 // language="HTML"
										 `<p>执行脚本的日期时间<time datetime="${timeString}">${timeString}</time></p>`
										);
										"""
								);
								System.out.println(
										webViewWindow.executeScriptAsync(
												"""
												document.querySelector("body").style.backgroundColor = "blue";
												"""
										)
								);
								try (var scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
									String line;
									while ( (line = scanner.nextLine()) != null ) {
										webViewWindow.executeScriptAsync(line);
									}
								}
							}
					);
			webViewWindow.run();
		}
	}
}

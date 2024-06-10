package io.github.IzumiDIA;

import io.github.IzumiDIA.controller.WebViewController;
import io.github.IzumiDIA.factory.WebViewFactory;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;
import java.util.EventListener;
import java.util.ServiceLoader;

public interface WebViewWindow {
	void run();
	
	int executeScript(final @Language("JavaScript") @NotNull String javascript);
	
	boolean executeScriptAsync(final @Language("JavaScript") @NotNull String javascript);
	
	int consumeScript();
	
	@FunctionalInterface
	interface WebMessageListener extends EventListener {
		
		int onWebMessageReceived(final @NotNull EventExchange eventExchange);
		
		interface EventExchange {
			int tryGetWebMessageAsString(final @NotNull MemorySegment bufferAddress);
			
			String tryGetWebMessageAsString();
			
			int postWebMessageAsString(final @NotNull MemorySegment messageToWebView);
			
			int postWebMessageAsString(final @NotNull String messageToWebView);
			
			MemorySegment createBuffer();
			
			default String getString(final @NotNull MemorySegment bufferAddress) {
				return bufferAddress.get(ValueLayout.ADDRESS, 0).reinterpret(Integer.MAX_VALUE).getString(0, StandardCharsets.UTF_16LE);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	static WebViewFactory<WebViewController> createFactory() {
		return (WebViewFactory<WebViewController>) ServiceLoader.load(WebViewFactory.class)
				                                                               .findFirst()
				                                                               .orElseThrow();
	}
}

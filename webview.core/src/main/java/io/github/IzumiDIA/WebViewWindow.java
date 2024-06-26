package io.github.IzumiDIA;

import io.github.IzumiDIA.WebViewWindow.WebMessageListener.EventExchange;
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
	
	boolean terminate();
	
	@FunctionalInterface
	interface WebMessageListener<R extends Result, T extends EventExchange<R>> extends EventListener {
		
		R onWebMessageReceived(final @NotNull T eventExchange);
		
		interface EventExchange<R extends Result> {
			R tryGetWebMessageAsString(final @NotNull MemorySegment bufferAddress);
			
			String tryGetWebMessageAsString();
			
			R postWebMessageAsString(final @NotNull MemorySegment messageToWebView);
			
			R postWebMessageAsString(final @NotNull String messageToWebView);
			
			MemorySegment createBuffer();
			
			Result.OK OK();
			
			Result.ABORT abort();
			
			Result.FAIL fail();
			
			Result.UNEXPECTED unexpected();
			
			default String getString(final @NotNull MemorySegment bufferAddress) {
				return bufferAddress.get(ValueLayout.ADDRESS, 0).reinterpret(Integer.MAX_VALUE).getString(0, StandardCharsets.UTF_16LE);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	static WebViewFactory<WebViewController, Result, EventExchange<Result>> createFactory() {
		return (WebViewFactory<WebViewController, Result, EventExchange<Result>>)
				       ServiceLoader.load(WebViewFactory.class)
						       .findFirst()
						       .orElseThrow();
	}
}

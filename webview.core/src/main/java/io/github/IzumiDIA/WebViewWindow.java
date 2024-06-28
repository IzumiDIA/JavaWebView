package io.github.IzumiDIA;

import io.github.IzumiDIA.WebViewWindow.WebMessageListener.EventExchange;
import io.github.IzumiDIA.controller.WebViewController;
import io.github.IzumiDIA.factory.WebViewFactory;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.lang.foreign.MemorySegment;
import java.util.EventListener;
import java.util.ServiceLoader;

public interface WebViewWindow {
	void run();
	
	Result executeScript(final @Language("JavaScript") @NotNull String javascript);
	
	boolean executeScriptAsync(final @Language("JavaScript") @NotNull String javascript);
	
	Result postWebMessageAsString(final @NotNull String messageToWebView);
	
	Result consumeScript();
	
	boolean terminate();
	
	@FunctionalInterface
	interface WebMessageListener<R extends Result, T extends EventExchange> extends EventListener {
		
		R onWebMessageReceived(final @NotNull T eventExchange);
		
		interface EventExchange {
			String tryGetWebMessageAsString();
			
			Result tryGetWebMessageAsStringToBuffer(final @NotNull MemorySegment bufferAddress);
			
			Result postWebMessageAsString(final @NotNull MemorySegment messageToWebView);
			
			Result postWebMessageAsString(final @NotNull String messageToWebView);
			
			Result getResult();
			
			Result.OK OK();
			
			Result.ABORT abort();
			
			Result.FAIL fail();
			
			Result.UNEXPECTED unexpected();
			
			String getString(final @NotNull MemorySegment bufferAddress);
		}
	}
	
	@SuppressWarnings("unchecked")
	static WebViewFactory<WebViewController, Result, EventExchange> createFactory() {
		return (WebViewFactory<WebViewController, Result, EventExchange>)
				       ServiceLoader.load(WebViewFactory.class)
						       .findFirst()
						       .orElseThrow();
	}
}

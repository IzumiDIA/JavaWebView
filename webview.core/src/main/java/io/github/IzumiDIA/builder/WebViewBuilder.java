package io.github.IzumiDIA.builder;

import io.github.IzumiDIA.Result;
import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.WebViewWindow.WebMessageListener;
import io.github.IzumiDIA.WebViewWindow.WebMessageListener.EventExchange;
import io.github.IzumiDIA.constant.enums.COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.nio.file.Path;

public interface WebViewBuilder<R extends Result, T extends EventExchange> {
	WebViewBuilder<R, T> setUserDataFolder(final Path userDataFolderPath);
	WebViewBuilder<R, T> enableScript(boolean enabled);
	WebViewBuilder<R, T> enableWebMessage(boolean enabled);
	WebViewBuilder<R, T> enableDevTools(boolean enabled);
	WebViewBuilder<R, T> enableContextMenu(boolean enabled);
	WebViewBuilder<R, T> enabledZoomControl(boolean enabled);
	WebViewBuilder<R, T> enableStatusBar(boolean enabled);
	WebViewBuilder<R, T> enabledDefaultScriptDialogs(boolean enabled);
	WebViewBuilder<R, T> setVirtualHostNameToFolderMapping(final @NotNull String virtualHostName, final Path folderMapping, final @NotNull COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND accessKind);
	WebViewBuilder<R, T> setWebMessageListener(final WebMessageListener<R, T> webMessageListener);
	WebViewBuilder<R, T> navigate(final @Language("HTML") @NotNull String content);
	WebViewBuilder<R, T> navigate(final @NotNull URI uri);
	WebViewWindow buildWebView();
}

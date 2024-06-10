package io.github.IzumiDIA.builder;

import io.github.IzumiDIA.WebViewWindow;
import io.github.IzumiDIA.WebViewWindow.WebMessageListener;
import io.github.IzumiDIA.constant.enums.COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.nio.file.Path;

public interface WebViewBuilder {
	WebViewBuilder setUserDataFolder(final Path userDataFolderPath);
	WebViewBuilder enableScript(boolean enabled);
	WebViewBuilder enableWebMessage(boolean enabled);
	WebViewBuilder enableDevTools(boolean enabled);
	WebViewBuilder enableContextMenu(boolean enabled);
	WebViewBuilder enabledZoomControl(boolean enabled);
	WebViewBuilder enableStatusBar(boolean enabled);
	WebViewBuilder setVirtualHostNameToFolderMapping(final @NotNull String virtualHostName, final Path folderMapping, final @NotNull COREWEBVIEW2_HOST_RESOURCE_ACCESS_KIND accessKind);
	WebViewBuilder setWebMessageListener(final WebMessageListener webMessageListener);
	WebViewBuilder navigate(final @Language("HTML") @NotNull String content);
	WebViewBuilder navigate(final @NotNull URI uri);
	WebViewWindow buildWebView();
}

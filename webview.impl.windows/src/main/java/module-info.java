import io.github.IzumiDIA.windows.factory.impl.WebViewFactoryImpl;

@SuppressWarnings("UnnecessaryFullyQualifiedName")
module webview.impl.windows {
	requires static org.jetbrains.annotations;
	requires transitive webview.core;
	
	provides io.github.IzumiDIA.factory.WebViewFactory with WebViewFactoryImpl;
}

import io.github.IzumiDIA.factory.WebViewFactory;
import io.github.IzumiDIA.windows.factory.impl.WebViewFactoryImpl;

module webview.impl.windows {
	requires static org.jetbrains.annotations;
	requires transitive webview.core;
	
	provides WebViewFactory with WebViewFactoryImpl;
}

@SuppressWarnings("UnnecessaryFullyQualifiedName")
module webview.core {
	uses io.github.IzumiDIA.factory.WebViewFactory;
	
	exports io.github.IzumiDIA;
	exports io.github.IzumiDIA.builder;
	exports io.github.IzumiDIA.constant.enums;
	exports io.github.IzumiDIA.factory;
	exports io.github.IzumiDIA.controller;
	exports io.github.IzumiDIA.listener;
	
	requires transitive java.desktop;
	requires static transitive org.jetbrains.annotations;
}

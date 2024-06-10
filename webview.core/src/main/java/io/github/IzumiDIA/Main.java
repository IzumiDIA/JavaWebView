package io.github.IzumiDIA;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

final class Main {
	/*
	GetModuleHandleA
	LoadImageA
	GetWindowLongPtrW
	SetWindowLongPtrW
	RegisterClassExW
	CreateWindowExW
	RegisterClassExW
	CreateWindowExW
	RegisterClassExW
	CreateWindowExW
	ShowWindow
	UpdateWindow
	GetModuleFileNameW
	PathFindFileNameW
	SHGetFolderPathW
	PathCombineW
	GetMessageW
	TranslateMessage
	DispatchMessageW
	DestroyWindow
	 */
	public static void main(final String... arguments) throws Throwable {
//		try (var arena = Arena.ofConfined()) {
//			final var webView2Loader = SymbolLookup.libraryLookup("WebView2Loader", arena);
//			final var kernel32 = SymbolLookup.libraryLookup("KERNEL32", arena);
//			final var user32 = SymbolLookup.libraryLookup("USER32", arena);
//			final var ole32 = SymbolLookup.libraryLookup("ole32", arena);
//			System.out.println(STR."[SymbolLookup] WebView2Loader: \{webView2Loader}");
//			System.out.println(STR."[SymbolLookup] KERNEL32: \{kernel32}");
//			System.out.println(STR."[SymbolLookup] USER32: \{user32}");
//			System.out.println(STR."[SymbolLookup] ole32r: \{ole32}");
//			System.out.println(user32.find("RegisterClassExW").isPresent());
//		}

		final var linker = Linker.nativeLinker();
		try (var arena = Arena.ofConfined()) {
			final var webviewLibraryLookup = SymbolLookup.libraryLookup("libwebview", arena);
			final MemorySegment webviewBindSegment = webviewLibraryLookup.find("webview_bind").orElseThrow(), webviewCreateSegment = webviewLibraryLookup.find("webview_create").orElseThrow(), webviewDestroySegment = webviewLibraryLookup.find("webview_destroy").orElseThrow(), webviewDispatchSegment = webviewLibraryLookup.find("webview_dispatch").orElseThrow(), webviewEvalSegment = webviewLibraryLookup.find("webview_eval").orElseThrow(), webviewGetNativeHandleSegment = webviewLibraryLookup.find("webview_get_native_handle").orElseThrow(), webviewGetWindowSegment = webviewLibraryLookup.find("webview_get_window").orElseThrow(), webviewInitSegment = webviewLibraryLookup.find("webview_init").orElseThrow(), webviewNavigateSegment = webviewLibraryLookup.find("webview_navigate").orElseThrow(), webviewReturnSegment = webviewLibraryLookup.find("webview_return").orElseThrow(), webviewRunSegment = webviewLibraryLookup.find("webview_run").orElseThrow(), webviewSetHtmlSegment = webviewLibraryLookup.find("webview_set_html").orElseThrow(), webviewSetSizeSegment = webviewLibraryLookup.find("webview_set_size").orElseThrow(), webviewSetTitleSegment = webviewLibraryLookup.find("webview_set_title").orElseThrow(), webviewTerminateSegment = webviewLibraryLookup.find("webview_terminate").orElseThrow(), webviewUnbindSegment = webviewLibraryLookup.find("webview_unbind").orElseThrow(), webviewVersionSegment = webviewLibraryLookup.find("webview_version").orElseThrow();
			final var kernel32 = SymbolLookup.libraryLookup("KERNEL32", arena);
			final var user32 = SymbolLookup.libraryLookup("USER32", arena);
			final var getModuleHandleASegment = kernel32.find("GetModuleHandleA").orElseThrow();
			final var getModuleHandleA = linker.downcallHandle(
					getModuleHandleASegment,
					FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS)
			);
			final var loadImageA = linker.downcallHandle(
					user32.find("LoadImageA").orElseThrow(),
					FunctionDescriptor.of(
							ValueLayout.ADDRESS,
							ValueLayout.ADDRESS,
							ValueLayout.ADDRESS,
							ValueLayout.JAVA_INT,
							ValueLayout.JAVA_INT,
							ValueLayout.JAVA_INT,
							ValueLayout.JAVA_INT
					)
			);
			final var registerClassExW = linker.downcallHandle(
					user32.find("RegisterClassExW").orElseThrow(),
					FunctionDescriptor.ofVoid()
			);
			final var createWindowExW = linker.downcallHandle(
					user32.find("CreateWindowExW").orElseThrow(),
					FunctionDescriptor.ofVoid()
			);
			System.out.println((MemorySegment) getModuleHandleA.invokeExact(MemorySegment.NULL));
			System.out.println(webviewBindSegment);
			System.out.println(webviewCreateSegment);
			System.out.println(webviewDestroySegment);
			System.out.println(webviewDispatchSegment);
			System.out.println(webviewEvalSegment);
			System.out.println(webviewGetNativeHandleSegment);
			System.out.println(webviewGetWindowSegment);
			System.out.println(webviewInitSegment);
			System.out.println(webviewNavigateSegment);
			System.out.println(webviewReturnSegment);
			System.out.println(webviewRunSegment);
			System.out.println(webviewSetHtmlSegment);
			System.out.println(webviewSetSizeSegment);
			System.out.println(webviewSetTitleSegment);
			System.out.println(webviewTerminateSegment);
			System.out.println(webviewUnbindSegment);
			System.out.println(webviewVersionSegment);
			final var webviewCreate = linker.downcallHandle(
					webviewCreateSegment,
					FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
			);
			final var webview = (MemorySegment) webviewCreate.invokeExact(0, MemorySegment.NULL);
			final var webviewSetTitle = linker.downcallHandle(
					webviewSetTitleSegment,
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)
			).bindTo(webview);
			final var webviewSetSize = linker.downcallHandle(
					webviewSetSizeSegment,
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)
			).bindTo(webview);
			final var webviewSetHtml = linker.downcallHandle(
					webviewSetHtmlSegment,
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)
			).bindTo(webview);
			final var webviewRun = linker.downcallHandle(
					webviewRunSegment,
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
			).bindTo(webview);
			final var webviewDestroy = linker.downcallHandle(
					webviewDestroySegment,
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
			).bindTo(webview);
			var _ = (int) webviewSetTitle.invokeExact(arena.allocateFrom("Java WebView Example"));
			var _ = (int) webviewSetSize.invokeExact(960, 540, 0);
			var _ = (int) webviewSetHtml.invokeExact(arena.allocateFrom("<h1>“Thanks for using webview!” —— from Java</h1>"));
			var _ = (int) webviewRun.invokeExact();
			var _ = (int) webviewDestroy.invokeExact();
		}
	}
	
	private static final class WebView {
		private static final MethodHandle WEBVIEW_CREATE, WEBVIEW_SET_TITLE, WEBVIEW_SET_SIZE, WEBVIEW_SET_HTML, WEBVIEW_RUN, WEBVIEW_DESTROY;
		
		
		static {
			final var linker = Linker.nativeLinker();
			final var arena = Arena.ofAuto();
			//noinspection SpellCheckingInspection
			final var webviewLibraryLookup = SymbolLookup.libraryLookup("libwebview", arena);
			WEBVIEW_CREATE = linker.downcallHandle(
					webviewLibraryLookup.find("webview_create").orElseThrow(),
					FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
			);
			WEBVIEW_SET_TITLE = linker.downcallHandle(
					webviewLibraryLookup.find("webview_set_title").orElseThrow(),
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)
			);
			WEBVIEW_SET_SIZE = linker.downcallHandle(
					webviewLibraryLookup.find("webview_set_size").orElseThrow(),
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT)
			);
			WEBVIEW_SET_HTML = linker.downcallHandle(
					webviewLibraryLookup.find("webview_set_html").orElseThrow(),
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS)
			);
			WEBVIEW_RUN = linker.downcallHandle(
					webviewLibraryLookup.find("webview_run").orElseThrow(),
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
			);
			WEBVIEW_DESTROY = linker.downcallHandle(
					webviewLibraryLookup.find("webview_destroy").orElseThrow(),
					FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS)
			);
		}
		
		private final MethodHandle webviewSetTitle, webviewSetSize, webviewSetHtml, webviewRun, webviewDestroy;
		
		public WebView() throws Throwable {
			final var webview = (MemorySegment) WEBVIEW_CREATE.invokeExact(0, MemorySegment.NULL);
			webviewSetTitle = WEBVIEW_SET_TITLE.bindTo(webview);
			webviewSetSize = WEBVIEW_SET_SIZE.bindTo(webview);
			webviewSetHtml = WEBVIEW_SET_HTML.bindTo(webview);
			webviewRun = WEBVIEW_RUN.bindTo(webview);
			webviewDestroy = WEBVIEW_DESTROY.bindTo(webview);
		}
	}
}

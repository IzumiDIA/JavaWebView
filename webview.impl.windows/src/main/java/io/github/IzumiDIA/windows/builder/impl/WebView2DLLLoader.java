package io.github.IzumiDIA.windows.builder.impl;

import io.github.IzumiDIA.windows.impl.HResult;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jextract.HKEY__;
import org.jextract.ICoreWebView2CreateCoreWebView2ControllerCompletedHandler;
import org.jextract.LayoutUtils;
import org.jextract.Windows;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.function.Supplier;

class WebView2DLLLoader extends WindowsNativeObject implements AutoCloseable {
	@SuppressWarnings("SpellCheckingInspection")
	private static final int HKEY_LOCAL_MACHINE = 0x80000002;
	private static final int KEY_READ = ((((0x00020000)) | (0x0001) | (0x0008) | (0x0010)) & (~(0x00100000)));
	private static final int KEY_WOW64_32KEY = (0x0200);
	private final MethodHandle createWebViewEnvironmentWithOptionsInternal;
	private final Arena confinedArena = Arena.ofConfined();
	
	@SuppressWarnings({"HardcodedFileSeparator"})
	WebView2DLLLoader(final Arena arena) {
		super(arena);
		final var embeddedBrowserWebViewSymbolLookup = SymbolLookup.libraryLookup(
				this.queryRegValue() + "\\EBWebView\\x64\\EmbeddedBrowserWebView",
				this.confinedArena
		);
		this.createWebViewEnvironmentWithOptionsInternal =
				Linker.nativeLinker()
						.downcallHandle(
								embeddedBrowserWebViewSymbolLookup.find("CreateWebViewEnvironmentWithOptionsInternal").orElseThrow(),
								FunctionDescriptor.of(
										ValueLayout.JAVA_INT,
										ValueLayout.JAVA_BOOLEAN,
										ValueLayout.JAVA_INT,
										LayoutUtils.PCWSTR,
										ValueLayout.ADDRESS,
										ValueLayout.ADDRESS.withTargetLayout(ICoreWebView2CreateCoreWebView2ControllerCompletedHandler.layout())
								)
						);
	}
	
	HResult createWebViewEnvironmentWithOptionsInternal(
			final int runtimeType,
			final MemorySegment userDataDir,
			final MemorySegment environmentOptions,
			final MemorySegment coreWebView2EnvironmentCompletedHandler
	) {
		try {
			return HResult.warpResult(
					(int) this.createWebViewEnvironmentWithOptionsInternal.invokeExact(
							true,
							runtimeType,
							userDataDir,
							environmentOptions,
							coreWebView2EnvironmentCompletedHandler
					)
			);
		} catch (Throwable ex$) {
			throw new AssertionError("should not reach here", ex$);
		}
	}
	
	@Override
	public void close() {
		this.confinedArena.close();
	}
	
	private String queryRegValue() {
		try (var regValueQuerier = new RegValueQuerier()) {
			return regValueQuerier.get();
		}
	}
	
	@SuppressWarnings({"NegativeIntConstantInLongContext", "SpellCheckingInspection"})
	private final class RegValueQuerier implements Supplier<String>, AutoCloseable {
		private static final AddressLayout layout = ValueLayout.ADDRESS.withTargetLayout(HKEY__.layout());
		private final MemorySegment phkResultPointer;
		
		private RegValueQuerier() {
			this.phkResultPointer = WebView2DLLLoader.this.confinedArena.allocateFrom(
					layout,
					HKEY__.allocate(WebView2DLLLoader.this.confinedArena)
			);
			final var LSTATUS = Windows.RegOpenKeyExW(
					MemorySegment.ofAddress(HKEY_LOCAL_MACHINE),
					WebView2DLLLoader.this.allocateString("Software\\Microsoft\\EdgeUpdate\\ClientState\\{F3017226-FE2A-4295-8BDF-00C3A9A7E4C5}"),
					0,
					KEY_READ | KEY_WOW64_32KEY,
					this.phkResultPointer
			);
			if ( LSTATUS != 0 ) {
				throw new IllegalStateException("Unable to RegOpenKeyExW. LSTATUS: " + LSTATUS);
			}
		}
		
		@Override
		public String get() {
			final var path = WebView2DLLLoader.this.confinedArena.allocate(256);
			final var cbPath = WebView2DLLLoader.this.confinedArena.allocateFrom(LayoutUtils.DWORD, 256);
			final var cbPathPointer = WebView2DLLLoader.this.confinedArena.allocateFrom(ValueLayout.ADDRESS.withTargetLayout(LayoutUtils.DWORD), cbPath);
			final var LSTATUS = Windows.RegQueryValueExW(
					this.phkResultPointer.get(layout, 0),
					WebView2DLLLoader.this.allocateString("EBWebView"),
					MemorySegment.NULL,
					MemorySegment.NULL,
					path,
					cbPathPointer
			);
			if ( LSTATUS == 0 ) return WebView2DLLLoader.this.getString(path);
			else throw new IllegalStateException("Unable to RegQueryValueExW. LSTATUS: " + LSTATUS);
		}
		
		@Override
		public void close() {
			final var LSTATUS = Windows.RegCloseKey(this.phkResultPointer.get(layout, 0));
			if ( LSTATUS != 0 ) {
				throw new IllegalStateException("Unable to RegCloseKey. LSTATUS: " + LSTATUS);
			}
		}
	}
}

package org.jextract;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class FFMUtils {
	
	private FFMUtils() {
		// Should not be called directly
	}
	static final boolean TRACE_DOWNCALLS = Boolean.getBoolean("jextract.trace.downcalls");
	
	static final Arena LIBRARY_ARENA = Arena.ofAuto();
	
	@SuppressWarnings("SpellCheckingInspection")
	static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.libraryLookup(System.mapLibraryName("USER32"), LIBRARY_ARENA)
			                .or(SymbolLookup.libraryLookup(System.mapLibraryName("ADVAPI32"), LIBRARY_ARENA))
			                .or(SymbolLookup.libraryLookup(System.mapLibraryName("ole32"), LIBRARY_ARENA))
			                .or(SymbolLookup.libraryLookup(System.mapLibraryName("SHELL32"), LIBRARY_ARENA))
			                .or(SymbolLookup.libraryLookup(System.mapLibraryName("SHLWAPI"), LIBRARY_ARENA))
			                .or(SymbolLookup.libraryLookup(System.mapLibraryName("VERSION"), LIBRARY_ARENA))
			                .or(SymbolLookup.libraryLookup(System.mapLibraryName("KERNEL32"), LIBRARY_ARENA))
			                .or(SymbolLookup.loaderLookup())
			                .or(Linker.nativeLinker().defaultLookup());
	
	static void traceDowncall(String name, Object... args) {
		String traceArgs = Arrays.stream(args)
				                   .map(Object::toString)
				                   .collect(Collectors.joining(", "));
		System.out.printf("%s(%s)\n", name, traceArgs);
	}
	
	@SuppressWarnings("SpellCheckingInspection")
	static MethodHandle upcallHandle(Class<?> fi, String name, FunctionDescriptor fdesc) {
		try {
			return MethodHandles.lookup().findVirtual(fi, name, fdesc.toMethodType());
		} catch (ReflectiveOperationException ex) {
			throw new AssertionError(ex);
		}
	}
	
	static MemoryLayout align(MemoryLayout layout, long align) {
		return switch (layout) {
			case PaddingLayout p -> p;
			case ValueLayout v -> v.withByteAlignment(align);
			case GroupLayout g -> {
				MemoryLayout[] alignedMembers = g.memberLayouts().stream()
						                                .map(m -> align(m, align)).toArray(MemoryLayout[]::new);
				yield g instanceof StructLayout ?
						      MemoryLayout.structLayout(alignedMembers) : MemoryLayout.unionLayout(alignedMembers);
			}
			case SequenceLayout s -> MemoryLayout.sequenceLayout(s.elementCount(), align(s.elementLayout(), align));
		};
	}
}

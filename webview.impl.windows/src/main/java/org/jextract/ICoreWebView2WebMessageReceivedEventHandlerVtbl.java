// Generated by jextract

package org.jextract;

import org.jextract.LayoutUtils.PointerLayoutHolder;

import java.lang.foreign.*;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

/**
 * {@snippet lang = c:
 * struct ICoreWebView2WebMessageReceivedEventHandlerVtbl {
 *     HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventHandler *, const IID *const, void **) __attribute__((stdcall));
 *     ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall));
 *     ULONG (*Release)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall));
 *     HRESULT (*Invoke)(ICoreWebView2WebMessageReceivedEventHandler *, ICoreWebView2 *, ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall));
 * }
 *}
 */
public class ICoreWebView2WebMessageReceivedEventHandlerVtbl {
	
	ICoreWebView2WebMessageReceivedEventHandlerVtbl() {
		// Should not be called directly
	}
	
	private static final StructLayout $LAYOUT = MemoryLayout.structLayout(
			LayoutUtils.C_POINTER.withName("QueryInterface"),
			LayoutUtils.C_POINTER.withName("AddRef"),
			LayoutUtils.C_POINTER.withName("Release"),
			LayoutUtils.C_POINTER.withName("Invoke")
	).withName("ICoreWebView2WebMessageReceivedEventHandlerVtbl");
	
	/**
	 * The layout of this struct
	 */
	public static StructLayout layout() {
		return $LAYOUT;
	}
	
	/**
	 * {@snippet lang = c:
	 * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventHandler *, const IID *const, void **) __attribute__((stdcall))
	 *}
	 */
	public static class QueryInterface {
		
		QueryInterface() {
			// Should not be called directly
		}
		
		/**
		 * The function pointer signature, expressed as a functional interface
		 */
		public interface Function {
			int apply(MemorySegment _x0, MemorySegment _x1, MemorySegment _x2);
		}
		
		private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
				LayoutUtils.HRESULT,
				ICoreWebView2WebMessageReceivedEventHandler.POINTER$LAYOUT,
				PointerLayoutHolder.IID_POINTER,
				LayoutUtils.VOID_POINTER_POINTER
		);
		
		/**
		 * The descriptor of this function pointer
		 */
		public static FunctionDescriptor descriptor() {
			return $DESC;
		}
		
		private static final MethodHandle UP$MH = FFMUtils.upcallHandle(QueryInterface.Function.class, "apply", $DESC);
		
		/**
		 * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
		 * The lifetime of the returned segment is managed by {@code arena}
		 */
		public static MemorySegment allocate(QueryInterface.Function fi, Arena arena) {
			return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
		}
		
		private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);
		
		/**
		 * Invoke the upcall stub {@code funcPtr}, with given parameters
		 */
		public static int invoke(MemorySegment funcPtr, MemorySegment _x0, MemorySegment _x1, MemorySegment _x2) {
			try {
				return (int) DOWN$MH.invokeExact(funcPtr, _x0, _x1, _x2);
			} catch (Error | RuntimeException ex) {
				throw ex;
			} catch (Throwable ex$) {
				throw new AssertionError("should not reach here", ex$);
			}
		}
	}
	
	private static final AddressLayout QueryInterface$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("QueryInterface"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventHandler *, const IID *const, void **) __attribute__((stdcall))
	 *}
	 */
	public static AddressLayout QueryInterface$layout() {
		return QueryInterface$LAYOUT;
	}
	
	private static final long QueryInterface$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("QueryInterface"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventHandler *, const IID *const, void **) __attribute__((stdcall))
	 *}
	 */
	public static long QueryInterface$offset() {
		return QueryInterface$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventHandler *, const IID *const, void **) __attribute__((stdcall))
	 *}
	 */
	public static MemorySegment QueryInterface(MemorySegment struct) {
		return struct.get(QueryInterface$LAYOUT, QueryInterface$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventHandler *, const IID *const, void **) __attribute__((stdcall))
	 *}
	 */
	public static void QueryInterface(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(QueryInterface$LAYOUT, QueryInterface$OFFSET, fieldValue);
	}
	
	/**
	 * {@snippet lang = c:
	 * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static class AddRef {
		
		AddRef() {
			// Should not be called directly
		}
		
		/**
		 * The function pointer signature, expressed as a functional interface
		 */
		public interface Function {
			int apply(MemorySegment _x0);
		}
		
		private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
				LayoutUtils.C_LONG,
				ICoreWebView2WebMessageReceivedEventHandler.POINTER$LAYOUT
		);
		
		/**
		 * The descriptor of this function pointer
		 */
		public static FunctionDescriptor descriptor() {
			return $DESC;
		}
		
		private static final MethodHandle UP$MH = FFMUtils.upcallHandle(AddRef.Function.class, "apply", $DESC);
		
		/**
		 * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
		 * The lifetime of the returned segment is managed by {@code arena}
		 */
		public static MemorySegment allocate(AddRef.Function fi, Arena arena) {
			return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
		}
		
		private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);
		
		/**
		 * Invoke the upcall stub {@code funcPtr}, with given parameters
		 */
		public static int invoke(MemorySegment funcPtr, MemorySegment _x0) {
			try {
				return (int) DOWN$MH.invokeExact(funcPtr, _x0);
			} catch (Error | RuntimeException ex) {
				throw ex;
			} catch (Throwable ex$) {
				throw new AssertionError("should not reach here", ex$);
			}
		}
	}
	
	private static final AddressLayout AddRef$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("AddRef"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static AddressLayout AddRef$layout() {
		return AddRef$LAYOUT;
	}
	
	private static final long AddRef$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("AddRef"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static long AddRef$offset() {
		return AddRef$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static MemorySegment AddRef(MemorySegment struct) {
		return struct.get(AddRef$LAYOUT, AddRef$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static void AddRef(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(AddRef$LAYOUT, AddRef$OFFSET, fieldValue);
	}
	
	/**
	 * {@snippet lang = c:
	 * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static class Release {
		
		Release() {
			// Should not be called directly
		}
		
		/**
		 * The function pointer signature, expressed as a functional interface
		 */
		public interface Function {
			int apply(MemorySegment _x0);
		}
		
		private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
				LayoutUtils.ULONG,
				ICoreWebView2WebMessageReceivedEventHandler.POINTER$LAYOUT
		);
		
		/**
		 * The descriptor of this function pointer
		 */
		public static FunctionDescriptor descriptor() {
			return $DESC;
		}
		
		private static final MethodHandle UP$MH = FFMUtils.upcallHandle(Release.Function.class, "apply", $DESC);
		
		/**
		 * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
		 * The lifetime of the returned segment is managed by {@code arena}
		 */
		public static MemorySegment allocate(Release.Function fi, Arena arena) {
			return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
		}
		
		private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);
		
		/**
		 * Invoke the upcall stub {@code funcPtr}, with given parameters
		 */
		public static int invoke(MemorySegment funcPtr, MemorySegment _x0) {
			try {
				return (int) DOWN$MH.invokeExact(funcPtr, _x0);
			} catch (Error | RuntimeException ex) {
				throw ex;
			} catch (Throwable ex$) {
				throw new AssertionError("should not reach here", ex$);
			}
		}
	}
	
	private static final AddressLayout Release$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("Release"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static AddressLayout Release$layout() {
		return Release$LAYOUT;
	}
	
	private static final long Release$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("Release"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static long Release$offset() {
		return Release$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static MemorySegment Release(MemorySegment struct) {
		return struct.get(Release$LAYOUT, Release$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventHandler *) __attribute__((stdcall))
	 *}
	 */
	public static void Release(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(Release$LAYOUT, Release$OFFSET, fieldValue);
	}
	
	/**
	 * {@snippet lang = c:
	 * HRESULT (*Invoke)(ICoreWebView2WebMessageReceivedEventHandler *, ICoreWebView2 *, ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
	 *}
	 */
	public static class Invoke {
		
		Invoke() {
			// Should not be called directly
		}
		
		/**
		 * The function pointer signature, expressed as a functional interface
		 */
		public interface Function {
			int apply(MemorySegment _x0, MemorySegment _x1, MemorySegment _x2);
		}
		
		private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
				LayoutUtils.HRESULT,
				ICoreWebView2WebMessageReceivedEventHandler.POINTER$LAYOUT,
				ICoreWebView2.POINTER$LAYOUT,
				ICoreWebView2WebMessageReceivedEventArgs.POINTER$LAYOUT
		);
		
		/**
		 * The descriptor of this function pointer
		 */
		public static FunctionDescriptor descriptor() {
			return $DESC;
		}
		
		private static final MethodHandle UP$MH = FFMUtils.upcallHandle(Invoke.Function.class, "apply", $DESC);
		
		/**
		 * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
		 * The lifetime of the returned segment is managed by {@code arena}
		 */
		public static MemorySegment allocate(Invoke.Function fi, Arena arena) {
			return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
		}
		
		private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);
		
		/**
		 * Invoke the upcall stub {@code funcPtr}, with given parameters
		 */
		public static int invoke(MemorySegment funcPtr, MemorySegment _x0, MemorySegment _x1, MemorySegment _x2) {
			try {
				return (int) DOWN$MH.invokeExact(funcPtr, _x0, _x1, _x2);
			} catch (Error | RuntimeException ex) {
				throw ex;
			} catch (Throwable ex$) {
				throw new AssertionError("should not reach here", ex$);
			}
		}
	}
	
	private static final AddressLayout Invoke$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("Invoke"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * HRESULT (*Invoke)(ICoreWebView2WebMessageReceivedEventHandler *, ICoreWebView2 *, ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
	 *}
	 */
	public static AddressLayout Invoke$layout() {
		return Invoke$LAYOUT;
	}
	
	private static final long Invoke$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("Invoke"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * HRESULT (*Invoke)(ICoreWebView2WebMessageReceivedEventHandler *, ICoreWebView2 *, ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
	 *}
	 */
	public static long Invoke$offset() {
		return Invoke$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * HRESULT (*Invoke)(ICoreWebView2WebMessageReceivedEventHandler *, ICoreWebView2 *, ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
	 *}
	 */
	public static MemorySegment Invoke(MemorySegment struct) {
		return struct.get(Invoke$LAYOUT, Invoke$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * HRESULT (*Invoke)(ICoreWebView2WebMessageReceivedEventHandler *, ICoreWebView2 *, ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
	 *}
	 */
	public static void Invoke(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(Invoke$LAYOUT, Invoke$OFFSET, fieldValue);
	}
	
	/**
	 * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
	 * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
	 */
	public static MemorySegment asSlice(MemorySegment array, long index) {
		return array.asSlice(layout().byteSize() * index);
	}
	
	/**
	 * The size (in bytes) of this struct
	 */
	public static long sizeof() {
		return layout().byteSize();
	}
	
	/**
	 * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
	 */
	public static MemorySegment allocate(SegmentAllocator allocator) {
		return allocator.allocate(layout());
	}
	
	/**
	 * Allocate an array of size {@code elementCount} using {@code allocator}.
	 * The returned segment has size {@code elementCount * layout().byteSize()}.
	 */
	public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
		return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
	}
	
	/**
	 * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
	 * The returned segment has size {@code layout().byteSize()}
	 */
	public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
		return reinterpret(addr, 1, arena, cleanup);
	}
	
	/**
	 * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
	 * The returned segment has size {@code elementCount * layout().byteSize()}
	 */
	public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
		return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
	}
}

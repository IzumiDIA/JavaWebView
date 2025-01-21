// Generated by jextract

package org.jextract;

import java.lang.foreign.*;
import java.util.function.*;

import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct ICoreWebView2WebMessageReceivedEventHandler {
 *     struct ICoreWebView2WebMessageReceivedEventHandlerVtbl *lpVtbl;
 * }
 * }
 */
public class ICoreWebView2WebMessageReceivedEventHandler {
	
	ICoreWebView2WebMessageReceivedEventHandler() {
		// Should not be called directly
	}
	
	private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
			Windows.C_POINTER.withName("lpVtbl")
	).withName("ICoreWebView2WebMessageReceivedEventHandler");
	
	/**
	 * The layout of this struct
	 */
	public static GroupLayout layout() {
		return $LAYOUT;
	}
	
	private static final AddressLayout lpVtbl$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("lpVtbl"));
	
	/**
	 * Layout for field:
	 * {@snippet lang=c :
	 * struct ICoreWebView2WebMessageReceivedEventHandlerVtbl *lpVtbl
	 * }
	 */
	public static AddressLayout lpVtbl$layout() {
		return lpVtbl$LAYOUT;
	}
	
	private static final long lpVtbl$OFFSET = 0;
	
	/**
	 * Offset for field:
	 * {@snippet lang=c :
	 * struct ICoreWebView2WebMessageReceivedEventHandlerVtbl *lpVtbl
	 * }
	 */
	public static long lpVtbl$offset() {
		return lpVtbl$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang=c :
	 * struct ICoreWebView2WebMessageReceivedEventHandlerVtbl *lpVtbl
	 * }
	 */
	public static MemorySegment lpVtbl(MemorySegment struct) {
		return struct.get(lpVtbl$LAYOUT, lpVtbl$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang=c :
	 * struct ICoreWebView2WebMessageReceivedEventHandlerVtbl *lpVtbl
	 * }
	 */
	public static void lpVtbl(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(lpVtbl$LAYOUT, lpVtbl$OFFSET, fieldValue);
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
	public static long sizeof() { return layout().byteSize(); }
	
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

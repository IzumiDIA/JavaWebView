// Generated by jextract

package org.jextract;

import java.lang.foreign.Arena;
import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;
import java.util.function.Consumer;

import static java.lang.foreign.ValueLayout.OfInt;

/**
 * {@snippet lang=c :
 * struct tagPOINT {
 *     LONG x;
 *     LONG y;
 * }
 * }
 */
public class tagPOINT {
	
	tagPOINT() {
		// Should not be called directly
	}
	
	private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
			Windows.C_LONG.withName("x"),
			Windows.C_LONG.withName("y")
	).withName("tagPOINT");
	
	/**
	 * The layout of this struct
	 */
	public static GroupLayout layout() {
		return $LAYOUT;
	}
	
	private static final OfInt x$LAYOUT = (OfInt)$LAYOUT.select(ValueLayout.PathElement.groupElement("x"));
	
	/**
	 * Layout for field:
	 * {@snippet lang=c :
	 * LONG x
	 * }
	 */
	public static OfInt x$layout() {
		return x$LAYOUT;
	}
	
	private static final long x$OFFSET = $LAYOUT.byteOffset(ValueLayout.PathElement.groupElement("x"));
	
	/**
	 * Offset for field:
	 * {@snippet lang=c :
	 * LONG x
	 * }
	 */
	public static long x$offset() {
		return x$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang=c :
	 * LONG x
	 * }
	 */
	public static int x(MemorySegment struct) {
		return struct.get(x$LAYOUT, x$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang=c :
	 * LONG x
	 * }
	 */
	public static void x(MemorySegment struct, int fieldValue) {
		struct.set(x$LAYOUT, x$OFFSET, fieldValue);
	}
	
	private static final OfInt y$LAYOUT = (OfInt)$LAYOUT.select(ValueLayout.PathElement.groupElement("y"));
	
	/**
	 * Layout for field:
	 * {@snippet lang=c :
	 * LONG y
	 * }
	 */
	public static OfInt y$layout() {
		return y$LAYOUT;
	}
	
	private static final long y$OFFSET = $LAYOUT.byteOffset(ValueLayout.PathElement.groupElement("y"));
	
	/**
	 * Offset for field:
	 * {@snippet lang=c :
	 * LONG y
	 * }
	 */
	public static long y$offset() {
		return y$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang=c :
	 * LONG y
	 * }
	 */
	public static int y(MemorySegment struct) {
		return struct.get(y$LAYOUT, y$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang=c :
	 * LONG y
	 * }
	 */
	public static void y(MemorySegment struct, int fieldValue) {
		struct.set(y$LAYOUT, y$OFFSET, fieldValue);
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

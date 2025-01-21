// Generated by jextract

package org.jextract;

import java.lang.foreign.*;
import java.util.function.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct EventRegistrationToken {
 *     long long value;
 * }
 * }
 */
public class EventRegistrationToken {
	
	EventRegistrationToken() {
		// Should not be called directly
	}
	
	private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
			Windows.C_LONG_LONG.withName("value")
	).withName("EventRegistrationToken");
	
	/**
	 * The layout of this struct
	 */
	public static GroupLayout layout() {
		return $LAYOUT;
	}
	
	private static final OfLong value$LAYOUT = (OfLong)$LAYOUT.select(groupElement("value"));
	
	/**
	 * Layout for field:
	 * {@snippet lang=c :
	 * long long value
	 * }
	 */
	public static OfLong value$layout() {
		return value$LAYOUT;
	}
	
	private static final long value$OFFSET = 0;
	
	/**
	 * Offset for field:
	 * {@snippet lang=c :
	 * long long value
	 * }
	 */
	public static long value$offset() {
		return value$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang=c :
	 * long long value
	 * }
	 */
	public static long value(MemorySegment struct) {
		return struct.get(value$LAYOUT, value$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang=c :
	 * long long value
	 * }
	 */
	public static void value(MemorySegment struct, long fieldValue) {
		struct.set(value$LAYOUT, value$OFFSET, fieldValue);
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

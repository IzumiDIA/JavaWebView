// Generated by jextract

package org.jextract;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.StructLayout;
import java.util.function.Consumer;

import static java.lang.foreign.ValueLayout.OfInt;

/**
 * {@snippet lang = c:
 * struct tagWNDCLASSEXW {
 *     UINT cbSize;
 *     UINT style;
 *     WNDPROC lpfnWndProc;
 *     int cbClsExtra;
 *     int cbWndExtra;
 *     HINSTANCE hInstance;
 *     HICON hIcon;
 *     HCURSOR hCursor;
 *     HBRUSH hbrBackground;
 *     LPCWSTR lpszMenuName;
 *     LPCWSTR lpszClassName;
 *     HICON hIconSm;
 * }
 *}
 */
@SuppressWarnings("SpellCheckingInspection")
public class tagWNDCLASSEXW {
	
	tagWNDCLASSEXW() {
		// Should not be called directly
	}
	
	private static final StructLayout $LAYOUT = MemoryLayout.structLayout(
			LayoutUtils.UINT.withName("cbSize"),
			LayoutUtils.UINT.withName("style"),
			LayoutUtils.WNDPROC_POINTER.withName("lpfnWndProc"),
			LayoutUtils.C_INT.withName("cbClsExtra"),
			LayoutUtils.C_INT.withName("cbWndExtra"),
			LayoutUtils.HINSTANCE.withName("hInstance"),
			LayoutUtils.HICON.withName("hIcon"),
			LayoutUtils.HCURSOR.withName("hCursor"),
			LayoutUtils.HBRUSH.withName("hbrBackground"),
			LayoutUtils.LPCWSTR.withName("lpszMenuName"),
			LayoutUtils.LPCWSTR.withName("lpszClassName"),
			LayoutUtils.HICON.withName("hIconSm")
	).withName("tagWNDCLASSEXW");
	
	/**
	 * The layout of this struct
	 */
	public static StructLayout layout() {
		return $LAYOUT;
	}
	
	private static final OfInt cbSize$LAYOUT = (OfInt) $LAYOUT.select(PathElement.groupElement("cbSize"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * UINT cbSize
	 *}
	 */
	public static OfInt cbSize$layout() {
		return cbSize$LAYOUT;
	}
	
	private static final long cbSize$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("cbSize"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * UINT cbSize
	 *}
	 */
	public static long cbSize$offset() {
		return cbSize$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * UINT cbSize
	 *}
	 */
	public static int cbSize(MemorySegment struct) {
		return struct.get(cbSize$LAYOUT, cbSize$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * UINT cbSize
	 *}
	 */
	public static void cbSize(MemorySegment struct, int fieldValue) {
		struct.set(cbSize$LAYOUT, cbSize$OFFSET, fieldValue);
	}
	
	private static final OfInt style$LAYOUT = (OfInt) $LAYOUT.select(PathElement.groupElement("style"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * UINT style
	 *}
	 */
	public static OfInt style$layout() {
		return style$LAYOUT;
	}
	
	private static final long style$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("style"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * UINT style
	 *}
	 */
	public static long style$offset() {
		return style$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * UINT style
	 *}
	 */
	public static int style(MemorySegment struct) {
		return struct.get(style$LAYOUT, style$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * UINT style
	 *}
	 */
	public static void style(MemorySegment struct, int fieldValue) {
		struct.set(style$LAYOUT, style$OFFSET, fieldValue);
	}
	
	private static final AddressLayout lpfnWndProc$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("lpfnWndProc"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * WNDPROC lpfnWndProc
	 *}
	 */
	public static AddressLayout lpfnWndProc$layout() {
		return lpfnWndProc$LAYOUT;
	}
	
	private static final long lpfnWndProc$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("lpfnWndProc"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * WNDPROC lpfnWndProc
	 *}
	 */
	public static long lpfnWndProc$offset() {
		return lpfnWndProc$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * WNDPROC lpfnWndProc
	 *}
	 */
	public static MemorySegment lpfnWndProc(MemorySegment struct) {
		return struct.get(lpfnWndProc$LAYOUT, lpfnWndProc$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * WNDPROC lpfnWndProc
	 *}
	 */
	public static void lpfnWndProc(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(lpfnWndProc$LAYOUT, lpfnWndProc$OFFSET, fieldValue);
	}
	
	private static final OfInt cbClsExtra$LAYOUT = (OfInt) $LAYOUT.select(PathElement.groupElement("cbClsExtra"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * int cbClsExtra
	 *}
	 */
	public static OfInt cbClsExtra$layout() {
		return cbClsExtra$LAYOUT;
	}
	
	private static final long cbClsExtra$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("cbClsExtra"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * int cbClsExtra
	 *}
	 */
	public static long cbClsExtra$offset() {
		return cbClsExtra$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * int cbClsExtra
	 *}
	 */
	public static int cbClsExtra(MemorySegment struct) {
		return struct.get(cbClsExtra$LAYOUT, cbClsExtra$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * int cbClsExtra
	 *}
	 */
	public static void cbClsExtra(MemorySegment struct, int fieldValue) {
		struct.set(cbClsExtra$LAYOUT, cbClsExtra$OFFSET, fieldValue);
	}
	
	private static final OfInt cbWndExtra$LAYOUT = (OfInt) $LAYOUT.select(PathElement.groupElement("cbWndExtra"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * int cbWndExtra
	 *}
	 */
	public static OfInt cbWndExtra$layout() {
		return cbWndExtra$LAYOUT;
	}
	
	private static final long cbWndExtra$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("cbWndExtra"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * int cbWndExtra
	 *}
	 */
	public static long cbWndExtra$offset() {
		return cbWndExtra$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * int cbWndExtra
	 *}
	 */
	public static int cbWndExtra(MemorySegment struct) {
		return struct.get(cbWndExtra$LAYOUT, cbWndExtra$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * int cbWndExtra
	 *}
	 */
	public static void cbWndExtra(MemorySegment struct, int fieldValue) {
		struct.set(cbWndExtra$LAYOUT, cbWndExtra$OFFSET, fieldValue);
	}
	
	private static final AddressLayout hInstance$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("hInstance"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * HINSTANCE hInstance
	 *}
	 */
	public static AddressLayout hInstance$layout() {
		return hInstance$LAYOUT;
	}
	
	private static final long hInstance$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("hInstance"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * HINSTANCE hInstance
	 *}
	 */
	public static long hInstance$offset() {
		return hInstance$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * HINSTANCE hInstance
	 *}
	 */
	public static MemorySegment hInstance(MemorySegment struct) {
		return struct.get(hInstance$LAYOUT, hInstance$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * HINSTANCE hInstance
	 *}
	 */
	public static void hInstance(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(hInstance$LAYOUT, hInstance$OFFSET, fieldValue);
	}
	
	private static final AddressLayout hIcon$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("hIcon"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * HICON hIcon
	 *}
	 */
	public static AddressLayout hIcon$layout() {
		return hIcon$LAYOUT;
	}
	
	private static final long hIcon$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("hIcon"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * HICON hIcon
	 *}
	 */
	public static long hIcon$offset() {
		return hIcon$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * HICON hIcon
	 *}
	 */
	public static MemorySegment hIcon(MemorySegment struct) {
		return struct.get(hIcon$LAYOUT, hIcon$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * HICON hIcon
	 *}
	 */
	public static void hIcon(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(hIcon$LAYOUT, hIcon$OFFSET, fieldValue);
	}
	
	private static final AddressLayout hCursor$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("hCursor"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * HCURSOR hCursor
	 *}
	 */
	public static AddressLayout hCursor$layout() {
		return hCursor$LAYOUT;
	}
	
	private static final long hCursor$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("hCursor"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * HCURSOR hCursor
	 *}
	 */
	public static long hCursor$offset() {
		return hCursor$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * HCURSOR hCursor
	 *}
	 */
	public static MemorySegment hCursor(MemorySegment struct) {
		return struct.get(hCursor$LAYOUT, hCursor$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * HCURSOR hCursor
	 *}
	 */
	public static void hCursor(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(hCursor$LAYOUT, hCursor$OFFSET, fieldValue);
	}
	
	private static final AddressLayout hbrBackground$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("hbrBackground"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * HBRUSH hbrBackground
	 *}
	 */
	public static AddressLayout hbrBackground$layout() {
		return hbrBackground$LAYOUT;
	}
	
	private static final long hbrBackground$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("hbrBackground"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * HBRUSH hbrBackground
	 *}
	 */
	public static long hbrBackground$offset() {
		return hbrBackground$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * HBRUSH hbrBackground
	 *}
	 */
	public static MemorySegment hbrBackground(MemorySegment struct) {
		return struct.get(hbrBackground$LAYOUT, hbrBackground$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * HBRUSH hbrBackground
	 *}
	 */
	public static void hbrBackground(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(hbrBackground$LAYOUT, hbrBackground$OFFSET, fieldValue);
	}
	
	private static final AddressLayout lpszMenuName$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("lpszMenuName"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszMenuName
	 *}
	 */
	public static AddressLayout lpszMenuName$layout() {
		return lpszMenuName$LAYOUT;
	}
	
	private static final long lpszMenuName$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("lpszMenuName"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszMenuName
	 *}
	 */
	public static long lpszMenuName$offset() {
		return lpszMenuName$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszMenuName
	 *}
	 */
	public static MemorySegment lpszMenuName(MemorySegment struct) {
		return struct.get(lpszMenuName$LAYOUT, lpszMenuName$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszMenuName
	 *}
	 */
	public static void lpszMenuName(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(lpszMenuName$LAYOUT, lpszMenuName$OFFSET, fieldValue);
	}
	
	private static final AddressLayout lpszClassName$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("lpszClassName"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszClassName
	 *}
	 */
	public static AddressLayout lpszClassName$layout() {
		return lpszClassName$LAYOUT;
	}
	
	private static final long lpszClassName$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("lpszClassName"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszClassName
	 *}
	 */
	public static long lpszClassName$offset() {
		return lpszClassName$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszClassName
	 *}
	 */
	public static MemorySegment lpszClassName(MemorySegment struct) {
		return struct.get(lpszClassName$LAYOUT, lpszClassName$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * LPCWSTR lpszClassName
	 *}
	 */
	public static void lpszClassName(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(lpszClassName$LAYOUT, lpszClassName$OFFSET, fieldValue);
	}
	
	private static final AddressLayout hIconSm$LAYOUT = (AddressLayout) $LAYOUT.select(PathElement.groupElement("hIconSm"));
	
	/**
	 * Layout for field:
	 * {@snippet lang = c:
	 * HICON hIconSm
	 *}
	 */
	public static AddressLayout hIconSm$layout() {
		return hIconSm$LAYOUT;
	}
	
	private static final long hIconSm$OFFSET = $LAYOUT.byteOffset(PathElement.groupElement("hIconSm"));
	
	/**
	 * Offset for field:
	 * {@snippet lang = c:
	 * HICON hIconSm
	 *}
	 */
	public static long hIconSm$offset() {
		return hIconSm$OFFSET;
	}
	
	/**
	 * Getter for field:
	 * {@snippet lang = c:
	 * HICON hIconSm
	 *}
	 */
	public static MemorySegment hIconSm(MemorySegment struct) {
		return struct.get(hIconSm$LAYOUT, hIconSm$OFFSET);
	}
	
	/**
	 * Setter for field:
	 * {@snippet lang = c:
	 * HICON hIconSm
	 *}
	 */
	public static void hIconSm(MemorySegment struct, MemorySegment fieldValue) {
		struct.set(hIconSm$LAYOUT, hIconSm$OFFSET, fieldValue);
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
		final var memorySegment = allocator.allocate(layout());
		tagWNDCLASSEXW.cbSize(memorySegment, (int) tagWNDCLASSEXW.sizeof());
		tagWNDCLASSEXW.hInstance(memorySegment, Constants.H_INSTANCE);
		return memorySegment;
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

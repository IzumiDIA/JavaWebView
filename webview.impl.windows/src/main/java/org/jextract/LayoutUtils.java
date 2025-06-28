package org.jextract;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.ValueLayout.*;

public final class LayoutUtils {
	
	private LayoutUtils() {
		// Should not be called directly
	}
	
	public static final OfBoolean C_BOOL;
	public static final OfByte C_CHAR;
	public static final OfShort C_SHORT;
	public static final OfChar WCHAR;
	public static final OfInt C_INT;
	public static final OfLong C_LONG_LONG;
	public static final OfFloat C_FLOAT;
	public static final OfDouble C_DOUBLE;
	public static final AddressLayout C_POINTER;
	public static final OfInt C_LONG;
	public static final OfDouble C_LONG_DOUBLE;
	
	static {
		final var canonicalLayouts = Linker.nativeLinker().canonicalLayouts();
		C_BOOL = (OfBoolean) canonicalLayouts.get("bool");
		C_CHAR = (OfByte) canonicalLayouts.get("char");
		C_SHORT = (OfShort) canonicalLayouts.get("short");
		WCHAR = (OfChar) canonicalLayouts.get("wchar_t");
		C_INT = (OfInt) canonicalLayouts.get("int");
		C_LONG_LONG = (OfLong) canonicalLayouts.get("long long");
		C_FLOAT = (OfFloat) canonicalLayouts.get("float");
		C_DOUBLE = (OfDouble) canonicalLayouts.get("double");
		C_POINTER = ((AddressLayout) canonicalLayouts.get("void*"))
				            .withTargetLayout(MemoryLayout.sequenceLayout(Long.MAX_VALUE, LayoutUtils.C_CHAR));
		C_LONG = (OfInt) canonicalLayouts.get("long");
		C_LONG_DOUBLE = (OfDouble) canonicalLayouts.get("double");
	}
	
	public static final AddressLayout DOUBLE_POINTER = C_POINTER.withTargetLayout(C_DOUBLE);
	public static final OfInt UINT = C_INT;
	@SuppressWarnings("SpellCheckingInspection")
	public static final OfLong LONGLONG = C_LONG_LONG;
	public static final OfShort ATOM = C_SHORT;
	/**
	 * {@snippet lang = c:
	 * typedef const CHAR *LPCSTR
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPCSTR = C_POINTER.withTargetLayout(MemoryLayout.sequenceLayout(Long.MAX_VALUE, C_CHAR));
	/**
	 * {@snippet lang = c:
	 * typedef const WCHAR *PCWSTR
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout PCWSTR = C_POINTER.withTargetLayout(MemoryLayout.sequenceLayout(Long.MAX_VALUE / WCHAR.byteSize(), WCHAR));
	/**
	 * {@snippet lang = c:
	 * typedef const WCHAR *LPCWSTR
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPCWSTR = C_POINTER.withTargetLayout(MemoryLayout.sequenceLayout(Long.MAX_VALUE / WCHAR.byteSize(), WCHAR));
	/**
	 * {@snippet lang = c:
	 * typedef WCHAR *LPWSTR
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPWSTR = C_POINTER.withTargetLayout(MemoryLayout.sequenceLayout(Long.MAX_VALUE / WCHAR.byteSize(), WCHAR));
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPWSTR_POINTER = C_POINTER.withTargetLayout(LPWSTR);
	/**
	 * {@snippet lang = c:
	 * typedef void *LPVOID
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPVOID = C_POINTER;
	/**
	 * {@snippet lang = c:
	 * typedef long LONG
	 *}
	 */
	public static final OfInt LONG = C_LONG;
	@SuppressWarnings("SpellCheckingInspection")
	public static final OfInt HRESULT = LONG;
	/**
	 * {@snippet lang = c:
	 * typedef unsigned long DWORD
	 *}
	 */
	public static final OfInt DWORD = C_LONG;
	@SuppressWarnings("SpellCheckingInspection")
	public static final OfInt ULONG = DWORD;
	public static final AddressLayout C_BOOL_POINTER = C_POINTER.withTargetLayout(C_BOOL);
	public static final AddressLayout VOID_POINTER_POINTER = C_POINTER.withTargetLayout(C_POINTER);
	/**
	 * {@snippet lang = c:
	 * typedef LONG_PTR LRESULT
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final OfLong LRESULT = C_LONG_LONG;
	/**
	 * {@snippet lang = c:
	 * typedef LONG_PTR LPARAM
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final OfLong LPARAM = C_LONG_LONG;
	/**
	 * {@snippet lang = c:
	 * typedef UINT_PTR WPARAM
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final OfLong WPARAM = C_LONG_LONG;
	/**
	 * {@snippet lang = c:
	 * typedef long long LONG_PTR
	 *}
	 */
	public static final OfLong LONG_PTR = C_LONG_LONG;
	/**
	 * {@snippet lang = c:
	 * typedef struct HICON__ {
	 *     int unused;
	 * } *HICON
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HICON = C_POINTER.withTargetLayout(HICON__.layout());
	/**
	 * {@snippet lang = c:
	 * typedef HICON HCURSOR
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HCURSOR = HICON;
	/**
	 * {@snippet lang = c:
	 * typedef struct HMENU__ {
	 *     int unused;
	 * } *HMENU
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HMENU = C_POINTER.withTargetLayout(HMENU__.layout());
	/**
	 * {@snippet lang = c:
	 * typedef struct HBRUSH__ {
	 *     int unused;
	 * } *HBRUSH
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HBRUSH = C_POINTER.withTargetLayout(HBRUSH__.layout());
	/**
	 * {@snippet lang = c:
	 * typedef struct HWND__ {
	 *     int unused;
	 * } *HWND
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HWND = C_POINTER.withTargetLayout(HWND__.layout());
	
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HWND_POINTER = C_POINTER.withTargetLayout(HWND);
	/**
	 * {@snippet lang = c:
	 * typedef struct HKEY__ {
	 *     int unused;
	 * } *HKEY
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HKEY = C_POINTER.withTargetLayout(HKEY__.layout());
	/**
	 * {@snippet lang = c:
	 * typedef struct HINSTANCE__ {
	 *     int unused;
	 * } *HINSTANCE
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HINSTANCE = C_POINTER.withTargetLayout(HINSTANCE__.layout());
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout HMODULE = HINSTANCE;
	
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPCREATESTRUCTA = C_POINTER.withTargetLayout(tagCREATESTRUCTA.layout());
	/**
	 * {@snippet lang = c:
	 * typedef LPCREATESTRUCTA LPCREATESTRUCT
	 *}
	 */
	public static final AddressLayout LPCREATESTRUCT = LPCREATESTRUCTA;
	/**
	 * {@snippet lang = c:
	 * typedef struct tagMINMAXINFO {
	 *     POINT ptReserved;
	 *     POINT ptMaxSize;
	 *     POINT ptMaxPosition;
	 *     POINT ptMinTrackSize;
	 *     POINT ptMaxTrackSize;
	 * } *LPMINMAXINFO
	 *}
	 */
	public static final AddressLayout LPMINMAXINFO = C_POINTER.withTargetLayout(tagMINMAXINFO.layout());
	/**
	 * {@snippet lang = c:
	 * typedef struct tagMSG {
	 *     HWND hwnd;
	 *     UINT message;
	 *     WPARAM wParam;
	 *     LPARAM lParam;
	 *     DWORD time;
	 *     POINT pt;
	 * } *LPMSG
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPMSG = C_POINTER.withTargetLayout(tagMSG.layout());
	/**
	 * {@snippet lang = c:
	 * typedef struct tagRECT {
	 *     LONG left;
	 *     LONG top;
	 *     LONG right;
	 *     LONG bottom;
	 * } *LPRECT
	 *}
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout LPRECT = C_POINTER.withTargetLayout(tagRECT.layout());
	public static final AddressLayout RECT_POINTER = LPRECT;
	/**
	 * {@snippet lang = c:
	 * typedef LARGE_INTEGER *PLARGE_INTEGER
	 *}
	 */
	public static final AddressLayout PLARGE_INTEGER = C_POINTER.withTargetLayout(LARGE_INTEGER.layout());
	@SuppressWarnings("SpellCheckingInspection")
	public static final AddressLayout WNDPROC_POINTER = C_POINTER;
	
	/**
	 * Lazy loading is used to solve the problem of static initialized ring dependencies.
	 */
	public static final class PointerLayoutHolder {
		public static final AddressLayout IID_POINTER = C_POINTER.withTargetLayout(IID.layout());
		public static final AddressLayout POINT_POINTER = C_POINTER.withTargetLayout(POINT.layout());
		@SuppressWarnings("SpellCheckingInspection")
		public static final AddressLayout WNDCLASSEXW_POINTER = C_POINTER.withTargetLayout(WNDCLASSEXW.layout());
		public static final AddressLayout I_CORE_WEB_VIEW_2_SETTINGS_POINTER = C_POINTER.withTargetLayout(ICoreWebView2Settings.layout());
		public static final AddressLayout I_CORE_WEB_VIEW_2_SETTINGS_POINTER_POINTER = C_POINTER.withTargetLayout(I_CORE_WEB_VIEW_2_SETTINGS_POINTER);
		
		private PointerLayoutHolder() {
			throw new UnsupportedOperationException();
		}
	}
}

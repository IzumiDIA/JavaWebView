package org.jextract;

import java.lang.foreign.MemorySegment;

public final class Constants {
	private Constants() {
		throw new UnsupportedOperationException();
	}
	
	static final MemorySegment H_INSTANCE = Windows.GetModuleHandleA(MemorySegment.NULL);
	
	@SuppressWarnings("SpellCheckingInspection")
	public static final class WindowMessages {
		
		public static final int
				DESTROY = 0x0002,
				SIZE = 0x0005,
				WM_SETFOCUS = 0x0007,
				WM_KILLFOCUS = 0x0008,
				CLOSE = 0x0010,
				QUIT = 0x0012,
				SYSCOMMAND = 0x0112,
				SIZING = 0x0214,
				USER = 0x0400;
		
		private WindowMessages() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static final class SystemCommand {
		public static final int MINIMIZE = 0xF020,
				MAXIMIZE = 0xF030,
				CLOSE = 0xF060,
				RESTORE = 0xF120;
		
		private SystemCommand() {
			throw new UnsupportedOperationException();
		}
	}
	
	@SuppressWarnings("SpellCheckingInspection")
	public static final class WindowStyles {
		private WindowStyles() {
			throw new UnsupportedOperationException();
		}
		
		public static final int
				OVERLAPPED = 0x00000000,
				CAPTION = 0x00C00000,
				SYSMENU = 0x00080000,
				THICKFRAME = 0x00040000,
				MINIMIZEBOX = 0x00020000,
				MAXIMIZEBOX = 0x00010000,
				OVERLAPPEDWINDOW = OVERLAPPED |
				                   CAPTION |
				                   SYSMENU |
				                   THICKFRAME |
				                   MINIMIZEBOX |
				                   MAXIMIZEBOX,
				VISIBLE = 0x10000000;
	}
	@SuppressWarnings("SpellCheckingInspection")
	public static final int CW_USEDEFAULT = 0x80000000;
	public static final int SW_SHOW = 5;
	public static final int IMAGE_ICON = 1;
	/**
	 * Loads the standalone image from the file specified by name (icon, cursor, or bitmap file).
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final int LR_LOADFROMFILE = 0x00000010;
	public static final int COLOR_WINDOW = 5;
	public static final int COLOR_WINDOWTEXT = 8;
	public static final int COLOR_HIGHLIGHT = 13;
	public static final int COLOR_HIGHLIGHTTEXT = 14;
	public static final int COLOR_3DFACE = 15;
	public static final int COLOR_GRAYTEXT = 17;
	public static final int COLOR_BTNTEXT = 18;
	public static final int COLOR_HOTLIGHT = 26;
	@SuppressWarnings("SpellCheckingInspection")
	public static final int WS_EX_APPWINDOW = 0x00040000;
	
	@SuppressWarnings("SpellCheckingInspection")
	public static final class SetWindowPosition {
		public static final int SWP_NOSIZE = 0x0001,
				SWP_NOMOVE = 0x0002,
				SWP_SHOWWINDOW = 0x0040;
		public static final MemorySegment HWND_TOP = MemorySegment.ofAddress(0);
		private SetWindowPosition() {
			throw new UnsupportedOperationException();
		}
	}
}

package io.github.IzumiDIA.windows.builder.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.Point2D;
import io.github.IzumiDIA.builder.WindowBuilder;
import io.github.IzumiDIA.constant.enums.Color;
import io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl;
import io.github.IzumiDIA.windows.impl.PlatformWindowImpl;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jetbrains.annotations.NotNull;
import org.jextract.WNDCLASSEXW;
import org.jextract.WNDPROC;
import org.jextract.Windows;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.file.Path;
import java.util.Optional;

public class WindowBuilderImpl extends WindowsNativeObject implements WindowBuilder<WebViewControllerImpl> {
	
	private static final double SCALE_X, SCALE_Y;
	
	static {
		final var defaultTransform = GraphicsEnvironment.getLocalGraphicsEnvironment()
				                             .getDefaultScreenDevice()
				                             .getDefaultConfiguration()
				                             .getDefaultTransform();
		SCALE_X = defaultTransform.getScaleX();
		SCALE_Y = defaultTransform.getScaleY();
	}
	
	private static final MemorySegment hInstance = Windows.GetModuleHandleA(MemorySegment.NULL);
	private final MemorySegment windowClass;
	
	private int extendStyle = 0;
	private String windowClassName = null, windowName = null, iconFilePath = null;
	private Point2D position = null;
	private Dimension dimension = null;
	
	@SuppressWarnings("java:S3252")
	public WindowBuilderImpl(final Arena arena) {
		super(arena);
		this.windowClass = WNDCLASSEXW.allocate(this.arena);
		WNDCLASSEXW.cbSize(this.windowClass, 80);
		WNDCLASSEXW.cbClsExtra(this.windowClass, 0);
		WNDCLASSEXW.cbWndExtra(this.windowClass, 0);
		WNDCLASSEXW.hInstance(
				this.windowClass, hInstance
		);
		WNDCLASSEXW.hbrBackground(
				this.windowClass, MemorySegment.ofAddress(Color.ACTIVEBORDER.ordinal())
		);
		WNDCLASSEXW.lpszMenuName(
				this.windowClass,
				MemorySegment.NULL
		);
	}
	
	@SuppressWarnings("java:S3252")
	@Override
	public WindowBuilderImpl setStyle(final int style) {
		WNDCLASSEXW.style(this.windowClass, style);
		return this;
	}
	
	@SuppressWarnings("java:S3252")
	@Override
	public WindowBuilderImpl setController(final @NotNull WebViewControllerImpl windowProcedure) {
		WNDCLASSEXW.lpfnWndProc(
				this.windowClass,
				WNDPROC.allocate(
						windowProcedure,
						this.arena
				)
		);
		return this;
	}
	
	@SuppressWarnings("java:S3252")
	@Override
	public WindowBuilderImpl setHandleIcon(final @NotNull Path iconFilePath) {
		if ( iconFilePath.isAbsolute() ) {
			if ( !iconFilePath.endsWith(".ico") ) {
				final var iconFile = iconFilePath.toFile();
				if ( iconFile.isFile() && iconFile.canRead() ) {
					this.iconFilePath = iconFilePath.toString();
					return this;
				}else throw new IllegalArgumentException("Path: [" + iconFile + "]. There is no readable file.");
			}else throw new IllegalArgumentException("The file suffix extension does not match.");
		}else throw new IllegalArgumentException("The path to the Icon file must be absolute.");
	}
	
	@SuppressWarnings("java:S3252")
	@Override
	public WindowBuilderImpl setHandleCursor(final @NotNull MemorySegment handleCursor) {
		WNDCLASSEXW.hCursor(this.windowClass, handleCursor);
		return this;
	}
	
	@SuppressWarnings("java:S3252")
	public WindowBuilderImpl setBrushBackground(final @NotNull Color handleBrush) {
		WNDCLASSEXW.hbrBackground(this.windowClass, MemorySegment.ofAddress(handleBrush.ordinal()));
		return this;
	}
	
	@Override
	public WindowBuilderImpl setSzClassName(final @NotNull String windowClassName) {
		this.windowClassName = windowClassName;
		return this;
	}
	
	@SuppressWarnings("java:S3252")
	@Override
	public WindowBuilderImpl setHandleIconSmall(final @NotNull MemorySegment handleIconSmall) {
		WNDCLASSEXW.hIconSm(this.windowClass, handleIconSmall);
		return this;
	}
	
	@Override
	public WindowBuilderImpl setExtendStyle(final int extendStyle) {
		this.extendStyle = extendStyle;
		return this;
	}
	
	@Override
	public WindowBuilderImpl setWindowName(final @NotNull String windowName) {
		this.windowName = windowName;
		return this;
	}
	
	@Override
	public WindowBuilderImpl setPosition(final @NotNull Point2D position) {
		this.position = position;
		return this;
	}
	
	@Override
	public WindowBuilderImpl setDimension(final @NotNull Dimension dimension) {
		this.dimension = dimension;
		return this;
	}
	
	@SuppressWarnings("java:S3252")
	@Override
	public PlatformWindow buildWindow() {
		MemorySegment hIcon = null;
		if ( this.iconFilePath != null ) {
			hIcon = Windows.LoadImageW(
					hInstance,
					this.allocateString(this.iconFilePath),
					Windows.IMAGE_ICON,
					0,
					0,
					Windows.LR_LOADFROMFILE
			);
			WNDCLASSEXW.hIcon(
					this.windowClass,
					hIcon
			);
		}
		final var windowClassNameSegment = this.allocateString(
				Optional.ofNullable(this.windowClassName)
						.orElseGet(this::toString)
		);
		WNDCLASSEXW.lpszClassName(
				this.windowClass,
				windowClassNameSegment
		);
		if ( this.dimension == null ) {
			this.dimension = new Dimension(Windows.CW_USEDEFAULT, Windows.CW_USEDEFAULT);
		} else {
			if ( this.position == null ) {
				final var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				this.position = new Point2D(
						Math.max(((int) (screenSize.width * SCALE_X) - this.dimension.width) >> 1, 0),
						Math.max(((int) (screenSize.height * SCALE_Y) - this.dimension.height) >> 1, 0)
				);
			}
		}
		if ( this.position == null ) {
			this.position = new Point2D(Windows.CW_USEDEFAULT, Windows.CW_USEDEFAULT);
		}
		if ( Windows.RegisterClassExW(this.windowClass) != 0 ) {
			final var handleWindow = Windows.CreateWindowExW(
					this.extendStyle,
					windowClassNameSegment,
					this.allocateString(
							Optional.ofNullable(this.windowName)
									.orElse("WebView2 Window")
					),
					Windows.WS_OVERLAPPEDWINDOW,
					this.position.x(),
					this.position.y(),
					this.dimension.width,
					this.dimension.height,
					MemorySegment.NULL,
					MemorySegment.NULL,
					hInstance,
					MemorySegment.NULL
			);
			if ( MemorySegment.NULL.equals(handleWindow) ) throw new NullPointerException("The handle is null.");
			else {
				final var platformWindow = new PlatformWindowImpl(this.arena, handleWindow);
				if ( platformWindow.showWindow(Windows.SW_SHOW) ) {
					throw new IllegalStateException("The window was previously visible.");
				} else {
					if ( hIcon != null ) {
						final var succeed = Windows.DestroyIcon(hIcon);
						if ( succeed ) hIcon = null;
						assert succeed;
					}
					if ( platformWindow.updateWindow() ) return platformWindow;
					else throw new IllegalStateException("UpdateWindow function fails.");
				}
			}
		} else throw new RuntimeException();
	}
}

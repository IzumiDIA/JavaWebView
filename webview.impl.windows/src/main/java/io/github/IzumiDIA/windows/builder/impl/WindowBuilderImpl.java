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

import java.awt.*;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.Optional;

public class WindowBuilderImpl extends WindowsNativeObject implements WindowBuilder<WebViewControllerImpl> {
	@SuppressWarnings({"SpellCheckingInspection", "PointlessBitwiseExpression"})
	public static final int WS_OVERLAPPEDWINDOW = 0x00000000 | 0x00C00000 | 0x00080000 | 0x00040000 | 0x00020000 | 0x00010000;
	@SuppressWarnings("SpellCheckingInspection")
	public static final int CW_USEDEFAULT = 0x80000000;
	public static final int SW_SHOW = 5;
	
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
	
	private String windowClassName;
	private int extendStyle = 0;
	private String windowName = null;
	private Point2D position = null;
	private Dimension dimension = null;
	
	
	public WindowBuilderImpl(final Arena arena) {
		super(arena);
		this.windowClass = WNDCLASSEXW.allocate(this.arena);
		WNDCLASSEXW.cbSize(this.windowClass, 80);
		WNDCLASSEXW.hInstance(
				this.windowClass, hInstance
		);
	}
	
	@Override
	public WindowBuilderImpl setStyle(final int style) {
		WNDCLASSEXW.style(this.windowClass, style);
		return this;
	}
	
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
	
	@Override
	public WindowBuilderImpl setHandleIcon(final @NotNull MemorySegment handleIcon) {
		WNDCLASSEXW.hIcon(this.windowClass, handleIcon);
		return this;
	}
	
	@Override
	public WindowBuilderImpl setHandleCursor(final @NotNull MemorySegment handleCursor) {
		WNDCLASSEXW.hCursor(this.windowClass, handleCursor);
		return this;
	}
	
	@Override
	public WindowBuilderImpl setBrushBackground(final @NotNull Color handleBrush) {
		WNDCLASSEXW.hbrBackground(this.windowClass, MemorySegment.ofAddress(handleBrush.ordinal()));
		return this;
	}
	
	@Override
	public WindowBuilderImpl setLpSzClassName(final @NotNull String windowClassName) {
		this.windowClassName = windowClassName;
		return this;
	}
	
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
	
	@Override
	public PlatformWindow buildWindow() {
		final var windowClassNameSegment = this.allocateString(
				Optional.ofNullable(this.windowClassName)
						.orElseGet(this::toString)
		);
		WNDCLASSEXW.lpszClassName(
				this.windowClass,
				windowClassNameSegment
		);
		if ( this.dimension == null ) {
			this.dimension = new Dimension(CW_USEDEFAULT, CW_USEDEFAULT);
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
			this.position = new Point2D(CW_USEDEFAULT, CW_USEDEFAULT);
		}
		if ( Windows.RegisterClassExW(windowClass) != 0 ) {
			final var handleWindow = Windows.CreateWindowExW(
					this.extendStyle,
					windowClassNameSegment,
					this.allocateString(
							Optional.ofNullable(this.windowName)
									.orElse("WebView2 Window")
					),
					WS_OVERLAPPEDWINDOW,
					this.position.x(),
					this.position.y(),
					this.dimension.width,
					this.dimension.height,
					MemorySegment.NULL,
					MemorySegment.NULL,
					hInstance,
					MemorySegment.NULL
			);
			if ( MemorySegment.NULL.equals(handleWindow) ) throw new RuntimeException();
			else {
				Windows.ShowWindow(handleWindow, SW_SHOW);
				Windows.UpdateWindow(handleWindow);
				return new PlatformWindowImpl(handleWindow);
			}
		} else throw new RuntimeException();
	}
}

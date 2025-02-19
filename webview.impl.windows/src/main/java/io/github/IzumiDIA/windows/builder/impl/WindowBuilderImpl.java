package io.github.IzumiDIA.windows.builder.impl;

import io.github.IzumiDIA.PlatformWindow;
import io.github.IzumiDIA.Point2D;
import io.github.IzumiDIA.builder.WindowBuilder;
import io.github.IzumiDIA.windows.controller.impl.WebViewControllerImpl;
import io.github.IzumiDIA.windows.impl.PlatformWindowImpl;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jetbrains.annotations.NotNull;
import org.jextract.Constants;
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
	
	private static final String DEFAULT_WINDOW_NAME = "WebView2 Window";
	private static final double SCALE_X, SCALE_Y;
	
	static {
		final var defaultTransform = GraphicsEnvironment.getLocalGraphicsEnvironment()
				                             .getDefaultScreenDevice()
				                             .getDefaultConfiguration()
				                             .getDefaultTransform();
		SCALE_X = defaultTransform.getScaleX();
		SCALE_Y = defaultTransform.getScaleY();
	}
	
	private final MemorySegment windowClass;
	
	private String windowClassName = null,
			windowName = null,
			iconFilePath = null,
			smallIconFilePath = null;
	private Point2D position = null;
	private Dimension dimension = null;
	private WebViewControllerImpl webViewController = null;
	private boolean resizable = false;
	
	public WindowBuilderImpl(final Arena arena) {
		super(arena);
		this.windowClass = WNDCLASSEXW.allocate(this.arena);
		WNDCLASSEXW.cbClsExtra(this.windowClass, 0);
		WNDCLASSEXW.cbWndExtra(this.windowClass, 0);
		WNDCLASSEXW.hbrBackground(
				this.windowClass, Windows.GetSysColorBrush(Constants.COLOR_WINDOW)
		);
		WNDCLASSEXW.lpszMenuName(
				this.windowClass,
				MemorySegment.NULL
		);
	}
	
	@Override
	public WindowBuilderImpl setResizable(final boolean resizable) {
		this.resizable = resizable;
		return this;
	}
	
	@Override
	public WindowBuilderImpl setController(final @NotNull WebViewControllerImpl windowProcedure) {
		this.webViewController = windowProcedure;
		return this;
	}
	
	@Override
	public WindowBuilderImpl setHandleIcon(final @NotNull Path iconFilePath) {
		if ( iconFilePath.isAbsolute() ) {
			if ( !iconFilePath.endsWith(".ico") ) {
				final var iconFile = iconFilePath.toFile();
				if ( iconFile.isFile() && iconFile.canRead() ) {
					this.iconFilePath = iconFilePath.toString();
					return this;
				} else throw new IllegalArgumentException("Path: [" + iconFile + "]. There is no readable file.");
			} else throw new IllegalArgumentException("The file suffix extension does not match.");
		} else throw new IllegalArgumentException("The path to the Icon file must be absolute.");
	}
	
	@Override
	public WindowBuilderImpl setClassName(final @NotNull String windowClassName) {
		this.windowClassName = windowClassName;
		return this;
	}
	
	@Override
	public WindowBuilderImpl setHandleIconSmall(final @NotNull Path smallIconFilePath) {
		if ( smallIconFilePath.isAbsolute() ) {
			if ( !smallIconFilePath.endsWith(".ico") ) {
				final var iconFile = smallIconFilePath.toFile();
				if ( iconFile.isFile() && iconFile.canRead() ) {
					this.smallIconFilePath = smallIconFilePath.toString();
					return this;
				} else throw new IllegalArgumentException("Path: [" + iconFile + "]. There is no readable file.");
			} else throw new IllegalArgumentException("The file suffix extension does not match.");
		} else throw new IllegalArgumentException("The path to the Icon file must be absolute.");
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
		if ( this.webViewController != null ) {
			WNDCLASSEXW.lpfnWndProc(
					this.windowClass,
					WNDPROC.allocate(
							this.webViewController,
							this.arena
					)
			);
			this.setHIcon();
			this.setSmallHIcon();
			final var windowClassNameSegment = this.setClassName();
			if ( this.dimension == null ) {
				this.dimension = new Dimension(Constants.CW_USEDEFAULT, Constants.CW_USEDEFAULT);
			} else if ( this.position == null ) {
				final var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				this.position = new Point2D(
						Math.max(((int) (screenSize.width * SCALE_X) - this.dimension.width) >> 1, 0),
						Math.max(((int) (screenSize.height * SCALE_Y) - this.dimension.height) >> 1, 0)
				);
			}
			if ( this.position == null ) {
				this.position = new Point2D(Constants.CW_USEDEFAULT, Constants.CW_USEDEFAULT);
			}
			if ( Windows.RegisterClassExW(this.windowClass) != 0 ) {
				final var handleWindow = Windows.CreateWindowExW(
						Constants.WS_EX_APPWINDOW,
						windowClassNameSegment,
						this.allocateString(
								Optional.ofNullable(this.windowName)
										.orElse(DEFAULT_WINDOW_NAME)
						),
						this.windowStyle() | Constants.WindowStyles.VISIBLE,
						this.position.x(),
						this.position.y(),
						this.dimension.width,
						this.dimension.height,
						MemorySegment.NULL,
						MemorySegment.NULL,
						MemorySegment.NULL
				);
				if ( MemorySegment.NULL.equals(handleWindow) ) throw new NullPointerException("The handle is null.");
				else {
					final var platformWindow = new PlatformWindowImpl(this.arena, handleWindow);
					if ( platformWindow.updateWindow() ) return platformWindow;
					else throw new IllegalStateException("UpdateWindow function fails.");
				}
			} else throw new IllegalStateException("RegisterClassExW function fails.");
		} else throw new IllegalArgumentException(new NullPointerException("The WebView Controller is null."));
	}
	
	private void setHIcon() {
		if ( this.iconFilePath != null ) {
			WNDCLASSEXW.hIcon(
					this.windowClass,
					Windows.LoadImageW(
							this.allocateString(this.iconFilePath),
							Constants.IMAGE_ICON,
							0,
							0,
							Constants.LR_LOADFROMFILE
					)
			);
		}
	}
	
	private void setSmallHIcon() {
		if ( this.smallIconFilePath != null ) {
			WNDCLASSEXW.hIconSm(
					this.windowClass,
					Windows.LoadImageW(
							this.allocateString(this.smallIconFilePath),
							Constants.IMAGE_ICON,
							0,
							0,
							Constants.LR_LOADFROMFILE
					)
			);
		}
	}
	
	private MemorySegment setClassName() {
		final var windowClassNameSegment = this.allocateString(
				Optional.ofNullable(this.windowClassName)
						.orElseGet(this::toString)
		);
		WNDCLASSEXW.lpszClassName(
				this.windowClass,
				windowClassNameSegment
		);
		return windowClassNameSegment;
	}
	
	private int windowStyle() {
		if ( this.resizable ) return Constants.WindowStyles.OVERLAPPEDWINDOW;
		else return Constants.WindowStyles.OVERLAPPEDWINDOW ^ Constants.WindowStyles.THICKFRAME;
	}
}

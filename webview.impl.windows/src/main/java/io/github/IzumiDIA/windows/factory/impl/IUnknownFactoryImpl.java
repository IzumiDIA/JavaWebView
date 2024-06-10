package io.github.IzumiDIA.windows.factory.impl;

import io.github.IzumiDIA.windows.factory.IUnknownFactory;
import io.github.IzumiDIA.windows.impl.WindowsNativeObject;
import org.jextract.IUnknownVtbl;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class IUnknownFactoryImpl<F> extends WindowsNativeObject implements IUnknownFactory<F> {
	private final Function<Arena, MemorySegment> allocator;
	private final BiFunction<F, Arena, MemorySegment> invokeSetter;
	
	public IUnknownFactoryImpl(
			final Arena arena,
			final Function<Arena, MemorySegment> allocator,
			final BiFunction<F, Arena, MemorySegment> invokeSetter
	) {
		super(arena);
		this.allocator = allocator;
		this.invokeSetter = invokeSetter;
	}
	
	@Override
	public MemorySegment wrapCallback(final F invokeFunction, final ToIntFunction<MemorySegment> queryInterfaceFunction) {
		final var comPtr = this.allocator.apply(this.arena);
		final var unknown = new IUnknownPart() {
			private final AtomicInteger handlerRefCount = new AtomicInteger(0);
			
			public int queryInterface(MemorySegment self, MemorySegment iid, MemorySegment unknown) {
				unknown.set(ValueLayout.ADDRESS, 0, self);
				return queryInterfaceFunction.applyAsInt(self);
			}
			
			public int addRef(final MemorySegment self) {
				return this.handlerRefCount.incrementAndGet();
			}
			
			public int release(final MemorySegment self) {
				return this.handlerRefCount.decrementAndGet();
			}
		};
		IUnknownVtbl.QueryInterface(comPtr, IUnknownVtbl.QueryInterface.allocate(unknown::queryInterface, this.arena));
		IUnknownVtbl.AddRef(comPtr, IUnknownVtbl.AddRef.allocate(unknown::addRef, this.arena));
		IUnknownVtbl.Release(comPtr, IUnknownVtbl.Release.allocate(unknown::release, this.arena));
		comPtr.set(
				ValueLayout.ADDRESS,
				IUnknownVtbl.sizeof(),
				this.invokeSetter.apply(invokeFunction, this.arena)
		);
		return comPtr;
	}
}

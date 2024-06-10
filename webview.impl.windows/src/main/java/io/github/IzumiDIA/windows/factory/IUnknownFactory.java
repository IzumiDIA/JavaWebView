package io.github.IzumiDIA.windows.factory;

import java.lang.foreign.MemorySegment;
import java.util.function.ToIntFunction;

@FunctionalInterface
public interface IUnknownFactory<F> {
	
	MemorySegment wrapCallback(final F invokeFunction, final ToIntFunction<MemorySegment> queryInterfaceFunction);
	
	@FunctionalInterface
	interface QueryInterface {
		int queryInterface(MemorySegment self, MemorySegment iid, MemorySegment unknown);
	}
	
	@FunctionalInterface
	interface AddRef {
		int addRef(final MemorySegment self);
	}
	
	@FunctionalInterface
	interface Release {
		int release(final MemorySegment self);
	}
	
	@FunctionalInterface
	interface Invoke {
		int invoke(MemorySegment self, int hResult, MemorySegment unknown);
	}
	
	interface IUnknownPart extends QueryInterface, AddRef, Release {
	
	}
}

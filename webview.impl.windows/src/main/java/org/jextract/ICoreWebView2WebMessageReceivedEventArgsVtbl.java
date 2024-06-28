// Generated by jextract

package org.jextract;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.util.function.*;

import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct ICoreWebView2WebMessageReceivedEventArgsVtbl {
 *     HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventArgs *, const IID *const, void **) __attribute__((stdcall));
 *     ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall));
 *     ULONG (*Release)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall));
 *     HRESULT (*get_Source)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall));
 *     HRESULT (*get_WebMessageAsJson)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall));
 *     HRESULT (*TryGetWebMessageAsString)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall));
 * }
 * }
 */
public class ICoreWebView2WebMessageReceivedEventArgsVtbl {

    ICoreWebView2WebMessageReceivedEventArgsVtbl() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        Windows.C_POINTER.withName("QueryInterface"),
        Windows.C_POINTER.withName("AddRef"),
        Windows.C_POINTER.withName("Release"),
        Windows.C_POINTER.withName("get_Source"),
        Windows.C_POINTER.withName("get_WebMessageAsJson"),
        Windows.C_POINTER.withName("TryGetWebMessageAsString")
    ).withName("ICoreWebView2WebMessageReceivedEventArgsVtbl");

    /**
     * The layout of this struct
     */
    public static GroupLayout layout() {
        return $LAYOUT;
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventArgs *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static class QueryInterface {

        QueryInterface() {
            // Should not be called directly
        }

        /**
         * The function pointer signature, expressed as a functional interface
         */
        public interface Function {
            int apply(MemorySegment _x0, MemorySegment _x1, MemorySegment _x2);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
            Windows.C_LONG,
            Windows.C_POINTER,
            Windows.C_POINTER,
            Windows.C_POINTER
        );

        /**
         * The descriptor of this function pointer
         */
        public static FunctionDescriptor descriptor() {
            return $DESC;
        }

        private static final MethodHandle UP$MH = Windows.upcallHandle(QueryInterface.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(QueryInterface.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }

        private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

        /**
         * Invoke the upcall stub {@code funcPtr}, with given parameters
         */
        public static int invoke(MemorySegment funcPtr,MemorySegment _x0, MemorySegment _x1, MemorySegment _x2) {
            try {
                return (int) DOWN$MH.invokeExact(funcPtr, _x0, _x1, _x2);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        }
    }

    private static final AddressLayout QueryInterface$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("QueryInterface"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventArgs *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static AddressLayout QueryInterface$layout() {
        return QueryInterface$LAYOUT;
    }

    private static final long QueryInterface$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventArgs *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static long QueryInterface$offset() {
        return QueryInterface$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventArgs *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static MemorySegment QueryInterface(MemorySegment struct) {
        return struct.get(QueryInterface$LAYOUT, QueryInterface$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2WebMessageReceivedEventArgs *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static void QueryInterface(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(QueryInterface$LAYOUT, QueryInterface$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static class AddRef {

        AddRef() {
            // Should not be called directly
        }

        /**
         * The function pointer signature, expressed as a functional interface
         */
        public interface Function {
            int apply(MemorySegment _x0);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
            Windows.C_LONG,
            Windows.C_POINTER
        );

        /**
         * The descriptor of this function pointer
         */
        public static FunctionDescriptor descriptor() {
            return $DESC;
        }

        private static final MethodHandle UP$MH = Windows.upcallHandle(AddRef.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(AddRef.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }

        private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

        /**
         * Invoke the upcall stub {@code funcPtr}, with given parameters
         */
        public static int invoke(MemorySegment funcPtr,MemorySegment _x0) {
            try {
                return (int) DOWN$MH.invokeExact(funcPtr, _x0);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        }
    }

    private static final AddressLayout AddRef$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("AddRef"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout AddRef$layout() {
        return AddRef$LAYOUT;
    }

    private static final long AddRef$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static long AddRef$offset() {
        return AddRef$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment AddRef(MemorySegment struct) {
        return struct.get(AddRef$LAYOUT, AddRef$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static void AddRef(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(AddRef$LAYOUT, AddRef$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static class Release {

        Release() {
            // Should not be called directly
        }

        /**
         * The function pointer signature, expressed as a functional interface
         */
        public interface Function {
            int apply(MemorySegment _x0);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
            Windows.C_LONG,
            Windows.C_POINTER
        );

        /**
         * The descriptor of this function pointer
         */
        public static FunctionDescriptor descriptor() {
            return $DESC;
        }

        private static final MethodHandle UP$MH = Windows.upcallHandle(Release.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(Release.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }

        private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

        /**
         * Invoke the upcall stub {@code funcPtr}, with given parameters
         */
        public static int invoke(MemorySegment funcPtr,MemorySegment _x0) {
            try {
                return (int) DOWN$MH.invokeExact(funcPtr, _x0);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        }
    }

    private static final AddressLayout Release$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("Release"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout Release$layout() {
        return Release$LAYOUT;
    }

    private static final long Release$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static long Release$offset() {
        return Release$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment Release(MemorySegment struct) {
        return struct.get(Release$LAYOUT, Release$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2WebMessageReceivedEventArgs *) __attribute__((stdcall))
     * }
     */
    public static void Release(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(Release$LAYOUT, Release$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*get_Source)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static class get_Source {

        get_Source() {
            // Should not be called directly
        }

        /**
         * The function pointer signature, expressed as a functional interface
         */
        public interface Function {
            int apply(MemorySegment _x0, MemorySegment _x1);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
            Windows.C_LONG,
            Windows.C_POINTER,
            Windows.C_POINTER
        );

        /**
         * The descriptor of this function pointer
         */
        public static FunctionDescriptor descriptor() {
            return $DESC;
        }

        private static final MethodHandle UP$MH = Windows.upcallHandle(get_Source.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(get_Source.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }

        private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

        /**
         * Invoke the upcall stub {@code funcPtr}, with given parameters
         */
        public static int invoke(MemorySegment funcPtr,MemorySegment _x0, MemorySegment _x1) {
            try {
                return (int) DOWN$MH.invokeExact(funcPtr, _x0, _x1);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        }
    }

    private static final AddressLayout get_Source$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("get_Source"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*get_Source)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout get_Source$layout() {
        return get_Source$LAYOUT;
    }

    private static final long get_Source$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*get_Source)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static long get_Source$offset() {
        return get_Source$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*get_Source)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment get_Source(MemorySegment struct) {
        return struct.get(get_Source$LAYOUT, get_Source$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*get_Source)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static void get_Source(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(get_Source$LAYOUT, get_Source$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*get_WebMessageAsJson)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static class get_WebMessageAsJson {

        get_WebMessageAsJson() {
            // Should not be called directly
        }

        /**
         * The function pointer signature, expressed as a functional interface
         */
        public interface Function {
            int apply(MemorySegment _x0, MemorySegment _x1);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
            Windows.C_LONG,
            Windows.C_POINTER,
            Windows.C_POINTER
        );

        /**
         * The descriptor of this function pointer
         */
        public static FunctionDescriptor descriptor() {
            return $DESC;
        }

        private static final MethodHandle UP$MH = Windows.upcallHandle(get_WebMessageAsJson.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(get_WebMessageAsJson.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }

        private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

        /**
         * Invoke the upcall stub {@code funcPtr}, with given parameters
         */
        public static int invoke(MemorySegment funcPtr,MemorySegment _x0, MemorySegment _x1) {
            try {
                return (int) DOWN$MH.invokeExact(funcPtr, _x0, _x1);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        }
    }

    private static final AddressLayout get_WebMessageAsJson$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("get_WebMessageAsJson"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*get_WebMessageAsJson)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout get_WebMessageAsJson$layout() {
        return get_WebMessageAsJson$LAYOUT;
    }

    private static final long get_WebMessageAsJson$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*get_WebMessageAsJson)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static long get_WebMessageAsJson$offset() {
        return get_WebMessageAsJson$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*get_WebMessageAsJson)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment get_WebMessageAsJson(MemorySegment struct) {
        return struct.get(get_WebMessageAsJson$LAYOUT, get_WebMessageAsJson$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*get_WebMessageAsJson)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static void get_WebMessageAsJson(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(get_WebMessageAsJson$LAYOUT, get_WebMessageAsJson$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*TryGetWebMessageAsString)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static class TryGetWebMessageAsString {

        TryGetWebMessageAsString() {
            // Should not be called directly
        }

        /**
         * The function pointer signature, expressed as a functional interface
         */
        public interface Function {
            int apply(MemorySegment _x0, MemorySegment _x1);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
            Windows.C_LONG,
            Windows.C_POINTER,
            Windows.C_POINTER
        );

        /**
         * The descriptor of this function pointer
         */
        public static FunctionDescriptor descriptor() {
            return $DESC;
        }

        private static final MethodHandle UP$MH = Windows.upcallHandle(TryGetWebMessageAsString.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(TryGetWebMessageAsString.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }

        private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

        /**
         * Invoke the upcall stub {@code funcPtr}, with given parameters
         */
        public static int invoke(MemorySegment funcPtr,MemorySegment _x0, MemorySegment _x1) {
            try {
                return (int) DOWN$MH.invokeExact(funcPtr, _x0, _x1);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        }
    }

    private static final AddressLayout TryGetWebMessageAsString$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("TryGetWebMessageAsString"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*TryGetWebMessageAsString)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout TryGetWebMessageAsString$layout() {
        return TryGetWebMessageAsString$LAYOUT;
    }

    private static final long TryGetWebMessageAsString$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*TryGetWebMessageAsString)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static long TryGetWebMessageAsString$offset() {
        return TryGetWebMessageAsString$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*TryGetWebMessageAsString)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment TryGetWebMessageAsString(MemorySegment struct) {
        return struct.get(TryGetWebMessageAsString$LAYOUT, TryGetWebMessageAsString$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*TryGetWebMessageAsString)(ICoreWebView2WebMessageReceivedEventArgs *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static void TryGetWebMessageAsString(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(TryGetWebMessageAsString$LAYOUT, TryGetWebMessageAsString$OFFSET, fieldValue);
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

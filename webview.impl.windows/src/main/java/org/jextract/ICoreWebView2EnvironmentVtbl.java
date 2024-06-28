// Generated by jextract

package org.jextract;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.util.function.*;

import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct ICoreWebView2EnvironmentVtbl {
 *     HRESULT (*QueryInterface)(ICoreWebView2Environment *, const IID *const, void **) __attribute__((stdcall));
 *     ULONG (*AddRef)(ICoreWebView2Environment *) __attribute__((stdcall));
 *     ULONG (*Release)(ICoreWebView2Environment *) __attribute__((stdcall));
 *     HRESULT (*CreateCoreWebView2Controller)(ICoreWebView2Environment *, HWND, ICoreWebView2CreateCoreWebView2ControllerCompletedHandler *) __attribute__((stdcall));
 *     HRESULT (*CreateWebResourceResponse)(ICoreWebView2Environment *, IStream *, int, LPCWSTR, LPCWSTR, ICoreWebView2WebResourceResponse **) __attribute__((stdcall));
 *     HRESULT (*get_BrowserVersionString)(ICoreWebView2Environment *, LPWSTR *) __attribute__((stdcall));
 *     HRESULT (*add_NewBrowserVersionAvailable)(ICoreWebView2Environment *, ICoreWebView2NewBrowserVersionAvailableEventHandler *, EventRegistrationToken *) __attribute__((stdcall));
 *     HRESULT (*remove_NewBrowserVersionAvailable)(ICoreWebView2Environment *, EventRegistrationToken) __attribute__((stdcall));
 * }
 * }
 */
public class ICoreWebView2EnvironmentVtbl {

    ICoreWebView2EnvironmentVtbl() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        Windows.C_POINTER.withName("QueryInterface"),
        Windows.C_POINTER.withName("AddRef"),
        Windows.C_POINTER.withName("Release"),
        Windows.C_POINTER.withName("CreateCoreWebView2Controller"),
        Windows.C_POINTER.withName("CreateWebResourceResponse"),
        Windows.C_POINTER.withName("get_BrowserVersionString"),
        Windows.C_POINTER.withName("add_NewBrowserVersionAvailable"),
        Windows.C_POINTER.withName("remove_NewBrowserVersionAvailable")
    ).withName("ICoreWebView2EnvironmentVtbl");

    /**
     * The layout of this struct
     */
    public static GroupLayout layout() {
        return $LAYOUT;
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2Environment *, const IID *const, void **) __attribute__((stdcall))
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
     * HRESULT (*QueryInterface)(ICoreWebView2Environment *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static AddressLayout QueryInterface$layout() {
        return QueryInterface$LAYOUT;
    }

    private static final long QueryInterface$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2Environment *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static long QueryInterface$offset() {
        return QueryInterface$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2Environment *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static MemorySegment QueryInterface(MemorySegment struct) {
        return struct.get(QueryInterface$LAYOUT, QueryInterface$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*QueryInterface)(ICoreWebView2Environment *, const IID *const, void **) __attribute__((stdcall))
     * }
     */
    public static void QueryInterface(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(QueryInterface$LAYOUT, QueryInterface$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2Environment *) __attribute__((stdcall))
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
     * ULONG (*AddRef)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout AddRef$layout() {
        return AddRef$LAYOUT;
    }

    private static final long AddRef$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static long AddRef$offset() {
        return AddRef$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment AddRef(MemorySegment struct) {
        return struct.get(AddRef$LAYOUT, AddRef$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ULONG (*AddRef)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static void AddRef(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(AddRef$LAYOUT, AddRef$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2Environment *) __attribute__((stdcall))
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
     * ULONG (*Release)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout Release$layout() {
        return Release$LAYOUT;
    }

    private static final long Release$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static long Release$offset() {
        return Release$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment Release(MemorySegment struct) {
        return struct.get(Release$LAYOUT, Release$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * ULONG (*Release)(ICoreWebView2Environment *) __attribute__((stdcall))
     * }
     */
    public static void Release(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(Release$LAYOUT, Release$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*CreateCoreWebView2Controller)(ICoreWebView2Environment *, HWND, ICoreWebView2CreateCoreWebView2ControllerCompletedHandler *) __attribute__((stdcall))
     * }
     */
    public static class CreateCoreWebView2Controller {

        CreateCoreWebView2Controller() {
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

        private static final MethodHandle UP$MH = Windows.upcallHandle(CreateCoreWebView2Controller.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(CreateCoreWebView2Controller.Function fi, Arena arena) {
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

    private static final AddressLayout CreateCoreWebView2Controller$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("CreateCoreWebView2Controller"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*CreateCoreWebView2Controller)(ICoreWebView2Environment *, HWND, ICoreWebView2CreateCoreWebView2ControllerCompletedHandler *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout CreateCoreWebView2Controller$layout() {
        return CreateCoreWebView2Controller$LAYOUT;
    }

    private static final long CreateCoreWebView2Controller$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*CreateCoreWebView2Controller)(ICoreWebView2Environment *, HWND, ICoreWebView2CreateCoreWebView2ControllerCompletedHandler *) __attribute__((stdcall))
     * }
     */
    public static long CreateCoreWebView2Controller$offset() {
        return CreateCoreWebView2Controller$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*CreateCoreWebView2Controller)(ICoreWebView2Environment *, HWND, ICoreWebView2CreateCoreWebView2ControllerCompletedHandler *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment CreateCoreWebView2Controller(MemorySegment struct) {
        return struct.get(CreateCoreWebView2Controller$LAYOUT, CreateCoreWebView2Controller$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*CreateCoreWebView2Controller)(ICoreWebView2Environment *, HWND, ICoreWebView2CreateCoreWebView2ControllerCompletedHandler *) __attribute__((stdcall))
     * }
     */
    public static void CreateCoreWebView2Controller(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(CreateCoreWebView2Controller$LAYOUT, CreateCoreWebView2Controller$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*CreateWebResourceResponse)(ICoreWebView2Environment *, IStream *, int, LPCWSTR, LPCWSTR, ICoreWebView2WebResourceResponse **) __attribute__((stdcall))
     * }
     */
    public static class CreateWebResourceResponse {

        CreateWebResourceResponse() {
            // Should not be called directly
        }

        /**
         * The function pointer signature, expressed as a functional interface
         */
        public interface Function {
            int apply(MemorySegment _x0, MemorySegment _x1, int _x2, MemorySegment _x3, MemorySegment _x4, MemorySegment _x5);
        }

        private static final FunctionDescriptor $DESC = FunctionDescriptor.of(
            Windows.C_LONG,
            Windows.C_POINTER,
            Windows.C_POINTER,
            Windows.C_INT,
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

        private static final MethodHandle UP$MH = Windows.upcallHandle(CreateWebResourceResponse.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(CreateWebResourceResponse.Function fi, Arena arena) {
            return Linker.nativeLinker().upcallStub(UP$MH.bindTo(fi), $DESC, arena);
        }

        private static final MethodHandle DOWN$MH = Linker.nativeLinker().downcallHandle($DESC);

        /**
         * Invoke the upcall stub {@code funcPtr}, with given parameters
         */
        public static int invoke(MemorySegment funcPtr,MemorySegment _x0, MemorySegment _x1, int _x2, MemorySegment _x3, MemorySegment _x4, MemorySegment _x5) {
            try {
                return (int) DOWN$MH.invokeExact(funcPtr, _x0, _x1, _x2, _x3, _x4, _x5);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        }
    }

    private static final AddressLayout CreateWebResourceResponse$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("CreateWebResourceResponse"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*CreateWebResourceResponse)(ICoreWebView2Environment *, IStream *, int, LPCWSTR, LPCWSTR, ICoreWebView2WebResourceResponse **) __attribute__((stdcall))
     * }
     */
    public static AddressLayout CreateWebResourceResponse$layout() {
        return CreateWebResourceResponse$LAYOUT;
    }

    private static final long CreateWebResourceResponse$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*CreateWebResourceResponse)(ICoreWebView2Environment *, IStream *, int, LPCWSTR, LPCWSTR, ICoreWebView2WebResourceResponse **) __attribute__((stdcall))
     * }
     */
    public static long CreateWebResourceResponse$offset() {
        return CreateWebResourceResponse$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*CreateWebResourceResponse)(ICoreWebView2Environment *, IStream *, int, LPCWSTR, LPCWSTR, ICoreWebView2WebResourceResponse **) __attribute__((stdcall))
     * }
     */
    public static MemorySegment CreateWebResourceResponse(MemorySegment struct) {
        return struct.get(CreateWebResourceResponse$LAYOUT, CreateWebResourceResponse$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*CreateWebResourceResponse)(ICoreWebView2Environment *, IStream *, int, LPCWSTR, LPCWSTR, ICoreWebView2WebResourceResponse **) __attribute__((stdcall))
     * }
     */
    public static void CreateWebResourceResponse(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(CreateWebResourceResponse$LAYOUT, CreateWebResourceResponse$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*get_BrowserVersionString)(ICoreWebView2Environment *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static class get_BrowserVersionString {

        get_BrowserVersionString() {
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

        private static final MethodHandle UP$MH = Windows.upcallHandle(get_BrowserVersionString.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(get_BrowserVersionString.Function fi, Arena arena) {
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

    private static final AddressLayout get_BrowserVersionString$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("get_BrowserVersionString"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*get_BrowserVersionString)(ICoreWebView2Environment *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout get_BrowserVersionString$layout() {
        return get_BrowserVersionString$LAYOUT;
    }

    private static final long get_BrowserVersionString$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*get_BrowserVersionString)(ICoreWebView2Environment *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static long get_BrowserVersionString$offset() {
        return get_BrowserVersionString$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*get_BrowserVersionString)(ICoreWebView2Environment *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment get_BrowserVersionString(MemorySegment struct) {
        return struct.get(get_BrowserVersionString$LAYOUT, get_BrowserVersionString$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*get_BrowserVersionString)(ICoreWebView2Environment *, LPWSTR *) __attribute__((stdcall))
     * }
     */
    public static void get_BrowserVersionString(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(get_BrowserVersionString$LAYOUT, get_BrowserVersionString$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*add_NewBrowserVersionAvailable)(ICoreWebView2Environment *, ICoreWebView2NewBrowserVersionAvailableEventHandler *, EventRegistrationToken *) __attribute__((stdcall))
     * }
     */
    public static class add_NewBrowserVersionAvailable {

        add_NewBrowserVersionAvailable() {
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

        private static final MethodHandle UP$MH = Windows.upcallHandle(add_NewBrowserVersionAvailable.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(add_NewBrowserVersionAvailable.Function fi, Arena arena) {
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

    private static final AddressLayout add_NewBrowserVersionAvailable$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("add_NewBrowserVersionAvailable"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*add_NewBrowserVersionAvailable)(ICoreWebView2Environment *, ICoreWebView2NewBrowserVersionAvailableEventHandler *, EventRegistrationToken *) __attribute__((stdcall))
     * }
     */
    public static AddressLayout add_NewBrowserVersionAvailable$layout() {
        return add_NewBrowserVersionAvailable$LAYOUT;
    }

    private static final long add_NewBrowserVersionAvailable$OFFSET = 48;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*add_NewBrowserVersionAvailable)(ICoreWebView2Environment *, ICoreWebView2NewBrowserVersionAvailableEventHandler *, EventRegistrationToken *) __attribute__((stdcall))
     * }
     */
    public static long add_NewBrowserVersionAvailable$offset() {
        return add_NewBrowserVersionAvailable$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*add_NewBrowserVersionAvailable)(ICoreWebView2Environment *, ICoreWebView2NewBrowserVersionAvailableEventHandler *, EventRegistrationToken *) __attribute__((stdcall))
     * }
     */
    public static MemorySegment add_NewBrowserVersionAvailable(MemorySegment struct) {
        return struct.get(add_NewBrowserVersionAvailable$LAYOUT, add_NewBrowserVersionAvailable$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*add_NewBrowserVersionAvailable)(ICoreWebView2Environment *, ICoreWebView2NewBrowserVersionAvailableEventHandler *, EventRegistrationToken *) __attribute__((stdcall))
     * }
     */
    public static void add_NewBrowserVersionAvailable(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(add_NewBrowserVersionAvailable$LAYOUT, add_NewBrowserVersionAvailable$OFFSET, fieldValue);
    }

    /**
     * {@snippet lang=c :
     * HRESULT (*remove_NewBrowserVersionAvailable)(ICoreWebView2Environment *, EventRegistrationToken) __attribute__((stdcall))
     * }
     */
    public static class remove_NewBrowserVersionAvailable {

        remove_NewBrowserVersionAvailable() {
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
            EventRegistrationToken.layout()
        );

        /**
         * The descriptor of this function pointer
         */
        public static FunctionDescriptor descriptor() {
            return $DESC;
        }

        private static final MethodHandle UP$MH = Windows.upcallHandle(remove_NewBrowserVersionAvailable.Function.class, "apply", $DESC);

        /**
         * Allocates a new upcall stub, whose implementation is defined by {@code fi}.
         * The lifetime of the returned segment is managed by {@code arena}
         */
        public static MemorySegment allocate(remove_NewBrowserVersionAvailable.Function fi, Arena arena) {
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

    private static final AddressLayout remove_NewBrowserVersionAvailable$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("remove_NewBrowserVersionAvailable"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * HRESULT (*remove_NewBrowserVersionAvailable)(ICoreWebView2Environment *, EventRegistrationToken) __attribute__((stdcall))
     * }
     */
    public static AddressLayout remove_NewBrowserVersionAvailable$layout() {
        return remove_NewBrowserVersionAvailable$LAYOUT;
    }

    private static final long remove_NewBrowserVersionAvailable$OFFSET = 56;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * HRESULT (*remove_NewBrowserVersionAvailable)(ICoreWebView2Environment *, EventRegistrationToken) __attribute__((stdcall))
     * }
     */
    public static long remove_NewBrowserVersionAvailable$offset() {
        return remove_NewBrowserVersionAvailable$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * HRESULT (*remove_NewBrowserVersionAvailable)(ICoreWebView2Environment *, EventRegistrationToken) __attribute__((stdcall))
     * }
     */
    public static MemorySegment remove_NewBrowserVersionAvailable(MemorySegment struct) {
        return struct.get(remove_NewBrowserVersionAvailable$LAYOUT, remove_NewBrowserVersionAvailable$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * HRESULT (*remove_NewBrowserVersionAvailable)(ICoreWebView2Environment *, EventRegistrationToken) __attribute__((stdcall))
     * }
     */
    public static void remove_NewBrowserVersionAvailable(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(remove_NewBrowserVersionAvailable$LAYOUT, remove_NewBrowserVersionAvailable$OFFSET, fieldValue);
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

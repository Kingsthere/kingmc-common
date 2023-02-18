package kingmc.common;

public class OpenAPI {
    private OpenAPI() {
        // This part of code shouldn't execute
        throw new UnsupportedOperationException("");
    }

    /**
     * The main [ClassLoader] of the application to load classes in the
     * application that driving the kingmc api, should not be null after
     * application loaded.
     *
     * @since 0.0.1
     */
    private static ClassLoader classLoader = null;

    /**
     * Support the class loader manually to kingmc api, the support
     * class loader should not is null
     *
     * @param classLoader0 the class loader to manually support
     * @since 0.0.1
     */
    public static void supportClassLoader(ClassLoader classLoader0) {
        classLoader = classLoader0;
    }

    /**
     * Get the current class loader that loading the current application, you can
     * use [.supportClassLoader] to set the current application
     * class loader
     *
     * @return the current class loader
     */
    public static ClassLoader classLoader() {
        return classLoader;
    }
}

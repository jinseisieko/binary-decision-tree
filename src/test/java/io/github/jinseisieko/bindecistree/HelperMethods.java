package io.github.jinseisieko.bindecistree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;

class HelperMethods {
    public static void assertThrowsWithNonEmptyMessage(Class<? extends Throwable> expectedType, Executable executable) {
        Throwable exception = assertThrows(expectedType, executable);
        assertNotNull(exception);
        assertFalse(exception.getMessage() == null || exception.getMessage().isEmpty(),
                "Exception message should not be null or empty");
    }
}
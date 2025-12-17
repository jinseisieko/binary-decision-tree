package io.github.jinseisieko.decisiontree;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.function.Executable;

class TestUtilities {
    public static void assertThrowsWithNonEmptyMessage(Class<? extends Throwable> expectedType, Executable executable) {
        Throwable exception = assertThrows(expectedType, executable);
        assertNotNull(exception);
        assertFalse(exception.getMessage() == null || exception.getMessage().isEmpty(),
                "Exception message should not be null or empty");
    }

    public static <T> Predicate<T> alwaysTrue() {
        return t -> true;
    }

    public static <T> Function<T, Integer> alwaysZero() {
        return t -> 0;
    }
    
    static class Counter {
        private int value;

        public Counter(int value) {
            this.value = value;
        }

        public Counter() {
            this(0);
        }
 
        public void increment() {
            value++;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Counter{" + value + "}";
        }
    }

    public static <I, O> DecisionTreeBuilder<I, O> buildCompleteSubtree(
            DecisionTreeBuilder<I, O> builder,
            Predicate<I> condition,
            O leafValue,
            int remainingDepth) {
        if (remainingDepth == 1) {
            builder.insertCondition(condition)
                .goToTrueChild()
                .insertOutcome(leafValue)
                .goToSibling()
                .insertOutcome(leafValue)
                .goToParent();
        } else {
            builder.insertCondition(condition);
            builder.goToTrueChild();
            buildCompleteSubtree(builder, condition, leafValue, remainingDepth - 1);
            builder.goToSibling();
            buildCompleteSubtree(builder, condition, leafValue, remainingDepth - 1);
            builder.goToParent();
        }
        return builder;
    }

    public static <I, O> DecisionTreeBuilder<I, O> buildCompleteSubtree(
            DecisionTreeBuilder<I, O> builder,
            Predicate<I> condition,
            Function<I, O> leafFunction,
            int remainingDepth) {
        if (remainingDepth == 1) {
            builder.insertCondition(condition)
                .goToTrueChild()
                .insertOutcome(leafFunction)
                .goToSibling()
                .insertOutcome(leafFunction)
                .goToParent();
        } else {
            builder.insertCondition(condition);
            builder.goToTrueChild();
            buildCompleteSubtree(builder, condition, leafFunction, remainingDepth - 1);
            builder.goToSibling();
            buildCompleteSubtree(builder, condition, leafFunction, remainingDepth - 1);
            builder.goToParent();
        }
        return builder;
    }
}

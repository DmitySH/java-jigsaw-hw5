package bse202.sda.jigsaw.interfaces;

/**
 * Delegate with 2 consumers.
 *
 * @param <T> first consumer type.
 * @param <V> second consumer type.
 */
@FunctionalInterface
public interface TwoConsumer<T, V> {
    void execute(T first, V second);
}

package bse202.sda.jigsaw.interfaces;

@FunctionalInterface
public interface TwoConsumer<T, V> {
    void execute(T first, V second);
}

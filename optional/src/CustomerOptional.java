import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public final class CustomerOptional<T> {
    private final T value;

    CustomerOptional(T value) {
        this.value = value;
    }

    /**
     * 简单集合不为空判断不为空 则执行具体实现方法体
     *
     * @param collection
     * @param consumer
     * @param <T>
     */
    public static <T> void ofNullable(Collection<T> collection, Consumer<Collection<T>> consumer) {
        if (collection == null && collection.isEmpty()) {
            throw new NullPointerException();
        }
        consumer.accept(collection);
    }

    /**
     * 不为空判断的foreach
     *
     * @param collection
     * @param consumer
     * @param <T>
     */
    public static <T> void ofNullableForeach(Collection<T> collection, Consumer<T> consumer) {
        if (collection == null && collection.isEmpty()) {
            throw new NullPointerException();
        }
        for (T t : collection) {
            consumer.accept(t);
        }
    }
}
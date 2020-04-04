import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * 测试函数式接口
 *
 * @author hht
 * @date 2020/4/3 17:42
 */
public class TestFunctionInterface {
    public static void main(String args[]) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //过滤偶数
        filter(list, n -> (n & 1) == 0).forEach(System.out::println);

        System.out.println("----------------------------");
        //过滤偶数且大于5
        filter(list, n -> (n & 1) == 0, n -> n > 5).forEach(System.out::println);

    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> newList = new ArrayList<>();
        list.forEach(n -> {
            if (predicate.test(n)) {
                newList.add(n);
            }
        });
        return newList;
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate, Predicate<T> predicate2) {
        List<T> newList = new ArrayList<>();
        list.forEach(n -> {
            if (predicate.and(predicate2).test(n)) {
                newList.add(n);
            }
        });
        return newList;
    }
}

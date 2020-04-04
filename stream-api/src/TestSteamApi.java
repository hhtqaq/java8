import java.util.Arrays;
import java.util.List;

/**
 * 测试Stream
 *
 * @author hht
 * @date 2020/4/4 16:40
 */
public class TestSteamApi {
    public static void main(String[] args) {

        //jdk 8之前 统计正数的个数
        List<Integer> numbers = Arrays.asList(-1, -2, 0, 4, 5, 6, 7, 8, 9, 10);
        long count = 0;
        for (Integer number : numbers) {
            if (number > 0) {
                count++;
            }
        }
        System.out.println("Positive count: " + count);
        //jdk8 之后stream
        System.out.println(numbers.parallelStream().filter(n -> n > 0).count());


        //numbers.stream().map(n -> Math.abs(n)).forEach(n -> System.out.println("Element abs: " + n));

        numbers.stream().map(n -> {
            System.out.println(n);
            return n;
        }).forEach(n-> System.out.println(n));

    }
}

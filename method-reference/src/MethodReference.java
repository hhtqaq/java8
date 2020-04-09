import java.util.Arrays;
import java.util.List;

/**
 * @author hht
 * @date 2020/4/7 10:29
 */
public class MethodReference {

    public static void main(String[] args) {
        List<String> strList = Arrays.asList(new String[]{"a", "c", "b"});

        //没有使用方法引用之前
        strList.stream().sorted((s1, s2) -> s1.compareToIgnoreCase(s2)).forEach(s -> System.out.println(s));
        //使用方法引用
        strList.stream().sorted(String::compareToIgnoreCase).forEach(System.out::println);
    }
}

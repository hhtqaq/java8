import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Test {
    public static void main(String[] args) {
        //lambda表达式使用：
        Arrays.asList(new String[]{"a", "c", "b"}).stream().forEach(s -> Test.println(s));
        //静态方法引用：
        Arrays.asList(new String[]{"a", "c", "b"}).stream().forEach(Test::println);

        Supplier<List<String>> supplier = ArrayList::new;
        supplier.get();
    }

    public static void println(String s) {
        System.out.println(s);
    }
}
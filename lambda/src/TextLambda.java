import java.util.Arrays;
import java.util.List;

/**
 * @author hht
 * @date 2020/4/3 10:27
 */
public class TextLambda {

    /**
     * 测试runnable lambda
     */
    public static void Runnable() {
        //java8 之前
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello java8");
            }
        }).start();

        //java8之后  只需要关注入参和方法体
        new Thread(() -> System.out.println("hello java8")).start();
    }

    /**
     * 测试list for循环  lambda
     */
    public static void list_for() {
        //java8 之前
        List<Integer> features = Arrays.asList(1, 2);
        for (Integer feature : features) {
            System.out.println(feature);
        }
        //java8 之后
        List<Integer> features2 = Arrays.asList(1,2);
        features2.forEach(n -> System.out.println(n));
    }

    public static void main(String[] args) {


    }
}

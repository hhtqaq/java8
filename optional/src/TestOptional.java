import java.util.*;
import java.util.function.Consumer;

/**
 * @author hht
 * @date 2020/4/8 10:20
 */
public class TestOptional {


    static class Student {
        private String username;
        private int score;

        public Student(String username, int score) {
            this.username = username;
            this.score = score;
        }

        public String getUsername() {
            return this.username;
        }

        public int getScore() {
            return this.score;
        }

    }

    public static void main(String[] args) {

        Student student = new Student("zhangsan", 100);
        Student student1 = null;
        System.out.println(getLambdaName(student1));
        System.out.println(getLambdaName(student));
        System.out.println(getName(student));
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("111");
        iter(list);
        String aaa = "aasdf$_$dsadad";
        System.out.println(aaa.split("\\$_\\$")[0]);
    }

    public static String getName(Student student) {
        if (student != null) {
            return student.getUsername();
        } else {
            return "unknown";
        }
    }

    public static String getLambdaName(Student student) {
        return Optional.ofNullable(student).map(stu -> stu.getUsername()).orElse("unknown");

    }

    public static void iter(List<String> list) {
        CustomerOptional.ofNullable(list, l -> l.forEach(System.out::println));
        CustomerOptional.ofNullableForeach(list, System.out::println);
    }


}

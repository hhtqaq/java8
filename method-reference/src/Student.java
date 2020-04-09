/**
 * @author hht
 * @date 2020/4/7 18:05
 */
public class Student {
    private String username;
    private int age;

    public void setStudent(String username, int age) {
        this.age = age;
        this.username = username;
        System.out.println("你在干什么");
    }

    public static void main(String[] args) {
        //lambda
        Test test2 = (student, username, age) -> student.setStudent("你好", 11);
        //方法引用
        Test test = Student::setStudent;
        test.set(new Student(), "aaa", 10);
    }

    @FunctionalInterface
    interface Test {
        void set(Student student, String username, int age);
    }
}

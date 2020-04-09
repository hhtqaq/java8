
## 方法引用语法

### 一、简介
方法引用是java8的新特性之一， 可以直接引用已有Java类或对象的方法或构造器。方法引用与lambda表达式结合使用，可以进一步简化代码。


    public static void main(String[] args) {
        List<String> strList = Arrays.asList(new String[]{"a", "c", "b"});

        //没有使用方法引用之前
        strList.stream().sorted((s1, s2) -> s1.compareToIgnoreCase(s2)).forEach(s -> System.out.println(s));
        //使用方法引用
        strList.stream().sorted(String::compareToIgnoreCase).forEach(System.out::println);
    }
上述程序上面生成一个Stream流，对流中的字符串进行排序并遍历打印。程序中采用lambda表达式的方式代替匿名类简化了代码，然而代码中两处lambda表达式都仅仅调用的是一个已存在的方法：String.compareToIgnoreCase、System.out.println，这种情况可以用方法引用来简化

对比一下可以看到，上述程序下面那行分别采用了类的任意对象的实例方法引用和特定对象的实例方法引用两种方法引用形式（下一章会讲述），采用方法引用的方式可以简化lambda表达式的写法。
### 二、方法引用的具体使用
java8方法引用有四种形式：

- 静态方法引用　　　　　　　：　 　ClassName :: staticMethodName
- 构造器引用　　　　　　　　：　 　ClassName :: new
- 类的任意对象的实例方法引用：　 　ClassName :: instanceMethodName
- 特定对象的实例方法引用　　：　 　object :: instanceMethodName

lambda表达式可用方法引用代替的场景可以简要概括为：lambda表达式的方法体仅包含一个表达式，且该表达式仅调用了一个已经存在的方法。方法引用的通用特性：方法引用所使用方法的入参和返回值与lambda表达式实现的函数式接口的入参和返回值一致。

####2.1  静态方法引用
    静态方法引用的语法格式为： 类名::静态方法名 ，如
System.out::println 等价于lambda表达式 s -> System.out.println(s) ，代码示例：

	public class Test {
	    public static void main(String[] args) {
	        //lambda表达式使用：
	        Arrays.asList(new String[]{"a", "c", "b"}).stream().forEach(s -> Test.println(s));
	        //静态方法引用：
	        Arrays.asList(new String[]{"a", "c", "b"}).stream().forEach(Test::println);
	    }
	
	    public static void println(String s) {
	        System.out.println(s);
	    }
	}   

静态方法引用适用于lambda表达式主体中仅仅调用了某个类的静态方法的情形。

####2.2  构造器引用
 构造器引用的语法格式为： 类名::new ，如() -> new ArrayList<String>() 等价于 ArrayList<String>::new，代码示例：
	
	Supplier<List<String>> supplier1= () -> new  ArrayList<String>();
等价于
	
	Supplier<List<String>> supplier = ArrayList<String>::new;

构造器引用适用于lambda表达式主体中仅仅调用了某个类的构造函数返回实例的场景。
####2.3 类的任意对象的实例方法引用
类的任意对象的实例方法引用的语法格式为： 类名::实例方法名 ， 这种方法引用相对比较复杂，我们来看示例

- 示例1

		Arrays.sort(strs,(s1,s2)->s1.compareToIgnoreCase(s2));
等价于
		
		Arrays.sort(strs, String::compareToIgnoreCase);

上述示例中，strs为一个String数组，lambda表达式(s1,s2)->s1.compareToIgnoreCase(s2)实现函数式接口的是Comparator接口, 我们看下jdk8中Comparator接口的源码（截取部分）：
	
	@FunctionalInterface 
	public interface Comparator<T> {
      int compare(T o1, T o2);
	}
			

而String类的compareToIgnoreCase方法源码为：

    public int compareToIgnoreCase(String str) {
		return CASE_INSENSITIVE_ORDER.compare(this, str);
    }

可以发现函数式接口Comparator<String>的compare方法比String类的compareToIgnoreCase方法多了一个String类型的入参。看到这里对类的任意对象的实例方法引用的使用可能似懂非懂，下面我们看一个自己实现一个类的任意对象的实例方法引用的示例（示例2）。

- 示例2
	
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

看完上述代码，我们可以总结出类的任意对象的实例方法引用的特性为：

- 方法引用的通用特性：方法引用所使用方法的入参和返回值与lambda表达式实现的函数式接口的入参和返回值一致；
- lambda表达式的第一个入参为实例方法的调用者，后面的入参与实例方法的入参一致。

### 三、总结
     方法引用使用运算符::连接类(或对象)与方法名称(或new)实现在特定场景下lambda表达式的简化表示，使用时要注意方法引用的使用场景及各种方法引用的特性。使用方法引用的好处是能够更进一步简化代码编写，使代码更简洁。
     然而作者认为，方法引用代替lambda表达式对代码的简化程度远远没有lambda表达式代替匿名类的简化程度大， 有时反而增加了代码的理解难度(如2.3节：类的任意对象的实例方法引用)，且使用场景的局限性不利于增加或修改代码，个人认为有时没有必要刻意使用方法引用~

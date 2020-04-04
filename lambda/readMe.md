## lambda语法
lambda的基本语法：

    (parameters) -> expression
    or
    (parameters) ->{ statements; }

lambda表达式的特性：

- 可选类型声明: 无需声明参数类型，编译器即可自动识别
- 可选的参数圆括号: 仅有一个参数时圆括号可以省略
- 可选的大括号：主体只包含一个语句时可省略大括号
- 可选的返回关键字：主体只包含一个表达式返回值并省略大括号时，编译器会自动return返回值；有大括号时，需要显式指定表达式return了一个数值

特性示例：

    //1、无参数，返回值1
    () -> 1 
    //2、无参数，无返回值
    () -> System.out.print("Java8 lambda.");
    //3、1个参数，参数类型为数字，返回值为其值的5倍
    x ->  5 * x 
    //4、2个参数，参数类型均为数字，返回值为其差值
    (x, y) -> x - y
    //5、2个参数，指定参数类型均为int型，返回值为其差值 
    (int x, int y) -> x - y  
    //6、1个参数，指定参数类型为String ，无返回值
    (String str) -> System.out.print(str)
    
 ## java8 lambda使用示例
 
 ###  1）java Runnable接口的lambda实现
用lambdah代替匿名类是java8中lambda的常用形式，本文以开发同学经常使用的Runnable接口匿名类为示例，演示如何用lambda表达式来代替匿名类
    
    //java8 之前
    new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("hello java8");
        }
    }).start();

    //java8之后  只需要关注入参和方法体
    new Thread(() -> System.out.println("hello java8")).start();
    
runnable接口内部  有一个函数式接口的注解，也满足只有一个抽象方法。
    
    @FunctionalInterface
    public interface Runnable {
        public abstract void run();
    }
    
###   java List迭代的lambda实现

开发同学经常会使用到集合类，并对集合类对象进行迭代，以实现业务逻辑。
 java8中，集合类的顶层接口java.lang.Iterable定义了一个forEach方法：
 
    default void forEach(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            for (T t : this) {
                action.accept(t);
            }
        }
forEach方法可以迭代集合的所有对象，其参数为Consumer对象，Consumer类位于java.util.function包下，我们看下其定义：
   
    @FunctionalInterface
    public interface Consumer<T> {
        void accept(T t);
        
        default Consumer<T> andThen(Consumer<? super T> after) {
            Objects.requireNonNull(after);
            return (T t) -> { accept(t); after.accept(t); };
        }
    }
同样满足函数式接口定义，只有一个抽象方法。

示例：

- java8之前
    
      List<Integer> features = Arrays.asList(1,2);
          for (Integer feature : features) {
              System.out.println(feature);
           }
 
 - java8之后 
 
        List<Integer> features = Arrays.asList(1,2);
            features.forEach(n -> System.out.println(n));                
 
 根据Consumer这个函数式接口，我们仍然只需要关注入参和方法体。也就是上面的n和打印。
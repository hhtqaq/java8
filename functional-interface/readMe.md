##函数式接口
函数式接口(Functional Interface)是java8新增的特性，它是一个有且仅有一个抽象方法，但是可以有多个非抽象方法的接口。函数式接口可以被隐式转换为lambda表达式。
JDK1.8新增加的函数式接口有java.util.function包下的接口

### 典型函数式接口的使用
#### 1 Predicate接口
- Predicate接口的基本用法

Predicate接口适合用于过滤，测试对象是否符合某个条件，Predicate接口源码如下:

    @FunctionalInterface
    public interface Predicate<T> {
    
        boolean test(T t);
    
        default Predicate<T> and(Predicate<? super T> other) {
            Objects.requireNonNull(other);
            return (t) -> test(t) && other.test(t);
        }
    
        default Predicate<T> negate() {
            return (t) -> !test(t);
        }
    
        default Predicate<T> or(Predicate<? super T> other) {
            Objects.requireNonNull(other);
            return (t) -> test(t) || other.test(t);
        }
    
        static <T> Predicate<T> isEqual(Object targetRef) {
            return (null == targetRef)
                    ? Objects::isNull
                    : object -> targetRef.equals(object);
        }
    }
        
 可以看到，Predicate接口待实现的唯一抽象方法是 boolean test(T t) 方法。我们用Predicate接口实现从整数型数组中过滤出偶数
    
    public static void main(String args[]) {
            List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
            //过滤偶数
            filter(list, n -> (n & 1) == 0).forEach(System.out::print);
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
- Predicate接口的进阶用法

我们再看上一节提到的Predicate接口的源码，发现它有三个default关键字定义的方法，分别为and()、negate()、or()三个方法，顾名思义，它们类似于逻辑操作&&、!、||，用于生成新的Predicate对象。
-  - 过滤偶数且大于5
        
         //过滤偶数且大于5   这里有个位运算的小技巧
          filter(list, n -> (n & 1) == 0, n -> n > 5).forEach(System.out::println);
          
         public static <T> List<T> filter(List<T> list, Predicate<T> predicate, Predicate<T> predicate2) {
             List<T> newList = new ArrayList<>();
             list.forEach(n -> {
                 if (predicate.and(predicate2).test(n)) {
                     newList.add(n);
                 }
             });
             return newList;
         } 

其实加多少个都可以，我们现在的思维只需要关注入参和方法体，也就是箭头前后。


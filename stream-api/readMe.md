## Stream API
###一、简介
     java8新添加了一个特性：流Stream。Stream让开发者能够以一种声明的方式处理数据源（集合、数组等），它专注于对数据源进行各种高效的聚合操作（aggregate operation）和大批量数据操作 (bulk data operation)。

    Stream API将处理的数据源看做一种Stream（流），Stream（流）在Pipeline（管道）中传输和运算，支持的运算包含筛选、排序、聚合等，当到达终点后便得到最终的处理结果。

**几个关键概念**：

 - **元素** Stream是一个来自数据源的元素队列，Stream本身并不存储元素。
- **数据源**（即Stream的来源）包含集合、数组、I/O channel、generator（发生器）等。
- **聚合操作** 类似SQL中的filter、map、find、match、sorted等操作
- **管道运算** Stream在Pipeline中运算后返回Stream对象本身，这样多个操作串联成一个Pipeline，并形成fluent风格的代码。这种方式可以优化操作，如延迟执行(laziness)和短路( short-circuiting)。
- **内部迭代** 不同于java8以前对集合的遍历方式（外部迭代），Stream API采用访问者模式（Visitor）实现了内部迭代。
- **并行运算** Stream API支持串行（stream() ）或并行（parallelStream() ）的两种操作方式。

**Stream API的特点：**

- Stream API的使用和同样是java8新特性的lambda表达式密不可分，可以大大提高编码效率和代码可读性。
- Stream API提供串行和并行两种操作，其中并行操作能发挥多核处理器的优势，使用fork/join的方式进行并行操作以提高运行速度。
- Stream API进行并行操作无需编写多线程代码即可写出高效的并发程序，且通常可避免多线程代码出错的问题。

### 二、简单示例 ###

统计list中满足条件的数量

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
    }
可以看到，上例中，使用filter()方法对数组进行了过滤，使用count()方法对过滤后的数组进行了大小统计，且使parallelStream()方法为集合创建了并行流，自动采用并行运算提高速度。

在更复杂的场景，还可以用forEach()、map()、limit()、sorted()、collect()等方法进行进一步的流运算.
### 三、典型接口详解 ###
#### 3.1 Stream的创建 ####

常见的 Stream 创建方式有：

- 通过 Stream 接口提供的静态工厂方法，Stream.of()、Stream.generate() 等;
- 通过 Collection 接口的默认方法，stream()、parallelStream() 等;
当然还有其它方法，比如 Arrays.stream()、StreamSupport.stream() 。

但是在实际的开发中，一般我们通过 Collection 的 default 方法来获取集合对应的 Stream，针对该 Stream 进行处理。

1.java8 Stream API支持串行或并行的方式，可以简单看下jdk1.8 Collection接口的源码
	
	 /**
	 * 串行流
     * 返回以该集合为源的顺序{@code Stream}。
     * @返回此集合中元素的顺序{@code Stream}
     * @从1.8开始
     */
    default Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * 并行流
	 * 以这个集合作为返回一个可能并行的{@code Stream}资源。 此方法允许返回顺序流。
     *
     *@在此元素上返回可能并行的{@code Stream}* 
	 * @从1.8开始	
     */
    default Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }
**注意：**
**使用parallelStream()生成并行流后，对集合元素的遍历是无序的。**

2.通过 Stream 接口静态方法

Stream.of()
	
	@SafeVarargs
	@SuppressWarnings("varargs") // Creating a stream from an array is safe
	public static<T> Stream<T> of(T... values) {
	    return Arrays.stream(values);
	}
内部通过 Arrays.stream() 进行创建。

示例：

	Stream<String> streamStr = Stream.of("a","b","c");
	Stream<Integer> stream = Stream.of(1,2,3,4,5);
Stream.generate()

	public static<T> Stream<T> generate(Supplier<T> s) {
    Objects.requireNonNull(s);
    return StreamSupport.stream(
            new StreamSpliterators.InfiniteSupplyingSpliterator.OfRef<>(Long.MAX_VALUE, s), false);
	}

内部通过 StreamSupport 进行生成。

示例：

	Stream.generate(()->{
	return Math.random();
	});
	
	Stream.generate(new Supplier<Integer>() {
	
		@Override
		public Integer get() {
			return Math.round(5);
		}
	});
#### 3.2 map()方法####
	/**
	* 返回一个流，其中包含将给定函数应用于此流的元素的结果。
	*
	/
	<R> Stream<R> map(Function<? super T, ? extends R> mapper);

示例：

	 numbers.stream().map( n -> Math.abs(n)).forEach(n ->  System.out.println("Element abs: " + n));
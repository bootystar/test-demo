package stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author booty
 * @date 2021/5/25 13:36
 */
public class StreamTest {

//================================================函数型接口================================================

    /*
     * 4大函数型接口
     *
     * Consumer<T> : 消费型接口
     *   void accept(T t)
     *      接收指定T类型参数，无返回值
     *
     * Supplier<T> : 供给型接口
     *   T get()
     *      返回指定类型对象，无接收参数
     *
     * Function<T,R> : 函数型接口
     *   R apply(T t)
     *      接收T类型参数，返回R类型对象
     *
     * Predicate<T> : 断言型接口
     *   boolean test(T t)
     *      接收T类型参数，返回布尔值
     */


//================================================中间操作及终止操作================================================
    /*
    常见中间操作:
    filter、map、mapToInt、mapToLong、mapToDouble
    flatMap、flatMapToInt、flatMapToLong、flatMapToDouble
    mapMulti、mapMultiToInt、mapMultiToLong、mapMultiToDouble、
    parallel、unordered、onClose、sequential
    distinct、sorted、peek、limit、skip、takeWhile、dropWhile、
     */

    /*
    常见终止操作:
    forEach、forEachOrdered、toArray、reduce、collect、toList、min、
    max、count、anyMatch、allMatch、noneMatch、findFirst、findAny、iterator
     */



//================================================创建stream================================================

    /**
     * 创建stream
     */
   @Test
    public void testCreate(){
       //通过Collection集合系列stream()或parallelStream()获取
       List<String> list=new ArrayList<>();
       Stream<String> stream1 = list.stream();

       //通过Arrays静态方法stream() 获取数组流
       String [] array=new String[5];
       Stream<String> stream2 = Arrays.stream(array);

       //通过Stream类of()方法获取
       Stream<String> stream3= Stream.of("a", "b", "c");

       //创建无限流

       // 迭代：seed：起始值，函数型接口，接收T类型,返回也是T类型
       // 在对流进行操作时才会产生数据
       Stream<Integer> stream4 = Stream.iterate(0, (x) -> x + 2);
       // limit()，只产生指定个数
       stream4.limit(4).forEach(System.out::println);
       //不指定个数，会无限产生
//       stream4.forEach(System.out::println);

       // 生成
       Stream<Double> stream5 = Stream.generate(Math::random);
       // limit()，只产生指定个数
       stream5.limit(10).forEach(System.out::println);
       // 无限生成
//       stream5.forEach(System.out::println);
   }


//====================================中间操作================================================

    /*
     * 多个中间操作可以连接起来形成一个流水线，
     * 除非执行终止操作，否则中间操作不会进行任何的处理，
     * 而在终止操作时一次性全部处理，也叫"惰性求值"
     *
     * 内部迭代：
     * api内部完成迭代
     */


    /**
     * 筛选，过滤
     *
     * filter       接收lambda，从流中排除某些元素
     * limit(n)     截断，使元素不超过给定个数
     * skip(n)      跳过前几个元素
     * distinct     筛选，通过流生成元素的hashcode和equals去重
     *
     */
    @Test
    public void testFilter(){
        Stream<String> stream= Stream.of("aa", "b", "cc","dd","eee","aa");
        stream.filter((e)-> {
            System.out.println("中间过滤操作，字符串长度大于1");
            return e.length()>1; })
                .skip(1)
                .limit(2)
                .distinct()
                .forEach(System.out::println)
        ;
    }

    /**
     * 排序
     *
     * sorted()                 自然排序
     * sorted(Comparator com)   指定比较器排序
     */
    @Test
    public void testSorted(){
        Stream<String> stream1= Stream.of("aa", "b", "cc","dd","eee","aa");
        //自然排序
        stream1.sorted().forEach(System.out::println);

        System.out.println("-------------------------");
        //指定排序，先长度，后自然
        Stream<String> stream2= Stream.of("aa", "b", "cc","dd","eee","aa");
        stream2.sorted((e1,e2)->{
            if(e1.length()!=e2.length()){
                return e1.length()-e2.length();
            }else {
                return e1.compareTo(e2);
            }
        }).forEach(System.out::println);
    }


    /**
     * 映射
     *
     * map      接收lambda，将元素转换成其他形式或提取信息，接收一函数作为参数，该函数会被应用到每个元素上，并映射成一个新的元素
     * flatMap  接收函数作为参数，将流的每一个值转换成另一个流，然后把所有流连接成一个流
     *
     */
    @Test
    public void testMapToObject(){
        //使用map
        Stream<String> stream= Stream.of("aa", "b", "cc","dd","eee","aa");
        stream.map(String::toUpperCase)
                .forEach(System.out::println);
        System.out.println("---------------------");

        //若需要返回流时，使用map需要forEach两次，不方便
        Stream<String> stream2= Stream.of("aa", "b", "cc","dd","eee","aa");
        Stream<Stream<Character>> streamStream = stream2.map(StreamTest::filterCharacter);
        streamStream.forEach(e-> e.forEach(System.out::println));

        System.out.println("----------------------");
        //此时使用flatMap
        Stream<String> stream3= Stream.of("aa", "b", "cc","dd","eee","aa");
        Stream<Character> characterStream = stream3.flatMap(StreamTest::filterCharacter);
        characterStream.forEach(System.out::println);

    }

    /**
     * 将String 转化为Character流
     * @param str 字符串
     * @return 字符流
     */
    public static Stream<Character> filterCharacter(String str){
        List<Character> list=new ArrayList<>();
        for (Character ch :str.toCharArray()){
            list.add(ch);
        }
        return list.stream();
    }


//====================================终止操作====================================


    /**
     * 查找与匹配
     *
     * allMatch     检查是否匹配所有元素
     * anyMatch     检查是否匹配至少一个
     * noneMatch    检查是否没有匹配
     * findFirst    返回第一个元素
     * findAny      返回任意元素
     * count        返回元素总个数
     * max          返回最大元素
     * min          返回最小元素
     *
     */
    @Test
    public void testMatch(){
        //是否全部包含a
        Stream<String> stream1= Stream.of("aa", "b", "cc","dd","eee","aa");
        boolean a1 = stream1.allMatch(e -> e.contains("a"));
        System.out.println(a1);

        //是否至少一个元素包含a
        Stream<String> stream2= Stream.of("aa", "b", "cc","dd","eee","aa");
        boolean a2 = stream2.anyMatch(e -> e.contains("a"));
        System.out.println(a2);

        //是否无元素包含a
        Stream<String> stream3= Stream.of("aa", "b", "cc","dd","eee","aa");
        boolean a3 = stream3.noneMatch(e -> e.contains("a"));
        System.out.println(a3);

        //获取第一个对象，此处返回Optional是为了避免空指针异常，可使用get方法获取对象
        Stream<String> stream4= Stream.of("aa", "b", "cc","dd","eee","aa");
        Optional<String> first = stream4.findFirst();
        System.out.println(first.get());

        //获取最大值(长度)
        Stream<String> stream5= Stream.of("aa", "b", "cc","dd","eee","aa");
        Optional<String> max = stream5.max(Comparator.comparingInt(String::length));
        System.out.println(max.get());

    }

    /**
     * 归约
     *
     * reduce(T identity BinaryOperator)
     * reduce(BinaryOperator)
     *
     * 将流中元素结合起来得到一个值
     *
     * 若需要规约的值为对象的属性，可先通过map映射获取该属性，再处理
     * 此种处理方式成为map-reduce模式，因google使用作为网络搜索而出名
     */
    @Test
    public void testReduce(){
        //计算集合中值的总和（若值为对象的属性，可先通过map映射获取该属性，再处理）
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        //reduce identity是初始x值
        // 第一个参数为x值（累加值、上次运算的结果），第二个参数y为流中当前元素
        Integer sum = list.stream().reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        //此处返回Optional是因reduce无起始值，可能返回空
        Optional<Integer> reduce = list.stream().reduce(Integer::sum);
        System.out.println(reduce.get());
    }

    /**
     * 收集
     * collect   将流转化为其他形式，接收一个Collector接口的实现，用于给stream中元素做汇总
     *
     * 同理，若收集的内容为某属性，使用map映射
     *
     * Collectors 工具类提供了很多静态方法用于收集器的实现
     * 如toList(),toSet(),
     * 若需要转化为指定类型的集合，可一通过toCollection(集合类::new)引用其构造器
     */
    @Test
    public void testCollect(){

        //集合
        Stream<String> stream1= Stream.of("aa", "b", "cc","dd","eee","aa");
        HashSet<String> collect = stream1.map(String::toUpperCase)
                .sorted(Comparator.comparing(String::length))
//                .collect(Collectors.toList())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        System.out.println(collect);

        //总数
        Stream<String> stream2= Stream.of("aa", "b", "cc","dd","eee","aa");
        Long collect1 = stream2.collect(Collectors.counting());
        System.out.println(collect1);

        //平均长度
        Stream<String> stream3= Stream.of("aa", "b", "cc","dd","eee","aa");
        Double collect2 = stream3.collect(Collectors.averagingDouble(String::length));
        System.out.println(collect2);


        //分组
        Stream<String> stream4= Stream.of("aa", "b", "cc","dd","eee","aa");
        Map<Integer, List<String>> collect3 = stream4.collect(Collectors.groupingBy(String::length));
        System.out.println(collect3);

        //多级分组
        Stream<String> stream5= Stream.of("aa", "b", "cc","dd","eee","aa");
        Map<Integer, Map<String, List<String>>> collect4 = stream5.collect(
                Collectors.groupingBy(String::length,
                        Collectors.groupingBy(String::toLowerCase)));
        System.out.println(collect4);


        //分区(断言true或false)
        Stream<String> stream6= Stream.of("aa", "b", "cc","dd","eee","aa");
        Map<Boolean, List<String>> collect5 = stream6.collect(Collectors.partitioningBy(e -> e.length() > 1));
        System.out.println(collect5);


        //返回包含多个统计结果的对象SummaryStatistics，可以调用该对象对应方法获得统计数据
        Stream<String> stream7= Stream.of("aa", "b", "cc","dd","eee","aa");
        DoubleSummaryStatistics collect6 = stream7.collect(Collectors.summarizingDouble(String::length));
        System.out.println(collect6.getAverage());
        System.out.println(collect6.getCount());

        //字符串拼接
        Stream<String> stream8= Stream.of("aa", "b", "cc","dd","eee","aa");
        String collect7 = stream8.collect(Collectors.joining(","));
        System.out.println(collect7);

    }

    /**
     * 给定一个数字列表，返回一个由每个数的平方数构成的列表
     */
    @Test
    public void testMapToX(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> collect = list.stream().map(e -> e * e).collect(Collectors.toList());
        System.out.println(collect);
    }


    /**
     * 用map和reduce实现计数
     * 思路：用map返回1作为元素计数，然后使用reduce累加
     */
    @Test
    public void testCount(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Optional<Integer> reduce = list.stream().map(e -> 1).reduce(Integer::sum);
        System.out.println(reduce.get());
    }


    /**
     * 并行流和串行流
     * 并行流使用fork/join模式，CPU利用率高，效率高
     *
     * parallel()       切换为并行流
     * sequential()     切换为串行流
     */
    @Test
    public void testSwitchStream(){
        Instant start = Instant.now();

        //LongStream为并行流rangeClosed
        long reduce = LongStream.rangeClosed(0, 10000000000L)
                .parallel()
                .reduce(0, Long::sum);
        System.out.println(reduce);

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis());
    }


//===============================================练习================================================

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Transaction{
        Trader trader;
        Integer year;
        Double money;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Trader{
        String name;
        String city;
    }



    List<Trader> traders=Arrays.asList(new Trader("lisi","beijing"),
            new Trader("zhangsan","chengdu"),
            new Trader("wangwu","beijing"),
            new Trader("zhaoliu","shanghai"),
            new Trader("tianqi","wuhan"));

    List<Transaction> transactions=Arrays.asList(
            new Transaction(traders.get(0),2011,200.0),
            new Transaction(traders.get(2),2012,1000.0),
            new Transaction(traders.get(1),2011,400.0),
            new Transaction(traders.get(0),2012,700.0),
            new Transaction(traders.get(3),2012,800.0),
            new Transaction(traders.get(4),2012,900.0));

    /**
     * 找出2011年所有交易，并按交易额排序
     */
    @Test
    public void test10(){
        transactions.stream()
                .filter(e -> e.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getMoney))
                .forEach(System.out::println);
    }
    /**
     * 找出来自chengdu的交易员
     */
    @Test
    public void test11(){
        transactions.stream()
                .filter(e -> e.getTrader().getCity().equals("chengdu"))
                .forEach(System.out::println);
    }

    /**
     * 找出来自beijing的交易员，并按姓名排序
     */
    @Test
    public void test12(){
        transactions.stream()
                .filter(e -> e.getTrader().getCity().equals("beijing"))
                .sorted(Comparator.comparing(e -> e.getTrader().getName()))
                .forEach(System.out::println);
    }

    /**
     * 返回所有交易员姓名字符串，按字符顺序排序
     */
    @Test
    public void test13(){
        transactions.stream()
                .map(e->e.getTrader().getName())
                .sorted()
                .distinct()
                .forEach(System.out::println);

        //取出单个字符然后对字符排序，此时需要使用flatmap，方便操作流
        transactions.stream()
                .map(e->e.getTrader().getName())
                .flatMap(StreamTest::filterCharacter)
                .sorted()
                .forEach(System.out::print);

    }

    /**
     * 有无交易员在beijing生活
     */
    @Test
    public void test14(){
        boolean beijing = transactions.stream()
                .anyMatch(e -> e.getTrader().getCity().equals("beijing"));
        System.out.println(beijing);
    }

    /**
     * 打印所有在beijing生活的交易员的交易额
     */
    @Test
    public void test15(){
       transactions.stream()
               .filter(e->e.getTrader().getCity().equals("beijing"))
               .map(Transaction::getMoney)
               .forEach(System.out::println);
    }


    /**
     * 打印最高交易额
     */
    @Test
    public void test16(){
        Optional<Double> max = transactions.stream()
                .map(Transaction::getMoney)
                .max(Double::compare);
        System.out.println(max.get());
    }



}


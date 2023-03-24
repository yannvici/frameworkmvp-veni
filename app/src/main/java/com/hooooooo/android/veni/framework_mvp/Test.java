package com.hooooooo.android.veni.framework_mvp;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Created by heyangpeng on 2023/3/22
 * <p>
 * Describe:
 * Function<Integer, R>
 * IntFunction<R>
 * Function<Long, R>
 * LongFunction<R>
 * Function<Double, R>
 * DoubleFunction<R>
 * Function<Double,Integer>
 * DoubleToIntFunction
 * Function<Double,Long>
 * DoubleToLongFunction
 * Function<Long,Double>
 * LongToDoubleFunction
 * Function<Long,Integer>
 * LongToIntFunction
 * Function<R,Integer>
 * ToIntFunction<R>
 * Function<R,Long>
 * ToLongFunction<R>
 * Function<R,Double>
 * ToDoubleFunction<R>
 * Function<T,T>
 * UnaryOperator<T>
 * BiFunction<T,T,T>
 * BinaryOperator<T>
 * Consumer<Integer>
 * IntConsumer
 * Consumer<Double>
 * DoubleConsumer
 * Consumer<Long>
 * LongConsumer
 * BiConsumer<T,Integer>
 * ObjIntConsumer<T>
 * BiConsumer<T,Long>
 * ObjLongConsumer<T>
 * BiConsumer<T,Double>
 * ObjDoubleConsumer<T>
 * Predicate<Integer>
 * IntPredicate
 * Predicate<Double>
 * DoublePredicate
 * Predicate<Long>
 * LongPredicate
 * Supplier<Integer>
 * IntSupplier
 * Supplier<Double>
 * DoubleSupplier
 * Supplier<Long>
 * LongSupplier
 * Supplier<Boolean>
 * BooleanSupplier
 * UnaryOperator<Integer>
 * IntUnaryOperator
 * UnaryOperator<Double>
 * DoubleUnaryOperator
 * UnaryOperator<Long>
 * LongUnaryOperator
 * BinaryOperator<Integer>
 * IntBinaryOperator
 * BinaryOperator<Long>
 * LongBinaryOperator
 * BinaryOperator<Double>
 * DoubleBinaryOperator
 * Function<T, Boolean>
 * Predicate<T>
 * BiFunction<T,U,Boolean>
 * BiPredicate<T,U>
 */
public class Test {
    public static void main(String[] args) {
        Function<Integer, Integer> function = integer -> integer + 100;
        IntFunction<Integer> integerIntFunction = function1 -> function1 + 100;
        test(function);
        Runnable runnable = () -> System.out.println("TTTTT");
        runnable.run();
//        Supplier<Integer> supplier = () -> function.apply(1) + 1;
//        Consumer<Integer> consumer = (x)->{
//            int y =x*x;runnable
//            System.out.println(y+"");
//        };
        List<Integer> list = Arrays.asList(1, 3, 5, 2, 4);
        System.out.println(list); // [1, 3, 5, 2, 4]

        Predicate<Integer> predicate = x -> x < 4;
        Predicate<Integer> predicate2 = x -> x > 1;
        // and方法表示两个predicate过滤条件需要同时成立，相当于&&（与）
        List<Integer> list2 = list.stream().filter(predicate.and(predicate2)).collect(Collectors.toList());
        System.out.println(list2); // [3, 2]

        Predicate<Integer> predicate3 = predicate.and(predicate2);
        // 可以直接用predicate.and(predicate2)，也可以定义一个新的predicate3
        List<Integer> list3 = list.stream().filter(predicate3).collect(Collectors.toList());
        System.out.println(list3); // [3, 2]

        Predicate<Integer> predicate4 = x -> x >= 4;
        Predicate<Integer> predicate5 = x -> x <= 1;
        // or方法表示两个predicate过滤条件只需要成立1个，相当于||（或）
        List<Integer> list4 = list.stream().filter(predicate4.or(predicate5)).collect(Collectors.toList());
        System.out.println(list4); // [1, 5, 4]

        // negate方法表示这个predicate过滤条件不成立，相当于!（非）
        List<Integer> list5 = list.stream().filter(predicate4.negate()).collect(Collectors.toList());
        System.out.println(list5); // [1, 3, 2]

        // test方法为测试单个用例，判断参数是否满足predicate的条件，返回值为boolean类型
        System.out.println(predicate5.test(10)); // false
        System.out.println(predicate5.test(1)); // true

        int number = 3;
        // isEqual为静态方法，用来判断集合的流中是否有与isEqual方法相等的值
        Predicate<Integer> predicate6 = Predicate.isEqual(number);
        System.out.println(list.stream().filter(predicate6).collect(Collectors.toList())); // [3]


    }

    private static void test(Function<Integer, Integer> flyable) {
        System.out.println(flyable.apply(1000) + "m/s");
    }


}

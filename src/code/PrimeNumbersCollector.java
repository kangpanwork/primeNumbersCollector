package code;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 2022/2/22 20:50
 */
public class PrimeNumbersCollector implements Collector<Integer, Map<Boolean, List<Integer>>,
        Map<Boolean, List<Integer>>>, PrimeNumber<Integer> {

    /**
     * 累加器提供者 创建一个初始容器 收集元素
     *
     * @return Supplier<Map < Boolean, List < Integer>>>
     */
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {
            {
                put(true, new ArrayList<>());
                put(false, new ArrayList<>());
            }
        };
    }

    /**
     * 将满足条件的元素添加到容器中
     *
     * @return
     */
    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> map, Integer number) -> {
            map.get(isPrime(map.get(true), number)).add(number);
        };
    }

    /**
     * 合并
     *
     * @return
     */
    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> result, Map<Boolean, List<Integer>> identity) -> {
            result.get(true).addAll(identity.get(true));
            result.get(false).addAll(identity.get(false));
            return result;
        };
    }

    /**
     * 对结果容器应用最终转换
     *
     * @return Function<Map < Boolean, List < Integer>>, Map<Boolean, List<Integer>>>
     */
    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    /**
     * —这表明完成器方法返回的函数是一个恒等函数，可以跳过。这种
     * 情况下，累加器对象将会直接用作归约过程的最终结果。这也意味着，将累加器A不加检
     * 查地转换为结果R是安全的
     *
     * @return Set<Characteristics>
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    @Override
    public Boolean isPrime(List<Integer> primes, Integer t) {
        int n = (int) Math.sqrt((double) t);
        return getPrimes(primes, i -> i <= n).stream().noneMatch(e -> t % e == 0);
    }
}

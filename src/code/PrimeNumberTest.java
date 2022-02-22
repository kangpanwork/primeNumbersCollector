package code;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class PrimeNumberTest {
    public static void main(String[] args) {
        Map<Boolean, List<Integer>> result = IntStream.rangeClosed(2, 20).boxed().collect(new PrimeNumbersCollector());
        // 质数
        result.get(true).forEach(System.out::println);
        // 非质数
        result.get(false).forEach(System.out::println);
    }
}

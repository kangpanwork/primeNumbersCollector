package code;

import java.util.List;
import java.util.function.Predicate;

public interface PrimeNumber<T> {

    Boolean isPrime(List<T> primes, T t);

    default List<T> getPrimes(List<T> primes, Predicate<T> predicate) {
        int subIndex = 0;
        for (T t : primes) {
            if (!predicate.test(t)) {
                return primes.subList(0, subIndex);
            }
            subIndex++;
        }
        return primes;
    }
}

package org.poc.demo;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamApiExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(3, 1, 6, 2, 5, 4);
        System.out.println("Simple List: " + list);

        // example of lambda expression
        list.forEach(i -> System.out.println(i));

        // example of method reference
        list.forEach(System.out::println);

        // example of Stream API - refer java doc for stream api
        Stream<Integer> integerStream = list.stream();
        integerStream.forEach(System.out::println);

        // demo to reuse the already processed stream
        //integerStream.forEach(System.out::println);

        // demo - count()
        Stream<Integer> integerStream2 = list.stream();
        System.out.println("Total Element in the list: " + integerStream2.count());

        // demo - sorted()
        Stream<Integer> integerStream3 = list.stream();
        Stream<Integer> sortedData = integerStream3.sorted();
        sortedData.forEach(System.out::println);

        // demo - map()
        Stream<Integer> integerStream4 = list.stream();
        Stream<Integer> mappedData = integerStream4.map(i -> i*2);
        mappedData.forEach(System.out::println);

        // demo - reduce the code
        list.stream()
                .sorted()
                .map(i -> i*2)
                .forEach(System.out::println);

        // demo - adding filter()
        System.out.println("~~~~ Demo - Adding Filter ~~~~");
        list.stream()
                .sorted()
                .filter(i -> i%2 != 0)
                .map(i -> i*2)
                .forEach(System.out::println);

        // demo - adding filter() - explaining Predicate
        System.out.println("~~~~ Demo - Adding Filter - explaining Predicate ~~~~");
        Predicate<Integer> predicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer i) {
                return i%2 != 0;
            }
        };

        list.stream()
                .sorted()
                .filter(predicate)
                .map(i -> i*2)
                .forEach(System.out::println);

        // demo - adding reduce()
        System.out.println("~~~~ Demo - Adding Reduce ~~~~");
        int reducedCount = list.stream()
                .sorted()
                .filter(i -> i%2 != 0)
                .map(i -> i*2)
                .reduce(0, (c, e) -> c+e);
        System.out.println("Reduced Count: " + reducedCount);

    }
}

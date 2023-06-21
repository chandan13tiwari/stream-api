package org.poc.demo;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamApiExample {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(3, 1, 6, 2, 5, 4);
        System.out.println("Simple List: " + list);

        // example of lambda expression
        System.out.println("~~~~ Demo - lambda expression ~~~~");
        list.forEach(i -> System.out.println(i));

        // example of method reference
        System.out.println("~~~~ Demo - method reference ~~~~");
        list.forEach(System.out::println);

        // example of Stream API - refer java doc for stream api
        System.out.println("~~~~ Demo - stream() ~~~~");
        Stream<Integer> integerStream = list.stream();
        integerStream.forEach(System.out::println);

        // demo to reuse the already processed stream
        //integerStream.forEach(System.out::println);

        // demo - count()
        System.out.println("~~~~ Demo - Adding count() ~~~~");
        Stream<Integer> integerStream2 = list.stream();
        System.out.println("Total Element in the list: " + integerStream2.count());

        // demo - sorted()
        System.out.println("~~~~ Demo - Adding sorted() ~~~~");
        Stream<Integer> integerStream3 = list.stream();
        Stream<Integer> sortedData = integerStream3.sorted();
        sortedData.forEach(System.out::println);

        // demo - map()
        System.out.println("~~~~ Demo - Adding map() ~~~~");
        Stream<Integer> integerStream4 = list.stream();
        Stream<Integer> mappedData = integerStream4.map(i -> i*2);
        mappedData.forEach(System.out::println);

        // demo - reduce the code
        System.out.println("~~~~ Demo - Reducing code ~~~~");
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

        // demo - findFirst()
        System.out.println("~~~~ Demo - Adding findFirst ~~~~");
        Optional<Integer> findFirst = list.stream()
                .sorted()
                .filter(i -> i%2 != 0)
                .map(i -> i*2)
                .findFirst();
        System.out.println("Find First Element: " + findFirst.get());


        // demo - count the occurrence of i with chars()
        System.out.println("~~~~ Demo - Count the occurrence of i with chars() ~~~~");
        String name = "Mississippi";
        IntStream stream = name.chars();
        int occurrenceCount = (int) stream.filter(ch -> ch == 'i').count();
        System.out.println(occurrenceCount);

        // demo - count the occurrence of i with codePoints()
        System.out.println("~~~~ Demo - Count the occurrence of i with codePoints() ~~~~");
        String name2 = "Mississippi";
        IntStream stream2 = name2.codePoints();
        int occurrenceCount2 = (int) stream2.filter(ch -> ch == 'i').count();
        System.out.println(occurrenceCount2);

        /*
        The implementation of both the methods are exact same. BUT
        Difference between chars() and codePoint() is just the range.
        codePoints range is from 0 - 10FFFF16
        char range is from 0 - FFFF
         */

        // demo - count the occurrence of each character -
        System.out.println("~~~~ Demo - Count the occurrence of each character ~~~~");
        String str = "Mississippi";
        Map<Character, Long> map = str.chars()
                .mapToObj(ch -> (char) ch)
                .collect(Collectors.groupingBy(ch -> ch, Collectors.counting()));
        map.forEach((key, value) -> System.out.println(key + " " + value));

        // demo - count the occurrence of each character ALTERNATIVE-
        System.out.println("~~~~ Demo - Count the occurrence of each character ALTERNATIVE ~~~~");
        String str5 = "AAAABBBCDD";
        Map<String, Integer> map2 = Arrays.stream(str5.split(""))
                        .collect(Collectors.toMap(Function.identity(), c -> 1, Math::addExact)); // we can use Integer::sum here
        map2.forEach((key, value) -> System.out.println(key + " " + value));

        // demo - count all the duplicate characters in a string
        System.out.println("~~~~ Demo - Count all the duplicate characters in a string ~~~~");
        String str2 = "java is java is again java";
        Set<String> setData = new HashSet<>();
        int occCount = (int) Arrays.stream(str2.split(" "))
                .filter(i -> !setData.add(i))
                .count();
        System.out.println(occCount);

        // demo - merge two string array using flatMap
        System.out.println("~~~~ Demo - Merge two string array using FlatMap ~~~~");
        String[] arr1 = {"1", "2", "3"};
        String[] arr2 = {"4", "5", "6"};
        String[] flatMap = Stream.of(arr1, arr2)
                .flatMap(Stream::of)
                .toArray(String[]::new);
        System.out.println(Arrays.toString(flatMap));

        // demo - remove duplicate words from String
        System.out.println("~~~~ Demo - Remove duplicate words from String ~~~~");
        String str3 = "JAVA is JAVA is again JAVA";
        String newString = Arrays.stream(str3.split(" "))
                .distinct()
                .collect(Collectors.joining(" "));
        System.out.println(newString);


        // demo - nth highest salary
        System.out.println("~~~~ Demo - Nth Highest Salary ~~~~");
        Map<String, Integer> employeeMap = new HashMap<>();
        employeeMap.put("Chandan", 1400);
        employeeMap.put("Satish", 1200);
        employeeMap.put("Golu", 1400);
        employeeMap.put("Manisha", 1800);
        employeeMap.put("Kamal", 1100);

        Map.Entry<Integer, List<String>> secondHighestSalary = employeeMap.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .toList()
                .get(1);

        System.out.println(secondHighestSalary);

    }
}

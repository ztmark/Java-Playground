package com.mark.java8;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Author: Mark
 * Date  : 16/3/23
 */
public class Streams {

    public static void main(String[] args) {

//        demo1();

//        stream();

//        parallelStream();

        Map<Integer, String> map = new HashMap<>();
//        for (int i = 0; i < 10; i++) {
//            map.putIfAbsent(i, "val" + i);
//        }
        IntStream.range(0, 10).forEach(i -> map.putIfAbsent(i, "val" + i));
        map.forEach((id, val) -> System.out.println(val));

        System.out.println("===========================");
        map.computeIfPresent(3, (num, val) -> val + num);
        System.out.println(map.get(3));

        map.computeIfPresent(9, (num, val) -> null);
        System.out.println(map.containsKey(9));

        map.computeIfAbsent(23, num -> "val" + num);
        System.out.println(map.containsKey(23));

        map.computeIfAbsent(3, num -> "bam");
        System.out.println(map.get(3));

        map.remove(3, "val3");
        System.out.println(map.get(3));

        map.remove(3, "val33");
        System.out.println(map.get(3));

        System.out.println(map.getOrDefault(22, "not found"));

        map.merge(9, "val9", String::concat);
        System.out.println(map.get(9));

        map.merge(9, "concat", String::concat);
        System.out.println(map.get(9));

    }

    private static void parallelStream() {
        List<String> values = new ArrayList<>();
        Stream.generate(UUID::randomUUID).map(UUID::toString).limit(1_000_000).forEach(values::add);
        System.out.println(values.size());

        long s = System.nanoTime();
        long count = values.stream().sorted().count();
        long e = System.nanoTime();
        long duration = TimeUnit.NANOSECONDS.toMillis(e - s);
        System.out.println(duration + " ms"); // 726 ms

        s = System.nanoTime();
        count = values.parallelStream().sorted().count();
        e = System.nanoTime();
        duration = TimeUnit.NANOSECONDS.toMillis(e - s);
        System.out.println(duration + " ms"); // 418 ms
    }

    private static void stream() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");

        stringCollection.stream()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);
        System.out.println("================");
        stringCollection.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println(stringCollection);
        System.out.println("================");
        stringCollection.stream()
                .map(String::toUpperCase)
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        System.out.println("================");
        boolean anyStartWithA = stringCollection.stream()
                .anyMatch(s -> s.startsWith("a"));
        System.out.println(anyStartWithA);
        boolean allStartsWithA = stringCollection.stream()
                .allMatch(s -> s.startsWith("a"));
        System.out.println(allStartsWithA);
        boolean noneStartsWithZ = stringCollection.stream()
                .noneMatch(s -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);

        System.out.println("================");
        long bs = stringCollection.stream()
                .filter(s -> s.startsWith("b"))
                .count();
        System.out.println(bs);

        System.out.println("================");
        Optional<String> reduced = stringCollection.stream()
                .reduce((s1, s2) -> s1 + "," + s2);
        reduced.ifPresent(System.out::println);
    }

    private static void demo1() {
        final Collection< Task > tasks = Arrays.asList(
                new Task( Status.OPEN, 5 ),
                new Task( Status.OPEN, 13 ),
                new Task( Status.CLOSED, 8 )
        );

// Calculate total points of all active tasks using sum()
        final long totalPointsOfOpenTasks = tasks
                .stream()
                .filter( task -> task.getStatus() == Status.OPEN )
//                .count();
                .mapToInt( Task::getPoints )
                .sum();

        System.out.println( "Total points: " + totalPointsOfOpenTasks );


        final double totalPoints = tasks
                .stream()
                .parallel()
                .map( task -> task.getPoints() ) // or map( Task::getPoints )
                .reduce( 0, Integer::sum );

        System.out.println( "Total points (all tasks): " + totalPoints );


// Group tasks by their status
        final Map< Status, List< Task >> map = tasks
                .stream()
                .collect( Collectors.groupingBy( Task::getStatus ) );
        System.out.println( map );


        // Calculate the weight of each tasks (as percent of total points)
        final Collection< String > result = tasks
                .stream()                                        // Stream< String >
                .mapToInt( Task::getPoints )                     // IntStream
                .asLongStream()                                  // LongStream
                .mapToDouble( points -> points / totalPoints )   // DoubleStream
                .boxed()                                         // Stream< Double >
                .mapToLong( weigth -> ( long )( weigth * 100 ) ) // LongStream
                .mapToObj( percentage -> percentage + "%" )      // Stream< String>
                .collect( Collectors.toList() );                 // List< String >

        System.out.println( result );
    }

    private enum Status {
        OPEN, CLOSED
    }

    private static final class Task {
        private final Status status;
        private final Integer points;

        Task( final Status status, final Integer points ) {
            this.status = status;
            this.points = points;
        }

        public Integer getPoints() {
            return points;
        }

        public Status getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return String.format( "[%s, %d]", status, points );
        }
    }
}

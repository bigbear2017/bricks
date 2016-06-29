package com.bricks;

import com.bricks.util.IntegerSupplier;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author caonn@mediav.com
 * @version 16/6/29.
 */
public class FeatureJava8 {

  public static void test_sort_lambda() {
    List<Integer> list = new Random().ints(30).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    Collections.sort(list, Integer::compare);
    list.stream().forEach(x -> System.out.print(x + " "));
    System.out.println();
  }

  public static void test_fibbonacci_stream() {
    Stream<Long> fibbValues = Stream.generate(new FibbonacciSupplier());
    fibbValues.limit(20).forEach( x -> System.out.print( x + " "));
    System.out.println();
  }

  public static void test_unlimit_stream() {
    Stream<Long> naturalValues = Stream.generate(new NatureSupplier());
    naturalValues.map( x -> x * x).limit(100).forEach( x -> System.out.print(x + " ") );
    System.out.println();
  }

  public static void test_stream() {
    Stream<Integer> stream = Stream.generate(new IntegerSupplier());
    stream.limit(100).filter( x-> x % 2 == 0 ).map( x -> x * x ).forEach( x -> System.out.print( x + " "));
    System.out.println();
  }

  public static void main(String [] args) {
    test_stream();
    test_unlimit_stream();
    test_fibbonacci_stream();
    test_sort_lambda();
  }

}
class NatureSupplier implements Supplier<Long> {
  long value = 0;
  @Override
  public Long get() {
    value += 1;
    return value;
  }
}

class FibbonacciSupplier implements Supplier<Long> {
  long value1 = 0;
  long value2 = 1;

  @Override
  public Long get() {
    long value3 = value1 + value2;
    value1 = value2;
    value2 = value3;
    return value3;
  }
}
package com.bricks.util;

import java.util.function.Supplier;

/**
 * To generate interger stream.
 * @author caonn@mediav.com
 * @version 16/6/29.
 */
public class IntegerSupplier implements Supplier<Integer> {
  private int value = 0;
  public Integer get() {
    return value++;
  }
}

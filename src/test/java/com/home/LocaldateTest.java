package com.home;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


public class LocaldateTest {
  
  @Test
  public void test() {
    LocalDate d1 = LocalDate.of(2000, 1, 1);
    LocalDate d2 = LocalDate.of(2000, 1, 2);
    Assertions.assertThat(d1.isBefore(d2)).isTrue();
    
  }

}

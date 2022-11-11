package com.roman14.springbootjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJpaApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(SpringBootJpaApplication.class, args);
  }

  /**
   * Hibernate5Module을 빈에 등록 해주면, 지연로딩 객체 즉 LAZY 타입을 자동으로 NULL 값으로 출력해준다.
   */
  /*@Bean
  Hibernate5Module hibernate5Module()
  {
    return new Hibernate5Module();
  }*/
}

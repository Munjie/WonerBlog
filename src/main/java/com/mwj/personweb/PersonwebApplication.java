package com.mwj.personweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan("com.mwj.personweb.dao")
@EnableCaching
public class PersonwebApplication {

  public static void main(String[] args) {
    SpringApplication.run(PersonwebApplication.class, args);
  }
}

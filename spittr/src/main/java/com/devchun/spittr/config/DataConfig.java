package com.devchun.spittr.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@PropertySource("classpath:db.properties")
public class DataConfig {
  private static final Logger logger = LoggerFactory.getLogger(DataConfig.class);

  @Autowired
  private Environment env;

  @Bean
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    logger.info(env.getProperty("jdbc.driver"));
    logger.info(env.getProperty("jdbc.url"));
    logger.info(env.getProperty("jdbc.username"));
    logger.info(env.getProperty("jdbc.password"));

    dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
    dataSource.setUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.username"));
    dataSource.setPassword(env.getProperty("jdbc.password"));

    return dataSource;
  }
}

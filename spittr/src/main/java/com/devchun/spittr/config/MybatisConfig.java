package com.devchun.spittr.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class MybatisConfig {
  private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

  @Autowired
  private DataSource dataSource;

  @Bean
  public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory
        .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*Mapper.xml"));
    sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

    return sessionFactory;
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
    SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sessionFactory);
    return sessionTemplate;
  }

  @Bean
  @Qualifier("mybatisTxManager")
  public DataSourceTransactionManager transactionManager() {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    dataSourceTransactionManager.setDataSource(dataSource);

    return dataSourceTransactionManager;
  }
}

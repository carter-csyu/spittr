package com.devchun.spittr.data;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.devchun.spittr.config.DataConfig;
import com.devchun.spittr.domain.Spitter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataConfig.class })
public class HibernateTest {
  private static final Logger logger = LoggerFactory.getLogger(HibernateTest.class);

  @Autowired
  private SessionFactory sessionFactory;

  public Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  @SuppressWarnings("unchecked")
  @Test
  @Transactional
  public void findAllTest() {
    List<Spitter> spitterList = 
        (List<Spitter>) sessionFactory
          .getCurrentSession()
          .createCriteria(Spitter.class)
          .list();

    for (Spitter s : spitterList) {
      logger.info(s.toString());
    }
  }
  
  @Test
  @Transactional
  public void findOneTest() {
    Spitter s = (Spitter) sessionFactory
        .getCurrentSession()
        .get(Spitter.class, "chundol42");
    
    logger.info(s.toString());
  }

  @Test
  public void findAll() {
    Session session = sessionFactory.openSession();
    Transaction tx = null;
    try {
      tx = session.getTransaction();

      List<Spitter> spitterList = (List<Spitter>) session.createCriteria(Spitter.class).list();

      for (Spitter s : spitterList) {
        logger.info(s.toString());
      }

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();
      logger.error(e.getMessage());
    } finally {
      session.close();
    }
  }
}

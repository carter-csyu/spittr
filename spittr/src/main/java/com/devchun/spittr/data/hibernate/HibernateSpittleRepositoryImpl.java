package com.devchun.spittr.data.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devchun.spittr.data.SpittleRepository;
import com.devchun.spittr.domain.Spittle;

@Repository
@Qualifier("hibernateSpittleRepository")
@Transactional(transactionManager="hibernateTxManager")
public class HibernateSpittleRepositoryImpl implements SpittleRepository {

  @Autowired
  @Qualifier("hibernateSessionFactory")
  private SessionFactory sessionFactory;
  
  
  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Spittle> findSpittles(long max, int count) {
    return getSession().createCriteria(Spittle.class).list();
  }

  @Override
  public Spittle findOne(long spittleId) {
    return (Spittle) getSession().get(Spittle.class, spittleId);
  }

  @Override
  public void save(Spittle spittle) {
    getSession().save(spittle);
  }

}

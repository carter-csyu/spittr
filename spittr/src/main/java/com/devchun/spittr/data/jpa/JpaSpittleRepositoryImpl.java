package com.devchun.spittr.data.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devchun.spittr.data.SpittleRepository;
import com.devchun.spittr.domain.Spittle;

@Repository
@Qualifier("jpaSpittleRepository")
@Transactional(transactionManager="jpaTxManager")
public class JpaSpittleRepositoryImpl implements SpittleRepository {

  @PersistenceContext
  private EntityManager em;
  
  @SuppressWarnings("unchecked")
  @Override
  public List<Spittle> findSpittles(long max, int count) {
    return (List<Spittle>) em.createQuery("select s from Spittle").getResultList();
  }

  @Override
  public Spittle findOne(long spittleId) {
    return (Spittle) em.find(Spittle.class, spittleId);
  }

  @Override
  public void save(Spittle spittle) {
    em.persist(spittle);
  }

}

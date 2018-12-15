package com.devchun.spittr.data.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devchun.spittr.data.SpitterRepository;
import com.devchun.spittr.domain.Spitter;

@Repository
@Qualifier("jpaSpitterRepository")
@Transactional(transactionManager="jpaTxManager")
public class JpaSpitterRepositoryImpl implements SpitterRepository {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  public Spitter save(Spitter spitter) {
    em.persist(spitter);
    return spitter;
  }

  @Override
  public Spitter findByUsername(String username) {
    return (Spitter) em.createQuery("select s from Spitter s where s.username=?")
        .setParameter(1, username).getSingleResult();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Spitter> findAll() {
    return (List<Spitter>) em.createQuery("select s from Spitter s").getResultList();
  }

}

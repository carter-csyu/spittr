package com.devchun.spittr.data;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devchun.spittr.domain.Spitter;
import com.devchun.spittr.web.exception.DuplicatedSpitterException;

@Repository
@Qualifier("hibernateSpitterRepository")
@Transactional(transactionManager="hibernateTxManager")
public class HibernateSpitterRepositoryImpl implements SpitterRepository {

  @Autowired
  @Qualifier("hibernateSessionFactory")
  private SessionFactory sessionFactory;

  private Session getSession() {
    return sessionFactory.getCurrentSession();
  }

  public Spitter save(Spitter spitter) {
    if (findByUsername(spitter.getUsername()) != null) {
      throw new DuplicatedSpitterException();
    }
    
    StandardPasswordEncoder encoder = new StandardPasswordEncoder("chundol42");
    spitter.setPassword(encoder.encode(spitter.getPassword()));
    
    Serializable id = getSession().save(spitter);
    
    return new Spitter(
        (Long) id,
        spitter.getUsername(),
        spitter.getPassword(),
        spitter.getFirstName(),
        spitter.getLastName(),
        spitter.getEmail());
        
  }

  public Spitter findByUsername(String username) {
    return (Spitter) getSession()
        .createCriteria(Spitter.class)
        .add(Restrictions.eq("username", username))
        .list()
        .get(0);
  }

  @SuppressWarnings("unchecked")
  public List<Spitter> findAll() {
    return (List<Spitter>) getSession().createCriteria(Spitter.class).list();
  }
  
  public Spitter findOne(long id) {
    return (Spitter) getSession().get(Spitter.class, id);
  }
}

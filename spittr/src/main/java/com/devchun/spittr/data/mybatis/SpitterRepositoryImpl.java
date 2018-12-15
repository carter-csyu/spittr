package com.devchun.spittr.data.mybatis;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.devchun.spittr.data.SpitterRepository;
import com.devchun.spittr.domain.Spitter;
import com.devchun.spittr.web.exception.DuplicatedSpitterException;

@Repository
@Qualifier("mybatisSpitterRepository")
public class SpitterRepositoryImpl implements SpitterRepository {
	private static final Logger logger = LoggerFactory.getLogger(SpitterRepositoryImpl.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public Spitter save(Spitter spitter) {
		Spitter newSpitter;
		newSpitter = sqlSession.selectOne("spittr.selectSpitter", spitter.getUsername());
		
		if (newSpitter != null) throw new DuplicatedSpitterException();
		
		logger.debug(spitter.toString());
		StandardPasswordEncoder encoder = new StandardPasswordEncoder("chundol42");
		logger.debug(encoder.encode(spitter.getPassword()));
		spitter.setPassword(encoder.encode(spitter.getPassword()));
		
		sqlSession.insert("spittr.insertSpitter", spitter);
		return spitter;
	}

	public Spitter findByUsername(String username) {
		return sqlSession.selectOne("spittr.selectSpitter", username);
	  	}

  @Override
  public List<Spitter> findAll() {
    return sqlSession.selectList("spittr.selectSpitters");
  }
}

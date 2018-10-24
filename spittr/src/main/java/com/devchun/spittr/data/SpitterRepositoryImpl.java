package com.devchun.spittr.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devchun.spittr.Spitter;
import com.devchun.spittr.web.DuplicatedSpitterException;

@Repository
public class SpitterRepositoryImpl implements SpitterRepository {
	private static final Logger logger = LoggerFactory.getLogger(SpitterRepositoryImpl.class);
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private List<Spitter> spitters = new ArrayList<Spitter>();
	
	@Override
	public Spitter save(Spitter spitter) {
		/*
		Spitter newSpitter;
		newSpitter = sqlSession.selectOne("spittr.selectSpitter", spitter.getUsername());
		
		if (newSpitter != null) throw new DuplicatedSpitterException();
		
		sqlSession.insert("spittr.insertSpitter", spitter);
		return spitter;
		*/
		
		boolean exist = false;
		
		for (Spitter s : spitters) {
			if (spitter.getUsername().equals(s.getUsername())) {
				logger.warn("Already exist username");
				exist = true;
				break;
			}
		}
		
		if (!exist) {
			spitters.add(spitter);
		}
		return spitter;
	}

	@Override
	public Spitter findByUsername(String username) {
		return sqlSession.selectOne("spittr.selectSpitter", username);
		
		/*
		Spitter spitter = null;
		for (Spitter s : spitters) {
			if (username.equals(s.getUsername())) {
				spitter = s;
				break;
			}
		}
		return spitter;
		*/
	}
}

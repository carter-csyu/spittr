package com.devchun.spittr.data.mybatis;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devchun.spittr.data.SpittleRepository;
import com.devchun.spittr.domain.Spittle;
import com.devchun.spittr.web.exception.SpittleNotFoundException;

@Repository
@Transactional(transactionManager="mybatisTxManager")
public class SpittleRepositoryImpl implements SpittleRepository {

  @Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<Spittle> findSpittles(long max, int count) {
		return sqlSession.selectList("spittr.selectSpittles");
	}

	public Spittle findOne(long spittleId) {
	  Spittle spittle = sqlSession.selectOne("spittr.selectSpittle", spittleId);
	  
	  if (spittle == null) {
	    throw new SpittleNotFoundException();
	  }
	  
	  return spittle;
	}

	public void save(Spittle spittle) {
		sqlSession.insert("spittr.insertSpittle", spittle);
	}
}
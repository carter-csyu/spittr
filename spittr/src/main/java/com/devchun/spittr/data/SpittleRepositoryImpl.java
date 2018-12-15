package com.devchun.spittr.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.devchun.spittr.domain.Spittle;
import com.devchun.spittr.web.exception.SpittleNotFoundException;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {

  @Autowired
	private SqlSessionTemplate sqlSession;
	
	private final Spittle[] initSpittles = {
		new Spittle((long)1, "Spittle1", new Date(), null, null),
		new Spittle((long)2, "Spittle2", new Date(), null, null),
		new Spittle((long)3, "Spittle3", new Date(), null, null)
	};
	private List<Spittle> spittles = new ArrayList<Spittle>(Arrays.asList(initSpittles));
	
	public List<Spittle> findSpittles(long max, int count) {
		return sqlSession.selectList("spittr.selectSpittles");
	}

	public Spittle findOne(long spittleId) {
		Spittle result = null;
		for (Spittle s : spittles) {
			if (s.getId() == spittleId) {
				result = s;
				break;
			}
		}
		if (result == null) throw new SpittleNotFoundException();
		
		return result;
	}

	public void save(Spittle spittle) {
		sqlSession.insert("spittr.insertSpittle", spittle);
	}
}
package com.devchun.spittr.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.devchun.spittr.Spittle;
import com.devchun.spittr.web.DuplicatedSpittleException;
import com.devchun.spittr.web.SpittleNotFoundException;

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
	
	@Override
	public List<Spittle> findSpittles(long max, int count) {
		return sqlSession.selectList("spittr.selectSpittles");
		
		//return spittles;
	}

	@Override
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

	@Override
	public void save(Spittle spittle) {
		if (spittle.getMessage().equals("chundol")) {
			throw new DuplicatedSpittleException();
		} 
		
		for (Spittle s : spittles) {
			if (s.equals(spittle)) {
				throw new DuplicatedSpittleException();
			}
		}
		spittles.add(spittle);
	}
}
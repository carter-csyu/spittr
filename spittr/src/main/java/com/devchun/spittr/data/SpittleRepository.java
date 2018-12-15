package com.devchun.spittr.data;

import java.util.List;

import com.devchun.spittr.domain.Spittle;

public interface SpittleRepository {
	List<Spittle> findSpittles(long max, int count);
	Spittle findOne(long spittleId);
	void save(Spittle spittle);
}

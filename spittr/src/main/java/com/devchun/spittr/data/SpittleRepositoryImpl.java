package com.devchun.spittr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.devchun.spittr.Spittle;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {

	@Override
	public List<Spittle> findSpittles(long max, int count) {
		List<Spittle> spittles = new ArrayList<Spittle>();
		
		for (int i = 0; i < count; i++) {
			spittles.add(new Spittle((long)i, "Spittle" + i, new Date(), null, null));
		}
		
		return spittles;
	}

	@Override
	public Spittle findOne(long spittleId) {
		return new Spittle(spittleId, "Spittle" + Long.toString(spittleId), new Date(), null, null);
	}
}
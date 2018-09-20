package com.devchun.spittr.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.devchun.spittr.Spitter;

@Repository
public class SpitterRepositoryImpl implements SpitterRepository {
	private static final Logger logger = LoggerFactory.getLogger(SpitterRepositoryImpl.class);
	
	private List<Spitter> spitters = new ArrayList<Spitter>();
	
	@Override
	public Spitter save(Spitter spitter) {
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
		Spitter spitter = null;
		
		for (Spitter s : spitters) {
			if (username.equals(s.getUsername())) {
				spitter = s;
				break;
			}
		}
		
		return spitter;
	}
}

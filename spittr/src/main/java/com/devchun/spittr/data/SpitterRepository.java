package com.devchun.spittr.data;

import java.util.List;

import com.devchun.spittr.domain.Spitter;

public interface SpitterRepository {
	public Spitter save(Spitter spitter);
	public Spitter findByUsername(String username);
	public List<Spitter> findAll();
}

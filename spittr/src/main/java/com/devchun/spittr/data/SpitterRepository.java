package com.devchun.spittr.data;

import com.devchun.spittr.Spitter;

public interface SpitterRepository {
	public Spitter save(Spitter spitter);
	public Spitter findByUsername(String username);
}

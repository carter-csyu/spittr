package com.devchun.spittr.domain;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Spittle {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
  
  @Column(name="message")
	private String message;
  
  @Column(name="created")
	private Date time;
  
  @Column(name="longitude")
	private Double longitude;
  
  @Column(name="latitude")
	private Double latitude;
  
  public Spittle() {}
	public Spittle(String message, Date time) {
		this(null, message, time, null, null);
	}
	
	public Spittle(Long id, String message, Date time, Double longitude, Double latitude) {
		this.id = id;
		this.message = message;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Spittle(Long id, String message, Timestamp time, Double longitude, Double latitude) {
		this.id = id;
		this.message = message;
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public long getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Date getTime() {
		return time;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Double getLatitude() {
		return latitude;
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, "id", "time");
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "time");
	}
}

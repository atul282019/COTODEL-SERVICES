package com.cotodel.hrms.auth.server.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
@Embeddable
public class CompositeKey implements Serializable {
	private Long id;
	private String code;
	
	public CompositeKey() {
		
	}
	public CompositeKey(Long id, String code) {
		super();
		this.id = id;
		this.code = code;
	}
	@Override
	public int hashCode() {
		return Objects.hash(code, id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompositeKey other = (CompositeKey) obj;
		return Objects.equals(code, other.code) && Objects.equals(id, other.id);
	}
	
}

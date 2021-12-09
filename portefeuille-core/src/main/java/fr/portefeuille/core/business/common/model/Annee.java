package fr.portefeuille.core.business.common.model;

import java.io.Serializable;

import org.bindgen.Bindable;

import com.fasterxml.jackson.annotation.JsonValue;

@Bindable
public final class Annee implements Serializable {

	private static final long serialVersionUID = 8738263383443372662L;

	private Integer value;

	public Annee() {
	}

	public Annee(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Annee other = (Annee) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

}

package com.shantery.thermo.entity;

import java.io.Serializable;


public class ThermoKey implements Serializable {
	private static final long serialVersionUID = 1L;
	private String user_id;
	private String thermo_id;
	
	
	//TODO 不具合起きなかったら下記メソッド消す
	/**@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((thermo_id == null) ? 0 : thermo_id.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
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
		ThermoKey other = (ThermoKey) obj;
		if (thermo_id == null) {
			if (other.thermo_id != null)
				return false;
		} else if (!thermo_id.equals(other.thermo_id))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}
	**/
	
}

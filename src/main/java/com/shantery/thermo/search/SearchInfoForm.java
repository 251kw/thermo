package com.shantery.thermo.search;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.shantery.thermo.util.ThermoConstants.*;

/**
 * @author y.sato
 * 検索条件form
 * 
 */
public class SearchInfoForm {

	private String sch_date;
	
	@Size(max = 64, message = NAME_OVER_ERROR)
	@Pattern(regexp = SCH_INFO_REGEX_PATTERN, message = NAME_ERROR)
	private String sch_name;
	
	private String sch_grade;
	private String sch_high;
	
	
	public String getSch_date() {
		return sch_date;
	}
	public void setSch_date(String sch_date) {
		this.sch_date = sch_date;
	}
	public String getSch_name() {
		return sch_name;
	}
	public void setSch_name(String sch_name) {
		this.sch_name = sch_name;
	}
	public String getSch_grade() {
		return sch_grade;
	}
	public void setSch_grade(String sch_grade) {
		this.sch_grade = sch_grade;
	}
	public String getSch_high() {
		return sch_high;
	}
	public void setSch_high(String sch_high) {
		this.sch_high = sch_high;
	}
	
}

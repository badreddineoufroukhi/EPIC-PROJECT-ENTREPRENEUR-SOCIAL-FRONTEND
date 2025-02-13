package ma.epicproject.backend.common.bean;

import java.io.Serializable;

public class EnumBean implements Serializable{

	private Integer id;
	private String label;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public EnumBean(Integer id, String label) {
		this.id = id;
		this.label = label;
	}

	public String toString() {
		return this.label;
	}

}

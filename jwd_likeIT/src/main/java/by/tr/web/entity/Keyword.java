package by.tr.web.entity;

import java.io.Serializable;

public class Keyword implements Serializable {

	private static final long serialVersionUID = 1L;

	private String keyword;

	public Keyword() {

	}

	public Keyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Keyword other = (Keyword) obj;
		if (keyword == null) {
			if (other.keyword != null) {
				return false;
			}
		} else if (!keyword.equals(other.keyword)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Keyword: keyword=" + keyword;
	}

}

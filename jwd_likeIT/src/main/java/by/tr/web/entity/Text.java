package by.tr.web.entity;

import java.io.Serializable;
import java.util.Date;

public abstract class Text implements Serializable {

	private static final long serialVersionUID = 1L;

	private String text;
	private String authorLogin;
	private Date creationDate;
	private int id;
	private double averageMark;

	public Text() {

	}

	public Text(int id, String text, String author, Date creationDate, double averageMark) {
		this.id = id;
		this.text = text;
		this.authorLogin = author;
		this.creationDate = creationDate;
		this.averageMark = averageMark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuthorLogin() {
		return authorLogin;
	}

	public void setAuthorLogin(String author) {
		this.authorLogin = author;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public double getAverageMark() {
		return averageMark;
	}

	public void setAverageMark(double averageMark) {
		this.averageMark = averageMark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((authorLogin == null) ? 0 : authorLogin.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		long temp;
		temp = Double.doubleToLongBits(averageMark);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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

		Text other = (Text) obj;
		if (authorLogin == null) {
			if (other.authorLogin != null) {
				return false;
			}
		} else if (!authorLogin.equals(other.authorLogin)) {
			return false;
		}
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		} else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (text == null) {
			if (other.text != null) {
				return false;
			}
		} else if (!text.equals(other.text)) {
			return false;
		}
		if (Double.doubleToLongBits(averageMark) != Double.doubleToLongBits(other.averageMark)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Text: text=" + text + ", authorLogin=" + authorLogin + ", creationDate=" + creationDate + ", id=" + id;
	}
}

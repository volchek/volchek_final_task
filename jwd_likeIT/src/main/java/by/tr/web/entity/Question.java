package by.tr.web.entity;

import java.util.Date;
import java.util.List;

public class Question extends Text {

	private static final long serialVersionUID = 1L;

	private String title;
	private List<Language> languages;

	public Question() {

	}

	public Question(int id, String text, String title, User author, Date creationDate, List<Language> languages) {
		super(id, text, author, creationDate);
		this.title = title;
		this.languages = languages;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLanguages(List<Language> languages) {
		this.languages = languages;
	}

	public List<Language> getLanguages() {
		return languages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((languages == null) ? 0 : languages.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Question other = (Question) obj;
		if (languages == null) {
			if (other.languages != null) {
				return false;
			}
		} else if (!languages.equals(other.languages)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Question: title=" + title + 
				", languages=" + languages + "; " + 
				super.toString();
	}

}

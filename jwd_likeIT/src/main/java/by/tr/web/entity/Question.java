package by.tr.web.entity;

import java.util.Date;
import java.util.List;

public class Question extends Text {

	private static final long serialVersionUID = 1L;

	private String title;
	private List<String> languages;
	private List<String> tags;
	private List<Answer> answers;

	public Question() {

	}

	public Question(int id, String text, String title, String author, Date creationDate, List<String> languages,
			List<String> tags, List<Answer> answers) {
		super(id, text, author, creationDate);
		this.title = title;
		this.languages = languages;
		this.tags = tags;
		this.answers = answers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	public void addLanguage(String language) {
		if (language != null) {
			languages.add(language);
		}
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void addTag(String tag) {
		if (tags != null) {
			tags.add(tag);
		}
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public void addAnswer(Answer newAnswer) {
		if (answers != null) {
			answers.add(newAnswer);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((languages == null) ? 0 : languages.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
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
		if (tags == null) {
			if (other.tags != null) {
				return false;
			}
		} else if (!tags.equals(other.tags)) {
			return false;
		}

		if (answers == null) {
			if (other.answers != null) {
				return false;
			}
		} else if (!answers.equals(other.answers)) {
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
		return "Question: title=" + title 
				+ ", languages=" + languages 
				+ ", tags=" + tags 
				+ ", answers=" + answers
				+ "; " + super.toString();
	}

}

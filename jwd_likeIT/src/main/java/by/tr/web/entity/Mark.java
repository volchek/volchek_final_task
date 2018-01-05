package by.tr.web.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Mark implements Serializable {

	private static final long serialVersionUID = 1L;

	private Text evaluatedText;
	private int score;
	private User user;
	private LocalDateTime creationDateTime;

	public Mark() {

	}

	public Mark(Text evaluatedText, int score, User user, LocalDateTime creationDateTime) {
		this.evaluatedText = evaluatedText;
		this.score = score;
		this.user = user;
		this.creationDateTime = creationDateTime;
	}

	public Text getEvaluatedText() {
		return evaluatedText;
	}

	public void setEvaluatedText(Text evaluatedText) {
		this.evaluatedText = evaluatedText;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(LocalDateTime creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDateTime == null) ? 0 : creationDateTime.hashCode());
		result = prime * result + ((evaluatedText == null) ? 0 : evaluatedText.hashCode());
		result = prime * result + score;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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

		Mark other = (Mark) obj;
		if (creationDateTime == null) {
			if (other.creationDateTime != null) {
				return false;
			}
		} else if (!creationDateTime.equals(other.creationDateTime)) {
			return false;
		}
		if (evaluatedText == null) {
			if (other.evaluatedText != null) {
				return false;
			}
		} else if (!evaluatedText.equals(other.evaluatedText)) {
			return false;
		}
		if (score != other.score) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Mark: evaluatedText=" + evaluatedText + 
				", score=" + score + 
				", user=" + user.getLogin() + 
				", creationDateTime=" + creationDateTime;
	}

}

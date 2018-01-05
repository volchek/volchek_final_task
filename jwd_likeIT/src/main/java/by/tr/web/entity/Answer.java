package by.tr.web.entity;

import java.util.Date;

public class Answer extends Text {

	private static final long serialVersionUID = 1L;

	private Question question;

	public Answer() {

	}

	public Answer(int id, String text, User author, Date creationDate, Question question) {
		super(id, text, author, creationDate);
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((question == null) ? 0 : question.hashCode());
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

		Answer other = (Answer) obj;
		if (question == null) {
			if (other.question != null) {
				return false;
			}
		} else if (!question.equals(other.question)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Answer: question=" + question + "; " + 
				super.toString();
	}

}

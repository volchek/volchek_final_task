package by.tr.web.entity;

import java.util.Date;

public class Answer extends Text {

	private static final long serialVersionUID = 1L;

	private int question;

	public Answer() {

	}

	public Answer(int id, String text, String author, Date creationDate, int question, int averageMark) {
		super(id, text, author, creationDate, averageMark);
		this.question = question;
	}

	public int getQuestion() {
		return question;
	}

	public void setQuestion(int question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + question;
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
		if (question != other.question) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Answer: question=" + question + "; " + super.toString();
	}

}

package by.tr.web.entity.text;

import java.util.Date;

public class Answer extends Text {

	private static final long serialVersionUID = 1L;

	private int question;
	private int markCount;

	public Answer() {

	}

	public Answer(int id, String text, String author, Date creationDate, int question, Double averageMark, int markCount) {
		super(id, text, author, creationDate, averageMark);
		this.question = question;
		this.markCount = markCount;
	}

	public int getQuestion() {
		return question;
	}

	public void setQuestion(int question) {
		this.question = question;
	}
	
	public int getMarkCount() {
		return markCount;
	}
	
	public void setMarkCount(int markCount) {
		this.markCount = markCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + question;
		result = prime * result + markCount;
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
		
		if (markCount != other.markCount) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Answer: question=" + question 
				+ "; markCount=" + markCount
				+ super.toString();
	}

}

package by.tr.web.controller.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class MarkTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String averageMark;
	private String defaultCharacter = "\u2014";

	public void setAverageMark(String averageMark) {
		this.averageMark = averageMark;
	}

	public int doStartTag() throws JspException {

		JspWriter out = pageContext.getOut();

		try {
			if (averageMark == null || averageMark.isEmpty() || averageMark.equals("null")) {
				out.write(defaultCharacter);
			} else {
				out.write(averageMark.substring(0, 3));
			}
		} catch (IOException ex) {
			throw new JspException("Error: " + ex.getMessage());
		}
		
		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}

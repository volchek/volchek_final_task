package by.tr.web.controller.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class SimpleQuestionTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String format;

	public void setFormat(String format) {
		this.format = format;
	}

	public int doStartTag() throws JspException {

		JspWriter out = pageContext.getOut();
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			out.print(formatter.format(today));
		} catch (IOException ex) {
			throw new JspException("Error: " + ex.getMessage());
		}

		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}
	
}

package by.tr.web.controller.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class DateTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String format = "dd.MM.yyyy";
	private Date date;
	private String text = "";

	public void setFormat(String format) {
		this.format = format;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int doStartTag() throws JspException {

		JspWriter out = pageContext.getOut();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			out.print(text);
			out.print(" ");
			out.print(formatter.format(date));
		} catch (IOException ex) {
			throw new JspException("Error: " + ex.getMessage());
		}
		return SKIP_BODY;
	}

	public int doEndTag() {
		return EVAL_PAGE;
	}

}

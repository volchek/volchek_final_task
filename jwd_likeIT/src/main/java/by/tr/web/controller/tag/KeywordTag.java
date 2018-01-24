package by.tr.web.controller.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class KeywordTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private String cssClass;
	private List<String> keywordList;

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setKeywordList(List<String> keywordList) {
		this.keywordList = keywordList;
	}

	public int doStartTag() throws JspException {

		JspWriter out = pageContext.getOut();
		try {
			for (String keyword : keywordList) {
				out.write("<span class='" + cssClass + "'>");
				out.write(keyword);
				out.write("</span>");
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

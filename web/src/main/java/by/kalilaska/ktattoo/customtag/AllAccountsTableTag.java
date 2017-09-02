package by.kalilaska.ktattoo.customtag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.kalilaska.ktattoo.bean.AccountBean;

public class AllAccountsTableTag extends TagSupport {
	private final static Logger LOGGER = LogManager.getLogger(AllAccountsTableTag.class);
	private List<AccountBean> accountList;
	private boolean allowed;
	
	public void setAccountList(List<AccountBean> accountList) {
		this.accountList = accountList;
	}
	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

	@Override
    public int doStartTag() throws JspTagException {		
		JspWriter out = pageContext.getOut();
		try {
			out.write("<table class=\"table table-hover\">");
			out.write("<thead>");
			out.write("<tr>");
			out.write("<th>#</th>");
			out.write("<th>Id</th>");
			out.write("<th>Name</th>");
			out.write("<th>Email</th>");
			out.write("<th>Phone</th>");
			out.write("<th>Role</th>");
			out.write("<th>Allowed</th>");
			out.write("</tr>");
			out.write("</thead>");
			out.write("<tbody id=\"accounts-data\">");
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
		}
		return EVAL_BODY_INCLUDE;		
	}
	
	@Override
    public int doAfterBody() throws JspTagException {
		JspWriter out = pageContext.getOut();
		try {
			if(accountList != null) {
				for (AccountBean account : accountList) {				
					if(account.isAllowed() == allowed) {
						out.write("<tr id='account-" + account.getId() + "' class='accounts-table-linkrow' onclick='workWithAccountsTable.selectAccount(this.id)'>");
						out.write("<td>");
						out.write("<div class='checkbox'>");
						out.write("<label><input type='checkbox' id='checkbox-account-" + account.getId() + "' onclick = 'workWithAccountsTable.selectAccount(this.id)' unchecked></label>");
						out.write("</div>");
						out.write("</td>");
						out.write("<td>" + account.getId() + "</td>");
						out.write("<td id='accountName-" + account.getId() + "'>" + account.getName() + "</td>");
						out.write("<td id='accountEmail-" + account.getId() + "'>" + account.getEmail() + "</td>");
						out.write("<td id='accountPhone-" + account.getId() + "'>" + handleNullData(account.getPhone()) + "</td>");
						out.write("<td id='accountRole-" + account.getId() + "'>" + account.getRole() + "</td>");
						out.write("<td>" + account.isAllowed() + "</td>");
						out.write("</tr>");					
					}
				}
			}			
				
		}catch (IOException e) {
			LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
		}			
		
		return SKIP_BODY;
	}
	
	private String handleNullData(String str) {
		if(str == null || str.isEmpty()) {
			return "-";
		}
		return str;
	}
	
	@Override
	public int doEndTag() throws JspTagException {
		 JspWriter out = pageContext.getOut();
		 
		 try {
			 out.write("</tbody>");
			 out.write("</table>");
			 
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, "output exception: " + e.getMessage());
		}
		 
		 return EVAL_PAGE;		 
	 }
}

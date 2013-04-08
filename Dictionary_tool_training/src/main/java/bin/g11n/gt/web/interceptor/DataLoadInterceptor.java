package bin.g11n.gt.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * forbid url access from address bar.
 * 
 * DataLoadInterceptor.java
 * 
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history (C) Copyright bingogo1@hotmail.com, LP - All Rights Reserved.
 */
public class DataLoadInterceptor extends AbstractInterceptor {

	public DataLoadInterceptor() {
	}

	/**
	 * intercept
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String intercept(ActionInvocation invocation) throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		// check the required url
		String requiredUrl = request.getRequestURL().toString();
		int start = requiredUrl.indexOf("-");
		int end = requiredUrl.indexOf("!");
		// get action string with the same as urltag
		// gotten:expenseApplicationEditJC-395
		if (end == -1) {
			// get rid of the method and '.action' of the url.
			end = requiredUrl.lastIndexOf(".");
		}
		// if url contains id, and this url has not been saved in the session,
		// redirect to logout.
		if (start != -1 && end != -1
				&& !"0".equals(requiredUrl.substring(start + 1, end))) {
			List<String> list = (List<String>) request.getSession()
					.getAttribute("dataAuthCheck");
			String requredAction = requiredUrl.substring(requiredUrl
					.lastIndexOf("/") + 1, end);
			if (list != null && !list.contains(requredAction)) {
				// address bar is forbidden.
				// TODO after all jsp have been updated, this line should be
				// enabled.
				response.sendRedirect("logout.jsp");
			}
		}
		return invocation.invoke();

	}

	public void init() {
	}

}

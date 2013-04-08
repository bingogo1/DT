package bin.g11n.gt.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * EncodingFilter.java
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class EncodingFilter implements Filter { 
    private String encoding = null;

	public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        if (encoding != null) {
        	System.setProperty("file.encoding", encoding);
            request.setCharacterEncoding(encoding);
            response.setContentType("text/html; charset=" + encoding);
        }
        chain.doFilter(request, response);
	}

	public void destroy() {
	}

}

package bin.g11n.gt.web.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.views.jsp.URLTag;

import com.opensymphony.xwork2.inject.Container;

/**
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class GtURLTag extends URLTag {

	public GtURLTag() {
		super();
	}

    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);
        
        populateParams();
        boolean evalBody = component.start(pageContext.getOut());

        if (evalBody) {
        	int start = action.indexOf("-");
    		int end = action.indexOf("!");
        	String toSaveUrl;
    		if (end == -1){
    			//get rid of the method and '.action' of the url.
    			end = action.lastIndexOf(".");
    		}
        	if (start != -1 ){
            	//set request for the url <url, flag>
            	List<String> list = (List<String>)pageContext.getSession().getAttribute("dataAuthCheck");
            	if (end != -1 ){
            		toSaveUrl = action.substring(0, end);
            	}else {
            		toSaveUrl = action;
            	}
            	
            	if (list == null){
            		list = new ArrayList<String>();
            	}
            	if (!list.contains(toSaveUrl)){
        			//if the url contains id of record, it need check.
            		//add tag's url into map
            		list.add(toSaveUrl);
                	if (list.size() > 500){
                		//limit the maximum size of list. remove 100 old records from it. 
                		for (int i = 0; i < 100; i ++){
                			list.remove(i);
                		}
                	}
        		}
            	pageContext.getSession().setAttribute("dataAuthCheck", list);
            	//set it to get rid of the parameters added by displaytag.
            	includeParams = "none";
    		}
        	
            return component.usesBody() ? EVAL_BODY_BUFFERED : EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

}

package bin.g11n.gt.web.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.gt.web.listener.StartupListener;


/**
 * This class is used to reload the drop-downs initialized in the
 * StartupListener.
 *
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class ReloadAction extends BaseAction {
    private static final long serialVersionUID = 295460450224891051L;

    public String execute() throws IOException {
        StartupListener.setupContext(getSession().getServletContext());

        String referer = getRequest().getHeader("Referer");
        HttpServletResponse response = ServletActionContext.getResponse();
        
        if (referer != null) {
        	logger.log(ILogger.ELevel.INFO, "reload complete, reloading user back to: " + referer);     
            saveMessage(getText("reload.succeeded"));
            response.sendRedirect(response.encodeRedirectURL(referer));
            return SUCCESS;
        } else {
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Context Reloaded</title>");
            out.println("</head>");
            out.println("<body bgcolor=\"white\">");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Reloading options succeeded! Click OK to continue.');");
            out.println("history.back();");
            out.println("</script>");
            out.println("</body>");
            out.println("</html>");
        }

        return SUCCESS;
    }
}

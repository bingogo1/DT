package bin.g11n.gt.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bin.g11n.gt.util.ByteArrayOutputStreamWrapper;
import bin.g11n.gt.util.ByteArrayResponseWrapper;

/** 
 * Filter that compresses output with gzip (assuming that browser supports gzip).
 * <P>
 * Taken from More Servlets and JavaServer Pages from Prentice Hall and 
 * Sun Microsystems Press, http://www.moreservlets.com/.
 * &copy; 2002 Marty Hall; may be freely used or adapted.
 *
 * @web.filter name="CompressionFilter"
 */

public class CompressionFilter implements Filter {
    
    private static Log mLogger = LogFactory.getLog(CompressionFilter.class);
    
    private boolean enabled = true;
    
    
    /** 
     * If browser does not support gzip, invoke resource normally. If browser 
     * does support gzip, set the Content-Encoding response header and invoke 
     * resource with a wrapped response that collects all the output. Extract 
     * the output and write it into a gzipped byte array. Finally, write that 
     * array to the client's output stream.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        if (!this.enabled || !isGzipSupported(req)) {
            // Invoke resource normally.
            chain.doFilter(req, res);
        } else {
            // Tell browser we are sending it gzipped data.
            res.setHeader("Content-Encoding", "gzip");
            
            // Invoke resource, accumulating output in the wrapper.
            ByteArrayResponseWrapper responseWrapper =
                    new ByteArrayResponseWrapper(response);
            
            chain.doFilter(req, responseWrapper);
            
            ByteArrayOutputStream outputStream = responseWrapper.getByteArrayOutputStream();
            
            // Get character array representing output.
            mLogger.debug("Pre-zip size:" + outputStream.size());
            
            // Make a writer that compresses data and puts
            // it into a byte array.
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            GZIPOutputStream zipOut = new GZIPOutputStream(byteStream);
            
            // Compress original output and put it into byte array.
            zipOut.write(responseWrapper.getByteArrayOutputStream().toByteArray());
            
            // Gzip streams must be explicitly closed.
            zipOut.close();
            
            mLogger.debug("Gzip size:" + byteStream.size());
            
            // Update the Content-Length header.
            res.setContentLength(byteStream.size());
            
            ByteArrayOutputStreamWrapper newOut =
                    (ByteArrayOutputStreamWrapper) responseWrapper.getOutputStream();
            newOut.clear();
            newOut.setFinallized();
            
            /* now force close of OutputStream */
            newOut.write(byteStream.toByteArray());
            newOut.close();
        }
        
    }
    

    public void init(FilterConfig config) throws ServletException {
        this.enabled = true;
    }
    
    
    public void destroy() {}
    
    
    private boolean isGzipSupported(HttpServletRequest req) {
        String browserEncodings = req.getHeader("Accept-Encoding");
        return ((browserEncodings != null)
                    && (browserEncodings.indexOf("gzip") != -1));
    }
    
}

package bin.g11n.gt.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

/*
 *
 * Implementation of ServletOutputStream that allows the filter to hold the
 * Response content for insertion into the cache.
 */
public class ByteArrayOutputStreamWrapper extends ServletOutputStream
{
    protected OutputStream intStream;
    protected ByteArrayOutputStream baStream;
    protected boolean finallized = false;
    protected boolean flushOnFinalizeOnly = true;

    public ByteArrayOutputStreamWrapper(OutputStream outStream)
    {
        intStream = outStream;
        baStream = new ByteArrayOutputStream();
    }

    public ByteArrayOutputStreamWrapper()
    {
        intStream = System.out;
        baStream = new ByteArrayOutputStream();
    }

    public ByteArrayOutputStream getByteArrayStream()
    {
        return baStream;
    }

    public void setFinallized()
    {
        finallized = true;
    }

    public boolean isFinallized()
    {
        return finallized;
    }


    public void write(int i) throws java.io.IOException
    {
        baStream.write(i);
    }

    public void close() throws java.io.IOException
    {
        if (finallized) {
            processStream();
            intStream.close();
        }
    }

    public void flush() throws java.io.IOException
    {
        if (baStream.size() != 0) {
            if (!flushOnFinalizeOnly || finallized) {
                processStream();
                baStream = new ByteArrayOutputStream();
            }
        }
    }

    protected void processStream() throws java.io.IOException
    {
        intStream.write(baStream.toByteArray());
        intStream.flush();
    }
    
    public void clear()
    {
        baStream = new ByteArrayOutputStream();
    }
}

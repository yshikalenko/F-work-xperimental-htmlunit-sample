package org.shikalenko.xprmntl.srv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yshikalenko
 *
 */
public class Servlet extends HttpServlet {

    private static final long serialVersionUID = 8061188004219032566L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        StringBuilder pathb = new StringBuilder("WEB-INF/app");
        if (pathInfo != null) {
            pathb.append('/').append(pathInfo);
        }
        String path = pathb.toString();
        String realPath = getServletContext().getRealPath(path);
        File template;
        try {
            template = checkFile(new File(realPath));
        } catch (FileNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
            return;
        }
        String buffer = readFile(template);
        output(resp, buffer);
        
        /*
        ServletOutputStream output = resp.getOutputStream();
        output.println("pathInfo = " + doubleQuoute(pathInfo));
        output.println("path = " + doubleQuoute(path));
        output.println("realPath = " + doubleQuoute(realPath));
        */
    }

    /**
     * @param resp
     * @param value
     * @throws IOException 
     */
    private void output(HttpServletResponse resp, String value) throws IOException {
        try (Writer writer = resp.getWriter()) {
            writer.write(value);
        }
    }

    /**
     * @param file
     * @return
     */
    private String readFile(File file) throws IOException {
        StringBuilder result;
        try (InputStream in = new FileInputStream(file)) {
            try (Reader reader = new InputStreamReader(in, "UTF-8")) {
                result = new StringBuilder();
                char[] buffer = new char[256];
                int read;
                while ((read = reader.read(buffer)) >= 0) {
                    if (read > 0) {
                        result.append(buffer, 0, read);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * @param file
     * @return
     * @throws FileNotFoundException 
     */
    private File checkFile(File file) throws FileNotFoundException {
        if (!file.isFile()) {
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        return file;
    }

    /**
     * @param value
     * @return
     */
    private String doubleQuoute(String value) {
        return quote(value, '"');
    }

    /**
     * @param value
     * @param c
     * @return
     */
    private String quote(String value, char c) {
        return value == null ? "null" : new StringBuilder().append(c).append(value).append(c).toString();
    }
    
    

}

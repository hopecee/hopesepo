/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

/**
 *
 * @author hope
 */
import java.io.*;
import java.util.ResourceBundle;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

@WebServlet(value = "/pluploadServlet", name = "pluploadServlet")
public class PluploadServlet extends HttpServlet {

     private ResourceBundle bundle = ResourceBundle.getBundle("constants");
    private final String UPLOADS_DIR = bundle.getString("uploadsDir");
    
    
    private static final long serialVersionUID = 3447685998419256747L;
    private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
    private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
    public static final String JSON = "application/json";
    public static final int BUF_SIZE = 2 * 1024;
    public String FileDir;
    private int chunk;
    private int chunks;
    private String name;
    private String user;
    private String time;
    // private String newFileName;

    @Override
    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        //this.imagePath = "C:\\Documents and Settings\\hope\\proshopImages";
//this.FileDir = getInitParameter("FileDir");
       this.FileDir = UPLOADS_DIR;
        //this.imagePath = PlatformConfig.getProperty("LocalOperation.Images.Path", null);
        // logger.debug("imagePath = " + imagePath);

        // In a Windows environment with the Applicationserver running on the
        // c: volume, the above path is exactly the same as "c:\images".
        // In UNIX, it is just straightforward "/images".
        // If you have stored files in the WebContent of a WAR, for example in
        // the
        // "/WEB-INF/images" folder, then you can retrieve the absolute path by:
        // this.imagePath = getServletContext().getRealPath("/WEB-INF/images");
    }

    /**
     * Handles an HTTP POST request from Plupload.
     *
     * @param req The HTTP request
     * @param resp The HTTP response
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String responseString = RESP_SUCCESS;
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        System.out.println("test==============================");
        if (isMultipart) {
            ServletFileUpload upload = new ServletFileUpload();
            try {
                System.out.println("test==============isMultipart");

                FileItemIterator iter = upload.getItemIterator(req);
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    InputStream input = item.openStream();

                    // Handle a form field.
                    if (item.isFormField()) {
                        String fileName = item.getFieldName();
                        String value = Streams.asString(input);
                        System.out.println("/fileName---sc /" + fileName);
                        if ("name".equals(fileName)) {
                            this.name = value;
                            System.out.println("/value /" + value);
                        } else if ("chunks".equals(fileName)) {
                            this.chunks = Integer.parseInt(value);
                        } else if ("chunk".equals(fileName)) {
                            this.chunk = Integer.parseInt(value);
                        } else if ("user".equals(fileName)) {
                            this.user = value;
                        } else if ("time".equals(fileName)) {
                            this.time = value;
                        }
                    } // Handle a multi-part MIME encoded file.
                    else {
                        //String fileDir = req.getSession().getServletContext().getRealPath("/")+FileDir;

                        // System.out.println("/==/" + req.getSession().getServletContext());


                        String fileDir = req.getContextPath() + FileDir;

                        String requestedImage = req.getPathInfo();
                        System.out.println("/requestedImage /" + requestedImage);

                        String sc = req.getContextPath();
                        System.out.println("/sc /" + sc);
                        System.out.println("/sc /" + fileDir);
                        File dstFile = new File(fileDir);
                        File dstFile2 = new File(fileDir);
                        if (!dstFile.exists()) {
                            dstFile.mkdirs();
                        }
                        if (!dstFile2.exists()) {
                            dstFile2.mkdirs();
                        }
                        //String newFileName = req.getParameter("newFileName");

                        String inputFileName = this.name;
                        int mid = inputFileName.lastIndexOf(".");
                        String fname = inputFileName.substring(0, mid);
                        String ext = inputFileName.substring(mid + 1, inputFileName.length());
                        System.out.println("File name =" + fname);
                        System.out.println("Extension =" + ext);

                        String inputFileName2 = this.name;
                        int mid2 = inputFileName2.lastIndexOf(".");
                        String fname2 = inputFileName2.substring(0, mid2);
                        String ext2 = inputFileName2.substring(mid2 + 1, inputFileName2.length());
                        //setSize(width, height);


                        File dst = new File(dstFile.getPath() + "/" + fname + "." + ext);
                        // File dst_1 = new File(dstFile.getPath() + "/" + fname + "_1." + ext);
                        File dst_temp = new File(dstFile.getPath() + "/" + fname2 + "_temp." + ext2);

                        if (dst.exists()) {

                            dst.delete();

                        }
                        if (dst_temp.exists()) {

                            dst_temp.delete();
                        }
                        saveUploadFile(input, dst);
                        dst.setExecutable(false);
                        saveUploadFile(input, dst_temp);
                        dst_temp.setExecutable(false);

                    }
                }
            } catch (Exception e) {
                responseString = RESP_ERROR;
            }
        } // Not a multi-part MIME request.
        else {
            responseString = RESP_ERROR;
        }

        if (this.chunk == this.chunks - 1) {
            System.out.println("user: " + this.user);
            System.out.println("name: " + this.name);
            System.out.println("time: " + this.time);
        }

        resp.setContentType(JSON);
        byte[] responseBytes = responseString.getBytes();
        resp.setContentLength(responseBytes.length);
        //ServletOutputStream output = resp.getOutputStream();
        // output.write(responseBytes);
        // output.flush();
        resp.getOutputStream().write(responseBytes);
        resp.getOutputStream().flush();

    }

    /**
     * Saves the given file item (using the given input stream) to the web
     * server's local temp directory.
     *
     * @param input The input stream to read the file from
     * @param dst The dir of upload
     */
    private void saveUploadFile(InputStream input, File dst) throws IOException {
        OutputStream out = null;
        try {
            if (dst.exists()) {
                out = new BufferedOutputStream(new FileOutputStream(dst, true),
                        BUF_SIZE);

            } else {
                out = new BufferedOutputStream(new FileOutputStream(dst),
                        BUF_SIZE);
            }

            byte[] buffer = new byte[BUF_SIZE];
            int len = 0;
            while ((len = input.read(buffer)) > 0) {
                out.write(buffer, 0, len);

            }
        } catch (Exception e) {
        } finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
        }
    }
}

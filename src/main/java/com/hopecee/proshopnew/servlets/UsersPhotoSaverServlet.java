/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopecee.proshopnew.servlets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hope
 */
@WebServlet(value = "/usersPhotoSaverServlet", name = "usersPhotoSaverServlet",
        initParams = {
    @WebInitParam(name = "outputUsersImageDstDir", value = "C:/proShopDir")})
public class UsersPhotoSaverServlet extends HttpServlet {

    private static final long serialVersionUID = 344768599841925674L;
    private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
    private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
    public static final String JSON = "application/json";
    public static final int BUF_SIZE = 2 * 1024;
    private String inputImageDstDir;
    private String outputImageDstDir;
    private int chunk;
    private int chunks;
    private String name;
    private String user;
    private String time;

    @Override
    public void init() throws ServletException {

        // Define base path somehow. You can define it as init-param of the servlet.
        //this.imagePath = "C:\\Documents and Settings\\hope\\proshopImages";
        // this.inputImageDstDir = getInitParameter("inputImageDstDir");
        this.outputImageDstDir = getInitParameter("outputUsersImageDstDir");
        //this.imagePath = PlatformConfig.getProperty("LocalOperation.Images.Path", null);
        //System.out.println("inputImageDstDir = " + inputImageDstDir);
        System.out.println("outputUsersImageDstDir = " + outputImageDstDir);
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

        System.out.println("DDDDDDDDDkh = " + outputImageDstDir);
        String stringX1 = req.getParameter("x1");
        String stringX2 = req.getParameter("x2");
        String stringY1 = req.getParameter("y1");
        String stringY2 = req.getParameter("y2");
        String stringScaledImgWidth = req.getParameter("scaledImgWidth");
        String stringScaledImgHeight = req.getParameter("scaledImgHeight");
        String stringOutSizeWidth = req.getParameter("outSizeWidth");
        String stringOutSizeHeight = req.getParameter("outSizeHeight");
        String userNeo4jIdStr = req.getParameter("userNeo4jId");



        double amount = Double.parseDouble(stringScaledImgWidth);
        double amount2 = Double.parseDouble(stringScaledImgHeight);


        // System.out.println(amount);
        // System.out.println(amount2);

        //Convert String numbers to double
        double x1 = Double.parseDouble(stringX1);
        double x2 = Double.parseDouble(stringX2);
        double y1 = Double.parseDouble(stringY1);
        double y2 = Double.parseDouble(stringY2);
        double scaledImgWidth = Double.parseDouble(stringScaledImgWidth);
        double scaledImgHeight = Double.parseDouble(stringScaledImgHeight);
        //Convert String numbers to Integer
        int outSizeWidth = Integer.parseInt(stringOutSizeWidth);
        int outSizeHeight = Integer.parseInt(stringOutSizeHeight);

        /*
         //Convert String numbers to Integer
         int x1 = Integer.parseInt(stringX1);
         int x2 = Integer.parseInt(stringX2);
         int y1 = Integer.parseInt(stringY1);
         int y2 = Integer.parseInt(stringY2);
         int scaledImgWidth = Integer.parseInt(stringScaledImgWidth);
         int scaledImgHeight = Integer.parseInt(stringScaledImgHeight);
         int outSizeWidth = Integer.parseInt(stringOutSizeWidth);
         int outSizeHeight = Integer.parseInt(stringOutSizeHeight);
         */


        System.out.println(x1);
        System.out.println(x2);
        System.out.println(y1);
        System.out.println(y2);
        System.out.println(scaledImgWidth);
        System.out.println(scaledImgHeight);
        System.out.println(outSizeWidth);
        System.out.println(outSizeHeight);
        System.out.println(userNeo4jIdStr);



        //URL path = req.getServletContext().getResource("/uploads/" + usersId + ".jpg");
        // String fileDir = inputImageDstDir;
        //String imgName = usersId + ".jpg";
// the leading slash is important
        InputStream fileInputStream = null;
        String inputImgFile = null;
        String inputImgFile_temp = null;
        String inputImgFile_del = null;
        String del_suffix = null;
        String pathDIR = null;
        String path = null;

        try {
            inputImgFile = userNeo4jIdStr + ".jpg";
            inputImgFile_temp = userNeo4jIdStr + "_temp.jpg";
            del_suffix = "_del.jpg";

            fileInputStream = req.getServletContext().getResourceAsStream("/resources/uploads/" + inputImgFile);
            path = req.getServletContext().getRealPath("/resources/uploads/" + inputImgFile_temp);
            pathDIR = req.getServletContext().getRealPath("/resources/uploads");
            System.out.println(path);


        } catch (Exception e) {
            System.out.println(e);

        }

        BufferedImage inputBufferedImage = ImageIO.read(fileInputStream);

        // int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

        System.out.println("/inputBufferedImage /" + inputBufferedImage.toString());
        //Output File.
        String outputDIR = outputImageDstDir;
        File dstFile = new File(outputDIR + File.separator + userNeo4jIdStr);
        //Check if directory exist, if not mkdir.
        if (!dstFile.exists()) {
            dstFile.mkdirs();
            System.out.println("/dstFile /" + dstFile.toString());
        }

        //Save files 80x100
        File dst = new File(dstFile.getPath() + File.separator + inputImgFile);
        double factor = 1.0;
        saveUploadFile(inputBufferedImage, dst, x1, y1, x2, y2, scaledImgWidth, scaledImgHeight, outSizeWidth, outSizeHeight, null, factor);

        //80x80 Image size.
        String inputImgFile_80x80 = userNeo4jIdStr + "_80x80.jpg";
        File dst_80x80 = new File(dstFile.getPath() + File.separator + inputImgFile_80x80);
        double factor_80x80 = 0.8;
        int outSizeWidth_80x80 = outSizeWidth;
        int outSizeHeight_80x80 = outSizeWidth;
        saveUploadFile(inputBufferedImage, dst_80x80, x1, y1, x2, y2, scaledImgWidth, scaledImgHeight, outSizeWidth_80x80, outSizeHeight_80x80, null, factor_80x80);

        //340x340 Image size.
        String inputImgFile_340x340 = userNeo4jIdStr + "_340x340.jpg";
        File dst_340x340 = new File(dstFile.getPath() + File.separator + inputImgFile_340x340);
        System.out.println("/dst_340x340 /" + dst_340x340.toString());
        double factor_340x340 = 0.8;
        int outSizeHeight_340x340 = outSizeWidth * 3;
        int outSizeWidth_340x340 = outSizeWidth * 3;
        saveUploadFile(inputBufferedImage, dst_340x340, x1, y1, x2, y2, scaledImgWidth, scaledImgHeight, outSizeWidth_340x340, outSizeHeight_340x340, null, factor_340x340);


        // Delete temp file.
        File file_temp = new File(path);
        System.out.println("/file_temp /" + file_temp.toString());
        file_temp.delete();

        //Find and delete the uneeded files. 
        File directory = new File(pathDIR);
        File[] files = directory.listFiles();
        for (int index = 0; index < files.length; index++) {

            if (files[index].isFile() && !files[index].getName().endsWith("user_blank.jpg")) {
                if (files[index].getName().endsWith(del_suffix)) {
                    int fileMinusEXT = files[index].getPath().lastIndexOf("_del.");
                    String fileMinusEXTString = files[index].getPath().substring(0, fileMinusEXT);
                    System.out.println("/fileMinusEXTString /" + fileMinusEXTString);
                    String newFileString = fileMinusEXTString.concat(".jpg");

                    System.out.println("/newFileSring /" + newFileString);
                    File newFile = new File(newFileString);
                    newFile.delete();
                    files[index].delete();

                }
                //Delet file that has no _temp.
                if (!files[index].getName().endsWith("_temp.jpg")) {
                    files[index].delete();
                }
            }
        }
        //Write the _del file for control. 

        File newDst = new File(pathDIR + File.separator + userNeo4jIdStr + "_del.jpg");
        //File newDst = new File(dstFile.getPath() + File.separator + usersId + "_del.jpg");

        newDst.createNewFile();
        // File newFile = new File(dst);  
        //ImageIO.write(inputBufferedImage, usersId + "_del.jpg", dst);
    }

    /**
     * Saves the given file item (using the given input stream) to the web
     * server's local temp directory.
     *
     * @param input The input stream to read the file from
     * @param dst The dir of upload
     */
    private void saveUploadFile(BufferedImage inputBufferedImage, File dst,
            double x1, double y1, double x2, double y2, double scaledImgWidth, double scaledImgHeight, int outSizeWidth, int outSizeHeight, String bgColor, double factor) throws IOException {
        // Color BG_COLOUR = Color.getColor(bgColor);
        //OutputStream out = null;
        try {
            int type = inputBufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : inputBufferedImage.getType();

            BufferedImage outputImage = new BufferedImage(outSizeWidth, outSizeHeight, type);

            Graphics2D g = outputImage.createGraphics();

            BufferedImage clipped = null;

            try {

                double ibiw1 = (double) inputBufferedImage.getWidth();
                double ibih = (double) inputBufferedImage.getHeight();
                double siw1 = scaledImgWidth;
                double sih1 = scaledImgHeight;
                //casting them to double.
                //double siw1 = siw;
                //double ibiw1 = ibiw;
                double ibiw1_siw1 = ibiw1 / siw1;

                double ibih1 = ibih * factor;
                //double sih1 = sih;
                double ibih1_sih1 = ibih1 / sih1;

                double xd = x1 * ibiw1_siw1;
                double yd = y1 * ibih1_sih1;
                double wd = (x2 - x1) * ibiw1_siw1;
                double hd = (y2 - y1) * ibih1_sih1;
                //convert them bact to integer.
                int x = (int) xd;
                int y = (int) yd;
                int w = (int) wd;
                int h = (int) hd;

                // System.out.println("scaledImgWidth: " + scaledImgWidth);
                // System.out.println("scaledImgHeight: " + scaledImgHeight);
                //System.out.println("w: " + w);
                //System.out.println("h: " + h);
                //System.out.println("x2: " + x2);
                //System.out.println("y2: " + y2);
                //System.out.println("ibiw: " + ibiw);
                //System.out.println("ibih: " + ibih);

                clipped = inputBufferedImage.getSubimage(x, y, w, h);
            } catch (Exception e) {
                System.out.println(e);

            }

            g.drawImage(clipped, 0, 0, outSizeWidth, outSizeHeight, null);

            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.setComposite(AlphaComposite.Src);
            g.dispose();

            ImageIO.write(outputImage, "jpg", dst);
            // dst.setWritable(true);//new hope
        } catch (IOException e) {
            // May as well use what is given...
            System.out.println("read error:" + e.getMessage());
        }


    }
}

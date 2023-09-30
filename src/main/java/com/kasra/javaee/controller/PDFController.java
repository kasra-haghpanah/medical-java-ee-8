package com.kasra.javaee.controller;

//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfWriter;
//import com.itextpdf.text.pdf.codec.Base64;
//import com.itextpdf.tool.xml.XMLWorkerHelper;

import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.framework.web.mvc.statics.Status;
import gui.ava.html.image.generator.HtmlImageGenerator;
import org.apache.commons.lang.StringEscapeUtils;
import webkit.PDF;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by kasra.haghpanah on 29/11/2016.
 */
@Named
@SessionScoped
@Controller(url = Status.DISABLED)
public class PDFController implements Serializable {

    @PostConstruct
    public void init() {
        System.out.println("Start PDFController!");
    }

    String convertToUTF8(String html) {
        try {
            return new String(html.getBytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @WebRequest(url = "htmltopdf.html", method = Method.POST)
    public void icon(HttpServletRequest request,
                     HttpServletResponse response,
                     KasraServlet servlet) {


        String html = request.getParameter("html");
        //html = convertToUTF8(html);
        String format = request.getParameter("format");
        String fileName = "export.pdf";


        String path = servlet.getContextRoot() + "/src/main/webapp/webkit/";

        //servlet.download(response, fileName, createPDFbyItext(html));
        if (format == null || format.equals("") || format.toLowerCase().equals("pdf")) {

            String myHtml = StringEscapeUtils.unescapeJavaScript(html);
            html = StringEscapeUtils.escapeHtml(myHtml);
            myHtml = myHtml.replace("&lt;","<").replace("&amp;" , "&").replace("&gt;" , ">").replace("&quot;" , "\"");
            response.setHeader("filename" , fileName);
            servlet.download(response, fileName, PDF.create(myHtml) /*getPdf(html, true, null)*/);  /*webkitHtmlToPdf(html, path)*/
        }

        //servlet.download(response, "export.png", createImagebyHTML(html , "png"));
        else {
            fileName = "export.png";
            response.setHeader("filename" , fileName);
            servlet.download(response, "export.png", getPdf(html, false, path) /*webkitHtmlToImage(html, path)*/);
        }


    }

    public byte[] getPdf(String html, boolean isPDF, String pathDir) {
        byte[] results = null;
        try {
            // For piping in and out, we need separate args for this for some reason
            String command = "wkhtmltopdf --encoding 'UTF-8' - -";
            if (!isPDF) {
                html = StringEscapeUtils.unescapeJavaScript(html);
                html = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /></head><body>" + html + "</body></html>";
                writeTextFile(pathDir + "webkit.html", html);
                command = "wkhtmltoimage --encoding 'UTF-8' --format png " + pathDir + "webkit.html " + pathDir + "webkit.png";
            } else {
                html = StringEscapeUtils.unescapeJavaScript(html);
                html = StringEscapeUtils.escapeHtml(html);
                html = html.replace("&lt;","<").replace("&amp;" , "&").replace("&gt;" , ">").replace("&quot;" , "\"");
                //html = "<html><head><meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" /></head><body>" + html + "</body></html>";

            }

            ProcessBuilder builder = new ProcessBuilder(command.split(" "));
            // this eats up stderr but prevents us from having to handle it ourselves
            builder.redirectErrorStream(false);

            Process process = builder.start();

            try (BufferedOutputStream stdin = new BufferedOutputStream(process.getOutputStream())) {
                stdin.write(html.getBytes());
            }

            try (BufferedInputStream stdout = new BufferedInputStream(process.getInputStream()); ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
                // Couldn't get a buffered read working properly without knowing the length of the result, so reading byte by byte instead.
                // BufferedInputStream should be buffering internally with 8192 bytes, and
                // ByteArrayOutputStream works similar to a vector with an internal byte array, so performance shouldn't be too bad.
                // TODO: properly buffer this
                while (true) {
                    int x = stdout.read();
                    if (x == -1) {
                        break;
                    }
                    outputStream.write(x);
                }

/*                if (!isPDF) {
                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(outputStream.toByteArray()));
                    ImageIO.write(bufferedImage, "PNG", outputStream);
                }*/

                results = outputStream.toByteArray();
                process.waitFor();
            }
        } catch (Exception e) {

            //return null;
        }

        if (results == null || results.length == 0) {
            Path path = Paths.get(pathDir + "webkit.png");
            try {
                results = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    byte[] createImagebyHTML(String html, String format) {
        HtmlImageGenerator imageGenerator = new HtmlImageGenerator();

        imageGenerator.loadHtml(html);
        BufferedImage bufferedImage = imageGenerator.getBufferedImage();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, format, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] imageInByte = outputStream.toByteArray();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return imageInByte;

    }

    byte[] createPDFbyItext(String html) {
/*
        ByteArrayOutputStream documentBody = new ByteArrayOutputStream();
        try {
            BufferedOutputStream out = new BufferedOutputStream(documentBody);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, documentBody);
            document.open();


            // if required, you can add document meta data
            document.addAuthor("Ravinder");
            //document.addCreator( creator );
            document.addSubject("HtmlWoker Parsed Pdf from iText");
            document.addCreationDate();
            document.addTitle("HtmlWoker Parsed Pdf from iText");


            //HTMLWorker htmlWorker = new HTMLWorker( document );
            //htmlWorker.parse( new StringReader( html.toString() ) );



            byte ptext[] = html.getBytes();
            html = new String(ptext, "UTF-8");
            InputStream is = new ByteArrayInputStream(html.toString().getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  documentBody.toByteArray();
        */

//        Process p = Runtime.getRuntime().exec("wkhtmltoimage " + html + "ddd.png");
//        p.waitFor();


        return null;
    }


    void writeTextFile(String fileName, String content) {
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            out.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}

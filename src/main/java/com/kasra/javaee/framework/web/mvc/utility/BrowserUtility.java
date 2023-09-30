package com.kasra.framework.utility;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kasra.haghpanah on 03/05/2017.
 */

// MultipartUtility

public class BrowserUtility {

    public static void basicAuthentication(final String username , final String password){

        Authenticator myAuth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        };

        Authenticator.setDefault(myAuth);
    }

    private static List<String> cookies = new ArrayList<String>();

    private static final String LINE_FEED = "\r\n";

    /**
     * This constructor initializes a new HTTP POST request with content type
     * is set to multipart/form-data
     *
     * @param requestURL
     * @param charset
     * @throws IOException
     */

    public static List<Object> upload(String requestURL, String method, String charset, Collection<Part> parts) {
        return privateUpload(requestURL, method, charset, parts);
    }

    public static List<Object> upload(String requestURL, String method, String charset, List<HashMap<String, Object>> parts) {
        return privateUpload(requestURL, method, charset, parts);
    }

    private static List<Object> privateUpload(String requestURL, String method, String charset, Object parts) {

        // creates a unique boundary based on time stamp
        String boundary = "===" + System.currentTimeMillis() + "===";

        URL url = null;
        try {
            url = new URL(requestURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection httpConn = null;
        try {
            httpConn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        if (username != null && password != null) {
            //String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary((username + ":" + password).getBytes(StandardCharsets.UTF_8));
            //httpConn.setRequestProperty("Authorization", "Basic " + encoded);
            login(httpConn, method, username, password);
        }
        */


        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true); // indicates POST method
        try {
            httpConn.setRequestMethod(method.toUpperCase());
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        httpConn.setRequestProperty("User-Agent", "CodeJava Agent");
        httpConn.setRequestProperty("Test", "Bonjour");


        OutputStream outputStream = null;
        try {
            outputStream = httpConn.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        addHeaderField("User-Agent", "CodeJava", writer);
        addHeaderField("Test-Header", "Header-Value", writer);


        if (parts.getClass().getName().equals("java.util.Collections$UnmodifiableRandomAccessList")) {

            for (Part part : (Collection<Part>) parts) {
                String name = StringEscapeUtils.unescapeJavaScript(part.getName());

                if (extractFileName(part).equals("")) {
                    try {
                        addFormField(name, convertBinaryToString(part.getInputStream()), charset, writer, boundary);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String filename = StringEscapeUtils.unescapeJavaScript(part.getName());
                    // servlet.extractFileName(part)
                    try {
                        addFilePart(name, part.getInputStream(), filename, writer, outputStream, boundary);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        } else if (parts.getClass().getName().equals("java.util.ArrayList")) {


            for (HashMap<String, Object> map : (List<HashMap<String, Object>>) parts) {

                Boolean isFile = (Boolean) map.get("isFile");
                String field = (String) map.get("field");
                String value = (String) map.get("value");

                field = StringEscapeUtils.unescapeJavaScript(field);

                if (!isFile) {
                    addFormField(
                            field,
                            StringEscapeUtils.unescapeJavaScript(value),
                            charset, writer, boundary
                    );
                } else {
                    addFilePart(field, value, writer, outputStream, boundary);
                }


            }

        }


        try {

            return finish(httpConn, boundary, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


    public static List<HashMap<String, Object>> addFormFieldInMap(List<HashMap<String, Object>> fieldList, String field, String value) {

        if (fieldList == null) {
            fieldList = new ArrayList<HashMap<String, Object>>();
        }

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("field", field);
        map.put("value", value);
        map.put("isFile", false);

        fieldList.add(map);
        return fieldList;
    }

    public static List<HashMap<String, Object>> addPartFieldInMap(List<HashMap<String, Object>> fieldList, String field, String path) {

        if (fieldList == null) {
            fieldList = new ArrayList<HashMap<String, Object>>();
        }

        HashMap<String, Object> map = new HashMap<String, Object>();

        map.put("field", field);
        map.put("value", path);
        map.put("isFile", true);

        fieldList.add(map);
        return fieldList;
    }

    /**
     * Adds a form field to the request
     *
     * @param name  field name
     * @param value field value
     */

    private static void addFormField(String name, String value, String charset, PrintWriter writer, String boundary) {

        writer.append("--" + boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
        writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Adds a upload file section to the request
     *
     * @param fieldName  name attribute in <input type="file" name="..." />
     //* @param uploadFile a File to be uploaded
     * @throws IOException
     */
    private static void addFilePart(String fieldName, String uploadFilePath, PrintWriter writer, OutputStream outputStream, String boundary) {

        Path path = Paths.get(uploadFilePath);
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(uploadFilePath);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

        try {
            addFilePart(fieldName, inputStream, file.getName(), writer, outputStream, boundary);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void addFilePart(String fieldName, InputStream inputStream, String fileName, PrintWriter writer, OutputStream outputStream, String boundary)
            throws IOException {

        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();

        /*
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        */

        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
        inputStream.close();

        writer.append(LINE_FEED);
        writer.flush();

    }

    public static String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }


    /**
     * Adds a header field to the request.
     *
     * @param name  - name of the header field
     * @param value - value of the header field
     */
    private static void addHeaderField(String name, String value, PrintWriter writer) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    /**
     * Completes the request and receives response from the server.
     *
     * @return a list of Strings as response in case the server returned
     * status OK, otherwise an exception is thrown.
     * @throws IOException
     */
    private static List<Object> finish(HttpURLConnection connection, String boundary, PrintWriter writer) throws IOException {
        List<Object> result = new ArrayList<Object>();

        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();

        // checks server's status code first
        int status = connection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            String disposition = connection.getHeaderField("Content-Disposition");
            if (disposition != null && !disposition.equals("")) {
                return downloadFile(connection);
            }
            result.add(convertBinaryToString(connection.getInputStream()));
            connection.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }


        return result;
    }

    public static String getInputRequest(HttpServletRequest request) {
        String line = "";
        StringBuffer result = new StringBuffer();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            while ((line = br.readLine()) != null) {
                result.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String convertBinaryToString(InputStream inputStream) {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String line = "";
        StringBuffer result = new StringBuffer();
        try {
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static Object send(String path, String method, String contentType, String acceptType, boolean cacheable) {
        return send(path, method, null, contentType, acceptType, cacheable, false, null, null);
    }

    public static List<Object> send(String path, String method, String sendData, String contentType, String acceptType, boolean cacheable, boolean isBasicAuthentication, String username, String password) {
        //http://localhost:8084/kasra/host/getAll?id=5&method=post

        List<Object> result = new ArrayList<Object>();

        HttpURLConnection connection = null;
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            if (username != null && !username.equals("")) {
                setCookie(connection, method, "UTF-8", null, null, cacheable, isBasicAuthentication, username, password);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            connection.setRequestMethod(method);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        if (contentType != null && !contentType.equals("")) {
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty("Accept", acceptType);
        }

        connection.setDoInput(true);
        if (sendData != null && !sendData.equals("")) {
            connection.setDoOutput(true);
            try {
                OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                wr.write(sendData);
                wr.flush();
                wr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        // check status code
        String exception = "";
        try {
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String disposition = connection.getHeaderField("Content-Disposition");
                if (disposition != null && !disposition.equals("")) {
                    return downloadFile(connection);
                } else {
                    result.add(convertBinaryToString(connection.getInputStream()));
                }


            } else {
                exception = connection.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        connection.disconnect();
        return result;

    }


    public static String authentication(boolean isHttps, String ip, int port, String method, boolean cacheable, boolean isBasicAuthentication, String username, String password) {
        //http://localhost:8084/kasra/host/getAll?id=5&method=post
        CookieHandler.setDefault(new CookieManager());
        if (username != null && !username.equals("")) {
            String type = (isHttps) ? "s" : "";
            String path = "http" + type + "://" + ip + ":" + port + "/j_security_check?j_username=" + username + "&j_password=" + password;

            List<Object> result = send(path, method, null, null, null, cacheable, isBasicAuthentication, username, password);
            if (result == null || result.size() == 0) {
                return null;
            }

            return (String) result.get(0);
        }
        return null;

    }

    public static void setCookie(HttpURLConnection conn, String method, String charset, boolean cacheable) {
        setCookie(conn, method, charset, "application/x-www-form-urlencoded", "text/html", cacheable, false, null, null);
    }

    public static void setCookie(HttpURLConnection conn, String method, String charset, String contentType, String acceptType, boolean cacheable, boolean isBasicAuthentication, String username, String password) {

        if (username == null || !username.equals("")) {
            return;
        }

        // Acts like a browser
        conn.setUseCaches(cacheable);

        try {
            conn.setRequestMethod(method);
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        conn.setRequestProperty("Host", "accounts.google.com");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

        if (acceptType != null && !acceptType.equals("")) {
            conn.setRequestProperty("Accept", acceptType + ";q=0.9,*/*;q=0.8;charset=" + charset);
        } else {
            //conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml,application/json;q=0.9,*/*;q=0.8;charset=" + charset);
            conn.setRequestProperty("Accept", "*/*;q=0.9,*/*;q=0.8;charset=" + charset);

        }

        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        if (cookies != null) {
            for (String cookie : cookies) {
                conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
            }
        }
        conn.setRequestProperty("Connection", "keep-alive");

        if (contentType != null && !contentType.equals("")) {
            conn.setRequestProperty("Content-Type", contentType);
        } else {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }


        if (isBasicAuthentication) {
            String encoded = javax.xml.bind.DatatypeConverter.printBase64Binary((username + ":" + password).getBytes(StandardCharsets.UTF_8));  //Java 8
            conn.setRequestProperty("Authorization", "BASIC " + encoded);
        }
        //conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);

    }

    public static byte[] serialize(Object obj) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = null;
        try {
            os = new ObjectOutputStream(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) {

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Object> downloadFile(HttpURLConnection httpConn)
            throws IOException {
        byte[] buffer = null;

        String fileURL = httpConn.getURL().getPath();
        int responseCode = httpConn.getResponseCode();
        String fileName = "";
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {

            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            //int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10, disposition.length());
                    fileName = StringEscapeUtils.unescapeJavaScript(fileName);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());
                fileName = StringEscapeUtils.unescapeJavaScript(fileName);
            }

            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            //System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            //String saveFilePath = saveDir + File.separator + fileName;

            // opens an output stream to save into file
            /*
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            BufferedOutputStream outputStream = new BufferedOutputStream(bytesOut);

            int bytesRead = -1;

            buffer = new byte[4096];
            int k = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                k++;
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();
            */
            buffer = IOUtils.toByteArray(inputStream);

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();

        List<Object> result = new ArrayList<Object>();
        result.add(buffer);
        result.add(fileName);
        return result;
    }


    public static String writeBinaryFile(String pathname, byte[] content) {

        if (content == null) {
            return "no content exist!";
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathname);
        } catch (FileNotFoundException e) {
            return e.getMessage();
        }
        try {
            fos.write(content);
            fos.close();
        } catch (IOException e) {
            return e.getMessage();
        }

        return "Uploaded Successfully!";

    }


    public static String writeBinaryFile(String pathname, InputStream content) {

        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(content);
        } catch (IOException e) {
            return e.getMessage();
        }
        return writeBinaryFile(pathname, bytes);
    }


    public static void writeTextFile(String pathname, String content) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter(pathname);
            bw = new BufferedWriter(fw);
            bw.write(content);

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }




}

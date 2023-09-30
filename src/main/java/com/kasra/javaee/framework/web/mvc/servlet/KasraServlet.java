package com.kasra.javaee.framework.web.mvc.servlet;


import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import org.apache.commons.lang.StringEscapeUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

//import  org.jboss.weld.bean.ManagedBean;


/**
 * Created by kasra.haghpanah on 12/08/2016.
 */
//urlPatterns = "(^(((?!(.)*.jsp)(?!j_security_check)).)*$)|\\/"
@WebServlet(description = "This is a simple Servlet.", urlPatterns = "/kasra/*")
@MultipartConfig(
        //location = "/tmp",
        fileSizeThreshold = 1024 * 1024 * 10 * 100,
        maxFileSize = 1024 * 1024 * 10 * 100,
        maxRequestSize = 1024 * 1024 * 10 * 100
)

public class KasraServlet extends HttpServlet {

    private static final long serialVersionUID = 1l;

    public KasraServlet() {
        super();
    }

    private static Map<String, HashMap<String, Object>> actions;

    private static Properties properties;

    private String contextRoot = "";

    @Inject
    BeanManager beanManager;


    @PostConstruct
    public void init() {

        System.out.println("Start Servlet!");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        //request.getContextPath()
        //String path = getServletContext().getRealPath("").toString().replace("\\", "/");
        String path = "";


        if (actions == null) {
            loadConfig();
            String basePackage = properties.getProperty("base-package");
            String cRoot = properties.getProperty("context-root");
            while (cRoot.indexOf('\\') > -1) {
                cRoot = cRoot.replace('\\', '/');
            }
            contextRoot = cRoot;
            basePackage = basePackage.replace('.', '/');
            walkTree(getServletContext().getRealPath("").toString().replace("\\", "/") + "/WEB-INF/classes/" + basePackage);
            //walkTree(contextRoot + "/src/main/java/" + basePackage);
        }

        if (path.equals("")) {
            path = contextRoot + "/src/main/webapp/";
        }

        System.out.println(getIpAddress(request));

        String url = request.getRequestURI().toString();
        if (url.indexOf("/kasra/download") == 0) {


            //path = path.substring(0, path.indexOf("/target")) + "/src/main/webapp/" + url.replace("/download/", "");
            //path = path + "/" + url.replace("/download/", "");
            path = path + url.replace("/kasra/download/", "");
            download(response, path);


        }
//        else if (url.equals("/kasra") || url.equals("/kasra/rrindex.html") || url.equals("/kasra/rrindex.html/")) {
//            String content = "";
//            //path = path.substring(0, path.indexOf("/target")) + "/src/main/webapp/rrindex.html";
//            //E:\Application\glassfish\glassfish4\glassfish\domains\domain1\applications\javaee7
//            path = path + "/kasra/rrindex.html";
//
//            content = readTextFile(path);
//            response.setContentType("text/html");
//            print(response, content, null, true);
//
//        }
//        else if (url.equals("/kasra/error.jsp")) {
//            String content = "";
//            //path = path.substring(0, path.indexOf("/target")) + "/src/main/webapp/error.jsp";
//            path = path + "/kasra/error.jsp";
//            content = readTextFile(path);
//            response.setContentType("text/html");
//            print(response, content, null, true);
//        }
        else {
            invokeAction(request, response, "GET");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        invokeAction(request, response, "POST");
    }

    protected void doHead(HttpServletRequest request, HttpServletResponse response) {
        invokeAction(request, response, "HEAD");
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        invokeAction(request, response, "PUT");
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        invokeAction(request, response, "DELETE");
    }

    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        invokeAction(request, response, "OPTIONS");
    }

    protected void doTrace(HttpServletRequest request, HttpServletResponse response) {
        invokeAction(request, response, "TRACE");
    }


    public String readTextFile(String filename) {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }

    public void write(InputStream initialStream, String targetAddress) {

        //InputStream initialStream = new FileInputStream(new File("src/main/resources/sample.txt"));


        File targetFile = new File(targetAddress);


        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(targetFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[1024 * 1024 * 5];
        int bytesRead;
        try {
            while ((bytesRead = initialStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //IOUtils.closeQuietly(initialStream);
        //IOUtils.closeQuietly(outStream);
        try {
            initialStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    public ByteArrayOutputStream copyToBuffer(String fileAddress) {
//        Path path = Paths.get(fileAddress);
//        byte[] documentBody = Files.readAllBytes(path);
        File file = new File(fileAddress);
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        BufferedOutputStream out = new BufferedOutputStream(bytesOut);
        System.out.println(bytesOut.size());

        byte[] buf = new byte[100000];
        long total = 0;
        while (true) {
            int r = 0;
            try {
                r = in.read(buf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (r == -1) {
                break;
            }
            try {
                out.write(buf, 0, r);
            } catch (IOException e) {
                e.printStackTrace();
            }
            total += r;
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytesOut;

    }

    public String getMimeFile(String fileName) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        String mimeType = mimeTypesMap.getContentType(fileName);
        File file = new File(fileName);
        return mimeTypesMap.getContentType(file);
    }

    public void download(HttpServletResponse response, String path) {
        File downloadFile = new File(path);
        if (downloadFile.exists()) {
            FileInputStream inStream = null;
            try {
                inStream = new FileInputStream(downloadFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


            // obtains ServletContext
            ServletContext context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(path);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtains response's output stream
            OutputStream outStream = null;
            try {
                outStream = response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            try {
                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect("/error.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void download(HttpServletResponse response, String filename, byte[] bytes) {

        // gets MIME type of the file
        String mimeType = "application/octet-stream";
        ;
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) bytes.length);
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", filename);
        response.setHeader(headerKey, headerValue);

        // obtains response's output stream
        OutputStream outStream = null;
        try {
            outStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[4096];
        int bytesRead = -1;


        try {
            outStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getInputRequest(HttpServletRequest request) {
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

    public Map<String, String[]> upload(HttpServletRequest request, String folder, Boolean isUnicode, String... filenames) {

        Map<String, String[]> map = new HashMap<String, String[]>();
        File Folder = new File(folder);
        if (!Folder.exists()) {
            try {
                Folder.mkdir();
            } catch (SecurityException se) {
                //handle it
            }
        }

        Collection<Part> parts = null;
        try {
            parts = request.getParts();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        int i = 0;
        int length = 0;
        if (filenames != null) {
            length = filenames.length;
        }
        for (Part part : parts) {
            //part.getContentType() == null
            if (extractFileName(part).equals("")) {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(part.getInputStream()));
                    String inputLine;
                    StringBuffer result = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        result.append(inputLine);
                    }
                    in.close();
                    String value = "";
                    value = result.toString();


                    if (map.get(part.getName()) == null) {

                        String getName = (isUnicode) ? StringEscapeUtils.unescapeJavaScript(part.getName()) : part.getName();
                        map.put(getName, new String[]{value});
                    } else {
                        String[] values = map.get(part.getName());
                        String[] newValues = new String[values.length + 1];
                        for (int k = 0; k < values.length; k++) {
                            newValues[k] = values[k];
                        }
                        newValues[values.length] = value;
                        map.put(part.getName(), newValues);
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (part.getContentType() != null) {

                try {
                    part = request.getPart(part.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ServletException e) {
                    e.printStackTrace();
                }
                InputStream fileContent = null;
                try {
                    fileContent = part.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String uploadFileName = "";

                if (i < length) {
                    uploadFileName = filenames[i];
                } else {
                    uploadFileName = (isUnicode)?StringEscapeUtils.unescapeJavaScript(part.getName()):part.getName(); //extractFileName(part);  //????????
                }

                if (!uploadFileName.equals("")) {
                    if(isUnicode){
                        uploadFileName = StringEscapeUtils.unescapeJavaScript(uploadFileName);
                    }
                    write(fileContent, folder + uploadFileName);
                }
                map.put(part.getName(), new String[]{uploadFileName});
                i++;
            }

        }
        return map;
    }

    public void print(HttpServletResponse response, String content, String contentType, boolean utf8) {

        if (contentType == null) {
            response.setContentType("text/html");
        } else if (contentType.equals("")) {
            response.setContentType("text/html");
        } else {
            response.setContentType(contentType);
        }

        if (utf8) {
            response.setCharacterEncoding("UTF-8");
        }
        response.setContentLength(content.length());
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(content);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");

        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    private void walkTree(String path) {

        File parent = new File(path);

        if (parent.isDirectory()) {
            for (String pathChild : parent.list()) {
                pathChild = path + "/" + pathChild;
                File child = new File(pathChild);

                if (child.isFile() && pathChild.lastIndexOf(".class") == pathChild.length() - 6) {
                    System.out.println(pathChild + " -> file");
                    javaControllerClassAnalize(pathChild);
                } else if (child.isDirectory()) {
                    //System.out.println(pathChild + " -> folder");
                    walkTree(pathChild);
                }
            }
        } else if (parent.isFile()) {
            System.out.println(parent + " -> file");
        }


    }


    private void javaControllerClassAnalize(String path) {

        path = (path.substring(path.indexOf("/WEB-INF/classes/") + 17, path.length() - 6)).replace('/', '.');

        Class clazz = null;

        try {
            clazz = Class.forName(path);
            System.out.println(clazz.getName());
        } catch (ClassNotFoundException e) {
            // e.printStackTrace();
        }

        if (clazz != null) {

            String urlController = "";

            for (Annotation classAnnotation : clazz.getAnnotations()) {

                if (classAnnotation instanceof Controller) {

                    Controller controller = (Controller) classAnnotation;
                    urlController = controller.url();
                    if (urlController.equals("")) {
                        urlController = clazz.getSimpleName();
                    }
                }
            }


            if (!urlController.equals("")) {

                if (actions == null) {
                    actions = new TreeMap<String, HashMap<String, Object>>();
                }

                for (Method method : clazz.getDeclaredMethods()) {
                    String url = "";
                    String webMethod = "";

                    for (Annotation methodAnnotation : method.getAnnotations()) {

                        if (methodAnnotation instanceof WebRequest) {

                            WebRequest webRequest = (WebRequest) methodAnnotation;
                            String urlMethod = webRequest.url();
                            if (urlMethod.equals("")) {
                                urlMethod = method.getName();
                            }
                            if (urlController.equals("disabled")) {
                                urlController = "";
                            } else {
                                urlController = "/" + urlController;
                            }
                            url = urlController + "/" + urlMethod;
                            url = "/kasra/" + url;
                            webMethod = webRequest.method();
                        }

                    }


                    if (!url.equals("")) {

                        while (url.indexOf("//") > -1) {
                            url = url.replaceAll("//", "/");
                        }

                        if (actions.get(url) == null) {
                            HashMap<String, Object> mapMethod = new HashMap<String, Object>();
                            mapMethod.put("method", method);
                            mapMethod.put("webMethod", webMethod);

                            actions.put(url, mapMethod);
                        } else {
                            System.out.println("This url( " + url + " ) from " + method.getDeclaringClass() + " use from another controller");
                        }
                    }

                }
            }

        }


    }


    private void invokeAction(HttpServletRequest request, HttpServletResponse response, String method) {

        if (actions == null) {
            loadConfig();
            String basePackage = properties.getProperty("base-package");
            String cRoot = properties.getProperty("context-root");
            while (cRoot.indexOf('\\') > -1) {
                cRoot = cRoot.replace('\\', '/');
            }
            contextRoot = cRoot;
            basePackage = basePackage.replace('.', '/');
            walkTree(getServletContext().getRealPath("").toString().replace("\\", "/") + "/WEB-INF/classes/" + basePackage);
            //walkTree(contextRoot + "/src/main/java/" + basePackage);
        }

        String url = request.getRequestURI();
        HashMap<String, Object> map = actions.get(url);

        if (map != null && map.get("webMethod").toString().toUpperCase().equals(method)) {
            Method reflectionMethod = (Method) map.get("method");


            Object o = getBean(reflectionMethod.getDeclaringClass());

            try {
                if (o != null) {

                    reflectionMethod.invoke(o, setParamereForInvokeMethod(request, response, reflectionMethod));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect("/error.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public Object[] setParamereForInvokeMethod(HttpServletRequest request, HttpServletResponse response, Method method) {

        Object[] objects = new Object[3];

        int i = 0;
        for (Type type : method.getParameterTypes()) {

            if (i > 2) {
                break;
            }

            if (((Class) type).getName().equals(HttpServletRequest.class.getName())) {
                objects[i] = request;
            }
            if (((Class) type).getName().equals(HttpServletResponse.class.getName())) {
                objects[i] = response;
            }
            if (((Class) type).getName().equals(this.getClass().getName())) {
                objects[i] = this;
            }
            i++;
        }

        return objects;
    }

    public Object getBean(Class clazz) {

        boolean ejb = false;
        for (Annotation obj : clazz.getAnnotations()) {

            String name = clazz.getSimpleName();

            boolean stateless = obj instanceof Stateless ? true : false;
            boolean stateful = obj instanceof Stateful ? true : false;
            boolean singleton = obj instanceof Singleton ? true : false;

            if (stateless || stateful || singleton) {
                String ejbName = "";
                if (stateless) {
                    ejbName = ((Stateless) obj).name();
                } else if (stateful) {
                    ejbName = ((Stateful) obj).name();
                } else if (singleton) {
                    ejbName = ((Singleton) obj).name();
                }
                if (!ejbName.equals("")) {
                    name = ejbName;
                }

                try {
                    return new InitialContext().lookup("java:app/" + name);
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }


        }
        Bean<?> bean = (Bean<?>) beanManager.getBeans(clazz, new AnnotationLiteral<Any>() {
        }).iterator().next();
        CreationalContext ctx = beanManager.createCreationalContext(bean); // could be inlined below
        return beanManager.getReference(bean, bean.getBeanClass(), ctx); // could be inlined with return
    }


    public Set<Bean<?>> getBeans() {
        Set<Bean<?>> beans = beanManager.getBeans(Object.class, new AnnotationLiteral<Any>() {
        });
        for (Bean<?> bean : beans) {
            System.out.println(bean.getBeanClass().getName());
        }
        return beans;
    }

    private Properties loadConfig() {
        if (properties == null) {
            InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
            properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    public String writeXml(Object list) {
        JAXBContext context = null;
        Marshaller marshaller = null;
        StringWriter sw = new StringWriter();

        try {
            context = JAXBContext.newInstance(list.getClass());
            marshaller = context.createMarshaller();
            marshaller.marshal(list, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return sw.toString();
    }


    public Object readXML(String XML, Class clazz) {


        JAXBContext context = null;
        Unmarshaller unmarshaller = null;


        try {
            context = JAXBContext.newInstance(clazz);
            unmarshaller = context.createUnmarshaller();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        StringReader reader = new StringReader(XML);
        try {
            return unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;

    }


    public byte[] serialize(Object obj) {
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

    public Object deserialize(byte[] data) {
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


    public String getContextRoot() {
        return contextRoot;
    }
}

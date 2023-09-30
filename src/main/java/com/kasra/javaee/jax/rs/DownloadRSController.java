package com.kasra.javaee.jax.rs;

import com.kasra.javaee.framework.web.mvc.utility.FileUtility;
import com.kasra.javaee.jaxb.FileDTO;
import org.apache.commons.lang.StringEscapeUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 08/04/2017.
 */
@Named
@RequestScoped
@Path("/download")
@ApplicationPath("/resources")
public class DownloadRSController {

    private static Properties properties;

    private static String path;


    private String start() {
        if (properties == null) {
            InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
            properties = new Properties();
            try {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        path = properties.getProperty("context-root") + "/src/main/webapp/upload/";
        path = path.replace("\\", "/").replaceAll("//", "/");
        return path;
    }

    @POST
    @Path(("{filename: (.)*(/(.)*)*}"))
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam(value = "filename") String filename) {
        //localhost:8084/resources/download/{filename}
        //localhost:8084/resources/download/kasra.jpg
        if (path == null) {
            path = start();
        }

        java.nio.file.Path pathObj = Paths.get(path + filename);
        byte[] data = null;
        try {
            data = Files.readAllBytes(pathObj);
        } catch (IOException e) {

        }
        Response.ResponseBuilder response = Response.ok(data, MediaType.APPLICATION_OCTET_STREAM);

        if (data != null) {
            response.header("Content-Length", pathObj.toFile().length());
            String fileName = filename.replaceAll("/", "");
            response.header("Content-Disposition", "attachment;filename=" + StringEscapeUtils.escapeJavaScript(fileName));
            //response.header("Content-Transfer-Encoding", " Binary");
            String mime = FileUtility.getMimeType((path + filename).replace("//", "/"));
            response.header("Content-Type", mime);
        }

        return response.build();

    }

    @GET
    @Path(("/file{dir: (/(.)*)*}"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<FileDTO> getListFile(@PathParam(value = "dir") String dir) {
        //localhost:8084/resources/download/file/{dir}
        if (path == null) {
            path = start();
        }

        File folder = new File(path + dir);
        File[] files = folder.listFiles();

        List<FileDTO> fileDTOs = new ArrayList<FileDTO>();

        if (files != null) {
            for (File file : files) {
                FileDTO fileDTO = new FileDTO();
                fileDTO.setFolder(!file.isFile());
                fileDTO.setFilename(file.getName());
                fileDTO.setMime(FileUtility.getMimeType(file.getAbsolutePath()));
                fileDTO.setSize(file.length());

                fileDTOs.add(fileDTO);
            }
        }


        return fileDTOs;
    }
}

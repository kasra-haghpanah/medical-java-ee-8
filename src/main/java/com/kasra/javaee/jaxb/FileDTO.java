package com.kasra.javaee.jaxb;

/**
 * Created by kasra.haghpanah on 08/04/2017.
 */
public class FileDTO {

    String filename;
    String mime;
    long size;
    Boolean folder = false;

    public FileDTO() {
    }

    public FileDTO(String filename, String mime, int size, Boolean folder) {
        this.filename = filename;
        this.mime = mime;
        this.size = size;
        this.folder = folder;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Boolean getFolder() {
        return folder;
    }

    public void setFolder(Boolean folder) {
        this.folder = folder;
    }

    @Override
    public String toString() {
        return "{"
                + "\"filename\":\"" + filename + "\""
                + ",\"mime\":\"" + mime + "\""
                + ",\"size\":\"" + size + "\""
                + ",\"folder\":\"" + folder + "\""
                + "}";
    }
}

package com.mwj.personweb.model;

/**
 * @Author: 母哥 @Date: 2019-03-08 16:36 @Version 1.0
 */
public class Archive {

    private int id;

    /**
     * 归档日期
     */
    private String archiveName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArchiveName() {
        return archiveName;
    }

    public void setArchiveName(String archiveName) {
        this.archiveName = archiveName;
    }
}

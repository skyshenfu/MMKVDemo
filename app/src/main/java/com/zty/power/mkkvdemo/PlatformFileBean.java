package com.zty.power.mkkvdemo;

import java.io.Serializable;

/**
 * 新版本文件的bean
 */
public class PlatformFileBean implements Serializable {

        private String createTime;
        private int del;
        private int fileType;
        private String fileUrl;
        private String fullUrl;
        private String id;
        private String name;
        private String size;
        private String suffix;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setDel(int del) {
            this.del = del;
        }
        public int getDel() {
            return del;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }
        public int getFileType() {
            return fileType;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
        public String getFileUrl() {
            return fileUrl;
        }

        public void setFullUrl(String fullUrl) {
            this.fullUrl = fullUrl;
        }
        public String getFullUrl() {
            return fullUrl;
        }

        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setSize(String size) {
            this.size = size;
        }
        public String getSize() {
            return size;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
        public String getSuffix() {
            return suffix;
        }

    }

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author shadl
 */
public class AlarmFileBean {
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
    
    String id;
    String installationId;
    String fileName;
    String loadDate;
    int linesCount;
    int insertedCount;
    int skippedCount;
    int errorCount;

    public AlarmFileBean() {
    }

    public AlarmFileBean(String id, String installationId, String fileName, Date loadDate, int linesCount, int insertedCount, int skippedCount, int errorCount) {
        this.id = id;
        this.installationId = installationId;
        this.fileName = fileName;
        this.loadDate = df.format(loadDate);
        this.linesCount = linesCount;
        this.insertedCount = insertedCount;
        this.skippedCount = skippedCount;
        this.errorCount = errorCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = df.format(loadDate);
    }

    public int getLinesCount() {
        return linesCount;
    }

    public void setLinesCount(int linesCount) {
        this.linesCount = linesCount;
    }

    public int getInsertedCount() {
        return insertedCount;
    }

    public void setInsertedCount(int insertedCount) {
        this.insertedCount = insertedCount;
    }

    public int getSkippedCount() {
        return skippedCount;
    }

    public void setSkippedCount(int skippedCount) {
        this.skippedCount = skippedCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
    
    
}

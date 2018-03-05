/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.beans;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author shadl
 */
public class AlarmDataBean  implements Serializable {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    private String id;
    private String idInstallation;
    private String idAlarmFile;
    private String idAlarmType;
    private String alarmStatus;
    private String alarmTime;

    public AlarmDataBean() {
    }

    public AlarmDataBean(String id, String idInstallation, String idAlarmFile, String idAlarmType, String alarmStatus, Date alarmTime) {
        this.id = id;
        this.idInstallation = idInstallation;
        this.idAlarmFile = idAlarmFile;
        this.idAlarmType = idAlarmType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = DATE_FORMAT.format(alarmTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdInstallation() {
        return idInstallation;
    }

    public void setIdInstallation(String idInstallation) {
        this.idInstallation = idInstallation;
    }

    public String getIdAlarmFile() {
        return idAlarmFile;
    }

    public void setIdAlarmFile(String idAlarmFile) {
        this.idAlarmFile = idAlarmFile;
    }

    public String getIdAlarmType() {
        return idAlarmType;
    }

    public void setIdAlarmType(String idAlarmType) {
        this.idAlarmType = idAlarmType;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
    
    
}

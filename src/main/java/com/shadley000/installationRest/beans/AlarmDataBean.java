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
    
    private int id;
    private int idInstallation;
    private int idAlarmFile;
    private int idAlarmType;
    private String alarmStatus;
    private String alarmTime;

    public AlarmDataBean() {
    }

    public AlarmDataBean(int id, int idInstallation, int idAlarmFile, int idAlarmType, String alarmStatus, Date alarmTime) {
        this.id = id;
        this.idInstallation = idInstallation;
        this.idAlarmFile = idAlarmFile;
        this.idAlarmType = idAlarmType;
        this.alarmStatus = alarmStatus;
        this.alarmTime = DATE_FORMAT.format(alarmTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInstallation() {
        return idInstallation;
    }

    public void setIdInstallation(int idInstallation) {
        this.idInstallation = idInstallation;
    }

    public int getIdAlarmFile() {
        return idAlarmFile;
    }

    public void setIdAlarmFile(int idAlarmFile) {
        this.idAlarmFile = idAlarmFile;
    }

    public int getIdAlarmType() {
        return idAlarmType;
    }

    public void setIdAlarmType(int idAlarmType) {
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

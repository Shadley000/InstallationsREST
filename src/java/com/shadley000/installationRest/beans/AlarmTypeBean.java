/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.beans;

import java.io.Serializable;

/**
 *
 * @author shadl
 */
public class AlarmTypeBean  implements Serializable  {
    private String id;
    private String idInstallation;
    private String system;
    private String subSystem;
    private String messageType;
    private String alarmPriority;
    private String tagName;
    private String description;

    public AlarmTypeBean() {
    }

    public AlarmTypeBean(String id, String idInstallation, String system, String subSystem, String messageType, String alarmPriority, String tagName, String description) {
        this.id = id;
        this.idInstallation = idInstallation;
        this.system = system;
        this.subSystem = subSystem;
        this.messageType = messageType;
        this.alarmPriority = alarmPriority;
        this.tagName = tagName;
        this.description = description;
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

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getAlarmPriority() {
        return alarmPriority;
    }

    public void setAlarmPriority(String alarmPriority) {
        this.alarmPriority = alarmPriority;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}

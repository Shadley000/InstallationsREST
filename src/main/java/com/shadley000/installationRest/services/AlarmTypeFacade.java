/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import com.shadley000.installationRest.beans.AlarmDataBean;
import com.shadley000.installationRest.beans.AlarmTypeBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author shadl
 */
public class AlarmTypeFacade {

    private static String SQL_GET_ALARM_TYPES = "SELECT ID, ID_INSTALLATION, SYSTEM, SUBSYSTEM, MESSAGE_TYPE, ALARM_PRIORITY, TAG_NAME, DESCRIPTION "
            + " FROM ALARM_TYPE WHERE ID_INSTALLATION = ? order by SYSTEM, SUBSYSTEM, MESSAGE_TYPE, TAG_NAME ";

    private static String SQL_GET_ALARM_TYPE = "SELECT ID, ID_INSTALLATION, SYSTEM, SUBSYSTEM, MESSAGE_TYPE, ALARM_PRIORITY, TAG_NAME, DESCRIPTION "
            + " FROM ALARM_TYPE  WHERE ID_INSTALLATION = ? AND ID = ? ";

    public Map<Integer, AlarmTypeBean> getAlarmTypes(int installationId) {

        Map<Integer, AlarmTypeBean> map = new HashMap<>();
        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_ALARM_TYPES);
            stmt.setInt(1, installationId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AlarmTypeBean alarmTypeBean = new AlarmTypeBean();
                alarmTypeBean.setId(rs.getInt("ID"));
                alarmTypeBean.setIdInstallation(installationId);
                alarmTypeBean.setSystem(rs.getString("SYSTEM"));
                alarmTypeBean.setSubSystem(rs.getString("SUBSYSTEM"));
                alarmTypeBean.setMessageType(rs.getString("MESSAGE_TYPE"));
                alarmTypeBean.setAlarmPriority(rs.getString("ALARM_PRIORITY"));
                alarmTypeBean.setTagName(rs.getString("TAG_NAME"));
                alarmTypeBean.setDescription(rs.getString("DESCRIPTION"));

                map.put(alarmTypeBean.getId(), alarmTypeBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InstallationFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public AlarmTypeBean getAlarmType(int installationId, int alarmTypeId) {

        AlarmTypeBean alarmTypeBean = null;
        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_ALARM_TYPE);
            stmt.setInt(1, installationId);
            stmt.setInt(2, alarmTypeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                alarmTypeBean = new AlarmTypeBean();
                alarmTypeBean.setId(rs.getInt("ID"));
                alarmTypeBean.setIdInstallation(installationId);
                alarmTypeBean.setSystem(rs.getString("SYSTEM"));
                alarmTypeBean.setSubSystem(rs.getString("SUBSYSTEM"));
                alarmTypeBean.setMessageType(rs.getString("MESSAGE_TYPE"));
                alarmTypeBean.setAlarmPriority(rs.getString("ALARM_PRIORITY"));
                alarmTypeBean.setTagName(rs.getString("TAG_NAME"));
                alarmTypeBean.setDescription(rs.getString("DESCRIPTION"));
                return alarmTypeBean;
            }

        } catch (SQLException ex) {
            Logger.getLogger(InstallationFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        throw new EntityNotFoundException("Unable to find Alarm Type Id "+alarmTypeId);
    }

}

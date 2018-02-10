/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import com.shadley000.installationRest.InstallationResource;
import com.shadley000.installationRest.beans.AlarmDataBean;
import com.shadley000.installationRest.beans.AlarmFileBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shadl
 */
public class AlarmDataFacade {

    private static String sql_getAlarmData = "select ID, ID_ALARM_FILE, ID_ALARM_TYPE, ALARM_STATUS, ALARM_TIME "
            + "from ALARM_DATA where ID_INSTALLATION = ? and ALARM_TIME >= ? ORDER BY ALARM_TIME ASC LIMIT 100";

    public List<AlarmDataBean> getHistory(int installationId, Date from) {

        List<AlarmDataBean> list = new ArrayList<>();

        Logger.getLogger(InstallationResource.class.getName()).log(Level.INFO, installationId + " " + from.toString());
        try (Connection connection = SQLConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql_getAlarmData);
            stmt.setInt(1, installationId);
            stmt.setTimestamp(2, new java.sql.Timestamp(from.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AlarmDataBean alarmDataBean = new AlarmDataBean();
                alarmDataBean.setId(rs.getInt("ID"));
                alarmDataBean.setIdAlarmFile(rs.getInt("ID_ALARM_FILE"));
                alarmDataBean.setIdAlarmType(rs.getInt("ID_ALARM_TYPE"));
                alarmDataBean.setAlarmStatus(rs.getString("ALARM_STATUS"));
                alarmDataBean.setAlarmTime(AlarmDataBean.DATE_FORMAT.format(rs.getTimestamp("ALARM_TIME")));
                list.add(alarmDataBean);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InstallationResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}

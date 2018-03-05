/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import com.shadley000.installationRest.InstallationResource;
import com.shadley000.installationRest.beans.AlarmFileBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author shadl
 */
public class AlarmFileFacade {

    private static String sql_getAlarmFiles = "select ID, FILE_NAME,LOAD_DATE,LINES_COUNT, INSERTED_COUNT, SKIPPED_COUNT, ERROR_COUNT "
            + " from ALARM_FILE where ID_INSTALLATION = ? ";
    private static String sql_getAlarmFile = "select ID, FILE_NAME,LOAD_DATE,LINES_COUNT, INSERTED_COUNT, SKIPPED_COUNT, ERROR_COUNT "
            + " from ALARM_FILE where ID_INSTALLATION = ? and ID = ?";

    public Map<String, AlarmFileBean> getAlarmFiles(String id) {

        Map<String, AlarmFileBean> map = new HashMap<>();

        try (Connection connection = SQLConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql_getAlarmFiles);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AlarmFileBean alarmFile = new AlarmFileBean();
                alarmFile.setId(rs.getString("ID"));
                alarmFile.setFileName(rs.getString("FILE_NAME"));
                alarmFile.setLoadDate(rs.getDate("LOAD_DATE"));
                alarmFile.setLinesCount(rs.getInt("LINES_COUNT"));
                alarmFile.setInsertedCount(rs.getInt("INSERTED_COUNT"));
                alarmFile.setSkippedCount(rs.getInt("SKIPPED_COUNT"));
                alarmFile.setErrorCount(rs.getInt("ERROR_COUNT"));
                map.put(alarmFile.getId(), alarmFile);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InstallationResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }

    public AlarmFileBean getAlarmFile(String id, String fileId) {
        AlarmFileBean alarmFile = null;

        try (Connection connection = SQLConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql_getAlarmFile);
            stmt.setString(1, id);
            stmt.setString(2, fileId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                alarmFile = new AlarmFileBean();
                alarmFile.setId(rs.getString("ID"));
                alarmFile.setFileName(rs.getString("FILE_NAME"));
                alarmFile.setLoadDate(rs.getDate("LOAD_DATE"));
                alarmFile.setLinesCount(rs.getInt("LINES_COUNT"));
                alarmFile.setInsertedCount(rs.getInt("INSERTED_COUNT"));
                alarmFile.setSkippedCount(rs.getInt("SKIPPED_COUNT"));
                alarmFile.setErrorCount(rs.getInt("ERROR_COUNT"));
                return alarmFile;

            }

        } catch (SQLException ex) {
            Logger.getLogger(InstallationResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new EntityNotFoundException("Unable to find File Id "+fileId);
        
    }

}

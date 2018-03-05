/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shadl
 */
public class AlarmPivotFacade {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static String SQL_GET_ALARM_PIVOT = "SELECT ID_INSTALLATION, ID_ALARM_TYPE, ALARM_COUNT, ALARM_DATE "
            + " FROM ALARM_PIVOT WHERE ID_INSTALLATION = ? AND ALARM_DATE >= ? AND ALARM_DATE < ? AND ALARM_COUNT >0";

    public Map<Integer, int[]> getPivot(String installationId, Date fromDate, Date toDate) {

        Map<Integer, int[]> map = new HashMap<>();

        int length = convertDateToIndex(fromDate, toDate);

        try (Connection connection = SQLConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(SQL_GET_ALARM_PIVOT);
            stmt.setString(1, installationId);
            stmt.setString(2, DATE_FORMAT.format(fromDate));
            stmt.setString(3, DATE_FORMAT.format(toDate));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Integer idAlarmType = rs.getInt("ID_ALARM_TYPE");
                int alarmCount = rs.getInt("ALARM_COUNT");
                Date alarmDate = rs.getDate("ALARM_DATE");

                int[] histogram = map.get(idAlarmType);
                if (histogram == null) {
                    histogram = initializeArray(length);
                    map.put(idAlarmType, histogram);
                }
                histogram[convertDateToIndex(fromDate, alarmDate)] = alarmCount;
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstallationFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

        return map;
    }

    private int convertDateToIndex(Date fromDate, Date d) {
        return (int) Math.round((d.getTime() - fromDate.getTime()) / (double) 86400000);
    }

    private int[] initializeArray(int length) {
        int[] histogram = new int[length];
        for (int i = 0; i < length; i++) {
            histogram[i] = 0;
        }
        return histogram;
    }
}

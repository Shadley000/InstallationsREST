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
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author shadl
 */
public class OperatorFacade {

    
    
    private static String SQL_GET_INSTALLATIONS = "select i.ID from OPERATOR o, INSTALLATION i where o.ID = i.ID_OPERATOR and o.NNAME = ? ";
    
    
    public Set<String> getInstallationsByOperatorName(String name) {
        Set<String> installationIdSet = new HashSet<String>();
         try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_INSTALLATIONS);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                installationIdSet.add(rs.getString("ID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return installationIdSet;
    }
    
}

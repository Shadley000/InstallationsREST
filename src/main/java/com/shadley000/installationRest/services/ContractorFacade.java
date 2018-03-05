/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import com.shadley000.installationRest.beans.ContractorBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author shadl
 */
public class ContractorFacade {

    private static String SQL_GET_CONTRACTOR = "SELECT ID, NNAME, LOGO FROM CONTRACTOR WHERE ID = ? ";
    private static String SQL_GET_CONTRACTORS = "SELECT ID, NNAME, LOGO FROM CONTRACTOR ORDER BY NNAME ";
    private static String SQL_GET_INSTALLATIONS = "select i.ID from CONTRACTOR c, INSTALLATION i where c.ID = i.ID_CONTRACTOR and c.NNAME = ? ";

    public ContractorBean getContractor(String id) {
        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_CONTRACTOR);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ContractorBean contractor = new ContractorBean();
                contractor.setId(rs.getString("ID"));
                contractor.setNname(rs.getString("NNAME"));
                contractor.setLogo(rs.getString("LOGO"));
                return contractor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new EntityNotFoundException("Unable to find Contractor Id "+id);
    
    }
     

    public Map<String, ContractorBean> getContractors() {
        Map<String, ContractorBean> map = new HashMap<>();

        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_CONTRACTORS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ContractorBean contractor = new ContractorBean();
                contractor.setId(rs.getString("ID"));
                contractor.setNname(rs.getString("NNAME"));
                contractor.setLogo(rs.getString("LOGO"));
                map.put(contractor.getId(), contractor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public Set<String> getInstallationsByContractorName(String name) {
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

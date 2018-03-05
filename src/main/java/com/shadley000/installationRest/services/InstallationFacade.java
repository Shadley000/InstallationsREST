/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import com.shadley000.installationRest.beans.InstallationBean;
import com.shadley000.installationRest.beans.NameIdBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author shadl
 */
public class InstallationFacade {

    private static String SQL_GET_INSTALLATIONS = "SELECT ID, NNAME FROM INSTALLATION ORDER BY NNAME";
    private static String SQL_GET_INSTALLATION = "SELECT ID, ID_VENDOR, ID_SHIP, ID_CONTRACTOR, PARSER_NAME, NNAME, DATA_DIRECTORY FROM INSTALLATION where ID = ?";

    ShipFacade shipFacade;
    VendorFacade vendorFacade;
    ContractorFacade contractorFacade;

    public InstallationFacade() {
        shipFacade = new ShipFacade();
        vendorFacade = new VendorFacade();
        contractorFacade = new ContractorFacade();
    }

    public Map<String, String> getInstallations(Set<String> filter) {

        Map<String, String> map = new HashMap<>();

        try (Connection connection = SQLConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(SQL_GET_INSTALLATIONS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String installationId = rs.getString("ID");
                String name = rs.getString("NNAME");
                if (filter.contains(installationId)) {
                    map.put(installationId, name);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(InstallationFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public InstallationBean getInstallation(String id) {

        InstallationBean installation = null;
        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_INSTALLATION);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            String vendorId = null;
            String contractorId = null;
            String shipId = null;
            if (rs.next()) {

                installation = new InstallationBean();
                installation.setId(id);
                vendorId = rs.getString("ID_VENDOR");
                contractorId = rs.getString("ID_CONTRACTOR");
                shipId = rs.getString("ID_SHIP");
            }
            if (installation != null) {

                installation.setVendor(vendorFacade.getVendor(vendorId));
                installation.setContractor(contractorFacade.getContractor(contractorId));
                installation.setShip(shipFacade.getShip(shipId));
                return installation;
            }

        } catch (SQLException ex) {
            Logger.getLogger(InstallationFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new EntityNotFoundException("Unable to find Installation Id " + id + " because " + ex.getMessage());
        }
        throw new EntityNotFoundException("Unable to find Installation Id " + id);
    }

}

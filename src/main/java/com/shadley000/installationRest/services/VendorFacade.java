/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import com.shadley000.installationRest.beans.VendorBean;
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
public class VendorFacade {

    private static String SQL_GET_VENDOR = "SELECT ID, NNAME, LOGO FROM VENDOR WHERE ID = ? ";
    private static String SQL_GET_VENDORS = "SELECT ID, NNAME, LOGO FROM VENDOR ORDER BY NNAME ";

    public VendorBean getVendor(String id) {
         try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_VENDOR);
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                VendorBean vendor = new VendorBean();
                vendor.setId(rs.getString("ID"));
                vendor.setNname(rs.getString("NNAME"));
                vendor.setLogo(rs.getString("LOGO"));
                return vendor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new EntityNotFoundException("Unable to find Vendor Id "+id);
    }

    public Map<String, VendorBean> getVendors() {
        Map<String, VendorBean> map = new HashMap<>();

        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_VENDORS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VendorBean vendor = new VendorBean();
                vendor.setId(rs.getString("ID"));
                vendor.setNname(rs.getString("NNAME"));
                vendor.setLogo(rs.getString("LOGO"));
                map.put(vendor.getId(), vendor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
}

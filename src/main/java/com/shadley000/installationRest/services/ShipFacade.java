/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.services;

import com.shadley000.installationRest.beans.ShipBean;
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
public class ShipFacade {

    private static String SQL_GET_SHIP = "SELECT ID, NNAME, LOGO FROM SHIP WHERE ID = ? ";
    private static String SQL_GET_SHIPS = "SELECT ID, NNAME, LOGO FROM SHIP ORDER BY NNAME ";

    public ShipBean getShip(int id) {
        if (id < 0) {
            return null;
        }

        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_SHIP);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ShipBean ship = new ShipBean();
                ship.setId(rs.getInt("ID"));
                ship.setNname(rs.getString("NNAME"));
                ship.setLogo(rs.getString("LOGO"));
                return ship;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
         throw new EntityNotFoundException("Unable to find Ship Id "+id);
    }
     

    public Map<Integer, ShipBean> getShips() {
        Map<Integer, ShipBean> map = new HashMap<>();

        try (Connection connection = SQLConnectionFactory.getConnection()) {

            PreparedStatement stmt = connection.prepareStatement(SQL_GET_SHIPS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ShipBean ship = new ShipBean();
                ship.setId(rs.getInt("ID"));
                ship.setNname(rs.getString("NNAME"));
                ship.setLogo(rs.getString("LOGO"));
                map.put(ship.getId(), ship);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ShipFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }
}

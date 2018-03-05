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
public class InstallationBean  implements Serializable {
    
    private String id;
    private String parserName;
    private String nname;
    private String dataDirectory;
    
    private VendorBean vendor;
    private ShipBean ship;
    private ContractorBean contractor;

    public InstallationBean() {
    }

    public InstallationBean(VendorBean vendor, ShipBean ship, ContractorBean contractor) {
        this.vendor = vendor;
        this.ship = ship;
        this.contractor = contractor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParserName() {
        return parserName;
    }

    public void setParserName(String parserName) {
        this.parserName = parserName;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public VendorBean getVendor() {
        return vendor;
    }

    public void setVendor(VendorBean vendor) {
        this.vendor = vendor;
    }

    public ShipBean getShip() {
        return ship;
    }

    public void setShip(ShipBean ship) {
        this.ship = ship;
    }

    public ContractorBean getContractor() {
        return contractor;
    }

    public void setContractor(ContractorBean contractor) {
        this.contractor = contractor;
    }
    
    
}

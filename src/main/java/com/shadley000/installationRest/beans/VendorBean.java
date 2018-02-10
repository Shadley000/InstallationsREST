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
public class VendorBean  implements Serializable {
    private int id;
    private String nname;
    private String logo;

    public VendorBean() {
    }

    public VendorBean(String nname, String logo) {
        this.nname = nname;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}

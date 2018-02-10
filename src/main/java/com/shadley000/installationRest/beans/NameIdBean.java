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
public class NameIdBean  implements Serializable {
    private String nname;
    private int id;

    public NameIdBean() {
    }

    public NameIdBean(String nname, int id) {
        this.nname = nname;
        this.id = id;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}

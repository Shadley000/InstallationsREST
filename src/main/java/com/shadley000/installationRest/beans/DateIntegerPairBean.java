/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author shadl
 */
public class DateIntegerPairBean  implements Serializable {
    
    Date date;
    int count;

    public DateIntegerPairBean() {
    }

    public DateIntegerPairBean(Date date, int count) {
        this.date = date;
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
}

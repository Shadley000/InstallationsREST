/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author shadl
 */
public class AlarmTypeHistogramBean1 implements Serializable {
   
    String alarmTypeId;
    Map<String, Integer> histogram;

    public AlarmTypeHistogramBean1() {
        histogram = new HashMap<String, Integer>();
    }

    int getCount(String key) {
        Integer count = histogram.get(key);
        if (count == null) {
            return 0;
        } else {
            return count;
        }
    }

    public void setCount(String key, int count) {
        histogram.put(key, count);
    }

    public void incrementCount(String key, int count) {
        Integer currentCount = histogram.get(key);
        if (currentCount == null) {
            histogram.put(key, count);
        } else {
            histogram.put(key, currentCount + count);
        }
    }

    public String getAlarmTypeId() {
        return alarmTypeId;
    }

    public void setAlarmTypeId(String alarmTypeId) {
        this.alarmTypeId = alarmTypeId;
    }

}

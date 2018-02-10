/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest;

import com.shadley000.installationRest.beans.AlarmDataBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author shadl
 */

@Path("test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {
    
    
    @GET
    @Path("/{id}/echo")
    public Map<String,String> getPivot(@PathParam("id") int id,
            @DefaultValue("2018-01-01") @QueryParam("from") String from,
            @DefaultValue("2018-01-07") @QueryParam("to") String to) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate;
        Date toDate;
        try {
            fromDate = df.parse(from);
            toDate = df.parse(to);

        } catch (ParseException ex) {
            Logger.getLogger(InstallationResource.class.getName()).log(Level.WARNING, null, ex);
            throw new BadRequestException("Bad date format. use:" + AlarmDataBean.DATE_FORMAT.toString());
        }
        
        Map<String,String> map =  new HashMap<>();

        map.put("id", ""+id);
        map.put("from",from);
        map.put("to", to);
        map.put("fromdate",fromDate.toString());
        map.put("todate", toDate.toString());
        return map;
    }
}

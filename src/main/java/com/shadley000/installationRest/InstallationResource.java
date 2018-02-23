/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest;

import com.shadley000.installationRest.beans.AlarmTypeBean;
import com.shadley000.installationRest.services.AlarmDataFacade;
import com.shadley000.installationRest.services.AlarmFileFacade;
import com.shadley000.installationRest.services.AlarmPivotFacade;
import com.shadley000.installationRest.services.AlarmTypeFacade;
import com.shadley000.installationRest.services.InstallationFacade;
import com.shadley000.installationRest.services.SQLConnectionFactory;
import com.shadley000.userManagerClient.NoConnectionException;
import com.shadley000.userManagerClient.NotFoundException;
import com.shadley000.userManagerClient.TokenClient;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author shadl
 */
@Path("installations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InstallationResource {

    InstallationFacade installationFacade;
    AlarmDataFacade alarmDataFacade;
    AlarmTypeFacade alarmTypeFacade;
    AlarmFileFacade alarmFileFacade;
    AlarmPivotFacade alarmPivotFacade;
    SimpleDateFormat pivotDateFormat;
    SimpleDateFormat historyDateFormat;
    
    TokenClient tokenClient;        
        
    public InstallationResource() {
        installationFacade = new InstallationFacade();
        alarmDataFacade = new AlarmDataFacade();
        alarmTypeFacade = new AlarmTypeFacade();
        alarmFileFacade = new AlarmFileFacade();
        alarmPivotFacade = new AlarmPivotFacade();

        //dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        pivotDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        historyDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        tokenClient = new TokenClient(ConfigurationProperties.TOKENMANAGER_URL); 
        SQLConnectionFactory.init();
    }
    
    protected long getUserID(long token) throws NoConnectionException, NotFoundException{        
        return tokenClient.getUserId(token);       
    }

    @GET
    public Response getIt(@QueryParam("token") long token) {
        logger().log(Level.INFO, "InstallationResource.getIt()");
        
         return Response.ok(installationFacade.getInstallations(),MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();
        //return installationFacade.getInstallations();
    }

    @GET
    @Path("/{id}")
    public Response  getById(@PathParam("id") int id, 
                                @QueryParam("token") long token) {
        logger().log(Level.INFO, "InstallationResource.getById(" + id + ")");
        return  Response.ok(installationFacade.getInstallation(id),MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();
    }

    @GET
    @Path("/{id}/alarmTypes")
    public Response  getAlarmTypes(@PathParam("id") int id, 
                                @QueryParam("token") long token) {
        logger().log(Level.INFO, "InstallationResource.getAlarmTypes(" + id + ")");
        return  Response.ok(alarmTypeFacade.getAlarmTypes(id),MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();
    }

    @GET
    @Path("/{id}/alarmTypes/{alarmid}")
    public Response  getAlarmType(@PathParam("id") int id, @PathParam("alarmid") int alarmId, 
                                @QueryParam("token") long token) {
        logger().log(Level.INFO, "InstallationResource.getAlarmTypes(" + id + ", " + alarmId + ")");
        AlarmTypeBean bean = alarmTypeFacade.getAlarmType(id, alarmId);
        return  Response.ok(bean,MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();
    }

    protected Logger logger() {
        return Logger.getLogger(InstallationResource.class.getName());
    }

    @GET
    @Path("/{id}/history")
    public Response  getHistory(@PathParam("id") int id,
            @QueryParam("from") String from,
            @DefaultValue("00:00:00") @QueryParam("time") String time, 
                                @QueryParam("token") long token) {

        Date fromDate = null;
        try {
            fromDate = historyDateFormat.parse(from+" "+time);

        } catch (ParseException ex) {
            logger().log(Level.WARNING, null, ex);
            //throw new BadRequestException("Bad date format. use:" + AlarmDataBean.DATE_FORMAT.toString());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger().log(Level.INFO, "InstallationResource.getHistory(" + id + ", " + from + ", " + time + ")");
        return  Response.ok(alarmDataFacade.getHistory(id, fromDate),MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();

    }

    @GET
    @Path("/{id}/pivot")
    public Response  getPivot(@PathParam("id") int id,
            @QueryParam("from") String from,
            @QueryParam("to") String to, 
                                @QueryParam("token") long token) {

        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = pivotDateFormat.parse(from);
            toDate = pivotDateFormat.parse(to);
        } catch (ParseException ex) {
            logger().log(Level.WARNING, null, ex);
            // throw new BadRequestException("Bad date format. use:" + AlarmDataBean.DATE_FORMAT.toString());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        logger().log(Level.INFO, "InstallationResource.getPivot(" + id + ", " + from + ", " + to + ")");
        return  Response.ok(alarmPivotFacade.getPivot(id, fromDate, toDate),MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();
    }

    @GET
    @Path("/{id}/files")
    public Response  getFiles(@PathParam("id") int id, 
                                @QueryParam("token") long token) {
        logger().log(Level.INFO, "InstallationResource.getFiles(" + id + ")");
        return  Response.ok(alarmFileFacade.getAlarmFiles(id),MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();
    }

    @GET
    @Path("/{id}/files/{fileId}")
    public Response  getFile(@PathParam("id") int id, @PathParam("fileId") int fileId, 
                                @QueryParam("token") long token) {

        logger().log(Level.INFO, "InstallationResource.getFile(" + id + ", " + fileId + ")");
        return  Response.ok(alarmFileFacade.getAlarmFile(id, fileId),MediaType.APPLICATION_JSON)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
            .build();
    }

}

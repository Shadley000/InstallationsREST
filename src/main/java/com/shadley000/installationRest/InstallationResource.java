/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shadley000.installationRest;

import com.shadley000.installationRest.beans.AlarmDataBean;
import com.shadley000.installationRest.beans.AlarmFileBean;
import com.shadley000.installationRest.beans.AlarmTypeBean;
import com.shadley000.installationRest.beans.InstallationBean;
import com.shadley000.installationRest.services.AlarmDataFacade;
import com.shadley000.installationRest.services.AlarmFileFacade;
import com.shadley000.installationRest.services.AlarmPivotFacade;
import com.shadley000.installationRest.services.AlarmTypeFacade;
import com.shadley000.installationRest.services.InstallationFacade;
import com.shadley000.installationRest.services.SQLConnectionFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    public InstallationResource() {
        installationFacade = new InstallationFacade();
        alarmDataFacade = new AlarmDataFacade();
        alarmTypeFacade = new AlarmTypeFacade();
        alarmFileFacade = new AlarmFileFacade();
        alarmPivotFacade = new AlarmPivotFacade();

        //dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        pivotDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        historyDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SQLConnectionFactory.init();
    }

    @GET
    public Map<Integer, String> getIt() {
        logger().log(Level.INFO, "InstallationResource.getIt()");
        return installationFacade.getInstallations();
    }

    @GET
    @Path("/{id}")
    public InstallationBean getById(@PathParam("id") int id) {
        logger().log(Level.INFO, "InstallationResource.getById(" + id + ")");
        return installationFacade.getInstallation(id);
    }

    @GET
    @Path("/{id}/alarmTypes")
    public Map<Integer, AlarmTypeBean> getAlarmTypes(@PathParam("id") int id) {
        logger().log(Level.INFO, "InstallationResource.getAlarmTypes(" + id + ")");
        return alarmTypeFacade.getAlarmTypes(id);
    }

    @GET
    @Path("/{id}/alarmTypes/{alarmid}")
    public AlarmTypeBean getAlarmType(@PathParam("id") int id, @PathParam("alarmid") int alarmId) {
        logger().log(Level.INFO, "InstallationResource.getAlarmTypes(" + id + ", " + alarmId + ")");
        AlarmTypeBean bean = alarmTypeFacade.getAlarmType(id, alarmId);
        return bean;
    }

    protected Logger logger() {
        return Logger.getLogger(InstallationResource.class.getName());
    }

    @GET
    @Path("/{id}/history")
    public List<AlarmDataBean> getHistory(@PathParam("id") int id,
            @QueryParam("from") String from,
            @DefaultValue("00:00:00") @QueryParam("time") String time) {

        Date fromDate = null;
        try {
            fromDate = historyDateFormat.parse(from+" "+time);

        } catch (ParseException ex) {
            logger().log(Level.WARNING, null, ex);
            throw new BadRequestException("Bad date format. use:" + AlarmDataBean.DATE_FORMAT.toString());
        }

        logger().log(Level.INFO, "InstallationResource.getHistory(" + id + ", " + from + ", " + time + ")");
        return alarmDataFacade.getHistory(id, fromDate);

    }

    @GET
    @Path("/{id}/pivot")
    public Map<Integer, int[]> getPivot(@PathParam("id") int id,
            @QueryParam("from") String from,
            @QueryParam("to") String to) {

        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = pivotDateFormat.parse(from);
            toDate = pivotDateFormat.parse(to);
        } catch (ParseException ex) {
            logger().log(Level.WARNING, null, ex);
            // throw new BadRequestException("Bad date format. use:" + AlarmDataBean.DATE_FORMAT.toString());
        }

        logger().log(Level.INFO, "InstallationResource.getPivot(" + id + ", " + from + ", " + to + ")");
        return alarmPivotFacade.getPivot(id, fromDate, toDate);
    }

    @GET
    @Path("/{id}/files")
    public Map<Integer, AlarmFileBean> getFiles(@PathParam("id") int id) {
        logger().log(Level.INFO, "InstallationResource.getFiles(" + id + ")");
        return alarmFileFacade.getAlarmFiles(id);
    }

    @GET
    @Path("/{id}/files/{fileId}")
    public AlarmFileBean getFile(@PathParam("id") int id, @PathParam("fileId") int fileId) {

        logger().log(Level.INFO, "InstallationResource.getFile(" + id + ", " + fileId + ")");
        return alarmFileFacade.getAlarmFile(id, fileId);
    }

}

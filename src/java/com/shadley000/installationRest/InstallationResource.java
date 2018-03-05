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
import com.shadley000.installationRest.services.ContractorFacade;
import com.shadley000.installationRest.services.OperatorFacade;
import com.shadley000.installationRest.services.ShipFacade;
import com.shadley000.userManagerClient.UsersManagerClient;
import com.shadley000.userManagerClient.beans.Role;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("installations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InstallationResource {

    InstallationFacade installationFacade;
    AlarmDataFacade alarmDataFacade;
    AlarmTypeFacade alarmTypeFacade;
    AlarmFileFacade alarmFileFacade;
    AlarmPivotFacade alarmPivotFacade;
    ShipFacade shipFacade;
    ContractorFacade contractorFacade;
    OperatorFacade operatorFacade;

    SimpleDateFormat pivotDateFormat;
    SimpleDateFormat historyDateFormat;

    UsersManagerClient usersManagerClient;

    public InstallationResource() {
        installationFacade = new InstallationFacade();
        alarmDataFacade = new AlarmDataFacade();
        alarmTypeFacade = new AlarmTypeFacade();
        alarmFileFacade = new AlarmFileFacade();
        alarmPivotFacade = new AlarmPivotFacade();
        shipFacade = new ShipFacade();
        contractorFacade = new ContractorFacade();
        operatorFacade = new OperatorFacade();

        pivotDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        historyDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        usersManagerClient = new UsersManagerClient(ConfigurationProperties.USERMANAGER_URL,  ConfigurationProperties.USERMANAGER_USER, ConfigurationProperties.USERMANAGER_PASSWORD);
        SQLConnectionFactory.init();
    }

    protected Response okResponse(Object obj) {
        return Response.ok(obj, MediaType.APPLICATION_JSON)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();
    }

    protected Response noContentResponse() {
        return Response.noContent().header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD")
                .build();
    }

    protected Logger logger() {
        return Logger.getLogger(InstallationResource.class.getName());
    }

    protected void securityCheck(String tokenStr,String installationId) throws ForbiddenException, IOException {
        Set<String> installationSet = securityCheck( tokenStr);
        if(!installationSet.contains(installationId)) throw new ForbiddenException();
    }
    
    protected Set<String> securityCheck(String tokenStr) throws ForbiddenException, IOException {
        String userId = usersManagerClient.getUserId(tokenStr);
        List<Role> roleList = usersManagerClient.getRolesByUser(userId);
        if (roleList.isEmpty()) {
            throw new ForbiddenException();
        }
         Set<String> installationIdSet = new HashSet<>();
        for (Role role : roleList) {
            if (role.getUd1() != null) {
                if (null != role.getUd1()) switch (role.getUd1()) {
                    case "Ship":
                        installationIdSet.addAll(shipFacade.getInstallationsByShipName(role.getName()));
                        break;
                    case "Contractor":
                        installationIdSet.addAll(contractorFacade.getInstallationsByContractorName(role.getName()));
                        break;
                    case "Operator":
                        installationIdSet.addAll( operatorFacade.getInstallationsByOperatorName(role.getName()));
                        break;
                    default:
                        break;
                }
            }
        }
        if (installationIdSet.isEmpty()) throw new ForbiddenException();
        else return installationIdSet;
    }

    @GET
    @Path("/health")
    public Response getHealth() {
        logger().log(Level.INFO, "getHealth ");
        return Response.ok("good").build();
    }

    @GET
    public Response getIt(@QueryParam("token") String tokenStr)  throws ForbiddenException, IOException {
        logger().log(Level.INFO, "InstallationResource.getIt()");
        Set<String> enabledInstallationsSet = securityCheck( tokenStr);
        return okResponse(installationFacade.getInstallations(enabledInstallationsSet));
    }

    @GET
    @Path("/{installationId}")
    public Response getById(@PathParam("installationId") String installationId,
            @QueryParam("token") String tokenStr) throws ForbiddenException, IOException {
        logger().log(Level.INFO, "InstallationResource.getById(" + installationId + ")");
        securityCheck( tokenStr, installationId);
        return okResponse(installationFacade.getInstallation(installationId));
    }

    @GET
    @Path("/{installationId}/alarmTypes")
    public Response getAlarmTypes(@PathParam("installationId") String installationId,
            @QueryParam("token") String tokenStr) throws ForbiddenException , IOException {
        logger().log(Level.INFO, "InstallationResource.getAlarmTypes(" + installationId + ")");
        securityCheck( tokenStr, installationId);
        return okResponse(alarmTypeFacade.getAlarmTypes(installationId));
    }

    @GET
    @Path("/{installationId}/alarmTypes/{alarmid}")
    public Response getAlarmType(@PathParam("installationId") String installationId, @PathParam("alarmid") String alarmId,
            @QueryParam("token") String tokenStr)  throws ForbiddenException, IOException {
        logger().log(Level.INFO, "InstallationResource.getAlarmTypes(" + installationId + ", " + alarmId + ")");
         securityCheck( tokenStr, installationId);
       AlarmTypeBean bean = alarmTypeFacade.getAlarmType(installationId, alarmId);
        return okResponse(bean);
    }

    @GET
    @Path("/{installationId}/history")
    public Response getHistory(@PathParam("installationId") String installationId,
            @QueryParam("from") String from,
            @DefaultValue("00:00:00") @QueryParam("time") String time,
            @QueryParam("token") String tokenStr)  throws ForbiddenException, IOException {
         securityCheck( tokenStr, installationId);
       Date fromDate = null;
        try {
            fromDate = historyDateFormat.parse(from + " " + time);
        } catch (ParseException ex) {
            logger().log(Level.WARNING, null, ex);
            //throw new BadRequestException("Bad date format. use:" + AlarmDataBean.DATE_FORMAT.toString());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        logger().log(Level.INFO, "InstallationResource.getHistory(" + installationId + ", " + from + ", " + time + ")");
        return okResponse(alarmDataFacade.getHistory(installationId, fromDate));
    }

    @GET
    @Path("/{installationId}/pivot")
    public Response getPivot(@PathParam("installationId") String installationId,
            @QueryParam("from") String from,
            @QueryParam("to") String to,
            @QueryParam("token") String tokenStr)   throws ForbiddenException, IOException{

         securityCheck( tokenStr, installationId);
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

        logger().log(Level.INFO, "InstallationResource.getPivot(" + installationId + ", " + from + ", " + to + ")");
        return okResponse(alarmPivotFacade.getPivot(installationId, fromDate, toDate));
    }

    @GET
    @Path("/{installationId}/files")
    public Response getFiles(@PathParam("installationId") String installationId,
            @QueryParam("token") String tokenStr)   throws ForbiddenException, IOException{
         securityCheck( tokenStr, installationId);
       logger().log(Level.INFO, "InstallationResource.getFiles(" + installationId + ")");
        return okResponse(alarmFileFacade.getAlarmFiles(installationId));
    }

    @GET
    @Path("/{installationId}/files/{fileId}")
    public Response getFile(@PathParam("installationId") String installationId, @PathParam("fileId") String fileId,
            @QueryParam("token") String tokenStr)   throws ForbiddenException, IOException{
 securityCheck( tokenStr, installationId);
       
        logger().log(Level.INFO, "InstallationResource.getFile(" + installationId + ", " + fileId + ")");
        return okResponse(alarmFileFacade.getAlarmFile(installationId, fileId));
    }

}

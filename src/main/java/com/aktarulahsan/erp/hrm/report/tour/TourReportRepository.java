package com.aktarulahsan.erp.hrm.report.tour;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CoreJasperService;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;

import com.aktarulahsan.erp.hrm.department.DepartmentModel;
import com.aktarulahsan.erp.hrm.department.DepartmentRepository;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeRepository;
import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import com.aktarulahsan.erp.hrm.leave.LeaveRepository;
import com.aktarulahsan.erp.hrm.model.RoAttendance;
import com.aktarulahsan.erp.hrm.report.dtos.LeaveDto;

import com.aktarulahsan.erp.hrm.report.tour.dto.TourDto;
import com.aktarulahsan.erp.util.CommonFunction;
import com.aktarulahsan.erp.util.Def;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;


@Repository
@Transactional
public class TourReportRepository extends BaseRepository {

    @Autowired
    CoreJasperService coreJasperService;

    @Autowired
    LeaveRepository repository;
    @Autowired
    HrmEmployeeRepository employeeRepository;
    @Autowired
    DepartmentRepository departmentRepository;

    public CusJasperReportDef tourReport(String reqObj) {
        JSONObject json = new JSONObject(reqObj);
        TourDto model = objectMapperReadValue(reqObj, TourDto.class);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("MMMMdd");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        HrmEmployeeModel empModel = employeeRepository.findById(model.getEmpCardNo());
        DepartmentModel dpModel = (DepartmentModel) departmentRepository.findObjectById(empModel.getDepartment_code());

        String departmentName = dpModel.getDepartment_name();
        String strDeComID= "0001" ;
        String strDivision = empModel.getDivision_name();
        String strDepartmentCard= empModel.getDepartment_code();
        String strMonthID= "" ;
        String strOption = Def.getString(json, "strOption") ;
        String strOption2= Def.getString(json, "strOption2") ;
        String strFromdate=Def.getString(json, "strFromdate")  ;
        String strToDate=Def.getString(json, "strToDate") ;
        int intSeparation =1 ;
        Connection con = null;
        ResultSet rsp = null;
        Statement stm = null;
        PreparedStatement sts = null;


        Connection con2 = null;
        ResultSet rsp2 = null;
        Statement stm2 = null;


//        TourDto model = new TourDto();
        List<TourDto> modelLists = new ArrayList<>();

        strMonthID= formatter2.format(new Date());

        model.setCompanyName("Deeplaid Laboratories Ltd.");
        model.setCompanyAddress("5th Floor, Al-Habib Complex, Kaptan Bazar, Wari, Dhaka 1203");
        model.setEmp_name(empModel.getEmp_name());
        model.setDivision_name(empModel.getDivision_name());
        model.setEmp_type_name(empModel.getEmp_type_name());
        model.setDepartment_name(dpModel.getDepartment_name());
        intSeparation = 1;


//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        int years = Calendar.getInstance().get(Calendar.YEAR);
        LocalDate firstDay = YearMonth.of(years,1).atDay(1);
        LocalDate lastDay = YearMonth.of(years,12).atDay(31);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date fsDate =Date.from(firstDay.atStartOfDay(defaultZoneId).toInstant());
        Date  lsDate =Date.from(lastDay.atStartOfDay(defaultZoneId).toInstant());
        String s = formatter.format(model.getFrom_date());
        String p = formatter.format(model.getTo_date());


//        JSONObject json = new JSONObject(reqObj);
        StringBuilder sqlQuery = new StringBuilder();


        List<RoAttendance> ooAttan = new ArrayList<>();
        try {
//            con = getOraConnection();
//            stm = con.createStatement();
//            int a = stm.executeUpdate(sqlQuery.toString());

        sqlQuery.append("SELECT A.EMP_CARD_NO,E.DIVISION_NAME,E.EMP_NAME,D.DEPARTMENT_NAME,DG.DESIGNATION_NAME ,A.MONTH_ID,A.ATTEN_DATEIN,A.ATTEN_STATUS,A.ATTEN_COMMENTS ");
        sqlQuery.append(",A.ATTEN_SHIFT,A.ATTEN_TIMEIN,A.ATTEN_TIMEOUT,A.TOTAL_LATE ATTEN_LATE,A.STAY_HOUR STAY_HOUR,E.EMP_JOIN_DATE ");
        sqlQuery.append(",A.ATTEN_OT_TOTAL from  HRS_TRANS_WORK_ATTENDANCE A , ");
        sqlQuery.append("HRS_EMPLOYEE E, HRS_DEPARTMENT D ,HRS_DESIGNATION DG ");
        sqlQuery.append("WHERE A.EMP_CARD_NO= E.EMP_CARD_NO and E.DEPARTMENT_CODE= D.DEPARTMENT_CODE and E.DESIGNATION_CODE= DG.DESIGNATION_CODE ");
        sqlQuery.append("AND E.EMP_TYPE_NAME <> 'NONE' ");
        if (Def.getInteger(json, "intSeparation") == 1) {
            sqlQuery.append("AND E.EMP_STATUS >= 3 ");
        } else {
            sqlQuery.append("AND E.EMP_STATUS < 3 ");
        }
        if (Def.getString(json, "strDivision") != "") {
            sqlQuery.append("AND E.DIVISION_NAME='" + strDivision + "'");
        }
        if (strOption == "Department") {
            if (strDepartmentCard != "") {
                sqlQuery.append("AND D.DEPARTMENT_NAME IN (" + model.getDepartment_name() + ")");
            }
        } else {
            if (strOption != "") {
                if (strOption == "EP") {
                    sqlQuery.append("AND A.EMP_CARD_NO = '" + model.getEmpCardNo() + "'");
                } else {
                    sqlQuery.append("AND A.EMP_CARD_NO IN ('" + model.getEmpCardNo() + "')");
                }
            }
        }
        if (strOption2 == "Month") {
            if (strMonthID != "") {

                sqlQuery.append("AND A.MONTH_ID = '" + strMonthID + "'");
            }
        } else {
            if (strFromdate != "") {

                sqlQuery.append("AND (A.ATTEN_DATEIN BETWEEN (" + "CONVERT(datetime,'"+s +"',103)"  + ") AND (" + "CONVERT(datetime,'"+p +"',103)" + ")) ");
            }
        }
        sqlQuery.append("ORDER BY  A.EMP_CARD_NO ");


        con = getOraConnection();
        stm = con.createStatement();
        rsp = stm.executeQuery(sqlQuery.toString());


        while (rsp.next()) {
            RoAttendance oEmpAtt = new RoAttendance();

            if (rsp.getString("EMP_CARD_NO") != "")
            {
                oEmpAtt.setStrCardNO(rsp.getString("EMP_CARD_NO"));
            }
            if (rsp.getString("EMP_NAME") != "")
            {
                oEmpAtt.setStrName(rsp.getString("EMP_NAME"));
            }
            if (rsp.getString("DIVISION_NAME") != "")
            {
                oEmpAtt.setStrDesignation(rsp.getString("DIVISION_NAME"));
            }
            if (rsp.getString("DEPARTMENT_NAME") != "")
            {
                oEmpAtt.setStrDepartment(rsp.getString("DEPARTMENT_NAME"));
            }
            if (rsp.getString("DESIGNATION_NAME") != "")
            {
                oEmpAtt.setStrDesignation(rsp.getString("DESIGNATION_NAME"));
            }
            if (rsp.getString("ATTEN_DATEIN") != "")
            {

                Date date = inputFormat.parse(rsp.getString("ATTEN_DATEIN"));

                oEmpAtt.setStrDate(formatter.format(date));
            }
            if (rsp.getString("EMP_JOIN_DATE") != "")
            {
                Date date = inputFormat.parse(rsp.getString("EMP_JOIN_DATE"));

                oEmpAtt.setStrJoingDate(formatter.format(date));

            }
            if (rsp.getString("ATTEN_STATUS") != "")
            {
                oEmpAtt.setStrStatus(rsp.getString("ATTEN_STATUS"));
            }
            if (rsp.getString("ATTEN_SHIFT") != "")
            {
                oEmpAtt.setStrShiftName(rsp.getString("ATTEN_SHIFT"));
            }
            if (rsp.getString("ATTEN_TIMEIN") != "")
            {
                oEmpAtt.setStrInTime(rsp.getString("ATTEN_TIMEIN"));
            }
            if (rsp.getString("ATTEN_TIMEOUT") != "")
            {
                oEmpAtt.setStrOutTime(rsp.getString("ATTEN_TIMEOUT"));
            }
            if (rsp.getString("ATTEN_LATE") != "")
            {
                oEmpAtt.setStrLateTime(rsp.getString("ATTEN_LATE"));
            }
            if (rsp.getString("STAY_HOUR") != "")
            {
                oEmpAtt.setStrStayTime(rsp.getString("STAY_HOUR"));
            }
            if (rsp.getString("ATTEN_COMMENTS") != "")
            {
                oEmpAtt.setStrRemarks(rsp.getString("ATTEN_COMMENTS"));
            }

            ooAttan.add(oEmpAtt);
        }
    }catch(Exception e){
        e.printStackTrace();
    }

        model.setItemList(ooAttan);


// for sub report
        List<RoAttendance> ooAttanSub = new ArrayList<>();

        try {

            StringBuilder sqlQuerys = new StringBuilder();
            sqlQuerys.append("SELECT A.EMP_CARD_NO,count(A.ATTEN_STATUS)as CountAttn ,A.ATTEN_STATUS ");
            sqlQuerys.append( "from  HRS_TRANS_WORK_ATTENDANCE A , ");
            sqlQuerys.append( "HRS_EMPLOYEE E, HRS_DEPARTMENT D ,HRS_DESIGNATION DG ");
            sqlQuerys.append( "WHERE A.EMP_CARD_NO= E.EMP_CARD_NO and E.DEPARTMENT_CODE= D.DEPARTMENT_CODE and E.DESIGNATION_CODE= DG.DESIGNATION_CODE ");


            if (strDivision != "")
            {
                sqlQuerys.append( "AND E.DIVISION_NAME='" + strDivision + "'");
            }

            if (strOption == "Department")
            {
                if (strDepartmentCard != "")
                {
                    sqlQuerys.append( "AND D.DEPARTMENT_NAME IN (" + strDepartmentCard + ")");
                }
            }
            else
            {
                if (strOption == "EP") {
                    sqlQuerys.append("AND A.EMP_CARD_NO = '" + model.getEmpCardNo() + "'");
                } else {
                    sqlQuerys.append("AND A.EMP_CARD_NO IN ('" + model.getEmpCardNo() + "')");
                }

            }
            if (strOption2 == "Month")
            {
                if (strMonthID != "")
                {
                    sqlQuerys.append( "AND A.MONTH_ID = '" + strMonthID + "'");
                }
            }
            else
            {
                if (strFromdate != "")
                {

                    sqlQuerys.append( "AND (A.ATTEN_DATEIN BETWEEN (" +"CONVERT(datetime,'"+s +"',103)"  + ") AND (" + "CONVERT(datetime,'"+p +"',103)"  + ")) ");
                }
            }


            sqlQuerys.append( "group by A.EMP_CARD_NO,A.ATTEN_STATUS ");
            sqlQuerys.append( "ORDER BY EMP_CARD_NO ,A.ATTEN_STATUS ");


            con2 = getOraConnection();
            stm2 = con2.createStatement();
            rsp2 = stm2.executeQuery(sqlQuerys.toString());

            while (rsp2.next())
            {
                RoAttendance oEmpAtts = new RoAttendance();

                if (rsp2.getString("EMP_CARD_NO") != "")
                {

                    oEmpAtts.setStrCardNO(rsp2.getString("EMP_CARD_NO"));
                }

                if (rsp2.getString("ATTEN_STATUS") != "")
                {
                    oEmpAtts.setStrStatus(rsp2.getString("ATTEN_STATUS"));
//                    oEmpAtt.strStatus = dr["ATTEN_STATUS"].ToString();
                }
                if (rsp2.getString("CountAttn")!= "")
                {
                    oEmpAtts.setDblAttCount(rsp2.getDouble("CountAttn"));
//                    oEmpAtt.dblAttCount = Convert.ToDouble(dr["CountAttn"].ToString());
                }
                ooAttanSub.add(oEmpAtts);
            }
            if (rsp2.hashCode() <0)
            {
                RoAttendance oEmpAtt = new RoAttendance();
                oEmpAtt.setStrCardNO("");
                oEmpAtt.setStrStatus("");
                oEmpAtt.setDblAmount(0.0);
                ooAttanSub.add(oEmpAtt);
            }
        }catch (Exception e){

        }







        model.setItemSubList(ooAttanSub);



        modelLists.add(model);
//        Map<String, Object> parameterMap = new HashMap<String, Object>();
//        parameterMap.put("SUBREPORT_DIR", CommonFunction.getResoucePath("/punch/punchSubreport"));
        CusJasperReportDef report = new CusJasperReportDef();
        report.setOutputFilename("demo");
        report.setReportName("punchReport");
        report.setReportDir(CommonFunction.getResoucePath("/report") + "/");
        report.setReportFormat(CommonFunction.printFormat("PDF"));
//        report.setParameters(parameterMap);
        report.setReportData(modelLists);
        ByteArrayOutputStream baos = null;

        try {
            baos = coreJasperService.generateReport(report);
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            finallyOutputStream(baos);
        }
        report.setContent(baos.toByteArray());

    return  report;
    }

public List<RoAttendance> modelLists(){
//
//    StringBuilder sqlQuery = new StringBuilder();
//    List<RoAttendance> ooAttan = = new ArrayList<>();
//    sqlQuery.append("SELECT A.EMP_CARD_NO,count(A.ATTEN_STATUS)as CountAttn ,A.ATTEN_STATUS ");
//    sqlQuery.append( "from  HRS_TRANS_WORK_ATTENDANCE A , ");
//    sqlQuery.append( "HRS_EMPLOYEE E, HRS_DEPARTMENT D ,HRS_DESIGNATION DG ");
//    sqlQuery.append( "WHERE A.EMP_CARD_NO= E.EMP_CARD_NO and E.DEPARTMENT_CODE= D.DEPARTMENT_CODE and E.DESIGNATION_CODE= DG.DESIGNATION_CODE ");
//
//
//    if (strDivision != "")
//    {
//        sqlQuery.append( "AND E.DIVISION_NAME='" + strDivision + "'");
//    }
//
//    if (strOption == "Department")
//    {
//        if (strDepartmentCard != "")
//        {
//            sqlQuery.append( "AND D.DEPARTMENT_NAME IN (" + strDepartmentCard + ")");
//        }
//    }
//    else
//    {
//        if (strOption == "EP")
//        {
//            sqlQuery.append( "AND A.EMP_CARD_NO = '" + strDepartmentCard + "'");
//        }
//        else
//        {
//            sqlQuery.append( "AND A.EMP_CARD_NO IN (" + strDepartmentCard + ")");
//        }
//    }
//    if (strOption2 == "Month")
//    {
//        if (strMonthID != "")
//        {
//            sqlQuery.append( "AND A.MONTH_ID = '" + strMonthID + "'");
//        }
//    }
//    else
//    {
//        if (strFromdate != "")
//        {
//
//            sqlQuery.append( "AND (A.ATTEN_DATEIN BETWEEN (" + Utility.cvtSQLDateString(strFromdate) + ") AND (" + Utility.cvtSQLDateString(strToDate) + ")) ");
//        }
//    }
//
//
//    sqlQuery.append( "group by A.EMP_CARD_NO,A.ATTEN_STATUS ");
//    sqlQuery.append( "ORDER BY EMP_CARD_NO ,A.ATTEN_STATUS ");
//
//
//
//        while (dr.Read())
//        {
//            RoAttendance oEmpAtt = new RoAttendance();
//            if (dr["EMP_CARD_NO"].ToString() != "")
//            {
//                oEmpAtt.strCardNO = dr["EMP_CARD_NO"].ToString();
//            }
//            if (dr["ATTEN_STATUS"].ToString() != "")
//            {
//                oEmpAtt.strStatus = dr["ATTEN_STATUS"].ToString();
//            }
//            if (dr["CountAttn"].ToString() != "")
//            {
//                oEmpAtt.dblAttCount = Convert.ToDouble(dr["CountAttn"].ToString());
//            }
//            ooAttan.Add(oEmpAtt);
//        }
//        if (!dr.HasRows)
//        {
//            RoAttendance oEmpAtt = new RoAttendance();
//            oEmpAtt.strCardNO = "";
//            oEmpAtt.strStatus = "";
//            oEmpAtt.dblAttCount = 0;
//            ooAttan.add(oEmpAtt);
//        }
//        return ooAttan;
//
    return null;
    }

}

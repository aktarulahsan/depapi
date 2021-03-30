package com.aktarulahsan.erp.hrm.report.bloodGroup;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CoreJasperService;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.department.DepartmentRepository;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeRepository;
import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import com.aktarulahsan.erp.hrm.model.RoAttendance;
import com.aktarulahsan.erp.hrm.report.dtos.LeaveDto;
import com.aktarulahsan.erp.util.CommonFunction;
import com.aktarulahsan.erp.util.Def;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class BloodGroupReportRepository  extends BaseRepository {

    @Autowired
    CoreJasperService coreJasperService;

    @Autowired
    private JdbcTemplate db;

    @Autowired
    HrmEmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    public CusJasperReportDef bloodGReports(String reqObj) {
        JSONObject json = new JSONObject(reqObj);
        BgreportDto umodel = objectMapperReadValue(reqObj, BgreportDto.class);
        BgreportDto model = new BgreportDto();
        List<BloodGroupReportDTO> modelLists = new ArrayList<>();
        List<LeaveModel> modelList = new ArrayList<>();
//		Response rs = repository.findDetailsById(umodel.getEmpCardNo());

        HrmEmployeeModel empModel = employeeRepository.findById(umodel.getEmpCardNo());
        StringBuilder sqlQuery = new StringBuilder();

        Connection con = null;
        ResultSet rsp = null;
        Statement stm = null;

        model.setCompanyName("Deeplaid Laboratories Ltd.");
        model.setCompanyAddress("5th Floor, Al-Habib Complex, Kaptan Bazar, Wari, Dhaka 1203");
        model.setEmp_name(empModel.getEmp_name());
        model.setDivision_name(empModel.getDivision_name());
        model.setEmp_type_name(empModel.getEmp_type_name());
        model.setEmpCardNo(empModel.getEmpCardNo());



        List<BloodGroupReportDTO> ooBranch = new ArrayList<>();
        sqlQuery.append( "SELECT E.EMP_CARD_NO, D.DEP_POS ,E.EMP_NAME, E.DIVISION_NAME,D.DEPARTMENT_NAME, P.EMP_BLOOD_GROUP,E.EMP_PRE_PHONE,E.EMP_COR_PHONE,DG.DESIGNATION_NAME ");
        sqlQuery.append( "FROM HRS_EMPLOYEE E, HRS_DEPARTMENT D,HRS_EMP_PERSONAL_INFO P, HRS_DESIGNATION DG ");
        sqlQuery.append( "WHERE E.DEPARTMENT_CODE=D.DEPARTMENT_CODE ");
        sqlQuery.append( "and  E.EMP_CARD_NO=P.EMP_CARD_NO ");
        sqlQuery.append( "and  E.DESIGNATION_CODE=DG.DESIGNATION_CODE ");

        String bGroup = Def.getString(json, "strBloodGroup");

        if (bGroup != null)
        {
            sqlQuery.append( "and P.EMP_BLOOD_GROUP='" + bGroup + "'");
        }
        sqlQuery.append( "order by E.DIVISION_NAME,D.DEP_POS,DG.DES_POS");

       try {




           con = getOraConnection();
           stm = con.createStatement();
           rsp = stm.executeQuery(sqlQuery.toString());

           while (rsp.next())
           {
               BloodGroupReportDTO oEmpinfo = new BloodGroupReportDTO();
               if (rsp.getString("EMP_CARD_NO") != "")
               {
                   oEmpinfo.setStrCardNo(rsp.getString("EMP_CARD_NO"));
               }


               if (rsp.getString("EMP_NAME")!= "")
               {
                   oEmpinfo.setStrEmpName(rsp.getString("EMP_NAME"));
               }
               if (rsp.getString("DIVISION_NAME") != "")
               {
                   oEmpinfo.setStrDivision(rsp.getString("DIVISION_NAME"));
               }
               if (rsp.getString("DEPARTMENT_NAME") != "")
               {
                   oEmpinfo.setStrDepartment(rsp.getString("DEPARTMENT_NAME"));
               }
               if (rsp.getString("DESIGNATION_NAME")!= "")
               {
                   oEmpinfo.setStrDesignation(rsp.getString("DESIGNATION_NAME")) ;
               }
               if (rsp.getString("EMP_BLOOD_GROUP") != "")
               {
                   oEmpinfo.setStrBloodGroup(rsp.getString("EMP_BLOOD_GROUP"));
               }
               if (rsp.getString("EMP_PRE_PHONE")!= "")
               {
                   oEmpinfo.setStrPhoneNumber(rsp.getString("EMP_PRE_PHONE"));
               }
               if (rsp.getString("EMP_COR_PHONE")!= "")
               {
                   oEmpinfo.setStrPhoneNumber2(rsp.getString("EMP_COR_PHONE"));
               }
               ooBranch.add(oEmpinfo);
           }

           if (rsp.hashCode() <0)
           {
               BloodGroupReportDTO oEmpinfo = new BloodGroupReportDTO();
               oEmpinfo.setStrCardNo("");
               oEmpinfo.setStrEmpName("");
               oEmpinfo.setStrDivision("");
               oEmpinfo.setStrDepartment("");
               oEmpinfo.setStrBloodGroup("");
               ooBranch.add(oEmpinfo);
           }

       }catch (Exception e){

       }
//        model.setItemList(ooBranch);
        modelLists= ooBranch;

        CusJasperReportDef report = new CusJasperReportDef();
        report.setOutputFilename("blood Group");
        report.setReportName("bgReport");
        report.setReportDir(CommonFunction.getResoucePath("/report/bloodGroupReport") + "/");
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


}

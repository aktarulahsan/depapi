package com.aktarulahsan.erp.hrm.report.pabx;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CoreJasperService;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.department.DepartmentModel;
import com.aktarulahsan.erp.hrm.department.DepartmentRepository;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeRepository;
import com.aktarulahsan.erp.hrm.model.RoAttendance;
import com.aktarulahsan.erp.hrm.report.bloodGroup.BgreportDto;
import com.aktarulahsan.erp.util.CommonFunction;
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
public class PabxReportRepository  extends BaseRepository {

    @Autowired
    CoreJasperService coreJasperService;

    @Autowired
    private JdbcTemplate db;

    @Autowired
    HrmEmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;


    public CusJasperReportDef pabxports(String reqObj) {
        JSONObject json = new JSONObject(reqObj);
        PabxReportDto umodel = objectMapperReadValue(reqObj, PabxReportDto.class);
        List<PabxReportDto> pabxReportList = new ArrayList<>();

        HrmEmployeeModel empModel = employeeRepository.findById(umodel.getStrEmpID());
//        DepartmentModel dpModel = (DepartmentModel) departmentRepository.findObjectById(empModel.getDepartment_code());
        Connection con = null;
        ResultSet rsp = null;
        Statement stm = null;


        umodel.setStrDivision(empModel.getDivision_name());
        StringBuilder sqlQuery = new StringBuilder();
        List<PabxReportDto> pabxList = new ArrayList<>();
        try {
            sqlQuery.append("SELECT  HRS_EMPLOYEE.EMP_TYPE_NAME, HRS_EMPLOYEE.EMP_NAME,  HRS_DEPARTMENT.DEP_POS, HRS_DESIGNATION.DES_POS, ");
             sqlQuery.append("HRS_EMPLOYEE.EMP_COR_PHONE,HRS_EMPLOYEE.EMP_PRE_PHONE,HRS_EMPLOYEE.PABX_NO, ");
             sqlQuery.append("HRS_EMPLOYEE.EMP_CARD_NO,OLD_CARD_NO, HRS_EMPLOYEE.DIVISION_NAME, HRS_DEPARTMENT.DEPARTMENT_CODE, ");
             sqlQuery.append("HRS_DEPARTMENT.DEPARTMENT_NAME, HRS_DESIGNATION.DESIGNATION_CODE, HRS_DESIGNATION.DESIGNATION_NAME, ");
             sqlQuery.append("HRS_EMPLOYEE.EMP_STATUS FROM HRS_EMPLOYEE,HRS_DEPARTMENT,HRS_DESIGNATION ");
             sqlQuery.append("WHERE HRS_EMPLOYEE.DEPARTMENT_CODE=HRS_DEPARTMENT.DEPARTMENT_CODE  AND ");
             sqlQuery.append("HRS_EMPLOYEE.DESIGNATION_CODE=HRS_DESIGNATION.DESIGNATION_CODE  ");
             sqlQuery.append("AND HRS_EMPLOYEE.DIVISION_NAME ='" + umodel.getStrDivision() + "' ");
             sqlQuery.append("and  HRS_EMPLOYEE.EMP_STATUS IN(1,2,7) ");
            // sqlQuery.append("AND HRS_EMPLOYEE.DIVISION_NAME IN(SELECT DIVISION_NAME  FROM HRS_USER_PRIVILEGES_DIVISION ");
            // sqlQuery.append("WHERE USER_LOGIN_NAME='" + vstrUserName + "') ");
             sqlQuery.append("AND HRS_EMPLOYEE.EMP_TYPE_NAME <>'NONE' ");
             sqlQuery.append("AND (HRS_EMPLOYEE.PABX_NO IS NOT NULL and HRS_EMPLOYEE.PABX_NO <> '') ");
             sqlQuery.append("ORDER BY HRS_DEPARTMENT.DEP_POS, HRS_DESIGNATION.DES_POS ");

            con = getOraConnection();
            stm = con.createStatement();
            rsp = stm.executeQuery(sqlQuery.toString());


            while (rsp.next())
            {
                PabxReportDto oEmpinfo = new PabxReportDto();



                oEmpinfo.setStrEmpName(rsp.getString("EMP_NAME"));
                oEmpinfo.setStrCardNo(rsp.getString("EMP_CARD_NO"));
                oEmpinfo.setStrDivision(rsp.getString("DIVISION_NAME"));
                oEmpinfo.setStrDepartment(rsp.getString("DEPARTMENT_NAME"));
                oEmpinfo.setStrDesignation(rsp.getString("DESIGNATION_NAME"));
                oEmpinfo.setStrCorporatePhoneNumber(rsp.getString("EMP_COR_PHONE"));
                oEmpinfo.setStrPhoneNumber(rsp.getString("EMP_PRE_PHONE"));
                oEmpinfo.setStrPabx(rsp.getString("PABX_NO"));
                pabxList.add(oEmpinfo);
            }
            if (rsp.hashCode() <0)
            {
                PabxReportDto oEmpinfo = new PabxReportDto();
                oEmpinfo.setStrEmpName("");
                oEmpinfo.setStrCardNo("");
                oEmpinfo.setStrDivision("");
                oEmpinfo.setStrDepartment("");
                oEmpinfo.setStrDesignation("");
                oEmpinfo.setStrCorporatePhoneNumber("");
                oEmpinfo.setStrPhoneNumber("");
                oEmpinfo.setStrPabx("");
                pabxList.add(oEmpinfo);

            }
        }catch (Exception e){

        }

        pabxReportList = pabxList;

        CusJasperReportDef report = new CusJasperReportDef();
        report.setOutputFilename("PABX Report");
        report.setReportName("pabxReport");
        report.setReportDir(CommonFunction.getResoucePath("/report/pabx") + "/");
        report.setReportFormat(CommonFunction.printFormat("PDF"));
//        report.setParameters(parameterMap);
        report.setReportData(pabxReportList);
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

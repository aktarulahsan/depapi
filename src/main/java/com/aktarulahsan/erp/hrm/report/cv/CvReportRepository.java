package com.aktarulahsan.erp.hrm.report.cv;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CoreJasperService;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.department.DepartmentRepository;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeRepository;
import com.aktarulahsan.erp.hrm.model.RoEmployeeInformation;
import com.aktarulahsan.erp.hrm.report.tour.dto.TourDto;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;


@Repository
@Transactional
public class CvReportRepository extends BaseRepository {

    @Autowired
    CoreJasperService coreJasperService;

    @Autowired
    private JdbcTemplate db;

    @Autowired
    HrmEmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;



    public CusJasperReportDef cvReports(String reqObj) {
        JSONObject json = new JSONObject(reqObj);
        HrmEmployeeModel model = objectMapperReadValue(reqObj, HrmEmployeeModel.class);
        HrmEmployeeModel empModel = employeeRepository.findById(model.getEmpCardNo());
        Connection con = null;
        ResultSet rsp = null;
        Statement stm = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        List<TourDto> modelLists = new ArrayList<>();

     CvDto cvDto =  new CvDto();

        List<CvDto> reportList= new ArrayList<>();



    try {
    StringBuilder sqlQuerys = new StringBuilder();


    sqlQuerys.append("SELECT HRS_EMPLOYEE_IMAGE.EMP_IMAGE,P.EMP_BLOOD_GROUP, N.EMP_NOMINEE_NAME,P.EMP_ECONTRACT,E.PF_A_C_NO,E.EMP_PF_DATE, E.EMP_CARD_NO, E.EMP_NAME, E.DIVISION_NAME, D.DEPARTMENT_NAME, P.EMP_FATHARS_NAME, ");
     sqlQuerys.append("P.EMP_MOTHER_NAME, DG.DESIGNATION_NAME, E.EMP_JOIN_DATE, E.BANK_ACCOUNT_STRING,  ");
     sqlQuerys.append("P.EMP_PRE_ADD1 + '-' + P.EMP_PRE_ADD2 AS PreAdd11, P.EMP_PRE_CITY + '-' + P.EMP_PRE_ZIP AS PreAdd12, P.EMP_PER_ADD1 + '-' + P.EMP_PER_ADD2 AS PerAdd21,  ");
     sqlQuerys.append("P.EMP_PER_CITY + '-' + P.EMP_PER_ZIP AS PerZipCity, P.EMP_DATE_OF_BIRTH, P.EMP_AGE, P.EMP_SEX,  ");
     sqlQuerys.append("P.EMP_MARITAL_STATUS, P.EMP_WEIGHT, P.EMP_HEIGHT, P.EMP_RELIGION, E.EMP_NATIONAL_ID,   ");
     sqlQuerys.append("E.EMP_NATIONALITY, E.EMP_PRE_PHONE, P.EMP_EMAIL, N.EMP_NOMINEE1_NAME, P.EMP_PRE_ADD1,  ");
     sqlQuerys.append("P.EMP_PER_ADD1, P.EMP_MARRIAGE_DATE, P.EMP_SPOUSE_NAME, P.EMP_ECONTRACT_MOBILE,   ");
     sqlQuerys.append("E.EMP_COR_PHONE, E.EMP_PF_STATUS, E.PF_A_C_NO  ");
     sqlQuerys.append("FROM  HRS_EMPLOYEE AS E INNER JOIN  ");
     sqlQuerys.append("HRS_DEPARTMENT AS D ON E.DEPARTMENT_CODE = D.DEPARTMENT_CODE INNER JOIN  ");
     sqlQuerys.append("HRS_DESIGNATION AS DG ON E.DESIGNATION_CODE = DG.DESIGNATION_CODE INNER JOIN  ");
     sqlQuerys.append("HRS_EMPLOYEE_IMAGE ON E.EMP_CARD_NO = HRS_EMPLOYEE_IMAGE.EMP_CARD_NO LEFT OUTER JOIN  ");
     sqlQuerys.append("HRS_EMP_NOMINEE AS N ON E.EMP_CARD_NO = N.EMP_CARD_NO LEFT OUTER JOIN  ");
     sqlQuerys.append("HRS_EMP_PERSONAL_INFO AS P ON E.EMP_CARD_NO = P.EMP_CARD_NO  ");
     sqlQuerys.append("WHERE (E.EMP_CARD_NO = '" + model.getEmpCardNo() + "') ");
     sqlQuerys.append("ORDER BY E.EMP_CARD_NO, D.DEPARTMENT_NAME, DG.DESIGNATION_NAME  ");

    con = getOraConnection();
    stm = con.createStatement();
    rsp= stm.executeQuery(sqlQuerys.toString());

        while (rsp.next())
        {
            CvDto oEmpinfo = new CvDto();
            oEmpinfo.setStrCardNo(rsp.getString("EMP_CARD_NO")) ;
            oEmpinfo.setStrEmpName(rsp.getString("EMP_NAME"));
            if (rsp.getString("EMP_BLOOD_GROUP") != "")
            {
                oEmpinfo.setStrBloodGroup(rsp.getString("EMP_BLOOD_GROUP"));
            }
            if (rsp.getString("EMP_NOMINEE_NAME") != null)
            {
                oEmpinfo.setStrNominee(rsp.getString("EMP_NOMINEE_NAME"));
            }
            if (rsp.getString("EMP_ECONTRACT")!= null)
            {
                oEmpinfo.setStrEmargencyContatctName(rsp.getString("EMP_ECONTRACT")) ;
            }
            if (rsp.getString("PF_A_C_NO")!= null)
            {
                oEmpinfo.setStrPFAcNO(rsp.getString("PF_A_C_NO")) ;
            }
            if (rsp.getString("EMP_PF_DATE") != null)
            {
                oEmpinfo.setStrPFDate(formatter.format(rsp.getString("EMP_PF_DATE")));
            }

            if (rsp.getString("DIVISION_NAME")  != null)
            {
                oEmpinfo.setStrDivision(rsp.getString("DIVISION_NAME")) ;
            }
            if (rsp.getString("DEPARTMENT_NAME")!= null)
            {
                oEmpinfo.setStrDepartment(rsp.getString("DEPARTMENT_NAME"));
            }
            if (rsp.getString("DESIGNATION_NAME")!= null)
            {
                oEmpinfo.setStrDesignation(rsp.getString("DESIGNATION_NAME")) ;
            }

            if (rsp.getString("EMP_JOIN_DATE") != null)
            {
                Date date = inputFormat.parse(rsp.getString("EMP_JOIN_DATE"));

                oEmpinfo.setStrJoningDate(formatter.format(date));

                oEmpinfo.setStrJobduretion(getDifferenceDays(date));
            }
            if (rsp.getString("EMP_DATE_OF_BIRTH")!= null)
            {
                Date date = inputFormat.parse(rsp.getString("EMP_DATE_OF_BIRTH"));
                oEmpinfo.setStrDate(formatter.format(date));
                oEmpinfo.setStrAge(calculateAge(date));
            }
            if (rsp.getString("PerAdd21")!=null)
            {
                oEmpinfo.setStrAddress1(rsp.getString("PerAdd21")) ;
            }
            if (rsp.getString("PreAdd11")!= null)
            {
                oEmpinfo.setStrAddress2(rsp.getString("PreAdd11")) ;
            }
            if (rsp.getString("PreAdd12") != null)
            {
                oEmpinfo.setStrPresentAdd1(rsp.getString("PreAdd12"));
            }
            if (rsp.getString("PerZipCity")!= null)
            {
                oEmpinfo.setStrPresentAdd2(rsp.getString("PerZipCity"));
            }
            if (rsp.getString("EMP_AGE")  != null)
            {
                oEmpinfo.setStrEMPAge(rsp.getString("EMP_AGE")) ;
            }
            if (rsp.getString("EMP_SEX")!= null)
            {
                if (rsp.getString("EMP_SEX").equals("M"))
                {
                    oEmpinfo.setStrEmpSex("Male");
                }
                else if (rsp.getString("EMP_SEX").equals("F"))
                {
                    oEmpinfo.setStrEmpSex("Female");
                }
                else
                {
                    oEmpinfo.setStrEmpSex("") ;
                }
            }
            if (rsp.getString("EMP_MARITAL_STATUS")!= null)
            {
                oEmpinfo.setIntStatus(rsp.getInt("EMP_MARITAL_STATUS"));
            }
            if (rsp.getString("EMP_HEIGHT")!= null)
            {
                oEmpinfo.setStrHeight(rsp.getString("EMP_HEIGHT")) ;
            }
            if (rsp.getString("EMP_WEIGHT") != null)
            {
                oEmpinfo.setStrWeight(rsp.getString("EMP_WEIGHT")) ;
            }
            if (rsp.getString("EMP_RELIGION")!= null)
            {
                oEmpinfo.setStrReligion(rsp.getString("EMP_RELIGION"));
            }
            if (rsp.getString("EMP_NATIONAL_ID") != null)
            {
                oEmpinfo.setStrNationalCardNo(rsp.getString("EMP_NATIONAL_ID")) ;
            }
            if (rsp.getString("EMP_NATIONALITY") != null)
            {
                oEmpinfo.setStrNationality(rsp.getString("EMP_NATIONALITY")) ;
            }
            if (rsp.getString("EMP_PRE_PHONE")!= null)
            {
                oEmpinfo.setStrPhoneNumber(rsp.getString("EMP_PRE_PHONE")) ;
            }
            if (rsp.getString("EMP_EMAIL")!= "")
            {
                oEmpinfo.setStrEmail(rsp.getString("EMP_EMAIL")) ;
            }

            if (rsp.getString("EMP_MOTHER_NAME") != "")
            {
                oEmpinfo.setStrMothersName(rsp.getString("EMP_MOTHER_NAME"));
            }
            if (rsp.getString("EMP_FATHARS_NAME")!= "")
            {
                oEmpinfo.setStrFathersName(rsp.getString("EMP_FATHARS_NAME")) ;
            }
            if (rsp.getString("EMP_NOMINEE1_NAME")!= "")
            {
                oEmpinfo.setStrNominee(rsp.getString("EMP_NOMINEE1_NAME"));
            }
            if (rsp.getString("EMP_MARRIAGE_DATE") != "")
            {
                Date date = inputFormat.parse(rsp.getString("EMP_MARRIAGE_DATE"));
                oEmpinfo.setStrMarriageDate(formatter.format(date));
            }
            if (rsp.getString("EMP_SPOUSE_NAME") != "")
            {
                oEmpinfo.setStrSpouseName(rsp.getString("EMP_SPOUSE_NAME")) ;
            }
            if (rsp.getString("EMP_ECONTRACT_MOBILE")!= "")
            {
                oEmpinfo.setStrEmargencyContatctNumuber(rsp.getString("EMP_ECONTRACT_MOBILE"));
            }
            if (rsp.getString("EMP_COR_PHONE")!= "")
            {
                oEmpinfo.setStrCorporatePhoneNumber(rsp.getString("EMP_COR_PHONE")) ;
            }
            if (rsp.getInt("EMP_PF_STATUS")!= 0)
            {
                int intstaus = rsp.getInt("EMP_PF_STATUS");
                if (intstaus == 1)
                {
                    oEmpinfo.setStrPFStatus("Yes") ;
                }
                else
                {
                    oEmpinfo.setStrPFStatus("No") ;
                }

            }
            if (rsp.getString("PF_A_C_NO") != null)
            {
                oEmpinfo.setStrPFAcNO(rsp.getString("PF_A_C_NO"));
            }

            if (rsp.getString("BANK_ACCOUNT_STRING")!= null)
            {
                oEmpinfo.setStrSalaryOnBankAcNO(rsp.getString("BANK_ACCOUNT_STRING")) ;
            }


            //if (dr["EMP_IMAGE"].ToByteArray()=="")
//            oEmpinfo.setStrIamge(rsp.getBytes("EMP_IMAGE"));
//            long bufLength = dr.GetBytes(0, 0, null, 0, 0);
//            oEmpinfo.setStrIamge(rsp.getBytes("EMP_IMAGE")) = new byte[bufLength];
//            dr.GetBytes(0, 0, oEmpinfo.strIamge, 0, (int)bufLength);
//            ooBranch.add(oEmpinfo);
            cvDto= oEmpinfo;
        }
        if (rsp.hashCode() <0)
        {
            CvDto oEmpinfo = new CvDto();
            oEmpinfo.setStrCardNo("");
            oEmpinfo.setStrEmpName("");
            oEmpinfo.setStrDivision("");
            oEmpinfo.setStrDepartment("");
            oEmpinfo.setStrDesignation("");
            oEmpinfo.setStrJoningDate("");
            oEmpinfo.setStrDate("");
            oEmpinfo.setStrAddress1("");
            oEmpinfo.setStrAddress2("");
            oEmpinfo.setStrPresentAdd1("");
            oEmpinfo.setStrPresentAdd2("");
            oEmpinfo.setStrEMPAge("");
            oEmpinfo.setStrEmpSex("");
            oEmpinfo.setIntStatus(0);
            oEmpinfo.setStrHeight("");
            oEmpinfo.setStrWeight("");
            oEmpinfo.setStrEmpSex("");
            cvDto= oEmpinfo;
        }

        }catch (Exception e){

        }


        try {

            StringBuilder sqlQuerys1 = new StringBuilder();

            List<RoEmployeeInformation> educationList =new ArrayList<>();

            sqlQuerys1.append("SELECT EMP_CARD_NO, E.QUALIFICATION_NAME, INSTITUTE, ACADEMIC_YEAR, SUBJECT, MARKS, ");
            sqlQuerys1.append("BOARD_NAME,POSITION FROM HRS_EDUCATIONAL_QUALIFICATION E,  ");
            sqlQuerys1.append("HRS_QUALIFICATION Q WHERE E.QUALIFICATION_NAME= Q.QUALIFICATION_NAME  ");
            sqlQuerys1.append("and (EMP_CARD_NO = '" + model.getEmpCardNo() + "')  ");
            sqlQuerys1.append("ORDER BY POSITION asc ");


            con = getOraConnection();
            stm = con.createStatement();
            rsp= stm.executeQuery(sqlQuerys1.toString());



                while (rsp.next())
                {
                    RoEmployeeInformation oEmpinfo = new RoEmployeeInformation();



                    if (rsp.getString("QUALIFICATION_NAME")!= "")
                    {
                        oEmpinfo.setStrQualification(rsp.getString("QUALIFICATION_NAME"));
                    }
                    if (rsp.getString("INSTITUTE")!= "")
                    {
                        oEmpinfo.setStrInstitute(rsp.getString("INSTITUTE")) ;
                    }
                    if (rsp.getString("ACADEMIC_YEAR")!= "")
                    {
                        oEmpinfo.setIntADL(rsp.getInt("ACADEMIC_YEAR"));
                    }
                    if (rsp.getString("SUBJECT")!= "")
                    {
                        oEmpinfo.setStrSubject(rsp.getString("SUBJECT"));
                    }
                    if (rsp.getString("MARKS") != "")
                    {
                        oEmpinfo.setDblMarks(rsp.getString("MARKS"));
                    }
                    if (rsp.getString("BOARD_NAME")!= "")
                    {
                        oEmpinfo.setStrBoardName(rsp.getString("BOARD_NAME"));
                    }
                    educationList.add(oEmpinfo);
                }


                if (rsp.hashCode() <0)
                {
                    RoEmployeeInformation oEmpinfo = new RoEmployeeInformation();
                    oEmpinfo.setStrQualificaton("");
                    oEmpinfo.setStrInstitute("");
                    oEmpinfo.setStrSubject("");
                    oEmpinfo.setIntAcademicYear(0);
                    oEmpinfo.setStrBoardName("");
                    oEmpinfo.setDblMarks("");
                }
            cvDto.itemList1= educationList;
        }catch (Exception e){

        }


        try {
            StringBuilder sqlQuerys2 = new StringBuilder();

            List<RoEmployeeInformation> experienceList =new ArrayList<>();

            sqlQuerys2.append("SELECT   COMPANY_NAME, DURATION, RESIGNATION_DATE, EXPERIENCE, RECENT_EMPLOYEE, SPECIAL_QUALIFICATION, DESIGNATION ");
            sqlQuerys2.append("FROM  HRS_EXPERIENCE ");
            sqlQuerys2.append("WHERE (EMP_CARD_NO = '" + model.getEmpCardNo() + "') ");

            con = getOraConnection();
            stm = con.createStatement();
            rsp= stm.executeQuery(sqlQuerys2.toString());

                while (rsp.next())
                {
                    RoEmployeeInformation oEmpinfo = new RoEmployeeInformation();


                    if (rsp.getString("COMPANY_NAME")!= "")
                    {
                        oEmpinfo.setStrRecentEmployee(rsp.getString("COMPANY_NAME"));
                    }
                    if (rsp.getString("DURATION")!= "")
                    {
                        oEmpinfo.setStrExperience(rsp.getString("DURATION")) ;
                    }
                    if (rsp.getString("RESIGNATION_DATE")!= "")
                    {
                        Date date = inputFormat.parse(rsp.getString("RESIGNATION_DATE"));
                        oEmpinfo.setStrDate(formatter.format(date));
                    }
                    if (rsp.getString("DESIGNATION")!= "")
                    {
                        oEmpinfo.setStrDesignation(rsp.getString("DESIGNATION"));
                    }
                    experienceList.add(oEmpinfo);
                }
                if (rsp.hashCode() <0)
                {
                    RoEmployeeInformation oEmpinfo = new RoEmployeeInformation();
                    oEmpinfo.setStrDesignation("");
                    oEmpinfo.setStrRecentEmployee("");
                    oEmpinfo.setDblExperience("");
                    oEmpinfo.setStrDate("");
                }
            cvDto.itemList2= experienceList;
        }catch (Exception e){

        }



















        reportList.add(cvDto);







        CusJasperReportDef report = new CusJasperReportDef();
        report.setOutputFilename("CV");
        report.setReportName("cvReport");
        report.setReportDir(CommonFunction.getResoucePath("/report/cv") + "/");
        report.setReportFormat(CommonFunction.printFormat("PDF"));
        report.setReportData(reportList);

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

        return report;

    }





}

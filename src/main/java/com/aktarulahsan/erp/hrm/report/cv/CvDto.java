package com.aktarulahsan.erp.hrm.report.cv;


import com.aktarulahsan.erp.hrm.model.RoAttendance;
import com.aktarulahsan.erp.hrm.model.RoEmployeeInformation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CvDto {

    String strEmpID;
    String strDesignation;
    String strCardNo;
    String strEmargencyContatctName;
    String strPFAcNO;
    String strFirstName;
    String strPhoneNumber2;
    String strTerritoryName;
    String strTerritoryCode;
    String strZone;
    String strArea;
    String strPFDate;
    String strPabx;
    String strEmpName;
    String strBloodGroup;
    int intStatus;
    int intMale;
    int intFemale;
    String strDepartment;
    String strDivision;
    String strJoningDate;
    String strDate;
    Double dblSalary;
    String strStatus;
    String strShiftname;
    String strShiftSterDate;
    String strPhoneNumber;
    String strCorporatePhoneNumber;
    String strReligion;
    byte[] strIamge;
    String strAddress1;
    String strEmail;
    String strAddress2;
    String strPresentAdd1;
    String strPresentAdd2;
    String strEMPAge;
    String strWeight;
    String strHeight;
    String strNationality;
    String strNationalCardNo;
    String strQualificaton;
    String strInstitute;
    String strAcademic;
    String strSubject;
    String dblMarks;
    String dblExperience;
    String strExperience;
    String strRecentEmployee;
    String strQualification;
    String strEmpSex;
    String strJobduretion;
    String strAge;
    String strBoardName;
    String strMarriageDate;
    String strSpouseName;
    String strEmargencyContatctNumuber;
    String strFathersName;
    String strMothersName;
    String strNominee;
    String strPFStatus;
    String strSalaryOnBankAcNO;


    List<RoEmployeeInformation> itemList1;
    List<RoEmployeeInformation> itemList2;
    List<RoEmployeeInformation> itemList3;




}

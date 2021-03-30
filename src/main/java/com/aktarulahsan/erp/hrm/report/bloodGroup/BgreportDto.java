package com.aktarulahsan.erp.hrm.report.bloodGroup;

import com.aktarulahsan.erp.hrm.model.RoAttendance;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class BgreportDto {
    String empCardNo;
    Date from_date;
    Date to_date;
    String companyName;
    String companyAddress;
    String emp_b_name;
    String emp_name;
    String emp_type_name;
    String division_name;
    String department_name;
    List<BloodGroupReportDTO> itemList;

}

package com.aktarulahsan.erp.hrm.report.barcode;

import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import com.aktarulahsan.erp.hrm.report.dtos.LeaveSummary;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Setter
@Getter
public class BarCodeModel {
    String companyName;
    String companyAddress;
    String emp_b_name;
    String emp_name;
    String emp_type_name;
    String division_name;
    String empCardNo;
    String lable;

}

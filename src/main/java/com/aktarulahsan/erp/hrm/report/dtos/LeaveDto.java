package com.aktarulahsan.erp.hrm.report.dtos;

import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Setter
@Getter
public class LeaveDto {
    String companyName;
    String companyAddress;
    Double pay_leave_serial;
    String emp_leave_key;
    String leave_id;
    Date from_date;
    Date to_date;
    int no_of_days;
    String comments;
    String department_name;


    String emp_b_name;
    String emp_name;
    String emp_type_name;
    String division_name;


    String empCardNo;
    List<LeaveModel> itemList;
    List<LeaveSummary> leaveSummaryList;
}

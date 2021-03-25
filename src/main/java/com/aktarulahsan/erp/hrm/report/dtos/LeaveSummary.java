package com.aktarulahsan.erp.hrm.report.dtos;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LeaveSummary {

    String empCardNo;
    String leave_id;
    String usedLeave;
    String balance;
    String leave_opening;

}

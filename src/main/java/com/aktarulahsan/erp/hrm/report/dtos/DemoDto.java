package com.aktarulahsan.erp.hrm.report.dtos;

import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DemoDto {

    String personalNumber;
    String patientname;
    String rank;
    String age;
    String unit;
    String gShear;
    String rAndD;
    String sShear;
    String splShear;
    String cosFund;
    Double totalTk;
    String remarks;
    String itemName;
    Double itemRate;
    String itemId;

    List<DemoDto> itemList;

    String companyName;
    String companyAddress;
    
}

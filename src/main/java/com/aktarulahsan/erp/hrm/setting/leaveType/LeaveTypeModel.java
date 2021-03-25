package com.aktarulahsan.erp.hrm.setting.leaveType;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "HRS_LEAVE_CONFIG")
public class LeaveTypeModel {


//    LEAVE_SERIAL, LEAVE_ID, LEAVE_NAME, NO_OF_DAYS, ALLOW_DEDUCTION_YN, DEDUCTION_ON, REF_HEAD, LEAVE_NATURE, DEFAULT_STATUS



    @Id
    @Column(nullable = false, name = "LEAVE_ID")
    String  leaveId;

    @Column(  name = "LEAVE_SERIAL")
    int leaveSerial;

    @Column(  name = "LEAVE_NAME")
    String leaveName;

    @Column(  name = "NO_OF_DAYS")
    int noOfDays;

    @Column(  name = "ALLOW_DEDUCTION_YN")
    int allowDeductionYn;

    @Column(  name = "REF_HEAD")
    int refHead;

    @Column(  name = "LEAVE_NATURE")
    String leaveNature;

    @Column(  name = "DEFAULT_STATUS")
    int defaultStatus;







}

package com.aktarulahsan.erp.hrm.tour;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "HRS_EMP_LEAVE_TOUR")
public class TourDetailsModel {


//    PAY_LEAVE_SERIAL, EMP_LEAVE_KEY, LEAVE_DATE, FROM_LEAVE, TO_LEAVE, WORK_WITH, REMARKS

    @Id

    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(  name = "PAY_LEAVE_SERIAL")
    int pay_leave_serial;

    @Column(  name = "EMP_LEAVE_KEY")
    String emp_leave_key;

    @Column(  name = "LEAVE_DATE")
    Date leave_date;

    @Column(  name = "FROM_LEAVE")
    String from_leave;

    @Column(  name = "TO_LEAVE")
    String to_leave;

    @Column(  name = "WORK_WITH")
    String work_with;

    @Column(  name = "REMARKS")
    String remarks;






}

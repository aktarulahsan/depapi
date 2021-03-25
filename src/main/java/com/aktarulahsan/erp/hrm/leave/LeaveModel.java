package com.aktarulahsan.erp.hrm.leave;

import com.aktarulahsan.erp.hrm.tour.TourDetailsModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "HRS_EMP_LEAVE")
public class LeaveModel implements Serializable {
//    PAY_LEAVE_SERIAL, EMP_LEAVE_KEY, EMP_CARD_NO, LEAVE_ID, FRIDAY, FROM_DATE, TO_DATE, NO_OF_DAYS, FIRST_DATE_MLEAVE, SECOND_DATE_MLEAVE, APPROVED_STATUS, COMMENTS,
//    RES_PEREMP_CARD_NO, FAL_HR_APP, DESTINATION, USER_LOGIN_NAME, INSERT_DATE, UPDATE_DATE


    @Column(  name = "EMP_CARD_NO")
    String empCardNo;




    @Column(  name = "PAY_LEAVE_SERIAL")
    Double pay_leave_serial;

    @Id
    @Column(  name = "EMP_LEAVE_KEY")
    String emp_leave_key;



    @Column(  name = "LEAVE_ID")
    String leave_id;

    @Column(  name = "FRIDAY")
    Double friday;

    @Column(  name = "FROM_DATE")
    Date from_date;

    @Column(  name = "TO_DATE")
    Date to_date;

    @Column(  name = "NO_OF_DAYS")
    int no_of_days;

    @Column(  name = "FIRST_DATE_MLEAVE")
    Date first_date_mleave;

    @Column(  name = "SECOND_DATE_MLEAVE")
    Date second_date_mleave;

    @Column(  name = "APPROVED_STATUS")
    int approved_status;

    @Column(  name = "COMMENTS")
    String comments;


    @Column(  name = "RES_PEREMP_CARD_NO")
    String  res_peremp_card_no;

    @Column(  name = "FAL_HR_APP")
    int fal_hr_app;

    @Column(  name = "DESTINATION")
    String destination;

    @Column(  name = "USER_LOGIN_NAME")
    String user_login_name;

    @Column(  name = "INSERT_DATE")
    Date insert_date;

    @Column(  name = "UPDATE_DATE")
    Date update_date;

    @Transient
    List<TourDetailsModel> tourList;


}

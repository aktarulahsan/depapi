package com.aktarulahsan.erp.hrm.hrsEmployee;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "HRS_EMPLOYEE")
public class HrmEmployeeModel {


    @Id
    @Column(  name = "EMP_CARD_NO")
    String empCardNo;

    @Column(  name = "OLD_CARD_NO")
    String old_card_no;



    @Column(  name = "EMP_TYPE_NAME")
    String emp_type_name;

    @Column(  name = "EMP_NAME")
    String emp_name;

    @Column(  name = "EMP_B_NAME")
    String emp_b_name;

    @Column(  name = "EMP_NATIONAL_ID")
    String emp_national_id;



    @Column(  name = "EP_LEAVE_PASSWORD")
    String ep_leave_password;



    @Column(  name = "DIVISION_NAME")
    String division_name;

    @Column(  name = "DEPARTMENT_CODE")
    String department_code;

    @Column(  name = "DESIGNATION_CODE")
    String designation_code;

    @Column(  name = "SECTION_CODE")
    String section_code;


    @Column(  name = "RESIDENTIAL")
    int residential;

    @Column(  name = "GRADE_NAME")
    String grade_name;

    @Column(  name = "EMP_STATUS")
    int emp_status;

    @Column(  name = "EMPLOYEE_SHIFT_NAME")
    String employee_shift_name;

    @Column(  name = "EMP_SHIFT_EFF_DATE")
    Date emp_shift_eff_date;

    @Column(  name = "EMP_JOIN_DATE")
    Date emp_join_date;

    @Column(  name = "EMP_CONFIRMATION_DATE")
    Date emp_confirmation_date;

    @Column(  name = "EMP_OT_STATUS")
    int emp_ot_status;

    @Column(  name = "EMP_PF_STATUS")
    int emp_pf_status;

    @Column(  name = "EMP_PF_DATE")
    Date emp_pf_date;

    @Column(  name = "PF_PERCENTAGES")
    String pf_percentages;


    @Column(  name = "PF_A_C_NO")
    String pf_a_c_no;

    @Column(  name = "EMP_COR_PHONE")
    String emp_cor_phone;

    @Column(  name = "DEPARTMENTAL_HEAD_NAME")
    String departmental_head_name;

    @Column(  name = "EL_CL_OPENING")
    int el_cl_opening;

    @Column(  name = "EL_ML_OPENING")
    String el_ml_opening;

    @Column(  name = "APPLICATiON_DATE")
    Date application_date;

    @Column(  name = "ACCEPATANCE_DATE")
    Date accepatance_date;

    @Column(  name = "_HR_SALARY")
    Double _hr_salary;

    @Column(  name = "EL_LEAVE_START_DATE")
    String el_leave_start_date;

    @Column(  name = "EMP_PHYSICAL_FITNESS")
    String emp_physical_fitness;


    @Column(  name = "EMP_JOINING_SALARY")
    Double emp_joining_salary;

    @Column(  name = "EMP_PRESENT_SALARY")
    Double emp_present_salary;

    @Column(  name = "EMP_MEDICAL")
    Double emp_medical;

    @Column(  name = "EMP_BASIC")
    Double emp_basic;

    @Column(  name = "EMP_HOUSE_RENT")
    Double emp_house_rent;

    @Column(  name = "EMP_MOBILE_BILL")
    Double emp_mobile_bill;

    @Column(  name = "EMP_HAZIRA_BONUS")
    Double emp_hazira_bonus;

    @Column(  name = "EMP_TIBIN_BILL")
    Double emp_tibin_bill;

    @Column(  name = "EMP_CONVEYANCE")
    Double emp_conveyance;

    @Column(  name = "EMP_OTHERS_ALLOWANCE")
    Double emp_others_allowance;


    @Column(  name = "EMP_NIGHT_ALLOWANCE")
    Double emp_night_allowance;

    @Column(  name = "STAMP")
    Double stamp;

    @Column(  name = "EMP_NATIONALITY")
    String emp_nationality;

    @Column(  name = "EMP_PRE_PHONE")
    String emp_pre_phone;

    @Column(  name = "EMP_REF1_NAME")
    String emp_ref1_name;

    @Column(  name = "EMP_REF1_ADDRESS")
    String emp_ref1_address;

    @Column(  name = "EMP_REF2_NAME")
    String emp_ref2_name;

    @Column(  name = "EMP_REF2_ADDRESS")
    String emp_ref2_address;

    @Column(  name = "EMER_CONT_NAME")
    String emer_cont_name;

    @Column(  name = "EMER_CONT_REL")
    String emer_cont_rel;


    @Column(  name = "EMER_CONT_ADDRESS")
    String emer_cont_address;

    @Column(  name = "EMER_CONT_TEL")
    String emer_cont_tel;

    @Column(  name = "PROCESS_STATUS")
    int process_status;

    @Column(  name = "BANK_CASH_ACCOUNT")
    int bank_cash_account;

    @Column(  name = "COMP_LOGO_NO")
    int comp_logo_no;

    @Column(  name = "BANK_ACCOUNT_STRING")
    String bank_account_string;

    @Column(  name = "PABX_NO")
    String pabx_no;

    @Column(  name = "EMP_INCREEMENT")
    Double emp_increement;

    @Column(  name = "FIRST_NAME")
    String first_name;

    @Column(  name = "PUNCH_STATUS")
    int punch_status;

    @Column(  name = "GR_NAME")
    String gr_name;

    @Column(  name = "TC")
    String tc;

    @Column(  name = "TC_NAME")
    String tc_name;


    @Column(  name = "FOOD_BILL")
    Double food_bill;



}

package com.aktarulahsan.erp.hrm.department;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "HRS_DEPARTMENT")
public class DepartmentModel {

//    DEPARTMENT_CODE, DEPARTMENT_NAME, DEPARTMENT_NAME_BANGLA, DIVISION_NAME, DEP_STATUS, DEP_POS,
//    DEP_SHORT_NAME, ALT_STATUS, ADL_STATUS, DEP_MERZE



    @Id

    @Column(  name = "DEPARTMENT_CODE")
    String department_code;

    @Column(  name = "DEPARTMENT_NAME")
    String department_name;
    @Column(  name = "DEPARTMENT_NAME_BANGLA")
    String department_name_bangla;
    @Column(  name = "DIVISION_NAME")
    String division_name;
    @Column(  name = "DEP_STATUS")
    int dep_status;
    @Column(  name = "DEP_POS")
    int dep_pos;
    @Column(  name = "DEP_SHORT_NAME")
    String dep_short_name;

    @Column(  name = "ALT_STATUS")
    int alt_status;

    @Column(  name = "ADL_STATUS")
    int adl_status;

    @Column(  name = "DEP_MERZE")
    int dep_merze;






}

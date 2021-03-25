package com.aktarulahsan.erp.hrm.designation;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "HRS_DESIGNATION")
public class DesignationModel {
//    DESIGNATION_SERIAL, DIVISION_NAME,
//    DEPARTMENT_CODE, DESIGNATION_CODE,
//    DESIGNATION_NAME, DESIGNATION_NAME_BANGLA,
//    DESIGNATION_STATUS, DES_POS, DES_SHORT_NAME



    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(  name = "DESIGNATION_SERIAL")
    int designation_serial;

    @Column(  name = "DIVISION_NAME")
    String division_name;

    @Column(  name = "DEPARTMENT_CODE")
    String department_code;
    @Column(  name = "DESIGNATION_CODE")
    String designation_code;

    @Column(  name = "DESIGNATION_NAME")
    String designation_name;

    @Column(  name = "DESIGNATION_NAME_BANGLA")
    String designation_name_bangla;

    @Column(  name = "DESIGNATION_STATUS")
    String designation_status;

    @Column(  name = "DES_POS")
    String des_pos;

    @Column(  name = "DES_SHORT_NAME")
    String des_short_name;

}

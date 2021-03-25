package com.aktarulahsan.erp.tms.setting.group;


import lombok.Data;
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
@Table(name = "INV_GROUP_MASTER")
public class GroupModel {

    @Id
    @Column(nullable = false, name = "GR_NAME_SERIAL")
    int id;

    @Column( name = "GR_NAME")
    String gname;

    @Column(name = "INSERT_DATE")
    Date insertDate;

    @Column( name = "UPDATE_DATE")
    Date updateDate;





}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aktarulahsan.erp.hrm.report;


import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mohammad Galib
 *
 */
@Service
public class ReportService {

    @Autowired
    ReportRepository reportRepository;
    
    public CusJasperReportDef demoReport(String invoiceno) {

        return reportRepository.demoReport(invoiceno);
    }
    
}

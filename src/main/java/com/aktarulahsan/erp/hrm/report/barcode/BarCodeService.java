package com.aktarulahsan.erp.hrm.report.barcode;

import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.report.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarCodeService {

    @Autowired
    BarCodeRepository reportRepository;

    public CusJasperReportDef demoReport(String invoiceno) {

        return reportRepository.labelBarcode(invoiceno);
    }
}

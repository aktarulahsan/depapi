package com.aktarulahsan.erp.hrm.report.tour;

import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourReportService {

    @Autowired
    TourReportRepository reportRepository;

    public CusJasperReportDef tourReport(String invoiceno) {

        return reportRepository.tourReport(invoiceno);
    }
}

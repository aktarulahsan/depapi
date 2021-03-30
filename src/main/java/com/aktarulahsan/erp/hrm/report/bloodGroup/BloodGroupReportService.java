package com.aktarulahsan.erp.hrm.report.bloodGroup;

import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.report.tour.TourReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BloodGroupReportService {


    @Autowired
    BloodGroupReportRepository reportRepository;

    public CusJasperReportDef bGroupReport(String empCardNo) {

        return reportRepository.bloodGReports(empCardNo);
    }
}

package com.aktarulahsan.erp.hrm.report.cv;


import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.report.pabx.PabxReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CvReportService {


    @Autowired
    CvReportRepository reportRepository;

    public CusJasperReportDef cvReports(String empCardNo) {

        return reportRepository.cvReports(empCardNo);
    }
}

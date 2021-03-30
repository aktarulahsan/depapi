package com.aktarulahsan.erp.hrm.report.pabx;

import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.report.bloodGroup.BloodGroupReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PabxReportService {


    @Autowired
    PabxReportRepository reportRepository;

    public CusJasperReportDef pabxports(String empCardNo) {

        return reportRepository.pabxports(empCardNo);
    }
}

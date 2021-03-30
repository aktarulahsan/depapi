package com.aktarulahsan.erp.hrm.report.cv;

import com.aktarulahsan.erp.core.reportConfig.ReportBaseController;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.report.pabx.PabxReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report")
public class CvReportController extends ReportBaseController {

    @Autowired
    CvReportService reportService;

    @PostMapping(value = "/cvReports")
    public ResponseEntity<byte[]> test(@RequestBody String reqObj) throws IOException {
        CusJasperReportDef report = reportService.cvReports(reqObj);
        return respondReportOutputWithoutHeader(report, false);
    }
}

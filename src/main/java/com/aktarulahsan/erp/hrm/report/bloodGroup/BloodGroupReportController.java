package com.aktarulahsan.erp.hrm.report.bloodGroup;

import com.aktarulahsan.erp.core.reportConfig.ReportBaseController;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report")
public class BloodGroupReportController extends ReportBaseController {

    @Autowired
    BloodGroupReportService reportService;

    @PostMapping(value = "/bGroupReport")
    public ResponseEntity<byte[]> test(@RequestBody String reqObj) throws IOException {
        CusJasperReportDef report = reportService.bGroupReport(reqObj);
        return respondReportOutputWithoutHeader(report, false);
    }

}

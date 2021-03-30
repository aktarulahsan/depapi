package com.aktarulahsan.erp.hrm.report.pabx;

import com.aktarulahsan.erp.core.reportConfig.ReportBaseController;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.report.bloodGroup.BloodGroupReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report")
public class PabxReportController extends ReportBaseController {


    @Autowired
    PabxReportService reportService;

    @PostMapping(value = "/pabxReport")
    public ResponseEntity<byte[]> test(@RequestBody String reqObj) throws IOException {
        CusJasperReportDef report = reportService.pabxports(reqObj);
        return respondReportOutputWithoutHeader(report, false);
    }

}

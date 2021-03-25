package com.aktarulahsan.erp.hrm.report;


import com.aktarulahsan.erp.core.reportConfig.ReportBaseController;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Mohammad Galib
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report")
public class ReportController extends ReportBaseController {

    @Autowired
   ReportService reportService;

//    @PostMapping(value = "/leaveReportss")
//    public ResponseEntity<byte[]> shareDistributionReport(@RequestParam Long invoiceNo) throws IOException {
//        CusJasperReportDef report = reportService.demoReport(invoiceNo);
//        return respondReportOutput(report, false);
//
//    }

    @PostMapping(value = "/leaveReport")
    public ResponseEntity<byte[]> test(@RequestBody String reqObj) throws IOException {
        CusJasperReportDef report = reportService.demoReport(reqObj);
        return respondReportOutputWithoutHeader(report, false);
    }
 
	
}














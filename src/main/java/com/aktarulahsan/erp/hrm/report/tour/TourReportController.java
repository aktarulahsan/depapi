package com.aktarulahsan.erp.hrm.report.tour;


import com.aktarulahsan.erp.core.reportConfig.ReportBaseController;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report")
public class TourReportController extends ReportBaseController {


    @Autowired
    TourReportService reportService;

//    @PostMapping(value = "/leaveReportss")
//    public ResponseEntity<byte[]> shareDistributionReport(@RequestParam Long invoiceNo) throws IOException {
//        CusJasperReportDef report = reportService.demoReport(invoiceNo);
//        return respondReportOutput(report, false);
//
//    }

    @PostMapping(value = "/punchReport")
    public ResponseEntity<byte[]> test(@RequestBody String reqObj) throws IOException {
        CusJasperReportDef report = reportService.tourReport(reqObj);
        return respondReportOutputWithoutHeader(report, false);
    }


}

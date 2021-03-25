package com.aktarulahsan.erp.hrm.report.barcode;

import com.aktarulahsan.erp.core.reportConfig.ReportBaseController;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report")
public class BarcodeController  extends ReportBaseController {

    @Autowired
    BarCodeService reportService;


    @PostMapping(value = "/barcodeReport")
    public ResponseEntity<byte[]> test(@RequestBody String reqObj) throws IOException {
        CusJasperReportDef report = reportService.demoReport(reqObj);
        return respondReportOutputWithoutHeader(report, false);
    }

}

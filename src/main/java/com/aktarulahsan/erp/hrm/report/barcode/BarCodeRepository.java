package com.aktarulahsan.erp.hrm.report.barcode;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CoreJasperService;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeRepository;
import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import com.aktarulahsan.erp.hrm.report.dtos.LeaveDto;
import com.aktarulahsan.erp.util.CommonFunction;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


@Repository
@Transactional
public class BarCodeRepository extends BaseRepository {

    @Autowired
    CoreJasperService coreJasperService;

    @Autowired
    HrmEmployeeRepository employeeRepository;


    public CusJasperReportDef barCodeReports(String reqObj) {

        LeaveModel umodel = objectMapperReadValue(reqObj, LeaveModel.class);
        BarCodeModel model = new BarCodeModel();
        List<BarCodeModel> modelLists = new ArrayList<>();
        List<LeaveModel> modelList = new ArrayList<>();


        HrmEmployeeModel empModel = employeeRepository.findById(umodel.getEmpCardNo());

        model.setCompanyName("Deeplaid Laboratories Ltd.");
        model.setCompanyAddress("5th Floor, Al-Habib Complex, Kaptan Bazar, Wari, Dhaka 1203");
        model.setEmp_name(empModel.getEmp_name());
        model.setDivision_name(empModel.getDivision_name());
        model.setEmp_type_name(empModel.getEmp_type_name());

        model.setEmpCardNo(umodel.getEmpCardNo());

        model.setLable(umodel.getEmpCardNo()+"  "+empModel.getEmp_name());

        modelLists.add(model);


        CusJasperReportDef report = new CusJasperReportDef();
        report.setOutputFilename("Barcode Report");
        report.setReportName("Barcode");
        report.setReportDir(CommonFunction.getResoucePath("/report") + "/");
        report.setReportFormat(CommonFunction.printFormat("PDF"));
        report.setReportData(modelLists);

        ByteArrayOutputStream baos = null;

        try {
            baos = coreJasperService.generateReport(report);
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            finallyOutputStream(baos);
        }
        report.setContent(baos.toByteArray());

        return report;
    }

    public CusJasperReportDef labelBarcode(String labNos) {


        LeaveModel umodel = objectMapperReadValue(labNos, LeaveModel.class);
        BarCodeModel model = new BarCodeModel();
        List<BarCodeModel> modelLists = new ArrayList<>();
        List<LeaveModel> modelList = new ArrayList<>();


        HrmEmployeeModel empModel = employeeRepository.findById(umodel.getEmpCardNo());

        model.setCompanyName("Deeplaid Laboratories Ltd.");
        model.setCompanyAddress("5th Floor, Al-Habib Complex, Kaptan Bazar, Wari, Dhaka 1203");
        model.setEmp_name(empModel.getEmp_name());
        model.setDivision_name(empModel.getDivision_name());
        model.setEmp_type_name(empModel.getEmp_type_name());

        model.setEmpCardNo(umodel.getEmpCardNo());

        model.setLable(umodel.getEmpCardNo()+"  "+empModel.getEmp_name());

        modelLists.add(model);

        CusJasperReportDef report = new CusJasperReportDef();
        report.setOutputFilename("Label Barcode");
        report.setReportName("Barcode");
        report.setReportDir(CommonFunction.getResoucePath("/report") + "/");
        report.setReportFormat(CommonFunction.printFormat("PDF"));
        // report.setParameters(parameterMap);
        report.setReportData(modelLists);

        ByteArrayOutputStream baos = null;

        try {
            baos = coreJasperService.generateReport(report);
        } catch (Exception e) {
            e.printStackTrace();
        }

        report.setContent(baos.toByteArray());

        return report;
    }



}

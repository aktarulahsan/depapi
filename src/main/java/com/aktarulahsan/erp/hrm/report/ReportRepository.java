/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aktarulahsan.erp.hrm.report;


import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CoreJasperService;
import com.aktarulahsan.erp.core.reportConfig.coreReport.CusJasperReportDef;
import com.aktarulahsan.erp.hrm.department.DepartmentModel;
import com.aktarulahsan.erp.hrm.department.DepartmentRepository;
import com.aktarulahsan.erp.hrm.designation.DesignationRepository;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeRepository;
import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import com.aktarulahsan.erp.hrm.leave.LeaveRepository;
import com.aktarulahsan.erp.hrm.report.dtos.LeaveDto;
import com.aktarulahsan.erp.hrm.report.dtos.LeaveSummary;

import com.aktarulahsan.erp.util.CommonFunction;
import com.aktarulahsan.erp.util.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohammad Galib
 */

@Repository
@Transactional
public class ReportRepository extends BaseRepository {

	@Autowired
	CoreJasperService coreJasperService;

	@Autowired
	LeaveRepository repository;

	@Autowired
	private JdbcTemplate db;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	HrmEmployeeRepository employeeRepository;

	@Autowired
	DepartmentRepository departmentRepository;


	public CusJasperReportDef demoReports(String reqObj) {

//		DemoDto demoDto = new DemoDto();
//		List<DemoDto> demoList = new ArrayList<>();
		LeaveModel umodel = objectMapperReadValue(reqObj, LeaveModel.class);
		LeaveDto model = new LeaveDto();
		List<LeaveDto> modelLists = new ArrayList<>();
		List<LeaveModel> modelList = new ArrayList<>();
//		Response rs = repository.findDetailsById(umodel.getEmpCardNo());
		Response rs = repository.findDetailsByIdforByDate(umodel);
		HrmEmployeeModel empModel = employeeRepository.findById(umodel.getEmpCardNo());



		modelList = rs.getItems();


		model.setCompanyName("Deeplaid Laboratories Ltd.");
		model.setCompanyAddress("5th Floor, Al-Habib Complex, Kaptan Bazar, Wari, Dhaka 1203");
		model.setEmp_name(empModel.getEmp_name());
		model.setDivision_name(empModel.getDivision_name());
		model.setEmp_type_name(empModel.getEmp_type_name());
		model.setFrom_date(umodel.getFrom_date());
		model.setTo_date(umodel.getTo_date());

		model.setItemList(modelList);
		model.setEmpCardNo(umodel.getEmpCardNo());

		modelLists.add(model);

//		DemoDto demoDto = new DemoDto();
//		List<DemoDto> demoList = new ArrayList<>();
//
//		demoDto.setPatientname("Mohammad Galib");
//		demoDto.setRank("Maj.");
//		demoDto.setAge("27Y");
//		demoDto.setCompanyName("Global  IT");
//		demoDto.setCompanyAddress("Mirpur 10, Dhaka");
//		demoDto.setGShear("0");
//
//		demoDto.setItemList(demoList);
//
//		demoList.add(demoDto);

		CusJasperReportDef report = new CusJasperReportDef();
		report.setOutputFilename("demo");
		report.setReportName("LeaveReport");
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



//	public static String hnPatientStmt(String reqObj) {
//
//
//	}


	public CusJasperReportDef demoReport(String reqObj) {
		Connection con = null;
		ResultSet rsp = null;
		Statement stm = null;
		PreparedStatement sts = null;
//		DemoDto demoDto = new DemoDto();
//		List<DemoDto> demoList = new ArrayList<>();
		LeaveModel umodel = objectMapperReadValue(reqObj, LeaveModel.class);
		LeaveDto model = new LeaveDto();
		List<LeaveDto> modelLists = new ArrayList<>();
		List<LeaveSummary> leaveSummaryList = new ArrayList<>();
		List<LeaveModel> modelList = new ArrayList<>();
//		Response rs = repository.findDetailsById(umodel.getEmpCardNo());
		Response rs = repository.findDetailsByIdforByDate(umodel);
		HrmEmployeeModel empModel = employeeRepository.findById(umodel.getEmpCardNo());
		DepartmentModel dpModel = (DepartmentModel) departmentRepository.findObjectById(empModel.getDepartment_code());
		model.setDepartment_name(dpModel.getDepartment_name());




		modelList = rs.getItems();


		model.setCompanyName("Deeplaid Laboratories Ltd.");
		model.setCompanyAddress("5th Floor, Al-Habib Complex, Kaptan Bazar, Wari, Dhaka 1203");
		model.setEmp_name(empModel.getEmp_name());
		model.setDivision_name(empModel.getDivision_name());
		model.setEmp_type_name(empModel.getEmp_type_name());


		model.setItemList(modelList);
		model.setEmpCardNo(umodel.getEmpCardNo());





		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		model.setFrom_date(umodel.getFrom_date());
		model.setTo_date(umodel.getTo_date());

		JSONObject json = new JSONObject(reqObj);
		StringBuilder sqlQuery = new StringBuilder();

		sqlQuery.append("ALTER VIEW LEAVE_BAL_SUMM AS ");
		sqlQuery.append("SELECT LEAVE_ID,  SUM(NO_OF_DAYS) NO_OF_DAYS FROM HRS_EMP_LEAVE ");
		sqlQuery.append("WHERE EMP_CARD_NO='" + umodel.getEmpCardNo() + "'");
		sqlQuery.append( "AND APPROVED_STATUS=0 ");
		if (umodel.getFrom_date() != null)
		{
			sqlQuery.append(" AND FROM_DATE  >= " +"CONVERT(datetime,'"+formatter.format(umodel.getFrom_date()) +"',103)" + "");
			sqlQuery.append(" AND  TO_DATE <=" + "CONVERT(datetime,'"+formatter.format(umodel.getTo_date()) +"',103)"+ " ");
		}
		sqlQuery.append("GROUP BY LEAVE_ID ");

		try {
			con = getOraConnection();
			stm = con.createStatement();
			int a = stm.executeUpdate(sqlQuery.toString());
//
//			while (a) {
				ResultSet rsps = null;
				StringBuilder sqlQueryb = new StringBuilder();
				sqlQueryb.append( "SELECT  HRS_EMP_LEAVE_PARENT.LEAVE_ID, ISNULL(HRS_EMP_LEAVE_PARENT.LEAVE_OPENING+HRS_EMP_LEAVE_PARENT.OPN_TRAN,0)LEAVE_OPENING, ISNULL(SUM(LEAVE_BAL_SUMM.NO_OF_DAYS), 0) AS USED_LEAVE ");
				sqlQueryb.append( "FROM  HRS_EMP_LEAVE_PARENT LEFT OUTER JOIN ");
				sqlQueryb.append( "LEAVE_BAL_SUMM ON HRS_EMP_LEAVE_PARENT.LEAVE_ID = LEAVE_BAL_SUMM.LEAVE_ID ");
				sqlQueryb.append( "WHERE (HRS_EMP_LEAVE_PARENT.EMP_CARD_NO = '" + umodel.getEmpCardNo() + "') ");
				if (umodel.getFrom_date() != null)
				{
					sqlQueryb.append( " AND EFFECTIVE_DATE  >= " +"CONVERT(datetime,'"+formatter.format(umodel.getFrom_date()) +"',103)" + "");
					sqlQueryb.append( " AND  EFFECTIVE_DATE <=" + "CONVERT(datetime,'"+formatter.format(umodel.getTo_date()) +"',103)"+ "");
				}
				sqlQueryb.append( "GROUP BY HRS_EMP_LEAVE_PARENT.LEAVE_ID, HRS_EMP_LEAVE_PARENT.LEAVE_OPENING,HRS_EMP_LEAVE_PARENT.OPN_TRAN ");
				sqlQueryb.append( "ORDER BY ISNULL(HRS_EMP_LEAVE_PARENT.LEAVE_OPENING+HRS_EMP_LEAVE_PARENT.OPN_TRAN,0) DESC ");

				con = getOraConnection();
				stm = con.createStatement();
//			boolean rskkkk = stm.execute(sqlQueryb.toString());
			  	rsps = stm.executeQuery(sqlQueryb.toString());

				while (rsps.next()){
					LeaveSummary summary = new LeaveSummary();
					summary.setLeave_id(rsps.getString("LEAVE_ID"));
					summary.setUsedLeave(rsps.getString("USED_LEAVE"));
					summary.setLeave_opening(rsps.getString("LEAVE_OPENING"));
					summary.setBalance(String.valueOf((Integer.parseInt(summary.getLeave_opening()) - Integer.parseInt(summary.getUsedLeave()))));

					leaveSummaryList.add(summary);
				}

		}catch (Exception e){
			e.printStackTrace();

		}


		model.setLeaveSummaryList(leaveSummaryList);

		modelLists.add(model);
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("SUBREPORT_DIR", CommonFunction.getResoucePath("/report"));
		CusJasperReportDef report = new CusJasperReportDef();
		report.setOutputFilename("demo");
		report.setReportName("LeaveReport");
		report.setReportDir(CommonFunction.getResoucePath("/report") + "/");
		report.setReportFormat(CommonFunction.printFormat("PDF"));
		report.setParameters(parameterMap);
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
	
}

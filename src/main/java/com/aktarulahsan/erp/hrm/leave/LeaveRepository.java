package com.aktarulahsan.erp.hrm.leave;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.hrm.report.dtos.LeaveSummary;
import com.aktarulahsan.erp.hrm.tour.TourDetailsModel;
import com.aktarulahsan.erp.hrm.tour.TourRepository;
import com.aktarulahsan.erp.util.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Repository
@Transactional
public class LeaveRepository extends BaseRepository {


    @Autowired
    TourRepository repository;


    public Response save(String reqObj) {
        Date dte=new Date();
        long orderId = dte.getTime();
        long pssss = orderId;
        String oid = String.valueOf(orderId).substring(8,13);
        int oidi= Integer.parseInt(oid);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        LeaveModel model = objectMapperReadValue(reqObj, LeaveModel.class);
        model.emp_leave_key = model.empCardNo + oid;
        model.pay_leave_serial = Double.valueOf(1+oid);
        JSONObject json = new JSONObject(reqObj);





        return baseOnlySave(model);
    }


    public Response saveTour(String reqObj) {
//        Date dte=new Date();
//        long orderId = dte.getTime();
//        long pssss = orderId;
//        TourDetailsModel tourDetailsModel ;
//        List<TourDetailsModel> tourDetailsModelList;
//
//        String oid = String.valueOf(orderId).substring(8,13);
//        int oidi= Integer.parseInt(oid);
//
//
//        LeaveModel model = objectMapperReadValue(reqObj, LeaveModel.class);
//        model.emp_leave_key = model.empCardNo + oid;
//        model.pay_leave_serial = Double.valueOf(1+oid);
//
//        Response rsp = baseOnlySave(model);
//
//        tourDetailsModelList = model.getTourList();
//        for (int i = 0; i < tourDetailsModelList.size(); i++) {
//
//            tourDetailsModel = tourDetailsModelList.get(i);
//            tourDetailsModel.setEmp_leave_key(model.getEmp_leave_key());
//
//            Response rs;
//            rs = baseOnlySave(tourDetailsModel);
//        }
//
//
//
        return null;
    }


    public Response getLeaveInfo(String reqObj){
        Connection con = null;
        ResultSet rsp = null;
        Statement stm = null;
        Response rs= new Response();
        List<LeaveSummary> leaveSummaryList = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");



        int years = Calendar.getInstance().get(Calendar.YEAR);
        LocalDate firstDay = YearMonth.of(years,1).atDay(1);
        LocalDate lastDay = YearMonth.of(years,12).atDay(31);


//        LocalDate firstOfCurrentMonth = today.with ( ChronoField.DAY_OF_MONTH , 1 );
        SimpleDateFormat mdyFormat = new SimpleDateFormat("dd-MM-yyyy");
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Date  fsDate =Date.from(firstDay.atStartOfDay(defaultZoneId).toInstant());
        Date  lsDate =Date.from(lastDay.atStartOfDay(defaultZoneId).toInstant());
        String s = mdyFormat.format(fsDate);
        String p = mdyFormat.format(lsDate);
        LeaveModel model = new LeaveModel();

        model.setEmpCardNo(reqObj);
        StringBuilder sqlQuery = new StringBuilder();

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
            sqlQueryb.append( "WHERE (HRS_EMP_LEAVE_PARENT.EMP_CARD_NO = '" + model.getEmpCardNo() + "') ");
            if (s != null)
            {
                sqlQueryb.append( " AND EFFECTIVE_DATE  >= " +"CONVERT(datetime,'"+s +"',103)" + "");
                sqlQueryb.append( " AND  EFFECTIVE_DATE <=" + "CONVERT(datetime,'"+p +"',103)"+ "");
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

      if(leaveSummaryList.size()>0){
          rs.setItems(leaveSummaryList);
          return  rs;

      }
        return null;

    }


    public Response update(String reqObj) {

        LeaveModel  model = objectMapperReadValue(reqObj, LeaveModel.class);



        return baseSaveOrUpdate(model);

    }

    public Response delete(String id) {
        if (id == null) {
            return getErrorResponse("Id is blank");
        }

        LeaveModel  model = findById(id);

        if (model != null) {
            return baseDelete(model);
        }

        return getErrorResponse("Id not found");
    }

    public LeaveModel findById(String id) {

        LeaveModel model 	= new LeaveModel();
        model.setEmpCardNo(id);
        Response response = baseFindById(criteriaQuery(model));
        if (response.isSuccess()) {

            return getValueFromObject(response.getObj(), LeaveModel.class);
        }
        return null;
    }

    public Response findDetailsById(String id) {
        LeaveModel entity = new LeaveModel();
        entity.setEmpCardNo(id);
//        roomEntity.setActiveStatus(1);
        return getListFindById(criteriaQuery(entity));
    }

    public Response findDetailsByIdforByDate(LeaveModel umodel) {

        return getListFindById(criteriaQuery(umodel));
    }

    public Response findTourById(String id, String lid) {
        LeaveModel entity = new LeaveModel();
        entity.setEmpCardNo(id);
        entity.setLeave_id(lid);
//        roomEntity.setActiveStatus(1);
        return getListFindById(criteriaQuery(entity));
    }



    public Response list(String reqObj) {

        LeaveModel branchModel = null;
        if (null != reqObj) {
            branchModel = objectMapperReadValue(reqObj, LeaveModel.class);
        }
        return baseList(criteriaQuery(branchModel));
    }

    private CriteriaQuery criteriaQuery(LeaveModel filter) {
        init();

        List<Predicate> p 	= new ArrayList<Predicate>();
        p = criteriaCondition(filter, null, null);

        if (!CollectionUtils.isEmpty(p)) {
            Predicate[] pArray 	= p.toArray(new Predicate[] {});
            Predicate predicate = builder.and(pArray);
            criteria.where(predicate);
        }
        return criteria;
    }
    private List<Predicate> criteriaCondition(LeaveModel filter, CriteriaBuilder builder, Root<LeaveModel> root) {

        if (builder == null) {
            builder 		= super.builder;
        }
        if (root == null) {
            root 			= super.root;
        }

        List<Predicate> p 	= new ArrayList<Predicate>();

        if (filter != null) {

//            if (filter.getRoleId() !=null) {
//                Predicate condition 	= builder.equal(root.get("activeStatus"), filter.getRoleId());
//                p.add(condition);
//            }
            if (filter.getEmpCardNo() !=null ) {
                Predicate condition 	= builder.equal(root.get("empCardNo"), filter.getEmpCardNo());
                p.add(condition);
            }
            if (filter.getLeave_id() !=null ) {
                Predicate condition 	= builder.equal(root.get("leave_id"), filter.getLeave_id());
                p.add(condition);
            }

            if (filter.getFrom_date() != null) {
                Predicate condition = builder.greaterThanOrEqualTo(root.get("from_date"), filter.getFrom_date());
                p.add(condition);
            }
            if (filter.getTo_date() != null) {
                Predicate condition = builder.lessThanOrEqualTo(root.get("to_date"), filter.getTo_date());
                p.add(condition);
            }
//            if (filter.getEmpCardNo() !=null ) {
//                Predicate condition 	= builder.equal(root.get("empCardNo"), filter.getEmpCardNo());
//                p.add(condition);
//            }



        }

        return p;
    }

    private void init() {
        initEntityManagerBuilderCriteriaQueryRoot(LeaveModel.class);
        CriteriaBuilder builder 	= super.builder;
        CriteriaQuery criteria 		= super.criteria;
        Root root 					= super.root;
    }


}

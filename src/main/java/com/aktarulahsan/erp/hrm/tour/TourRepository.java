package com.aktarulahsan.erp.hrm.tour;


import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.hrm.leave.LeaveModel;
import com.aktarulahsan.erp.util.Response;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class TourRepository extends BaseRepository {

    ArrayList<TourDetailsModel> list = new ArrayList<TourDetailsModel>();

    public Response save(String reqObj) {
        Date dte=new Date();
        long orderId = dte.getTime();
        long pssss = orderId;
        String oid = String.valueOf(orderId).substring(8,13);
        int oidi= Integer.parseInt(oid);

        LeaveModel model = objectMapperReadValue(reqObj, LeaveModel.class);
        model.setEmp_leave_key(model.getEmpCardNo() + oid);
        model.setPay_leave_serial(Double.valueOf(1+oid));
        model.setFrom_date(new Date());
        model.setInsert_date(new Date());



        JSONObject json = new JSONObject(reqObj);

       Response rs = baseOnlySave(model);

        for (int i = 0; i < model.getTourList().size(); i++) {

            TourDetailsModel tmodel = model.getTourList().get(i);

            tmodel.setEmp_leave_key(model.getEmp_leave_key());

                Response rsp = baseOnlySave(tmodel);
            String p = rsp.toString();
        }


        return rs;
               
    }

    public Response update(String reqObj) {

        Response res = new Response();
        LeaveModel model = objectMapperReadValue(reqObj, LeaveModel.class);
//        model.setEmp_leave_key(model.getEmpCardNo() + oid);
//        model.setPay_leave_serial(Double.valueOf(1+oid));
//        model.setFrom_date(new Date());
        model.setUpdate_date(new Date());

        res = baseSaveOrUpdate(model);
        Response dres = findDetailsById(String.valueOf(model.getEmp_leave_key()));

        if(dres !=null){
            Response ds = delete(model.getEmp_leave_key());
            String a = ds.toString();
        }
//        model.(new Date());

        for (int i = 0; i < model.getTourList().size(); i++) {

            TourDetailsModel tmodel = model.getTourList().get(i);

            tmodel.setEmp_leave_key(model.getEmp_leave_key());

            Response rsp = baseSaveOrUpdate(tmodel);
            String p = rsp.toString();
        }

        return res;

    }

    public Response delete(String id) {
        if (id ==null) {
            return getErrorResponse("Id is blank");
        }

        Response  res = findDetailsById(id);

        if(res.isSuccess()){
            list = (ArrayList<TourDetailsModel>) res.getItems();
            for (int i = 0; i < list.size(); i++) {
                baseDelete(list.get(i));
            }
            return res;
        }

        return getErrorResponse("Id not found");
    }

    public TourDetailsModel findById(String id) {

        TourDetailsModel model 	= new TourDetailsModel();
        model.setEmp_leave_key(id);
        Response response = baseFindById(criteriaQuery(model));
        if (response.isSuccess()) {

            return getValueFromObject(response.getObj(), TourDetailsModel.class);
        }
        return null;
    }

    public Response findDetailsById(String id) {
        TourDetailsModel entity = new TourDetailsModel();
        entity.setEmp_leave_key(id);
//        roomEntity.setActiveStatus(1);
        return getListFindById(criteriaQuery(entity));
    }



    public Response list(String reqObj) {

        TourDetailsModel branchModel = null;
        if (null != reqObj) {
            branchModel = objectMapperReadValue(reqObj, TourDetailsModel.class);
        }
        return baseList(criteriaQuery(branchModel));
    }

    private CriteriaQuery criteriaQuery(TourDetailsModel filter) {
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
    private List<Predicate> criteriaCondition(TourDetailsModel filter, CriteriaBuilder builder, Root<TourDetailsModel> root) {

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
            if (filter.getEmp_leave_key() !=null ) {
                Predicate condition 	= builder.equal(root.get("emp_leave_key"), filter.getEmp_leave_key());
                p.add(condition);
            }
//            if (filter.getLeave_id() !=null ) {
//                Predicate condition 	= builder.equal(root.get("leave_id"), filter.getLeave_id());
//                p.add(condition);
//            }
//            if (filter.getFrom_date() !=null ) {
//                Predicate condition 	= builder.equal(root.get("from_date"), filter.getFrom_date());
//                p.add(condition);
//            }
//            if (filter.getEmpCardNo() !=null ) {
//                Predicate condition 	= builder.equal(root.get("empCardNo"), filter.getEmpCardNo());
//                p.add(condition);
//            }



        }

        return p;
    }

    private void init() {
        initEntityManagerBuilderCriteriaQueryRoot(TourDetailsModel.class);
        CriteriaBuilder builder 	= super.builder;
        CriteriaQuery criteria 		= super.criteria;
        Root root 					= super.root;
    }

}

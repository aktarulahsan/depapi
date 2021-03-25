package com.aktarulahsan.erp.hrm.setting.leaveType;

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
import java.util.List;


@Repository
@Transactional
public class LeaveTypeRepository extends BaseRepository {


    public Response save(String reqObj) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object pricipal = auth.getPrincipal();
        LeaveTypeModel model = objectMapperReadValue(reqObj, LeaveTypeModel.class);


        JSONObject json = new JSONObject(reqObj);

        return baseOnlySave(model);
    }

    public Response update(String reqObj) {

        LeaveTypeModel  model = objectMapperReadValue(reqObj, LeaveTypeModel.class);


//        model.(new Date());

        return baseSaveOrUpdate(model);

    }

    public Response delete(String id) {
        if (id == null) {
            return getErrorResponse("Id is blank");
        }

        LeaveTypeModel  model = findById(id);

        if (model != null) {
            return baseDelete(model);
        }

        return getErrorResponse("Id not found");
    }

    public LeaveTypeModel findById(String id) {

        LeaveTypeModel model 	= new LeaveTypeModel();
        model.setLeaveId(id);
        Response response = baseFindById(criteriaQuery(model));
        if (response.isSuccess()) {

            return getValueFromObject(response.getObj(), LeaveTypeModel.class);
        }
        return null;
    }

    public Response findDetailsById(String id) {
        LeaveTypeModel entity = new LeaveTypeModel();
        entity.setLeaveId(id);
//        roomEntity.setActiveStatus(1);
        return getListFindById(criteriaQuery(entity));
    }



    public Response list(String reqObj) {

        LeaveTypeModel branchModel = null;
        if (null != reqObj) {
            branchModel = objectMapperReadValue(reqObj, LeaveTypeModel.class);
        }
        return baseList(criteriaQuery(branchModel));
    }

    private CriteriaQuery criteriaQuery(LeaveTypeModel filter) {
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
    private List<Predicate> criteriaCondition(LeaveTypeModel filter, CriteriaBuilder builder, Root<LeaveTypeModel> root) {

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

            if (filter.getLeaveId() !=null ) {
                Predicate condition 	= builder.equal(root.get("leave_id"), filter.getLeaveId());
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
        initEntityManagerBuilderCriteriaQueryRoot(LeaveTypeModel.class);
        CriteriaBuilder builder 	= super.builder;
        CriteriaQuery criteria 		= super.criteria;
        Root root 					= super.root;
    }
}

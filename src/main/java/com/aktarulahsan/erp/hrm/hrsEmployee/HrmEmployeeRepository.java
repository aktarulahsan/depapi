package com.aktarulahsan.erp.hrm.hrsEmployee;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.hrm.tour.TourDetailsModel;
import com.aktarulahsan.erp.tms.setting.category.CategoryModel;
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
public class HrmEmployeeRepository extends BaseRepository {



    public Response save(String reqObj) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object pricipal = auth.getPrincipal();
        HrmEmployeeModel model = objectMapperReadValue(reqObj, HrmEmployeeModel.class);
//        if (pricipal instanceof User) {
//            model.setSsCreator(((User) pricipal).getUsername());
//        }

//        model.setSsModifiedOn(new Date());

        JSONObject json = new JSONObject(reqObj);

        return baseOnlySave(model);
    }

    public Response update(String reqObj) {

        HrmEmployeeModel  model = objectMapperReadValue(reqObj, HrmEmployeeModel.class);


//        model.(new Date());

        return baseSaveOrUpdate(model);

    }

    public Response delete(String id) {
        if (id == null) {
            return getErrorResponse("Id is blank");
        }

        HrmEmployeeModel  model = findById(id);

        if (model != null) {
            return baseDelete(model);
        }

        return getErrorResponse("Id not found");
    }

    public HrmEmployeeModel findById(String id) {

        HrmEmployeeModel model 	= new HrmEmployeeModel();
        model.setEmpCardNo(id);
        Response response = baseFindById(criteriaQuery(model));
        if (response.isSuccess()) {

            return getValueFromObject(response.getObj(), HrmEmployeeModel.class);
        }
        return null;
    }

    public Response findByUser(String id) {

        HrmEmployeeModel model 	= new HrmEmployeeModel();
        model.setEmpCardNo(id);
        Response response = baseFindById(criteriaQuery(model));

        return response;
    }



    public Response findResponsePersone(String id) {
        HrmEmployeeModel entity = new HrmEmployeeModel();
        entity.setDepartment_code(id);
        Response rs = getListFindById(criteriaQuery(entity));

        return rs;
    }


    public Response findByDepartmentId(String id) {
        HrmEmployeeModel entity = new HrmEmployeeModel();
        entity.setDepartment_code(id);

        return getListFindById(criteriaQuery(entity));
    }



    public Response list(String reqObj) {

        HrmEmployeeModel branchModel = null;
        if (null != reqObj) {
            branchModel = objectMapperReadValue(reqObj, HrmEmployeeModel.class);
        }
        return baseList(criteriaQuery(branchModel));
    }

    private CriteriaQuery criteriaQuery(HrmEmployeeModel filter) {
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
    private List<Predicate> criteriaCondition(HrmEmployeeModel filter, CriteriaBuilder builder, Root<HrmEmployeeModel> root) {

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


            if (filter.getDepartment_code() !=null ) {
                Predicate condition 	= builder.equal(root.get("department_code"), filter.getDepartment_code());
                p.add(condition);
            }



        }

        return p;
    }

    private void init() {
        initEntityManagerBuilderCriteriaQueryRoot(HrmEmployeeModel.class);
        CriteriaBuilder builder 	= super.builder;
        CriteriaQuery criteria 		= super.criteria;
        Root root 					= super.root;
    }




}

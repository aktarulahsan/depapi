package com.aktarulahsan.erp.hrm.designation;

import com.aktarulahsan.erp.core.base.BaseRepository;
import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeModel;
import com.aktarulahsan.erp.util.CommonMethod;
import com.aktarulahsan.erp.util.Response;
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
public class DesignationRepository extends BaseRepository implements CommonMethod {
    @Override
    public Response create(String regObj) {
        return  null;
    }

    @Override
    public Response update(String regObj) {
        return  null;
    }

    @Override
    public Response delete(String regObj) {
        return  null;
    }

    @Override
    public Response findById(String regObj) {


        DesignationModel model = objectMapperReadValue(regObj, DesignationModel.class);

        model.setDesignation_code(regObj);

        Response response = baseFindById(criteriaQuery(model));

        return response;

    }

    @Override
    public Object findObjectById(String regObj) {
        return null;
    }


    private CriteriaQuery criteriaQuery(DesignationModel filter) {
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
    private List<Predicate> criteriaCondition(DesignationModel filter, CriteriaBuilder builder, Root<DesignationModel> root) {

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
            if (filter.getDesignation_code() !=null ) {
                Predicate condition 	= builder.equal(root.get("designation_code"), filter.getDesignation_code());
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
        initEntityManagerBuilderCriteriaQueryRoot(DesignationModel.class);
        CriteriaBuilder builder 	= super.builder;
        CriteriaQuery criteria 		= super.criteria;
        Root root 					= super.root;
    }


}

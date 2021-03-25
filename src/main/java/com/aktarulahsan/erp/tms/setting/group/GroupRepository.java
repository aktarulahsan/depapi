package com.aktarulahsan.erp.tms.setting.group;


import com.aktarulahsan.erp.core.base.BaseRepository;
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
public class GroupRepository extends BaseRepository {


    public Response save(String reqObj) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        GroupModel model = objectMapperReadValue(reqObj, GroupModel.class);


        model.setInsertDate(new Date());

        JSONObject json = new JSONObject(reqObj);

        return baseOnlySave(model);
    }

    public Response update(String reqObj) {

        GroupModel  model = objectMapperReadValue(reqObj, GroupModel.class);


//        model.setSsCreatedOn(new Date());

        return baseSaveOrUpdate(model);

    }

    public Response delete(String id) {
        if (id == null) {
            return getErrorResponse("Id is blank");
        }

        GroupModel  model = findById(id);

        if (model != null) {
            return baseDelete(model);
        }

        return getErrorResponse("Id not found");
    }

    public GroupModel findById(String id) {

        GroupModel model 	= new GroupModel();
//        model.setCategoryId(Integer.parseInt(id));
        Response response = baseFindById(criteriaQuery(model));
        if (response.isSuccess()) {

            return getValueFromObject(response.getObj(), GroupModel.class);
        }
        return null;
    }





    public Response list(String reqObj) {

        GroupModel branchModel = null;
        if (null != reqObj) {
            branchModel = objectMapperReadValue(reqObj, GroupModel.class);
        }
        return baseList(criteriaQuery(branchModel));
    }

    private CriteriaQuery criteriaQuery(GroupModel filter) {
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
    private List<Predicate> criteriaCondition(GroupModel filter, CriteriaBuilder builder, Root<GroupModel> root) {

        if (builder == null) {
            builder 		= super.builder;
        }
        if (root == null) {
            root 			= super.root;
        }

        List<Predicate> p 	= new ArrayList<Predicate>();

        if (filter != null) {

            if (filter.getId() >0) {
                Predicate condition 	= builder.equal(root.get("id"), filter.getId());
                p.add(condition);
            }


        }

        return p;
    }

    private void init() {
        initEntityManagerBuilderCriteriaQueryRoot(GroupModel.class);
        CriteriaBuilder builder 	= super.builder;
        CriteriaQuery criteria 		= super.criteria;
        Root root 					= super.root;
    }

}


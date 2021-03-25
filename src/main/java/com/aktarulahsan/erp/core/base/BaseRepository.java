package com.aktarulahsan.erp.core.base;


import com.aktarulahsan.erp.util.CommonFunctions;
import com.aktarulahsan.erp.util.Response;
import org.hibernate.jpa.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.env.Environment;

public class BaseRepository  implements CommonFunctions {

    public CriteriaBuilder builder = null;
    public CriteriaQuery criteria = null;
    public Root root = null;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private Environment env;

//    @PersistenceContext()
//    private EntityManager entityManager;


    public void initEntityManagerBuilderCriteriaQueryRoot(Class clazz) {
        criteriaRoot(clazz);
    }
    public Root criteriaRoot(Class clazz) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery(clazz);
        Root root = criteria.from(clazz);
        this.builder = builder;

        this.criteria = criteria;
        this.root = root;

        return root;
    }

    public Response baseOnlySave(Object obj) {
        Response response = new Response();
        try {
            entityManager.persist(obj);
            response.setObj(obj);
            return getSuccessResponse("Saved Successfully", response);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            response.setMessage(e.toString());
            return getErrorResponse("Save fail !!",response);
        }

    }


    public Response baseBatchOnlySave(List<Object> objects) {
        Response response = new Response();
        int batchSize =objects.size();
        try {
            List<Object> items = new ArrayList<Object>();
            for (int i = 0; i < objects.size(); i++) {
                if (i > 0 && i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                Object object = objects.get(i);
                entityManager.persist(object);
                items.add(object);
            }
            response.setItems(items);
            return getSuccessResponse("Save Successfully", response);
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse("Batch Save Fail !!");
        }
    }

    public Response baseList(CriteriaQuery criteria) {
        Response response = new Response();
        List list = null;
        try {
            list = entityManager.createQuery(criteria).setHint(QueryHints.HINT_READONLY, true).getResultList();

            if(list.size() > 0) {
                response.setData(list);
                return getSuccessResponse("Data found ", response);
            }

            return getSuccessResponse("Data Empty " );
        } catch (Exception e) {
            // TODO: handle exception
            return getErrorResponse("Data not found !!");
        }

    }
    public Response getListFindById(CriteriaQuery criteria) {
        Response response = new Response();
        Object obj = null;
        try {
            obj = entityManager.createQuery(criteria).getResultList();
            response.setItems((List) obj);
            return getSuccessResponse("find data Successfully", response);
        } catch (Exception e) {
            // TODO: handle exception
            return getErrorResponse("Data not Found !!");
        }

    }


    public Response baseDelete(Object obj) {
        try {
            entityManager.remove(obj);
            return getSuccessResponse("Delete Successfully");
        } catch (Exception e) {
            return getErrorResponse("Delete fail !!");
        }

    }


    public Response baseUpdate(Object obj) {
        Response response = new Response();
        try {
            System.out.println(entityManager.merge(obj));
            response.setObj(entityManager.merge(obj));

            response.setObj(obj);
            return getSuccessResponse("Updated Successfully",response);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return getErrorResponse("Update fail !!");
        }

    }
    public Response baseSaveOrUpdate(Object obj) {
        Response response = new Response();
        try {
            response.setObj(entityManager.merge(obj));
            entityManager.flush();
            return getSuccessResponse("Update Successfully", response);
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e.getCause().getMessage());
            return getErrorResponse("Save fail !!");
        }

    }

    public Response baseFindById(CriteriaQuery criteria) {
        Response response = new Response();
        Object obj = null;
        try {
            obj = entityManager.createQuery(criteria).getSingleResult();
            response.setObj(obj);
            return getSuccessResponse("find data Successfully", response);
        } catch (Exception e) {
            // TODO: handle exception
            return getErrorResponse("Data not Found !!");
        }

    }
    public void finallyOutputStream(ByteArrayOutputStream baos) {

        if(baos != null) {
            try {
                baos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public Connection getOraConnection() {

        try {

            Class.forName(env.getProperty("spring.datasource.driver-class-name"));

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your Oracle JDBC Driver?");

        }
        Connection connection = null;
        try {
//            connection = DriverManager.getConnection(env.getProperty("ora.url"), env.getProperty("ora.user"),
//                    env.getProperty("ora.password"));
            connection = DriverManager.getConnection(env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"),
                    env.getProperty("spring.datasource.password"));

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
        }
        if (connection != null) {
            return connection;
        } else {
            System.out.println("Failed to make connection!");
            return null;
        }
    }


    public Response baseSingleObject(CriteriaQuery criteria) {
        Response response = new Response();
        Object obj = null;
        try {
            obj = entityManager.createQuery(criteria).getSingleResult();
            response.setObj(obj);
            return getSuccessResponse("find data Successfully", response);
        } catch (Exception e) {
            // TODO: handle exception
            return getErrorResponse("Data not Found !!");
        }

    }



}

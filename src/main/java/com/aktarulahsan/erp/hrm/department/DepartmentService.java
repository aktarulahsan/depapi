package com.aktarulahsan.erp.hrm.department;

import com.aktarulahsan.erp.util.CommonMethod;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService implements CommonMethod {

    @Autowired
    DepartmentRepository repository;


    @Override
    public Response create(String regObj) {
        return null;
    }

    @Override
    public Response update(String regObj) {
        return null;
    }

    @Override
    public Response delete(String regObj) {
        return null;
    }

    @Override
    public Response findById(String regObj) {
        return repository.findById(regObj);
    }

    @Override
    public Object findObjectById(String regObj) {
        return null;
    }
}

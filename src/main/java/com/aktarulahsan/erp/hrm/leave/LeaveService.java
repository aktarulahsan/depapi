package com.aktarulahsan.erp.hrm.leave;

import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeRepository;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LeaveService {

    @Autowired
    private LeaveRepository repository;
    public Response list(String reqObj) {
        return repository.list(reqObj);
    }

    public Response save(String reqObj) {
        return repository.save(reqObj);
    }

    public Response saveTour(String reqObj) {
        return repository.saveTour(reqObj);
    }

    public Response update(String reqObj ) {
        return repository.update(reqObj);
    }

    public Response delete(String id) {
        return repository.delete(id);
    }

    public Response findDetailsById(String id) {

        return repository.findDetailsById(id);
    }
    public Response findTourById(String id, String lid) {

        return repository.findTourById(id, lid);
    }

    public Response getLeaveInfo(String id) {

        return repository.getLeaveInfo(id);
    }
}

package com.aktarulahsan.erp.hrm.tour;

import com.aktarulahsan.erp.hrm.leave.LeaveRepository;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TourService {



    @Autowired
    private TourRepository repository;
    public Response list(String reqObj) {
        return repository.list(reqObj);
    }

    public Response save(String reqObj) {
        return repository.save(reqObj);
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
    public Response findTourListById(String id) {

        return repository.findDetailsById(id);
    }


}

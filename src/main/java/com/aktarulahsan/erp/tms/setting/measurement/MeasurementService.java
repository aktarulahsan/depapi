package com.aktarulahsan.erp.tms.setting.measurement;

import com.aktarulahsan.erp.tms.setting.category.CategoryRepository;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MeasurementService {

    @Autowired
    private MeasurementRepository repository;
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
}

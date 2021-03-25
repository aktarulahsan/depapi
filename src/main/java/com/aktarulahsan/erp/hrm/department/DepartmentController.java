package com.aktarulahsan.erp.hrm.department;


import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hrm/department")
public class DepartmentController {

    @Autowired
    DepartmentService service;


    @GetMapping("/findById")
    public Response findByusername(@RequestParam("department_code") String reqObj) {
        return service.findById(reqObj);
    }
}

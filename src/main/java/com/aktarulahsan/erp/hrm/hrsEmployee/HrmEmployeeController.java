package com.aktarulahsan.erp.hrm.hrsEmployee;


import com.aktarulahsan.erp.tms.setting.category.CategoryService;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hrm/employee")
public class HrmEmployeeController {

    @Autowired
    HrmEmployeeService service;


    @GetMapping("/list")
    public Response getAll(@RequestBody(required = false) String reqObj) {
        return service.list(reqObj);
    }



    @PostMapping("/create")
    public Response create(@RequestBody String reqObj) {

        return service.save(reqObj);
    }

    @PutMapping("/update")
    public Response update(@RequestBody String reqObj) {
        return service.update(reqObj);
    }

    @DeleteMapping("/delete")
    public Response delete(@RequestParam("cusId") String reqId) {
        return service.delete(reqId);
    }

    @GetMapping("/findResponsePersone")
    public Response findResponsePersone(@RequestParam("department_code") String id) {
        return service.findResponsePersone(id);
    }


    @GetMapping("/findByDepartmentId")
    public Response findByDepartmentId(@RequestParam("department_code") String id) {
        return service.findByDepartmentId(id);
    }

    @GetMapping("/findByUser")
    public Response findByusername(@RequestParam("empCardNo") String reqObj) {
        return service.findByUser(reqObj);
    }
}

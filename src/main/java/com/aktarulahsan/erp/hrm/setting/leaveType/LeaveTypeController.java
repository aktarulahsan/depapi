package com.aktarulahsan.erp.hrm.setting.leaveType;


import com.aktarulahsan.erp.hrm.leave.LeaveService;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave/leaveConfig")
public class LeaveTypeController {

    @Autowired
    LeaveTypeService service;


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

    @GetMapping("/findById")
    public Response findorderById(@RequestParam("empCardNo") String id) {
        return service.findDetailsById(id);
    }
}

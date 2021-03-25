package com.aktarulahsan.erp.hrm.leave;


import com.aktarulahsan.erp.hrm.hrsEmployee.HrmEmployeeService;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave/leave")
public class LeaveController {

    @Autowired
    LeaveService service;


    @GetMapping("/list")
    public Response getAll(@RequestBody(required = false) String reqObj) {
        return service.list(reqObj);
    }



    @PostMapping("/create")
    public Response create(@RequestBody String reqObj) {

        return service.save(reqObj);
    }
    @PostMapping("/saveTour")
    public Response createTour(@RequestBody String reqObj) {

        return service.saveTour(reqObj);
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


    @GetMapping("/findTourById")
    public Response findTourById(@RequestParam("empCardNo") String id,@RequestParam("leave_id") String leaveId) {
        return service.findTourById(id, leaveId);
    }

    @GetMapping("/getLeaveInfo")
    public Response getLeaveInfo(@RequestParam("empCardNo") String id) {
        return service.getLeaveInfo(id);
    }

}

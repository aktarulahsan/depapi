package com.aktarulahsan.erp.hrm.tour;

import com.aktarulahsan.erp.hrm.leave.LeaveService;
import com.aktarulahsan.erp.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/leave/tour")
public class TourController {


    @Autowired
    TourService service;


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

    @GetMapping("/findTourListById")
    public Response findTourListById(@RequestParam("orderId") String id) {
        return service.findTourListById(id);
    }
}

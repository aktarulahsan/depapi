package com.aktarulahsan.erp.util;

public interface CommonMethod {

    public Response create(String regObj);
    public Response update(String regObj);
    public Response delete(String regObj);
    public Response findById(String regObj);
    public Object findObjectById(String regObj);

}

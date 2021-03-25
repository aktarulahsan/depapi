package com.aktarulahsan.erp.core.reportConfig.coreReport;


public class ReportPathAndName {
	
 private String pClient;
 private String pLayout;
 private String rPath;
 private String rName;
 private String rDescription;


	public ReportPathAndName(String pClien,String pLayout,String rPath,String rName) {
   	 this.pClient = pClien;
   	 this.pLayout = pLayout;
   	 this.rPath = rPath;
   	 this.rName = rName;
     }


	public ReportPathAndName(String pClien,String pLayout,String rPath,String rName, String rDescription) {
    	 this.pClient = pClien;
    	 this.pLayout = pLayout;
    	 this.rPath = rPath;
    	 this.rName = rName;
    	 this.rDescription = rDescription;
      }


	public String getpClient() {
		return pClient;
	}


	public void setpClient(String pClient) {
		this.pClient = pClient;
	}


	public String getpLayout() {
		return pLayout;
	}


	public void setpLayout(String pLayout) {
		this.pLayout = pLayout;
	}


	public String getrPath() {
		return rPath;
	}


	public void setrPath(String rPath) {
		this.rPath = rPath;
	}


	public String getrName() {
		return rName;
	}


	public void setrName(String rName) {
		this.rName = rName;
	}


	public String getrDescription() {
		return rDescription;
	}


	public void setrDescription(String rDescription) {
		this.rDescription = rDescription;
	}
 

}

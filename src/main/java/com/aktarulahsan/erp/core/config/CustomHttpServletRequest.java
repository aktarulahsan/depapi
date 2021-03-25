package com.aktarulahsan.erp.core.config;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustomHttpServletRequest implements Serializable{

	private static final long serialVersionUID = 2410847241220452549L;
	private final String remoteAddress;
    private final  String os;
	private  String browser;
	
	
	public CustomHttpServletRequest(HttpServletRequest request) {

		this.remoteAddress      = request.getRemoteAddr();
		String  browserDetails  =   request.getHeader("User-Agent");
        String  userAgent       =   browserDetails;
        String  user            =   userAgent.toLowerCase();



   
        //================= OS =======================
         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
         {
             this.os = "Windows";
         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
         {
        	 this.os = "Mac";
         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
         {
        	 this.os = "Unix";
         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
         {
        	 this.os = "Android";
         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
         {
        	 this.os = "IPhone";
         }else{
        	 this.os = "UnKnown, More-Info: "+userAgent;
         }
         //===============Browser===========================
        if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            this.browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
        	this.browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera"))
            	this.browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        else if(user.contains("opr"))
            	this.browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
        } else if (user.contains("chrome"))
        {
        	this.browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
        {
        	this.browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
        	this.browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
        	this.browser="IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
        } else
        {
        	this.browser = "UnKnown, More-Info: "+userAgent;
        }

		
	}

}
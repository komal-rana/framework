package Library.Generic;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;

import DriverScript.DriverScript;
public class GenericMethods {
	
	DriverScript ds=new DriverScript();
	 html_result hr=new html_result();
	
public void Fn_LaunchApp()
{
  ds.driver.get(ds.vProjectUrl);
  ds.driver.manage().window().maximize();
  ds.driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
}

public void Fn_verifyTitle(String vExp) throws Throwable
{
	String vActTitle=ds.driver.getTitle();
	if(vActTitle.trim().equals(vExp.trim()))
	{
		System.out.println("PASS");
		hr.fgInsertResult(ds.vResFilePath, "Fn_verifyTitle", vExp+" should be match successfully", "Title Matched successfully", "PASS");
	}
	else
	{
		System.out.println("FAIL");
		hr.fgInsertResult(ds.vResFilePath, "Fn_verifyTitle", vExp+" should be match successfully", "Title did not match successfully", "FAIL");
	}
}

public void Fn_verifyObjectExist(int vObjCnt,double vExpCnt) throws Throwable
{
	if(vObjCnt==vExpCnt)
	{
		  System.out.println("PASS");
		hr.fgInsertResult(ds.vResFilePath, "Fn_verifyObjectExist", "Object should be Exist", "Object exist", "PASS");
	}
	else
	{
		System.out.println("FAIL");
		hr.fgInsertResult(ds.vResFilePath, "Fn_verifyObjectExist", "Object should be Exist", "Object does not exist", "FAIL");
	}
}

public void Fn_SetVal(WebElement vObj, int vObjCnt,String vVal) throws Throwable 
{
	if(vObjCnt==1)
	{
		vObj.clear();
		vObj.sendKeys(vVal);
		Thread.sleep(1000);
		String vGetVal=vObj.getAttribute("value").trim();
		if(vGetVal.equals(vVal))
		{
		    System.out.println("PASS");
		    hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "Value "+vVal+" should be entered successfully", "Value Entered", "PASS");
		}
		else
		{
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_SetVal", "Value "+vVal+" should be entered successfully", "Value did not enter", "FAIL");
		}
	}
	else
	{
		System.out.println("FAIL");
	}
}

public void Fn_SelectVal(WebElement vObj, int vObjCnt,String vVal) throws Throwable 
{
	if(vObjCnt==1)
	{
		//vObj.clear();
		vObj.sendKeys(vVal);
		Thread.sleep(1000);
		String vGetVal=vObj.getAttribute("value");
		if(vGetVal.equals(vVal))
		{
			System.out.println("PASS");
		    hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value "+vVal+" should be selected successfully", "Value Selected", "PASS");
		}
		else
		{
			System.out.println("FAIL");
			hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value "+vVal+" should be selected successfully", "Value did not Select", "FAIL");
		}
	}
	else
	{
		System.out.println("FAIL");
		hr.fgInsertResult(ds.vResFilePath, "Fn_SelectVal", "Value "+vVal+" should be selected successfully", "Value did not Select", "FAIL");
	}
}


public void Fn_Click(WebElement vObj, int vObjCnt) throws Throwable 
{
	if(vObjCnt==1)
	{		
		vObj.click();
		System.out.println("PASS");
	    hr.fgInsertResult(ds.vResFilePath, "Fn_Click", "Object should be Clicked successfully", "Object Clicked", "PASS");
	}
	else
	{
		System.out.println("FAIL");
		hr.fgInsertResult(ds.vResFilePath, "Fn_Click", "Object should be Clicked successfully", "Object did not click", "FAIL");
	}
}

}

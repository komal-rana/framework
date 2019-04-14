package Library.Generic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import DriverScript.DriverScript;
import Library.Utility.Xls_Reader;

public class KeywordDriver {
	
	public WebElement vObj=null;
	public int vObjCnt=0;
	public double vExpCnt=0;
	public String ObjString,vExp,vVal;
	public double ExpCnt;
	
	GenericMethods gm=new GenericMethods();
	DriverScript dscript=new DriverScript();
	public KeywordDriver(String vActKeyword,Xls_Reader xrs,int m,String vModuleName) throws Throwable
	{
		Properties prop=new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\ObjectRepository\\Objects.properties");
		prop.load(fis);
		switch (vActKeyword.trim())
		{
		 case "Fn_LaunchApp":
			 gm.Fn_LaunchApp();
			 break;
		 case "Fn_verifyTitle":	
			 vExp=xrs.getCellData(vModuleName, "Param1", m);
			 gm.Fn_verifyTitle(vExp);
			 break;
		 case "Fn_verifyObjectExist":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			 vObj=ExtractObject(ObjString);
			 vObjCnt=ObjectCounter(ObjString);
			 try
			 {
			 ExpCnt=Double.parseDouble(xrs.getCellData(vModuleName, "Param1", m));
			 }catch(Throwable t)
			 {
				 System.out.println(t.getMessage());
			 }
			 if(ExpCnt>0)
			 {
				 vExpCnt=ExpCnt;
			 }
			 else
			 {
				 vExpCnt=1;
			 }
			 gm.Fn_verifyObjectExist(vObjCnt, vExpCnt);
			 break;
		 case "Fn_SetVal":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			 vObj=ExtractObject(ObjString);
			 vObjCnt=ObjectCounter(ObjString);	
			 vVal=xrs.getCellData(vModuleName, "Param1", m);
			 gm.Fn_SetVal(vObj, vObjCnt,vVal);
			 break;
		 case "Fn_SelectVal":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			 vObj=ExtractObject(ObjString);
			 vObjCnt=ObjectCounter(ObjString);	
			 vVal=xrs.getCellData(vModuleName, "Param1", m);
			 gm.Fn_SelectVal(vObj, vObjCnt,vVal);
			 break;
		 case "Fn_Click":
			 ObjString=prop.getProperty(xrs.getCellData(vModuleName, "Object1", m).trim());
			 vObj=ExtractObject(ObjString);
			 vObjCnt=ObjectCounter(ObjString);				 
			 gm.Fn_Click(vObj,vObjCnt);
			 break;
		 default:
			 System.out.println("Invalid Keyword");
		}		
		
	}
	
	public WebElement ExtractObject(String ObjString)
	{
		WebElement vObj = null;
		if(ObjString.length()>0)
		{
			String[] extract=ObjString.split("@@@");
			switch (extract[0])
			{
			case "xpath":
				vObj=dscript.driver.findElement(By.xpath(extract[1]));
				break;
			case "id":
				vObj=dscript.driver.findElement(By.id(extract[1]));
				break;
			case "name":
				vObj=dscript.driver.findElement(By.name(extract[1]));
				break;
			case "linkText":
				vObj=dscript.driver.findElement(By.linkText(extract[1]));
				break;
			case "cssSelector":
				vObj=dscript.driver.findElement(By.cssSelector(extract[1]));
				break;
			}
		}
		
		return vObj;
		
	}
	
	public int ObjectCounter(String ObjString)
	{
		int vObjCnt = 0;
		if(ObjString.length()>0)
		{
			String[] extract=ObjString.split("@@@");
			switch (extract[0])
			{
			case "xpath":
				vObjCnt=dscript.driver.findElements(By.xpath(extract[1])).size();
				break;
			case "id":
				vObjCnt=dscript.driver.findElements(By.id(extract[1])).size();
				break;
			case "name":
				vObjCnt=dscript.driver.findElements(By.name(extract[1])).size();
				break;
			case "linkText":
				vObjCnt=dscript.driver.findElements(By.linkText(extract[1])).size();
				break;
			case "cssSelector":
				vObjCnt=dscript.driver.findElements(By.cssSelector(extract[1])).size();
				break;
			}
		}
		
		return vObjCnt;
		
	}

}

package DriverScript;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import Library.Generic.KeywordDriver;
import Library.Generic.html_result;
import Library.Utility.Xls_Reader;

public class DriverScript {
	
	public static int vProjectRowCnt=0;
	public static WebDriver driver;
	public static String vProjectUrl,vTCName,vDescription,vResFilePath,vModuleName,vProjectName;
	public static int noofTC, passtc, failtc, passval, failval,failcnt;

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		String vRun,vProjectModuleFile,vProjectScenarioFile,vProjectTestDataFile,vDBName,vDBUser,vDBpassword,vBrowser,vModRun,vPriority,vTCRun,vTCPriority,vKeyword,vActKeyword;
		int vModuleRowCnt,vTCCnt,vScnCnt;
		System.out.println(System.getProperty("user.dir"));
		
		Xls_Reader xr=new Xls_Reader(System.getProperty("user.dir")+"\\src\\DriverFiles\\ProjectDriver.xlsx");
		vProjectRowCnt=xr.getRowCount("Projects");
		System.out.println(vProjectRowCnt);
		for(int i=2;i<=vProjectRowCnt;i++)
		{
			 vRun=xr.getCellData("Projects", "Run", i);
			 if(vRun.equalsIgnoreCase("ON"))
			 {
				 vProjectName=xr.getCellData("Projects", "ProjectName", i);
				 vProjectUrl=xr.getCellData("Projects", "ProjectUrl", i);
				 vProjectModuleFile=xr.getCellData("Projects", "ProjectModuleFile", i);
				 vProjectScenarioFile=xr.getCellData("Projects", "ProjectScenarioFile", i);
				 vProjectTestDataFile=xr.getCellData("Projects", "ProjectTestDataFile", i);
				 vDBName=xr.getCellData("Projects", "DBName", i);
				 vDBUser=xr.getCellData("Projects", "DBUser", i);
				 vDBpassword=xr.getCellData("Projects", "DBpassword", i);
				 vBrowser=xr.getCellData("Projects", "Browser", i);
				 System.out.println("==================================================");
				 System.out.println(vProjectName);
				 System.out.println("==================================================");
				 
				 //html result
				 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				 Date date = new Date();	
				 String startDate=dateFormat.format(date);
				 html_result hr=new html_result();
				 vResFilePath=hr.CreateResultFileAndPath(vProjectName);
				 hr.fg_OpenResultsFile(vResFilePath, vProjectName);
				 noofTC=0;
				 passtc=0;
				 failtc=0;
				 passval=0;
				 failval=0;
				 // Launch brpwser
				 driver=new FirefoxDriver();
				 
				 Xls_Reader xrm=new Xls_Reader(System.getProperty("user.dir")+"\\src\\DriverFiles\\"+vProjectModuleFile);
				 Xls_Reader xrs=new Xls_Reader(System.getProperty("user.dir")+"\\src\\Scenario\\"+vProjectScenarioFile);
				 Xls_Reader xres=new Xls_Reader(System.getProperty("user.dir")+"\\src\\Results\\ExcelResult.xlsx");
				 //switch to project sheet
				 vModuleRowCnt=xr.getRowCount(vProjectName);
				 for(int j=2;j<=vModuleRowCnt;j++)
				 {
					 vModRun=xr.getCellData(vProjectName, "Run", j); 
					 if(vModRun.equalsIgnoreCase("ON"))
					 {
						 vModuleName=xr.getCellData(vProjectName, "ModuleName", j); 
						 vPriority=xr.getCellData(vProjectName, "Priority", j); 
						 System.out.println(vModuleName);
						 
						 vTCCnt=xrm.getRowCount(vModuleName);
						 vScnCnt=xrs.getRowCount(vModuleName);
						 
						 for(int k=2;k<=vTCCnt;k++)
						 {
							 vTCRun=xrm.getCellData(vModuleName, "Run", k); 
							 if(vTCRun.equalsIgnoreCase("ON"))
							 {
								 int flag=0;
								 int TCRowNum=0;
								 failcnt=0;
								 vTCName=xrm.getCellData(vModuleName, "TCName", k);  
								 vTCPriority=xrm.getCellData(vModuleName, "Priority", k);
								 System.out.println(vTCName);
								 noofTC=noofTC+1;
								 for(int m=2;m<=vScnCnt;m++)
								 {
									 vKeyword=xrs.getCellData(vModuleName, "Keywords", m);  
									 if(vKeyword.trim().equals(vTCName.trim()))
									 {
										 flag=1;
										 vDescription=xrs.getCellData(vModuleName, "Keywords", m-1); 
										 TCRowNum=m;
										 hr.fgInsertStep(vResFilePath);
									 }
									 if((m>TCRowNum) && (flag==1))
									 {
										 if(vKeyword.contains("//"))
										 {
											 break;
										 }
										 else
										 {
											 vActKeyword=vKeyword.trim();
											 System.out.println(vActKeyword);
											 KeywordDriver kd=new KeywordDriver(vActKeyword, xrs, m, vModuleName);
										 }
									 }
								 }
								 if(failcnt>0)
								 {
									 failtc=failtc+1;
									 hr.ExcelResult(xres,"FAIL");
								 }
								 else
								 {
									 hr.ExcelResult(xres,"PASS");
								 }
							 }
						 }
						 
					 }
				 }
				 Date date1 = new Date();	
				 String endDate=dateFormat.format(date1);
				 long timeofexe=hr.timeDiffernce(startDate, endDate, dateFormat);
				 hr.fgCreateSummary(vResFilePath);
				 hr.fgInsertSummary(vResFilePath, noofTC, noofTC-failtc, failtc, passval, failval, timeofexe);
			     hr.fgCloseFile(vResFilePath);
			 }
		}
	}

}

package com.homeene.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.homeene.model.GrossProfit;

public class ReadExcel {
	//总行数
    private int totalRows = 0;  
    //总条数
    private int totalCells = 0; 
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ReadExcel(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;} 
    //获取总列数
    public int getTotalCells() {  return totalCells;} 
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }  
    
  /**
   * 读EXCEL文件，获取信息集合
   * @param fielName
   * @return
   */
	public List<GrossProfit> getExcelInfo(MultipartFile mFile) {
		String fileName = mFile.getOriginalFilename();//获取文件名
		List<GrossProfit> userList = new ArrayList<GrossProfit>();
		try {
			if (!validateExcel(fileName)) {// 验证文件名是否合格
				return null;
			}
			boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
			if (isExcel2007(fileName)) {
				isExcel2003 = false;
			}
			userList = createExcel(mFile.getInputStream(), isExcel2003);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
  
  /**
   * 根据excel里面的内容读取信息
   * @param is 输入流
   * @param isExcel2003 excel是2003还是2007版本
   * @return
 * @throws FileNotFoundException 
   * @throws IOException
   */
	public List<GrossProfit> createExcel(InputStream is, boolean isExcel2003) throws FileNotFoundException {
		List<GrossProfit> userList = new ArrayList<GrossProfit>();
		try{
			Workbook wb = null;
			if (isExcel2003) {// 当excel是2003时,创建excel2003
				wb = new HSSFWorkbook(is);
			} else {// 当excel是2007时,创建excel2007
				wb = new XSSFWorkbook(is);
			}
			userList =	readExcelValue(wb);// 读取Excel里面客户的信息
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userList;
	}
  
  /**
   * 读取Excel里面客户的信息
   * @param wb
   * @return
   */
	private List<GrossProfit> readExcelValue(Workbook wb) {
		// 得到第一个shell
		Sheet sheet = wb.getSheetAt(0);
		// 得到Excel的行数
		this.totalRows = sheet.getPhysicalNumberOfRows();
		// 得到Excel的列数(前提是有行数)
		if (totalRows > 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		List<GrossProfit> mdList = new ArrayList<GrossProfit>();
		// 循环Excel行数
		for (int r = 1; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null){
				continue;
			}
			GrossProfit md = new GrossProfit();
			// 循环Excel的列
			String type="0";
			for (int c = 1; c < this.totalCells; c++) {
				Cell cell = row.getCell(c);
				if (null != cell) {
					if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING) {
						String name=cell.getStringCellValue();
						switch(name) {
							case "本年实际数":type="0";break;
							case "上年同期数":type="1";break;
							case "本年预算":type="2";break;
						}
					}else {
						Integer money=(int) cell.getNumericCellValue();
						switch (c) {
						case 2:md.setJanuary(money);break;
						case 3:md.setFebruary(money);break;
						case 4:md.setMarch(money);break;
						case 5:md.setApril(money);break;
						case 6:md.setMay(money);break;
						case 7:md.setJune(money);break;
						case 8:md.setJunly(money);break;
						case 9:md.setAugust(money);break;
						case 10:md.setSeptember(money);break;
						case 11:md.setOctober(money);break;
						case 12:md.setNovember(money);break;
						case 13:md.setDecember(money);break;
						default:
							md.setType(type);
							LocalDate today = LocalDate.now(); 
							md.setYear(today.getYear()+"");
							break;
						}
						
						}
					}
				
				}
			mdList.add(md);	
			}
			// 添加到list
		  mdList.forEach(e->System.out.println(" 一月份："+e.getJanuary()+" 二月份："+e.getFebruary()+" 三月份："+e.getMarch()+" type："+e.getType()));	
		return mdList;
	}
	
	public float fomartFloat(String money){
		if(StringUtils.isEmpty(money)){
			return 0;
		}
		float f = Float.parseFloat(money);
		return f;
	}
	
	/**       
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */      
	public boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			errorMsg = "文件名不是excel格式";
			return false;
		}
		return true;
	}
	
	// @描述：是否是2003的excel，返回true是2003 
    public static boolean isExcel2003(String filePath)  {  
         return filePath.matches("^.+\\.(?i)(xls)$");  
     }  
   
    //@描述：是否是2007的excel，返回true是2007 
    public static boolean isExcel2007(String filePath)  {  
         return filePath.matches("^.+\\.(?i)(xlsx)$");  
     }
    
    public static void main(String[] args) throws FileNotFoundException {
    	System.out.println("start");
    	ReadExcel readExcel = new ReadExcel();
		// 解析excel，获取上传的事件单
    	InputStream is=new FileInputStream(new File("d://homeene//bi.xlsx"));
		List<GrossProfit> resultList = readExcel.createExcel(is, false);
	}
}

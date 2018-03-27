package com.bedrock.origin.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;

import com.bedrock.origin.annotation.ExcelIndex;
import com.bedrock.origin.annotation.Validate;
import com.bedrock.origin.annotation.ValidateType;

/**
 * excel帮助类
 * @author liuxiangtao90
 *
 */
public  class ExcelHelper {
	
	/**
	 * 批量导出
	 * @param request
	 * @param response
	 * @param list
	 * @param sheetName
	 * @param fileName
	 */
	public static <T> void exportExcelList(HttpServletRequest request,HttpServletResponse response,List<T> list,String sheetName,String fileName)
	{
		HSSFWorkbook  workbook = new HSSFWorkbook(); 
		try 
		{ 
			workbook.createSheet(sheetName);
	    	int rowNum=0;
	    	HSSFSheet  hssfShet=workbook.getSheet(sheetName);
	        HSSFRow row = hssfShet.createRow(rowNum++);
			// 处理表头行  无可导出数据在前台校验
	        // Field[] field1=list.getClass().getGenericSuperclass().getClass().getDeclaredFields();
			Field[] field= list.get(0).getClass().getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				ExcelIndex ei=field[i].getAnnotation(ExcelIndex.class);
				if(ei==null)
					continue;
				HSSFCell cell=row.createCell(ei.index());
				// 这个地方 导入导出耦合了 以后完善
				Validate v=field[i].getAnnotation(Validate.class);
				if(v==null)
				{
					cell.setCellValue(ei.name());
					continue;
				}
				cell.setCellValue(v.name());
			}
			
			// 处理数据行
			for (int k = 0; k < list.size(); k++) {
				HSSFRow rowtemp=hssfShet.createRow(rowNum++);
				Field[] fieldTemp= list.get(k).getClass().getDeclaredFields();
				for (int i = 0; i < field.length; i++) {
					try
					{
						ExcelIndex ei=fieldTemp[i].getAnnotation(ExcelIndex.class);
						if(ei==null)
						{
							continue;
						}
						HSSFCell cell=rowtemp.createCell(ei.index());
		
						String name = fieldTemp[i].getName().substring(0, 1).toUpperCase() + fieldTemp[i].getName().substring(1);
						Method m = list.get(k).getClass().getMethod("get" + name);
						//Class<?> type= m.getReturnType();
						Object value= m.invoke(list.get(k));
						if(ei.type().equals(ValidateType.TIMESTAMP))
						{
							value=TimeUtil.timeStamp2Str(TimeUtil.DAY1, Integer.parseInt(value+""));
						}
						cell.setCellValue(value+"");
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			response.reset();
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName="
					+ URLEncoder.encode(fileName+".xls", "utf-8"));// 设置文件名
			workbook.write(response.getOutputStream());
		} 
		catch (Exception e5) 
		{
			e5.printStackTrace();
		}
		finally
		{
			if(workbook!=null)
			{
				try 
				{
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 导出Excel模板
	 * @param request
	 * @param response
	 */
	public static void exportExcel (HttpServletRequest request,HttpServletResponse response,String[] titleRow,String sheetName,String fileName)
	{
		FileInputStream fis = null;
		OutputStream os = null;
		String path = request.getSession().getServletContext().getRealPath("")
				+ "/"+fileName+".xls";
		File file = new File(path);
		if(!file.exists())
		{
			HSSFWorkbook  workbook = new HSSFWorkbook(); 
			FileOutputStream out = null;  
			try 
			{
		        workbook.createSheet(sheetName);    
	        	int rowNum=0;
	            HSSFRow row = workbook.getSheet(sheetName).createRow(rowNum++);    //创建第一行    
	            for(short i = 0;i < titleRow.length;i++){  
	                HSSFCell cell = row.createCell(i);  
	                cell.setCellValue(titleRow[i]);  
	            }
	            out = new FileOutputStream(path);  
	            workbook.write(out);  
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			finally 
			{
				try 
				{
					if(workbook!=null)
					{
						workbook.close();
					}
					if(out!=null)
					{
						out.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try
		{
			response.reset();
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition", "attachment;fileName="
					+ URLEncoder.encode(fileName+".xls", "utf-8"));// 设置文件名
			File fileTemp = new File(path);
			fis = new FileInputStream(fileTemp);
			os = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int i;
			while ((i = fis.read(buffer)) != -1) {
				os.write(buffer);
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		finally
		{
			try
			{
				if(fis!=null)
				{
					fis.close();
				}
				if(os!=null)
				{
					os.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
    /** 
     * 创建新excel. 
     * @param fileDir  excel的路径 
     * @param sheetName 要创建的表格索引 
     * @param titleRow excel的第一行即表格头 
     */  
    public  static<T> void createExcel(String fileDir,String sheetName,String titleRow[],String[] column,List<T> t)throws Exception{  
        //创建workbook  
        HSSFWorkbook  workbook = new HSSFWorkbook();  
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
        HSSFSheet sheet1 = workbook.createSheet(sheetName);    
        //新建文件  
        FileOutputStream out = null;  
        try {  
        	int rowNum=0;
            //添加表头  
            HSSFRow row = workbook.getSheet(sheetName).createRow(rowNum++);    //创建第一行    
            for(short i = 0;i < titleRow.length;i++){  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleRow[i]);  
            }
            for(int beanIndex=0;beanIndex<t.size();beanIndex++)
            {
            	row = workbook.getSheet(sheetName).createRow(rowNum++);
            	for(int cIndex=0;cIndex<column.length;cIndex++)
            	{
            		T beanClass=t.get(beanIndex);
            		String name = column[cIndex].substring(0, 1).toUpperCase() + column[cIndex].substring(1);
            		Method m = beanClass.getClass().getMethod("get" + name);
            		Object value = m.invoke(beanClass); // 调用getter方法获取属性值
                	HSSFCell cell = row.createCell(cIndex);  
                    cell.setCellValue(value+"");  
            	}
            }	
            out = new FileOutputStream(fileDir);  
            workbook.write(out);  
        } catch (Exception e) {  
            throw e;
        } finally {    
            try {    
                out.close();    
			} catch (IOException e) {    
                e.printStackTrace();  
            }    
        }    
    }
    
    /** 
     * 删除文件. 
     * @param fileDir  文件路径 
     */  
    public static boolean deleteExcel(String fileDir) {  
        boolean flag = false;  
        File file = new File(fileDir);  
        // 判断目录或文件是否存在    
        if (!file.exists()) {  // 不存在返回 false    
            return flag;    
        } else {    
            // 判断是否为文件    
            if (file.isFile()) {  // 为文件时调用删除文件方法    
                file.delete();  
                flag = true;  
            }   
        }  
        return flag;  
    }  
    
    /**
     * Excel 批量导入
     * @param inputStreim
     * @param bean
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     * @throws SecurityException
     * @throws ClassNotFoundException
     */
    public static<T> List<T> ImportExcel(InputStream inputStreim,Class<T> bean) throws 
    InstantiationException, IllegalAccessException, IllegalArgumentException, 
    InvocationTargetException, IntrospectionException, SecurityException, ClassNotFoundException
    {
    	 HSSFWorkbook wb=null;
    	 try 
    	 {  
    		 POIFSFileSystem fs = new POIFSFileSystem(inputStreim);  
    		 wb = new HSSFWorkbook(fs);
    		 
         } catch (IOException e) {  
             e.printStackTrace();  
         }  
    	 HSSFSheet sheet = wb.getSheetAt(0);  
    	 HSSFRow row = sheet.getRow(0); 
    	 int rowNum = sheet.getLastRowNum()+1;
    	 List<T> list=new ArrayList<T>(rowNum);
         // 标题总列数  
         Field[] filed=bean.getDeclaredFields();
         Map<Integer, Field> map=new HashMap<Integer, Field>();
         for (int i = 0; i < filed.length; i++) 
         {
        	 ExcelIndex excelindex= filed[i].getAnnotation(ExcelIndex.class);
        	 if ( excelindex != null ) {
        		 int index= excelindex.index();
        		 map.put(index, filed[i]);
             }
		 }
         for(int i=1;i<rowNum;i++)
         {
        	 T t=bean.newInstance();
        	 for(int j=0;j<map.size();j++)
        	 {
        		 // String fieldType=map.get(j).getType().getSimpleName();
        		 PropertyDescriptor pd = new PropertyDescriptor(map.get(j).getName(), bean);
        		 Method method = pd.getWriteMethod();
        		 HSSFCell cell=sheet.getRow(i).getCell(j);
        		 Object obj;
        		 // 日期
        		 if(cell.getCellType()==0&&DateUtil.isCellDateFormatted(cell))
        		 {
			         //用于转化为日期格式
			         Date d = cell.getDateCellValue();
			         obj=TimeUtil.format(TimeUtil.DAY1, d);
        		 }
        		 else
        		 {
        			 obj=cell+"";
        		 }
        		 // 全部插入string类型  获取list再校验必填行及数据格式问题
                 method.invoke(t, obj+"");
        	 }
        	 list.add(t);
         }
         try 
         {
        	 if(wb!=null)
        	 {
        		 wb.close();
        	 }
        	 if(inputStreim!=null)
        	 {
        		 inputStreim.close();
        	 }
		}
        catch (IOException e)
        {
			e.printStackTrace();
		}
    	return list;
    }
    
}
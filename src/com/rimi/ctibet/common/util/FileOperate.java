package com.rimi.ctibet.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author zl
 * 
 */
public class FileOperate {
	private String message;

	private String filespara = "/";


	public FileOperate() {
	}

	public String readTxt(String filePathAndName) {
		return readTxt(filePathAndName, "UTF-8");
	}

	public String readTxt(String filePathAndName, String encoding) {
		encoding = encoding.trim();
		StringBuffer str = new StringBuffer("");
		String st = "";
		try {
			FileInputStream fs = new FileInputStream(filePathAndName);
			InputStreamReader isr;
			if (encoding.equals("")) {
				isr = new InputStreamReader(fs);
			} else {
				isr = new InputStreamReader(fs, encoding);
			}
			BufferedReader br = new BufferedReader(isr);
			try {
				String data = "";
				while ((data = br.readLine()) != null) {
					str.append(data + "\n");
				}
			} catch (Exception e) {
				str.append(e.toString());
			} finally {
				br.close();
				isr.close();
			}
			st = str.toString();
		} catch (IOException es) {
			st = "";
		}
		return st;
	}

	public void writeTxt(String path, String filename, String fileContent) {
		writeTxt(path, filename, fileContent, "UTF-8");

	}

	public void writeTxt(String path, String filename, String fileContent, String encoding) {
		PrintWriter pwrite = null;
		try {
			File file = new File(path);
			if (StringUtils.isNotBlank(filename)) {
				if (!file.exists()) {
					file.mkdirs();
				}
				file = new File(path + filespara + filename);
			}

			if (encoding != null && !"".equals(encoding)) {
				pwrite = new PrintWriter(file, encoding);
			} else {
				pwrite = new PrintWriter(file);
			}
			pwrite.println(fileContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pwrite != null) {
				pwrite.close();
			}
		}
	}
	public void writeAppendTxt(String path, String filename, String fileContent){
		FileWriter pwrite = null;
		try {
				File dirFile=new File(path);
				if(!dirFile.exists()){
					dirFile.mkdir();
				}
				pwrite = new FileWriter(path+filespara+filename,true);
				pwrite.write(fileContent);
				pwrite.write("\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pwrite != null) {
				try {
					pwrite.flush();
					pwrite.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public void copyDir(String sourceDir, String targetDir) throws Exception {
		String url1 = sourceDir;
		String url2 = targetDir;
		if (!(new File(url2)).exists()) {
			(new File(url2)).mkdirs();
		}
		File[] file = (new File(url1)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				file[i].toString();
				FileInputStream input = new FileInputStream(file[i]);
				FileOutputStream output = new FileOutputStream(url2 + "/" + (file[i].getName()).toString());
				byte[] b = new byte[1024 * 5];
				int len;
				while ((len = input.read(b)) != -1) {
					output.write(b, 0, len);
				}
				output.flush();
				output.close();
				input.close();
			} else if (file[i].isDirectory()) {
				String url_2_dir = url2 + "/" + file[i].getName();
				copyDir(file[i].getPath(), url_2_dir);
			}
		}
	}

	public String createFolder(String folderPath) {
		String txt = folderPath;
		try {
			java.io.File myFilePath = new java.io.File(txt);
			txt = folderPath;
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			message = " 错误 ";
		}
		return txt;
	}

	public boolean delFile(String filePathAndName) {
		boolean bea = false;
		try {
			String filePath = filePathAndName;
			File myDelFile = new File(filePath);
			if (myDelFile.exists()) {
				myDelFile.delete();
				bea = true;
			} else {
				bea = false;
				message = (filePathAndName);
			}
		} catch (Exception e) {
			message = e.toString();
		}
		return bea;
	}

	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			message = ("");
		}
	}

	public boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
				bea = true;
			}
		}
		return bea;
	}

	public String copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			if (oldfile.exists()) {
				InputStream inStream = new FileInputStream(oldPathFile);
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread;

					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
			return "";
		} catch (Exception e) {
			return ("错误") + e.getMessage();
		}
	}

	public void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	public void moveFolder(String sourceDir, String targetDir) throws Exception {
		copyDir(sourceDir, targetDir);
		delFolder(sourceDir);
	}

	public String getMessage() {
		return this.message;
	}


	public String getStringFileCurrentPath(String id) {
		String strPath = "";
		String sfolder_year = id.substring(0, 4);
		String sfolder_month = id.substring(4, 6);
		strPath = "/updownFiles/" + sfolder_year + sfolder_month + "/";
		return strPath;
	}

	public String getFileByPathId(String pathFile, String encoding) {
		File myFile = new File(pathFile);
		if (myFile.exists())
			return new FileOperate().readTxt(pathFile, encoding);
		else
			return "";
	}

	public void saveFileByPathId(String path, String id, String nr) {
		new FileOperate().writeTxt(path, id + ".txt", nr);
	}


	public void delFileByPathId(String pathFile) {
		File myFile = new File(pathFile);
		if (myFile.exists())
			new FileOperate().delFile(pathFile);
	}

	  
	/**
	 * 解压zip文件
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList ectract(String sZipPathFile, String sDestPath) throws Exception {
		ArrayList allFileName = new ArrayList();
		File f = new File(sZipPathFile);
		if (!f.exists() || !f.isFile()) {
			return null;
		}
		// 为空表示在当前目录下解压
		if (StringUtils.isBlank(sDestPath)) {
			sDestPath = f.getParent();
		}
		FileInputStream fins = new FileInputStream(sZipPathFile);
		ZipInputStream zins = new ZipInputStream(fins);
		ZipEntry ze = null;
		byte ch[] = new byte[1024];
		while ((ze = zins.getNextEntry()) != null) {
			File zfile = new File(sDestPath + "/" + ze.getName());
			File fpath = new File(zfile.getParentFile().getPath());
			if (ze.isDirectory()) {
				if (!zfile.exists())
					zfile.mkdirs();
				zins.closeEntry();
			} else {
				if (!fpath.exists())
					fpath.mkdirs();
				FileOutputStream fouts = new FileOutputStream(zfile);
				int i;
				allFileName.add(zfile.getAbsolutePath());
				while ((i = zins.read(ch)) != -1)
					fouts.write(ch, 0, i);
				zins.closeEntry();
				fouts.close();
			}
		}
		fins.close();
		zins.close();

		return allFileName;
	}

	/**
	 * 将HTTP资源另存为文件
	 * 
	 * @param destUrl
	 *            String
	 * @param fileName
	 *            String
	 * @throws Exception
	 */
	public static void saveToFile(String destUrl, String fileName) throws IOException {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[1024];
		int size = 0;
		// 建立链接
		url = new URL(destUrl);
		httpUrl = (HttpURLConnection) url.openConnection();
		// 连接指定的资源
		httpUrl.connect();
		// 获取网络输入流
		bis = new BufferedInputStream(httpUrl.getInputStream());
		// 建立文件
		fos = new FileOutputStream(fileName);

		// 保存文件
		while ((size = bis.read(buf)) != -1)
			fos.write(buf, 0, size);
		fos.close();
		bis.close();
		httpUrl.disconnect();
	}
	
	/**
	 * isDot=true 返回扩展名加上 .
	 * @param filename
	 * @param isDot
	 * @return
	 */
	public static String getFileExt(String filename, boolean isDot) {
		String ext = null;
		if ((filename != null) && (filename.length() > 0)) {
			int i = filename.lastIndexOf('.');

			if ((i > 0) && (i < (filename.length() - 1))) {
				ext = filename.substring(i + 1);
			}
		}
		if(ext!=null && !ext.equals("") && isDot){
			ext = "." + ext;
		}
		return ext;
	}
	/**
	 * 解析2003 Excel  xls后缀
	* @Title: exceltoListPoiByXls 
	* @Description: TODO
	* @param @param sheetIndex
	* @param @param fileInputStream
	* @param @param mToCol
	* @param @return
	* @param @throws FileNotFoundException
	* @param @throws IOException 
	* @return List<Map<String,String>>
	* @throws
	 */
	public static List<Map<String, String>> exceltoListPoiByXls(int sheetIndex,
			InputStream fileInputStream,Map mToCol) throws FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		HSSFSheet sheet = workbook.getSheetAt(sheetIndex);// 按索引获取sheet引用 
		int start = 1;  //sheet起始行索引   
		int end = sheet.getLastRowNum();//sheet起结束行索引  
		HSSFRow row = null ;   
		HSSFRow startrow = sheet.getRow(0);//获取第N行表格数据
		List list = new ArrayList();
		while(start <= end){  //开始行 <= 结束行
			  row =  sheet.getRow(start);//获取第N行表格数据
			  if(row==null){
				  start++;
				  continue;
			  }
			  short cels=row.getFirstCellNum();
			  if(row.getCell((short)0)==null && row.getCell((short)1)==null){	
				  start++;
				  continue;
			  }
			  Map m = new HashMap();
			  int tmp=0;
			  if(startrow.getLastCellNum()<=row.getLastCellNum()){
				  tmp=startrow.getLastCellNum();
			  }else{
				  tmp=row.getLastCellNum();
			  }
			  while(cels<tmp){	//开始单元格 < 结束单元格
				  if(row.getCell(cels)==null || startrow.getCell(cels)==null){	
					  cels++;
					  continue;
				  }
				  String CellTitle = mToCol.get(StringUtils.trim(startrow.getCell(cels).getStringCellValue()))+"";
				  if(row.getCell(cels).getCellType()==0){
						 m.put(mToCol.get(StringUtils.trim(startrow.getCell(cels).getStringCellValue())), getDstr(StringUtils
									.trim(row.getCell(cels).getNumericCellValue()+"")));
				  }else{
						 m.put(mToCol.get(StringUtils.trim(startrow.getCell(cels).getStringCellValue())), StringUtils
									.trim(row.getCell(cels).getStringCellValue()));
				  }
				  cels++;
			  }
			  if(!m.isEmpty()){
				  list.add(m);
			  }
			  start++;
		}
		fileInputStream.close();
		return list;
	}
	
	/**
	 * 解析2007 版Excel  xlsx 为后缀
	* @Title: exceltoListPoiByXlsx 
	* @Description: TODO
	* @param @param sheetIndex
	* @param @param fileInputStream
	* @param @param mToCol
	* @param @return
	* @param @throws FileNotFoundException
	* @param @throws IOException 
	* @return List<Map<String,String>>
	* @throws
	 */
	public static List<Map<String, String>> exceltoListPoiByXlsx(int sheetIndex,
			InputStream fileInputStream,Map mToCol) throws FileNotFoundException, IOException {
		XSSFWorkbook  workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(sheetIndex);// 按索引获取sheet引用 
		int start = 1;  //sheet起始行索引   
		int end = sheet.getLastRowNum();//sheet起结束行索引  
		XSSFRow row = null ;   
	 
		XSSFRow startrow = sheet.getRow(0);//获取第N行表格数据
		List list = new ArrayList();
		while(start <= end){  //开始行 <= 结束行
			  row =  sheet.getRow(start);//获取第N行表格数据
			  if(row==null){
				  start++;
				  continue;
			  }
			  short cels=row.getFirstCellNum();
			  if(row.getCell((short)0)==null && row.getCell((short)1)==null){	
				  start++;
				  continue;
			  }
			  Map m = new HashMap();
			  int tmp=0;
			  if(startrow.getLastCellNum()<=row.getLastCellNum()){
				  tmp=startrow.getLastCellNum();
			  }else{
				  tmp=row.getLastCellNum();
			  }
			  while(cels<tmp){	//开始单元格 < 结束单元格
				  if(row.getCell(cels)==null || startrow.getCell(cels)==null){	
					  cels++;
					  continue;
				  }
				  String CellTitle = mToCol.get(StringUtils.trim(startrow.getCell(cels).getStringCellValue()))+"";
				  if(row.getCell(cels).getCellType()==0){
						 m.put(mToCol.get(StringUtils.trim(startrow.getCell(cels).getStringCellValue())), getDstr(StringUtils
									.trim(row.getCell(cels).getNumericCellValue()+"")));
				  }else{
						 m.put(mToCol.get(StringUtils.trim(startrow.getCell(cels).getStringCellValue())), StringUtils
									.trim(row.getCell(cels).getStringCellValue()));
				  }
				  cels++;
			  }
			  if(!m.isEmpty()){
				  list.add(m);
			  }
			  start++;
		}
		fileInputStream.close();
		return list;
	}
	
    private  static String getDstr(String s) {
        try {
          Double d = new Double(s);
          java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
          nf.setGroupingUsed(false);
          return nf.format(d);
        }
        catch (Exception es) {
          return "";
        }
      }
    
    /**
     * 简单的行级导出excel
    * @Title: exportExcelForList 
    * @Description: TODO
    * @param @param out 需要输出的流
    * @param @param titleList   标题集合 格式List<"column,title,columnWidth">
    * @param @param resultMap    结果数据集合
    * @return void
    * @throws
     */
    public  static void exportExcelForList(OutputStream out,List<String> titleList,List<Map> resultList){
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet sheet = workbook.createSheet(); //产生工作表对象
    	//创建一行 设置标题
    	HSSFRow row0 = sheet.createRow(0);  
    	//创建第一行的 每一列
    	
    	String[] columns = new String[titleList.size()];
    	for(int i = 0;i<titleList.size();i++){
    		   
    		String[] titles = titleList.get(i).split(",");
    		columns[i] = titles[0];
    		HSSFCell cell = row0.createCell(i);  
    		sheet.setColumnWidth(i, Integer.valueOf(titles[2]));  //设置列宽
    	    cell.setCellValue(new HSSFRichTextString(titles[1]));//设置title   
    	}
    	
    	//创建每一行
    	for(int i=0;i<resultList.size();i++){
    		HSSFRow row = sheet.createRow(i+1);  //创建行
    		//创建每一列的数据
    		for(int k = 0;k <columns.length;k++){
    			String value = resultList.get(i).get(columns[k])+"";
    			HSSFCell cell = row.createCell(k);  //创建列
    			cell.setCellValue(new HSSFRichTextString(value));
    		}
    	}
    	try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}   
    }
    /**
     * 简单的行级导出excel
    * @Title: exportExcelForList 
    * @Description: TODO
    * @param @param out 需要输出的流
    * @param @param titleList   标题集合 格式List<"column,title,columnWidth">
    * @param @param resultMap    结果数据集合
    * @return void
    * @throws
     */
    public  static void exportExcelForListMap(OutputStream out,List<String> titleList,List<Map<String, Object>> resultList){
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFSheet sheet = workbook.createSheet(); //产生工作表对象
    	//创建一行 设置标题
    	HSSFRow row0 = sheet.createRow(0);  
    	//创建第一行的 每一列
    	
    	String[] columns = new String[titleList.size()];
    	for(int i = 0;i<titleList.size();i++){
    		   
    		String[] titles = titleList.get(i).split(",");
    		columns[i] = titles[0];
    		HSSFCell cell = row0.createCell(i);  
    		sheet.setColumnWidth(i, Integer.valueOf(titles[2]));  //设置列宽
    	    cell.setCellValue(new HSSFRichTextString(titles[1]));//设置title   
    	}
    	
    	//创建每一行
    	for(int i=0;i<resultList.size();i++){
    		HSSFRow row = sheet.createRow(i+1);  //创建行
    		//创建每一列的数据
    		for(int k = 0;k <columns.length;k++){
    			String value = resultList.get(i).get(columns[k])+"";
    			if(value == null || value.equals("null")){
    				value = "";
    			}
    			HSSFCell cell = row.createCell(k);  //创建列
    			cell.setCellValue(new HSSFRichTextString(value));
    		}
    	}
    	try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}   
    }
   
}
     
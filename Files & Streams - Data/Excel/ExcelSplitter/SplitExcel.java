import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SplitExcel {
	
	private static Integer onMemoryFiles;
	private static String newFileName;
	private static String newPath;
	private static Integer maxRows;
	private static Integer numFichero;
	private static String result;
	
	

	
	public static String splitExcelFile (String path, String newFileName, String newPath, Integer maxRows) {
		SplitExcel.newFileName = newFileName;
		SplitExcel.newPath = newPath;
		SplitExcel.maxRows = maxRows;
		SplitExcel.numFichero = 0;
		SplitExcel.onMemoryFiles = 100;
		SplitExcel.result = "";
		splitExcel(path);
		
		return SplitExcel.result;
	}
	
	
	
	/**
	 * <PRE>
	 * Nombre del método: splitExcel
	 * Código de proyecto: INCDSG
	 * SplitExcel 
	 * Caso de uso: <rellenar> 
	 * Descripción: 
	 *	<rellenar>
	 * Fecha de creación: 21/05/2019
	 * </PRE>
	 * @author jpardinas
	 * @param newPath
	 */
	private static void splitExcel(String path) {
		
		numFichero = 0;
		
		try {
			File file = new File(path);
			OPCPackage opcPackage = OPCPackage.open(file.getAbsolutePath());
			XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			/* Only split if there are more rows than the desired amount. */
            if (sheet.getPhysicalNumberOfRows() >= maxRows) {
                splitWorkbook(workbook);
            }
            opcPackage.close();
			
		} catch (InvalidFormatException e) {
			SplitExcel.result = "Error getting file, path format?";
		} catch (FileNotFoundException e) {
			SplitExcel.result = "File not found";
		} catch (IOException e) {
			SplitExcel.result = "XSSFWorkbook opcPackage not supported";
		} catch (IllegalArgumentException e) {
			SplitExcel.result = "Format Values Error";
		} catch (IllegalStateException e) {
			SplitExcel.result = "File not found";
		} catch (Exception e) {
			SplitExcel.result = "Unknow error, check values";
		}
		
	}
	
	
	
	
	/**
	 * <PRE>
	 * Nombre del método: splitWorkbook
	 * Código de proyecto: INCDSG
	 * SplitExcel 
	 * Caso de uso: <rellenar> 
	 * Descripción: 
	 *	<rellenar>
	 * Fecha de creación: 21/05/2019
	 * </PRE>
	 * @author jpardinas
	 * @param workbook
	 */
	private static void splitWorkbook(XSSFWorkbook workbook) {
		
		//Nuevo WorkBook - SXSS
        SXSSFWorkbook wb = new SXSSFWorkbook(SplitExcel.onMemoryFiles);
        //Hoja WorkBook
        SXSSFSheet sh = (SXSSFSheet) wb.createSheet();
        //Row y Celdas Work
        SXSSFRow newRow;
        SXSSFCell newCell;

        //WorkBook recibido XSSF -> Hoja y Celda
        XSSFSheet sheet = workbook.getSheetAt(0);
        //Numero de columnas
        int rowSize = sheet.getRow(0).getLastCellNum();
        
        
        //Tamaño de las columnas
        List<Integer> cellWidth = new ArrayList<Integer>();
        //Textos del header del archivo
        List<String> headerString = new ArrayList<String>();
        
        //Estilos de los headers y de la información de las columnas
        List<XSSFCellStyle> headerCellStyles = new ArrayList<XSSFCellStyle>();
        List<XSSFCellStyle> dataCellStyles = new ArrayList<XSSFCellStyle>();
        
        
        //Seteamos informacion
        for (int a = 0; a < rowSize; a++) {
        	//Tamaño y String headers
        	cellWidth.add(sheet.getColumnWidth(a));
        	headerString.add(sheet.getRow(0).getCell(a).getStringCellValue());
        	
        	//Creamos un estilo por defecto
        	XSSFCellStyle headerStyle = (XSSFCellStyle) wb.createCellStyle();
        	//Clonamos y añadimos a la lista el estilo del workbook importado
        	headerStyle.cloneStyleFrom(sheet.getRow(0).getCell(a).getCellStyle());
        	headerCellStyles.add(headerStyle);
        	
        	//Creamos un estilo por defecto
        	XSSFCellStyle dataStyle = (XSSFCellStyle) wb.createCellStyle();
        	//Clonamos y añadimos a la lista el estilo del workbook importado
        	try {
        		dataStyle.cloneStyleFrom(sheet.getRow(1).getCell(a).getCellStyle());
			} catch (Exception e) {
				//Si esta a null creamos un estilo de texto por defecto
				DataFormat fmt = wb.createDataFormat();
				dataStyle.setDataFormat(fmt.getFormat("@"));
			}
        	dataCellStyles.add(dataStyle);
        }
        
        
        //Boolean para crear los textos de columnas en un nuevo archivo
        boolean nuevoLibro = false;
        
        
        //Numero de filas y columnas en el archivo
        int rowCount = 0;
        int colCount = 0;
        

        for (int z = 0; z <= (sheet.getLastRowNum() + 1); z++) {
        		

        	//Si se alcanza el numero de filas maximo
            if (rowCount == maxRows || z > (sheet.getLastRowNum())) {

            	//Seteamos el tamaño de las columnas
            	for (int a = 0; a < rowSize; a++) {
            		sh.setColumnWidth(a, cellWidth.get(a));
            	}

            	//Creamos el archivo y liberamos
            	writeWorkBooks(wb);
                wb.dispose();
                
                //Inicializamos la información
            	wb = new SXSSFWorkbook(SplitExcel.onMemoryFiles);
            	sh = (SXSSFSheet) wb.createSheet();
            	nuevoLibro = true;
            	rowCount = 0;
            	colCount = 0;
            	
            	
            	headerCellStyles = new ArrayList<XSSFCellStyle>();
                dataCellStyles = new ArrayList<XSSFCellStyle>();
            	
                /* Al setear un nuevo workbook perdemos los estilos guardados previamente, los reinsertamos*/
            	//Seteamos informacion
                if (!(z == (sheet.getLastRowNum() + 1))) {
                	
                	for (int a = 0; a < rowSize; a++) {

                    	//Creamos un estilo por defecto
                    	XSSFCellStyle headerStyle = (XSSFCellStyle) wb.createCellStyle();
                    	//Clonamos y añadimos a la lista el estilo del workbook importado
                    	headerStyle.cloneStyleFrom(sheet.getRow(0).getCell(a).getCellStyle());
                    	headerCellStyles.add(headerStyle);
                    	
                    	//Creamos un estilo por defecto
                    	XSSFCellStyle dataStyle = (XSSFCellStyle) wb.createCellStyle();
                    	//Clonamos y añadimos a la lista el estilo del workbook importado
                    	try {
                    		dataStyle.cloneStyleFrom(sheet.getRow(1).getCell(a).getCellStyle());
            			} catch (Exception e) {
            				//Si esta a null creamos un estilo de texto por defecto
            				DataFormat fmt = wb.createDataFormat();
            				dataStyle.setDataFormat(fmt.getFormat("@"));
            			}
                    	dataCellStyles.add(dataStyle);
                    }
                	
                }
                
                
            	

            }
            
            if (!(z == (sheet.getLastRowNum() + 1))) {
            	
            	//Introducimos los header
                if (nuevoLibro) {
                	//Nueva fila
            		newRow = (SXSSFRow) sh.createRow(rowCount++);
            		for (int a = 0; a < rowSize; a++) {
            			//Celdas y valores + estilos
                   		newCell = (SXSSFCell) newRow.createCell(colCount++);
                   		newCell.setCellValue(headerString.get(a));
                        newCell.setCellStyle(headerCellStyles.get(a));
                    }
               		nuevoLibro = false;
               		colCount = 0;
            	}
            	
              //Nueva fila
               	newRow = (SXSSFRow) sh.createRow(rowCount++);
           		for (int a = 0; a < rowSize; a++) {
           			
           			if (z != 0) {
           				newCell = (SXSSFCell) newRow.createCell(colCount++);
           				setCellValue(newCell, sheet.getRow(z).getCell(a));
                   		newCell.setCellStyle(dataCellStyles.get(a));
                   		
           			} else {
           				newCell = (SXSSFCell) newRow.createCell(colCount++);
           				newCell.setCellValue(headerString.get(a));
                        newCell.setCellStyle(headerCellStyles.get(a));
           			}
           			
                }
                colCount = 0;
                
            	
            }
            
            
            

            
        }
        
        SplitExcel.result = "Success";
        
	}
	
	
	
	
	
	/**
	 * <PRE>
	 * Nombre del método: setCellValue
	 * Código de proyecto: INCDSG
	 * SplitExcel 
	 * Caso de uso: <rellenar> 
	 * Descripción: 
	 *	<rellenar>
	 * Fecha de creación: 21/05/2019
	 * </PRE>
	 * @author jpardinas
	 * @param newCell
	 * @param xssfCell
	 */
	private static void setCellValue(SXSSFCell newCell, XSSFCell xssfCell) {
		
		if (xssfCell != null) {
			
			switch (xssfCell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				newCell.setCellValue(xssfCell.getRichStringCellValue().getString());
	            break;
	            
			case XSSFCell.CELL_TYPE_NUMERIC:
	        	if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
	                newCell.setCellValue(xssfCell.getDateCellValue());
	        	 } else {
	                 newCell.setCellValue(xssfCell.getNumericCellValue());
	             }
	            break;
	        
			case XSSFCell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(xssfCell.getBooleanCellValue());
	            break;
	            
			case XSSFCell.CELL_TYPE_FORMULA:
				newCell.setCellFormula(xssfCell.getCellFormula());
	            break;
	            
			default:
				newCell.setCellValue("ERROR CELL TYPE - CHECK");
				break;
			}
			
		} else {
			newCell.setCellValue(" ");
		}
		

	}
	
	
	
	
	/**
	 * <PRE>
	 * Nombre del método: writeWorkBooks
	 * Código de proyecto: INCDSG
	 * SplitExcel 
	 * Caso de uso: <rellenar> 
	 * Descripción: 
	 *	<rellenar>
	 * Fecha de creación: 21/05/2019
	 * </PRE>
	 * @author jpardinas
	 * @param wbs
	 */
	private static void writeWorkBooks(SXSSFWorkbook wbs) {
		
		FileOutputStream out;
        try {
                out = new FileOutputStream(new File(SplitExcel.newPath + newFileName + "_" + (SplitExcel.numFichero++ + 1) + ".xlsx"));
                wbs.write(out);
                out.close();
            
        } catch (IOException e) {
        	SplitExcel.result = "Error writing files";
        }
		
	}



	/**
	 * @return the result
	 */
	public static String getResult() {
		return result;
	}
	
	
	


}

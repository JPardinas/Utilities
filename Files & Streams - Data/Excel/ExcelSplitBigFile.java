import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class TestExcel {
	
	static String fileName = "plantillaSGEDevo.xlsx";
	static String path = "C:/Users/jpardinas/Desktop/pruebaspdf/";
	static int maxRows = 50000;
	static int fichero = 0;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		
		File file = new File("C:/Users/jpardinas/Desktop/pruebaspdf/plantillaSGEDevo.xlsx");
		

		
		
		OPCPackage opcPackage;
		try {
			opcPackage = OPCPackage.open(file.getAbsolutePath());
			XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			/* Only split if there are more rows than the desired amount. */
            if (sheet.getPhysicalNumberOfRows() >= maxRows) {
                splitWorkbook(workbook);
            }
            opcPackage.close();
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	


	private static void splitWorkbook(XSSFWorkbook workbook) {
		
		//Nuevo WorkBook - SXSS
        SXSSFWorkbook wb = new SXSSFWorkbook(300);
        //Hoja WorkBook
        SXSSFSheet sh = (SXSSFSheet) wb.createSheet();
        //Row y Celdas Work
        SXSSFRow newRow;
        SXSSFCell newCell;


        //WorkBook recibido XSSF -> Hoja y Celda
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFCell celda;
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
        
        
        for (int z = 0; z < sheet.getLastRowNum(); z++) {
        		

        	//Si se alcanza el numero de filas maximo
            if (rowCount == maxRows) {

            	//Seteamos el tamaño de las columnas
            	for (int a = 0; a < rowSize; a++) {
            		sh.setColumnWidth(a, cellWidth.get(a));
            	}

            	//Creamos el archivo y liberamos
            	writeWorkBooks(wb);
                wb.dispose();
                
                //Inicializamos la información
            	wb = new SXSSFWorkbook(300);
            	sh = (SXSSFSheet) wb.createSheet();
            	nuevoLibro = true;
            	rowCount = 0;
            	colCount = 0;
            }
            
            
            //Introducimos los header
            if (nuevoLibro) {
            	//Nueva fila
        		newRow = (SXSSFRow) sh.createRow(rowCount++);
        		for (int a = 0; a < rowSize; a++) {
        			//Celdas y valores + estilos
               		newCell = (SXSSFCell) newRow.createCell(colCount++);
                    newCell.setCellStyle(headerCellStyles.get(a));
               		newCell.setCellValue(headerString.get(a));
                }
           		nuevoLibro = false;
           		colCount = 0;
        	}
            

            //Nueva fila
           	newRow = (SXSSFRow) sh.createRow(rowCount++);
       		for (int a = 0; a < rowSize; a++) {
       			
       			if (z != 0) {
       				newCell = (SXSSFCell) newRow.createCell(colCount++);
               		newCell.setCellStyle(dataCellStyles.get(a));
               		setCellValue(newCell, sheet.getRow(z).getCell(a));
       			} else {
       				newCell = (SXSSFCell) newRow.createCell(colCount++);
                    newCell.setCellStyle(headerCellStyles.get(a));
               		newCell.setCellValue(headerString.get(a));
       			}
       			
            }
           		
          
            colCount = 0;
        }
        

	}

	
	


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



	private static void writeWorkBooks(SXSSFWorkbook wbs) {
			
			FileOutputStream out;
	        try {
	                String newFileName = fileName.substring(0, fileName.length() - 5);
	                out = new FileOutputStream(new File(path + newFileName + "_" + (fichero++ + 1) + ".xlsx"));
	                wbs.write(out);
	                out.close();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}
	
	


}

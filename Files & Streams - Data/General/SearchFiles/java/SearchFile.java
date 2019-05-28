import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;



public class SearchFile {
	

	private String textToSearch;
	private String filePath;
	private String newFilePath;
	private Integer searchMode;
	private Text labelResult;
	private Text labelErrors;
	private Button buttonStart;
	
	private int numErrors;
	private List<String> filesCopied;
	public static Integer SEARCH_FILES_FILE_NAMES = 0;
	public static Integer SEARCH_FILES_TEXT_INTO_FILES = 1;
	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	
	

	/**
	 * <PRE>
	 * Constructor de la clase: SearchFile
	 * Código de proyecto: INCDSG
	 * SearchFiles 
	 * Caso de uso: <rellenar> 
	 * Descripción: 
	 *	<rellenar>
	 * Fecha de creación: 28/05/2019
	 * </PRE>
	 * @author jpardinas
	 * @param textToSearch
	 * @param filePath
	 * @param newFilePath
	 * @param searchMode
	 * @param labelResult 
	 * @param labelErrors 
	 * @param buttonStart 
	 */ 
	public SearchFile(String textToSearch, String filePath, String newFilePath,
			Integer searchMode, Text labelResult, Text labelErrors, Button buttonStart) {
		super();
		this.textToSearch = textToSearch;
		this.filePath = filePath;
		this.newFilePath = newFilePath;
		this.searchMode = searchMode;
		this.labelResult = labelResult;
		this.labelErrors = labelErrors;
		this.buttonStart = buttonStart;
	}
	
	
	

	public void run() {

		buttonStart.setEnabled(true);
		labelResult.setText("Progress :");
		filesCopied = searchForFiles();
		
		
		if (filesCopied != null) {
			labelResult.setText("Total Files (" + filesCopied.size() + "): ");
			for (String resultString: filesCopied) {
				labelResult.setText(labelResult.getText().concat(resultString).concat(", "));
			}
			labelErrors.setText("Total Errors: " + numErrors + "     ");
		} else {
			labelResult.setText("Error");
		}

		
	}



	private List<String> searchForFiles () {
		filesCopied = new ArrayList<String>();
		numErrors = 0;
		File dir = new File(filePath);
		
		if (dir.isDirectory()) {
			if (searchMode == SEARCH_FILES_FILE_NAMES) {
				searchFile(dir, textToSearch, filePath, newFilePath);
			} else if (searchMode == SEARCH_FILES_TEXT_INTO_FILES) {
				searchIntoFile(dir, textToSearch, filePath, newFilePath);
			}
		}

		
		return filesCopied;
	}
	
	
	
	private  void searchFile(File dir, String textToSearch, String filePath, String newFilePath) {
		

	    if (dir.isDirectory()) {	    	

	        File[] files = dir.listFiles();
	        int total = files.length;
	        
	        int percentage = 0;
			
			for (File f : files) {
				percentage++;
				labelResult.setText("Progress: " + percentage(new BigDecimal(total), new BigDecimal(percentage)).toString());
				
				if (f.isDirectory()) {
					
					searchFile(f, textToSearch, filePath, newFilePath);
				} else {
					if (f.getName().contains(textToSearch)) {
			    		try {
			    			
			    			if (!filesCopied.contains(f.getName())) {
			    				File copyFile = new File(newFilePath + f.getName());
								copyFileUsingStream(f,copyFile);
								filesCopied.add(f.getName());
			    			} else {
			    				File copyFile = new File(newFilePath + "duplicated_" + filesCopied.size() + "_" + f.getName());
								copyFileUsingStream(f,copyFile);
								filesCopied.add(f.getName());
			    			}
						} catch (FileNotFoundException e) {
							numErrors++;
						} catch (IOException e) {
							numErrors++;
						}
			    	}
				}
			}
	    } 
	}
	
	private  void searchIntoFile(File dir, String textToSearch, String filePath, String newFilePath) {
		
		FileReader fr;
		BufferedReader br;
		String s;    
		
	    if (dir.isDirectory()) {
	    	
	    	File[] files = dir.listFiles();
	    	int total = files.length;
	    	
	    	int percentage = 0;
			for (File f : files) {
				percentage++;
				labelResult.setText("Progress: " + percentage(new BigDecimal(total), new BigDecimal(percentage)));
				
				if (f.isDirectory()) {
					searchIntoFile(f, textToSearch, filePath, newFilePath);
				} else {
					
					try {
						fr = new FileReader(f);
						br = new BufferedReader(fr); 
						
					    while((s=br.readLine())!=null) {
					    	  if (s.contains(textToSearch)){
					    		  try {
					    			  if (!filesCopied.contains(f.getName())) {
					        				File copyFile = new File(newFilePath + f.getName());
											copyFileUsingStream(f,copyFile);
											filesCopied.add(f.getName());
					        			} else {
					        				File copyFile = new File(newFilePath + "duplicated_" + filesCopied.size() + "_" + f.getName());
											copyFileUsingStream(f,copyFile);
											filesCopied.add(f.getName());
					        			}
										break;
									} catch (FileNotFoundException e) {
										numErrors++;
									} catch (IOException e) {
										numErrors++;
									} 
					    	  }
					    }
						
						
					} catch (FileNotFoundException e) {
						numErrors++;
					}  catch (IOException e) {
						numErrors++;
					}
					
					
				}
				

			}
	        
	    } 
	}
	
	
	
	
	
	private void copyFileUsingStream(File source, File dest) throws IOException {
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	    	System.out.println("File: " + source.getName() + ", Directory: " + source.getCanonicalPath());
	        is.close();
	        os.close();
	    }
	}



	private BigDecimal percentage(BigDecimal base, BigDecimal pct){
	    return base.multiply(pct).divide(ONE_HUNDRED);
	}


	/**
	 * @return the filesCopied
	 */
	public List<String> getFilesCopied() {
		return filesCopied;
	}



	/**
	 * @return the numErrors
	 */
	public int getNumErrors() {
		return numErrors;
	}
	
	
	

}

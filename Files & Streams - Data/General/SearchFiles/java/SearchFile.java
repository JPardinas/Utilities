import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;



public class SearchFile {
	

	private static int numErrors;
	private static List<String> filesCopyed;
	public static Integer SEARCH_FILES_FILE_NAMES = 0;
	public static Integer SEARCH_FILES_TEXT_INTO_FILES = 1;

	
	public static List<String> searchForFiles (String textToSearch, String filePath, String newFilePath, Integer searchMode) {
		filesCopyed = new ArrayList<String>();
		numErrors = 0;
		File dir = new File(filePath);
		
		if (dir.isDirectory()) {
			if (searchMode == SEARCH_FILES_FILE_NAMES) {
				searchFile(dir, textToSearch, filePath, newFilePath);
			} else if (searchMode == SEARCH_FILES_TEXT_INTO_FILES) {
				searchIntoFile(dir, textToSearch, filePath, newFilePath);
			}
		}

		
		return filesCopyed;
	}
	
	
	
	private static void searchFile(File dir, String textToSearch, String filePath, String newFilePath) {
		

	    if (dir.isDirectory()) {	    	

	        File[] files = dir.listFiles();
	        
	        try {
	        	
	        	for (File f : files) {
		        	
		        	if (f.isDirectory()) {
						System.out.println("directory:" + f.getCanonicalPath());
						searchFile(f, textToSearch, filePath, newFilePath);
					} else {
						if (f.getName().contains(textToSearch)) {
			        		try {
			        			
			        			if (!filesCopyed.contains(f.getName())) {
			        				File copyFile = new File(newFilePath + f.getName());
									copyFileUsingStream(f,copyFile);
									filesCopyed.add(f.getName());
			        			} else {
			        				File copyFile = new File(newFilePath + f.getTotalSpace()+ f.getName());
									copyFileUsingStream(f,copyFile);
									filesCopyed.add(f.getName());
			        			}
								
								
							} catch (FileNotFoundException e) {
								numErrors++;
							} catch (IOException e) {
								numErrors++;
							}
			        		
			        	}
					}
		        }
	        	
	        } catch (IOException e) {
	        	numErrors++;
	        }
	    } 
	}
	
	private static void searchIntoFile(File dir, String textToSearch, String filePath, String newFilePath) {
		
		FileReader fr;
		BufferedReader br;
		String s;    
		
	    if (dir.isDirectory()) {
	    	
	    	File[] files = dir.listFiles();
	    	
	    	try {
	    		
	    		for (File f : files) {
		        	
		        	
		        	if (f.isDirectory()) {
						System.out.println("directory:" + f.getCanonicalPath());
						searchIntoFile(f, textToSearch, filePath, newFilePath);
					} else {
						
						try {
							fr = new FileReader(f);
							br = new BufferedReader(fr); 
							
						    while((s=br.readLine())!=null) {
						    	  if (s.contains(textToSearch)){
						    		  try {
						    			  if (!filesCopyed.contains(f.getName())) {
						        				File copyFile = new File(newFilePath + f.getName());
												copyFileUsingStream(f,copyFile);
												filesCopyed.add(f.getName());
						        			} else {
						        				File copyFile = new File(newFilePath + f.getTotalSpace() + f.getName());
												copyFileUsingStream(f,copyFile);
												filesCopyed.add(f.getName());
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
	    		
	    		
	    	} catch (IOException e) {
	    		numErrors++;
	    	}
	        
	    } 
	}
	
	
	
	
	
	private static void copyFileUsingStream(File source, File dest) throws IOException {
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
	        is.close();
	        os.close();
	    }
	}



	/**
	 * @return the numErrors
	 */
	public static int getNumErrors() {
		return numErrors;
	}
	
	
	

}

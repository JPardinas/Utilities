import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;




public class Inicio {
	
	private Display display;
	private Shell shell;
	private Text labelResult;
	private Text textFilePath;
	private Text textTextToSearch;
	private Text textNewFilePath;
	private Button buttonFileNames;
	private Button buttonTextInTo;
	private Integer searchMode;
	private Text labelErrors;
	private Button buttonStart;
	
	public Inicio () {
		initialize();
		open();
	}
	


	private void initialize() {
		display = new Display();
		shell = new Shell(display);
		searchMode = -1;
		
		configureShell();
		createComposite();
		
	}
	
	private void configureShell() {
		
		shell.setSize(500,300);
		shell.setMinimumSize(300, 300);
		shell.setLayout(new GridLayout(1, false));
		shell.setText("File Searcher");
		
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2, (screenSize.height - shell.getBounds().height) / 2);
	}



	
	private void createComposite() {
		
		final Composite composite = new Composite(shell, SWT.FILL);
	    composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL,
				GridData.BEGINNING, true, false));
		
		Group groupFiltrosGenerales = new Group(composite, SWT.NONE);
		groupFiltrosGenerales.setLayout(new GridLayout(3, false));
		groupFiltrosGenerales.setLayoutData(new GridData(GridData.FILL,
				GridData.FILL, true, false));
		groupFiltrosGenerales.setText("Directory");
		
		
		
		
		createFirstRow(groupFiltrosGenerales);
		createSecondRow(groupFiltrosGenerales);
		createThirdRow(groupFiltrosGenerales);
		createFourthRow(groupFiltrosGenerales);
		createButton(composite);
		
	}




	private void createFirstRow(Group groupFiltrosGenerales) {
		
		Label labelFile = new Label(groupFiltrosGenerales, SWT.FILL);
		GridData gridDataLabelId = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		labelFile.setLayoutData(gridDataLabelId);
		labelFile.setText("Files Path: ");
		
		
		textFilePath = new Text(groupFiltrosGenerales, SWT.SINGLE | SWT.BORDER);
		GridData gdText = new GridData(4, 2, true, false);
		gdText.widthHint = 500;
		textFilePath.setLayoutData(gdText);
		textFilePath.setText("");
		
		Button button = new Button(groupFiltrosGenerales, SWT.PUSH);
		button.setText("Browse...");
		
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				DirectoryDialog fileDialog = new DirectoryDialog(shell);
				// Set the text
				fileDialog.setText("Select directory");
				// Set filter on .txt files
				String selection = fileDialog.open();
				if (selection != null)
					textFilePath.setText(selection);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	
	private void createSecondRow(Group groupFiltrosGenerales) {
		
		Label labelFile = new Label(groupFiltrosGenerales, SWT.FILL);
		GridData gridDataLabelId = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		labelFile.setLayoutData(gridDataLabelId);
		labelFile.setText("Text to Search: ");
		
		
		textTextToSearch = new Text(groupFiltrosGenerales, SWT.SINGLE | SWT.BORDER);
		GridData gdText = new GridData(4, 2, true, false);
		gdText.widthHint = 500;
		gdText.horizontalSpan = 2;
		textTextToSearch.setLayoutData(gdText);
		textTextToSearch.setText("");
		
	}
	
	
	private void createThirdRow(Group groupFiltrosGenerales) {
		
		Label labelFile = new Label(groupFiltrosGenerales, SWT.FILL);
		GridData gridDataLabelId = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		labelFile.setLayoutData(gridDataLabelId);
		labelFile.setText("New File Path: ");
		
		
		textNewFilePath = new Text(groupFiltrosGenerales, SWT.SINGLE | SWT.BORDER);
		GridData gdText = new GridData(4, 2, true, false);
		gdText.widthHint = 500;
		textNewFilePath.setLayoutData(gdText);
		textNewFilePath.setText("");
		
		Composite groupButtons = new Composite(groupFiltrosGenerales, SWT.NONE);
		groupButtons.setLayout(new GridLayout(2, false));
		
		Button button1 = new Button(groupButtons, SWT.PUSH);
		button1.setText("Browse...");
		
		button1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				DirectoryDialog fileDialog = new DirectoryDialog(shell);
				// Set the text
				fileDialog.setText("Select directory");
				// Set filter on .txt files
				String selection = fileDialog.open();
				if (selection != null)
					textNewFilePath.setText(selection);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button button2 = new Button(groupButtons, SWT.PUSH);
		button2.setText("Open...");
		
		button2.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (textNewFilePath.getText() != null && textNewFilePath.getText().trim().length() > 0)
				
				if (Desktop.isDesktopSupported()) {
			        try {
						Desktop.getDesktop().open(new File(textNewFilePath.getText()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	private void createFourthRow(Group groupFiltrosGenerales) {
		
		Label labelFile = new Label(groupFiltrosGenerales, SWT.FILL);
		GridData gridDataLabelId = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		labelFile.setLayoutData(gridDataLabelId);
		labelFile.setText("Search Mode: ");
		
		
		Composite genderGroup = new Composite(groupFiltrosGenerales, SWT.NONE);
		genderGroup.setLayout(new GridLayout(2, false));
		 
		 
		buttonFileNames = new Button(genderGroup, SWT.RADIO);
		buttonFileNames.setText("File Names");
		buttonFileNames.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				searchMode = 0;
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		 
		buttonTextInTo = new Button(genderGroup, SWT.RADIO);
		buttonTextInTo.setText("Text in to Files");
		buttonTextInTo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				searchMode = 1;
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	
	
	private void createButton(Composite composite) {
		
		buttonStart = new Button(composite, SWT.CENTER);
		GridData gridDataLabelId = new GridData(SWT.RIGHT, SWT.NONE, false, false);
		gridDataLabelId.widthHint = 100;
		buttonStart.setLayoutData(gridDataLabelId);
		buttonStart.setText("Start");
		
		buttonStart.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (validValues()) {
					
					buttonStart.setEnabled(false);
					File testPath = new File (textFilePath.getText());
					File testCopyPath = new File (textNewFilePath.getText());
//					labelResult.setText("Searching...");
					
					if (!testPath.exists()) {
						labelResult.setText("File Path not found");
					} else if (!testCopyPath.exists()) {
						labelResult.setText("Copy File Path not found");
					} else {
						
						display.update();
						
						Display.getDefault().asyncExec(new Runnable() {
					        public void run() {
					        	
						        SearchFile runnable = new SearchFile(textTextToSearch.getText(), textFilePath.getText(), 
						        		textNewFilePath.getText(), searchMode, labelResult, labelErrors, buttonStart);
						        runnable.run();
					        }
					    });
						
						
						

						
						
						
					}
					
					
					
					
				} else {
					labelResult.setText("Values not valid, check it");
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		labelResult = new Text(composite, SWT.FILL);
		GridData gridDataLabel = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		gridDataLabel.widthHint = 300;
		labelResult.setLayoutData(gridDataLabel);
		labelResult.setEditable(false);
		
		
		labelErrors = new Text(composite, SWT.FILL);
		GridData gridDataLabelErors = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		gridDataLabelErors.widthHint = 300;
		labelErrors.setLayoutData(gridDataLabelErors);
		labelErrors.setEditable(false);
		labelErrors.setText("");
		
	}



	protected boolean validValues() {
		
		boolean valid = true;
		
		
		if (
				(textFilePath.getText().isEmpty() || textFilePath.getText().trim().length() < 1) ||
				(textTextToSearch.getText().isEmpty() || textTextToSearch.getText().trim().length() < 1) ||
				(textNewFilePath.getText().isEmpty() || textNewFilePath.getText().trim().length() < 1) ||
				(searchMode == -1)
				) {
			valid = false;
		} else {
			if (!textNewFilePath.getText().substring(textNewFilePath.getText().length() -1).equals("\\")) {
				textNewFilePath.setText(textNewFilePath.getText().concat("\\"));
			}
			if (!textFilePath.getText().substring(textFilePath.getText().length() -1).equals("\\")) {
				textFilePath.setText(textFilePath.getText().concat("\\"));
			}
		}
		
		
		
		
		return valid;
	}



	private void open() {
		
		shell.open();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
		
	}
	
	public static void main(String[] args) {
		new Inicio();
	}
	

}

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;




public class Inicio {
	
	private Display display;
	private Shell shell;
	private Label labelResult;
	private Text textFilePath;
	private Text textNewFileName;
	private Text textNewFilePath;
	private Text textNumberOfRows;
	
	public Inicio () {
		initialize();
		open();
	}
	


	private void initialize() {
		display = new Display();
		shell = new Shell(display);
		
		
		configureShell();
		createComposite();
		
	}
	
	private void configureShell() {
		
		shell.setSize(500,300);
		shell.setMinimumSize(300, 300);
		shell.setLayout(new GridLayout(1, false));
		shell.setText("Excel Splitter");
		
		Rectangle screenSize = display.getPrimaryMonitor().getBounds();
		shell.setLocation((screenSize.width - shell.getBounds().width) / 2, (screenSize.height - shell.getBounds().height) / 2);
	}



	
	private void createComposite() {
		
		final Composite composite = new Composite(shell, SWT.FILL);
	    composite.setLayout(new GridLayout(1, false));
		composite.setLayoutData(new GridData(GridData.FILL,
				GridData.BEGINNING, true, false));
		
		Group groupFiltrosGenerales = new Group(composite, SWT.NONE);
		groupFiltrosGenerales.setLayout(new GridLayout(2, false));
		groupFiltrosGenerales.setLayoutData(new GridData(GridData.FILL,
				GridData.FILL, true, false));
		groupFiltrosGenerales.setText("Excel File");
		
		
		
		
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
		labelFile.setText("File Path: ");
		
		
		textFilePath = new Text(groupFiltrosGenerales, SWT.SINGLE | SWT.BORDER);
		GridData gdText = new GridData(4, 2, true, false);
		gdText.widthHint = 500;
		textFilePath.setLayoutData(gdText);
		textFilePath.setText("");
		
	}
	
	
	
	private void createSecondRow(Group groupFiltrosGenerales) {
		
		Label labelFile = new Label(groupFiltrosGenerales, SWT.FILL);
		GridData gridDataLabelId = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		labelFile.setLayoutData(gridDataLabelId);
		labelFile.setText("New File Name: ");
		
		
		textNewFileName = new Text(groupFiltrosGenerales, SWT.SINGLE | SWT.BORDER);
		GridData gdText = new GridData(4, 2, true, false);
		gdText.widthHint = 500;
		textNewFileName.setLayoutData(gdText);
		textNewFileName.setText("");
		
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
		
	}
	
	
	private void createFourthRow(Group groupFiltrosGenerales) {
		
		Label labelFile = new Label(groupFiltrosGenerales, SWT.FILL);
		GridData gridDataLabelId = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		labelFile.setLayoutData(gridDataLabelId);
		labelFile.setText("Number of Rows: ");
		
		
		textNumberOfRows = new Text(groupFiltrosGenerales, SWT.SINGLE | SWT.BORDER);
		GridData gdText = new GridData(4, 2, true, false);
		gdText.widthHint = 500;
		textNumberOfRows.setLayoutData(gdText);
		textNumberOfRows.setText("");
		
		textNumberOfRows.addVerifyListener(new VerifyListener() {
			
			@Override
			public void verifyText(VerifyEvent paramVerifyEvent) {
				paramVerifyEvent.doit = paramVerifyEvent.text.matches("[0-9]*");
				
			}
		});
		
	}
	
	
	
	private void createButton(Composite composite) {
		
		Button button = new Button(composite, SWT.CENTER);
		GridData gridDataLabelId = new GridData(SWT.RIGHT, SWT.NONE, false, false);
		gridDataLabelId.widthHint = 100;
		button.setLayoutData(gridDataLabelId);
		button.setText("Start");
		
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				if (validValues()) {
					labelResult.setText(SplitExcel.splitExcelFile(textFilePath.getText(), textNewFileName.getText(), textNewFilePath.getText(), Integer.valueOf(textNumberOfRows.getText())));
				} else {
					labelResult.setText("Values not valid, check it");
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		labelResult = new Label(composite, SWT.FILL);
		GridData gridDataLabel = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		gridDataLabel.widthHint = 300;
		labelResult.setLayoutData(gridDataLabel);
		
		
		
	}



	protected boolean validValues() {
		
		boolean valid = true;
		
		
		if (
				(textFilePath.getText().isEmpty() || textFilePath.getText().trim().length() < 1) ||
				(textNewFileName.getText().isEmpty() || textNewFileName.getText().trim().length() < 1) ||
				(textNewFilePath.getText().isEmpty() || textNewFilePath.getText().trim().length() < 1) ||
				(textNumberOfRows.getText().isEmpty() || textFilePath.getText().trim().length() < 1) 
				) {
			valid = false;
		} else {
			if (!textNewFilePath.getText().substring(textNewFilePath.getText().length() -1).equals("\\")) {
				textNewFilePath.setText(textNewFilePath.getText().concat("\\"));
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

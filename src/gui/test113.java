package gui;

//import java.io.File;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
//import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class test113 extends AuetShell {
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			test113 shell = new test113(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public test113(Display display) {
		super(display);
		final test113 dis = this;
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Times New Roman", 18, SWT.NORMAL));
		lblNewLabel.setBounds(132, 138, 397, 28);
		lblNewLabel.setText("Enter SoftwareAG installation directory");
		
		text = new Text(this, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("Times New Roman", 15, SWT.NORMAL));
		text.setBounds(132, 172, 272, 28);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnNewButton.setBounds(426, 175, 92, 25);
		btnNewButton.setText("Browse...");
		
		btnNewButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event){
				Shell sh = new Shell();
				String selectedDir = "";
				 DirectoryDialog directoryDialog = new DirectoryDialog(sh);
				 
		        directoryDialog.setFilterPath(selectedDir);
		        directoryDialog.setMessage("Please select a directory and click OK");
			        
		        String dir = directoryDialog.open();
		        if(dir != null) {
		          text.setText(dir);
		          //selectedDir = dir;
		        }
			}
			});
		Button btnNext = new Button(this, SWT.NONE);
		btnNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				String SourcePath;
				SourcePath = text.getText();
				Shell sh = new Shell();
				int style = SWT.ICON_ERROR;
				MessageBox msg = new MessageBox(sh,style);
				
				
				//check if source directory is empty
				if(SourcePath.equals("")){
					msg.setMessage("The source directory cannot be empty");
					msg.open();
				} else {//validate the input
					File sag_dir = new File(SourcePath);
					if(sag_dir.exists()){			//check to see if source path exists
						if(sag_dir.isDirectory()){	//checks to see if it is valid directory 
							if(sag_dir.canRead()){	//checks read permissions
								
								//continue processing
								dis.getShell().setVisible(false);
								
								//dis.getLocation();
								Display display = Display.getDefault();
								Details shell = new Details(display,SourcePath);
								shell.setLocation(dis.getLocation());
								shell.open();
								try {
									shell.processInformation();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							} else {
								msg.setMessage("Access denied");
								msg.open();
							}
						} else {
							msg.setMessage("Invald directory");
							msg.open();
						}
					
					} else {
						msg.setMessage("Invalid path, please enter again");
						msg.open();
					}
						
					
				}
			}
		});
		btnNext.setBounds(285, 246, 75, 25);
		btnNext.setText("Next >>");
		/*btnNext.addSelectionListener(new SelectionListener(){
			  public void widgetSelected(SelectionEvent event) {
				  
			  }
				  public void widgetDefaultSelected(SelectionEvent event) {
					  
				  }
			  }
		});*/
		//setLayout(new FormLayout());
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(676, 370);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Event;
import java.awt.Frame;
import org.eclipse.swt.awt.SWT_AWT;
import java.awt.Panel;
import java.awt.BorderLayout;
import javax.swing.JRootPane;

public class test112 extends Shell {
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			test112 shell = new test112(display);
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
	public test112(Display display) {
		super(display, SWT.SHELL_TRIM);
		setToolTipText("Automatic Upgrade Estimation Tool");
		setImage(SWTResourceManager.getImage(test112.class, "/images/logo2.png"));
		
		Label label = new Label(this, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(test112.class, "/images/logo2.png"));
		label.setBounds(67, 0, 303, 163);
		
		Label lblEnterSourceDirectory = new Label(this, SWT.NONE);
		lblEnterSourceDirectory.setBounds(33, 190, 131, 15);
		lblEnterSourceDirectory.setText("Enter Source Directory");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(170, 187, 200, 27);
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		final String selectedDir="";
		//Display display2 = new Display();
		final Shell shell = new Shell();
		btnNewButton.setBounds(376, 190, 100, 25);
		btnNewButton.setText("Browse...");
		
		Button btnNext = new Button(this, SWT.NONE);
		btnNext.setBounds(223, 233, 75, 25);
		btnNext.setText("Next");
		btnNewButton.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event event){
				 DirectoryDialog directoryDialog = new DirectoryDialog(shell);
			        
			        directoryDialog.setFilterPath(selectedDir);
			        directoryDialog.setMessage("Please select a directory and click OK");
			        
			        String dir = directoryDialog.open();
			        if(dir != null) {
			          text.setText(dir);
			          //selectedDir = dir;
			}
			}
			});
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Automatic Upgrade Estimation Tool");
		setSize(523, 323);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

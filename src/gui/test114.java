package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class test114 extends AuetShell {
	
	public ProgressBar progressBar;
	public Label status;
	public Text text;
	public Button btnCancel;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			
			Display display = Display.getDefault();
			test114 shell = new test114(display);
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
	public test114(Display display) {
		super(display);
		
		Composite composite = new Composite(this, SWT.HORIZONTAL | SWT.INDETERMINATE);
		composite.setBounds(50, 131, 700, 148);
		
		progressBar = new ProgressBar(composite, SWT.SMOOTH);
		progressBar.setBounds(38, 10, 294, 17);
		
		status = new Label(composite,SWT.NONE);
		status.setBounds(340, 10, 200, 17);
		status.setText("Not started");
		
		ExpandBar expandBar = new ExpandBar(composite, SWT.NONE);
		expandBar.setBounds(38, 33, 128, 32);
		
		ExpandItem xpndtmDetails = new ExpandItem(expandBar, SWT.NONE);
		xpndtmDetails.setText("Details:");
		
		text = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		text.setBounds(38, 63, 650, 79);
		
		btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(538, 280, 75, 25);
		btnCancel.setText("Cancel");
		createContents();
		btnCancel.addSelectionListener(new SelectionListener(){
			@Override
			public void widgetSelected(SelectionEvent e){
				Display d = Display.getCurrent();
				d.close();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e){
				Display d = Display.getCurrent();
				d.close();
			}
		});
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

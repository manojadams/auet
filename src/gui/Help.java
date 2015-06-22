package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

public class Help extends Shell {

	/**
	 * Launch the application.
	 * @param args
	 
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Help shell = new Help(display);
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
	}*/

	/**
	 * Create the shell.
	 * @param display
	 */
	public Help(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 414, 196);
		lblNewLabel.setText("Automatic upgrade estimation tool\r\n\r\nSee how systems are running right now\u2014not just after the fact. \r\nWith webMethods Operational Intelligence, your business can gain\r\na competitive advantage in fast-changing economic environments.\r\nYou can understand your systems regardless of the software they are \r\nrunning, and you can build real-time monitoring into new solutions \r\nand business processes.");
		
		Button btnOk = new Button(this, SWT.NONE);
		btnOk.setBounds(349, 212, 75, 25);
		btnOk.setText("OK");
		createContents();
		final Help dis = this;
		btnOk.addSelectionListener(new SelectionListener(){
			public void widgetSelected(SelectionEvent event) {
		        
		    	dis.close();
		    	
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		    
		    	  dis.close();
		    	  
		      }

		});
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("HELP-- Auet");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

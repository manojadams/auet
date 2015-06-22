package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

public class About extends Shell {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			About shell = new About(display);
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
	public About(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 0, 434, 261);
		
		TabItem tbtmSummary = new TabItem(tabFolder, SWT.NONE);
		tbtmSummary.setText("Summary");
		
		Label lblNewLabel = new Label(tabFolder, SWT.NONE);
		tbtmSummary.setControl(lblNewLabel);
		lblNewLabel.setText("About\r\n\r\nThis is Automatic upgrade estimation tool which can be used to e\r\nstimate the upgrade time for a project.");
		
		TabItem tbtmBroker = new TabItem(tabFolder, SWT.NONE);
		tbtmBroker.setText("Broker");
		
		TabItem tbtmIntegrationServer = new TabItem(tabFolder, SWT.NONE);
		tbtmIntegrationServer.setText("Integration Server");
		
		TabItem tbtmMws = new TabItem(tabFolder, SWT.NONE);
		tbtmMws.setText("MWS");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("ABOUT | Auet");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}

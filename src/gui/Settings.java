package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Settings extends Shell {
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Settings shell = new Settings(display);
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
	public Settings(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setBounds(0, 0, 507, 25);
		
		TabItem tbtmBroker = new TabItem(tabFolder, SWT.NONE);
		tbtmBroker.setText("Broker");
		
		TabItem tbtmIntegrationServer = new TabItem(tabFolder, SWT.NONE);
		tbtmIntegrationServer.setText("Integration Server");
		
		TabItem tbtmTradingNetworks = new TabItem(tabFolder, SWT.NONE);
		tbtmTradingNetworks.setText("Trading Networks");
		
		TabItem tbtmThirdParty = new TabItem(tabFolder, SWT.NONE);
		tbtmThirdParty.setText("Third Party");
		
		TabItem tbtmInstalledProducts = new TabItem(tabFolder, SWT.NONE);
		tbtmInstalledProducts.setText("Installed Products");
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBounds(0, 25, 331, 226);
		
		Label lblInstances = new Label(composite, SWT.NONE);
		lblInstances.setBounds(51, 31, 55, 15);
		lblInstances.setText("Instances:");
		
		Button btnVerify = new Button(composite, SWT.NONE);
		btnVerify.setBounds(222, 59, 75, 25);
		btnVerify.setText("Verify");
		
		text = new Text(composite, SWT.BORDER);
		text.setBounds(49, 59, 154, 25);
		
		Button btnAdd = new Button(composite, SWT.NONE);
		btnAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAdd.setBounds(82, 119, 75, 25);
		btnAdd.setText("Add");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(523, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

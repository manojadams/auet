package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

public class AuetShell extends Shell {

	/**
	 * Launch the application.
	 * @param args
	 
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			AuetShell shell = new AuetShell(display);
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
*/
	/**
	 * Create the shell.
	 * @param display
	 */
	public AuetShell(final Display display) {
		super(display, SWT.SHELL_TRIM);
		setMinimumSize(new Point(665, 370));
		setImage(SWTResourceManager.getImage(AuetShell.class, "/images/logo2.png"));
		setLayout(null);
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.CASCADE);
		mntmNewItem.setText("File");
		
		Menu menu_1 = new Menu(mntmNewItem);
		mntmNewItem.setMenu(menu_1);
		
		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");
		mntmExit.addSelectionListener(new SelectionListener(){
			  public void widgetSelected(SelectionEvent event) {
				  new Thread(new Runnable() {
			            public void run() {
			               
			                  Display.getDefault().asyncExec(new Runnable() {
			                     public void run() {
			                        display.close();
			                     }
			                  });
			               
			            }
			         }).start();  
				  }
			  
			  public void widgetDefaultSelected(SelectionEvent event) {
				  new Thread(new Runnable() {
			            public void run() {
			               
			                  Display.getDefault().asyncExec(new Runnable() {
			                     public void run() {
			                    	 display.close();
			                     }
			                  });
			               
			            }
			         }).start();
			  }
		});
		
		MenuItem mntmAbout = new MenuItem(menu, SWT.NONE);
		mntmAbout.setText("About");
		
		mntmAbout.addSelectionListener(new SelectionListener(){
			  public void widgetSelected(SelectionEvent event) {
				  new Thread(new Runnable() {
			            public void run() {
			               
			                  Display.getDefault().asyncExec(new Runnable() {
			                     public void run() {
			                        About abt = new About(display);
			                        abt.open();
			                     }
			                  });
			               
			            }
			         }).start();  
				  }
			  
			  public void widgetDefaultSelected(SelectionEvent event) {
				  new Thread(new Runnable() {
			            public void run() {
			               
			                  Display.getDefault().asyncExec(new Runnable() {
			                     public void run() {
			                    	 About abt = new About(display);
				                     abt.open();
			                     }
			                  });
			               
			            }
			         }).start();
			  }
		});
		
		MenuItem mntmSettings = new MenuItem(menu, SWT.NONE);
		mntmSettings.setText("Settings");
		mntmSettings.addSelectionListener(new SelectionListener() {

		      public void widgetSelected(SelectionEvent event) {
		        
		        new Thread(new Runnable() {
		            public void run() {
		               
		                  
		                  Display.getDefault().asyncExec(new Runnable() {
		                     public void run() {
		                        Settings s = new Settings(display);
		                        s.open();
		                     }
		                  });
		               
		            }
		         }).start();
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  new Thread(new Runnable() {
			            public void run() {
			               
			              
			                  Display.getDefault().asyncExec(new Runnable() {
			                     public void run() {
			                        Help h = new Help(display);
			                        h.open();
			                     }
			                  });
			               
			            }
			         }).start();
		      }
		      });
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.NONE);
		mntmHelp.setText("Help");
		
		mntmHelp.addSelectionListener(new SelectionListener() {

		      public void widgetSelected(SelectionEvent event) {
		        
		        new Thread(new Runnable() {
		            public void run() {
		               
		                  
		                  Display.getDefault().asyncExec(new Runnable() {
		                     public void run() {
		                        Help h = new Help(display);
		                        h.open();
		                     }
		                  });
		               
		            }
		         }).start();
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  new Thread(new Runnable() {
			            public void run() {
			               
			              
			                  Display.getDefault().asyncExec(new Runnable() {
			                     public void run() {
			                        Help h = new Help(display);
			                        h.open();
			                     }
			                  });
			               
			            }
			         }).start();
		      }
		      });
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		composite.setBounds(0, 4, 661, 98);
		
		Label auetLabel1 = new Label(composite, SWT.NONE);
		auetLabel1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		auetLabel1.setBounds(10, 10, 148, 80);
		auetLabel1.setImage(SWTResourceManager.getImage(AuetShell.class, "/images/logo3.png"));
		
		Label auetLabel2 = new Label(composite, SWT.CENTER);
		auetLabel2.setBounds(154, 25, 467, 34);
		auetLabel2.setText("Automatic Upgrade Estimation Tool");
		auetLabel2.setForeground(SWTResourceManager.getColor(0, 0, 128));
		auetLabel2.setFont(SWTResourceManager.getFont("@Adobe Gothic Std B", 20, SWT.NORMAL));
		auetLabel2.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(342, 61, 273, 15);
		lblNewLabel.setText("2015 Copyright SoftwareAG ltd. All rights reserved.");
		
		Label auetLabel3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		auetLabel3.setBounds(-10, 100, 671, 2);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("Automatic Upgarde Estimation Tool");
		setSize(677, 448);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}

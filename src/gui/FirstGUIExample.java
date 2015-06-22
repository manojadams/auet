package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

	
	public class FirstGUIExample extends JFrame {

		   public FirstGUIExample() 
		   		{
			   		
			   		this.setTitle("Automatic Upgrade Estimation Tool");
			   		setSize(700, 300);
			   		setDefaultCloseOperation(EXIT_ON_CLOSE);
			   		Container c = getContentPane();
			   		c.setLayout(new FlowLayout());
			   		
			   		
			   		//Declarations
			   		String[] ProdList = { "IntegrationServer", "MWS" , "BPM"};		        
			   		JButton dirButton = new JButton("Browse");
			   		JButton nextButton = new JButton("Next>>");
			   		JComboBox prodList = new JComboBox(ProdList);
		       		final JTextField tf = new JTextField("Please Select source SAG Dir..",30);
		       		Font bigFont = tf.getFont().deriveFont(Font.PLAIN, 20f);
		       		tf.setFont(bigFont);
		       		final JLabel statusbar =new JLabel("Automatic Upgrade Estimation");
		       		
			   		JLabel sagLabel = new JLabel("");
			   		Image img = new ImageIcon(this.getClass().getResource("/sag.jpg")).getImage();
			   		sagLabel.setIcon(new ImageIcon(img));
			   		sagLabel.setBounds(10,53,166,256);
			   		
			   		//browse button action
			   		dirButton.addActionListener(new ActionListener() {
			   			public void actionPerformed(ActionEvent ae) 
			   				{
			   					JFileChooser chooser = new JFileChooser();
			   					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			   					
			   				try{	
			   					int option = chooser.showOpenDialog(FirstGUIExample.this);
			   					if (option == JFileChooser.APPROVE_OPTION) 
			   						{	File file = chooser.getSelectedFile();
			   							String fullPath=file.getCanonicalPath();
			   						   	tf.setText(fullPath);
			   						}	
			   					else {
			   							statusbar.setText("You canceled.");
			   						}
			   					}
			   					catch (IOException e) {}
			   				}
			   			});
			   		
			   		
			   		
			 //next button action  		
			   		nextButton.addActionListener(new ActionListener() {
			   			public void actionPerformed(ActionEvent ae) {
			   				String Sourcepath;
			   				int count;
			   				int node;
			   				Sourcepath=tf.getText();
			   				// System.out.println(Sourcepath);
			   				File dir= new File(Sourcepath);
			   				count=packageCount(Sourcepath);
			   				try {
								SavePkg(count);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			   				
			   				getlist(dir);
			   				System.out.println("Total Packages: "+count);
			   				node=find();
			   				try {
								SaveNodeCount(node);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			   				
			   				
			   				TableDemo t1=new TableDemo();
			   				String[] args = null;
							t1.main(args);
			   				
			   				
			   				
			   				
			   				
			   				
			   			/*	try {
								findNode();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			   				*/
			   				}
			   			});
		    
		    
			   		//display orientation
			   		c.add(sagLabel,BorderLayout.NORTH);
			   		c.add(tf,BorderLayout.WEST);
			   		c.add(dirButton,BorderLayout.EAST);
			   		c.add(prodList);
			   		c.add(nextButton,BorderLayout.SOUTH);
			   		
			//   		c.add(statusbar);
			   		
			   		
		  }
		   
		   
		   
		   
		   
		   
		   //pkg count fn
		   public int packageCount(String Sourcepath)
			{
			   		String fullpkgpath=Sourcepath+"/IntegrationServer/instances/default/packages";
					File dir=new File(fullpkgpath);
					String[] files = dir.list();
					int count=0;
					if (files.length == 0) 
						{
							System.out.println("The directory is empty");
						} 
					else {	
		
							for (String aFile : files) 
								{
									System.out.println(aFile);
									count++;
								}
							}
					//System.out.println(count);
					return count;
			}
		   
		   
		   
		   
		   //get dir fn
		   public static void getlist(File dir)
		   {
			   System.out.println();
			   String path=null; 
				try {
					File[] files = dir.listFiles();
					for (File file : files) {
						if (file.isDirectory()) 
						{
							String type1="Folder:";
							System.out.println("Folder:" + file.getCanonicalPath());
							file.getCanonicalPath();
							Save(file.getCanonicalPath(),type1);
							getlist(file);
						} else {
							String type2="Contents:";
							System.out.println("     Contents:" + file.getCanonicalPath());
							path=file.getCanonicalPath();
							System.out.println();
							Save(file.getCanonicalPath(),type2);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
	}
		   
		   
		   
		   
		   
		   
		   
		   //save dir fn
		   public static void Save(String text,String type) throws IOException{
			   try
			   {
				   File file =new File("c:/output.txt");
			       //String filename= "c:/output.txt";
			       FileWriter fileWritter = new FileWriter(file,true);
			      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			       bufferWritter.write(type);
	    	        bufferWritter.write(text);
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
			       
			   }
			   catch(IOException ioe)
			   {
			       System.err.println("IOException: " + ioe.getMessage());
			   } 
			}
		   
		   
		   
		   
		   
		   
		   //save pkg count
		   public static void SavePkg(int count) throws IOException{
			   try
			   {
				   File file =new File("c:/output.txt");
				   
				   
			       //String filename= "c:/output.txt";
			       FileWriter fileWritter = new FileWriter(file,true);
			      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			       bufferWritter.write("Package count: "+count);
			      // bufferWritter.write();
			      bufferWritter.newLine();
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
			       
			   }
			   catch(IOException ioe)
			   {
			       System.err.println("IOException: " + ioe.getMessage());
			   } 
			 }
		   
		   
		   
		   
		   
		   //save node count
		   public static void SaveNodeCount(int count) throws IOException{
			   try
			   {
				   File file =new File("c:/output.txt");
				   
				   
			       //String filename= "c:/output.txt";
			       FileWriter fileWritter = new FileWriter(file,true);
			      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
			       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			       bufferWritter.write("Nodes count: "+count);
			      // bufferWritter.write();
			      bufferWritter.newLine();
	    	        bufferWritter.newLine();
	    	        bufferWritter.close();
			       
			   }
			   catch(IOException ioe)
			   {
			       System.err.println("IOException: " + ioe.getMessage());
			   } 
			}
		   
		   
		   
		   
		   //find node fn
		   public static int find() {
		    	 File f = new File("c:/output.txt");
		    	 String searchString= "node.ndf";
		        boolean result = false;
		        Scanner in = null;
		        int count=0;
		        try {
		            in = new Scanner(new FileReader(f));
		            while(in.hasNextLine() ) {
		                result = in.nextLine().indexOf(searchString) >= 0;
		                count++;
		            }
		        }
		        catch(IOException e) {
		            e.printStackTrace();      
		        }
		        finally {
		            try { in.close() ; } catch(Exception e) { /* ignore */ }  
		        }
		        
		        System.out.printf("Result of searching for %s in %s was %d\n", searchString, f.getName(),+count);
		        return count;
		        
		    }
		   
		   
		   
		   
		   //main fn
		  public static void main(String args[]) 
		  { 
			  FirstGUIExample gui = new FirstGUIExample();
			  gui.setVisible(true);
			  
		  }
		}

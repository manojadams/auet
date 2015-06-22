package sag;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;


public class dir {
	/*public static void main(String[] args) {
    File dir = new File("d:/SoftwareAG98/IntegrationServer/instances/default/packages");
    File[] files = dir.listFiles();
    FileFilter fileFilter = new FileFilter() {
       public boolean accept(File file) {
          return file.isDirectory();
       }
    };
    files = dir.listFiles(fileFilter);
    System.out.println(files.length);
    if (files.length == 0) {
       System.out.println("Either dir does not exist or is not a directory");
    }
    else {
       for (int i=0; i< files.length; i++) {
          File filename = files[i];
          System.out.println(filename.toString());
       }
    }
 }*/
	public static void main(String[] args) {
		File currentDir = new File("d:/SoftwareAG98/IntegrationServer/instances/default/packages"); // current directory
		displayDirectoryContents(currentDir);
	}

	public static void displayDirectoryContents(File dir) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					String path = file.getCanonicalPath();
					System.out.println("directory:" + file.getCanonicalPath());
					if(path.endsWith("node.ndf")){
						File writefile =new File("d:/output3.txt");
						   
						   
					       //String filename= "c:/output.txt";
					       FileWriter fileWritter = new FileWriter(writefile,true);
					      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
					       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					       bufferWritter.write(path);
					       bufferWritter.newLine();
					        bufferWritter.newLine();
					        bufferWritter.close();
					}
					displayDirectoryContents(file);
				} else {
					String path = file.getCanonicalPath();
					if(path.endsWith("node.ndf")){
						File writefile =new File("d:/output3.txt");
						   
						   
					       //String filename= "c:/output.txt";
					       FileWriter fileWritter = new FileWriter(writefile,true);
					      // FileWriter fw = new FileWriter(filename,true); //the true will append the new data
					       BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
					       bufferWritter.write(path);
					       bufferWritter.newLine();
					        bufferWritter.newLine();
					        bufferWritter.close();
					}
					System.out.println("     file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

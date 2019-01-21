package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import model.FileTIF;

public class Count {
	
	
	
	public static FTPClient openConnectionToServer(String nameSRV, String IP, int port, String user, String password) {
		
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(IP, port);
			ftpClient.login(user, password);			
			return ftpClient;
		}catch (Exception e) {
			System.out.println(e);
			return null;
		}
		
	}
	
	
	
	public static FileTIF countFilesTIFInServer(String pathFolder, String IP, int port, String user, String password)  {			
		
		
		int countFolders = 0;
		int countFiles = 0;	
		FileTIF reportFileTIF = new FileTIF();
		
		FTPClient ftpClient = openConnectionToServer(pathFolder, IP, port, user, password);
		String content = ""; 
		if(ftpClient.isConnected()) {
			
			// lists files and directories in the current working directory
			FTPFile[] files = null;
			try {
				files = ftpClient.listFiles(pathFolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						 
			System.out.println("Init counting...");		
			
			for (FTPFile file : files) {
			    String name = file.getName();		    
			    
			    if (file.isDirectory()) {
			    	countFolders++;
			    	FTPFile[] filesInFolder = null;
					try {
						filesInFolder = ftpClient.listFiles(pathFolder+"/"+name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			    	for(FTPFile nameFileInFolder : filesInFolder) {
			    		name = nameFileInFolder.getName();
			    		String extensao = name.substring(name.length()-3);
			    		if(extensao.equals("tif") || extensao.equals("TIF")) {
			    			countFiles++;
			    			
			    			content += "\n"+ name;
			    			System.out.println(countFiles + " - " + name);
			    		}
			    	}
			      
			    }
			   
			}
			
			reportFileTIF.setCountFiles(countFiles);
			reportFileTIF.setCountFolders(countFolders);
			
		}

		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("lista de arquivos no SRV do Detran.txt"), "utf-8"))) {
		writer.write(content);
		}catch (Exception e) {
			System.out.println(e);
		}finally {
			try {
				ftpClient.logout();
				ftpClient.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
		System.out.println(countFolders+" pastas encontradas.");
		System.out.println(countFiles+" arquivos .TIF encontrados.");
		return reportFileTIF;

	}
	
	
	public static FileTIF countFilesInFolderLocal(String pathFolder)  {

		int countFolders = 0;
		int countFiles = 0;
		FileTIF reportFileTIF = new FileTIF();
		String content = "";
		
		File baseFolder = new File(pathFolder);	
		
		if(baseFolder.exists()) {
			
			File[] listFolders = baseFolder.listFiles();
			
			System.out.println("Init counting...");		
			try {
				for (int index = 0; index < listFolders.length; index++) {
					
					File folder = listFolders[index];			
					content += "\n"+ folder;
					
					 if(!folder.getPath().endsWith(".*")){						
						File[] listFilesTIF = folder.listFiles();
						for (int index1 = 0; index1 < listFilesTIF.length; index1++) {
							
							if (listFilesTIF[index1].getPath().endsWith(".tif") || listFilesTIF[index1].getPath().endsWith(".TIF")) {
								countFiles++;
							}
						}	
						countFolders++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			saveListFileInTXT(content);
			
			reportFileTIF.setCountFiles(countFiles);
			reportFileTIF.setCountFolders(countFolders);		
			System.out.println(countFiles+" arquivos .TIF encontrados.");
			System.out.println(countFolders+" pastas encontradas.");
			return reportFileTIF;
			
		}
		
		return null;
	
	}
	
	
	public static String saveListFileInTXT(String content) {
		
		String nameFile = "lista de arquivos TIF.txt";
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(nameFile), "utf-8"))) {
		writer.write(content);
		return nameFile;
		}catch (Exception e) {
			System.out.println(e);
			return "";
		}
	}
	
	

}


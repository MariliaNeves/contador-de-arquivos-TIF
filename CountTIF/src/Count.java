
import java.io.File;
import java.io.IOException;

public class Count {
	
	
	public static void readFile(String filenames) {
		
	}

	public static void countFiles(String filenames) throws IOException {

		int totalArquivosEncontrados = 0;
		int totalPastasEncontradas=0;			
		
		File baseFolder = new File(filenames);		
		File[] listPastas = baseFolder.listFiles();
		for (int index = 0; index < listPastas.length; index++) {
			File pasta = listPastas[index];
			
			 if(!pasta.getPath().endsWith(".*")){						
				File[] listArquivosTIF = pasta.listFiles();
				for (int index1 = 0; index1 < listArquivosTIF.length; index1++) {
					
					if (listArquivosTIF[index1].getPath().endsWith(".tif") || listArquivosTIF[index1].getPath().endsWith(".TIF")) {
						totalArquivosEncontrados++;
					}
				}	
				totalPastasEncontradas++;
			}
		}
		System.out.println(totalArquivosEncontrados+" arquivos .TIF encontrados.");
		System.out.println(totalPastasEncontradas+" pastas encontradas.");
	}
	
	public static void main(String[] args) throws IOException {		
		String filenames = "\\\\srvimg01\\repositorio_2\\DOKMEE\\RENACH";
		countFiles(filenames);
	}

}

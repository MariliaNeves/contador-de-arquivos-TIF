package teste;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import main.Count;
import model.FileTIF;

class CountTest {


	@Test
	void testCountFilesInFolderLocal() {		
		FileTIF actual = Count.countFilesInFolderLocal("C:\\Users\\mcandi\\Documents\\");		
		assertTrue(actual.countFiles > 0);
		assertTrue(actual.countFolders > 0);
		
	}	
	
	@Test
	void testSaveListFileInTXT() {
		String actual = Count.saveListFileInTXT("Um texto para testar...");
		File file = new File(actual);
		assertTrue(file.exists());
	}
	
	@Test
	void testOpenConnectionToServer() {
		fail("Not yet implemented");
	}
	
	@Test
	void testCountFilesTIFInServer() {
		fail("Not yet implemented");
	}

}

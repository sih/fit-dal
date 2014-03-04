package eu.waldonia.labs;

import java.io.File;
import java.io.FilenameFilter;

import nom.tam.fits.Fits;

/**
 * 
 * @author sid
 *
 */
public class FITDirectoryTraverser {
    
    private String imageDir;

    /**
     * Command Pattern
     * @param command What to execute when traversing each file
     * @return the number of files successfully processed by the supplied command
     */
    public int traverse(FITFileCommand command) {
	
	int successfullyProcessed = 0;
	if (null != imageDir) {
	    
	    File dir = new File(imageDir);
	    if (dir.isDirectory()) {
		// only both processing FITS files
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File directory, String fileName) {
			return fileName.endsWith(".fits");
		    }
		};
		// list out all the FITS files
		File[] fitFiles = dir.listFiles(filter);
		for (int i = 0; i < fitFiles.length; i++) {
		    try {
			Fits f = new Fits(fitFiles[i]);
			command.process(f);
			successfullyProcessed++;
		    } 
		    catch (Exception e) {
			e.printStackTrace();
		    }
		}
		
	    }
	}	
	else {
	    throw new IllegalArgumentException("You need to set an image directory to traverse");
	}
	
	return successfullyProcessed;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }
    
    
    
}

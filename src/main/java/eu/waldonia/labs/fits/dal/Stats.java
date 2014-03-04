package eu.waldonia.labs.fits.dal;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;

import nom.tam.fits.BasicHDU;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;

/**
 * Performs statistics on a dataset. This expects to be initialised with a directory
 * containing a number of FITS files.
 * @author sid
 */
public class Stats {
    
    private static String ID = "ID";
    private static String NO_ID = "NO_ID";
    
    private String imageDir;

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }
    
    /**
     * @return A Map with the number of files that have a Kepler Id versus those that don't
     */
    public Map<String,Integer> getKeplerIdStats() {
	long start = System.currentTimeMillis();
	Map<String,Integer> stats = new HashMap<String,Integer>();
	stats.put(ID, null);
	stats.put(NO_ID, null);
	
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
		int noId = 0;
		int id = 0;
		for (int i = 0; i < fitFiles.length; i++) {
		    try {
			Fits f = new Fits(fitFiles[i]);
			// get the headers from each file
			BasicHDU[] bhdus = f.read();
			for (int j = 0; j < bhdus.length; j++) {
			    BasicHDU hdu = bhdus[j];
			    // stat me
			    Object kid = hdu.getObject();
			    if (null == kid) noId++;
			    else id++;
			    
			}
		    } 
		    catch (FitsException e) {
			e.printStackTrace();
		    }
		}
		
		stats.put(ID, id);
		stats.put(NO_ID, noId);
		System.out.println("Collected stats for "+(id+noId)+" files in "+((System.currentTimeMillis()-start)/1000)+"s");
		
	    }
	    
	}
	return stats;
    }
    
    
    public static void main(String[] args) {
	Stats s = new Stats();
	s.setImageDir("/Users/sid/data/kepler/Q0_public/");
	Map<String,Integer> idStats = s.getKeplerIdStats();
	int id = idStats.get(ID);
	int noId = idStats.get(NO_ID);
	int total = id + noId;
	System.out.println("With Id: "+id+"/"+total);
	System.out.println("Without Id: "+noId+"/"+total);	
    }
}

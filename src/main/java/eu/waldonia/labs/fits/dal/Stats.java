package eu.waldonia.labs.fits.dal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import nom.tam.fits.BasicHDU;
import nom.tam.fits.Data;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsFactory;
import nom.tam.fits.Header;
import eu.waldonia.labs.FITDirectoryTraverser;
import eu.waldonia.labs.FITFileCommand;

/**
 * Performs statistics on a dataset. This expects to be initialised with a
 * directory containing a number of FITS files.
 * 
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
     * @return A Map with the number of files that have a Kepler Id versus those
     *         that don't
     */
    public Map<String, Integer> getKeplerIdStats() {
	long start = System.currentTimeMillis();
	Map<String, Integer> stats = new HashMap<String, Integer>();
	stats.put(ID, null);
	stats.put(NO_ID, null);

	FITDirectoryTraverser traverser = new FITDirectoryTraverser();
	traverser.setImageDir("/Users/sid/data/kepler/Q0_public/");

	IdCounter counter = new IdCounter();
	traverser.traverse(counter);

	stats.put(ID, counter.getId());
	stats.put(NO_ID, counter.getNoId());

	return stats;
    }

    public void nosey() {
	long start = System.currentTimeMillis();

	FITDirectoryTraverser traverser = new FITDirectoryTraverser();
	traverser.setImageDir("/Users/sid/data/kepler/Q0_public/");

	FileInvestigator fi = new FileInvestigator(10);
	traverser.traverse(fi);

    }

    public static void main(String[] args) {
	Stats s = new Stats();
	s.setImageDir("/Users/sid/data/kepler/Q0_public/");
	/*
	 * Map<String,Integer> idStats = s.getKeplerIdStats(); int id =
	 * idStats.get(ID); int noId = idStats.get(NO_ID); int total = id +
	 * noId; System.out.println("With Id: "+id+"/"+total);
	 * System.out.println("Without Id: "+noId+"/"+total);
	 */
	s.nosey();
    }

    class IdCounter implements FITFileCommand {

	private int id;
	private int noId;

	@Override
	public void process(Fits fits, File file) throws Exception {
	    // get the headers from each file
	    BasicHDU[] bhdus = fits.read();
	    for (int j = 0; j < bhdus.length; j++) {
		BasicHDU hdu = bhdus[j];
		String kid = hdu.getObject().toString();
		if (null == kid)
		    noId++;
		else
		    id++;
	    }
	}

	public int getId() {
	    return id;
	}

	public int getNoId() {
	    return noId;
	}
    }

    class FileInvestigator implements FITFileCommand {

	int counter = 0;
	int maxFiles = 0;
	
	final static int DEFAULT_MAX_FILES = 10;
	
	FileInvestigator() {
	    this(DEFAULT_MAX_FILES);
	}
	
	FileInvestigator (int maxFiles) {
	    this.maxFiles = maxFiles;
	}

	@Override
	public void process(Fits fits, File file) throws Exception {
	    // pick the first 10 files
	    if (counter < maxFiles) {
		// get the headers from each file
		BasicHDU[] bhdus = fits.read();
		for (int j = 0; j < bhdus.length; j++) {
		    BasicHDU hdu = bhdus[j];
		    Header hdr = hdu.getHeader();
		    Data d = FitsFactory.dataFactory(hdr);
		    System.out.println(file.getAbsolutePath() + " => "
			    + d.getClass().toString());
		}
		counter++;

	    }

	}

    }

}

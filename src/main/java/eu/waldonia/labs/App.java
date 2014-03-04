package eu.waldonia.labs;

import nom.tam.fits.BasicHDU;
import nom.tam.fits.BinaryTableHDU;
import nom.tam.fits.Fits;
import nom.tam.fits.FitsException;
import nom.tam.fits.TableHDU;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args ) {
	
	try {
	    Fits f = new Fits("/Users/sid/data/kepler/Q0_public/kplr009953741-2009131105131_llc.fits");
	    BasicHDU[] bhdus = f.read();
	    for (int i = 0; i < bhdus.length; i++) {
		BasicHDU hdu = bhdus[i];
		System.out.println("Type: "+hdu.getClass());
    	    	System.out.println("Observation Date: "+hdu.getObservationDate());
    	    	System.out.println("Creation Date: "+hdu.getCreationDate());
    	    	System.out.println("Kepler Id: "+hdu.getObject());
    	    	System.out.println("Origin: "+hdu.getOrigin());
    	    	System.out.println("Observer: "+hdu.getObserver());
    	    	System.out.println("Telescope: "+hdu.getTelescope()); 
    	    	System.out.println("Reference: "+hdu.getReference());   
    	    	
    	    	

    	    	if (hdu instanceof TableHDU) {
    	    	    TableHDU t = (TableHDU)hdu;
    	    	    
    	    	    
    	    	    int numCols = t.getNCols();
    	    	    for (int j = 0; j < numCols; j++) {
			System.out.println(t.getColumnName(j));
		    }
    	    	    
    	    	}
    	    	
    	    	System.out.println("---------------------------------");
	    }
	} 
	catch (FitsException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }
}

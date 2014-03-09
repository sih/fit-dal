package eu.waldonia.labs.fits.dal;

import eu.waldonia.labs.FITDirectoryTraverser;

/**
 * Redis-backed implementation of the data service
 * @author sid
 *
 */
public class RedisLightCurveDataService implements LightCurveDataService {

    
    @Override
    public int indexByKeplerId() throws Exception {
	
	FITDirectoryTraverser traverser = new FITDirectoryTraverser();
	traverser.setImageDir("/Users/sid/data/kepler/Q0_public/");
	int processed = traverser.traverse(new RedisFileIndexer());
	
	return processed;
    }
    
    public static void main(String[] args) {
	long start = System.currentTimeMillis();
	RedisLightCurveDataService rlcds = new RedisLightCurveDataService();
	
	try {
	   int count = rlcds.indexByKeplerId();
	   long elapsed = (System.currentTimeMillis() - start)/1000;
	   System.out.println("Took "+elapsed+"s to index "+count+" objects");
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    
}

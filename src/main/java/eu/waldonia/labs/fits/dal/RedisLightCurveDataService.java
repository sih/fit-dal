package eu.waldonia.labs.fits.dal;

import nom.tam.fits.BasicHDU;
import nom.tam.fits.Fits;
import redis.clients.jedis.Jedis;
import eu.waldonia.labs.FITDirectoryTraverser;
import eu.waldonia.labs.FITFileCommand;

/**
 * Redis-backed implementation of the data service
 * @author sid
 *
 */
public class RedisLightCurveDataService implements LightCurveDataService, FITFileCommand {

    @Override
    public int indexByKeplerId() throws Exception {
	
	FITDirectoryTraverser traverser = new FITDirectoryTraverser();
	traverser.setImageDir("/Users/sid/data/kepler/");
	int processed = traverser.traverse(new RedisLightCurveDataService());
	
	return processed;
    }
    
    
    @Override
    public void process(Fits f) throws Exception {
	// get the headers from each file
	BasicHDU[] bhdus = f.read();
	for (int j = 0; j < bhdus.length; j++) {
	    BasicHDU hdu = bhdus[j];

	    Object kid = hdu.getObject();
	    
	    // TODO store me in redis
	    
	}	
    }

    
    private Jedis getConnection() {
	return new Jedis("localhost");
    }

    
}

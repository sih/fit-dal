package eu.waldonia.labs.fits.dal;

import java.io.File;

import nom.tam.fits.BasicHDU;
import nom.tam.fits.Fits;
import redis.clients.jedis.Jedis;
import eu.waldonia.labs.FITFileCommand;

public class RedisFileIndexer implements FITFileCommand {
    private static final String FILES_PER_KID = "FilesPerKId";
    
    Jedis jedis = new Jedis("localhost");
    
    @Override
    public void process(Fits fits, File file) throws Exception {
	// get the headers from each file
	BasicHDU[] bhdus = fits.read();
	for (int j = 0; j < bhdus.length; j++) {
	    BasicHDU hdu = bhdus[j];

	    String kid = hdu.getObject().toString();

	    String fileList = jedis.hget(FILES_PER_KID, kid);
	    if (null != fileList) fileList=fileList+","+file.getAbsolutePath();
	    else fileList = file.getAbsolutePath();
	    
	    jedis.hset(FILES_PER_KID, kid.toString(), fileList);
	    
	}	
    }
    
}

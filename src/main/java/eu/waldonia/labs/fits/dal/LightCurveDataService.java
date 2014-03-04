package eu.waldonia.labs.fits.dal;

/**
 * 
 * @author sid
 *
 */
public interface LightCurveDataService {

    /**
     * 
     * @return The number of Kepler Ids stored
     * @throws Exception When no indexing can be done
     */
    public int indexByKeplerId() throws Exception;
    
}

package eu.waldonia.labs;

import nom.tam.fits.Fits;

/**
 * Interface for Command pattern so that the FITDirectoryTraverser can be called with different operations
 * @author sid
 *
 */
public interface FITFileCommand {
    
    /**
     * @param f The DirectoryTraveresr will call the process method on the command it's passed
     */
    public void process(Fits f) throws Exception;

}

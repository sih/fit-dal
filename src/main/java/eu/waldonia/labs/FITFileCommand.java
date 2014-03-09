package eu.waldonia.labs;

import java.io.File;

import nom.tam.fits.Fits;

/**
 * Interface for Command pattern so that the FITDirectoryTraverser can be called with different operations
 * @author sid
 *
 */
public interface FITFileCommand {
    
    /**
     * @param fits The DirectoryTraveresr will call the process method on the command it's passed
     * @param file The file containing the data
     */
    public void process(Fits fits, File file) throws Exception;

}

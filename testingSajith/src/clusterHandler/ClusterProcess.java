/**
 * 
 */
package clusterHandler;

import weka.core.Instance;
import weka.core.Instances;
import weka.clusterers.SimpleKMeans;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import objectStructures.User;
import sun.util.locale.provider.LocaleServiceProviderPool.LocalizedObjectGetter;

import java.util.logging.Logger;
import java.util.logging.Level;

import log.LogFactory;

/**
 * @author Sajith Dananjaya
 *
 */
public class ClusterProcess {

	private static final Logger LOGGER = LogFactory.getNewLogger(ClusterProcess.class.getName());

	private SimpleKMeans dataGraph;
	private boolean clusterInitiated = false;

	public int getUsers(User user) {
		return 0;
	}

	public void buildGraph(String filePath) {
		Instances userPoints;
		
		LOGGER.log(Level.WARNING, "May throw exception for poorly formated data files");
		try{
			userPoints = new Instances(getDataFile(filePath));
			
		}catch(IOException e){
			
			LOGGER.log(Level.SEVERE, "Error@ClusterProcess_initiateCluster", e);
		}
		
	}

	private void initiateClusters(int randomSeedMax, int clusterCount) {
		if (!clusterInitiated) {
			dataGraph = new SimpleKMeans();
			dataGraph.setPreserveInstancesOrder(true);
			dataGraph.setSeed(randomSeedMax);

			LOGGER.log(Level.WARNING, "May throw exception if the cluster count is a negative value");
			try {
				dataGraph.setNumClusters(clusterCount);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Error@ClusterProcess_initiateCluster", e);
				clusterCount = ((-1) * clusterCount)+1;
				initiateClusters(randomSeedMax, clusterCount);
			}
			clusterInitiated = true;
		}
	}

	private BufferedReader getDataFile(String filePath) {
		BufferedReader inputReader = null;
		
		LOGGER.log(Level.WARNING, "May throw file not found exception for given path "+filePath);
        try {
            inputReader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
        	LOGGER.log(Level.SEVERE, "Error@ClusterProcess_getDataFile", e);
        }
        return inputReader;
	}

	private Instance toInstance(User user,Instances dataSet) {
		return null;
	}
	
	private int seedAmoutn(Instances dataPoints){
		return 100;
	}
	
	private int clusterCount(Instances dataPoints){
		int totalDataCount = dataPoints.numInstances();
		return (int)totalDataCount/3;
	}

}

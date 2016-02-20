/**
 * 
 */
package log;


import java.util.logging.Logger;
import java.util.logging.Handler;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;


/**
 * @author Sajith Dananjaya
 *
 */
public class LogFactory {
	
	private static Handler logFile;
	
	
	public static Logger getNewLogger(String loggerName){
		Logger tempLogger = Logger.getLogger(loggerName);
		if(logFile==null){
			initiateHanlder();
		}
		
		tempLogger.addHandler(logFile);
		logFile.setLevel(Level.ALL);
		tempLogger.setLevel(Level.ALL);
		return tempLogger;
	}
	
	private static void initiateHanlder(){
		try{
			logFile=new FileHandler("./appLog.log");
		}catch(IOException e){
			System.err.println("Error@initiateHanlder");
			System.err.println(e.toString());
		}
	}

}

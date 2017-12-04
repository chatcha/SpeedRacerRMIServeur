
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brasc
 */
public class SpeedRacer {
      public static final ArenaParty party = new ArenaParty();
    
	public static void main(String[] args) {
		
	/*	String portNum, registryURL;
            
            try {
                
            registryURL="rmi://"+127.0.0.1+":"+ 1099 + "/GameServer" ;
            Registry registry = LocateRegistry.createRegistry(Constants.SERVER_PORT);
             GameEngine exportedObj = new GameEngine(party);
            registry.rebind(Constants.SERVER_PATH, exportedObj);
            System.out.println("RMI Server started");
        } catch (RemoteException ex) {
            Logger.getLogger(SpeedRacer.class.getName()).log(Level.SEVERE, null, ex);
        }*/
  String portNum, registryURL;
   
    try{     

  
   
      int RMIPortNum = 1099;
      String url = "127.0.0.1";
      startRegistry(RMIPortNum);
      
      GameEngine exportedObj = 
        new GameEngine(party);
      
       
      registryURL="rmi://"+url+":"+ 1099 + "/GameServer";
    
      Naming.rebind(registryURL, exportedObj);
      System.out.println("Server ready."+InetAddress.getLocalHost().getHostAddress());
    }// end try
    catch (Exception re) {
      System.out.println(
        "Exception in LightBikesServer.main: " + re);
    } // end catch
    
  } // end main
	
	//This method starts a RMI registry on the local host, if
  //it does not already exists at the specified port number.
  private static void startRegistry(int RMIPortNum)
    throws RemoteException{
    try {
      Registry registry = 
        LocateRegistry.getRegistry(RMIPortNum);
      registry.list( );  
        // This call will throw an exception
        // if the registry does not already exist
    }
    catch (RemoteException e) { 
      // No valid registry at that port.
      Registry registry = 
        LocateRegistry.createRegistry(RMIPortNum);
    }
  } // end startRegistry
}

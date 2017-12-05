
import java.rmi.*;
import java.util.Vector;

public interface ClientInterface
        extends java.rmi.Remote {
    // This remote method is invoked by a callback
    // server to make a callback to an client which
    // implements this interface.
    // @param message - a string containing information for the
    //                  client to process upon being called back.

    public void newGrid()
            throws java.rmi.RemoteException;

    public void update(Vector<Rectangle> vDisplayRoad, Vector<Rectangle> vDisplayObstacles, Vector<Rectangle> vDisplayCars, Car myCar, int pos, int nbParticipants, boolean bGameOver, String sPosition) throws java.rmi.RemoteException;

    public String getName() throws RemoteException;

    /**
     * Pour que le serveur envoie les nouvelles partie au fur et à mesure
     * qu'elles s'ajoutent
     *
     * @param party
     * @return
     * @throws RemoteException
     */
    public boolean addParty(String party) throws RemoteException;

    /**
     * Pour que le serveur envoie l'information au client quand on supprime une
     * partie
     *
     * @param party
     * @return
     * @throws RemoteException
     */
    public boolean removeParty(String party) throws RemoteException;

    public void setEnabled(boolean flag) throws java.rmi.RemoteException;

} // end interface

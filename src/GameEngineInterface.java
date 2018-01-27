
import java.awt.Color;
import java.rmi.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public interface GameEngineInterface
        extends java.rmi.Remote {

    /**
     * quand le client se connecte pour la prmiere fois
     *
     * @param client
     * @return
     * @throws RemoteException
     */
    public long connect(ClientInterface client) throws RemoteException;

    /**
     * Quand le client se déconnecte et quitte completement le jeu
     *
     * @param userId
     * @throws RemoteException
     */
    public void disconnect(long userId) throws RemoteException;

    /**
     * Liste des parties que le client peut demandé
     *
     * @return
     * @throws RemoteException
     */
    public Set<String> listArenas() throws RemoteException;

    /**
     * Quand le client veut rejoinde une partie existante
     *
     * @param userId
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean joinArena(long userId, String name) throws RemoteException;

    /**
     * Pour quitter une partie
     *
     * @param userId
     * @return
     * @throws RemoteException
     */
    public boolean leaveArena(long userId) throws RemoteException;

    /**
     * Créer une nouvelle partie
     *
     * @param userId
     * @param name
     * @return
     * @throws RemoteException
     */
    public boolean createArena(long userId, String name) throws RemoteException;

   /**
    * La liste des scores de la partie dans laquelle le client il est
    * @param userId
    * @return
    * @throws RemoteException 
    */
    public Map<String, Integer> getScores(long userId) throws RemoteException;

   /**
    * Renvoyer la liste Vector containing the obstacles to display in the
    * sliding window (layer 2)
    * @param userId
    * @param name
    * @return
    * @throws RemoteException 
    */
    public Vector<Rectangle> listvDisplayObstacles(long userId, String name) throws RemoteException;

    /**
     * Pour démarrer une partie
     *
     * @param userId
     * @return
     * @throws RemoteException
     */
    public boolean startGame(long userId) throws RemoteException;

    /**
     * Pour lancer une partie
     * @param userId
     * @throws RemoteException 
     */
    public void beginGame(long userId) throws RemoteException;

  /**
   * Initialisation de la grille
   * @param userId
   * @throws RemoteException 
   */
    public void newGrid(long userId) throws RemoteException;

    /**
     * Renvoyer la liste des voitures à afficher dans la fénètre coullisante
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    public Vector<Rectangle> listvDisplayCars(long userId, String name) throws RemoteException;

    /**
     * Renvoyer la liste contenant les voitures
     *
     * @param userId
     * @param name
     * @return
     * @throws RemoteException
     */
    public Vector<Car> listvCars(long userId, String name) throws RemoteException;

    /**
     * Renvoyer la liste contenant les éléments de la route dans la fenètre
     * coulissante (couche1)
     *
     * @param userId
     * @param name
     * @return
     * @throws RemoteException
     */
    public Vector<Rectangle> listvDisplayRoad(long userId, String name) throws RemoteException;

    /**
     * 
     * @param userId
     * @return
     * @throws RemoteException 
     */
    public int getScoreClient(long userId) throws RemoteException;

    /**
     * 
     * @param userId
     * @param choice
     * @param flag
     * @throws RemoteException 
     */
    public void moveCar(long userId, String choice, boolean flag) throws RemoteException;

} // end interface

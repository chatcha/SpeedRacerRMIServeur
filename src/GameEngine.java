
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author brasc
 */
public class GameEngine extends UnicastRemoteObject implements GameEngineInterface {

    private ArenaParty party;

    public GameEngine(ArenaParty party) throws RemoteException {
        super();
        this.party = party;
    }

    /**
     * Crée un ClientLink a partir d'un client RMI et le connecte au système
     *
     * @param client le client RMI qui se connecte
     * @return l'id unique généré pour ce client
     * @throws RemoteException
     */
    @Override
    public long connect(ClientInterface client) throws RemoteException {
        Player cLink = new Player(client.getName(),client, party);

        if (party.connect(cLink)) {
            System.out.println("Nouveau client connecté : " + client.getName());
            party.setgGUI(client);
            return cLink.getId();
        }
        System.out.println("Erreur de connexion du client : " + client.getName());
        return 0L;
    }

    /**
     * Deconnecte un client par son ID.
     *
     * @param userId id unique du client
     * @throws RemoteException
     */
    @Override
    public void disconnect(long userId) throws RemoteException {
        party.disconnect(userId);
    }

    /**
     * Retourne la liste des arena disponibles
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public HashSet<String> listArenas() throws RemoteException {
        return party.listArenas();
    }

    /**
     * Rejoint une arena existante, par son nom
     *
     * @param userId id du client qui veut rejoindre l'arena
     * @param name nom de l'arena
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean joinArena(long userId, String name) throws RemoteException {
        return party.joinArena(userId, name);
    }

    /**
     * Quitte l'arene dans laquelle le client se trouve
     *
     * @param userId id du client qui veut quitter son arena
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean leaveArena(long userId) throws RemoteException {
        return party.leaveArena(userId);
    }

    /**
     * Crée une arena
     *
     * @param userId id du client qui veut créer une arena
     * @param name nom de l'arene à créer
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean createArena(long userId, String name) throws RemoteException {
        return party.createArena(userId, name);
    }

    /**
     * Renvoie la liste des couleurs encores disponibles de l'arena à laquelle
     * participe le client userId
     *
     * @param userId
     * @return
     * @throws RemoteException
     */
    /**
     * Renvoie les scores de la partie en cours
     *
     * @param userId id du client qui fait la demande
     * @return
     * @throws RemoteException
     */
    @Override
    public HashMap<Long, Integer> getScores(long userId) throws RemoteException {
        return party.getScores(userId);
    }

    /**
     * Démarre une partie, pour ça le client a du se connecter à une arena et
     * choisir sa couleur
     *
     * @param userId
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean startGame(long userId) throws RemoteException {
        return party.startGame(userId);
    }

    @Override
    public Vector<Rectangle> listvDisplayCars(long userId, String name) throws RemoteException {

        return party.getVDisplayCars(userId, name);
    }

    @Override
    public Vector<Car> listvCars(long userId, String name) throws RemoteException {

        return party.getVCars(userId, name);
    }

    @Override
    public Vector<Rectangle> listvDisplayRoad(long userId, String name) throws RemoteException {

        return party.getVDisplayRoad(userId, name);
    }

    @Override
    public Vector<Rectangle> listvDisplayObstacles(long userId, String name) throws RemoteException {

        return party.listvDisplayObstacles(userId, name);
    }

    @Override
    public void newGrid(long userId) throws RemoteException {
        party.newGrid(userId);
    }

    @Override
    public void beginGame(long userId) throws RemoteException {
        party.beginGame(userId);
    }

    @Override
    public void moveCar(long userId, String choice, boolean flag) throws RemoteException {

        party.moveCar(userId, choice, flag);
    }

    @Override
    public int getScoreClient(long userId) throws RemoteException {
        
       return party.getScoreClient(userId);
    }

    @Override
    public int iFinalPosition(long userId,String name ) throws RemoteException {
        return party.iFinalPosition(userId,name);
    }

    @Override
    public String sFinalPosition(long userId,String name) throws RemoteException {
         return party.sFinalPosition(userId, name);
    }

    @Override
    public boolean bGameFinishing(long userId,String name) throws RemoteException {
         return party.bGameFinishing(userId,name);
    }

    @Override
    public int iNbParticipants(long userId,String name) throws RemoteException {
        return party.iNbParticipants(userId,name);
    }

    
}

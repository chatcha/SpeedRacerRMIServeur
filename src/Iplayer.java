
import java.security.SecureRandom;
import java.util.ArrayList;
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
public abstract class Iplayer {

    /**
     * Un random normalement plus sécurisé que le Random classique, Il est
     * réputé non devinable.
     *
     */
    private static final SecureRandom prng = new SecureRandom();

    /**
     * Nom du client choisi à la connexion
     */
    private final String name;

    /**
     * Utiliser le Random pour generer un id unisque au client
     */
    private final long userId = prng.nextLong();

    /**
     * Pour enregistrer la partie à laquelle le client appartient
     */
    private Core core = null;

    protected Iplayer(String name) {
        this.name = name;
    }

    /**
     * C'est la méthode qui est appellée par l'arena quand elle veut lancer la
     * mise à jour du client Cette méthode doit être implémentée par le système
     * de communication (RMI...)
     *
     * @param vTiles La liste des tiles à jour
     * @param bGameOver Indique si la partie est terminée
     * @param sWinner Le gagnant, si la partie est terminée
     */
    public abstract void update(Vector<Rectangle> vDisplayRoad, Vector<Rectangle> vDisplayObstacles, Vector<Rectangle> vDisplayCars, Car myCar, int pos, int nbParticipants, boolean bGameOver, String sPosition) throws java.rmi.RemoteException;

    public abstract void addArena(String name);

    public abstract void removeArena(String name);

    /**
     * Retourne l'id unique du client
     *
     * @return
     */
    public long getId() {
        return this.userId;
    }

    public String getName() {
        return name;
    }

    /**
     * Fait entrer le client dans une arena
     *
     * @param arena
     * @return
     */
    public boolean setCore(Core core) {
        if (core == null || this.core == null) {
            this.core = core;
            return true;
        }
        return false;
    }

    public Core getCore() {
        return core;
    }

    /**
     * Fait entrer le client dans une arena
     *
     * @param arena
     * @return
     */
    public boolean setArena(Core core) {
        if (core == null || this.core == null) {
            this.core = core;
            return true;
        }
        return false;
    }

    public Core getArena() {
        return core;
    }
}

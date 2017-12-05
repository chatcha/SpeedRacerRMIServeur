
import java.awt.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * 
 * Une classe qui va contenir tous les joueurs connectés 
  et toutes les parties crées du jeu.
 * 
 */
public class ArenaParty {

    /**
     * A reference to the graphical user interface
     */
    private ClientInterface gGUI;
    /**
     * Une map qui va contenir toutes les parties crées
     */
    private final Map<String, Core> arenasByName = new HashMap<>();

    /**
     * une liste des clients par leurs nom,
     */
    private final Map<String, Iplayer> clientsByName = new HashMap<>();

    /**
     * une liste des clients par leurs id, pour les retrouver quand ils
     * communiquent avec le serveur
     */
    private final Map<Long, Iplayer> clientsById = new HashMap<>();

    /**
     * Utiliser pour le netoyage du serveur, après combien temps on supprime une
     * partie terminée
     */
    private final long maxage = 30000;

    /**
     * Pour la boucle du netoyage, on boucle tous X 1000s
     */
    private final int checkTime = 10000;

    public ArenaParty() {
        startManagementThread();
    }

    /**
     * Fonctions du netoyage des veilles parties
     */
    private void startManagementThread() {
        Thread serverManagement = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(checkTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ArenaParty.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    long now = System.currentTimeMillis();
                    // On fait une copie de la liste des partie sur laquelle on boucle car
                    // on ne peut pas modifier un Iterable sur lequel on boucle.
                    (new HashSet(arenasByName.keySet())).forEach(name -> {
                        Core arena = arenasByName.get(name);
                        if ((now - arena.getStoppedAt()) > maxage) {
                            synchronized (ArenaParty.this) {
                                arenasByName.remove(name);
                                arena.clearGame();
                                clientsById.values().forEach((updateclient) -> {
                                    updateclient.removeArena((String) name);
                                });
                            }
                        }
                    });
                }
            }
        };
        serverManagement.start();
    }

    /**
     * Quand un client se connecte pour la premiere fois
     *
     * @param client
     * @return
     */
    public boolean connect(Iplayer client) {
        if (!clientsByName.containsKey(client.getName())) {
            clientsByName.put(client.getName(), client);
            clientsById.put(client.getId(), client);
            return true;
        }
        return false;
    }

    /**
     * Deconnecte un client du systeme Si le client est dans une arena, on le
     * retire également
     *
     * @param userId
     */
    public void disconnect(long userId) {
        Iplayer client = clientsById.remove(userId);
        if (client != null) {
            clientsByName.remove(client.getName());
            Core arena = client.getArena();
            if (arena != null) {
                arena.removePlayer(client);
            }
        }
    }

    /**
     * Renvoie un Set de String de toutes les Arena créées
     *
     * @return
     */
    public HashSet<String> listArenas() {
        return new HashSet(arenasByName.keySet());
    }

    /**
     * Permet a un client de rejoindre une arena
     *
     * @param userId
     * @param name
     * @return
     */
    public boolean joinArena(long userId, String name) {
        Iplayer client = clientsById.get(userId);
        Core arena = arenasByName.get(name);
        return client != null && arena != null && arena.addPlayer(client);
    }

    /**
     * permet a un client de quitter une arena
     *
     * @param userId
     * @return
     */
    public boolean leaveArena(long userId) {
        Iplayer client = clientsById.get(userId);
        if (client != null) {
            Core arena = client.getArena();
            return arena != null && arena.removePlayer(client);
        }
        return false;
    }

    /**
     * Permet a un client de créer une arena
     *
     * @param userId
     * @param name
     * @return
     */
    public boolean createArena(long userId, String name) {
        Iplayer client = clientsById.get(userId);
        if (client != null) {
            Core arena = client.getArena();
            if (arena == null && arenasByName.get(name) == null) {
                arenasByName.put(name, new Core(gGUI));
                clientsById.values().forEach((updateclient) -> {
                    updateclient.addArena(name);
                });
                return true;
            }
        }
        return false;
    }

    /**
     * Retourn la liste des scores, par couleur
     *
     * @param userId
     * @return
     */
    public HashMap<String, Integer> getScores(long userId) {
        Iplayer client = clientsById.get(userId);
        if (client != null) {
            Core arena = client.getArena();
            if (arena != null) {
                return arena.getScores();
            }
        }
        return null;
    }

    /**
     * Démarre la partie dans laquelle se trouve le client
     *
     * @param userId
     * @return
     */
    public boolean startGame(long userId) {
        Iplayer client = clientsById.get(userId);
        if (client != null) {
            Core arena = client.getArena();
            if (arena != null) {
                return arena.startGame();
            }
        }
        return false;
    }

    /**
     * Démarre la partie dans laquelle se trouve le client
     *
     * @param userId
     * @return
     */
    public void newGrid(long userId) {
        Iplayer client = clientsById.get(userId);
        if (client != null) {
            Core arena = client.getArena();
            if (arena != null) {
                arena.newGrid();

            }
        }

    }

    /**
     * Lance la partie dans laquelle se trouve le client
     *
     * @param userId
     * @return
     */
    public void beginGame(long userId) {
        Iplayer client = clientsById.get(userId);
        if (client != null) {
            Core arena = client.getArena();
            if (arena != null) {
                arena.beginGame();

            }
        }

    }

    /**
     * Lance la partie dans laquelle se trouve le client
     *
     * @param userId
     * @return
     */
    public void moveCar(long userId, String choice, boolean flag) {
        Iplayer client = clientsById.get(userId);
        if (client != null) {
            Core arena = client.getArena();
            if (arena != null) {
                arena.moveCar(choice, flag);

            }
        }

    }

    /**
     * Pour avoir la liste des parties
     *
     * @return
     */
    public Set<Core> getArenas() {
        return new HashSet(arenasByName.values());
    }

    /**
     * Renvoie la liste des VDisplayRoad initiales
     *
     * @param id
     * @param name
     * @return
     */
    public Vector<Rectangle> getVDisplayRoad(long id, String name) {
        Core arena = arenasByName.get(name);
        Iplayer client = clientsById.get(id);
        if (arena != null && client != null) {
            return arena.vDisplayRoad;
        }
        return null;
    }

    /**
     * Renvoie la liste des "voitures" initiales
     *
     * @param id
     * @param name
     * @return
     */
    public Vector<Car> getVCars(long id, String name) {
        Core arena = arenasByName.get(name);
        Iplayer client = clientsById.get(id);
        if (arena != null && client != null) {
            return arena.vCars;
        }
        return null;
    }

    /**
     * Renvoie la liste des "VDisplayCars" initiales
     *
     * @param id
     * @param name
     * @return
     */
    public Vector<Rectangle> getVDisplayCars(long id, String name) {
        Core arena = arenasByName.get(name);
        Iplayer client = clientsById.get(id);
        if (arena != null && client != null) {
            return arena.vDisplayCars;
        }
        return null;
    }

    /**
     * Renvoie la liste des "VDisplayCars" initiales
     *
     * @param id
     * @param name
     * @return
     */
    public Vector<Rectangle> listvDisplayObstacles(long id, String name) {
        Core arena = arenasByName.get(name);
        Iplayer client = clientsById.get(id);
        if (arena != null && client != null) {
            return arena.vDisplayObstacles;
        }
        return null;
    }

    public void setgGUI(ClientInterface gGUI) {
        this.gGUI = gGUI;
    }

}

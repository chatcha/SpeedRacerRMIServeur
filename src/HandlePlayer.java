
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class HandlePlayer {
    
     /**
     * A reference to the graphical user interface
     */
    private ClientInterface  gGUI;
    
      /**
     * Une map qui va contenir toutes les parties crées
     */
    private final Map<String, Core> arenasByName = new HashMap<>();
    
   
    
    /**Core
     * une liste des clients par leurs nom, 
     */
    private final Map<String, Iplayer> clientsByName = new HashMap<>();
    
    /**
     * une liste des clients par leurs id, pour les retrouver quand ils communiquent
     * avec le serveur 
     */
    private final Map<Long, Iplayer> clientsById = new HashMap<>();
    
    /**
     * Utiliser pour le netoyage du serveur, après combien temps on supprime 
     * une partie terminée
     */
    private final long maxage = 30000;
    
    /**
     * Pour la boucle du netoyage, on boucle tous X 1000s
     */
    private final int checkTime = 10000;

    public HandlePlayer() {
       // startManagementThread();
    }

    /**
     * Fonctions du netoyage des veilles parties
     */
 /*   private void startManagementThread() {
        Thread serverManagement = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(checkTime);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
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
                                    updateclient.removeArena((String)name);
                                });
                            }
                        }
                    });
                }
            }
        };
        serverManagement.start();
    } */

    /**
     * Quand un client se connecte pour la premiere fois
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
                arenasByName.put(name, new Core( gGUI));
                clientsById.values().forEach((updateclient) -> {
                    updateclient.addArena(name);
                });
                return true;
            }
        }
        return false;
    }

    /**
     * permet a un client de faire une requete de déplacement de ses troupes
     * TODO: tester si le déplacement demandé concerne des fences détenue par le
     * client en question
     *
     * @param userId
     * @param source
     * @param dest
     */
   /* public void requestMove(long userId, Fence source, Fence dest) {
        GenericClient client = clientsById.get(userId);
        if (client != null) {
            ArenaEngine arena = client.getArena();
            if (arena != null) {
                arena.requestMove(source, dest);
            }
        }
    } */

    /**
     * retourne les couleurs disponibles de l'arena dans laquelle se trouve le
     * client
     *
     * @param userId
     * @return
     */
  /*  public HashSet<Color> listAvailableColors(long userId) {
        GenericClient client = clientsById.get(userId);
        if (client != null) {
            ArenaEngine arena = client.getArena();
            if (arena != null) {
                return arena.listAvailableColors();
            }
        }
        return null;
    } */

    /**
     * Permet a un client de choisir sa couleur
     *
     * @param userId
     * @param color
     * @return
     */
   /* public boolean chooseColor(long userId, Color color) {
        GenericClient client = clientsById.get(userId);
        if (client != null) {
            ArenaEngine arena = client.getArena();
            if (arena != null) {
                return arena.chooseColor(client, color);
            }
        }
        return false;
    } */

    /**
     * Retourn la liste des scores, par id du joueur
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
        if (client != null ) {
            Core arena = client.getArena();
            if (arena != null) {
                return arena.startGame();
            }
        }
        return false;
    }

    /**
     * Pour avoir la liste des parties 
     * @return 
     */
    public Set<Core> getArenas() {
        return new HashSet(arenasByName.values());
    }

    /**
     * Renvoie la liste des tiles initiales 
     * @param id
     * @param name
     * @return 
     */
   /* public List<Tile> getTilesFor(long id, String name) {
        ArenaEngine arena = arenasByName.get(name);
        GenericClient client = clientsById.get(id);
        if (arena != null && client != null) {
            return arena.alTiles;
        }
        return null;
    }*/
    
    /**
     * Renvoie la liste des fences initiales
     * @param id
     * @param name
     * @return 
     */

   /* public List<Fence> getFencesFor(long id, String name) {
        ArenaEngine arena = arenasByName.get(name);
        GenericClient client = clientsById.get(id);
        if (arena != null && client != null) {
            return arena.alFences;
        }
        return null;
    }*/

    
}

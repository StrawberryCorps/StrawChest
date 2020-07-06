package bzh.strawberry.chest.api;

import bzh.strawberry.chest.api.manager.AnimationType;
import bzh.strawberry.chest.api.manager.IChest;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class StrawChest extends JavaPlugin {

    private static StrawChest STRAWCHEST;

    public StrawChest() {
        STRAWCHEST = this;
    }

    /**
     * Recupérer l'instance de l'API d'ElesiaChest
     *
     * @return intance
     */
    public static StrawChest getInstance() {
        return STRAWCHEST;
    }

    /**
     * Permet de crée un coffre le coffre doit être physiquement présent a l'endroit prévu !
     *
     * @param name     Le nom du coffre & de la clé
     * @param location La position du coffre
     * @return une instance de IChest
     */
    public abstract IChest createChest(String name, String flatName, Location location, AnimationType animationType);

    /**
     * Récuperer un coffre
     *
     * @param name Le nom du coffre
     * @return l'instance du IChest
     */
    public abstract IChest getChest(String name);

    /**
     * Recupère le prefix du plugin
     *
     * @return prefix
     */
    public abstract String getPrefix();
}

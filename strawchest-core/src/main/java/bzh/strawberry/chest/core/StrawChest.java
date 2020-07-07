package bzh.strawberry.chest.core;

import bzh.strawberry.chest.core.commands.CleCommand;
import bzh.strawberry.chest.core.commands.GiveKeyCommand;
import bzh.strawberry.chest.core.listeners.BlockBreak;
import bzh.strawberry.chest.core.listeners.PlayerInteract;
import bzh.strawberry.chest.core.listeners.PlayerJoin;
import bzh.strawberry.chest.core.manager.Chest;
import bzh.strawberry.chest.core.manager.ItemToClaimManager;
import bzh.strawberry.chest.api.manager.AnimationType;
import bzh.strawberry.chest.api.manager.IChest;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StrawChest extends bzh.strawberry.chest.api.StrawChest {

    private List<Chest> chests;
    public static StrawChest INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Objects.requireNonNull(getServer().getPluginCommand("givekey")).setExecutor(new GiveKeyCommand());
        Objects.requireNonNull(getServer().getPluginCommand("cle")).setExecutor(new CleCommand());

        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new BlockBreak(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);

        this.chests = new ArrayList<>();
        new ItemToClaimManager();
    }

    @Override
    public IChest createChest(String name, String flatName, Location location, AnimationType animationType) {
        Chest chest = new Chest(name, flatName, location, animationType);
        this.chests.add(chest);
        return chest;
    }

    public List<Chest> getChests() {
        return chests;
    }

    @Override
    public Chest getChest(String name) {
        return this.chests.stream().filter(chest -> chest.getFlatName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
    }

    public Chest getChest(Location location) {
        return this.chests.stream().filter(chest -> chest.getLocation().getBlockX() == location.getBlockX() && chest.getLocation().getBlockY() == location.getBlockY() && chest.getLocation().getBlockZ() == location.getBlockZ()).findFirst().orElse(null);
    }

    @Override
    public String getPrefix() {
        return "§8[§9Chest§8] §r";
    }
}

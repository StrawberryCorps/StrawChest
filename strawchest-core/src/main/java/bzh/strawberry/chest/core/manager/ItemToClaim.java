package bzh.strawberry.chest.core.manager;

import bzh.strawberry.chest.api.StrawChest;
import bzh.strawberry.chest.api.manager.IItemToclaim;
import bzh.strawberry.chest.api.utils.JsonItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/*
 * This file ItemToClaim is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 01:08 by Uicias.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class ItemToClaim implements IItemToclaim {

    private Player player;
    private final int localId;
    private Map<Integer, ItemStack> map;
    private final String nomTable;

    public ItemToClaim(Player player, Map<Integer, ItemStack> map, String nomTableBDD, int id) {
        this.player = player;
        this.nomTable = nomTableBDD;
        this.localId = id;

        if (map != null)
            this.map = map;
        else
            this.map = new HashMap<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void removeItem(int ind) {
        this.map.remove(ind);
        try {
            // @TODO SGBD
//             PreparedStatement ps = prepareStatement("DELETE FROM " + this.nomTable + " WHERE `id` = ?");
//            ps.setInt(1, ind);
//            ps.executeUpdate();
        } catch (Exception e) {
            player.sendMessage(StrawChest.getInstance().getPrefix() + "§cUne erreur est survenue..." + e.getMessage());
        }
    }

    public void setMap(Map<Integer, ItemStack> nouvelle) {
        this.map = nouvelle;
    }

    public Map<Integer, ItemStack> getMap() {
        return this.map;
    }

    public void addItem(Integer id, ItemStack item) {
        this.map.put(id, item);
    }

    /**
     * Ajoute en bdd l'item du joueur
     *
     * @param item le nouvel item
     * @return l'id dans la nomTable
     */
    public int addNouvelItem(ItemStack item) {
        player.sendMessage(StrawChest.getInstance().getPrefix() + "§8Vous avez un nouvel item en attente : §e/cle");
        int ret = -1;
        try {
            // @TODO SGBD
//            PreparedStatement ps = .prepareStatement("INSERT INTO " + this.nomTable + " (`player_id`, `item`) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, this.localId);
//            ps.setString(2, JsonItemStack.toJson(item));
//            ps.executeUpdate();
//            ResultSet rs = ps.getGeneratedKeys();
//            if(rs.next())
//                ret = rs.getInt(1);
        } catch (Exception e) {
            player.sendMessage(StrawChest.getInstance().getPrefix() + "§cUne erreur est survenue..." + e.getMessage());
        }

        return ret;

    }

    //TODO : save en BDD
    // https://bukkit.org/threads/cardboard-serializable-itemstack-with-enchantments.75768/

}

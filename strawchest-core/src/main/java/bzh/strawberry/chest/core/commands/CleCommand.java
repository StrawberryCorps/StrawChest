package bzh.strawberry.chest.core.commands;

import bzh.strawberry.api.StrawAPI;
import bzh.strawberry.api.command.AbstractCommand;
import bzh.strawberry.chest.core.gui.ItemToClaimGUI;
import bzh.strawberry.chest.core.StrawChest;
import bzh.strawberry.chest.core.manager.ItemToClaimManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/*
 * This file CleCommand is part of a project StrawChest.strawchest-core.
 * It was created on 07/07/2020 00:59 by Eclixal.
 * This file as the whole project shouldn't be modify by others without the express permission from StrawberryCorps author(s).
 * Also this comment shouldn't get remove from the file. (see Licence)
 */
public class CleCommand extends AbstractCommand {

    public CleCommand() {
        super(StrawChest.getInstance(), "strawchest.cle");
    }

    @Override
    protected boolean onCommand(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        StrawAPI.getAPI().getInterfaceManager().openInterface(new ItemToClaimGUI(player, ItemToClaimManager.INSTANCE.getItemToClaim(player)), player);
        return true;
    }
}

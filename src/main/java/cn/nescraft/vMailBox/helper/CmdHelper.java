package cn.nescraft.vMailBox.helper;

import cn.nescraft.vMailBox.gui.MailboxUI;
import cn.nescraft.vMailBox.service.MailService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.UUID;

public class CmdHelper implements CommandExecutor {
    public CmdHelper() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (strings.length == 0) {
            return false;
        }

        String subCmd = strings[0];
        if (subCmd.equalsIgnoreCase("send")) {
            if (strings.length < 2) {
                return false;
            }
            String receiver = strings[1];

            String senderName = player.getName();
            UUID receiverUUID = Bukkit.getOfflinePlayer(receiver).getUniqueId();
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            try {
                MailService.sendMail(senderName, (receiverUUID), itemStack);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        if (subCmd.equalsIgnoreCase("show")) {
            MailboxUI.showMailboxForPlayer(player);
            return true;
        }
        return false;
    }
}

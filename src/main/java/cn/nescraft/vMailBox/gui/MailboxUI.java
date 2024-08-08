package cn.nescraft.vMailBox.gui;

import cn.nescraft.vMailBox.service.MailService;
import cn.nescraft.vMailBox.types.Mail;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MailboxUI implements Listener {
    public MailboxUI() {
    }

    public static void showMailboxForPlayer(Player player) {
        Inventory inventory = player.getServer().createInventory(player, 36, "邮箱");
        refreshMailbox(player, inventory);
        player.openInventory(inventory);
    }

    protected static void refreshMailbox(Player player, Inventory inventory) {
        inventory.clear();
        List<Mail> mails = MailService.getMails(player.getUniqueId());
        for (int i = 0; i < mails.size(); i++) {
            Mail mail = mails.get(i);
            ItemStack mailViewModel = transRawMailTypeToMailViewModel(mail);
            inventory.setItem(i, mailViewModel);
        }
    }

    private static ItemStack transRawMailTypeToMailViewModel(Mail mail) {
        ItemStack itemStack = mail.itemStack;
        var meta = itemStack.getItemMeta();

        // 设置 lore
        List<String> lore = new ArrayList<>();
        lore.add("来自 " + mail.sender);
        meta.setLore(lore);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @EventHandler
    public void onMailboxClick(InventoryClickEvent event) {
        var title = event.getView().getTitle();
        if (!title.equals("邮箱")) {
            return;
        }

        event.setCancelled(true);
        var index = event.getRawSlot();
        var player = (Player) event.getWhoClicked();

        var mail = MailService.deleteMail(player.getUniqueId(), index);
        if (mail != null) {
            player.getInventory().addItem(mail.itemStack);
            refreshMailbox(player, event.getInventory());
        }
    }

    @EventHandler
    public void onMailboxBlockClick(PlayerInteractEvent event) {
        var player = event.getPlayer();
        var block = event.getClickedBlock();
        if (block != null && block.getType() == Material.DISPENSER) {
            event.setCancelled(true);
            showMailboxForPlayer(player);
        }
    }
}

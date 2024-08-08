package cn.nescraft.vMailBox.helper;

import org.bukkit.Bukkit;

public class MessageHelper {
    public static void sendBroadcast(String message) {
        Bukkit.broadcastMessage(message);
    }

    public static void sendAdminBroadcast(String message) {
        Bukkit.broadcast(message, "vMailBox.admin");
    }
}

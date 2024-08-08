package cn.nescraft.vMailBox;

import cn.nescraft.vMailBox.gui.MailboxUI;
import cn.nescraft.vMailBox.helper.CmdHelper;
import cn.nescraft.vMailBox.helper.PluginSetupHelper;
import org.bukkit.plugin.java.JavaPlugin;

public final class VMailBox extends JavaPlugin {
    public static VMailBox plugin;

    @Override
    public void onEnable() {
        plugin = this;

        PluginSetupHelper pluginSetupHelper = new PluginSetupHelper();
        CmdHelper cmdHelper = new CmdHelper();
        getCommand("mail").setExecutor(cmdHelper);
        getServer().getPluginManager().registerEvents(new MailboxUI(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package cn.nescraft.vMailBox.helper;

public class PluginSetupHelper {
    public static PluginSetupHelper instance;

    public PluginSetupHelper() {
        if (isSingletonExist()) {
            return;
        }

        MessageHelper.sendBroadcast("§a[§6vMailBox§a] §7插件已加载");
        instance = this;
    }

    public static boolean isSingletonExist() {
        return instance != null;
    }
}

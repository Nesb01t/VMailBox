package cn.nescraft.vMailBox.helper;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PersistenceDataHelper {
    private static final File dataFolder = new File("plugins/VMailBox");

    public static void createPlayerFile(UUID player) throws IOException {
        String fileName = player + ".yml";
        File playerFile = new File(dataFolder, fileName);

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        playerConfig.save(playerFile);
    }

    public static FileConfiguration getPlayerConfig(UUID player) {
        File playerFile = new File(dataFolder, player + ".yml");
        return YamlConfiguration.loadConfiguration(playerFile);
    }

    public static boolean isPlayerConfigExist(UUID player) {
        File playerFile = new File(dataFolder, player + ".yml");
        return playerFile.exists();
    }

    public static void savePlayerConfig(UUID player, FileConfiguration playerConfig) {
        File playerFile = new File(dataFolder, player + ".yml");
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

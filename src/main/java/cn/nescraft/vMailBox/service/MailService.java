package cn.nescraft.vMailBox.service;

import cn.nescraft.vMailBox.helper.PersistenceDataHelper;
import cn.nescraft.vMailBox.types.Mail;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MailService {
    public static boolean sendMail(String sender, UUID receiver, ItemStack itemStack) throws IOException {
        boolean playerFileExist = PersistenceDataHelper.isPlayerConfigExist(receiver);
        if (!playerFileExist) {
            PersistenceDataHelper.createPlayerFile(receiver);
        }

        FileConfiguration config = PersistenceDataHelper.getPlayerConfig(receiver);

        boolean pathExist = config.contains("mail");
        if (!pathExist) {
            List<String> emptyList = new ArrayList<>();
            config.set("mail", emptyList);
        }

        List<Map<String, Object>> mails = (List<Map<String, Object>>) config.getList("mail");
        Mail newMail = new Mail(sender, receiver, itemStack);
        mails.add(newMail.serialize());
        config.set("mail", mails);

        PersistenceDataHelper.savePlayerConfig(receiver, config);
        return true;
    }

    public static List<Mail> getMails(UUID receiver) {
        FileConfiguration config = PersistenceDataHelper.getPlayerConfig(receiver);
        List<Map<String, Object>> mails = (List<Map<String, Object>>) config.getList("mail");
        List<Mail> mailList = new ArrayList<>();

        // 从文件系统反序列化回来
        for (Map<String, Object> mail : mails) {
            mailList.add(Mail.deserialize(mail));
        }
        return mailList;
    }

    public static Mail deleteMail(UUID receiver, int index) {
        FileConfiguration config = PersistenceDataHelper.getPlayerConfig(receiver);
        List<Map<String, Object>> mails = (List<Map<String, Object>>) config.getList("mail");
        if (mails == null) {
            return null;
        }
        if (index < 0 || index >= mails.size() || mails.get(index) == null) {
            return null;
        }
        Mail mail = Mail.deserialize(mails.get(index));
        mails.remove(index);
        config.set("mail", mails);
        PersistenceDataHelper.savePlayerConfig(receiver, config);

        return mail;
    }
}

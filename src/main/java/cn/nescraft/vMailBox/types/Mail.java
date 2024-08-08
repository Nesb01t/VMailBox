package cn.nescraft.vMailBox.types;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public class Mail implements ConfigurationSerializable {
    public String sender;
    public UUID receiver;
    public ItemStack itemStack;

    public Mail(String sender, UUID receiver, ItemStack itemStack) {
        this.sender = sender;
        this.receiver = receiver;
        this.itemStack = itemStack;
    }

    public static Mail deserialize(Map<String, Object> map) {
        return new Mail(
                (String) map.get("sender"),
                UUID.fromString((String) map.get("receiver")),
                ItemStack.deserialize((Map<String, Object>) map.get("itemStack"))
        );
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "sender", sender,
                "receiver", receiver.toString(),
                "itemStack", itemStack.serialize()
        );
    }
}

package me.jashscript.tradesplus.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Serialization {

    public static String serializeItemStack(ItemStack itemStack) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        dataOutput.writeObject(itemStack);
        dataOutput.close();
        return Base64Coder.encodeLines(outputStream.toByteArray());
    }


    public static ItemStack deserializeItemStack(String serialized) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(serialized));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        ItemStack output = (ItemStack) dataInput.readObject();
        dataInput.close();
        return output;
    }
}

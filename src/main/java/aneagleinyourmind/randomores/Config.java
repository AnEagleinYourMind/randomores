package aneagleinyourmind.randomores;

import java.io.File;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

public class Config {

    public static String greeting = "Hello World";

    public static Item[] default_items = {
        Items.coal,
        Items.iron_ingot,
        Items.gold_ingot,
        Items.redstone,
        Items.diamond
    };

    public static int[] weights = { // TODO: make this not hard coded
        5,
        5,
        3,
        4,
        2
    };

    // TODO: hardcoded blacklist, include things like bedrock and portals

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        greeting = configuration.getString("greeting", Configuration.CATEGORY_GENERAL, greeting, "How shall I greet?");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}

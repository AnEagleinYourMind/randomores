package aneagleinyourmind.randomores.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

import org.apache.commons.lang3.StringUtils;

import aneagleinyourmind.randomores.RandomOres;

public class Config {

    private static String[] unparsedDropList;
    public static List<ItemDrop> normalDropList = new ArrayList<>(); // TODO: set?
    public static Set<Integer> allowedDimensions = new HashSet<>();
    public static int maximumY, minimumY, orePerChunk, explosionDelay;

    // TODO: hardcoded blacklist, include things like bedrock and portals

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        int[] primitiveDimensionArray = configuration
            .get(
                Configuration.CATEGORY_GENERAL,
                "allowed_dimensions",
                new int[] { 0, 7 },
                "The dimension IDs ores can generate in")
            .getIntList();
        Arrays.stream(primitiveDimensionArray)
            .boxed()
            .forEach(boxedInt -> allowedDimensions.add(boxedInt));

        unparsedDropList = configuration
            .get(
                Configuration.CATEGORY_GENERAL,
                "normal_item_drops",
                new String[] { "minecraft:diamond", "minecraft:coal", "minecraft:dye.4", "minecraft:dirt" },
                "Item ids that will be dropped by normal random ore")
            .getStringList();

        maximumY = configuration
            .get(Configuration.CATEGORY_GENERAL, "max_y", 35, "The maximum altitude ore will generate at")
            .getInt();
        minimumY = configuration
            .get(Configuration.CATEGORY_GENERAL, "min_y", 0, "The minimum altitude ore will generate at")
            .getInt();
        orePerChunk = configuration
            .get(Configuration.CATEGORY_GENERAL, "ore_per_chunk", 1, "The number of ore veins that generate per chunk")
            .getInt();
        explosionDelay = configuration
            .get(
                Configuration.CATEGORY_GENERAL,
                "explosion_delay",
                10,
                "The time delay in ticks before an ore explodes")
            .getInt();

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    public static void processDropLists() {
        for (String fullName : unparsedDropList) {
            String modName = StringUtils.substringBefore(fullName, ":");
            String itemName = StringUtils.substringAfter(fullName, ":");
            int meta = 0;

            if (StringUtils.contains(itemName, '.')) {
                meta = Integer.parseInt(StringUtils.substringAfter(itemName, "."));
                itemName = StringUtils.substringBefore(itemName, ".");
            }

            try {
                meta = Integer.parseInt(StringUtils.substringAfter(fullName, "."));
            } catch (NumberFormatException ignored) {

            }

            RandomOres.LOG.warn(itemName + " meta " + meta);
            normalDropList.add(new ItemDrop(modName, itemName, meta));
        }

        System.out.println(normalDropList.size());
    }
}

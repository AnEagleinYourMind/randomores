package aneagleinyourmind.randomores;

import net.minecraft.block.Block;

import aneagleinyourmind.randomores.block.RandomOreBlock;
import aneagleinyourmind.randomores.item.RandomOreBlockItem;
import aneagleinyourmind.randomores.util.Config;
import aneagleinyourmind.randomores.world.RandomOresWorldGen;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

    public static Block RANDOM_ORE;

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        Config.synchronizeConfiguration(event.getSuggestedConfigurationFile());

        RANDOM_ORE = GameRegistry
            .registerBlock(new RandomOreBlock("random_ore"), RandomOreBlockItem.class, "random_ore");
        GameRegistry.registerWorldGenerator(new RandomOresWorldGen(), 0);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        Config.processDropLists();
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {}
}

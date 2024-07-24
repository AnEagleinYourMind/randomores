package aneagleinyourmind.randomores.util;

import net.minecraft.item.Item;

import aneagleinyourmind.randomores.RandomOres;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemDrop {

    private int meta;
    private Item item;

    public ItemDrop(String modName, String itemName, int meta) {
        item = GameRegistry.findItem(modName, itemName);

        if (item == null) {
            RandomOres.LOG.warn("FML GameRegistry could not find item " + modName + ":" + itemName + " meta " + meta);
        }

        this.meta = meta;
    }

    public Item getItem() {
        return item;
    }

    public int getMeta() {
        return meta;
    }
}

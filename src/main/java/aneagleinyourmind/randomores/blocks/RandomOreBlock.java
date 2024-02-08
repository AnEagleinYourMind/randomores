package aneagleinyourmind.randomores.blocks;

import aneagleinyourmind.randomores.Config;
import aneagleinyourmind.randomores.RandomOres;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Random;

public class RandomOreBlock extends Block {
    private int dropMultiplier;

    public RandomOreBlock(String registryName, int dropMultiplier) {
        super(Material.rock);
        setBlockName(registryName);
        setBlockTextureName(RandomOres.MODID + ":" + registryName);
        setCreativeTab(CreativeTabs.tabBlock);
        setHarvestLevel("pickaxe", 2);
        setHardness(3.0F);
        setResistance(5.0F);

        this.dropMultiplier = dropMultiplier;
    }

    // TODO: fortune

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Config.default_items[random.nextInt(Config.default_items.length)];
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(2) * dropMultiplier;
    }
}

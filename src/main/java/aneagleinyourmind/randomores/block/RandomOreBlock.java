package aneagleinyourmind.randomores.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import aneagleinyourmind.randomores.RandomOres;
import aneagleinyourmind.randomores.util.Config;
import aneagleinyourmind.randomores.util.ItemDrop;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RandomOreBlock extends Block {

    @SideOnly(Side.CLIENT)
    private static final String[] TEXTURE_NAMES = { "random_ore_0", "random_ore_1" };
    @SideOnly(Side.CLIENT)
    private IIcon[] ore_icons;

    public RandomOreBlock(String registryName) {
        super(Material.rock);
        setBlockName(registryName);
        setCreativeTab(CreativeTabs.tabBlock);
        setHarvestLevel("pickaxe", 2);
        setHardness(3.0F);
        setResistance(5.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return ore_icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        ore_icons = new IIcon[TEXTURE_NAMES.length];

        ore_icons[0] = register.registerIcon(RandomOres.RESOURCE_ID + TEXTURE_NAMES[0]);
        ore_icons[1] = register.registerIcon(RandomOres.RESOURCE_ID + TEXTURE_NAMES[1]);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tabs, List<ItemStack> itemsList) {
        itemsList.add(new ItemStack(item, 1, 0));
        itemsList.add(new ItemStack(item, 1, 1));
    }

    // TODO: fortune

    @Override
    public int quantityDropped(Random random) { // prevent the block itself from being dropped
        return 0;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block broken, int meta) {
        if (!world.isRemote) {
            ItemDrop randomDrop = Config.normalDropList.get(world.rand.nextInt(Config.normalDropList.size()));
            int dropQuantity = 1;

            if (randomDrop.getItem() == null) return;

            if (meta == 1) {
                dropQuantity = world.rand.nextInt(4); // exclusive bound, maximum qty. of 3
            }

            ItemStack dropStack = new ItemStack(randomDrop.getItem(), dropQuantity, randomDrop.getMeta());

            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, dropStack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        if (!world.isRemote) {
            if (meta == 1 && world.rand.nextBoolean()) {
                EntityTNTPrimed tnt = new EntityTNTPrimed(world, x + 0.5f, y + 0.5f, z + 0.5f, null);
                world.spawnEntityInWorld(tnt);
                world.playSoundAtEntity(tnt, "mob.villager.no", 1.0f, 1.0f);

                tnt.fuse = Config.explosionDelay; // in ticks
            }
        }
    }
}

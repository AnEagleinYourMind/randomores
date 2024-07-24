package aneagleinyourmind.randomores.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class RandomOreBlockItem extends ItemBlockWithMetadata {

    public RandomOreBlockItem(Block block) {
        super(block, block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "_" + stack.getItemDamage();
    }
}

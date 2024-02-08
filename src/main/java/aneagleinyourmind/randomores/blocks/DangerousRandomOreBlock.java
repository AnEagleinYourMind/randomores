package aneagleinyourmind.randomores.blocks;

import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class DangerousRandomOreBlock extends RandomOreBlock {
    public DangerousRandomOreBlock(String registryName, int dropMultiplier) {
        super(registryName, dropMultiplier);
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta) {
        if (!world.isRemote && world.rand.nextBoolean()) { // 50/50 chance of death >:)
            EntityTNTPrimed tnt = new EntityTNTPrimed(world, x + 0.5f, y + 0.5f, z + 0.5f, null);
            world.spawnEntityInWorld(tnt);
            world.playSoundAtEntity(tnt, "mob.villager.no", 1.0f, 1.0f);

            tnt.fuse = 10; // in ticks
        }
    }
}

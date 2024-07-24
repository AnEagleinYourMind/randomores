package aneagleinyourmind.randomores.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import aneagleinyourmind.randomores.CommonProxy;
import aneagleinyourmind.randomores.util.Config;
import cpw.mods.fml.common.IWorldGenerator;

public class RandomOresWorldGen implements IWorldGenerator {

    private final WorldGenMinable randomOreMinable;
    private final WorldGenMinable dangerousOreMinable;

    public RandomOresWorldGen() {
        randomOreMinable = new WorldGenMinable(CommonProxy.RANDOM_ORE, 0, 8, Blocks.stone);
        dangerousOreMinable = new WorldGenMinable(CommonProxy.RANDOM_ORE, 1, 8, Blocks.stone);
    }

    private void generateOre(WorldGenMinable minable, int chunkX, int chunkZ, World world, Random random, int minY,
        int maxY) {
        int xPos = chunkX * 16 + random.nextInt(16);
        int yPos = random.nextInt(maxY - minY) - minY;
        int zPos = chunkZ * 16 + random.nextInt(16);

        minable.generate(world, random, xPos, yPos, zPos);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
        IChunkProvider chunkProvider) {
        final Integer dimensionId = world.provider.dimensionId;

        Config.allowedDimensions.forEach(allowedDimensionId -> {
            if (Config.allowedDimensions.contains(dimensionId)) {
                for (int i = 0; i < Config.orePerChunk; i++) {
                    generateOre(randomOreMinable, chunkX, chunkZ, world, random, Config.minimumY, Config.maximumY);
                    generateOre(dangerousOreMinable, chunkX, chunkZ, world, random, Config.minimumY, Config.maximumY);
                }
            }
        });
    }
}

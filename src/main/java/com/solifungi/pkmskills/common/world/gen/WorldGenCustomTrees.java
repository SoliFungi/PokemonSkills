package com.solifungi.pkmskills.common.world.gen;

import com.solifungi.pkmskills.common.world.gen.generators.WorldGenMegaMossyTree;
import com.solifungi.pkmskills.common.world.gen.generators.WorldGenMossyTree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import scala.actors.threadpool.Arrays;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenCustomTrees implements IWorldGenerator
{
    private final WorldGenerator MOSSY = new WorldGenMossyTree();
    private final WorldGenerator MEGA_MOSSY = new WorldGenMegaMossyTree(true,10,20);

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.getDimension())
        {
            case 1:
                break;
            case 0:
                runGenerator(MOSSY, world, random, chunkX, chunkZ ,0.3,-1,0, BiomeSwamp.class);
                runGenerator(MEGA_MOSSY, world, random, chunkX, chunkZ ,3,-1,0, BiomeJungle.class);
                break;
            case -1:
        }
    }

    //chancesToSpawn为每个区块生成数量的期望
    private void runGenerator(WorldGenerator generator, World world, Random rand, int chunkX, int chunkZ, double chancesToSpawn, int minHeight, int maxHeight, Class<?>... classes)
    {
        if(chancesToSpawn < 1)
        {
            if(rand.nextDouble() < chancesToSpawn) { chancesToSpawn = 1; }
            else { chancesToSpawn = 0; }
        }

        ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(Arrays.asList(classes));
        int heightDiff = maxHeight - minHeight + 1;

        for(int i = 0; i < chancesToSpawn; i++)
        {
            BlockPos pos = new BlockPos(chunkX * 16 + 10 + rand.nextInt(15), minHeight + rand.nextInt(heightDiff), chunkZ * 16 + 10 + rand.nextInt(15));

            if(minHeight < 0)
            {
                pos = world.getHeight(pos);
            }

            Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();

            if(classesList.contains(biome) || classes.length == 0)
            {
                generator.generate(world, rand, pos);
            }
        }
    }

}

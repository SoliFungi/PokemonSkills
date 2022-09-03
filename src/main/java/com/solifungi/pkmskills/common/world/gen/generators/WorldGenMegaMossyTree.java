package com.solifungi.pkmskills.common.world.gen.generators;

import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenMegaMossyTree extends WorldGenAbstractTree
{
    private final int baseHeight;
    private final int extraRandomHeight;

    public WorldGenMegaMossyTree(boolean notify, int baseHeightIn, int extraRandomHeightIn)
    {
        super(notify);
        this.baseHeight = baseHeightIn;
        this.extraRandomHeight = extraRandomHeightIn;
    }

    private int getHeight(Random rand)
    {
        int i = rand.nextInt(3) + this.baseHeight;

        if (this.extraRandomHeight > 1)
        {
            i += rand.nextInt(this.extraRandomHeight);
        }

        return i;
    }

    private boolean isSpaceAt(World worldIn, BlockPos leavesPos, int height)
    {
        boolean flag = true;

        if (leavesPos.getY() >= 1 && leavesPos.getY() + height + 1 <= 256)
        {
            for (int i = 0; i <= 1 + height; ++i)
            {
                int j = 2;
                if (i == 0) { j = 1;}

                for (int k = -j; k <= j && flag; ++k)
                {
                    for (int l = -j; l <= j && flag; ++l)
                    {
                        if (leavesPos.getY() + i < 0 || leavesPos.getY() + i >= 256 || !this.isReplaceable(worldIn,leavesPos.add(k, i, l)))
                        {
                            flag = false;
                        }
                    }
                }
            }

            return flag;
        }
        else
        {
            return false;
        }
    }

    private boolean ensureDirtsUnderneath(BlockPos pos, World worldIn)
    {
        BlockPos blockpos = pos.down();
        IBlockState state = worldIn.getBlockState(blockpos);
        boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, blockpos, net.minecraft.util.EnumFacing.UP, ((net.minecraft.block.BlockSapling) Blocks.SAPLING));

        if (isSoil && pos.getY() >= 2)
        {
            this.onPlantGrow(worldIn, blockpos, pos);
            this.onPlantGrow(worldIn, blockpos.east(), pos);
            this.onPlantGrow(worldIn, blockpos.south(), pos);
            this.onPlantGrow(worldIn, blockpos.south().east(), pos);
            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean ensureGrowable(World worldIn, BlockPos treePos, int height)
    {
        return this.isSpaceAt(worldIn, treePos, height) && this.ensureDirtsUnderneath(treePos, worldIn);
    }

    protected void growLeavesLayerStrict(World worldIn, BlockPos layerCenter, int width)
    {
        int i = width * width;

        for (int j = -width; j <= width + 1; ++j)
        {
            for (int k = -width; k <= width + 1; ++k)
            {
                int l = j - 1;
                int i1 = k - 1;

                if (j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i)
                {
                    BlockPos blockpos = layerCenter.add(j, 0, k);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos, WorldGenMossyTree.LEAF);
                    }
                }
            }
        }
    }

    protected void growLeavesLayer(World worldIn, BlockPos layerCenter, int width)
    {
        int i = width * width;

        for (int j = -width; j <= width; ++j)
        {
            for (int k = -width; k <= width; ++k)
            {
                if (j * j + k * k <= i)
                {
                    BlockPos blockpos = layerCenter.add(j, 0, k);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos, WorldGenMossyTree.LEAF);
                    }
                }
            }
        }
    }

    //Just a helper macro
    private void onPlantGrow(World world, BlockPos pos, BlockPos source)
    {
        IBlockState state = world.getBlockState(pos);
        state.getBlock().onPlantGrow(state, world, pos, source);
    }


    /**Tree Gens*/
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int height = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, position, height))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.up(height), 2);

            for (int j = position.getY() + height - 2 - rand.nextInt(4); j > position.getY() + height / 2; j -= 2 + rand.nextInt(4))
            {
                float f = rand.nextFloat() * ((float)Math.PI * 2F);
                int k = position.getX() + (int)(0.5F + MathHelper.cos(f) * 4.0F);
                int l = position.getZ() + (int)(0.5F + MathHelper.sin(f) * 4.0F);

                for (int i1 = 0; i1 < 5; ++i1)
                {
                    k = position.getX() + (int)(1.5F + MathHelper.cos(f) * (float)i1);
                    l = position.getZ() + (int)(1.5F + MathHelper.sin(f) * (float)i1);
                    this.setBlockAndNotifyAdequately(worldIn, new BlockPos(k, j - 3 + i1 / 2, l), WorldGenMossyTree.LOG);
                }

                int j2 = 1 + rand.nextInt(2);

                for (int k1 = j - j2; k1 <= j; ++k1)
                {
                    int l1 = k1 - j;
                    this.growLeavesLayer(worldIn, new BlockPos(k, k1, l), 1 - l1);
                }
            }

            for (int i2 = 0; i2 < height; ++i2)
            {
                BlockPos blockpos = position.up(i2);

                if (this.isAirLeaves(worldIn,blockpos))
                {
                    this.setBlockAndNotifyAdequately(worldIn, blockpos, WorldGenMossyTree.LOG);

                    if (i2 > 0)
                    {
                        this.placeVine(worldIn, rand, blockpos.west(), BlockVine.EAST);
                        this.placeVine(worldIn, rand, blockpos.north(), BlockVine.SOUTH);
                    }
                }

                if (i2 < height - 1)
                {
                    BlockPos blockpos1 = blockpos.east();

                    if (this.isAirLeaves(worldIn,blockpos1))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos1, WorldGenMossyTree.LOG);

                        if (i2 > 0)
                        {
                            this.placeVine(worldIn, rand, blockpos1.east(), BlockVine.WEST);
                            this.placeVine(worldIn, rand, blockpos1.north(), BlockVine.SOUTH);
                        }
                    }

                    BlockPos blockpos2 = blockpos.south().east();

                    if (this.isAirLeaves(worldIn,blockpos2))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos2, WorldGenMossyTree.LOG);

                        if (i2 > 0)
                        {
                            this.placeVine(worldIn, rand, blockpos2.east(), BlockVine.WEST);
                            this.placeVine(worldIn, rand, blockpos2.south(), BlockVine.NORTH);
                        }
                    }

                    BlockPos blockpos3 = blockpos.south();

                    if (this.isAirLeaves(worldIn,blockpos3))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos3, WorldGenMossyTree.LOG);

                        if (i2 > 0)
                        {
                            this.placeVine(worldIn, rand, blockpos3.west(), BlockVine.EAST);
                            this.placeVine(worldIn, rand, blockpos3.south(), BlockVine.NORTH);
                        }
                    }
                }
            }

            return true;
        }
    }

    private void placeVine(World world, Random random, BlockPos pos, PropertyBool property)
    {
        if (random.nextInt(3) > 0 && world.isAirBlock(pos))
        {
            this.setBlockAndNotifyAdequately(world, pos, Blocks.VINE.getDefaultState().withProperty(property, Boolean.valueOf(true)));
        }
    }

    private void createCrown(World worldIn, BlockPos pos, int p_175930_3_)
    {
        for (int j = -2; j <= 0; ++j)
        {
            this.growLeavesLayerStrict(worldIn, pos.up(j), p_175930_3_ + 1 - j);
        }
    }

    //Helper macro
    private boolean isAirLeaves(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }

}

package com.solifungi.pkmskills.common.world.gen.generators;

import com.solifungi.pkmskills.common.blocks.tree.BlockCustomLeaves;
import com.solifungi.pkmskills.common.blocks.tree.BlockCustomLog;
import com.solifungi.pkmskills.common.blocks.tree.BlockCustomSapling;
import com.solifungi.pkmskills.common.init.ModBlocks;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

public class WorldGenMossyTree extends WorldGenAbstractTree
{
    public static final IBlockState LOG = ModBlocks.LOG.getDefaultState().withProperty(BlockCustomLog.VARIANT, EnumHandler.EnumWoodType.MOSSY);
    public static final IBlockState LEAF = ModBlocks.LEAVES.getDefaultState().withProperty(BlockCustomLeaves.VARIANT, EnumHandler.EnumWoodType.MOSSY);

    private final int minHeight;

    public WorldGenMossyTree()
    {
        super(false);
        this.minHeight = 12;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int height = this.minHeight + rand.nextInt(3);
        boolean flag = true;

        int x = position.getX();
        int y = position.getY();
        int z = position.getZ();

        for(int yPos = y; yPos <= y + 1 + height; yPos++)
        {
            int b0 = 1;
            if(yPos == y) {b0 = 0;}
            if(yPos >= y + 1 + height - 2) {b0 = 2;}

            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for(int xPos = x - b0; xPos <= x + b0 && flag; xPos++)
            {
                for(int zPos = z - b0; zPos <= z + b0 && flag; zPos++)
                {
                    if(yPos >= 0 && yPos < worldIn.getHeight())
                    {
                        if(!this.isReplaceable(worldIn, mutable.setPos(xPos, yPos, zPos)))
                        {
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = false;
                    }
                }
            }
        }

        if(!flag)
        {
            return false;
        }
        else
        {
            BlockPos down = position.down();
            IBlockState state = worldIn.getBlockState(down);

            boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, EnumFacing.UP, (BlockCustomSapling)ModBlocks.SAPLING);

            if(isSoil && y < worldIn.getHeight() - height - 1)
            {
                state.getBlock().onPlantGrow(state, worldIn, down, position);

                //Leaves
                for(int yPos = y - 3 + height; yPos <= y + height; yPos++)
                {
                    int b1 = yPos - (y + height);
                    int b2 = 1 - b1 / 2;

                    for(int xPos = x - b2; xPos <= x + b2; xPos++)
                    {
                        int b3 = xPos - x;
                        for(int zPos = z - b2; zPos <= z + b2; zPos++)
                        {
                            int b4 = zPos - z;
                            if(Math.abs(b3) != b2 || Math.abs(b4) != b2 || rand.nextInt(2) != 0 && b1 != 0)
                            {
                                BlockPos treePos = new BlockPos(xPos, yPos, zPos);
                                IBlockState treeState = worldIn.getBlockState(treePos);

                                if(treeState.getBlock().isAir(treeState, worldIn, treePos) || treeState.getBlock().isLeaves(treeState, worldIn, treePos) || treeState.getMaterial() == Material.VINE)
                                {
                                    this.setBlockAndNotifyAdequately(worldIn, treePos, LEAF);
                                    this.setBlockAndNotifyAdequately(worldIn, treePos.add(0,-0.25 * height,0), LEAF);
                                    this.setBlockAndNotifyAdequately(worldIn, treePos.add(0,-0.5 * height,0), LEAF);
                                }
                            }
                        }
                    }
                }

                //Logs & attached vines
                for(int logHeight = 0; logHeight < height ; logHeight++)
                {
                    BlockPos up = position.up(logHeight);
                    IBlockState logState = worldIn.getBlockState(up);

                    if(logState.getBlock().isAir(logState, worldIn, up) || logState.getBlock().isLeaves(logState, worldIn, up))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, position.up(logHeight), LOG);

                        if(logHeight > 0)
                        {
                            if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(-1, logHeight, 0)))
                            {
                                this.addVine(worldIn, position.add(-1, logHeight, 0), BlockVine.EAST);
                            }

                            if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(1, logHeight, 0)))
                            {
                                this.addVine(worldIn, position.add(1, logHeight, 0), BlockVine.WEST);
                            }

                            if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, logHeight, -1)))
                            {
                                this.addVine(worldIn, position.add(0, logHeight, -1), BlockVine.SOUTH);
                            }

                            if (rand.nextInt(3) > 0 && worldIn.isAirBlock(position.add(0, logHeight, 1)))
                            {
                                this.addVine(worldIn, position.add(0, logHeight, 1), BlockVine.NORTH);
                            }
                        }
                    }
                }

                //Hanging vines
                for (int yPos = y - 3 + height; yPos <= y + height; yPos++)
                {
                    int b1 = yPos - (y + height);
                    int b2 = 2 - b1 / 2;

                    BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

                    for (int xPos = x - b2; xPos <= x + b2; xPos++)
                    {
                        for (int zPos = z - b2; zPos <= z + b2; zPos++)
                        {
                            mutable.setPos(xPos, yPos, zPos);
                            state = worldIn.getBlockState(mutable);

                            if (state.getBlock().isLeaves(state, worldIn, mutable))
                            {
                                BlockPos west = mutable.west();
                                BlockPos east = mutable.east();
                                BlockPos north = mutable.north();
                                BlockPos south = mutable.south();

                                if (rand.nextInt(4) == 0 && worldIn.isAirBlock(west))
                                {
                                    this.addHangingVine(worldIn, west, BlockVine.EAST);
                                }

                                if (rand.nextInt(4) == 0 && worldIn.isAirBlock(east))
                                {
                                    this.addHangingVine(worldIn, east, BlockVine.WEST);
                                }

                                if (rand.nextInt(4) == 0 && worldIn.isAirBlock(north))
                                {
                                    this.addHangingVine(worldIn, north, BlockVine.SOUTH);
                                }

                                if (rand.nextInt(4) == 0 && worldIn.isAirBlock(south))
                                {
                                    this.addHangingVine(worldIn, south, BlockVine.NORTH);
                                }
                            }
                        }
                    }
                }

                return true;
            }
        }

        return true;
    }

    private void addVine(World worldIn, BlockPos pos, PropertyBool prop)
    {
        this.setBlockAndNotifyAdequately(worldIn, pos, Blocks.VINE.getDefaultState().withProperty(prop, Boolean.valueOf(true)));
    }

    private void addHangingVine(World worldIn, BlockPos pos, PropertyBool prop)
    {
        this.addVine(worldIn, pos, prop);
        int i = 4;

        for (BlockPos blockpos = pos.down(); worldIn.isAirBlock(blockpos) && i > 0; --i)
        {
            this.addVine(worldIn, blockpos, prop);
            blockpos = blockpos.down();
        }
    }
}

package com.solifungi.pkmskills.common.blocks.tree;

import com.google.common.base.Predicate;
import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.blocks.items.ItemBlockVariants;
import com.solifungi.pkmskills.common.init.ModBlocks;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import com.solifungi.pkmskills.common.util.interfaces.IMetaName;
import com.solifungi.pkmskills.common.world.gen.generators.WorldGenMegaMossyTree;
import com.solifungi.pkmskills.common.world.gen.generators.WorldGenMossyTree;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCustomSapling extends BlockBush implements IGrowable, IMetaName, IHasModel
{
    private String name;
    private static final int typeNum = EnumHandler.EnumWoodType.values().length;
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

    public static final PropertyEnum<EnumHandler.EnumWoodType> VARIANT = PropertyEnum.<EnumHandler.EnumWoodType>create("variant", EnumHandler.EnumWoodType.class, new Predicate<EnumHandler.EnumWoodType>()
    {
        public boolean apply(@Nullable EnumHandler.EnumWoodType apply)
        {
            return apply.getMeta() < typeNum;
        }
    });

    public BlockCustomSapling(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(ModCreativeTabs.PKM_MISC);
        setSoundType(SoundType.PLANT);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.EnumWoodType.MOSSY).withProperty(STAGE, 0));
        this.name = name;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
    }

    //Sapling Shape
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    //Variants
    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for(EnumHandler.EnumWoodType type : EnumHandler.EnumWoodType.values())
        {
            items.add(new ItemStack(this,1, type.getMeta()));
        }
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumHandler.EnumWoodType)state.getValue(VARIANT)).getMeta();
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return EnumHandler.EnumWoodType.values()[stack.getItemDamage()].getName();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumWoodType.byMetadata(meta & (typeNum - 1)));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i |= ((EnumHandler.EnumWoodType) state.getValue(VARIANT)).getMeta();
        i |= ((Integer)state.getValue(STAGE)).intValue() << 3;
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{VARIANT, STAGE});
    }

    @Override
    public void registerModels()
    {
        for(int i = 0; i < EnumHandler.EnumWoodType.values().length; i++)
        {
            PokemonSkills.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "sapling_" + EnumHandler.EnumWoodType.values()[i].getName(),"inventory");
        }
    }

    /*****Tree Code Below*****/

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        if(((Integer)state.getValue(STAGE)).intValue() == 0)
        {
            worldIn.setBlockState(pos, state.cycleProperty(STAGE),4);
        }
        else
        {
            this.generateTree(worldIn, rand, pos, state);
        }
    }

    public void generateTree(World world, Random rand, BlockPos pos, IBlockState state)
    {
        if(TerrainGen.saplingGrowTree(world, rand, pos))
        {
            return;
        }
        WorldGenerator gen = (WorldGenerator)(rand.nextInt(10) == 0 ? new WorldGenBigTree(false) : new WorldGenTrees(false));

        int x = 0, z = 0;
        boolean flag = false;

        switch ((EnumHandler.EnumWoodType)state.getValue(VARIANT))
        {
            case MOSSY:

                label0:

                for (x = 0; x >= -1; --x)
                {
                    for (z = 0; z >= -1; --z)
                    {
                        if (this.isTwoByTwoOfType(world, pos, x, z, EnumHandler.EnumWoodType.MOSSY))
                        {
                            gen = new WorldGenMegaMossyTree(true, 10, 20);
                            flag = true;
                            break label0;
                        }
                    }
                }

                if (!flag)
                {
                    gen = new WorldGenMossyTree();
                }

                break;
        }

        IBlockState iBlockState = Blocks.AIR.getDefaultState();
        if(flag)
        {
            world.setBlockState(pos.add(0,0,0), iBlockState,4);
            world.setBlockState(pos.add(1,0,0), iBlockState,4);
            world.setBlockState(pos.add(0,0,1), iBlockState,4);
            world.setBlockState(pos.add(1,0,1), iBlockState,4);
        }
        else
        {
            world.setBlockState(pos, iBlockState,4);
        }

        if(!gen.generate(world, rand, pos))
        {
            if(flag)
            {
                world.setBlockState(pos.add(0,0,0), iBlockState,4);
                world.setBlockState(pos.add(1,0,0), iBlockState,4);
                world.setBlockState(pos.add(0,0,1), iBlockState,4);
                world.setBlockState(pos.add(1,0,1), iBlockState,4);
            }
            else
            {
                world.setBlockState(pos, iBlockState,4);
            }
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
    {
        return (double)worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    protected boolean canSustainBush(IBlockState state)
    {
        return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
    }

    private boolean isTwoByTwoOfType(World worldIn, BlockPos pos, int x, int z, EnumHandler.EnumWoodType variant)
    {
        return this.isTypeAt(worldIn, pos.add(x, 0, z), variant) && this.isTypeAt(worldIn, pos.add(x + 1, 0, z), variant) && this.isTypeAt(worldIn, pos.add(x, 0, z + 1), variant) && this.isTypeAt(worldIn, pos.add(x + 1, 0, z + 1), variant);
    }

    public boolean isTypeAt(World worldIn, BlockPos pos, EnumHandler.EnumWoodType variant)
    {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock() == this && state.getValue(VARIANT) == variant;
    }

}

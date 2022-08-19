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
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockCustomLeaves extends BlockLeaves implements IHasModel, IMetaName
{
    private String name;
    private static final int typeNum = EnumHandler.EnumWoodType.values().length;

    public static final PropertyEnum<EnumHandler.EnumWoodType> VARIANT = PropertyEnum.<EnumHandler.EnumWoodType>create("variant", EnumHandler.EnumWoodType.class, new Predicate<EnumHandler.EnumWoodType>()
    {
        public boolean apply(@Nullable EnumHandler.EnumWoodType apply)
        {
            return apply.getMeta() < typeNum;
        }
    });

    public BlockCustomLeaves(String name)
    {
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.PLANT);
        setCreativeTab(ModCreativeTabs.PKM_MISC);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.EnumWoodType.MOSSY).withProperty(CHECK_DECAY,true).withProperty(DECAYABLE,true));
        this.name = name;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
    {
        for(EnumHandler.EnumWoodType type : EnumHandler.EnumWoodType.values())
        {
            items.add(new ItemStack(this,1, type.getMeta()));
        }
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this),1, ((EnumHandler.EnumWoodType)state.getValue(VARIANT)).getMeta());
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
        //if语句暂时添加以消除bug
        if(typeNum < 2)
        {
            return this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumWoodType.byMetadata(meta));
        }
        return this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumWoodType.byMetadata(meta % (typeNum - 1)));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i = ((EnumHandler.EnumWoodType) state.getValue(VARIANT)).getMeta();

        if(!((Boolean)state.getValue(DECAYABLE).booleanValue()))
        {
            i |= typeNum;
        }
        if(!((Boolean)state.getValue(CHECK_DECAY).booleanValue()))
        {
            i |= (2 * typeNum);
        }

        return i;
    }

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {}

    @Override
    protected int getSaplingDropChance(IBlockState state)
    {
        return 15; //Default: 20
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta)
    {
        return null;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return NonNullList.withSize(1, new ItemStack(this,1, world.getBlockState(pos).getValue(VARIANT).getMeta()));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[]{VARIANT, DECAYABLE, CHECK_DECAY});
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void registerModels()
    {
        for(int i = 0; i < EnumHandler.EnumWoodType.values().length; i++)
        {
            PokemonSkills.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "leaves_" + EnumHandler.EnumWoodType.values()[i].getName(),"inventory");
        }
    }
}

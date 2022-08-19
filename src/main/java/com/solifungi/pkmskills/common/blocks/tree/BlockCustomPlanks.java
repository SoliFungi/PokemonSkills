package com.solifungi.pkmskills.common.blocks.tree;

import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.blocks.items.ItemBlockVariants;
import com.solifungi.pkmskills.common.init.ModBlocks;
import com.solifungi.pkmskills.common.init.ModCreativeTabs;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.util.handlers.EnumHandler;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import com.solifungi.pkmskills.common.util.interfaces.IMetaName;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BlockCustomPlanks extends Block implements IHasModel, IMetaName
{
    public static final PropertyEnum<EnumHandler.EnumWoodType> VARIANT = PropertyEnum.<EnumHandler.EnumWoodType>create("variant", EnumHandler.EnumWoodType.class);
    private String name;

    public BlockCustomPlanks(String name)
    {
        super(Material.WOOD);
        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.WOOD);
        setCreativeTab(ModCreativeTabs.PKM_MISC);
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.EnumWoodType.MOSSY));

        this.name = name;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumHandler.EnumWoodType)state.getValue(VARIANT)).getMeta();
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
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumWoodType.byMetadata(meta));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(Item.getItemFromBlock(this),1, getMetaFromState(world.getBlockState(pos)));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumHandler.EnumWoodType)state.getValue(VARIANT)).getMeta();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    @Override
    public String getSpecialName(ItemStack stack)
    {
        return EnumHandler.EnumWoodType.values()[stack.getItemDamage()].getName();
    }

    @Override
    public void registerModels()
    {
        for(int i = 0; i < EnumHandler.EnumWoodType.values().length; i++)
        {
            PokemonSkills.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "planks_" + EnumHandler.EnumWoodType.values()[i].getName(),"inventory");
        }
    }
}

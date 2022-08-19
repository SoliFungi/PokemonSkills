package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.blocks.pokeanvil.ContainerPokeAnvil;
import com.solifungi.pkmskills.common.blocks.pokeanvil.GuiPokeAnvil;
import com.solifungi.pkmskills.common.blocks.pokeanvil.TileEntityPokeAnvil;
import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == Reference.GUI_POKE_ANVIL)
        {
            return new ContainerPokeAnvil(player.inventory,(TileEntityPokeAnvil)world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        if(ID == Reference.GUI_POKE_ANVIL)
        {
            return new GuiPokeAnvil(player.inventory,(TileEntityPokeAnvil)world.getTileEntity(new BlockPos(x, y, z)));
        }
        return null;
    }
}

package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.blocks.pokeanvil.TileEntityPokeAnvil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler
{
    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityPokeAnvil.class, new ResourceLocation("poke_anvil"));
    }
}

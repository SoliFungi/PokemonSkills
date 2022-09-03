package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.commands.CommandListPotions;
import com.solifungi.pkmskills.common.init.ModBlocks;
import com.solifungi.pkmskills.common.init.ModItems;
import com.solifungi.pkmskills.common.init.ModPotions;
import com.solifungi.pkmskills.common.init.ModStatusConditions;
import com.solifungi.pkmskills.common.util.interfaces.IHasModel;
import com.solifungi.pkmskills.common.world.gen.WorldGenCustomTrees;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHandler
{
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
        ModItems.setRepairItems();
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
        TileEntityHandler.registerTileEntities();
    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for(Item item : ModItems.ITEMS)
        {
            if(item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
        }

        for(Block block : ModBlocks.BLOCKS)
        {
            if(block instanceof IHasModel)
            {
                ((IHasModel)block).registerModels();
            }
        }
    }

    public static void preInitRegistries(FMLPreInitializationEvent event)
    {
        GameRegistry.registerWorldGenerator(new WorldGenCustomTrees(), 0);

        ModPotions.registerPotions();
        ModStatusConditions.registerStatusConditions();

        ConfigHandler.registerConfig(event);
    }

    public static void initRegistries(FMLInitializationEvent event)
    {
        SoundsHandler.registerSounds();

        NetworkRegistry.INSTANCE.registerGuiHandler(PokemonSkills.instance, new GuiHandler());
    }

    public static void postInitRegistries(FMLPostInitializationEvent event)
    {
        //TODOS
    }

    public static void serverRegistries(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandListPotions());
    }
}

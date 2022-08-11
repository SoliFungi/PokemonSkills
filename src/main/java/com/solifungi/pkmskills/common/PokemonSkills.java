package com.solifungi.pkmskills.common;

import com.solifungi.pkmskills.common.proxy.CommonProxy;
import com.solifungi.pkmskills.common.util.Reference;
import com.solifungi.pkmskills.common.util.handlers.RegistryHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;


/**
 * @author SoliFungi
 */

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class PokemonSkills
{

    public static Logger logger;

    @Mod.Instance
    public static PokemonSkills instance;


    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.COMMON)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        RegistryHandler.preInitRegistries();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event)
    {
        logger.info("Mod initialized:" + Reference.NAME);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event)
    {

    }
}

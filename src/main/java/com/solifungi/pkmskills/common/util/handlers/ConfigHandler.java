package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.PokemonSkills;
import com.solifungi.pkmskills.common.util.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler
{
    public static Configuration config;

    public static boolean isFreezeFrostbite = false;

    public static void init(File file)
    {
        config = new Configuration(file);

        String category;

        category = "statuses";
        config.addCustomCategoryComment(category,"Settings for mod status conditions");
        isFreezeFrostbite = config.getBoolean("isFreezeFrostbite",category,false,"Put true if you want frostbite status instead of freeze");

        config.save();
    }

    public static void registerConfig(FMLPreInitializationEvent event)
    {
        PokemonSkills.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
        PokemonSkills.config.mkdirs();
        init(new File(PokemonSkills.config.getPath(), Reference.MODID + ".cfg"));
    }
}

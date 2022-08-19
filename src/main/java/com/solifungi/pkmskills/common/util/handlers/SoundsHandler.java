package com.solifungi.pkmskills.common.util.handlers;

import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundsHandler
{
    public static SoundEvent BLOCK_POKEANVIL_FORGING, BLOCK_POKEANVIL_FINISH;

    public static void registerSounds()
    {
        BLOCK_POKEANVIL_FORGING = registerSound("block.pokeanvil.forging");
        BLOCK_POKEANVIL_FINISH = registerSound("block.pokeanvil.finish");
    }

    private static SoundEvent registerSound(String name)
    {
        ResourceLocation location = new ResourceLocation(Reference.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);

        return event;
    }
}

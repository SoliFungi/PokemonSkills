package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.util.Reference;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ModAvrilRecipes
{
    @SubscribeEvent
    public static void checkEdictCraft(AnvilUpdateEvent event)
    {
        if(event.getLeft() != ItemStack.EMPTY && event.getRight() != ItemStack.EMPTY)
        {
            ItemStack left = event.getLeft();
            ItemStack right = event.getRight();

//            if(left.getItem() == ModItems.PICKAXE_FIRE && right.getItem() == ModItems.DISASSEMBLING_HAMMER)
//            {
//                event.setMaterialCost(1);
//                event.setCost(30);
//
//                ItemStack result = new ItemStack(ModItems.PICKAXE_TOP_FIRE);
//                event.setOutput(result);
//            }
        }
    }
}

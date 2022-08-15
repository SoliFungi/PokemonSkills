package com.solifungi.pkmskills.common.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModCreativeTabs
{
    public static final CreativeTabs PKM_MISC = new CreativeTabs(CreativeTabs.getNextID(),"pkmMiscTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.ELEMENT_CRYSTAL,1,9);
        }
    };

    public static final CreativeTabs PKM_EQUIP = new CreativeTabs(CreativeTabs.getNextID(),"pkmEquipTab")
    {
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(ModItems.PICKAXE_FIRE);
        }
    };
}



//public class ModCreativeTabs extends CreativeTabs
//{
//    public ModCreativeTabs(String label)
//    {
//        super("pkmMiscTab");
//        this.setBackgroundImageName("pkmmisctab.png");
//    }
//    public ItemStack getTabIconItem() {return new ItemStack(items.DIAMOND);}
//}

//  Need initialization in PkmSkills.java


package com.solifungi.pkmskills.common.util.handlers;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class EnumHandler
{
    public static enum EnumElementType implements IStringSerializable
    {
        NORMAL(new Style().setColor(TextFormatting.GRAY),0, "normal"),
        FIGHT(new Style().setColor(TextFormatting.DARK_RED),1, "fight"),
        FLYING(new Style().setColor(TextFormatting.BLUE),2, "flying"),
        POISON(new Style().setColor(TextFormatting.DARK_GREEN),3, "poison"),
        GROUND(new Style().setColor(TextFormatting.GOLD),4, "ground"),
        ROCK(new Style().setColor(TextFormatting.YELLOW),5, "rock"),
        BUG(new Style().setColor(TextFormatting.GREEN),6, "bug"),
        GHOST(new Style().setColor(TextFormatting.BLUE),7, "ghost"),
        STEEL(new Style().setColor(TextFormatting.DARK_AQUA),8, "steel"),
        FIRE(new Style().setColor(TextFormatting.GOLD),9, "fire"),
        WATER(new Style().setColor(TextFormatting.DARK_BLUE),10, "water"),
        GRASS(new Style().setColor(TextFormatting.GREEN),11, "grass"),
        ELECTRIC(new Style().setColor(TextFormatting.YELLOW),12, "electric"),
        PSYCHIC(new Style().setColor(TextFormatting.RED),13, "psychic"),
        ICE(new Style().setColor(TextFormatting.AQUA),14, "ice"),
        DRAGON(new Style().setColor(TextFormatting.DARK_BLUE),15, "dragon"),
        DARK(new Style().setColor(TextFormatting.BLACK),16, "dark"),
        FAIRY(new Style().setColor(TextFormatting.LIGHT_PURPLE),17, "fairy");

        private static final EnumElementType[] META_LOOKUP = new EnumElementType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;
        private final Style color;


        EnumElementType(Style color, int meta, String name)
        {
            this(color,meta,name,name);
        }

        EnumElementType(Style color, int metaIn, String nameIn, String unlocalizedNameIn)
        {
            this.color = color;
            this.meta = metaIn;
            this.name = nameIn;
            this.unlocalizedName = unlocalizedNameIn;
        }


        @Override
        public String getName()
        {
            return this.name;
        }

        public String getUnlocalizedName()
        {
            return this.unlocalizedName;
        }

        public Style getColor(){
            return color;
        }

        public int getMeta()
        {
            return this.meta;
        }

        @Override
        public String toString()
        {
            return this.name;
        }

        public static EnumElementType byMetadata(int meta)
        {
            if (meta < 0 || meta >= META_LOOKUP.length)
            {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }

        static
        {
            for (EnumElementType enumelementtype : values())
            {
                META_LOOKUP[enumelementtype.getMeta()] = enumelementtype;
            }
        }
    }
}

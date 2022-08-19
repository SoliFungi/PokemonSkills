package com.solifungi.pkmskills.common.init;

import com.solifungi.pkmskills.common.blocks.BlockBlaze;
import com.solifungi.pkmskills.common.blocks.pokeanvil.BlockPokeAnvil;
import com.solifungi.pkmskills.common.blocks.tree.BlockCustomLeaves;
import com.solifungi.pkmskills.common.blocks.tree.BlockCustomLog;
import com.solifungi.pkmskills.common.blocks.tree.BlockCustomPlanks;
import com.solifungi.pkmskills.common.blocks.tree.BlockCustomSapling;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block BLOCK_BLAZE = new BlockBlaze("block_blaze");
    public static final Block POKE_ANVIL = new BlockPokeAnvil("poke_anvil");

    public static final Block LOG_MOSSY = new BlockCustomLog("log");
    public static final Block PLANKS_MOSSY = new BlockCustomPlanks("planks");
    public static final Block SAPLING_MOSSY = new BlockCustomSapling("sapling");
    public static final Block LEAVES_MOSSY = new BlockCustomLeaves("leaves");
}

package com.solifungi.pkmskills.common.commands;

import com.solifungi.pkmskills.common.init.ModStatusConditions;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
public class CommandListPotions extends CommandBase {

    @Override
    public String getName()
    {
        return "potionlist";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "command.potionlist.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length >= 1)
        {
            EntityLivingBase entityLivingBase = getEntity(server, sender, args[0], EntityLivingBase.class);

            //All potion effect
            if(args.length == 1 || "all".equalsIgnoreCase(args[1]))
            {
                Map<Potion, PotionEffect> potionEffectMap = entityLivingBase.getActivePotionMap();

                if(potionEffectMap.isEmpty())
                {
                    throw new CommandException("commands.potionlist.failure.potionEmpty", new Object[] {entityLivingBase.getName()});
                }
                else
                {
                    sender.sendMessage(new TextComponentTranslation("commands.potionlist.allPotions", new Object[] {potionEffectMap.size(), entityLivingBase.getName()}));
                    for(Potion potion : potionEffectMap.keySet())
                    {
                        sender.sendMessage(new TextComponentTranslation(potion.getName()));
                    }
                }
            }
            //Status conditions only
            else if(args.length == 2 && "status".equalsIgnoreCase(args[1]))
            {
                Map<Potion,Boolean> statusConditionMap = ModStatusConditions.getEntityStatusMap(entityLivingBase);

                if(!statusConditionMap.containsValue(true))
                {
                    throw new CommandException("commands.potionlist.failure.statusEmpty", new Object[] {entityLivingBase.getName()});
                }
                else
                {
                    sender.sendMessage(new TextComponentTranslation("commands.potionlist.allStatuses", new Object[] {ModStatusConditions.statusCount(statusConditionMap), entityLivingBase.getName()}));
                    for(Potion status : statusConditionMap.keySet())
                    {
                        if(statusConditionMap.get(status))
                        {
                            sender.sendMessage(new TextComponentTranslation(status.getName()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        if (args.length == 1)
        {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        else if (args.length == 2)
        {
            return getListOfStringsMatchingLastWord(args, new String[] {"all", "status"});
        }
        else
        {
            return Collections.emptyList();
        }
    }

}

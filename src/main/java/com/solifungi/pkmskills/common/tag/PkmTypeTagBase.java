package com.solifungi.pkmskills.common.tag;

import net.minecraft.entity.EntityLivingBase;

import java.util.*;

public class PkmTypeTagBase {
    private static Set<String> PKMTypeTagNames = new HashSet<>(EnumPkmTypeTag.PkmTypeTag.getAllName());
    private static Set<EnumPkmTypeTag.PkmTypeTag> PKMTypeTagArrays = new HashSet<>();

    public static Set<String> getPKMTypeTagNames() {
        return PKMTypeTagNames;
    }

    public static Set<EnumPkmTypeTag.PkmTypeTag> getPKMTypeTagArrays() {
        return PKMTypeTagArrays;
    }

    /**
     * 如果无无效效果返回null，如果有返回Set<>;
     * 相比Enum里的方法多一个无效判定，更推荐使用
     *
     * */
    public static Set<String> getNoneEffectiveType(String type_tag){
        if (EnumPkmTypeTag.PkmTypeTag.hasNoneEffectiveType(type_tag)){
            return new HashSet<String>(Arrays.asList(EnumPkmTypeTag.PkmTypeTag.getClassByName(type_tag).none_effective_type));
        }
        else {
            return null;
        }
    }

    /**
     * @param attacker_tags 攻击者属性
     * @param on_attacker_tags 被攻击者属性
     * @return power，伤害倍率，双精度属性浮点值
     * */
    public static double getDamagePower(Set<EnumPkmTypeTag.PkmTypeTag> attacker_tags, Set<EnumPkmTypeTag.PkmTypeTag> on_attacker_tags){
        double power = 1.0;
        for (EnumPkmTypeTag.PkmTypeTag attackerTypeTag : attacker_tags){
            for (EnumPkmTypeTag.PkmTypeTag onAttackerTypeTag : on_attacker_tags){
                //如果攻击者的0倍攻击属性列表含有被攻击者属性TAG
                if (attackerTypeTag.getNoneEffectiveType().contains(onAttackerTypeTag.getTypeTag())){
                    return 0.0;
                }
                //如果攻击者效果不好属性列表含有被攻击者TAG
                else if(attackerTypeTag.getNotEffectiveType().contains(onAttackerTypeTag.getTypeTag())){
                    power = power * 0.5;
                }
                //如果攻击者效果绝佳属性列表含有被攻击者TAG
                else if(attackerTypeTag.getSpuerEffectiveType().contains(onAttackerTypeTag.getTypeTag())){
                    power = power *2;
                }
            }
        }
        return power;

    }


//    public Set<EnumPkmTypeTag.PkmTypeTag> getEntityPKMtype_tagByName(Set<String> name){
//        for (String tag : name){
//            if (PKMtypeTagName.contains(tag)){
//                PKMtypetagArrays.add(EnumPkmTypeTag.PkmTypeTag.getClassByName(tag));
//            }
//        }
//        return PKMtypetagArrays;
//    }


    /**
     * 对实体的所有操作均在此处
     * */
    public class EntityTag extends PkmTypeTagBase{
        private EntityLivingBase entity;
        private Set<EnumPkmTypeTag.PkmTypeTag> pkmTypeTagSet;
        public EntityTag(EntityLivingBase entityLivingBase){
            this.entity = entityLivingBase;
            this.pkmTypeTagSet = getEntityPKMTypeTag();
        }


        public Set<EnumPkmTypeTag.PkmTypeTag> getEntityPKMTypeTag(){
            Collection<String> set_tag = entity.getTags();
            for (String tag : set_tag){
                if (PKMTypeTagNames.contains(tag)){
                    PKMTypeTagArrays.add(EnumPkmTypeTag.PkmTypeTag.getClassByName(tag));
                }
            }
            return PKMTypeTagArrays;
        }

        /**
         * @see EnumPkmTypeTag
         * 用于添加生物Tag
         * */
        public EnumPkmTypeTag.TypeTagUpdateEnum addEnitityTypeTag(String new_tag){
            //SET SIZE >2
            if (pkmTypeTagSet.size()>=2){
                return EnumPkmTypeTag.TypeTagUpdateEnum.TAG_LIST_IS_FULL;
            }
            //如果TAG属于本模组TAG
            if(PKMTypeTagNames.contains(new_tag)){
                pkmTypeTagSet.add(EnumPkmTypeTag.PkmTypeTag.getClassByName(new_tag));
                return EnumPkmTypeTag.TypeTagUpdateEnum.TAG_UPDATE_SUCCESS;
            }
            //如果不属于或者未定义
            else{
                return EnumPkmTypeTag.TypeTagUpdateEnum.TAG_NOT_DEFINE;
            }
        }

        /**
         * 用于清空TAG
         * */
        public void clearAllEnitityTypeTag(){
            pkmTypeTagSet.clear();
        }

        /**
         * 用于清空指定TAG
         * */
        public EnumPkmTypeTag.TypeTagUpdateEnum removeEnitityTypeTagByName(String remove_tag){
            if (!PKMTypeTagNames.contains(remove_tag)){
                return EnumPkmTypeTag.TypeTagUpdateEnum.TAG_NOT_DEFINE;
            }

            EnumPkmTypeTag.PkmTypeTag remove_tag_class = EnumPkmTypeTag.PkmTypeTag.getClassByName(remove_tag);
            if(PKMTypeTagArrays.contains(remove_tag_class)){
                PKMTypeTagArrays.remove(remove_tag_class);
                return EnumPkmTypeTag.TypeTagUpdateEnum.TAG_UPDATE_SUCCESS;
            }
            else {
                return EnumPkmTypeTag.TypeTagUpdateEnum.TAG_NOT_IN_LiST;
            }
        }

    }

    public class BlockTag{

    }

    public class ItemTag{

    }
}

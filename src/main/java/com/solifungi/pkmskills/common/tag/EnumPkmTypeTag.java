package com.solifungi.pkmskills.common.tag;

import net.minecraft.util.IStringSerializable;

import java.io.Serializable;
import java.util.*;

/**
 * 定义基本属性TAG
 * */
public class EnumPkmTypeTag implements Serializable {

    /**
     * 该类默认情况下是以攻击者属性为主
     * */
    public enum PkmTypeTag implements IStringSerializable {
        BUG("bug", new HashSet<>(Arrays.asList("grass","dark","psychic")), new HashSet<>(Arrays.asList("flight","flying","poison","ghost","steel","fire","fairy")), new HashSet<>(Arrays.asList("fire","fairy")),"None"),
        DARK("dark", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        DRAGON("dragon", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"fairy"),
        ELECTRIC("electric", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        FAIRY("fairy", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"ground"),
        FLIGHT("flight", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"ghost"),
        FIRE("fire", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        FLYING("flying", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        GHOST("ghost", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        GROUND("ground", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"flying"),
        ICE("ice", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        NORMAL("normal", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"ghost"),
        POISON("poison", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"steel"),
        PSYCHIC("psychic", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"dark"),
        ROCK("rock", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        STEEL("steel", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None"),
        WATER("water", new HashSet<>(Arrays.asList("ghost")), new HashSet<>(Arrays.asList("fire","fairy")),new HashSet<>(Arrays.asList("fairy")),"None");



        protected final static EnumPkmTypeTag.PkmTypeTag[] PKM_TYPE_TAGS= new EnumPkmTypeTag.PkmTypeTag[values().length];
        private final String type_tag;
        private final Set<String> super_effective_type;
        private final Set<String> effective_type;
        private final Set<String> not_effective_type;
        protected final String none_effective_type;


        PkmTypeTag(String type_tag, Set<String> super_effective_type, Set<String>effective_type, Set<String> not_effective_type, String none_effective_type ){
            this.type_tag = type_tag;
            this.super_effective_type = super_effective_type;
            this.effective_type = effective_type;
            this.not_effective_type = not_effective_type;
            this.none_effective_type = none_effective_type ;


        }

        @Override
        public String getName() {
            return "pokemon_skills_PKMTypeTag"+this.type_tag;
        }





        /**
         * get All Tags name in there
         * @return A set String
         * this is a  static method
         * */
        public static Set<String> getAllName(){
            Set<String> tag_names_set = new HashSet<>();
            for(PkmTypeTag ptg : PkmTypeTag.values()){
                tag_names_set.add(ptg.type_tag);
            }
            return tag_names_set;
        }

        /**
         * this method be instead of valueOf()
         * */
        public static PkmTypeTag getClassByName(String tag_name){
            return PkmTypeTag.valueOf(tag_name.toUpperCase());
        }

        public String getTypeTag() {
            return type_tag;
        }

        public Set<String> getSpuerEffectiveType() {
            return super_effective_type;
        }

        public Set<String> getEffectiveType() {
            return effective_type;
        }

        public Set<String> getNotEffectiveType() {
            return not_effective_type;
        }

        /**
         * 可能返回一个含None的集合，并不推荐使用（不方便处理）
         * @see PkmTypeTagBase getNoneEffectiveType
         * */
        public Set<String> getNoneEffectiveType() {
            return new HashSet<>(Arrays.asList(none_effective_type));
        }

        /**
         * 返回该属性是否拥有0x属性
         * @param type_tag 对象的属性tag
         * @return true:存在 false：不存在
         * @see PkmTypeTag getNoneEffectiveType
         * */
        public static boolean hasNoneEffectiveType(String type_tag){
            return !getClassByName(type_tag).type_tag.equals("None");
        }
    }

    /**
     * 定义属性Tag更新状态
     * */
    protected enum TypeTagUpdateEnum{
        TAG_UPDATE_SUCCESS("tag update success", "tag_update_success",true),
        TAG_IS_ALREADY("tag is already", "tag_already", false),
        TAG_NOT_DEFINE("tag not define in PkmTypeTagBase", "tag_not_define", false),
        TAG_LIST_IS_FULL("tag list is full", "tag_type_is_full", false),
        TAG_NOT_IN_LiST("tag is not in list", "tag_not_in_list", false)

        ;
        private final String type_tag_warring_info;
        private final String type_tag_warring_name;
        private final boolean update_flag;

        TypeTagUpdateEnum(String type_tag_warring_info, String type_tag_warring_name, boolean update_flag){
            this.type_tag_warring_info = type_tag_warring_info;
            this.type_tag_warring_name = type_tag_warring_name;
            this.update_flag = update_flag;
        }

        public String getType_tag_warring_info() {
            return this.type_tag_warring_info;
        }

        public String getType_tag_warring_name() {
            return this.type_tag_warring_name;
        }

        public boolean isUpdate() {
            return this.update_flag;
        }

        public boolean getUpdate_flag(){
            return this.update_flag;
        }
    }


}

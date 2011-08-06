package com.minecarts.towncrier;

import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.*;
import org.bukkit.Material;

public class Messages {
    public static HashMap<DamageCause,List<String>> PVP = new HashMap<DamageCause,List<String>>();
    public static HashMap<DamageCause,List<String>> Death = new HashMap<DamageCause,List<String>>();
    public static HashMap<String,List<String>> NoCause = new HashMap<String,List<String>>();
    
    public static HashMap<CreatureType,List<String>> CreatureNames = new HashMap<CreatureType,List<String>>();
    public static HashMap<Material,List<String>> ItemNames = new HashMap<Material,List<String>>();
    
    private static final Random random = new Random(); 
    public static void initialize(org.bukkit.util.config.Configuration config){
        //Player killed another player
        PVP.put(DamageCause.ENTITY_ATTACK,config.getStringList("PVP.ENTITY_ATTACK",null));
        PVP.put(DamageCause.CUSTOM,config.getStringList("PVP.CUSTOM",null));
        Death.put(DamageCause.ENTITY_ATTACK,config.getStringList("DEATH.ENTITY_ATTACK",null));
        Death.put(DamageCause.DROWNING,config.getStringList("DEATH.DROWNING",null));
        Death.put(DamageCause.BLOCK_EXPLOSION, config.getStringList("DEATH.BLOCK_EXPLOSION",null));
        Death.put(DamageCause.CONTACT,config.getStringList("DEATH.CONTACT",null));
        Death.put(DamageCause.SUFFOCATION, config.getStringList("DEATH.SUFFOCATION",null));
        Death.put(DamageCause.FALL, config.getStringList("DEATH.FALL",null));
        Death.put(DamageCause.FIRE, config.getStringList("DEATH.FIRE",null));
        Death.put(DamageCause.FIRE_TICK, config.getStringList("DEATH.FIRE_TICK",null));
        Death.put(DamageCause.LAVA, config.getStringList("DEATH.LAVA",null));
        Death.put(DamageCause.PROJECTILE, config.getStringList("DEATH.PROJECTILE",null));
        Death.put(DamageCause.ENTITY_EXPLOSION, config.getStringList("DEATH.ENTITY_EXPLOSION",null));
        Death.put(DamageCause.VOID, config.getStringList("DEATH.VOID",null));
        NoCause.put("Unknown", config.getStringList("NoCause.Unknown",null));
        NoCause.put("Suicide", config.getStringList("NoCause.Suicide",null));
        
        CreatureNames.put(CreatureType.CREEPER, config.getStringList("CreatureNames.CREEPER",null));
        CreatureNames.put(CreatureType.GHAST,config.getStringList("CreatureNames.GHAST",null));
        CreatureNames.put(CreatureType.GIANT, config.getStringList("CreatureNames.GIANT",null));
        CreatureNames.put(CreatureType.PIG_ZOMBIE, config.getStringList("CreatureNames.PIG_ZOMBIE",null));
        CreatureNames.put(CreatureType.SKELETON, config.getStringList("CreatureNames.SKELETON",null));
        CreatureNames.put(CreatureType.SLIME, config.getStringList("CreatureNames.SLIME",null));
        CreatureNames.put(CreatureType.SPIDER, config.getStringList("CreatureNames.SPIDER",null));
        CreatureNames.put(CreatureType.WOLF,config.getStringList("CreatureNames.WOLF",null));
        CreatureNames.put(CreatureType.ZOMBIE, config.getStringList("CreatureNames.ZOMBIE",null));
        
        ItemNames.put(Material.AIR, config.getStringList("ItemNames.AIR",null));

    }
    public static String getRandomMessage(String causeKey){
        List<String> messages = NoCause.get(causeKey);
        if(messages == null || messages.size() == 0) return "{0}[{1}] {2}{0} died!";
        return messages.get(random.nextInt(messages.size()));
    }
    public static String getRandomMessage(String type, DamageCause damageCause){
        List<String> messages;
        if(type.equalsIgnoreCase("PVP")){
            messages = PVP.get(damageCause);
        } else {
            messages = Death.get(damageCause);
        }
        if(messages == null || messages.size() == 0) return "{0}[{1}] {2}{0} died.";
        return messages.get(random.nextInt(messages.size()));
    }
    public static String getCeatureName(org.bukkit.entity.Entity monster){
        List<String> messages = null;
        if(monster instanceof Creeper){ messages = CreatureNames.get(CreatureType.CREEPER);
        } else if (monster instanceof Ghast) { messages = CreatureNames.get(CreatureType.GHAST);
        } else if (monster instanceof Giant) { messages = CreatureNames.get(CreatureType.GIANT);
        } else if (monster instanceof PigZombie) { messages = CreatureNames.get(CreatureType.PIG_ZOMBIE);
        } else if (monster instanceof Skeleton) { messages = CreatureNames.get(CreatureType.SKELETON);
        } else if (monster instanceof Slime) { messages = CreatureNames.get(CreatureType.SLIME);
        } else if (monster instanceof Spider) { messages = CreatureNames.get(CreatureType.SPIDER);
        } else if (monster instanceof Zombie) { messages = CreatureNames.get(CreatureType.ZOMBIE);
        } else if (monster instanceof Wolf) { messages = CreatureNames.get(CreatureType.WOLF);
        }
        if(monster == null) { return "a dispenser"; }
        if(messages == null) return monster.toString();
        return messages.get(random.nextInt(messages.size()));
    }
    public static String getItemName(Material item){        
        List<String> messages = ItemNames.get(item);
        if(messages == null || messages.size() == 0){
            String itemName = item.toString().replace('_', ' ');
            return itemName.toLowerCase();
        } else {
            return messages.get(random.nextInt(messages.size()));
        }       
    }
}

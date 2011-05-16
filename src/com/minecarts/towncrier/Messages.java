package com.minecarts.towncrier;

import java.util.List;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.*;

public class Messages {
    public static HashMap<DamageCause,List<String>> PVP = new HashMap<DamageCause,List<String>>();
    public static HashMap<DamageCause,List<String>> Death = new HashMap<DamageCause,List<String>>();
    public static HashMap<String,List<String>> NoCause = new HashMap<String,List<String>>();
    
    public static HashMap<CreatureType,List<String>> CreatureNames = new HashMap<CreatureType,List<String>>();
    
    private static final Random random = new Random(); 
    public static void initialize(){
        
        //Player killed another player
        PVP.put(DamageCause.ENTITY_ATTACK, Arrays.asList(
                "{0}[{1}] {2}{0} was killed by {3}''s{0} {4}{0}.",
                "{0}[{1}] {3}{0} just killed {2}{0} with a {4}{0}."
                ));
        //PVP Custom == suicide
        PVP.put(DamageCause.CUSTOM, Arrays.asList(
                "{0}[{1}] {2}{0} killed himself.",
                "{0}[{1}] {2}{0} took a free teleport to the spawn, sans items.",
                "{0}[{1}] {2}{0} took the easy way out.",
                "{0}[{1}] {2}{0} died from a self inflicted /kill."
                ));
        //Monster attack
        Death.put(DamageCause.ENTITY_ATTACK, Arrays.asList(
                "{0}[{1}] {2}{0} was killed by {3}{0}.",
                "{0}[{1}] {2}{0} took on {3}{0} and lost.",
                "{0}[{1}] {2}{0} failed to defeat {3}{0}.",
                "{0}[{1}] {3}{0} sent {2}{0} back to the spawn.",
                "{0}[{1}] {3}{0} was no match for {2}{0}."
                ));
        Death.put(DamageCause.DROWNING, Arrays.asList(
                "{0}[{1}] {2}{0} couldn''t find nemo in time.",
                "{0}[{1}] {2}{0} is swimming with the fishes.",
                "{0}[{1}] {2}{0} took a long walk off a short pier.",
                "{0}[{1}] {2}{0} couldn't beat the world record for longest held breath.",
                "{0}[{1}] {2}{0} is now 100% water.",
                "{0}[{1}] {2}{0} couldn't find any sunken treasure ships.",
                "{0}[{1}] {2}{0} fell asleep in the tub.",
                "{0}[{1}] {2}{0} quenched their thirst.",
                "{0}[{1}] Water is now {2}''s{0} best friend."
                ));
        Death.put(DamageCause.BLOCK_EXPLOSION, Arrays.asList(
                "{0}[{1}] {2}{0} died in an explosion."
                ));
        Death.put(DamageCause.CONTACT, Arrays.asList(
                "{0}[{1}] {2}{0} successfully hugged a cactus. Hooray!",
                "{0}[{1}] {2}{0} poked a cactus, but the cactus poked back.",
                "{0}[{1}] {2}{0} died to a cactus.",
                "{0}[{1}] {2}{0} tripped and fell onto a cactus."
                ));
        Death.put(DamageCause.SUFFOCATION, Arrays.asList(
                "{0}[{1}] {2}{0} cannot not breathe when burried alive.",
                "{0}[{1}] {2}{0} was burried alive."
                ));
        Death.put(DamageCause.FALL, Arrays.asList(
                "{0}[{1}] {2}{0} wondered why the ground was rushing toward him...",
                "{0}[{1}] The ground disappeared from under {2}{0}.",
                "{0}[{1}] {2}{0} fell to his death.",
                "{0}[{1}] {2}{0} didn't bring their parachute.",
                "{0}[{1}] {2}{0} forgot a parachute.",
                "{0}[{1}] {2}{0} took a misguided leap of faith."
                ));
        Death.put(DamageCause.FIRE, Arrays.asList(
                "{0}[{1}] {2}{0} died in a fire.",
                "{0}[{1}] {2}{0} forgot how to stop, drop, and roll.",
                "{0}[{1}] {2}{0} is not fire resistant.",
                "{0}[{1}] {2}{0} was burned alive.",
                "{0}[{1}] {2}{0} fell onto the barbecue.",
                "{0}[{1}] {2}{0} discovered fire.",
                "{0}[{1}] Did someone order a well done {2}{0}?"
                ));
        Death.put(DamageCause.FIRE_TICK, Arrays.asList(
                "{0}[{1}] {2}{0} couldn't find water quickly enough."
                ));
        Death.put(DamageCause.LAVA, Arrays.asList(
                "{0}[{1}] {2}{0} tried to reach the diamonds, but fell into the lava instead.",
                "{0}[{1}] The lava wouldn't stop chasing {2}{0}.",
                "{0}[{1}] {2}{0} now knows lava is not water.",
                "{0}[{1}] {2}{0} has become obsidian.",
                "{0}[{1}] {2}{0} fell into lava."
                ));
        Death.put(DamageCause.ENTITY_EXPLOSION, Arrays.asList(
                "{0}[{1}] KABLEWY! {2}{0} just died in an explosion!",
                "{0}[{1}] {2}{0} combusted into a thousand tiny pieces.",
                "{0}[{1}] {2}{0} found that TNT they lost earlier.",
                "{0}[{1}] {2}{0} had a premature detonation."
                ));
        Death.put(DamageCause.VOID, Arrays.asList(
                "{0}[{1}] {2}{0} fell through the world.",
                "{0}[{1}] {2}{0} discovered bedrock isn't always solid."
                ));
        NoCause.put("Unknown", Arrays.asList(
                "{0}[{1}] {2}{0} died from unknown causes."
                ));
        
        CreatureNames.put(CreatureType.CREEPER, Arrays.asList(
                "a creeper",
                "a cute creeper",
                "an adorable creeper",
                "a lonely creeper"
        ));
        CreatureNames.put(CreatureType.GHAST, Arrays.asList(
                "a frenzied ghast",
                "a ghast"
        ));
        CreatureNames.put(CreatureType.GIANT, Arrays.asList(
                "a huge zombie",
                "a giant"
        ));
        CreatureNames.put(CreatureType.PIG_ZOMBIE, Arrays.asList(
                "a zombie pigman"
        ));
        CreatureNames.put(CreatureType.SKELETON, Arrays.asList(
                "a skeleton",
                "an undead archer"
        ));
        CreatureNames.put(CreatureType.SLIME, Arrays.asList(
                "an icky slime",
                "a slime"
        ));
        CreatureNames.put(CreatureType.SPIDER, Arrays.asList(
                "a spider"
        ));
        CreatureNames.put(CreatureType.WOLF, Arrays.asList(
                "a wolf",
                "an angry wolf",
                "a hungry wolf"
        ));
        CreatureNames.put(CreatureType.ZOMBIE, Arrays.asList(
                "an undead zombie",
                "a hungry zombie",
                "a zombie"
        ));
        
    }
    public static String getRandomMessage(String causeKey){
        List<String> messages = NoCause.get(causeKey);
        return messages.get(random.nextInt(messages.size()));
    }
    public static String getRandomMessage(String type, DamageCause damageCause){
        List<String> messages;
        System.out.println(damageCause);
        if(type.equalsIgnoreCase("PVP")){
            messages = PVP.get(damageCause);
        } else {
            messages = Death.get(damageCause);
        }
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
}

package com.minecarts.towncrier;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;

import org.bukkit.event.*;
import org.bukkit.entity.Player;

import com.minecarts.towncrier.listener.*;

import com.minecarts.objectdata.ObjectData;
import com.minecarts.barrenschat.BarrensChat;
import com.minecarts.barrenschat.event.ChatChannelAnnounceEvent;
import com.minecarts.barrenschat.ChatChannel;

public class TownCrier extends org.bukkit.plugin.java.JavaPlugin{
    public final Logger log = Logger.getLogger("com.minecarts.templateplugin");

    private EntityListener entityListener;
    private EventListener eventListener;

    public BarrensChat barrensChat;

    private ChatChannel announceChannel;
    private final Random random = new Random();

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdf = getDescription();

        barrensChat = (BarrensChat) pm.getPlugin("BarrensChat");
        
        eventListener = new EventListener(this);
        entityListener = new EntityListener(this);

        this.announceChannel = barrensChat.channelHelper.getChannelFromName("PVP");
        
        //Register our events
        pm.registerEvent(Event.Type.CUSTOM_EVENT, this.eventListener, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, this.entityListener, Event.Priority.Monitor, this);

        log.info("[" + pdf.getName() + "] version " + pdf.getVersion() + " enabled.");

        getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    
    public void onDisable(){
        
    }

    public String getItemName(Material item){
        List<String> messages = getConfig().getList("ITEMS." + item.toString());
        if(messages == null || messages.size() == 0){
            String itemName = item.toString().replace('_', ' ');
            return itemName.toLowerCase();
        } else {
            return messages.get(random.nextInt(messages.size()));
        }
    }

    public String getCreatureName(Entity entity){
        List<String> names = getConfig().getList("CREATURES." + entity.toString());
        if(names == null || names.size() == 0) return entity.toString();
        return names.get(random.nextInt(names.size()));
    }

    //These damage causes are strings so that we can pass in UNKNOWN and other custom causes
    public String getMultiAttackMessage(String cause){
        List<String> messages = getConfig().getList("DEATH." + cause);
        if(messages == null || messages.size() == 0) return "{0}[{1}] {2}{0} died.";
        return messages.get(random.nextInt(messages.size()));
    }
    public String getSingleAttackMessage(String cause){
        List<String> messages = getConfig().getList("DEATH." + cause);
        if(messages == null || messages.size() == 0) return "{0}[{1}] {2}{0} died!";
        return messages.get(random.nextInt(messages.size()));
    }

    public void announceMessage(Player[] involvedPlayers, String format, String... args){
        ChatChannelAnnounceEvent evt = new ChatChannelAnnounceEvent(announceChannel, involvedPlayers, format, args);
        this.getServer().getPluginManager().callEvent(evt);
    }
}

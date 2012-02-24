package com.minecarts.towncrier;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;

import com.minecarts.chitchat.event.ChannelMessage;

import org.bukkit.event.*;
import org.bukkit.entity.Player;

import com.minecarts.towncrier.listener.*;


public class TownCrier extends org.bukkit.plugin.java.JavaPlugin{
    public final Logger log = Logger.getLogger("com.minecarts.to");

    private EntityListener entityListener;
    private EventListener eventListener;

    private final Random random = new Random();

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdf = getDescription();


        eventListener = new EventListener(this);
        entityListener = new EntityListener(this);
        
        //Register our events
        pm.registerEvent(Event.Type.CUSTOM_EVENT, this.eventListener, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, this.entityListener, Event.Priority.Monitor, this);


        // reload config command
        getCommand("crier").setExecutor(new CommandExecutor() {
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if(!sender.hasPermission("towncrier.reload")) return true; // "hide" command output for non-ops

                if(args[0].equalsIgnoreCase("reload")) {
                    TownCrier.this.reloadConfig();
                    sender.sendMessage("TownCrier config reloaded.");
                    return true;
                }
                return false;
            }
        });

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
        String entityName = entity.toString();
        if(entity instanceof Wolf){ //Handle wolves becuase they have data in their toString
            entityName = "CraftWolf";
        }
        List<String> names = getConfig().getList("CREATURES." + entityName);
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
        ChannelMessage evt = new ChannelMessage("Announcement",involvedPlayers, format, args);
        this.getServer().getPluginManager().callEvent(evt);
    }
}

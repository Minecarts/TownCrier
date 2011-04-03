package com.minecarts.towncrier;

import java.util.logging.Logger;

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
	
	public ObjectData objectData;
	public BarrensChat barrensChat;
	
	private ChatChannel announceChannel; 

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        PluginDescriptionFile pdf = getDescription();
        
        objectData = (ObjectData) pm.getPlugin("ObjectData");
        barrensChat = (BarrensChat) pm.getPlugin("BarrensChat");
        
        eventListener = new EventListener(this);
        entityListener = new EntityListener(this);

        this.announceChannel = barrensChat.channelHelper.getChannelFromName("PVP");
        
        //Register our events
        pm.registerEvent(Event.Type.CUSTOM_EVENT, this.eventListener, Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, this.entityListener, Event.Priority.Monitor, this);

        //Initialize our messages because java isn't super awesome
        Messages.initialize();
        
        log.info("[" + pdf.getName() + "] version " + pdf.getVersion() + " enabled.");
    }
    
    public void onDisable(){
        
    }
    
    public void announceMessage(Player[] involvedPlayers, String format, String... args){
        ChatChannelAnnounceEvent evt = new ChatChannelAnnounceEvent(announceChannel, involvedPlayers, format, args);
        this.getServer().getPluginManager().callEvent(evt);
    }
}

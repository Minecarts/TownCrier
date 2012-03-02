package com.minecarts.towncrier.listener;

import java.text.MessageFormat;

import org.bukkit.event.Event;
import org.bukkit.entity.Player;
import com.minecarts.towncrier.TownCrier;

import com.minecarts.sandandgravel.event.*;
import com.minecarts.sandandgravel.game.Game.State;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


public class EventListener implements Listener {
        
    private TownCrier plugin;
    public EventListener(TownCrier plugin){
        this.plugin = plugin;
    }

    private static enum events {
        GameCompleteEvent,
      }

    /* Needs SAG support to throw a new event type
    @EventHandler(priority = EventPriority.MONITOR)
    public void onCustomEvent(CustomEvent event){
        try {
            events.valueOf(event.getEventName());
          } catch (IllegalArgumentException e) {
            return;
          }
      
          switch (events.valueOf(event.getEventName())){
              case GameCompleteEvent:  { 
                     GameCompleteEvent e = (GameCompleteEvent) event;
                     Player[] involvedPlayers = {e.getPlayerGravel(),e.getPlayerSand()};
                     switch(e.getGameState()){
                         case WINNER_SAND:
                             plugin.announceMessage(involvedPlayers,plugin.getConfig().getString("EXTERNAL.SAG_WIN"),e.getPlayerSand().getDisplayName(),e.getPlayerGravel().getDisplayName());
                             break;
                         case WINNER_GRAVEL:
                             plugin.announceMessage(involvedPlayers,plugin.getConfig().getString("EXTERNAL.SAG_WIN"),e.getPlayerGravel().getDisplayName(),e.getPlayerSand().getDisplayName());
                             break;
                         case GAME_TIE:
                             plugin.announceMessage(involvedPlayers,plugin.getConfig().getString("EXTERNAL.SAG_TIE"),e.getPlayerSand().getDisplayName(),e.getPlayerGravel().getDisplayName());
                             break;
                         default:
                             System.out.println("TownCrier> Unknown GameCompleteEvent state: " + e.getGameState());
                             break;
                     }
                  } //GameCompleteEvent
                  break;
          } //switch
    }
    */
}

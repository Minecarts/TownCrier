package com.minecarts.towncrier.listener;

import java.text.MessageFormat;

import org.bukkit.event.Event;
import org.bukkit.entity.Player;
import com.minecarts.towncrier.TownCrier;

import com.minecarts.sandandgravel.event.*;
import com.minecarts.sandandgravel.game.Game.State;



public class EventListener extends org.bukkit.event.CustomEventListener{
    
    private final String SaG_Defeat = "{0}[{1}] {2}{0} defeated {3}{0} at Sand and Gravel!";
    private final String SaG_Tie = "{2}{0} and {3}{0} tied in an epic match of Sand and Gravel!";
    
    private TownCrier plugin;
    public EventListener(TownCrier plugin){
        this.plugin = plugin;
    }

    private static enum events {
        GameCompleteEvent,
      }

    @Override
    public void onCustomEvent(Event event){
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
                             plugin.announceMessage(involvedPlayers,this.SaG_Defeat,e.getPlayerSand().getDisplayName(),e.getPlayerGravel().getDisplayName());
                             break;
                         case WINNER_GRAVEL:
                             plugin.announceMessage(involvedPlayers,this.SaG_Defeat,e.getPlayerGravel().getDisplayName(),e.getPlayerSand().getDisplayName());
                             break;
                         case GAME_TIE:
                             plugin.announceMessage(involvedPlayers,this.SaG_Tie,e.getPlayerSand().getDisplayName(),e.getPlayerGravel().getDisplayName());
                             break;
                         default:
                             System.out.println("TownCrier> Unknown GameCompleteEvent state: " + e.getGameState());
                             break;
                     }
                  } //GameCompleteEvent
                  break;
          } //switch
    }
}

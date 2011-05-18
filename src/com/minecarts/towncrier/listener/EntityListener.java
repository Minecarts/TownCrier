package com.minecarts.towncrier.listener;


import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.entity.Player;

import com.minecarts.towncrier.Messages;
import com.minecarts.towncrier.TownCrier;

public class EntityListener extends org.bukkit.event.entity.EntityListener{ 
    private TownCrier plugin;

    public EntityListener(TownCrier plugin){
        this.plugin = plugin;
    }
    @Override
    public void onEntityDeath(EntityDeathEvent eventDeath){
        if(!(eventDeath.getEntity() instanceof Player)) return;
        Player victim = (Player) eventDeath.getEntity();
        EntityDamageEvent eventDamage = (EntityDamageEvent) plugin.objectData.shared.get(eventDeath.getEntity(), "lastDamageEvent");

        if(eventDamage == null){
            //Unknown causes
            String format = Messages.getRandomMessage("Unknown");
            Player[] involvedPlayers = {victim};
            plugin.announceMessage(involvedPlayers, format,victim.getDisplayName());
            
        } else if(eventDamage instanceof EntityDamageByEntityEvent){
            //Entity vs Entity!
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) eventDamage;
            Integer damage = event.getDamage();
            
            if(event.getDamager() instanceof Player){
                Player attacker = (Player) event.getDamager();
                Player[] involvedPlayers = {attacker, victim};
                if(attacker.getName() == victim.getName()){
                    //Suicide!
                    plugin.announceMessage(involvedPlayers, 
                            Messages.getRandomMessage("Suicide"), 
                            victim.getDisplayName()
                            );
                } else {
                  //PVP Kill
                    plugin.announceMessage(
                            involvedPlayers, 
                            Messages.getRandomMessage("PVP",event.getCause()), 
                            victim.getDisplayName(), 
                            attacker.getDisplayName(),
                            Messages.getItemName(attacker.getItemInHand().getType()));
                }
            } else {
                Player[] involvedPlayers = {victim};
                //Entity Kill
                plugin.announceMessage(
                        involvedPlayers, 
                        Messages.getRandomMessage("Death",event.getCause()), 
                        victim.getDisplayName(), 
                        Messages.getCeatureName(event.getDamager()));
            }

        } else if (eventDamage instanceof EntityDamageByProjectileEvent){
            //EntityDamageByProjectileEvent event = (EntityDamageByProjectileEvent) eventDamage;
            System.out.println("shot death?!");
        } else {
            Player[] involvedPlayers = {victim};
            //General death
            plugin.announceMessage(
                    involvedPlayers, 
                    Messages.getRandomMessage("Death", eventDamage.getCause()), 
                    victim.getDisplayName());
        }
        //e.setCancelled(true);
    }
}

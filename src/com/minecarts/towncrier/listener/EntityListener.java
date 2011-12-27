package com.minecarts.towncrier.listener;


import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Player;

import com.minecarts.towncrier.TownCrier;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EntityListener extends org.bukkit.event.entity.EntityListener{ 
    private TownCrier plugin;

    public EntityListener(TownCrier plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        
        //Get rid of Notch's duplicate death messages
        if(event instanceof PlayerDeathEvent){
            ((PlayerDeathEvent) event).setDeathMessage(null);
        }
        
        Player victim = (Player) event.getEntity();
        EntityDamageEvent lastDamageEvent = victim.getLastDamageCause();
        if(lastDamageEvent == null){ //No last damage event
            //Unknown causes
            Player[] involvedPlayers = {victim};

            plugin.announceMessage(involvedPlayers,
                    plugin.getSingleAttackMessage("UNKNOWN"),
                    victim.getDisplayName());

        } else {
            DamageCause cause = lastDamageEvent.getCause();
            if(lastDamageEvent instanceof EntityDamageByEntityEvent){
                EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) lastDamageEvent;
                if(e.getDamager() instanceof Player){
                    Player attacker = (Player) e.getDamager();
                    Player[] involvedPlayers = {attacker, victim};

                    //If it's the same player, it's a suicide
                    if(attacker.getName().equals(victim.getName())){
                        plugin.announceMessage(involvedPlayers,
                                plugin.getSingleAttackMessage("SUICIDE"),
                                victim.getDisplayName());
                        return;
                    }

                    plugin.announceMessage(involvedPlayers,
                            plugin.getMultiAttackMessage("PVP"),
                            victim.getDisplayName(),
                            attacker.getDisplayName(),
                            plugin.getItemName(attacker.getItemInHand().getType())
                    );
                    return;
                } else {  //It wasn't a player, so..
                    Player[] involvedPlayers = {victim};
                    plugin.announceMessage(involvedPlayers,
                            plugin.getMultiAttackMessage(cause.name()),
                            victim.getDisplayName(),
                            plugin.getCreatureName(e.getDamager())
                    );
                    return;
                }
            }
            //If we got to this point, it had to have been the player dieing alone
                Player[] involvedPlayers = {victim};
                plugin.announceMessage(involvedPlayers,
                        plugin.getSingleAttackMessage(cause.name()),
                        victim.getDisplayName()
                );
        } //lastDamageEvent == null
    }//onEntityDeath()
} //class EntityListener
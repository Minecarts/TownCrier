package com.minecarts.towncrier.listener;


import org.bukkit.entity.Arrow;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Player;

import com.minecarts.towncrier.TownCrier;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.text.MessageFormat;

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
            return;
        }

        DamageCause cause = lastDamageEvent.getCause();

        //Log the death location to the console
        System.out.println(MessageFormat.format("TownCrier> {0} died at ({1},{2},{3}) from {4}",
                victim.getName(),
                victim.getLocation().getBlockX(),
                victim.getLocation().getBlockY(),
                victim.getLocation().getBlockZ(),
                cause.name()
        ));

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
            }

            //Check to see if it's an arrow
            if(e.getDamager() instanceof Arrow){
                Arrow arrow = (Arrow)e.getDamager();
                if(arrow.getShooter() instanceof Player){
                    Player[] involvedPlayers = {victim, (Player)arrow.getShooter()};
                    plugin.announceMessage(involvedPlayers,
                            plugin.getMultiAttackMessage(cause.name()),
                            victim.getDisplayName(),
                            ((Player)arrow.getShooter()).getDisplayName()
                    );
                } else {
                    String creatureName = "Unknown";
                    if(arrow.getShooter() == null){
                        creatureName = "a dispenser";
                    } else {
                        creatureName = plugin.getCreatureName(arrow.getShooter());
                    }

                    Player[] involvedPlayers = {victim};
                    plugin.announceMessage(involvedPlayers,
                            plugin.getMultiAttackMessage(cause.name()),
                            victim.getDisplayName(),
                            creatureName);
                }
                return;
            }

            if(e.getDamager() instanceof Wolf){
                //Check if it's a wolf, if so we need to do some custom things due to the name() returning
                //  lots of metadata about the object
                Wolf wolf = (Wolf) e.getDamager();
                if(wolf.isTamed()){
                    Player[] involvedPlayers = {victim,(Player)wolf.getOwner()};
                    plugin.announceMessage(involvedPlayers,
                            plugin.getMultiAttackMessage("PVP_WOLF"),
                            victim.getDisplayName(),
                            ((Player) wolf.getOwner()).getDisplayName()
                    );
                    return;
                }
            }
            //Else at this point, try and handle it based upon the name in the config
            Player[] involvedPlayers = {victim};
            plugin.announceMessage(involvedPlayers,
                    plugin.getMultiAttackMessage(cause.name()),
                    victim.getDisplayName(),
                    plugin.getCreatureName(e.getDamager())
            );
            return;
        }

        //If we got to this point, it had to have been the player dieing alone
            Player[] involvedPlayers = {victim};
            plugin.announceMessage(involvedPlayers,
                    plugin.getSingleAttackMessage(cause.name()),
                    victim.getDisplayName()
            );
    }//onEntityDeath()
} //class EntityListener
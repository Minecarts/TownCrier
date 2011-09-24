package com.minecarts.towncrier.listener;


import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Player;

import com.minecarts.towncrier.Messages;
import com.minecarts.towncrier.TownCrier;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerVelocityEvent;

public class EntityListener extends org.bukkit.event.entity.EntityListener{ 
    private TownCrier plugin;

    public EntityListener(TownCrier plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEntityDeath(EntityDeathEvent eventDeath){
        if(!(eventDeath.getEntity() instanceof Player)) return;
        
        //Get rid of Notch's duplicate death messages
        if(eventDeath instanceof PlayerDeathEvent){
            ((PlayerDeathEvent) eventDeath).setDeathMessage(null);
        }

        Player victim = (Player) eventDeath.getEntity();
        EntityDamageEvent eventDamage = (EntityDamageEvent) victim.getLastDamageCause();
        if(eventDamage == null){ //No last damage event
            //Unknown causes
            String format = Messages.getRandomMessage("Unknown");
            Player[] involvedPlayers = {victim};
            plugin.announceMessage(involvedPlayers, format,victim.getDisplayName());
        } else if(eventDamage instanceof EntityDamageByEntityEvent){
            //Entity vs Entity!
            EntityDamageByEntityEvent event = (EntityDamageByEntityEvent) eventDamage;
            Integer damage = event.getDamage();

            //PVP of some sort
            if(event.getDamager() instanceof Player){
                Player attacker = (Player) event.getDamager();
                Player[] involvedPlayers = {attacker, victim};
                //Suicide!
                if(attacker.getName().equals(victim.getName())){
                    plugin.announceMessage(involvedPlayers, Messages.getRandomMessage("Death",DamageCause.SUICIDE), victim.getDisplayName());
                //PVP Kill
                } else {
                    plugin.announceMessage(involvedPlayers,Messages.getRandomMessage("PVP",event.getCause()), victim.getDisplayName(),attacker.getDisplayName(),Messages.getItemName(attacker.getItemInHand().getType()));
                }
            //Death by projectile
            } else if (event.getDamager() instanceof Projectile){
                org.bukkit.entity.LivingEntity shooter = ((Projectile) event.getDamager()).getShooter();
                if(shooter instanceof Player){
                    Player attacker = (Player) shooter;
                    Player[] involvedPlayers = {victim, attacker};
                    plugin.announceMessage(involvedPlayers, Messages.getRandomMessage("Death",event.getCause()),victim.getDisplayName(),attacker.getDisplayName());
                } else {
                    Player[] involvedPlayers = {victim};
                    plugin.announceMessage(involvedPlayers, Messages.getRandomMessage("Death",event.getCause()),victim.getDisplayName(),Messages.getCeatureName(shooter));
                }
            //Non player entity killed someone
            } else {
                Player[] involvedPlayers = {victim};
                //Entity Kill
                plugin.announceMessage(involvedPlayers, Messages.getRandomMessage("Death",event.getCause()), victim.getDisplayName(), Messages.getCeatureName(event.getDamager()));
            }
        } else { //Not entitydamagedbyentity event
            Player[] involvedPlayers = {victim};
            //General death
            plugin.announceMessage(involvedPlayers, Messages.getRandomMessage("Death", eventDamage.getCause()), victim.getDisplayName());
        }
    }
}
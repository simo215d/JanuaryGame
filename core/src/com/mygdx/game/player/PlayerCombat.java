package com.mygdx.game.player;

import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerEffects.*;
import com.mygdx.game.world.CollisionDetector;
import com.mygdx.game.world.Level1;

import java.util.ArrayList;

public class PlayerCombat {
    private int maxHealth;
    private int health;
    private boolean isImmuneToDamage = false;
    private int maxMana;
    private int mana;
    private int manaGenerationRate = 5;
    private int previousManaTime = 0;
    //attack damage
    private int attack1Damage = 2;
    public static int attack2Damage = 4;
    public static int attack3Damage = 8;
    public static int attack4Damage = 1;
    //mana costs
    public static int attack1Mana=0;
    public static int attack2Mana=12;
    public static int attack3Mana=3;
    public static int attack4Mana=25;
    public static int attack5Mana=42;
    public static int attack6Mana=23;
    //spell keepers
    private ArrayList<FireBall> fireBalls = new ArrayList<>();
    //we need this variable, because we need unique names for every fire ball because of collision detection
    private int fireBallCount = 0;
    private Meteor currentMeteor;
    private ArrayList<Orb> orbs = new ArrayList<>();
    private FireShield fireShield = null;
    private FireBreath fireBreath = null;

    public PlayerCombat(){
        maxHealth = 100;
        health=maxHealth;
        maxMana = 100;
        mana=maxMana;
    }

    public void takeDamage(int damage){
        if (!isImmuneToDamage) {
            health -= damage;
            if (health <= 0) {
                health = maxHealth;
            }
            System.out.println("im player and i took: " + damage + " damage.");
        } else System.out.println("im immune bitch!");
    }

    public void drainMana(int spellNumber){
        switch (spellNumber){
            case 1: mana-=attack1Mana; break;
            case 2: mana-=attack2Mana; break;
            case 3: mana-=attack3Mana; break;
            case 4: mana-=attack4Mana; break;
            case 5: mana-=attack5Mana; break;
            case 6: mana-=attack6Mana; break;
        }
    }

    public void generateMana(){
        if ((int)darkknight.gameTimeCentiSeconds/10>previousManaTime){
            previousManaTime=(int)darkknight.gameTimeCentiSeconds/10;
            if (mana<=100-manaGenerationRate){
                mana+=manaGenerationRate;
            } else mana=maxMana;
        }
    }

    //sword is 1
    public void attack1(){
        if (mana>=attack1Mana) {
            drainMana(1);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            //if the player jumps and immediately presses 1, then his foot is still grounded.
            // he then starts attack, but that is overwritten when the player exits the walkable collider because he is on his way upwards.
            // therefore we need to make sure that the player is static when he attacks
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attacking1");
            //handling the actual attack logic
            //if sprite facing left
            if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()) {
                if (CollisionDetector.currentLeftEnemies.size() > 0) {
                    for (String string : CollisionDetector.currentLeftEnemies) {
                        Level1.level1Enemies.attackAnEnemy(string, attack1Damage);
                    }
                }
            }
            //if sprite facing right
            if (!darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()) {
                if (CollisionDetector.currentRightEnemies.size() > 0) {
                    for (String string : CollisionDetector.currentRightEnemies) {
                        Level1.level1Enemies.attackAnEnemy(string, attack1Damage);
                    }
                }
            }
        }
    }

    //fireball is 2
    public void attack2(){
        if (mana>=attack2Mana) {
            drainMana(2);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            //if the player jumps and immediately presses 1, then his foot is still grounded.
            // he then starts attack, but that is overwritten when the player exits the walkable collider because he is on his way upwards.
            // therefore we need to make sure that the player is static when he attacks
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attacking2");
            //handling the actual attack logic
            darkknight.player.getPlayerCombat().attack2FireBall();
        }
    }

    //this method can be called from the animation or directly from above method depending on when fireball should spawn
    public void attack2FireBall(){
        FireBall fireBall = new FireBall("FireBall"+fireBallCount);
        fireBallCount++;
        fireBalls.add(fireBall);
    }

    //meteor is 5
    public void attack3(){
        //i only allow 1 meteor
        if (currentMeteor==null && mana>=attack5Mana) {
            drainMana(5);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attacking3");
            currentMeteor = new Meteor();
        }
    }

    //orbs are 3
    public void attack4(){
        if (orbs.size()>0){
            for (Orb orb : orbs){
                if (orb.getAnimationState().equals("Stationary")){
                    orb.setAnimationState("Launching");
                    break;
                }
            }
        }
        if (orbs.size()==0 && mana>=attack3Mana){
            drainMana(3);
            Player.orbsToDelete.clear();
            for (int i = 1; i <= 3; i++) {
                orbs.add(new Orb("playerOrb"+i));
            }
        }
    }

    //fireShield is 4
    public void attack5(){
        if (fireShield==null && mana>=attack4Mana){
            drainMana(4);
            fireShield = new FireShield();
            //sets player to be immune
            setImmuneToDamage(true);
            //in the fireShield class this is set to false when it dies
        }
    }

    public void attack6(){
        if (fireBreath==null && mana>=attack6Mana){
            drainMana(6);
            darkknight.player.getPlayerMovement().setAttacking1(true);
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attacking6");
            fireBreath = new FireBreath();
        }
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<FireBall> getFireBalls(){
        return fireBalls;
    }

    public Meteor getCurrentMeteor() {
        return currentMeteor;
    }

    public void setCurrentMeteorToNull(){
        currentMeteor=null;
    }

    public ArrayList<Orb> getOrbs(){
        return orbs;
    }

    public FireShield getFireShield(){
        return fireShield;
    }

    public void setFireShieldToNull(){
        fireShield=null;
    }

    public void setImmuneToDamage(boolean bool){
        isImmuneToDamage=bool;
    }

    public boolean getIsImmuneToDamage(){
        return isImmuneToDamage;
    }

    public void setFireBreathToNull(){
        fireBreath=null;
    }

    public FireBreath getFireBreath(){
        return fireBreath;
    }

    public int getMaxMana(){
        return maxMana;
    }

    public int getMana(){
        return mana;
    }
}

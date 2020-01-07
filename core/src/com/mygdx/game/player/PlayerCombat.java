package com.mygdx.game.player;

import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerEffects.FireBall;
import com.mygdx.game.player.PlayerEffects.FireShield;
import com.mygdx.game.player.PlayerEffects.Meteor;
import com.mygdx.game.player.PlayerEffects.Orb;
import com.mygdx.game.world.CollisionDetector;
import com.mygdx.game.world.Level1;

import java.util.ArrayList;

public class PlayerCombat {
    private int maxHealth;
    private int health;
    private boolean isImmuneToDamage = false;
    //attack damage
    private int attack1Damage = 2;
    public static int attack2Damage = 4;
    public static int attack3Damage = 8;
    public static int attack4Damage = 1;
    private ArrayList<FireBall> fireBalls = new ArrayList<>();
    //we need this variable, because we need unique names for every fire ball because of collision detection
    private int fireBallCount = 0;
    private Meteor currentMeteor;
    private ArrayList<Orb> orbs = new ArrayList<>();
    private FireShield fireShield = null;

    public PlayerCombat(){
        maxHealth = 100;
        health=maxHealth;
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

    public void attack1(){
        darkknight.player.getPlayerMovement().setAttacking1(true);
        //if the player jumps and immediately presses 1, then his foot is still grounded.
        // he then starts attack, but that is overwritten when the player exits the walkable collider because he is on his way upwards.
        // therefore we need to make sure that the player is static when he attacks
        darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0,0);
        darkknight.player.getPlayerGraphics().setAnimationState("attacking1");
        //handling the actual attack logic
        //if sprite facing left
        if (darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
            if (CollisionDetector.currentLeftEnemies.size()>0){
                for (String string : CollisionDetector.currentLeftEnemies){
                    Level1.level1Enemies.attackAnEnemy(string, attack1Damage);
                }
            }
        }
        //if sprite facing right
        if (!darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
            if (CollisionDetector.currentRightEnemies.size()>0){
                for (String string : CollisionDetector.currentRightEnemies){
                    Level1.level1Enemies.attackAnEnemy(string, attack1Damage);
                }
            }
        }
    }

    public void attack2(){
        darkknight.player.getPlayerMovement().setAttacking1(true);
        //if the player jumps and immediately presses 1, then his foot is still grounded.
        // he then starts attack, but that is overwritten when the player exits the walkable collider because he is on his way upwards.
        // therefore we need to make sure that the player is static when he attacks
        darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0,0);
        darkknight.player.getPlayerGraphics().setAnimationState("attacking2");
        //handling the actual attack logic
        darkknight.player.getPlayerCombat().attack2FireBall();
    }

    //this method can be called from the animation or directly from above method depending on when fireball should spawn
    public void attack2FireBall(){
        FireBall fireBall = new FireBall("FireBall"+fireBallCount);
        fireBallCount++;
        fireBalls.add(fireBall);
    }

    //meteor
    public void attack3(){
        //i only allow 1 meteor
        if (currentMeteor==null) {
            darkknight.player.getPlayerMovement().setAttacking1(true);
            darkknight.player.getPlayerPhysics().getPlayerBody().setLinearVelocity(0, 0);
            darkknight.player.getPlayerGraphics().setAnimationState("attacking3");
            currentMeteor = new Meteor();
        }
    }

    public void attack4(){
        if (orbs.size()>0){
            for (Orb orb : orbs){
                if (orb.getAnimationState().equals("Stationary")){
                    orb.setAnimationState("Launching");
                    break;
                }
            }
        }
        if (orbs.size()==0){
            Player.orbsToDelete.clear();
            for (int i = 1; i <= 3; i++) {
                orbs.add(new Orb("playerOrb"+i));
            }
        }
    }

    public void attack5(){
        if (fireShield==null){
            fireShield = new FireShield();
            //sets player to be immune
            setImmuneToDamage(true);
            //in the fireShield class this is set to false when it dies
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
}

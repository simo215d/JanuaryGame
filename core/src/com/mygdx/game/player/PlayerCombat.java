package com.mygdx.game.player;

import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerEffects.FireBall;
import com.mygdx.game.world.CollisionDetector;

import java.util.ArrayList;

public class PlayerCombat {
    private int maxHealth;
    private int health;
    private ArrayList<FireBall> fireBalls = new ArrayList<>();

    public PlayerCombat(){
        maxHealth = 100;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        health-=damage;
        if (health<=0){
            health=maxHealth;
        }
        System.out.println("im player and i took: "+damage+" damage.");
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
                    darkknight.level1.level1Enemies.attack1(string);
                }
            }
        }
        //if sprite facing right
        if (!darkknight.player.getPlayerGraphics().getSpritePlayer().isFlipX()){
            if (CollisionDetector.currentRightEnemies.size()>0){
                for (String string : CollisionDetector.currentRightEnemies){
                    darkknight.level1.level1Enemies.attack1(string);
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
    }

    //this method is called from the animation
    public void attack2FireBall(){
        FireBall fireBall = new FireBall("FireBall"+fireBalls.size());
        fireBalls.add(fireBall);
        for (FireBall fireBall1 : fireBalls){
            System.out.println("we have the fireball: "+fireBall1.getName());
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
}

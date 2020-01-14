package com.mygdx.game.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerEffects.DeathAnimationEffect;
import com.mygdx.game.player.PlayerEffects.FireBall;
import com.mygdx.game.player.PlayerEffects.Orb;
import com.mygdx.game.world.CollisionDetector;
import com.mygdx.game.world.Level1;

import java.util.ArrayList;

public class Player {
    private PlayerGraphics playerGraphics;
    private PlayerPhysics playerPhysics;
    private PlayerMovement playerMovement;
    private PlayerUI playerUI;
    private PlayerCombat playerCombat;
    private Body playerBody;
    public static ArrayList<FireBall> fireBallsToDelete = new ArrayList<>();
    public static ArrayList<Orb> orbsToDelete = new ArrayList<>();
    //2 next fields are required to see if we should render the character red as an indication of damage taken
    public static int renderRedStartTime;
    public static boolean renderRed = false;
    public static boolean isDead = false;
    private DeathAnimationEffect deathAnimationEffect = null;

    public Player(){
        playerPhysics = new PlayerPhysics();
        playerBody=playerPhysics.getPlayerBody();
        playerGraphics = new PlayerGraphics();
        playerMovement = new PlayerMovement(playerBody);
        playerCombat = new PlayerCombat();
        playerUI = new PlayerUI();
    }

    public void draw(Batch batch, OrthographicCamera camera){
        //check if we should still render him red
        if (renderRed && darkknight.gameTimeCentiSeconds-renderRedStartTime>1){
            renderRed=false;
        }
        playerCombat.generateMana();
        playerGraphics.draw(batch);
        playerUI.draw(batch, camera, playerCombat.getHealth(), playerCombat.getMaxHealth(), playerCombat.getMana(), playerCombat.getMaxMana());
        for (FireBall fireBall : getPlayerCombat().getFireBalls()){
            fireBall.draw(batch);
        }
        if (playerCombat.getCurrentMeteor()!=null) {
            playerCombat.getCurrentMeteor().draw(batch);
        }
        for (Orb orb : getPlayerCombat().getOrbs()){
            orb.draw(batch);
        }
        if (playerCombat.getFireShield()!=null){
            playerCombat.getFireShield().draw(batch);
        }
        if (playerCombat.getFireBreath()!=null){
            playerCombat.getFireBreath().draw(batch);
        }
        if (playerCombat.getShieldBlockEffect()!=null){
            playerCombat.getShieldBlockEffect().draw(batch);
        }
        //check if there are any fireballs that should be deleted from our list
        FireBall fireBallToDelete = null;
        for (FireBall fireBall : fireBallsToDelete){
            for (FireBall fireBall1 : playerCombat.getFireBalls()){
                if (fireBall.getName().equals(fireBall1.getName())){
                    fireBallToDelete=fireBall1;
                }
            }
        }
        if (fireBallToDelete!=null) {
            playerCombat.getFireBalls().remove(fireBallToDelete);
            fireBallsToDelete.clear();
        }
        //check if there are any orbs that should be deleted from our list
        Orb orbToDelete = null;
        for (Orb orb : orbsToDelete){
            for (Orb orb1 : playerCombat.getOrbs()){
                if (orb.getName().equals(orb1.getName())){
                    orbToDelete=orb1;
                }
            }
        }
        if (orbToDelete!=null) {
            playerCombat.getOrbs().remove(orbToDelete);
            fireBallsToDelete.clear();
        }
        //draw death sprite if dead
        if (isDead && deathAnimationEffect!=null){
            deathAnimationEffect.draw(batch);
            playerBody.setLinearVelocity(0,0);
        }
    }

    public void deleteAFireBall(FireBall fireBall){
        fireBallsToDelete.add(fireBall);
    }
    public void deleteAnOrb(Orb orb){
        orbsToDelete.add(orb);
    }

    public PlayerGraphics getPlayerGraphics() {
        return playerGraphics;
    }

    public PlayerPhysics getPlayerPhysics() {
        return playerPhysics;
    }

    public PlayerMovement getPlayerMovement() {
        return playerMovement;
    }

    public PlayerCombat getPlayerCombat(){
        return playerCombat;
    }

    public PlayerUI getPlayerUI(){
        return playerUI;
    }

    public void setDeathAnimationEffectToNull(){
        deathAnimationEffect=null;
    }

    //this is called from player combat take damage when health is<=0
    public void death(){
        isDead=true;
        deathAnimationEffect = new DeathAnimationEffect(playerBody.getPosition().x, playerBody.getPosition().y, getPlayerGraphics().getSpritePlayer().isFlipX());
        //this actions make collision detection very sad
        //darkknight.player.getPlayerPhysics().getPlayerBody().setTransform(playerBody.getPosition().x,200,0);
        playerCombat.setImmuneToDamage(true);
    }

    //this is called from death text effect when it is done animating
    public void respawn(){
        getPlayerUI().setDeathTextEffectToNull();
        darkknight.world=new World(new Vector2(0, -10), true);
        darkknight.level1=new Level1();
        darkknight.player=new Player();
        CollisionDetector.currentContacts.clear();
        CollisionDetector.currentRightEnemies.clear();
        CollisionDetector.currentLeftEnemies.clear();
        isDead=false;
    }
}

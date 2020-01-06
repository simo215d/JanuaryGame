package com.mygdx.game.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.player.PlayerEffects.FireBall;
import com.mygdx.game.player.PlayerEffects.Orb;

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

    public Player(){
        playerPhysics = new PlayerPhysics();
        playerBody=playerPhysics.getPlayerBody();
        playerGraphics = new PlayerGraphics();
        playerMovement = new PlayerMovement(playerBody);
        playerCombat = new PlayerCombat();
        playerUI = new PlayerUI();
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

    public void draw(Batch batch, OrthographicCamera camera){
        playerGraphics.draw(batch);
        playerUI.draw(batch, camera, playerCombat.getHealth(), playerCombat.getMaxHealth());
        for (FireBall fireBall : getPlayerCombat().getFireBalls()){
            fireBall.draw(batch);
        }
        if (playerCombat.getCurrentMeteor()!=null) {
            playerCombat.getCurrentMeteor().draw(batch);
        }
        for (Orb orb : getPlayerCombat().getOrbs()){
            orb.draw(batch);
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
    }

    public void deleteAFireBall(FireBall fireBall){
        fireBallsToDelete.add(fireBall);
    }
    public void deleteAnOrb(Orb orb){
        orbsToDelete.add(orb);
    }
}

package com.mygdx.game.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.player.PlayerEffects.FireBall;

import java.util.ArrayList;

public class Player {
    private PlayerGraphics playerGraphics;
    private PlayerPhysics playerPhysics;
    private PlayerMovement playerMovement;
    private PlayerUI playerUI;
    private PlayerCombat playerCombat;
    private Body playerBody;
    public static ArrayList<FireBall> fireBallsToDelete = new ArrayList<>();

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
    }

    public void deleteAFireBall(FireBall fireBall){
        fireBallsToDelete.add(fireBall);
    }
}

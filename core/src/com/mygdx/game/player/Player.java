package com.mygdx.game.player;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {
    private PlayerGraphics playerGraphics;
    private PlayerPhysics playerPhysics;
    private PlayerMovement playerMovement;
    private PlayerUI playerUI;
    private PlayerCombat playerCombat;
    private Body playerBody;

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
    }
}

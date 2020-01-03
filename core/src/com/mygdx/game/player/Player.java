package com.mygdx.game.player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {
    private PlayerGraphics playerGraphics;
    private PlayerPhysics playerPhysics;
    private PlayerActions playerActions;
    private PlayerUI playerUI;
    private Body playerBody;

    public Player(){
        playerPhysics = new PlayerPhysics();
        playerBody=playerPhysics.getPlayerBody();
        playerGraphics = new PlayerGraphics();
        playerActions = new PlayerActions(playerBody);
        playerUI = new PlayerUI();
    }

    public PlayerGraphics getPlayerGraphics() {
        return playerGraphics;
    }

    public PlayerPhysics getPlayerPhysics() {
        return playerPhysics;
    }

    public PlayerActions getPlayerActions() {
        return playerActions;
    }

    public void draw(Batch batch, OrthographicCamera camera){
        playerGraphics.draw(batch);
        playerUI.draw(batch, camera);
    }
}

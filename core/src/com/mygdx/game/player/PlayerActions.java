package com.mygdx.game.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.darkknight;
import com.mygdx.game.world.CollisionDetector;

public class PlayerActions {
    private boolean isAirBorne = true;
    private boolean pushingRight = false;
    private boolean pushingLeft = false;
    private boolean attacking1 = false;
    private Body playerBody;

    public PlayerActions(){
        playerBody = darkknight.playerPhysics.getPlayerBody();
    }

    private boolean isAirBorne() { return isAirBorne; }
    public void setAirBorne(boolean airBorne) { isAirBorne = airBorne; }
    public boolean getIsAirBorne(){
        return isAirBorne;
    }

    public void jump(){
        //only jump if we are not moving in y
        //the comment above is a bad way of doing it because what if we are running uphill rather check if our feet are on the ground
        if (/*playerBody.getLinearVelocity().y==0*/!getIsAirBorne()) {
            //the amount of impulse required to make the player jump at a desired height depends on the density and size of the players body
            //playerBody.applyLinearImpulse(0,550,playerBody.getPosition().x,playerBody.getPosition().y,true);
            //a direct change in velocity is however not dependent on density and size of the body
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x,12);
            //setAirBorne(true);
        }
    }

    public void moveLeft(){
        darkknight.playerPhysics.getPlayerBody().setLinearVelocity(-18,playerBody.getLinearVelocity().y);
        darkknight.playerGraphics.getSpritePlayer().setFlip(true, false);
        if (!isAirBorne() && !getPushingLeft() && !darkknight.playerGraphics.getAnimationState().equals("running")){
            //darkknight.playerGraphics.getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight3.gif"))));
            darkknight.playerGraphics.setAnimationState("running");
        }
    }

    public void moveRight(){
        darkknight.playerPhysics.getPlayerBody().setLinearVelocity(18,playerBody.getLinearVelocity().y);
        darkknight.playerGraphics.getSpritePlayer().setFlip(false, false);
        if (!isAirBorne() && !pushingRight && !darkknight.playerGraphics.getAnimationState().equals("running")){
            //darkknight.playerGraphics.getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight3.gif"))));
            darkknight.playerGraphics.setAnimationState("running");
        }
    }

    public void moveStop(){
        darkknight.playerPhysics.getPlayerBody().setLinearVelocity(0,playerBody.getLinearVelocity().y);
    }

    public void shroomBounce(String shroomData){
        playerBody.setLinearVelocity(playerBody.getLinearVelocity().x,20);
        darkknight.level1.level1MapGraphics.shroomBounce(shroomData);
        setAirBorne(true);
        darkknight.playerGraphics.setAnimationState("jumping");
    }

    public void attack1(){
        attacking1=true;
        //if the player jumps and immediately presses 1, then his foot is still grounded.
        // he then starts attack, but that is overwritten when the player exits the walkable collider because he is on his way upwards.
        // therefore we need to make sure that the player is static when he attacks
        darkknight.playerPhysics.getPlayerBody().setLinearVelocity(0,0);
        darkknight.playerGraphics.setAnimationState("attacking1");
        //handling the actual attack logic
        //if sprite facing left
        if (darkknight.playerGraphics.getSpritePlayer().isFlipX()){
            if (CollisionDetector.currentLeftEnemies.size()>0){
                for (String string : CollisionDetector.currentLeftEnemies){
                    darkknight.level1.level1Enemies.attack1(string);
                }
            }
        }
        //if sprite facing right
        if (!darkknight.playerGraphics.getSpritePlayer().isFlipX()){
            if (CollisionDetector.currentRightEnemies.size()>0){
                for (String string : CollisionDetector.currentRightEnemies){
                    darkknight.level1.level1Enemies.attack1(string);
                }
            }
        }
    }

    public void setPushingRight(boolean push){
        pushingRight=push;
    }

    public boolean getPushingRight(){
        return pushingRight;
    }

    public void setPushingLeft(boolean push){
        pushingLeft=push;
    }

    public boolean getPushingLeft(){
        return pushingLeft;
    }

    public void setAttacking1(boolean bool){
        attacking1=bool;
    }

    public boolean isAttacking1(){
        return attacking1;
    }
}

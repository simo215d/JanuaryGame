package com.mygdx.game.hmi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.darkknight;
import com.mygdx.game.world.CollisionDetector;

public class InputHandler {
    public void handleInput(OrthographicCamera cam, Sprite sprite3){
        {
            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                cam.zoom += 0.02;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                cam.zoom -= 0.02;
            }
            //idle
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && !Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerMovement().getIsAirBorne() && !darkknight.player.getPlayerMovement().getPushingRight() && !darkknight.player.getPlayerMovement().getPushingLeft() && !darkknight.player.getPlayerMovement().isAttacking1()){
                darkknight.player.getPlayerGraphics().getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight1.png"))));
                darkknight.player.getPlayerGraphics().setAnimationState("idle");
                darkknight.player.getPlayerMovement().moveStop();
            }
            //run left
            if (Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerMovement().getPushingLeft() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                //cam.translate(-0.5f, 0, 0);
                darkknight.player.getPlayerMovement().moveLeft();
            }
            //run right
            if (Gdx.input.isKeyPressed(Input.Keys.E) && !darkknight.player.getPlayerMovement().getPushingRight() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                //cam.translate(0.5f, 0, 0);
                darkknight.player.getPlayerMovement().moveRight();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Q) && darkknight.player.getPlayerMovement().getPushingLeft() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                if (!darkknight.player.getPlayerMovement().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight5.png"))));
                    sprite3.setFlip(true,false);
                    darkknight.player.getPlayerMovement().moveLeft();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E) && darkknight.player.getPlayerMovement().getPushingRight() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                if (!darkknight.player.getPlayerMovement().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight5.png"))));
                    sprite3.setFlip(false, false);
                    darkknight.player.getPlayerMovement().moveRight();
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.Q) && darkknight.player.getPlayerMovement().getPushingLeft() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                if (!darkknight.player.getPlayerMovement().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight4.png"))));
                    sprite3.setFlip(true,false);
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && darkknight.player.getPlayerMovement().getPushingRight() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                if (!darkknight.player.getPlayerMovement().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight4.png"))));
                    sprite3.setFlip(false, false);
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && !Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerMovement().getIsAirBorne()) {
                darkknight.player.getPlayerMovement().moveStop();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W) && !darkknight.player.getPlayerMovement().isAttacking1()) {
                darkknight.player.getPlayerMovement().jump();
            }

            //TODO RESPECT THE COOLDOWN/MANA
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attack1();
            }

            //fireball
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2) && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attack2();
            }

            //meteor
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3) && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attack3();
            }

            //orbs
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
                darkknight.player.getPlayerCombat().attack4();
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)){
                if (darkknight.isWorldStopped) {
                    System.out.println("game un-paused");
                    darkknight.isWorldStopped = false;
                }
                else {
                    System.out.println("game paused");
                    darkknight.isWorldStopped=true;
                }
            }

            cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 65/cam.viewportWidth);

            float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
            float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

            cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
            cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
        }
    }
}

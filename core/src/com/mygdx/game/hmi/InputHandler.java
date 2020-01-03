package com.mygdx.game.hmi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerPhysics;
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
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && !Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerActions().getIsAirBorne() && !darkknight.player.getPlayerActions().getPushingRight() && !darkknight.player.getPlayerActions().getPushingLeft() && !darkknight.player.getPlayerActions().isAttacking1()){
                darkknight.player.getPlayerGraphics().getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight1.png"))));
                darkknight.player.getPlayerGraphics().setAnimationState("idle");
                darkknight.player.getPlayerActions().moveStop();
            }
            //run left
            if (Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerActions().getPushingLeft() && !darkknight.player.getPlayerActions().isAttacking1()) {
                //cam.translate(-0.5f, 0, 0);
                darkknight.player.getPlayerActions().moveLeft();
            }
            //run right
            if (Gdx.input.isKeyPressed(Input.Keys.E) && !darkknight.player.getPlayerActions().getPushingRight() && !darkknight.player.getPlayerActions().isAttacking1()) {
                //cam.translate(0.5f, 0, 0);
                darkknight.player.getPlayerActions().moveRight();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Q) && darkknight.player.getPlayerActions().getPushingLeft() && !darkknight.player.getPlayerActions().isAttacking1()) {
                if (!darkknight.player.getPlayerActions().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight5.png"))));
                    sprite3.setFlip(true,false);
                    darkknight.player.getPlayerActions().moveLeft();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E) && darkknight.player.getPlayerActions().getPushingRight() && !darkknight.player.getPlayerActions().isAttacking1()) {
                if (!darkknight.player.getPlayerActions().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight5.png"))));
                    sprite3.setFlip(false, false);
                    darkknight.player.getPlayerActions().moveRight();
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.Q) && darkknight.player.getPlayerActions().getPushingLeft() && !darkknight.player.getPlayerActions().isAttacking1()) {
                if (!darkknight.player.getPlayerActions().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight4.png"))));
                    sprite3.setFlip(true,false);
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && darkknight.player.getPlayerActions().getPushingRight() && !darkknight.player.getPlayerActions().isAttacking1()) {
                if (!darkknight.player.getPlayerActions().getIsAirBorne()) {
                    sprite3.setTexture(new Texture((Gdx.files.internal("knight4.png"))));
                    sprite3.setFlip(false, false);
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && !Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerActions().getIsAirBorne()) {
                darkknight.player.getPlayerActions().moveStop();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W) && !darkknight.player.getPlayerActions().isAttacking1()) {
                darkknight.player.getPlayerActions().jump();
            }

            //TODO RESPECT THE COOLDOWN/DRIP FAM
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && !darkknight.player.getPlayerActions().getIsAirBorne()  && !darkknight.player.getPlayerActions().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerActions().attack1();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
                System.out.println("animation state: "+darkknight.player.getPlayerGraphics().getAnimationState()+" airborne= "+darkknight.player.getPlayerActions().getIsAirBorne());
            }

            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)){
                System.out.println("enemies to the right: ");
                for (String string : CollisionDetector.currentRightEnemies){
                    System.out.println(string);
                }
                System.out.println("enemies to the left: ");
                for (String string : CollisionDetector.currentLeftEnemies){
                    System.out.println(string);
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

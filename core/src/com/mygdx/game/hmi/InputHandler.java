package com.mygdx.game.hmi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.darkknight;

public class InputHandler {
    public void handleInput(OrthographicCamera cam, Sprite sprite3){
        {
            if (Gdx.input.isKeyPressed(Input.Keys.X)) {
                //cam.zoom += 0.02;
                System.out.println("to zoom out, pls edit the input event then");
            }
            if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
                //cam.zoom -= 0.02;
                System.out.println("to zoom in, pls edit the input event then");
            }
            //idle
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && !Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerMovement().getIsAirBorne() && !darkknight.player.getPlayerMovement().getPushingRight() && !darkknight.player.getPlayerMovement().getPushingLeft() && !darkknight.player.getPlayerMovement().isAttacking1()){
                //darkknight.player.getPlayerGraphics().getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight1.png"))));
                if (!darkknight.player.getPlayerGraphics().getAnimationState().equals("idle"))
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
                    if (!darkknight.player.getPlayerGraphics().getAnimationState().equals("isPushing"))
                    darkknight.player.getPlayerGraphics().setAnimationState("isPushing");
                    sprite3.setFlip(true,false);
                    darkknight.player.getPlayerMovement().moveLeft();
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.E) && darkknight.player.getPlayerMovement().getPushingRight() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                if (!darkknight.player.getPlayerMovement().getIsAirBorne()) {
                    if (!darkknight.player.getPlayerGraphics().getAnimationState().equals("isPushing"))
                    darkknight.player.getPlayerGraphics().setAnimationState("isPushing");
                    sprite3.setFlip(false, false);
                    darkknight.player.getPlayerMovement().moveRight();
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.Q) && darkknight.player.getPlayerMovement().getPushingLeft() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                if (!darkknight.player.getPlayerMovement().getIsAirBorne()) {
                    if (!darkknight.player.getPlayerGraphics().getAnimationState().equals("canPush"))
                        darkknight.player.getPlayerGraphics().setAnimationState("canPush");
                    sprite3.setFlip(true,false);
                }
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.E) && darkknight.player.getPlayerMovement().getPushingRight() && !darkknight.player.getPlayerMovement().isAttacking1()) {
                if (!darkknight.player.getPlayerMovement().getIsAirBorne()) {
                    if (!darkknight.player.getPlayerGraphics().getAnimationState().equals("canPush"))
                        darkknight.player.getPlayerGraphics().setAnimationState("canPush");
                    sprite3.setFlip(false, false);
                }
            }

            if (!Gdx.input.isKeyPressed(Input.Keys.E) && !Gdx.input.isKeyPressed(Input.Keys.Q) && !darkknight.player.getPlayerMovement().getIsAirBorne() && !darkknight.player.getPlayerCombat().isShielding()) {
                darkknight.player.getPlayerMovement().moveStop();
            }

            if (Gdx.input.isKeyPressed(Input.Keys.W) && !darkknight.player.getPlayerMovement().isAttacking1()) {
                darkknight.player.getPlayerMovement().jump();
            }

            //sword
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1) && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attackSword();
            }

            //fireball
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_3) && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attackFireball();
            }

            /*
            //meteor
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_5) && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attack3();
            }
             */

            //orbs
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)){
                darkknight.player.getPlayerCombat().attackOrbs();
            }

            //shield
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)  && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attackShield();
            }

            if (darkknight.player.getPlayerCombat().isShielding()){
                if (!Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
                    darkknight.player.getPlayerCombat().setShielding(false);
                    darkknight.player.getPlayerMovement().setAttacking1(false);
                    darkknight.player.getPlayerGraphics().getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight1.png"))));
                    darkknight.player.getPlayerGraphics().setAnimationState("");
                    darkknight.player.getPlayerCombat().setImmuneToDamage(false);
                }
            }

            //fire breath
            /*
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_6) && !darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                darkknight.player.getPlayerCombat().attack6();
            }
             */

            //only use this button if you know what it does...
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)){
                if (darkknight.isWorldStopped) {
                    System.out.println("game un-paused");
                    darkknight.isWorldStopped = false;
                }
                else {
                    System.out.println("game paused");
                    darkknight.isWorldStopped=true;
                }
            }


            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
                System.out.println("ani state: "+darkknight.player.getPlayerGraphics().getAnimationState());
            }

            /*this is no longer needed
            cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 65/cam.viewportWidth);
            float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
            float effectiveViewportHeight = cam.viewportHeight * cam.zoom;
            cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
            cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
            */
            int distance = (int) (darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x-cam.position.x);
            if (distance<2 && distance>-2){
                cam.translate(0,0);
            }
            if (distance>=2 && distance<6){
                cam.translate(0.1f,0);
            }
            if (distance>=6 && distance<10){
                cam.translate(0.2f,0);
            }
            if (distance>=10){
                cam.translate(0.4f,0);
            }
            if (distance<=-2 && distance>-6){
                cam.translate(-0.1f,0);
            }
            if (distance<=-6 && distance>-10){
                cam.translate(-0.2f,0);
            }
            if (distance<=-10){
                cam.translate(-0.4f,0);
            }
        }
    }
}

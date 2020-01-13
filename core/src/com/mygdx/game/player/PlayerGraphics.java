package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class PlayerGraphics {
    //TODO combatAnimation state as well, so that we dont overwrite our
    // mobility animation state, so we can remember what to swap back to
    //TODO ABILITY COOLDOWN!? GLOBAL COOLDOWN?
    private String animationState = "";
    //sprite
    private Texture texturePlayer;
    private Sprite spritePlayer;
    //animation
    private ArrayList<PlayerAnimation> animations = new ArrayList<>();
    private PlayerAnimation jumpAnimation = new PlayerAnimation(3,1,"knightJumpSheet.png",0.1f, false);
    private PlayerAnimation idleAnimation = new PlayerAnimation(2,1,"knightIdleSheet.png",1f, true);
    private PlayerAnimation canPushAnimation = new PlayerAnimation(2,1,"knightCanPushSheet.png",0.5f, true);
    private PlayerAnimation isPushingAnimation = new PlayerAnimation(4,1,"knightIsPushingSheet.png",0.15f, true);
    private PlayerAnimation runAnimation = new PlayerAnimation(5,1,"knightRunSheet.png",0.1f, true);
    private PlayerAnimation attackSwordAnimation = new PlayerAnimation(4,1,"knightAttack1Sheet.png",0.075f, false);
    private PlayerAnimation attackFireballAnimation = new PlayerAnimation(3,1,"knightAttack1FireSheet.png",0.1f, false);
    private PlayerAnimation attackMeteorAnimation = new PlayerAnimation(7,1,"knightAttackMeteorSheet.png",0.1f, false);
    private PlayerAnimation attackFireBreathAnimation = new PlayerAnimation(7,1,"knightFireBreathSheet.png",0.1f, false);
    private PlayerAnimation attackShieldAnimation = new PlayerAnimation(3,1,"knightShieldSheet.png",0.05f, false);

    public PlayerGraphics(){
        //sprite
        texturePlayer = new Texture(Gdx.files.internal("knight1.png"));
        spritePlayer = new Sprite(texturePlayer,0,0,32,32);
        spritePlayer.setPosition(0,5f);
        spritePlayer.setSize(16,16);
        //animation
        animations.add(jumpAnimation);
        animations.add(runAnimation);
        animations.add(attackSwordAnimation);
        animations.add(attackShieldAnimation);
        animations.add(attackFireballAnimation);
        animations.add(attackMeteorAnimation);
        animations.add(attackFireBreathAnimation);
        animations.add(idleAnimation);
        animations.add(canPushAnimation);
        animations.add(isPushingAnimation);
    }

    public Sprite getSpritePlayer(){
        return spritePlayer;
    }

    public void setAnimationState(String state){
        //in some weird interaction with bodies that are walkable we might accidentaly be able to hit while we are sliding down
        //which means hitting is illegal //TODO FIX FOOT SENSOR BECAUSE OF THIS?
        if (!state.equals("attackingSword") && !state.equals("attackingFireball") && !state.equals("attackingMeteor") && !state.equals("attackingFireBreath") && !state.equals("attackingShield")){
            darkknight.player.getPlayerMovement().setAttacking1(false);
        }
        animationState=state;
        for (PlayerAnimation animation : animations){
            animation.resetStateTime();
        }
    }

    public String getAnimationState(){
        return animationState;
    }

    public void draw(Batch batch){
        switch (animationState){
            case "jumping": jumpAnimation.animate(spritePlayer,batch); break;
            case "running": runAnimation.animate(spritePlayer,batch); break;
            case "attackingSword": attackSwordAnimation.animate(spritePlayer,batch); break;
            case "attackingFireball": attackFireballAnimation.animate(spritePlayer,batch); break;
            case "attackingMeteor": attackMeteorAnimation.animate(spritePlayer,batch); break;
            case "attackingFireBreath": attackFireBreathAnimation.animate(spritePlayer,batch); break;
            case "attackingShield": attackShieldAnimation.animate(spritePlayer,batch); break;
            case "idle": idleAnimation.animate(spritePlayer,batch); break;
            case "canPush": canPushAnimation.animate(spritePlayer,batch); break;
            case "isPushing": isPushingAnimation.animate(spritePlayer,batch); break;
            default: spritePlayer.draw(batch); break;
        }
    }
}

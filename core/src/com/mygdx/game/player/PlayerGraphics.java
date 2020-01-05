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
    private PlayerAnimation runAnimation = new PlayerAnimation(5,1,"knightRunSheet.png",0.1f, true);
    private PlayerAnimation attack1Animation = new PlayerAnimation(4,1,"knightAttack1Sheet.png",0.075f, false);
    private PlayerAnimation attack2Animation = new PlayerAnimation(3,1,"knightAttack1FireSheet.png",0.1f, false);
    private PlayerAnimation attack3Animation = new PlayerAnimation(7,1,"knightAttackMeteorSheet.png",0.1f, false);

    public PlayerGraphics(){
        //sprite
        texturePlayer = new Texture(Gdx.files.internal("knight1.png"));
        spritePlayer = new Sprite(texturePlayer,0,0,32,32);
        spritePlayer.setPosition(0,5f);
        spritePlayer.setSize(16,16);
        //animation
        animations.add(jumpAnimation);
        animations.add(runAnimation);
        animations.add(attack1Animation);
        animations.add(attack2Animation);
        animations.add(attack3Animation);
    }

    public Sprite getSpritePlayer(){
        return spritePlayer;
    }

    public void setAnimationState(String state){
        //in some weird interaction with bodies that are walkable we might accidentaly be able to hit while we are sliding down
        //which means hitting is illegal //TODO FIX FOOT SENSOR BECAUSE OF THIS?
        if (!state.equals("attacking1") && !state.equals("attacking2") && !state.equals("attacking3")){
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
            case "attacking1": attack1Animation.animate(spritePlayer,batch); break;
            case "attacking2": attack2Animation.animate(spritePlayer,batch); break;
            case "attacking3": attack3Animation.animate(spritePlayer,batch); break;
            default: spritePlayer.draw(batch); break;
        }
    }
}

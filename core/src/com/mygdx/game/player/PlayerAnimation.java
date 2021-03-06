package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.darkknight;

public class PlayerAnimation {
    private int FRAME_COLS, FRAME_ROWS;
    private Animation<TextureRegion> animation; // Must declare frame type (TextureRegion)
    private Texture animationSheet;
    private float stateTime; // A variable for tracking elapsed time for the animation
    private boolean animationLooping;
    private float frameDuration;

    public PlayerAnimation(int sheetColumns, int sheetRows, String url, float frameDuration, boolean animationLooping){
        FRAME_COLS = sheetColumns;
        FRAME_ROWS = sheetRows;
        this.frameDuration=frameDuration;
        this.animationLooping=animationLooping;
        animationSheet = new Texture(Gdx.files.internal(url));
        TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth() / FRAME_COLS, animationSheet.getHeight() / FRAME_ROWS);
        TextureRegion[] animationFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                animationFrames[index++] = tmp[i][j];
            }
        }
        animation = new Animation<TextureRegion>(frameDuration, animationFrames);
        stateTime = 0f;
    }

    public void animate(Sprite spritePlayer, Batch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, animationLooping);
        if (spritePlayer.isFlipX()){
            currentFrame.flip(!currentFrame.isFlipX(), false);
        } else {
            currentFrame.flip(currentFrame.isFlipX(), false);
        }
        if (Player.renderRed){
            batch.setColor(Color.RED);
            batch.draw(currentFrame, spritePlayer.getX(), spritePlayer.getY(),16,16);
            batch.setColor(Color.WHITE);
        } else batch.draw(currentFrame, spritePlayer.getX(), spritePlayer.getY(),16,16);
        //event upon animation ending
        if (stateTime>=frameDuration*FRAME_ROWS*FRAME_COLS){
            //shielding should'nt end when the animation is over. only when we aren't pressing the designated button
            //stop the animation
            if (darkknight.player.getPlayerMovement().isAttacking1() && !animationSheet.toString().equals("knightShieldSheet.png")){
                darkknight.player.getPlayerMovement().setAttacking1(false);
                darkknight.player.getPlayerGraphics().getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight1.png"))));
                darkknight.player.getPlayerGraphics().setAnimationState("");
            }
        }
    }

    public void resetStateTime(){
        stateTime=0;
    }

    public Texture getAnimationSheet(){
        return animationSheet;
    }
}

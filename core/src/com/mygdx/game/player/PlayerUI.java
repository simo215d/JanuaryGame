package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class PlayerUI {
    ArrayList<Sprite> spellIcons = new ArrayList<>();
    //spell frame
    private Texture spellFrameTexture;
    private Sprite spellFrameSprite;
    //green health bar
    private Texture greenHealthBarTexture;
    private Sprite greenHealthBarSprite;
    //spell icon 1
    private Texture spellIcon1Texture;
    private Sprite spellIcon1Sprite;
    //spell icon 2
    private Texture spellIcon2Texture;
    private Sprite spellIcon2Sprite;
    //spell icon 3
    private Texture spellIcon3Texture;
    private Sprite spellIcon3Sprite;
    //spell icon 4
    private Texture spellIcon4Texture;
    private Sprite spellIcon4Sprite;

    public PlayerUI(){
        //spell frame
        spellFrameTexture = new Texture(Gdx.files.internal("uiPlayerSpellsFrame.png"));
        spellFrameSprite = new Sprite(spellFrameTexture,0,0,43,13);
        spellFrameSprite.setSize(43/2f,13/2f);
        //spell frame
        greenHealthBarTexture = new Texture(Gdx.files.internal("greenHealthBar1.png"));
        greenHealthBarSprite = new Sprite(greenHealthBarTexture,0,0,43,13);
        greenHealthBarSprite.setSize(20.5f,1f);
        //spell icon 1
        spellIcon1Texture = new Texture(Gdx.files.internal("uiPlayerAttack1Icon.png"));
        spellIcon1Sprite = new Sprite(spellIcon1Texture,0,0,10,10);
        spellIcon1Sprite.setSize(5,5);
        //spell icon 2
        spellIcon2Texture = new Texture(Gdx.files.internal("uiPlayerAttack2Icon.png"));
        spellIcon2Sprite = new Sprite(spellIcon2Texture,0,0,10,10);
        spellIcon2Sprite.setSize(5,5);
        //spell icon 3
        spellIcon3Texture = new Texture(Gdx.files.internal("uiUnknownIcon.png"));
        spellIcon3Sprite = new Sprite(spellIcon3Texture,0,0,10,10);
        spellIcon3Sprite.setSize(5,5);
        //spell icon 4
        spellIcon4Texture = new Texture(Gdx.files.internal("uiUnknownIcon.png"));
        spellIcon4Sprite = new Sprite(spellIcon4Texture,0,0,10,10);
        spellIcon4Sprite.setSize(5,5);
        //insert into spell icon list
        spellIcons.add(spellIcon1Sprite);
        spellIcons.add(spellIcon2Sprite);
        spellIcons.add(spellIcon3Sprite);
        spellIcons.add(spellIcon4Sprite);
    }

    public void draw(Batch batch, OrthographicCamera camera, int health, int maxHealth){
        spellFrameSprite.setPosition(camera.position.x-spellFrameSprite.getWidth()/2,camera.position.y-24.5f);
        spellFrameSprite.draw(batch);
        //render spell icons so they are within the frame
        for (int i = 0; i < spellIcons.size(); i++) {
            Sprite sprite = spellIcons.get(i);
            sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
            sprite.draw(batch);
        }
        //render player health bar
        greenHealthBarSprite.setSize(20.5f*((float) health/(float) maxHealth),1);
        greenHealthBarSprite.setPosition(spellFrameSprite.getX()+0.5f,spellFrameSprite.getY()+5);
        greenHealthBarSprite.draw(batch);
    }
}

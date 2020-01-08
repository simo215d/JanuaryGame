package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class PlayerUI {
    ArrayList<Sprite> spellIcons = new ArrayList<>();
    //spell frame
    private Texture spellFrameTexture;
    private Sprite spellFrameSprite;
    //green health bar
    private Texture greenHealthBarTexture;
    private Sprite greenHealthBarSprite;
    //mana bar
    private Texture manaBarTexture;
    private Sprite manaBarSprite;
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
    //spell icon 5
    private Texture spellIcon5Texture;
    private Sprite spellIcon5Sprite;
    //spell icon 6
    private Texture spellIcon6Texture;
    private Sprite spellIcon6Sprite;

    public PlayerUI(){
        //spell frame
        spellFrameTexture = new Texture(Gdx.files.internal("uiPlayerSpellsFrame.png"));
        spellFrameSprite = new Sprite(spellFrameTexture,0,0,65,16);
        spellFrameSprite.setSize(65/2f,16/2f);
        //green health bar
        greenHealthBarTexture = new Texture(Gdx.files.internal("greenHealthBar1.png"));
        greenHealthBarSprite = new Sprite(greenHealthBarTexture,0,0,43,2);
        greenHealthBarSprite.setSize(20.5f,1f);
        //green health bar
        manaBarTexture = new Texture(Gdx.files.internal("uiPlayerManaBar.png"));
        manaBarSprite = new Sprite(manaBarTexture,0,0,43,2);
        manaBarSprite.setSize(20.5f,1f);
        //spell icon 1
        spellIcon1Texture = new Texture(Gdx.files.internal("uiPlayerAttack1Icon.png"));
        spellIcon1Sprite = new Sprite(spellIcon1Texture,0,0,10,10);
        spellIcon1Sprite.setSize(5,5);
        //spell icon 2
        spellIcon2Texture = new Texture(Gdx.files.internal("uiPlayerAttack2Icon.png"));
        spellIcon2Sprite = new Sprite(spellIcon2Texture,0,0,10,10);
        spellIcon2Sprite.setSize(5,5);
        //spell icon 3
        spellIcon3Texture = new Texture(Gdx.files.internal("uiPlayerAttack3Icon.png"));
        spellIcon3Sprite = new Sprite(spellIcon3Texture,0,0,10,10);
        spellIcon3Sprite.setSize(5,5);
        //spell icon 4
        spellIcon4Texture = new Texture(Gdx.files.internal("uiPlayerAttack4Icon.png"));
        spellIcon4Sprite = new Sprite(spellIcon4Texture,0,0,10,10);
        spellIcon4Sprite.setSize(5,5);
        //spell icon 5
        spellIcon5Texture = new Texture(Gdx.files.internal("uiPlayerAttack5Icon.png"));
        spellIcon5Sprite = new Sprite(spellIcon5Texture,0,0,10,10);
        spellIcon5Sprite.setSize(5,5);
        //spell icon 6
        spellIcon6Texture = new Texture(Gdx.files.internal("uiPlayerAttack6Icon.png"));
        spellIcon6Sprite = new Sprite(spellIcon6Texture,0,0,10,10);
        spellIcon6Sprite.setSize(5,5);
        //insert into spell icon list
        spellIcons.add(spellIcon1Sprite);
        spellIcons.add(spellIcon2Sprite);
        spellIcons.add(spellIcon3Sprite);
        spellIcons.add(spellIcon4Sprite);
        spellIcons.add(spellIcon5Sprite);
        spellIcons.add(spellIcon6Sprite);
    }

    public void draw(Batch batch, OrthographicCamera camera, int health, int maxHealth, int mana, int maxMana){
        spellFrameSprite.setPosition(camera.position.x-spellFrameSprite.getWidth()/2,camera.position.y-24.6f);
        spellFrameSprite.draw(batch);
        //render player health bar
        greenHealthBarSprite.setSize(31.5f*((float) health/(float) maxHealth),1);
        greenHealthBarSprite.setPosition(spellFrameSprite.getX()+0.5f,spellFrameSprite.getY()+6.5f);
        greenHealthBarSprite.draw(batch);
        //render player mana bar
        manaBarSprite.setSize(31.5f*((float) mana/(float) maxMana),1);
        manaBarSprite.setPosition(spellFrameSprite.getX()+0.5f,spellFrameSprite.getY()+5f);
        manaBarSprite.draw(batch);
        //render spell icons so they are within the frame
        for (int i = 0; i < spellIcons.size(); i++) {
            switch (i){
                case 0: if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                case 1: if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping") && mana>PlayerCombat.attack2Mana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                case 2: if (darkknight.player.getPlayerCombat().getOrbs().size()==0 && mana>PlayerCombat.attack3Mana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                case 3: if (darkknight.player.getPlayerCombat().getFireShield()==null && mana>PlayerCombat.attack4Mana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                case 4: if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping") && darkknight.player.getPlayerCombat().getCurrentMeteor()==null && mana>PlayerCombat.attack5Mana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                case 5: if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping") && mana>PlayerCombat.attack6Mana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
            }
        }
    }
}

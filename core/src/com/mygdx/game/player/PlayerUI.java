package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.darkknight;
import com.mygdx.game.player.PlayerEffects.DeathTextEffect;

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
    private Texture spellIconSwordTexture;
    private Sprite spellIconSwordSprite;
    //spell icon 2
    private Texture spellIconFireballTexture;
    private Sprite spellIconFireballSprite;
    //spell icon 3
    private Texture spellIconOrbsTexture;
    private Sprite spellIconOrbsSprite;
    //spell icon 4
    private Texture spellIconShieldTexture;
    private Sprite spellIconShieldSprite;
    /*
    //spell icon 5
    private Texture spellIconMeteorTexture;
    private Sprite spellIconMeteorSprite;
    //spell icon 6
    private Texture spellIconFireBreathTexture;
    private Sprite spellIconFireBreathSprite;
     */
    //death screen
    private DeathTextEffect deathTextEffect = null;

    public PlayerUI(){
        //spell frame
        spellFrameTexture = new Texture(Gdx.files.internal("uiPlayerSpellsFrame.png"));
        spellFrameSprite = new Sprite(spellFrameTexture,0,0,43,16);
        spellFrameSprite.setSize(43/2f,16/2f);
        //green health bar
        greenHealthBarTexture = new Texture(Gdx.files.internal("greenHealthBar1.png"));
        greenHealthBarSprite = new Sprite(greenHealthBarTexture,0,0,43,2);
        greenHealthBarSprite.setSize(20.5f,1f);
        //green health bar
        manaBarTexture = new Texture(Gdx.files.internal("uiPlayerManaBar.png"));
        manaBarSprite = new Sprite(manaBarTexture,0,0,43,2);
        manaBarSprite.setSize(20.5f,1f);
        //spell icon 1
        spellIconSwordTexture = new Texture(Gdx.files.internal("uiPlayerAttack1Icon.png"));
        spellIconSwordSprite = new Sprite(spellIconSwordTexture,0,0,10,10);
        spellIconSwordSprite.setSize(5,5);
        //spell icon 2
        spellIconFireballTexture = new Texture(Gdx.files.internal("uiPlayerAttack2Icon.png"));
        spellIconFireballSprite = new Sprite(spellIconFireballTexture,0,0,10,10);
        spellIconFireballSprite.setSize(5,5);
        //spell icon 3
        spellIconOrbsTexture = new Texture(Gdx.files.internal("uiPlayerAttack3Icon.png"));
        spellIconOrbsSprite = new Sprite(spellIconOrbsTexture,0,0,10,10);
        spellIconOrbsSprite.setSize(5,5);
        //spell icon 4
        spellIconShieldTexture = new Texture(Gdx.files.internal("uiPlayerAttack4Icon.png"));
        spellIconShieldSprite = new Sprite(spellIconShieldTexture,0,0,10,10);
        spellIconShieldSprite.setSize(5,5);
        /*
        //spell icon 5
        spellIconMeteorTexture = new Texture(Gdx.files.internal("uiPlayerAttack5Icon.png"));
        spellIconMeteorSprite = new Sprite(spellIconMeteorTexture,0,0,10,10);
        spellIconMeteorSprite.setSize(5,5);
        //spell icon 6
        spellIconFireBreathTexture = new Texture(Gdx.files.internal("uiPlayerAttack6Icon.png"));
        spellIconFireBreathSprite = new Sprite(spellIconFireBreathTexture,0,0,10,10);
        spellIconFireBreathSprite.setSize(5,5);
         */
        //insert into spell icon list
        spellIcons.add(spellIconSwordSprite);
        spellIcons.add(spellIconShieldSprite);
        spellIcons.add(spellIconFireballSprite);
        spellIcons.add(spellIconOrbsSprite);
        /*
        spellIcons.add(spellIconMeteorSprite);
        spellIcons.add(spellIconFireBreathSprite);
         */
    }

    public void draw(Batch batch, OrthographicCamera camera, int health, int maxHealth, int mana, int maxMana){
        //spell frame
        spellFrameSprite.setPosition(camera.position.x-spellFrameSprite.getWidth()/2,camera.position.y-24.6f);
        spellFrameSprite.draw(batch);
        //render player health bar
        greenHealthBarSprite.setSize(20.5f*((float) health/(float) maxHealth),1);
        greenHealthBarSprite.setPosition(spellFrameSprite.getX()+0.5f,spellFrameSprite.getY()+6.5f);
        greenHealthBarSprite.draw(batch);
        //render player mana bar
        manaBarSprite.setSize(20.5f*((float) mana/(float) maxMana),1);
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
                case 1:
                    if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping")/*darkknight.player.getPlayerCombat().getFireShield()==null && mana>PlayerCombat.attackShieldMana*/){
                        Sprite sprite = spellIcons.get(i);
                        sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                        sprite.draw(batch);
                } break;
                case 2:
                    if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping") && mana>PlayerCombat.attackFireballMana){
                        Sprite sprite = spellIcons.get(i);
                        sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                        sprite.draw(batch);
                } break;
                case 3: if (darkknight.player.getPlayerCombat().getOrbs().size()==0 && mana>PlayerCombat.attackOrbsMana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                /*
                case 4: if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping") && darkknight.player.getPlayerCombat().getCurrentMeteor()==null && mana>PlayerCombat.attackMeteorMana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                case 5: if (!darkknight.player.getPlayerMovement().getIsAirBorne()  && !darkknight.player.getPlayerMovement().isAttacking1() && !darkknight.player.getPlayerGraphics().getAnimationState().equals("jumping") && mana>PlayerCombat.attackFireBreathMana){
                    Sprite sprite = spellIcons.get(i);
                    sprite.setPosition(spellFrameSprite.getX()+(0+(i*5.5f)),spellFrameSprite.getY());
                    sprite.draw(batch);
                } break;
                 */
            }
        }
        //death effect
        if (deathTextEffect!=null){
            deathTextEffect.draw(batch, camera.position.x,camera.position.y);
        }
    }

    public void setDeathTextEffect(){
        deathTextEffect=null;
        deathTextEffect=new DeathTextEffect();
    }

    public void setDeathTextEffectToNull(){
        deathTextEffect=null;
    }
}

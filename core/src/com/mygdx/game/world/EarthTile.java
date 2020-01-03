package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EarthTile {
    private Texture earthTexture = new Texture(Gdx.files.internal("earth1.png"));
    private Texture earthTexture2 = new Texture(Gdx.files.internal("earth2.png"));
    private Sprite earthSprite;

    public EarthTile(float x, float y, boolean isGrass){
        if (isGrass){
            earthSprite = new Sprite(earthTexture,0,0,32,16);
        } else {
            earthSprite = new Sprite(earthTexture2,0,0,32,16);
        }
        earthSprite.setPosition(x,y);
        earthSprite.setSize(16,8f);
    }

    public Sprite getSprite(){
        return earthSprite;
    }
}

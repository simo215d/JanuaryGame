package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EarthTile {
    private Texture earthTexture1 = new Texture(Gdx.files.internal("earth1.png"));
    private Texture earthTexture2 = new Texture(Gdx.files.internal("earth2.png"));
    private Texture earthTexture3 = new Texture(Gdx.files.internal("earth3.png"));
    private Texture earthTexture4 = new Texture(Gdx.files.internal("earth4.png"));
    private Sprite earthSprite;

    public EarthTile(float x, float y, int grassType){
        switch (grassType){
            case 1: earthSprite = new Sprite(earthTexture1,0,0,32,16); break;
            case 2: earthSprite = new Sprite(earthTexture2,0,0,32,16); break;
            case 3: earthSprite = new Sprite(earthTexture3,0,0,32,16); break;
            case 4: earthSprite = new Sprite(earthTexture4,0,0,32,16); break;
        }
        earthSprite.setPosition(x,y);
        earthSprite.setSize(16,8f);
    }

    public Sprite getSprite(){
        return earthSprite;
    }
}

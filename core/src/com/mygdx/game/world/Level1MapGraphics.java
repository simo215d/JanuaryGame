package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class Level1MapGraphics {
    private ArrayList<Sprite> earthSprites = new ArrayList<>();
    private ArrayList<Sprite> bushSprites = new ArrayList<>();
    private ArrayList<Sprite> treeSprites = new ArrayList<>();
    private ArrayList<Sprite> shroomSprites = new ArrayList<>();
    private ArrayList<Sprite> joyers = new ArrayList<>();
    private Texture bushTexture = new Texture(Gdx.files.internal("bush1.png"));
    private Texture treeTexture = new Texture(Gdx.files.internal("tree1.png"));
    private Texture joyTexture = new Texture(Gdx.files.internal("joyemoji.png"));
    private EarthTile earthTile1 = new EarthTile(0,0,true);
    private EarthTile earthTile2 = new EarthTile(16,0,true);
    private EarthTile earthTile3 = new EarthTile(32,0,false);
    private EarthTile earthTile4 = new EarthTile(32,8,true);
    private EarthTile earthTile5 = new EarthTile(48,0,false);
    private EarthTile earthTile6 = new EarthTile(48,8,true);
    private Sprite treeSprite1;
    private Sprite bushSprite1;
    private Sprite joySprite1;
    //we need this because the constructor build the body
    private Shroom shroom1 = new Shroom();

    public Level1MapGraphics(){
        treeSprite1 = new Sprite(treeTexture,0,0,32,64);
        treeSprite1.setPosition(0,7.50f);
        treeSprite1.setSize(16,32);
        bushSprite1 = new Sprite(bushTexture,0,0,32,32);
        bushSprite1.setPosition(-3,2.5f);
        bushSprite1.setSize(16,16);
        joySprite1 = new Sprite(joyTexture,0,0,16,16);
        joySprite1.setPosition(2f,10f);
        joySprite1.setSize(8f,8f);
        earthSprites.add(earthTile1.getSprite());
        earthSprites.add(earthTile2.getSprite());
        earthSprites.add(earthTile3.getSprite());
        earthSprites.add(earthTile4.getSprite());
        earthSprites.add(earthTile5.getSprite());
        earthSprites.add(earthTile6.getSprite());
        bushSprites.add(bushSprite1);
        treeSprites.add(treeSprite1);
        shroomSprites.add(shroom1.getShroomSprite());
        joyers.add(joySprite1);
    }

    public void shroomBounce(String shroomData){
        int index = Integer.parseInt(shroomData.substring(shroomData.length()-1));
        switch (index){
            case 1: shroom1.setBouncing(true);
            //TODO MORE SHROOMS ALSO MAYBE SHROOM PHYSICS AND SPRITE IN SAME CLASS?
        }
    }

    public void draw(SpriteBatch batch){
        for (Sprite sprite : earthSprites){
            sprite.draw(batch);
        }
        for (Sprite sprite : treeSprites){
            sprite.draw(batch);
        }
        for (Sprite sprite : bushSprites){
            sprite.draw(batch);
        }
        for (Sprite sprite : shroomSprites){
            shroom1.draw(batch, sprite);
        }
        for (Sprite sprite : joyers){
            //because the box body might be moving we need to move the sprite as well
            sprite.setPosition(darkknight.level1.level1MapPhysics.joyBody.getPosition().x-joySprite1.getWidth()/2,darkknight.level1.level1MapPhysics.joyBody.getPosition().y-joySprite1.getHeight()/2);
            //this is still fucked
            sprite.setOrigin(joySprite1.getWidth()/2,joySprite1.getHeight()/2);
            sprite.setRotation(darkknight.level1.level1MapPhysics.joyBody.getTransform().getRotation()*55);
            sprite.draw(batch);
        }
    }

    //TODO DISPOSE SHIT?
    public Texture getBushTexture(){
        return bushTexture;
    }
}

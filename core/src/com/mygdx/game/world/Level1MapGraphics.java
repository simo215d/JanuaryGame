package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class Level1MapGraphics {
    private ArrayList<Sprite> earthSprites = new ArrayList<>();
    private ArrayList<Sprite> level0EarthSprites = new ArrayList<>();
    private ArrayList<Sprite> level1EarthSprites = new ArrayList<>();
    private ArrayList<Sprite> bushSprites = new ArrayList<>();
    private ArrayList<Sprite> treeSprites = new ArrayList<>();
    private ArrayList<Sprite> shroomSprites = new ArrayList<>();
    private ArrayList<Sprite> joyers = new ArrayList<>();
    private ArrayList<Sprite> buildings = new ArrayList<>();
    private Texture bushTexture = new Texture(Gdx.files.internal("bush1.png"));
    private Texture treeTexture = new Texture(Gdx.files.internal("tree1.png"));
    private Texture joyTexture = new Texture(Gdx.files.internal("joyemoji.png"));
    private Texture tree2Texture = new Texture(Gdx.files.internal("tree2.png"));
    private Texture tower1Texture = new Texture(Gdx.files.internal("tower1.png"));
    //top level earthSprites are always made this way
    private EarthTile earthTile1 = new EarthTile(0,0,1);
    private EarthTile earthTile2 = new EarthTile(16,0,1);
    private EarthTile earthTile3 = new EarthTile(32,8,1);
    private EarthTile earthTile4 = new EarthTile(48,8,1);
    private EarthTile earthTile5 = new EarthTile(64,8,1);
    private EarthTile earthTile6 = new EarthTile(80,8,3);
    private EarthTile earthTile7 = new EarthTile(96,8,4);
    private EarthTile earthTile8 = new EarthTile(112,8,4);
    private EarthTile earthTile9 = new EarthTile(128,8,4);
    private EarthTile earthTile10 = new EarthTile(144,8,4);
    private EarthTile earthTile11 = new EarthTile(160,8,4);
    private EarthTile earthTile12 = new EarthTile(176,8,4);
    private EarthTile earthTile13 = new EarthTile(192,8,4);
    private EarthTile earthTile14 = new EarthTile(208,8,4);
    private Sprite treeSprite1;
    private Sprite bushSprite1;
    private Sprite joySprite1;
    private Sprite tree2Sprite1;
    private Sprite tower1Sprite1;
    //we need this because the constructor build the body
    private Shroom shroom1 = new Shroom();

    public Level1MapGraphics(){
        treeSprite1 = new Sprite(treeTexture,0,0,32,64);
        treeSprite1.setPosition(-3,7.50f);
        treeSprite1.setSize(16,32);
        bushSprite1 = new Sprite(bushTexture,0,0,32,32);
        bushSprite1.setPosition(-6,2.5f);
        bushSprite1.setSize(16,16);
        joySprite1 = new Sprite(joyTexture,0,0,16,16);
        joySprite1.setPosition(2f,10f);
        joySprite1.setSize(8f,8f);
        tree2Sprite1 = new Sprite(tree2Texture,0,0,32,64);
        tree2Sprite1.setPosition(110,15f);
        tree2Sprite1.setSize(16,32);
        tower1Sprite1 = new Sprite(tower1Texture,0,0,64,64);
        tower1Sprite1.setPosition(-35,7.5f);
        tower1Sprite1.setSize(32,32);
        //create bottom/level0 earthTiles
        for (int i = -3; i < 14; i++) {
            level0EarthSprites.add(new EarthTile(16*i,-8,2).getSprite());
            if (i<0){
                level0EarthSprites.add(new EarthTile(16*i,0,1).getSprite());
            }
        }
        //create level1 earthTiles
        for (int i = 2; i < 14; i++) {
            level1EarthSprites.add(new EarthTile(16*i,0,2).getSprite());
        }
        earthSprites.add(earthTile1.getSprite());
        earthSprites.add(earthTile2.getSprite());
        earthSprites.add(earthTile3.getSprite());
        earthSprites.add(earthTile4.getSprite());
        earthSprites.add(earthTile5.getSprite());
        earthSprites.add(earthTile6.getSprite());
        earthSprites.add(earthTile7.getSprite());
        earthSprites.add(earthTile8.getSprite());
        earthSprites.add(earthTile9.getSprite());
        earthSprites.add(earthTile10.getSprite());
        earthSprites.add(earthTile11.getSprite());
        earthSprites.add(earthTile12.getSprite());
        earthSprites.add(earthTile13.getSprite());
        earthSprites.add(earthTile14.getSprite());
        bushSprites.add(bushSprite1);
        treeSprites.add(treeSprite1);
        shroomSprites.add(shroom1.getShroomSprite());
        joyers.add(joySprite1);
        treeSprites.add(tree2Sprite1);
        buildings.add(tower1Sprite1);
    }

    public void shroomBounce(String shroomData){
        int index = Integer.parseInt(shroomData.substring(shroomData.length()-1));
        switch (index){
            case 1: shroom1.setBouncing(true);
            //TODO MORE SHROOMS ALSO MAYBE SHROOM PHYSICS AND SPRITE IN SAME CLASS?
        }
    }

    public void draw(Batch batch){
        for (Sprite sprite : earthSprites){
            sprite.draw(batch);
        }
        for (Sprite sprite : level0EarthSprites){
            sprite.draw(batch);
        }
        for (Sprite sprite : level1EarthSprites){
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
        for (Sprite sprite : buildings){
            sprite.draw(batch);
        }
    }

    //TODO DISPOSE SHIT?
    public Texture getBushTexture(){
        return bushTexture;
    }
}

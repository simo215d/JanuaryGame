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
    private Texture bush2Texture = new Texture(Gdx.files.internal("bush2.png"));
    private Texture treeTexture = new Texture(Gdx.files.internal("tree1.png"));
    private Texture joyTexture = new Texture(Gdx.files.internal("joyemoji.png"));
    private Texture tree2Texture = new Texture(Gdx.files.internal("tree2.png"));
    private Texture tower1Texture = new Texture(Gdx.files.internal("tower1.png"));
    private Texture tent1Texture = new Texture(Gdx.files.internal("tent1.png"));
    private Texture armoryTexture = new Texture(Gdx.files.internal("armoryStand.png"));
    private Texture guard2Texture = new Texture(Gdx.files.internal("guard2.png"));
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
    private EarthTile earthTile15 = new EarthTile(208+16,8,4);
    private EarthTile earthTile16 = new EarthTile(208+16*2,8,4);
    private EarthTile earthTile17 = new EarthTile(208+16*3,8,4);
    private EarthTile earthTile18 = new EarthTile(208+16*4,8,4);
    private EarthTile earthTile19 = new EarthTile(208+16*5,8,4);
    private EarthTile earthTile20 = new EarthTile(208+16*14,8,4);
    private EarthTile earthTile21 = new EarthTile(208+16*15,8,4);
    private EarthTile earthTile22 = new EarthTile(208+16*16,8,4);
    private EarthTile earthTile23 = new EarthTile(208+16*17,8,4);
    private EarthTile earthTile24 = new EarthTile(208+16*18,8,4);
    private EarthTile earthTile25 = new EarthTile(208+16*19,8,4);
    private EarthTile earthTile26 = new EarthTile(208+16*20,8,4);
    private EarthTile earthTile27 = new EarthTile(208+16*21,8,4);
    private EarthTile earthTile28 = new EarthTile(208+16*22,8,4);
    private EarthTile earthTile29 = new EarthTile(208+16*23,8,4);
    private EarthTile earthTile30 = new EarthTile(208+16*24,8,4);
    private EarthTile earthTile31 = new EarthTile(208+16*25,8,4);
    private EarthTile earthTile32 = new EarthTile(208+16*26,8,4);
    private EarthTile earthTile33 = new EarthTile(208+16*27,8,4);
    private EarthTile earthTile34 = new EarthTile(208+16*28,8,4);
    private EarthTile earthTile35 = new EarthTile(208+16*29,8,4);
    private EarthTile earthTile36 = new EarthTile(208+16*30,8,4);
    private BridgeTorch bridgeTorch = new BridgeTorch(292,33f);
    private Sprite treeSprite1;
    private Sprite bushSprite1;
    private Sprite bushSprite2;
    private Sprite bush2Sprite1;
    private Sprite bush2Sprite2;
    private Sprite joySprite1;
    private Sprite tree2Sprite1;
    private Sprite tree2Sprite2;
    private Sprite tree2Sprite3;
    private Sprite tree2Sprite4;
    private Sprite tree2Sprite5;
    private Sprite tree2Sprite6;
    private Sprite tower1Sprite1;
    private Sprite tent1Sprite1;
    private Sprite tent1Sprite2;
    private Sprite armorySprite1;
    private Sprite guard2Sprite;
    private BridgeLift bridgeLift = new BridgeLift();
    //we need this because the constructor build the body
    private Shroom shroom1 = new Shroom();

    public Level1MapGraphics(){
        treeSprite1 = new Sprite(treeTexture,0,0,32,64);
        treeSprite1.setPosition(-3,7.50f);
        treeSprite1.setSize(16,32);
        bushSprite1 = new Sprite(bushTexture,0,0,32,32);
        bushSprite1.setPosition(-6,2.5f);
        bushSprite1.setSize(16,16);
        bush2Sprite1 = new Sprite(bush2Texture,0,0,10,10);
        bush2Sprite1.setPosition(125,15f);
        bush2Sprite1.setSize(5,5);
        bush2Sprite2 = new Sprite(bush2Texture,0,0,10,10);
        bush2Sprite2.setPosition(300,15f);
        bush2Sprite2.setSize(5,5);
        joySprite1 = new Sprite(joyTexture,0,0,16,16);
        joySprite1.setPosition(2f,10f);
        joySprite1.setSize(8f,8f);
        tree2Sprite1 = new Sprite(tree2Texture,0,0,32,64);
        tree2Sprite1.setPosition(110,15f);
        tree2Sprite1.setSize(16,32);
        tree2Sprite2 = new Sprite(tree2Texture,0,0,32,64);
        tree2Sprite2.setPosition(210,15f);
        tree2Sprite2.setSize(16,32);
        tree2Sprite3 = new Sprite(tree2Texture,0,0,32,64);
        tree2Sprite3.setPosition(435,15f);
        tree2Sprite3.setSize(16,32);
        tree2Sprite4 = new Sprite(tree2Texture,0,0,32,64);
        tree2Sprite4.setPosition(492,15f);
        tree2Sprite4.setSize(16,32);
        tree2Sprite5 = new Sprite(tree2Texture,0,0,32,64);
        tree2Sprite5.setPosition(530,15f);
        tree2Sprite5.setSize(16,32);
        tree2Sprite6 = new Sprite(tree2Texture,0,0,32,64);
        tree2Sprite6.setPosition(660,15f);
        tree2Sprite6.setSize(16,32);
        tower1Sprite1 = new Sprite(tower1Texture,0,0,64,64);
        tower1Sprite1.setPosition(-35,7.5f);
        tower1Sprite1.setSize(32,32);
        tent1Sprite1 = new Sprite(tent1Texture,0,0,32,32);
        tent1Sprite1.setPosition(455,15f);
        tent1Sprite1.setSize(16,16);
        tent1Sprite2 = new Sprite(tent1Texture,0,0,32,32);
        tent1Sprite2.setPosition(510,15f);
        tent1Sprite2.setSize(16,16);
        armorySprite1 = new Sprite(armoryTexture,0,0,16,32);
        armorySprite1.setPosition(495,15f);
        armorySprite1.setSize(8,16);
        guard2Sprite = new Sprite(guard2Texture,0,0,32,32);
        guard2Sprite.setPosition(445,15f);
        guard2Sprite.setSize(16,16);
        //create bottom/level0 earthTiles
        for (int i = -3; i < 44; i++) {
            level0EarthSprites.add(new EarthTile(16*i,-8,2).getSprite());
            if (i<0){
                level0EarthSprites.add(new EarthTile(16*i,0,1).getSprite());
            }
        }
        //create level1 earthTiles
        for (int i = 2; i < 44; i++) {
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
        earthSprites.add(earthTile15.getSprite());
        earthSprites.add(earthTile16.getSprite());
        earthSprites.add(earthTile17.getSprite());
        earthSprites.add(earthTile18.getSprite());
        earthSprites.add(earthTile19.getSprite());
        earthSprites.add(earthTile20.getSprite());
        earthSprites.add(earthTile21.getSprite());
        earthSprites.add(earthTile22.getSprite());
        earthSprites.add(earthTile23.getSprite());
        earthSprites.add(earthTile24.getSprite());
        earthSprites.add(earthTile25.getSprite());
        earthSprites.add(earthTile26.getSprite());
        earthSprites.add(earthTile27.getSprite());
        earthSprites.add(earthTile28.getSprite());
        earthSprites.add(earthTile29.getSprite());
        earthSprites.add(earthTile30.getSprite());
        earthSprites.add(earthTile31.getSprite());
        earthSprites.add(earthTile32.getSprite());
        earthSprites.add(earthTile33.getSprite());
        earthSprites.add(earthTile34.getSprite());
        earthSprites.add(earthTile35.getSprite());
        earthSprites.add(earthTile36.getSprite());
        bushSprites.add(bushSprite1);
        bushSprites.add(bush2Sprite1);
        bushSprites.add(bush2Sprite2);
        treeSprites.add(treeSprite1);
        shroomSprites.add(shroom1.getShroomSprite());
        //joyers.add(joySprite1);
        treeSprites.add(tree2Sprite1);
        treeSprites.add(tree2Sprite2);
        treeSprites.add(tree2Sprite3);
        treeSprites.add(tree2Sprite4);
        treeSprites.add(tree2Sprite5);
        treeSprites.add(tree2Sprite6);
        buildings.add(tower1Sprite1);
        buildings.add(tent1Sprite1);
        buildings.add(tent1Sprite2);
        buildings.add(armorySprite1);
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
        bridgeLift.draw(batch);
        bridgeTorch.draw(batch);
        guard2Sprite.draw(batch);
    }

    //TODO DISPOSE SHIT?
    public Texture getBushTexture(){
        return bushTexture;
    }
}

package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.world.allies.Level1Allies;

public class Level1 {
    public static Level1MapGraphics level1MapGraphics;
    public static Level1MapPhysics level1MapPhysics;
    public static Level1Enemies level1Enemies;
    public static Level1Allies level1Allies;

    public Level1(){
        level1MapGraphics = new Level1MapGraphics();
        level1MapPhysics = new Level1MapPhysics();
        level1Enemies = new Level1Enemies();
        level1Allies = new Level1Allies();
    }

    public void draw(Batch batch){
        //render map sprites
        level1MapGraphics.draw(batch);
        //render enemies
        level1Enemies.draw(batch);
        //render allies
        level1Allies.draw(batch);
    }
}

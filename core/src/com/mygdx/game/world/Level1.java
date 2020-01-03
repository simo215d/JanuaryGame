package com.mygdx.game.world;

public class Level1 {
    public static Level1MapGraphics level1MapGraphics;
    public static Level1MapPhysics level1MapPhysics;
    public static Level1Enemies level1Enemies;

    public Level1(){
        level1MapGraphics = new Level1MapGraphics();
        level1MapPhysics = new Level1MapPhysics();
        level1Enemies = new Level1Enemies();
    }
}

package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.darkknight;
import com.mygdx.game.world.targetdummy.TargetDummy;
import com.mygdx.game.world.targetdummy.TargetDummyPhysics;

import java.util.ArrayList;

public class Level1Enemies {
    //targetdummy test
    private TargetDummy targetDummy2 = new TargetDummy("TTTDummyBody2",20, 40);
    private TargetDummy targetDummy1 = new TargetDummy("TTTDummyBody1", 20, 30);
    ArrayList<ArrayList> enemies = new ArrayList<>();
    ArrayList<TargetDummy> targetDummies = new ArrayList<>();

    public Level1Enemies(){
        enemies.add(targetDummies);
        targetDummies.add(targetDummy1);
        targetDummies.add(targetDummy2);
    }

    public void draw(Batch batch){
        for (ArrayList list : enemies){
            for (TargetDummy targetDummy : targetDummies){
                targetDummy.draw(batch);
            }
        }
    }

    public void attack1(String name){
        for (ArrayList list : enemies){
            for (TargetDummy targetDummy : targetDummies){
                if (targetDummy.getName().equals(name)){
                    targetDummy.attack1(1);
                }
            }
        }
    }
}

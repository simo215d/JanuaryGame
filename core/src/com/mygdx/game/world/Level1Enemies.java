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
    ArrayList<ArrayList> enemies = new ArrayList<>();
    //targetDummies
    private TargetDummy targetDummy2 = new TargetDummy("TTTFDummyBody2",35, 60);
    private TargetDummy targetDummy1 = new TargetDummy("TTTFDummyBody1", 35, 50);
    ArrayList<TargetDummy> targetDummies = new ArrayList<>();
    //bonfire test
    private DamageObject bonfire = new DamageObject("FFFTbonfire1",5,5,"bonfire1Sheet.png",5,1,0.3f,40,14, 2.5f, 1);
    ArrayList<DamageObject> damageObjects = new ArrayList<>();

    public Level1Enemies(){
        enemies.add(targetDummies);
        enemies.add(damageObjects);
        targetDummies.add(targetDummy1);
        targetDummies.add(targetDummy2);
        damageObjects.add(bonfire);
    }

    public void draw(Batch batch){
        for (ArrayList list : enemies){
            for (TargetDummy targetDummy : targetDummies){
                targetDummy.draw(batch);
            }
            for (DamageObject object : damageObjects){
                object.draw(batch);
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

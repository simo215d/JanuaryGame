package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.world.targetdummy.TargetDummy;

import java.util.ArrayList;

public class Level1Enemies {
    ArrayList<ArrayList> enemies = new ArrayList<>();
    //targetDummies
    private TargetDummy targetDummy2 = new TargetDummy("TTTFDummyBody2",20, 60);
    private TargetDummy targetDummy1 = new TargetDummy("TTTFDummyBody1", 20, 50);
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

    public void attackAnEnemy(String name, int damage){
        for (TargetDummy targetDummy : targetDummies){
            if (targetDummy.getName().equals(name)){
                targetDummy.takeDamage(damage);
            }
        }
    }
}

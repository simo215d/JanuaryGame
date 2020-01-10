package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.world.targetdummy.TargetDummy;
import com.mygdx.game.world.undead1.Undead1;

import java.util.ArrayList;

public class Level1Enemies {
    ArrayList<ArrayList> enemies = new ArrayList<>();
    //targetDummies
    private TargetDummy targetDummy2 = new TargetDummy("TTTFDummyBody2",20, 60);
    private TargetDummy targetDummy1 = new TargetDummy("TTTFDummyBody1", 20, 50);
    public ArrayList<TargetDummy> targetDummies = new ArrayList<>();
    //undead1s
    private Undead1 undead11 = new Undead1("TTTFUndead1",80,60);
    public ArrayList<Undead1> undead1s = new ArrayList<>();
    //bonfire test
    private DamageObject bonfire = new DamageObject("FFFTbonfire1",5,5,"bonfire1Sheet.png",5,1,0.3f,40,14, 2.5f, 1);
    public ArrayList<DamageObject> damageObjects = new ArrayList<>();

    public Level1Enemies(){
        enemies.add(targetDummies);
        enemies.add(damageObjects);
        enemies.add(undead1s);
        targetDummies.add(targetDummy1);
        targetDummies.add(targetDummy2);
        undead1s.add(undead11);
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
            for (Undead1 undead1 : undead1s){
                undead1.draw(batch);
            }
        }
    }

    public void attackAnEnemy(String name, int damage){
        //run through all enemy lists and find the chosen one!
        for (TargetDummy targetDummy : targetDummies){
            if (targetDummy.getName().equals(name)){
                targetDummy.takeDamage(damage);
            }
        }
        for (Undead1 undead1 : undead1s){
            if (undead1.getName().equals(name)){
                undead1.takeDamage(damage);
            }
        }
    }

    public void enterCombatWith(String name){
        //run through all enemy lists and find the chosen one!
        for (Undead1 undead1 : undead1s){
            if (undead1.getName().equals(name)){
                undead1.setInCombat(true);
            }
        }
    }
}

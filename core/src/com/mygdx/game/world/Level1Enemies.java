package com.mygdx.game.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.world.targetdummy.TargetDummy;
import com.mygdx.game.world.undead1.Undead1;
import com.mygdx.game.world.undead2.Undead2;

import java.util.ArrayList;

public class Level1Enemies {
    ArrayList<ArrayList> enemies = new ArrayList<>();
    //targetDummies
    private TargetDummy targetDummy2 = new TargetDummy("TTTFDummyBody2",20, 60);
    private TargetDummy targetDummy1 = new TargetDummy("TTTFDummyBody1", 20, 50);
    public ArrayList<TargetDummy> targetDummies = new ArrayList<>();
    //undead1s
    private Undead1 undead1_1 = new Undead1("TTTFUndead1_1",160,60);
    private Undead1 undead1_2 = new Undead1("TTTFUndead1_2",254,30);
    public ArrayList<Undead1> undead1s = new ArrayList<>();
    //undead2s
    private Undead2 undead2_1 = new Undead2("TTTFUndead2_1",190,60);
    public ArrayList<Undead2> undead2s = new ArrayList<>();
    //bonfire test
    private DamageObject bonfire = new DamageObject("FFFTbonfire1",5,5,"bonfire1Sheet.png",5,1,0.3f,40,14, 2.5f, 1, false, true);
    private DamageObject toxicEarth = new DamageObject("FFFTToxicEarth1",20,5,"earthWithToxicSheet.png",2,1,1f,208+16*6,8, 22f, 3, false, true);
    private DamageObject toxicEarth2 = new DamageObject("FFFTToxicEarth2",20,5,"earthWithToxicSheet.png",2,1,1f,208+16*10,8, 22f, 3, true, true);
    private DamageObject toxicEarth3JustAsDamageObjectToFillTheGap = new DamageObject("FFFTToxicEarth3",20,5,"earthWithToxicSheet.png",2,1,1f,208+16*8,8, 22f, 3, false, false);
    public ArrayList<DamageObject> damageObjects = new ArrayList<>();
    //toxic shrooms
    private ShroomToxic shroomToxic1 = new ShroomToxic("FFFFShroomToxic1",105,16.5f);
    private ShroomToxic shroomToxic2 = new ShroomToxic("FFFFShroomToxic2",230,16.5f);
    public ArrayList<ShroomToxic> shroomToxics = new ArrayList<>();

    public Level1Enemies(){
        enemies.add(targetDummies);
        enemies.add(damageObjects);
        enemies.add(undead1s);
        enemies.add(shroomToxics);
        targetDummies.add(targetDummy1);
        targetDummies.add(targetDummy2);
        undead1s.add(undead1_1);
        undead1s.add(undead1_2);
        undead2s.add(undead2_1);
        damageObjects.add(bonfire);
        damageObjects.add(toxicEarth);
        damageObjects.add(toxicEarth2);
        damageObjects.add(toxicEarth3JustAsDamageObjectToFillTheGap);
        shroomToxics.add(shroomToxic1);
        shroomToxics.add(shroomToxic2);
    }

    public void draw(Batch batch){
        int undead1IndexToDelete = -1;
        int undead2IndexToDelete = -1;
        for (ArrayList list : enemies){
            for (TargetDummy targetDummy : targetDummies){
                targetDummy.draw(batch);
            }
            for (DamageObject object : damageObjects){
                object.draw(batch);
            }
            for (Undead1 undead1 : undead1s){
                undead1.draw(batch);
                if (undead1.isShouldBeDeleted()){
                    undead1IndexToDelete=undead1s.indexOf(undead1);
                }
            }
            for (Undead2 undead2 : undead2s){
                undead2.draw(batch);
                if (undead2.isShouldBeDeleted()){
                    undead2IndexToDelete=undead2s.indexOf(undead2);
                }
            }
            for (ShroomToxic shroomToxic : shroomToxics){
                shroomToxic.draw(batch);
            }
        }
        if (undead1IndexToDelete!=-1){
            undead1s.remove(undead1IndexToDelete);
        }
        if (undead2IndexToDelete!=-1){
            undead2s.remove(undead2IndexToDelete);
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
        for (Undead2 undead2 : undead2s){
            if (undead2.getName().equals(name)){
                undead2.takeDamage(damage);
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
        for (Undead2 undead2 : undead2s){
            if (undead2.getName().equals(name)){
                undead2.setInCombat(true);
            }
        }
    }
}

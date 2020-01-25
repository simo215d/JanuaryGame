package com.mygdx.game.world.necromancer;

import com.mygdx.game.darkknight;
import com.mygdx.game.world.DamageNumber;

import java.util.ArrayList;

public class NecromancerActions {
    private int health;
    private int maxHealth;
    private String actionState = "idle";
    private boolean isDead;
    //combat fields
    private boolean inCombat;
    private boolean canAct = true;
    private int numberOfShots=0;
    private boolean isPaused;
    private float pauseTime;
    private ArrayList<BloodBall> bloodBalls = new ArrayList<>();
    private int bloodBallCounter = 1;
    private BloodMeteor bloodMeteor;
    //2 next fields are required to see if we should render the character red as an indication of damage taken
    private int renderRedStartTime;
    private boolean renderRed = false;
    //damage numbers to render
    private ArrayList<DamageNumber> damageNumbers = new ArrayList<>();

    public NecromancerActions(){
        maxHealth=50;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        if (!isDead) {
            health -= damage;
            if (health <= 0) {
                isDead = true;
                health = 0;
                actionState="death";
            }
            renderRed = true;
            renderRedStartTime = (int) darkknight.gameTimeCentiSeconds;
            damageNumbers.add(new DamageNumber(damage, damageNumbers));
            if (!inCombat) {
                setInCombat(true);
            }
        }
    }

    public void update(){
        //deleted expired damageNumbers
        int damageNumberToDeleteIndex = -1;
        for (DamageNumber damageNumber : damageNumbers){
            if (damageNumber.getShouldBeDeleted()){
                damageNumberToDeleteIndex=damageNumbers.indexOf(damageNumber);
            }
        }
        if (damageNumberToDeleteIndex!=-1){
            damageNumbers.remove(damageNumberToDeleteIndex);
        }
        //check if we should still render him red
        if (renderRed && darkknight.gameTimeCentiSeconds-renderRedStartTime>1){
            renderRed=false;
        }
        //check if we should unPause (3 sec pause)
        if (isPaused && darkknight.gameTimeCentiSeconds-pauseTime>30){
            setPaused(false);
        }
        if (inCombat && canAct && !isPaused && !isDead) fight();
    }

    private void fight(){
        if (numberOfShots<3){
            shoot();
        } else summon();
    }

    private void shoot(){
        actionState="shoot";
        numberOfShots++;
        canAct=false;
    }

    public void launch(float x, float y){
        bloodBalls.add(new BloodBall("BloodBall"+bloodBallCounter,x,y));
        bloodBallCounter++;
    }

    public void destroyBloodBall(String name){
        System.out.println(name);
        int arrowToDeleteIndex = -1;
        for (BloodBall bloodBall : bloodBalls){
            if (bloodBall.getName().equals(name)){
                darkknight.bodiesToDestroy.add(bloodBall.getBody());
                arrowToDeleteIndex=bloodBalls.indexOf(bloodBall);
                System.out.println("Deleting: "+bloodBall.getName());
            }
        }
        if (arrowToDeleteIndex!=-1)
            bloodBalls.remove(arrowToDeleteIndex);
        System.out.println("numbers of bloodBalls: "+bloodBalls.size());
    }

    private void summon(){
        actionState="summon";
        canAct=false;
        numberOfShots=0;
    }

    public void launchMeteor(float x, float y){
        bloodMeteor = new BloodMeteor("BloodMeteor",x,y);
    }

    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public ArrayList<DamageNumber> getDamageNumbers(){
        return damageNumbers;
    }

    public boolean isRenderRed(){
        return renderRed;
    }

    public void setInCombat(boolean b){
        inCombat=b;
    }

    public void setCanAct(boolean b){
        canAct=b;
    }

    public String getActionState(){
        return actionState;
    }

    public void setPaused(boolean b){
        if (!isDead)
        actionState="idle";
        pauseTime=darkknight.gameTimeCentiSeconds;
        isPaused=b;
    }

    public ArrayList<BloodBall> getBloodBalls(){
        return bloodBalls;
    }

    public BloodMeteor getBloodMeteor(){
        return bloodMeteor;
    }

    public void setBloodMeteorToNull(){
        bloodMeteor=null;
        System.out.println("METEOR IS NULL NOW :D:DD:DD:");
    }

    public boolean isDead(){
        return isDead;
    }
}

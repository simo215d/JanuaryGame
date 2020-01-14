package com.mygdx.game.world.undead2;

import com.mygdx.game.darkknight;
import com.mygdx.game.world.DamageNumber;

import java.util.ArrayList;

public class Undead2Actions {
    private String actionState = "idle";
    private int health;
    private int maxHealth;
    private boolean isAlive = true;
    //this boolean is used to see if player has entered the sensor
    private boolean inCombat = false;
    //2 next fields are required to see if we should render the character red as an indication of damage taken
    private int renderRedStartTime;
    private boolean renderRed = false;
    //damage numbers to render
    private ArrayList<DamageNumber> damageNumbers = new ArrayList<>();
    //boolean to make sure we only shoot once. this boolean is controlled from graphics
    private boolean hasRecentlyShotArrow = false;
    //list of arrows
    private ArrayList<Undead2Arrow> arrows = new ArrayList<>();
    //this is used to make sure arrows have unique names
    private int arrowId = 1;

    public Undead2Actions(){
        maxHealth=15;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        if(health-damage<=0){
            health=0;
            isAlive=false;
            actionState="dying";
        } else{
            health-=damage;
        }
        inCombat=true;
        renderRed=true;
        renderRedStartTime=(int) darkknight.gameTimeCentiSeconds;
        damageNumbers.add(new DamageNumber(damage, damageNumbers));
        if (isAlive){
            actionState="shooting";
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
    }

    //this is called from graphics shoot animation upon its end
    public void shootArrow(float x, float y, boolean isFlipX, String name){
        arrows.add(new Undead2Arrow(("FFFFArrow"+arrowId)+name,x,y,isFlipX));
        arrowId++;
    }

    public String getActionState() {
        return actionState;
    }

    public int getHealth(){
        return health;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setInCombat(Boolean bool){
        actionState="shooting";
        inCombat=bool;
    }

    public void setHasRecentlyShotArrow(boolean bool){
        hasRecentlyShotArrow=bool;
    }

    public boolean getHasRecentlyShotArrow(){
        return hasRecentlyShotArrow;
    }

    public boolean isRenderRed(){
        return renderRed;
    }

    public ArrayList<DamageNumber> getDamageNumbers(){
        return damageNumbers;
    }

    public ArrayList<Undead2Arrow> getArrows(){
        return arrows;
    }

    public void destroyAnArrow(String name){
        int arrowToDeleteIndex = -1;
        for (Undead2Arrow undead2Arrow : arrows){
            if (undead2Arrow.getName().equals(name)){
                darkknight.bodiesToDestroy.add(undead2Arrow.getBody());
                arrowToDeleteIndex=arrows.indexOf(undead2Arrow);
                System.out.println("Deleting: "+undead2Arrow.getName());
            }
        }
        arrows.remove(arrowToDeleteIndex);
        System.out.println("numbers of arrows: "+arrows.size());
    }
}

package com.mygdx.game.world.undead1;

import com.mygdx.game.darkknight;

public class Undead1Actions {
    private String actionState = "idle";
    private int health;
    private int maxHealth;
    private float combatRange = 13;
    //this boolean is used to see if player has entered the sensor
    private boolean inCombat = false;
    //this is used to make sure he doesnt jump when he first starts running because his velocity is 0 when he starts
    private boolean hasMoved = false;
    private boolean isAttacking = false;
    private int numberOfAttacks = 0;
    //this is used to mark the time we ended the animation
    private int pauseTime;
    //this is used to see if enough time has passed since pauseTime
    private int attackCoolDownDeciSeconds = 50;

    public Undead1Actions(){
        maxHealth=20;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        if(health-damage<=0){
            health=maxHealth;
        } else{
            health-=damage;
            System.out.println("undead took "+damage+" damage...");
        }
        inCombat=true;
    }

    public void update(Undead1Physics physics){
        float distanceToPlayer = physics.getBody().getPosition().x-darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x;
        //run
        if (inCombat && distanceToPlayer>combatRange && !isAttacking|| inCombat && distanceToPlayer<-combatRange && !isAttacking){
            actionState="running";
            //jump if stuck
            if (physics.getBody().getLinearVelocity().x==0.0f && hasMoved){
                //jump right
                if (distanceToPlayer<0){
                    physics.getBody().setLinearVelocity(physics.getBody().getLinearVelocity().x,10);
                }
                //jump left
                if (distanceToPlayer>0){
                    physics.getBody().setLinearVelocity(physics.getBody().getLinearVelocity().x,10);
                }
            }
            //run left else right
            if (distanceToPlayer>0){
                physics.getBody().setLinearVelocity(-10,physics.getBody().getLinearVelocity().y);
            } else physics.getBody().setLinearVelocity(10,physics.getBody().getLinearVelocity().y);
            hasMoved=true;
        } else {
            hasMoved=false;
            if (!isAttacking)
            actionState="idle";
            physics.getBody().setLinearVelocity(0,physics.getBody().getLinearVelocity().y);
        }
        //fight
        if (inCombat && distanceToPlayer<=combatRange && distanceToPlayer>=-combatRange){
            if ((int)darkknight.gameTimeCentiSeconds-pauseTime>=attackCoolDownDeciSeconds) {
                fight(physics);
            }
        }
    }

    private void fight(Undead1Physics physics){
        if (numberOfAttacks==0){
            numberOfAttacks=(int)(Math.random()*2)+2;
            System.out.println("fight sequence has been reset with new number of attacks: "+numberOfAttacks);
        }
        isAttacking=true;
        physics.getBody().setLinearVelocity(0,physics.getBody().getLinearVelocity().y);
        if (!actionState.equals("slamming") && !actionState.equals("swinging")) {
            slam();
        }
    }

    //slam is started from fight method, and maybe continued after swing is done, if numberOfAttacks were 3
    public void slam(){
        actionState="slamming";
    }

    //this method is called from graphics upon animation ending
    public void endAnAttack(String attack){
        //start the slam animation to begin the sequence if not already in battle actionState
        if (!actionState.equals("slamming") && !actionState.equals("swinging")){
            actionState="slamming";
        }
        if (attack.equals("slamming")){
            if (numberOfAttacks>0){
                actionState="swinging";
                numberOfAttacks-=1;
            }
        }
        if (attack.equals("swinging")){
            if (numberOfAttacks>0){
                actionState="slamming";
                numberOfAttacks-=1;
            }
        }
        if (numberOfAttacks==0){
            setAttacking(false);
            actionState="idle";
            //TODO idle counter = 0. wait for 5 idles then set boolean canStartNewAttackSequence to true
            pauseTime=(int)darkknight.gameTimeCentiSeconds;
            System.out.println("pause time: "+pauseTime);
        }
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
        inCombat=bool;
    }

    public void setAttacking(boolean bool){
        isAttacking=bool;
    }
}

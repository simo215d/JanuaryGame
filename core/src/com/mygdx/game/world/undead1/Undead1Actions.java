package com.mygdx.game.world.undead1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mygdx.game.darkknight;
import com.mygdx.game.world.DamageNumber;

import java.util.ArrayList;

public class Undead1Actions {
    //private Sound takeDamageSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sound_oof.mp3"));
    private Sound slamSound = Gdx.audio.newSound(Gdx.files.internal("sounds/donk.mp3"));
    private Sound swingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/klonk.mp3"));
    private Sound missSound = Gdx.audio.newSound(Gdx.files.internal("sounds/woosh.mp3"));
    //private Sound deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/marioScream.mp3"));
    private String actionState = "idle";
    private int health;
    private int maxHealth;
    private boolean isAlive = true;
    private float combatRange = 13;
    //this boolean is used to see if player has entered the sensor
    private boolean inCombat = false;
    //this is used to make sure he doesnt jump when he first starts running because his velocity is 0 when he starts
    private boolean hasMoved = false;
    private boolean isAttacking = false;
    private int numberOfAttacks = 0;
    //this is used to mark the time we ended the animation
    private int pauseTime;
    //2 next fields are required to see if we should render the character red as an indication of damage taken
    private int renderRedStartTime;
    private boolean renderRed = false;
    //this is used to see if enough time has passed since pauseTime
    private int attackCoolDownDeciSeconds = 30;
    private float horizontalDistanceToPlayer;
    private float verticalDistanceToPlayer;
    //to make sure only take damage once, since fps updates in graphics yadaydayda
    private boolean recentlyAttackedWithSlam = false;
    private boolean recentlyAttackedWithSwing = false;
    //damage numbers to render
    private ArrayList<DamageNumber> damageNumbers = new ArrayList<>();

    public Undead1Actions(){
        maxHealth=20;
        health=maxHealth;
    }

    public void takeDamage(int damage){
        if(health-damage<=0){
            health=0;
            if (isAlive){
                //deathSound.play();
            }
            isAlive=false;
        } else{
            //takeDamageSound.play();
            health-=damage;
        }
        inCombat=true;
        renderRed=true;
        renderRedStartTime=(int)darkknight.gameTimeCentiSeconds;
        damageNumbers.add(new DamageNumber(damage, damageNumbers));
    }

    public void update(Undead1Physics physics){
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
        if (isAlive) {
            horizontalDistanceToPlayer = physics.getBody().getPosition().x - darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().x;
            verticalDistanceToPlayer = physics.getBody().getPosition().y - darkknight.player.getPlayerPhysics().getPlayerBody().getPosition().y;
            //run
            if (inCombat && horizontalDistanceToPlayer > combatRange && !isAttacking || inCombat && horizontalDistanceToPlayer < -combatRange && !isAttacking) {
                actionState = "running";
                //jump if stuck
                if (physics.getBody().getLinearVelocity().x == 0.0f && hasMoved) {
                    //jump right
                    if (horizontalDistanceToPlayer < 0) {
                        physics.getBody().setLinearVelocity(physics.getBody().getLinearVelocity().x, 10);
                    }
                    //jump left
                    if (horizontalDistanceToPlayer > 0) {
                        physics.getBody().setLinearVelocity(physics.getBody().getLinearVelocity().x, 10);
                    }
                }
                //run left else right
                if (horizontalDistanceToPlayer > 0) {
                    physics.getBody().setLinearVelocity(-10, physics.getBody().getLinearVelocity().y);
                } else physics.getBody().setLinearVelocity(10, physics.getBody().getLinearVelocity().y);
                hasMoved = true;
            } else {
                hasMoved = false;
                if (!isAttacking)
                    actionState = "idle";
                physics.getBody().setLinearVelocity(0, physics.getBody().getLinearVelocity().y);
            }
            //fight
            if (inCombat && horizontalDistanceToPlayer <= combatRange && horizontalDistanceToPlayer >= -combatRange) {
                if ((int) darkknight.gameTimeCentiSeconds - pauseTime >= attackCoolDownDeciSeconds) {
                    fight(physics);
                }
            }
        } else actionState="dying";
    }

    private void fight(Undead1Physics physics){
        if (numberOfAttacks==0){
            numberOfAttacks=(int)(Math.random()*2)+2;
            //System.out.println("fight sequence has been reset with new number of attacks: "+numberOfAttacks);
            //prevent him from sliding into a fight
            physics.getBody().setLinearVelocity(0,physics.getBody().getLinearVelocity().y);
        }
        isAttacking=true;
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
        if (attack.equals("slamming")){
            if (numberOfAttacks>0){
                actionState="swinging";
                numberOfAttacks-=1;
                recentlyAttackedWithSlam=true;
                recentlyAttackedWithSwing=false;
                missSound.play(0.5f);
            }
        }
        if (attack.equals("swinging")){
            if (numberOfAttacks>0){
                actionState="slamming";
                numberOfAttacks-=1;
                recentlyAttackedWithSlam=false;
                recentlyAttackedWithSwing=true;
                missSound.play(0.5f);
            }
        }
        if (numberOfAttacks==0){
            setAttacking(false);
            actionState="idle";
            pauseTime=(int)darkknight.gameTimeCentiSeconds;
            recentlyAttackedWithSlam=false;
            recentlyAttackedWithSwing=false;
        }
    }

    public void damagePlayerIfInRange(String attack){
        if (attack.equals("slamming") && !recentlyAttackedWithSlam){
            if (horizontalDistanceToPlayer <=combatRange && horizontalDistanceToPlayer >0 && verticalDistanceToPlayer<8 && verticalDistanceToPlayer>-8 || horizontalDistanceToPlayer <0 && horizontalDistanceToPlayer >=-combatRange && verticalDistanceToPlayer<8 && verticalDistanceToPlayer>-8) {
                darkknight.player.getPlayerCombat().takeDamage(20, horizontalDistanceToPlayer);
                slamSound.play();
                System.out.println("HIT: distance: " + horizontalDistanceToPlayer);
                recentlyAttackedWithSlam = true;
                recentlyAttackedWithSwing = false;
            } else System.out.println("MISS: distance: "+ horizontalDistanceToPlayer);
        } else if (attack.equals("swinging") && !recentlyAttackedWithSwing){
            if (horizontalDistanceToPlayer <=combatRange && horizontalDistanceToPlayer >0 && verticalDistanceToPlayer<8 && verticalDistanceToPlayer>-8 || horizontalDistanceToPlayer <0 && horizontalDistanceToPlayer >=-combatRange && verticalDistanceToPlayer<8 && verticalDistanceToPlayer>-8) {
                darkknight.player.getPlayerCombat().takeDamage(20, horizontalDistanceToPlayer);
                swingSound.play();
                System.out.println("HIT: distance: " + horizontalDistanceToPlayer);
                recentlyAttackedWithSwing = true;
                recentlyAttackedWithSlam = false;
            } else {
                System.out.println("MISS: distance: "+ horizontalDistanceToPlayer);
            }
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

    public boolean isRenderRed(){
        return renderRed;
    }

    public ArrayList<DamageNumber> getDamageNumbers(){
        return damageNumbers;
    }
}

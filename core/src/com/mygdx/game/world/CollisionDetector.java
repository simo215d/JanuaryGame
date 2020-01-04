package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class CollisionDetector implements ContactListener {
    public static ArrayList<String> currentContacts = new ArrayList<>();
    public static ArrayList<String> currentRightEnemies = new ArrayList<>();
    public static ArrayList<String> currentLeftEnemies = new ArrayList<>();

    @Override
    public void beginContact(Contact contact) {
        //Regarding fixture a and b: We don't know which is which, so we'll need to check.
        //The first letters of other objects are T or F. Current booleans: walkable, pushable, attackable, damageObject
        //System.out.println("ENTERED: FA: "+contact.getFixtureA().getUserData()+" FB: "+contact.getFixtureB().getUserData());
        //airborne checker
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().toString().charAt(0)=='T' || contact.getFixtureA().getUserData().toString().charAt(0)=='T' && contact.getFixtureB().getUserData().equals("Foot")){
            //if we land while we should be pushing it needs to know to go to pushing animation, because there is a scenario where
            //you jump without exiting pushing sensor, so we need to set it back to pushing
            if (darkknight.player.getPlayerMovement().getIsAirBorne()){
                //set animation state based on ongoing player-actions
                if (darkknight.player.getPlayerMovement().getPushingRight()){
                    darkknight.player.getPlayerGraphics().setAnimationState("pushingRight");
                }
                if (darkknight.player.getPlayerMovement().getPushingLeft()){
                    darkknight.player.getPlayerGraphics().setAnimationState("pushingLeft");
                }
            }
            darkknight.player.getPlayerMovement().setAirBorne(false);
            //check if our foot collided with a walkable, if so then add
            addToContacts(contact);
        }
        //shroom bounce
        //TODO WE SHOULD ADD A THIRD DATA BOOLEAN: BOUNCABLE BECAUSE WE MIGHT NEED MULTIBLE SHROOMS WITH DIFFERENT ID
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().equals("FFFFShroomCollider1") || contact.getFixtureA().getUserData().equals("FFFFShroomCollider1") && contact.getFixtureB().getUserData().equals("Foot")){
            if (contact.getFixtureB().getUserData().equals("FFFFShroomCollider1")){
                darkknight.player.getPlayerMovement().shroomBounce(contact.getFixtureB().getUserData().toString());
            } else darkknight.player.getPlayerMovement().shroomBounce(contact.getFixtureA().getUserData().toString());
        }
        //push right
        if (contact.getFixtureA().getUserData().equals("UpperRightBody") && contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperRightBody")){
            darkknight.player.getPlayerMovement().setPushingRight(true);
            darkknight.player.getPlayerGraphics().setAnimationState("pushingRight");
            darkknight.player.getPlayerGraphics().getSpritePlayer().setFlip(false,false);
            darkknight.player.getPlayerGraphics().getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight4.png"))));
        }
        //push left
        if (contact.getFixtureA().getUserData().equals("UpperLeftBody") && contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperLeftBody")){
            darkknight.player.getPlayerMovement().setPushingLeft(true);
            darkknight.player.getPlayerGraphics().setAnimationState("pushingLeft");
            darkknight.player.getPlayerGraphics().getSpritePlayer().setFlip(true,false);
            darkknight.player.getPlayerGraphics().getSpritePlayer().setTexture(new Texture((Gdx.files.internal("knight4.png"))));
        }
        //player right combat sensor
        if (contact.getFixtureA().getUserData().equals("RightCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T' || contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("RightCombatSensor")){
            System.out.println("Enemy right ENTER");
            addToCurrentEnemies(contact,currentRightEnemies,"RightCombatSensor");
        }
        //player left combat sensor
        if (contact.getFixtureA().getUserData().equals("LeftCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T'|| contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("LeftCombatSensor")){
            System.out.println("Enemy left ENTER");
            addToCurrentEnemies(contact,currentLeftEnemies,"LeftCombatSensor");
        }
        //damage object
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().toString().charAt(3)=='T'|| contact.getFixtureA().getUserData().toString().charAt(3)=='T' && contact.getFixtureB().getUserData().equals("Foot")){
            for (DamageObject object : Level1.level1Enemies.damageObjects){
                if (object.getName().equals(contact.getFixtureA().getUserData()) || object.getName().equals(contact.getFixtureB().getUserData())){
                    object.setActive(true);
                }
            }
        }
        //fireBall. first make sure we only call the substring on strings that even have that amount of characters in it
        if (contact.getFixtureA().getUserData().toString().length()>=8 && contact.getFixtureB().getUserData().toString().length()>=8){
            if (contact.getFixtureA().getUserData().toString().substring(0,8).equals("FireBall") && contact.getFixtureB().getUserData().toString().charAt(2)=='T' || contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().toString().substring(0,8).equals("FireBall"))
            System.out.println("our fireball hit an enemy");
        }
    }

    @Override
    public void endContact(Contact contact) {
        //System.out.println("EXITED: FA: "+contact.getFixtureA().getUserData()+" FB: "+contact.getFixtureB().getUserData());
        //check if something exited from our foot. important that we check this before we use it in our statement next.
        if (contact.getFixtureA().getUserData().equals("Foot") || contact.getFixtureB().getUserData().equals("Foot")) {
            removeFromContacts(contact);
        }
        //airborne checker
        if (contact.getFixtureA().getUserData().equals("Foot") && getCountOfWalkableContacts()==0 || getCountOfWalkableContacts()==0 && contact.getFixtureB().getUserData().equals("Foot")){
            darkknight.player.getPlayerMovement().setAirBorne(true);
            darkknight.player.getPlayerGraphics().setAnimationState("jumping");
        }
        //push right
        if (contact.getFixtureA().getUserData().equals("UpperRightBody") &&contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperRightBody")){
            darkknight.player.getPlayerMovement().setPushingRight(false);
            if (darkknight.player.getPlayerMovement().getIsAirBorne()){
                darkknight.player.getPlayerGraphics().setAnimationState("jumping");
            }
        }
        //push left
        if (contact.getFixtureA().getUserData().equals("UpperLeftBody") && contact.getFixtureB().getUserData().toString().charAt(1)=='T' || contact.getFixtureA().getUserData().toString().charAt(1)=='T' && contact.getFixtureB().getUserData().equals("UpperLeftBody")){
            darkknight.player.getPlayerMovement().setPushingLeft(false);
            if (darkknight.player.getPlayerMovement().getIsAirBorne()){
                darkknight.player.getPlayerGraphics().setAnimationState("jumping");
            }
        }
        //player right combat sensor
        if (contact.getFixtureA().getUserData().equals("RightCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T' || contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("RightCombatSensor")){
            System.out.println("Enemy right EXIT");
            removeFromCurrentEnemies(contact,currentRightEnemies);
        }
        //player left combat sensor
        if (contact.getFixtureA().getUserData().equals("LeftCombatSensor") && contact.getFixtureB().getUserData().toString().charAt(2)=='T'|| contact.getFixtureA().getUserData().toString().charAt(2)=='T' && contact.getFixtureB().getUserData().equals("LeftCombatSensor")){
            System.out.println("Enemy left EXIT");
            removeFromCurrentEnemies(contact,currentLeftEnemies);
        }
        //damage object
        if (contact.getFixtureA().getUserData().equals("Foot") && contact.getFixtureB().getUserData().toString().charAt(3)=='T'|| contact.getFixtureA().getUserData().toString().charAt(3)=='T' && contact.getFixtureB().getUserData().equals("Foot")){
            for (DamageObject object : Level1.level1Enemies.damageObjects){
                if (object.getName().equals(contact.getFixtureA().getUserData()) || object.getName().equals(contact.getFixtureB().getUserData())){
                    object.setActive(false);
                }
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    //we use this to check if we have 0 walkable contacts then allow for airborne
    private int getCountOfWalkableContacts(){
        int countsOfWalkable = 0;
        for (String string : currentContacts){
            if (string.charAt(0)=='T'){
                countsOfWalkable++;
            }
        }
        return countsOfWalkable;
    }

    //find a non player string that ended collision with foot and remove from list
    private void removeFromContacts(Contact contact) {
        //find a non player string in fixtureA
        String fixtureAString = contact.getFixtureA().getUserData().toString();
        int ia = -1;
        for (String aUserdata : currentContacts) {
            if (fixtureAString.equals(aUserdata)) {
                ia = currentContacts.indexOf(aUserdata);
            }
        }
        if (ia >= 0) {
            currentContacts.remove(ia);
            //System.out.println("we ended contact with: "+fixtureAString);
        }
        //find a non player string in fixtureB
        String fixtureBString = contact.getFixtureB().getUserData().toString();
        int ib = -1;
        for (String bUserdata : currentContacts){
            if (fixtureBString.equals(bUserdata)){
                ib=currentContacts.indexOf(bUserdata);
            }
        }
        if (ib>=0) {
            currentContacts.remove(ib);
            //System.out.println("we ended contact with: "+fixtureBString);
        }
    }

    //if our foot is involved in an enter collision then add the thing we collided with
    private void addToContacts(Contact contact){
        String fixtureAString = contact.getFixtureA().getUserData().toString();
        String fixtureBString = contact.getFixtureB().getUserData().toString();
        if (fixtureAString.equals("Foot")){
            currentContacts.add(fixtureBString);
        }
        if (fixtureBString.equals("Foot")){
            currentContacts.add(fixtureAString);
        }
    }

    private void addToCurrentEnemies(Contact contact, ArrayList<String> list, String playerSensor){
        String fixA = contact.getFixtureA().getUserData().toString();
        String fixB = contact.getFixtureB().getUserData().toString();
        if (fixA.equals(playerSensor)){
            list.add(fixB);
        } else list.add(fixA);
    }

    private void removeFromCurrentEnemies(Contact contact, ArrayList<String> list){
        String fixA = contact.getFixtureA().getUserData().toString();
        String fixB = contact.getFixtureB().getUserData().toString();
        int stringToRemove = -1;
        for (String string : list){
            if (fixA.equals(string) || fixB.equals(string)){
                stringToRemove=list.indexOf(string);
            }
        }
        if (stringToRemove>-1){
            list.remove(stringToRemove);
        }
    }
}

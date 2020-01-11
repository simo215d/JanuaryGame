package com.mygdx.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.darkknight;

import java.util.ArrayList;

public class DamageNumber {
    private int damage;
    private float x=0;
    private float y=0;
    private ArrayList<DamageNumber> list;
    private BitmapFont text = new BitmapFont();
    private boolean shouldBeDeleted = false;
    private int startTime;
    private int listPosition;

    public DamageNumber(int damage, ArrayList<DamageNumber> list){
        text.getData().setScale(0.2f);
        startTime= (int)darkknight.gameTimeCentiSeconds;
        this.damage=damage;
        this.list=list;
        listPosition=list.size();
    }

    public void draw(Batch batch, float x, float y){
        if (darkknight.gameTimeCentiSeconds-startTime>=5){
            shouldBeDeleted=true;
        }
        if (this.x==0 && this.y==0){
            this.x=x;
            this.y=y;
            System.out.println("we set position");
        }
        text.draw(batch,""+damage,this.x,this.y+13+listPosition*3);
    }

    public boolean getShouldBeDeleted(){
        return shouldBeDeleted;
    }
}

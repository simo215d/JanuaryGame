package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EscapeUI {
    public static boolean isActive;
    private boolean isOnPage1 = true;
    //graphics
    private Texture uiMenuTop1Texture = new Texture(Gdx.files.internal("uiMenuTop1.png"));
    private Texture uiMenuTop2Texture = new Texture(Gdx.files.internal("uiMenuTop2.png"));
    private Texture uiMenuTop3Texture = new Texture(Gdx.files.internal("uiMenuTop3.png"));
    private Texture uiMenuControls1Texture = new Texture(Gdx.files.internal("uiMenuControls1.png"));
    private Texture uiMenuControls2Texture = new Texture(Gdx.files.internal("uiMenuControls2.png"));
    private Texture uiMenuAbilities1Texture = new Texture(Gdx.files.internal("uiMenuAbilities1.png"));
    private Texture uiMenuAbilities2Texture = new Texture(Gdx.files.internal("uiMenuAbilities2.png"));
    private Sprite uiMenuTopSprite;
    private Sprite uiMenuContentSprite;
    //stage stuff
    private Stage stage;
    private TextButton buttonContinue;
    private TextButton buttonQuit;
    private TextButton buttonNextPage;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;

    public EscapeUI(){
        //other
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        font.getData().setScale(2);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font=font;
        buttonContinue = new TextButton("Continue", textButtonStyle);
        buttonQuit = new TextButton("Quit", textButtonStyle);
        buttonNextPage = new TextButton("ooo",textButtonStyle);
        buttonQuit.setPosition(darkknight.cam.viewportWidth*11,darkknight.cam.viewportHeight*26);
        buttonQuit.setScale(1.88f,1.33f);
        buttonContinue.setPosition(darkknight.cam.viewportWidth*15,darkknight.cam.viewportHeight*26);
        buttonContinue.setScale(1.75f,1.35f);
        buttonNextPage.setPosition(darkknight.cam.viewportWidth*15.5f,darkknight.cam.viewportHeight*9.5f);
        //listener for quit button
        buttonQuit.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                uiMenuTopSprite.setTexture(uiMenuTop2Texture);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                uiMenuTopSprite.setTexture(uiMenuTop1Texture);
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                //doing this because there still seems to be some things open/in use, in Task manager
                System.exit(0);
            }
        });
        //listener for continue button
        buttonContinue.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                uiMenuTopSprite.setTexture(uiMenuTop3Texture);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                uiMenuTopSprite.setTexture(uiMenuTop1Texture);
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isActive=false;
            }
        });
        //listener for continue button
        buttonNextPage.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                if (isOnPage1){
                    uiMenuContentSprite.setTexture(uiMenuControls2Texture);
                } else uiMenuContentSprite.setTexture(uiMenuAbilities2Texture);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if (isOnPage1){
                    uiMenuContentSprite.setTexture(uiMenuControls1Texture);
                } else uiMenuContentSprite.setTexture(uiMenuAbilities1Texture);
            }
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isOnPage1){
                    uiMenuContentSprite.setTexture(uiMenuAbilities1Texture);
                    isOnPage1=false;
                } else {
                    uiMenuContentSprite.setTexture(uiMenuControls1Texture);
                    isOnPage1=true;
                }
            }
        });
        stage.addActor(buttonContinue);
        stage.addActor(buttonQuit);
        stage.addActor(buttonNextPage);
        //sprites
        uiMenuTopSprite = new Sprite(uiMenuTop1Texture,0,0,256,56);
        uiMenuTopSprite.setSize(32,7);
        uiMenuContentSprite = new Sprite(uiMenuControls1Texture,0,0,256,200);
        uiMenuContentSprite.setSize(32,25);
    }

    public void draw(Batch batch){
        if (isActive) {
            uiMenuTopSprite.setPosition(darkknight.cam.position.x - 15, darkknight.cam.position.y + 15);
            uiMenuTopSprite.draw(batch);
            uiMenuContentSprite.setPosition(darkknight.cam.position.x - 15, darkknight.cam.position.y - 10);
            uiMenuContentSprite.draw(batch);
            stage.act();
        }
        //this is only used for debugging
        /*else {
            stage.act();
            stage.draw();
        }
         */
    }
}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.hmi.InputHandler;
import com.mygdx.game.player.Player;
import com.mygdx.game.world.CollisionDetector;
import com.mygdx.game.world.Level1;

public class darkknight extends ApplicationAdapter {
	//it is not in seconds because we need 2 decimals if we want a fast paced action based on time
	public static long gameTimeCentiSeconds;
	private long startTime;

	private OrthographicCamera cam;
	private SpriteBatch batch;

	public static Level1 level1;
	private InputHandler inputHandler;
	//public static PlayerActions playerActions;
	//public static PlayerGraphics playerGraphics;
	public static Player player;
	//box2d stuff
	public static World world;
	private Box2DDebugRenderer debugRenderer;
	//public static PlayerPhysics playerPhysics;
	private CollisionDetector collisionDetector;

	@Override
	public void create() {
		gameTimeCentiSeconds =0;
		startTime=System.currentTimeMillis();
		//box2d stuff
		world = new World(new Vector2(0, -10), true);
		debugRenderer = new Box2DDebugRenderer();
		collisionDetector = new CollisionDetector();
		//class creators
		level1 = new Level1();
		inputHandler = new InputHandler();
		/*
		playerGraphics = new PlayerGraphics();
		playerPhysics = new PlayerPhysics();
		playerActions = new PlayerActions();
		 */
		player = new Player();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		// Constructs a new OrthographicCamera, using the given viewport width and height
		// Height is multiplied by aspect ratio.
		cam = new OrthographicCamera(30, 30 * (h / w));
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();

		batch = new SpriteBatch();
	}

	@Override
	public void render() {
		//update game time centi seconds
		gameTimeCentiSeconds =((TimeUtils.timeSinceMillis(startTime)) / 100L);
	    //downward fall multiplier
        player.getPlayerPhysics().fallFaster(1f);
		//player follow circle
		player.getPlayerGraphics().getSpritePlayer().setPosition(player.getPlayerPhysics().getPlayerBody().getPosition().x-player.getPlayerGraphics().getSpritePlayer().getWidth()/2,player.getPlayerPhysics().getPlayerBody().getPosition().y-3f);
		//checks for inputs
		inputHandler.handleInput(cam,player.getPlayerGraphics().getSpritePlayer());
		cam.update();
		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//render sprites
		batch.begin();
		//render map sprites
		level1.level1MapGraphics.draw(batch);
		//render enemies
		level1.level1Enemies.draw(batch);
		//player.getPlayerGraphics().draw(batch);
		player.draw(batch, cam);
		batch.end();
		//box2d
		world.step(1/60f, 6, 2);
		world.setContactListener(collisionDetector);
		//box2d collider graphics
		debugRenderer.render(world, cam.combined);
	}

	@Override
	public void resize(int width, int height) {
		cam.viewportWidth = 30f;
		cam.viewportHeight = 30f * height/width;
		cam.update();
	}

	@Override
	public void dispose() {
		batch.dispose();
		level1.level1MapGraphics.getBushTexture().dispose();
	}
}
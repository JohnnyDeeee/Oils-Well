package johndoe.game.screens;

import johndoe.game.font.BmapFont;
import johndoe.game.listeners.LabelListener;
import johndoe.game.utils.SaveFileInteract;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class EndGame implements Screen{

	private Stage stage;
	private Skin skin;
	private Table table;
	private BmapFont bmapFont;
	private static TextField username_field;
	private TextFieldStyle textfieldStyle_1;
	private Label title_label, yourscore_label, score_label, username_label, save_label;
	private LabelStyle labelStyle_1, labelStyle_2;
	private static int score, meters;
	private static String username;
	private boolean isCleared;
	private Texture background;
	
	public EndGame(int score, int meters){
		this.score = score;
		this.meters = meters;
	}
	
	@Override
	public void show() {
		isCleared = false;
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		background = new Texture(Gdx.files.internal("tilesets/main_menu.png"));
		
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		
		table = new Table(skin); 
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		labelStyle_1 = new LabelStyle(bmapFont.bubblegum_white_128x128, Color.PINK);
		labelStyle_2 = new LabelStyle(bmapFont.bubblegum_white_64x64, Color.PINK);
		textfieldStyle_1 = new TextFieldStyle(bmapFont.bubblegum_white_64x64, Color.PURPLE, null, null, null);
		
		title_label = new Label("Save score", labelStyle_1);
		title_label.setFontScale(0.9f);
		
		yourscore_label = new Label("Your score:", labelStyle_2);
		yourscore_label.setFontScale(0.4f);
		
		score_label = new Label(Integer.toString(score) + " points, " + Integer.toString(meters) + " meters", labelStyle_2);
		
		username_label = new Label("Username: ", labelStyle_2);
		username_label.setFontScale(0.5f);
		
		username_field = new TextField("type your username", textfieldStyle_1);
		username_field.setAlignment(Align.center);
		username_field.addListener(new FocusListener(){
	        @Override
	        public boolean handle(Event event){
	        if (event.toString().equals("mouseMoved") 
	                || event.toString().equals("exit") 
	                || event.toString().equals("enter") 
	                || event.toString().equals("keyDown") 
	                || event.toString().equals("touchUp")){

	            return false;
	        }
	            if(!isCleared){
	            	username_field.setText("");
	            	isCleared = true;
	            }

	            return true;
	        }

	    });
		
		save_label = new Label("Save", labelStyle_2);
		save_label.setFontScale(0.7f);
		save_label.setName("Save");
		save_label.addListener(new LabelListener(save_label));
		
		
		table.center().top();
		table.add().spaceBottom(10);
		table.row();
		table.add(title_label);
		table.row();
		table.add().spaceBottom(20);
		table.row();
		table.add(yourscore_label);
		table.row();
		table.add(score_label);
		table.row();
		table.add().spaceBottom(30);
		table.row();
		table.add(username_label);
		table.row();
		table.add(username_field).size(700, 64);
		table.row();
		table.add().spaceBottom(60);
		table.row();
		table.add(save_label);
//		table.debug();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		stage.getBatch().begin();
		stage.getBatch().draw(background, 0, 0);
		stage.getBatch().end();
		
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@SuppressWarnings("static-access")
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		bmapFont.dispose();
	}
	
	public static void save(){
		username = username_field.getText();
		String object = username + "," + score + "," + meters;
		SaveFileInteract.saveScore(Gdx.files.local("save/users.txt"), object);
	}

}

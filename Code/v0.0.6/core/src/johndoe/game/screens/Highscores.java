package johndoe.game.screens;

import java.util.ArrayList;

import johndoe.game.font.BmapFont;
import johndoe.game.listeners.LabelListener;
import johndoe.game.utils.SaveFileInteract;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Highscores implements Screen{

	private Stage stage;
	private Skin skin;
	private Table table;
	private LabelStyle labelStyle_1, labelStyle_2;
	//private BmapFont bmapFont;
	private Label title_label, back_label;
	private FileHandle highscoreList;
	private Texture background;
	
	@Override
	public void show() {
		highscoreList = Gdx.files.local("save/users.txt");
		
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		background = new Texture(Gdx.files.internal("tilesets/main_menu.png"));
		
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		labelStyle_1 = new LabelStyle(BmapFont.bubblegum_white_128x128, Color.PINK);
		labelStyle_2 = new LabelStyle(BmapFont.bubblegum_white_64x64, Color.PINK);
		
		title_label = new Label("Highscores", labelStyle_1);
		title_label.setFontScale(0.9f);
		
		back_label = new Label("Back", labelStyle_2);
		back_label.setName("BackToMenu");
		back_label.setFontScale(0.6f);
		back_label.addListener(new LabelListener(back_label));
		
		table.center().top();
		table.add().spaceBottom(10);
		table.row();
		table.add(title_label);
		table.getCell(title_label).spaceBottom(90);
		table.row();
		
		
		
		ArrayList<String> scores = SaveFileInteract.getScores(highscoreList);
		int limit = 5;
		
		if(scores.size() < 5){
			limit = scores.size();
		}
		
		for(int i = 0; i < limit; i++){
			Label score_label = new Label(scores.get(i), labelStyle_2);
			
			score_label.setFontScale(0.7f);
			
			table.add(score_label);
			table.row();
		}
		
		table.add().spaceBottom(90);
		table.add(back_label);
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
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

	@Override
	public void dispose() {
		stage.dispose();
	}

}

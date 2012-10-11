package org.burstingbrains.pocketmonsters;

import java.util.HashMap;

import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.singleton.MusicPlayerSingleton;
import org.burstingbrains.pocketmon.singleton.SimpleDBSingleton;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WaitingRoomActivity extends Activity implements GameConstants{

	private static final String DUMMY_STRING = "DummyString";
	
	private static MusicPlayerSingleton musicPlayer = MusicPlayerSingleton.getSingleton();
	private static GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	private static SimpleDBSingleton database = SimpleDBSingleton.getSingleton();
	
	private TextView gameNameTextView;
	private Button p1SitButton;
	private Button p2SitButton;
	private Button refreshButton;
	
	private String gameName = DUMMY_STRING;
	private String p1Ready = DUMMY_STRING;
	private String p2Ready = DUMMY_STRING;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_room);

		//database.createItem(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "3");
		//database.updateAttribute(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "1", "Name", "Value2");
		//database.getAttributeValue(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "2", "Name");

		gameNameTextView = (TextView) this.findViewById(R.id.game_name_textView);
		gameNameTextView.setText(gameName);
		
		refreshButton = (Button) this.findViewById(R.id.refresh_button);
		refreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetDataFromServerTask().execute();
			}
		});
	}


	private void updateWaitingRoom(CharSequence string){
		gameNameTextView.setText(string);
	}

	
	class GetDataFromServerTask extends AsyncTask<Void, Void, Void>{

		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(WaitingRoomActivity.this, "Hello", "Loading. Please wait...", true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			HashMap<String,String> attributeHashMap = database.getAttributesForItem(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "1");

			gameName = attributeHashMap.get("GameName");
			p1Ready = attributeHashMap.get("P1Ready");
			p2Ready = attributeHashMap.get("P2Ready");
			
//			Iterator<Entry<String, String>> it = attributeHashMap.entrySet().iterator();
//			while(it.hasNext()){
//				Entry<String, String> pairs = it.next();
//				Log.d("qwe", "key: " + pairs.getKey() + ", value: " + pairs.getValue());
//			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			updateWaitingRoom(gameName);
			dialog.dismiss();
		}
	};
}

//private Camera camera;
//
//@Override
//public EngineOptions onCreateEngineOptions() {
//	camera = new Camera(0, 0, CAMERA_HEIGHT, CAMERA_WIDTH);
//
//	final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
//	
//	engineOptions.getTouchOptions().setNeedsMultiTouch(true);
//	engineOptions.getAudioOptions().setNeedsSound(true);
//	engineOptions.getAudioOptions().setNeedsMusic(true);
//	
//	return engineOptions;
//}
//
//
//@Override
//protected void onCreateResources() {
//	assets.init(this);
//	assets.load();
//}
//
//
//@Override
//protected Scene onCreateScene() {
//	 
//	final FPSCounter fpsCounter = new FPSCounter();
//	this.mEngine.registerUpdateHandler(fpsCounter);
//
//	final Scene scene = new Scene();
//	scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
//
////	final Text elapsedText = new Text(100, 160, assets.fontJokalLarge, "Seconds elapsed:", "Seconds elapsed: XXXXXXXXXXXXXXXXXXXXXXXXXX".length(), this.getVertexBufferObjectManager());
////	final Text fpsText = new Text(250, 240, assets.fontJokalLarge, "FPS:", "FPS: XXXXXXXXXXXXXXXXXXXXXXXXXX".length(), this.getVertexBufferObjectManager());
////	scene.attachChild(elapsedText);
////	scene.attachChild(fpsText);
//	
//	final Text gameNameText = new Text(50, 50, assets.fontJokalMedium, "Game Name:", "Game Name:".length(), this.getVertexBufferObjectManager());
//	final Text p1Text = new Text(50, 150, assets.fontJokalMedium, "Player 1", "Player 1".length(), this.getVertexBufferObjectManager());
//	final Text p2Text = new Text(50, 250, assets.fontJokalMedium, "Player 2", "Player 2".length(), this.getVertexBufferObjectManager());
//
//	scene.attachChild(gameNameText);
//	scene.attachChild(p1Text);
//	scene.attachChild(p2Text);
//
//
//	Log.d("qwe", "gameName: " + gameName);
//	Log.d("qwe", "p1Ready: " + p1Ready);
//	Log.d("qwe", "p2Ready: " + p2Ready);
//	
//	new GetDataFromServerTask().execute();
//	
////	scene.registerUpdateHandler(new TimerHandler(1 / 20.0f, true, new ITimerCallback() {
////		@Override
////		public void onTimePassed(final TimerHandler pTimerHandler) {
////			elapsedText.setText("Seconds elapsed: " + WaitingRoomActivity.this.mEngine.getSecondsElapsedTotal());
////			fpsText.setText("FPS: " + fpsCounter.getFPS());
////		}
////	}));
//
//	return scene;
//}
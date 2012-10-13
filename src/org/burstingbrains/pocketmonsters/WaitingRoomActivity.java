package org.burstingbrains.pocketmonsters;

import java.util.ArrayList;
import java.util.List;

import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.singleton.MusicPlayerSingleton;
import org.burstingbrains.pocketmon.singleton.SimpleDBSingleton;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomGameModel;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomGameView;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomListAdapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(11)
public class WaitingRoomActivity extends Activity implements GameConstants{

	private static final String DUMMY_STRING = "DummyString";
	
	private static MusicPlayerSingleton musicPlayer = MusicPlayerSingleton.getSingleton();
	private static GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	private static SimpleDBSingleton database = SimpleDBSingleton.getSingleton();
	
	
	private ListView waitingRoomGamesListView;
	private WaitingRoomListAdapter waitingRoomListAdapter;
	
	private Button refreshButton;
	
	private String gameName = DUMMY_STRING;
	private String p1Ready = DUMMY_STRING;
	private String p2Ready = DUMMY_STRING;
	
	private List<WaitingRoomGameModel> gameModels;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waiting_room);

		
		gameModels = new ArrayList<WaitingRoomGameModel>();
		for(int i=0; i<3; ++i){
			WaitingRoomGameModel gameModel = new WaitingRoomGameModel();
			gameModel.isActive = true;
			gameModel.gameName = "qwe" + i;
			gameModels.add(gameModel);
		}
		
		//database.createItem(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "3");
		//database.updateAttribute(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "1", "Name", "Value2");
		//database.getAttributeValue(SimpleDBSingleton.WAITING_ROOM_DOMAIN, "2", "Name");
		
		waitingRoomGamesListView = (ListView) this.findViewById(R.id.waiting_room_listView);
		//waitingRoomGamesListView.addView(new WaitingRoomGameView(this));
		
		waitingRoomListAdapter = new WaitingRoomListAdapter(this.getApplicationContext());
		waitingRoomListAdapter.setGamesList(gameModels);
		waitingRoomListAdapter.notifyDataSetChanged();
		waitingRoomGamesListView.setAdapter(waitingRoomListAdapter);
		
		refreshButton = (Button) this.findViewById(R.id.refresh_button);
		refreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetDataFromServerTask().execute();
			}
		});
		

		
		
//		new GetDataFromServerTask().execute();
	}


	public void updateWaitingRoomListView() {
		waitingRoomListAdapter.setGamesList(gameModels);
		waitingRoomListAdapter.notifyDataSetChanged();

	}

	class GetDataFromServerTask extends AsyncTask<Void, Void, Void>{
		
		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(WaitingRoomActivity.this, "Hello", "Loading. Please wait...", true);
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			List<String> itemNames = database.getItemNamesForDomain(SimpleDBSingleton.WAITING_ROOM_DOMAIN);
			
			int i = 0;
			for(String itemName : itemNames){
				gameModels.get(0).gameName = itemName;
				++i;
			}
	
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			updateWaitingRoomListView();
			dialog.dismiss();
		}
	}
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
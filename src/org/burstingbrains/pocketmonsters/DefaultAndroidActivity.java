package org.burstingbrains.pocketmonsters;

import java.util.ArrayList;
import java.util.List;

import org.burstingbrains.pocketmonsters.singleton.SettingsSingleton;
import org.burstingbrains.pocketmonsters.singleton.SettingsSingleton.Player;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.CreateDomainRequest;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class DefaultAndroidActivity extends Activity{
	
	private final static String USER_ACCOUNTS_DOMAIN = "UserAccount";
	
	protected AmazonSimpleDBClient sdbClient;
	protected Handler handler;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	final long delayInMilliSeconds = 50;
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        AWSCredentials credentials = new BasicAWSCredentials(PropertyLoader.getInstance().getAccessKey(), PropertyLoader.getInstance().getSecretKey()  );
        sdbClient = new AmazonSimpleDBClient( credentials);
        handler = new Handler();
        
        final Button loginOrRegisterButton = (Button) findViewById(R.id.login_register_button);
        loginOrRegisterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginOrRegister();
            }
        });
        
        final Button waitingRoomActivityButton = (Button) findViewById(R.id.waiting_room_activity_button);
        waitingRoomActivityButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		handler.postDelayed(launchWaitingRoomActivityRunnable, delayInMilliSeconds);
        	}
        });

        final Button gameMapActivityButton = (Button) findViewById(R.id.game_map_activity_button);
        gameMapActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	handler.postDelayed(launchGameMapActivityRunnable, delayInMilliSeconds);
            }
        });
        
        final Button multiplayerActivityButton = (Button) findViewById(R.id.multiplayer_activity_button);
        multiplayerActivityButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		handler.postDelayed(launchMultiplayerActivityRunnable, delayInMilliSeconds);
        	}
        });
    }

	protected void LoginOrRegister() {
        String email	= ((EditText) findViewById(R.id.user_email_edit_text)).getText().toString();
        String password = ((EditText) findViewById(R.id.user_password_edit_text)).getText().toString();
        
        if("".equals(email) || "".equals(password)){
            Toast.makeText(DefaultAndroidActivity.this, "Field cannot be empty.", Toast.LENGTH_SHORT).show();
        }
	    else{
	        new CreateDomainRequestTask().execute(USER_ACCOUNTS_DOMAIN);
	        new PutAttributesInUserAccountRequestTask().execute(email, password);
	        Toast.makeText(DefaultAndroidActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
	    }
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.player_one_radio_button:
	            if (checked){
	            	SettingsSingleton.player = Player.PlayerOne;
	    	        Toast.makeText(DefaultAndroidActivity.this, "P1", Toast.LENGTH_SHORT).show();
	            }
	            break;
	        case R.id.player_two_radio_button:
	            if (checked){
	            	SettingsSingleton.player = Player.PlayerTwo;
	    	        Toast.makeText(DefaultAndroidActivity.this, "P2", Toast.LENGTH_SHORT).show();
	            }
	            break;
	    }
	}


	private Runnable launchWaitingRoomActivityRunnable = new Runnable(){
		public void run(){
			Intent myIntent = new Intent(DefaultAndroidActivity.this, WaitingRoomActivity.class);
			startActivity(myIntent);
		}
	};
	
	private Runnable launchGameMapActivityRunnable = new Runnable(){
		public void run(){
			Intent myIntent = new Intent(DefaultAndroidActivity.this, GameMapActivity.class);
			startActivity(myIntent);
		}
	};
	
	
	private Runnable launchMultiplayerActivityRunnable = new Runnable(){
		public void run(){
			Intent myIntent = new Intent(DefaultAndroidActivity.this, MultiplayerActivity.class);
			startActivity(myIntent);
		}
	};
	
	class CreateDomainRequestTask extends AsyncTask<String, Void, Void> {
	    @Override
	    protected Void doInBackground(String... domainNames) {
	        CreateDomainRequest cdr = new CreateDomainRequest(domainNames[0]);
	        sdbClient.createDomain( cdr );
	    	
	    	return null;
	    }
	 }
	
	class PutAttributesInUserAccountRequestTask extends AsyncTask<String, Void, Void> {
	    @Override
	    protected Void doInBackground(String... keyValuePair) {
	        ReplaceableAttribute scoreAttribute = new ReplaceableAttribute( "Password", keyValuePair[1], Boolean.TRUE );
	        		
	        List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>(1);
	        attrs.add( scoreAttribute );
	        		
	        PutAttributesRequest par = new PutAttributesRequest( USER_ACCOUNTS_DOMAIN, keyValuePair[0], attrs);		
	        sdbClient.putAttributes( par );
	    	
	    	return null;
	    }
	 }
}

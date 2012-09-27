package org.burstingbrains.pocketmonsters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        
        final Button mainMenuActivityButton = (Button) findViewById(R.id.main_menu_activity_button);
        mainMenuActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	handler.postDelayed(launchMainMenuActivityRunnable, delayInMilliSeconds);
            }
        });

        final Button gameActivityButton = (Button) findViewById(R.id.game_activity_button);
        gameActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	handler.postDelayed(launchGameActivityRunnable, delayInMilliSeconds);
            }
        });
    }

	protected void LoginOrRegister() {
        String email	= ((EditText) findViewById(R.id.user_email_edit_text)).getText().toString();
        String password = ((EditText) findViewById(R.id.user_password_edit_text)).getText().toString();
        
        if("".equals(email) || "".equals(password)){
            Toast.makeText(DefaultAndroidActivity.this, "Field cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        
        new CreateDomainRequestTask().execute(USER_ACCOUNTS_DOMAIN);
        new PutAttributesInUserAccountRequestTask().execute(email, password);
        Toast.makeText(DefaultAndroidActivity.this, "Success!!", Toast.LENGTH_SHORT).show();
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	private Runnable launchGameActivityRunnable = new Runnable(){
		public void run(){
			Intent myIntent = new Intent(DefaultAndroidActivity.this, GameActivity.class);
			startActivity(myIntent);
		}
	};

	private Runnable launchMainMenuActivityRunnable = new Runnable(){
		public void run(){
			Intent myIntent = new Intent(DefaultAndroidActivity.this, MainMenuActivity.class);
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

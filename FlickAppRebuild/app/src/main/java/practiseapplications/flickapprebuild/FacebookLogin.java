package practiseapplications.flickapprebuild;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import java.util.Map;

import static android.R.attr.data;


public class FacebookLogin extends AppCompatActivity {

    CallbackManager mFacebookCallbackManager;
    LoginButton mfbLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_login);

        mFacebookCallbackManager = CallbackManager.Factory.create();

        if (isLoggedIn()){

            Intent i = new Intent(FacebookLogin.this, MapsActivity.class);
            startActivity(i);
        }
        else {
            LoginManager.getInstance().registerCallback(mFacebookCallbackManager,
                    new FacebookCallback<LoginResult>() {

                        private ProfileTracker mProfileTracker;

                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            if(Profile.getCurrentProfile() == null) {
                                mProfileTracker = new ProfileTracker() {
                                    @Override
                                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                                        mProfileTracker.stopTracking();
                                    }
                                };

                            }
                            else {
                                Profile profile = Profile.getCurrentProfile();
                                Toast.makeText(FacebookLogin.this,"Got Profile",Toast.LENGTH_SHORT).show();

                                Intent i = new Intent(FacebookLogin.this, MapsActivity.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

}

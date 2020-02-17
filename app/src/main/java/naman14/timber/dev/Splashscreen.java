package naman14.timber.dev;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.naman14.timber.OnboardScreen;
import com.naman14.timber.R;
import com.naman14.timber.activities.MainActivity;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    SharedPreferences onboarding = getSharedPreferences("MyPrefsFile", 0);
                    boolean onboard = onboarding.getBoolean("isused", false);

                    if (onboard) {
                        Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                        startActivity(intent);
                    }else if (!onboard){
                        Intent intent2 = new Intent(Splashscreen.this, OnboardScreen.class);
                        startActivity(intent2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

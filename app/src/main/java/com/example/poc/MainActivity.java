package com.example.poc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.eyeofcloud.ab.Eyeofcloud;
//import com.eyeofcloud.ab.EyeofcloudFactory;
import com.eyeofcloud.ab.EyeofcloudUserContext;
import com.eyeofcloud.ab.android.sdk.EyeofcloudClient;
import com.eyeofcloud.ab.android.sdk.EyeofcloudManager;
import com.eyeofcloud.ab.android.shared.DatafileConfig;
import com.eyeofcloud.ab.android.shared.WorkerScheduler;
import com.eyeofcloud.ab.event.internal.EventFactory;
import com.eyeofcloud.ab.eyeofclouddecision.EyeofcloudDecision;
import com.eyeofcloud.ab.eyeofcloudjson.EyeofcloudJSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EyeofcloudUserContext user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEyeofcloud();
        EyeofcloudDecision decision = user.decide("buy"); //change flagKey
        EyeofcloudJSON variables = decision.getVariables();
        Object buttonText = variables.toMap().get("string_variable");

        Button button = findViewById(R.id.button);


        button.setText(buttonText != null ? buttonText.toString() : "编辑");

        int color = Color.parseColor("#6495ED");
        button.setBackgroundColor(color);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.trackEvent("buy");
            }
        });
    }

    public void initEyeofcloud() {
        // change datafileHost
        DatafileConfig.defaultHost = "https://cdn.eyeofcloud.com";
        // change eventHost
        EventFactory.EVENT_ENDPOINT = "https://event.eyeofcloud.com/v1/events";

        EyeofcloudManager eyeofcloudManager = EyeofcloudManager.builder()
                .withEventDispatchInterval(1L, TimeUnit.SECONDS)
                .withDatafileDownloadInterval(15, TimeUnit.MINUTES)
                .withSDKKey("1000122_2e2d7fbdea1afc51")  //change sdk
                .build(getApplicationContext());
        WorkerScheduler.requestOnlyWhenConnected = false;
        Map<String, Object> attributes = new HashMap<>();
        EyeofcloudClient eyeofcloudClient = eyeofcloudManager.initialize(getApplicationContext(), R.raw.datafile);
        //用户id，实际使用时，用真实用户的id替换
        String userId = "androiddemouser1";  //change userId
        user = eyeofcloudClient.createUserContext(userId, attributes);
    }

}
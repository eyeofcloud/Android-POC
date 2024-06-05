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
        EyeofcloudDecision decision = user.decide("xingye_shiyanyi"); //change flagKey
        EyeofcloudJSON variables = decision.getVariables();
        Object buttonText = variables.toMap().get("button_text");
        Object buttonColor = variables.toMap().get("button_color");

        Button button = findViewById(R.id.button);


        button.setText(buttonText != null ? buttonText.toString() : "编辑");

        int color = Color.parseColor("#6495ED");
        try{
            color = Color.parseColor(buttonColor != null ? buttonColor.toString() : "#6495ED");
        }catch (Exception e){
            // log exception
        }
        button.setBackgroundColor(color);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.trackEvent("dianjilv1");
            }
        });
    }

    public void initEyeofcloud() {
        // change datafileHost
        DatafileConfig.defaultHost = "http://116.198.11.124:8010";
        // change eventHost
        EventFactory.EVENT_ENDPOINT = "http://116.198.11.124:8020/v1/events";

        EyeofcloudManager eyeofcloudManager = EyeofcloudManager.builder()
                .withEventDispatchInterval(1L, TimeUnit.SECONDS)
                .withDatafileDownloadInterval(15, TimeUnit.MINUTES)
                .withSDKKey("21_05f247c0c3c80d0c")  //change sdk
                .build(getApplicationContext());
        WorkerScheduler.requestOnlyWhenConnected = false;
        Map<String, Object> attributes = new HashMap<>();
        EyeofcloudClient eyeofcloudClient = eyeofcloudManager.initialize(getApplicationContext(), R.raw.datafile);
        //用户id，实际使用时，用真实用户的id替换
        String userId = "androiddemouser1";  //change userId
        user = eyeofcloudClient.createUserContext(userId, attributes);
    }

}
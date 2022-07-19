package jp.Ken.flash_anzan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class Main1 extends AppCompatActivity {

    private SoundPool soundPool;
    private int ok_btn;
    private AdView mAdView;
    private Runnable r;
    private final Handler handler = new Handler ( );


    @SuppressLint ( "ResourceAsColor" )
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.main1 );


        // ステータスバー色
        getWindow ( ).setStatusBarColor ( R.color.back );

        AudioAttributes audioAttributes = new AudioAttributes.Builder ( )
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage ( AudioAttributes.USAGE_GAME )
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType ( AudioAttributes.CONTENT_TYPE_SPEECH )
                .build ( );
        soundPool = new SoundPool.Builder ( )
                .setAudioAttributes ( audioAttributes )
                // ストリーム数に応じて
                .setMaxStreams ( 1 )
                .build ( );
        ok_btn = soundPool.load ( this , R.raw.ok_btn , 1 );

        r = new Runnable ( ) {
            @Override
            public void run ( ) {
                //GMA SDK初期化
                MobileAds.initialize ( getApplication ( ) , new OnInitializationCompleteListener ( ) {
                    @Override
                    public void onInitializationComplete ( @NonNull InitializationStatus initializationStatus ) {
                    }
                } );
                mAdView = findViewById ( R.id.adView );
                AdRequest adRequest = new AdRequest.Builder ( ).build ( );
                mAdView.loadAd ( adRequest );
            }
        };
        handler.postDelayed ( r , 2500 );
    }

    public void Test ( View v ) {
        //クリック音
        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
        //遷移
        Intent intent = new Intent ( getApplication ( ) , Main2_1.class );
        startActivity ( intent );
    }

    public void Custom ( View v ) {
        //クリック音
        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
        //遷移
        Intent intent = new Intent ( getApplication ( ) , Main2_2.class );
        startActivity ( intent );
    }
}
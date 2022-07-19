package jp.Ken.flash_anzan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main2_2 extends AppCompatActivity {
    private SoundPool soundPool;
    private int ok_btn;
    private NumberPicker numberPicker_1, numberPicker_2, numberPicker_3;
    public static final String SET_DIGIT = "set_digit";
    public static final String SET_NOD = "set_nod";
    public static final String SET_SECOND = "set_second";
    public static final String MOVE_2_2 = "main2_2";


    @SuppressLint ( "ResourceAsColor" )
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.main2_2 );

        AdView mAdView = findViewById ( R.id.adView );
        AdRequest adRequest = new AdRequest.Builder ( ).build ( );
        mAdView.loadAd ( adRequest );
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

        //桁数
        numberPicker_1 = findViewById ( R.id.numPicker1 );
        //口数
        numberPicker_2 = findViewById ( R.id.numPicker2 );
        //秒数
        numberPicker_3 = findViewById ( R.id.numPicker3 );

        //最大値
        numberPicker_1.setMaxValue ( 5 );
        numberPicker_2.setMaxValue ( 30 );
        numberPicker_3.setMaxValue ( 99 );
        //最小値
        numberPicker_1.setMinValue ( 1 );
        numberPicker_2.setMinValue ( 2 );
        numberPicker_3.setMinValue ( 1 );

    }

    public void Decision ( View v ) {
        //クリック音
        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
        //画面遷移アニメーション
        overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
        //遷移
        Intent intent = new Intent ( getApplication ( ) , Main3_2.class );
        intent.putExtra ( MOVE_2_2 , "move_main2_2" );
        //桁数
        intent.putExtra ( SET_DIGIT , numberPicker_1.getValue ( ) );
        //口数
        intent.putExtra ( SET_NOD , numberPicker_2.getValue ( ) );
        //秒数
        intent.putExtra ( SET_SECOND , numberPicker_3.getValue ( ) );
        startActivity ( intent );
        //アクティビティ終了
        finish ( );
    }
}
package jp.Ken.flash_anzan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main5_1 extends AppCompatActivity {
    private SoundPool soundPool;
    private int ok_btn;


    @SuppressLint ( "ResourceAsColor" )
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );

        setContentView ( R.layout.main5_1 );

        AdView mAdView = findViewById ( R.id.adView );
        AdRequest adRequest = new AdRequest.Builder ( ).build ( );
        mAdView.loadAd ( adRequest );


        TextView result_1_text = findViewById ( R.id.result_1 );
        TextView result_2_text = findViewById ( R.id.result_2 );

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
                .setMaxStreams ( 3 )
                .build ( );

        ok_btn = soundPool.load ( this , R.raw.ok_btn , 1 );

        //遷移時にセットした値を取得
        Intent intent = getIntent ( );
        int ans = intent.getIntExtra ( Main4_1.ANSWER , 0 );
        int ans_ent_i = intent.getIntExtra ( Main4_1.SET_ANSWER , 0 );

        if ( ans == ans_ent_i ) {
            result_1_text.setText ( "正解" );
        } else {

            result_1_text.setText ( "不正解" );
            result_1_text.setTextColor ( Color.rgb ( 66 , 165 , 245 ) );
        }
        //3桁ごとカンマで区切る
        @SuppressLint ( "DefaultLocale" ) String ans_t = String.format ( "%,d" , ans );
        //答え表示
        result_2_text.setText ( ans_t );

    }

    //もう一度同じ問題
    public void Retry ( View view ) {
        //クリック音
        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
        Intent intent = new Intent ( getApplication ( ) , Main3_1.class );
        //画面遷移アニメーション
        overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
        //遷移
        startActivity ( intent );
        //アクティビティ終了
        finish ( );
    }

    //リストへ
    public void List ( View v ) {
        //クリック音
        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
        Intent intent = new Intent ( getApplication ( ) , Main2_1.class );
        //画面遷移アニメーション
        overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
        //遷移
        startActivity ( intent );
        //アクティビティ終了
        finish ( );
    }

    //ホームへ
    public void Home ( View view ) {
        //クリック音
        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
        Intent intent = new Intent ( getApplication ( ) , Main1.class );
        //画面遷移アニメーション
        overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
        //遷移
        startActivity ( intent );
        //アクティビティ終了
        finish ( );
    }

    //端末戻るボタン無効
    @Override
    public void onBackPressed ( ) {
    }
}
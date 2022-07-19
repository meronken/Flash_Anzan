package jp.Ken.flash_anzan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Main4_1 extends AppCompatActivity {

    private int ans = 0;
    private EditText ans_num;
    private int ok_btn;
    private SoundPool soundPool;
    public static final String ANSWER = "answer";
    public static final String SET_ANSWER = "set_answer";


    @SuppressLint ( "ResourceAsColor" )
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.main4 );


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
                .setMaxStreams ( 3 )
                .build ( );

        ok_btn = soundPool.load ( this , R.raw.ok_btn , 1 );

        Intent intent = getIntent ( );
        ans = intent.getIntExtra ( Main3_1.ANSWER , 0 );


        ans_num = findViewById ( R.id.ans_num );
        Button ans_btn = findViewById ( R.id.ans_btn );

        ans_num.requestFocus ( );
        //結果画面遷移
        ans_btn.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                //クリック音
                soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
                Intent intent = new Intent ( getApplication ( ) , Main5_1.class );
                //画面遷移アニメーション
                overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
                //入力した値を取得
                Editable ans_ent_t = ans_num.getText ( );
                //未入力だった場合　エラー表示
                if ( ans_num.getText ( ).toString ( ).equals ( "" ) ) {
                    Toast.makeText ( getApplication ( ) , "答えを入力してください" , Toast.LENGTH_SHORT ).show ( );
                }
                //入力されていた場合結果画面へ遷移
                else {
                    int ans_ent_i = Integer.parseInt ( ans_ent_t.toString ( ) );
                    //答え
                    intent.putExtra ( ANSWER , ans );
                    //入力した値
                    intent.putExtra ( SET_ANSWER , ans_ent_i );
                    //遷移
                    startActivity ( intent );
                    //アクティビティ終了
                    finish ( );
                }
            }
        } );
    }

    //端末戻るボタン無効
    @Override
    public void onBackPressed ( ) {
    }
}
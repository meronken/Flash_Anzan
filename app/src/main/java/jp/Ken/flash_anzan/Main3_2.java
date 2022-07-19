package jp.Ken.flash_anzan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main3_2 extends AppCompatActivity {

    private int set_digit, set_nod, set_second;
    private int selected_Id;
    private TextView timerText;
    private int start_count = 0;
    private int count = 0;
    private int ans = 0;
    private SoundPool soundPool;
    private int ok_btn;
    private final Handler handler = new Handler ( );
    private Runnable r;
    private final Timer timer1 = new Timer ( );
    private final Timer timer2 = new Timer ( );
    public static final String ANSWER = "answer";

    @SuppressLint ( "ResourceAsColor" )
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.main3 );

        // ステータスバー色
        getWindow ( ).setStatusBarColor ( R.color.back );

        SharedPreferences pref = getSharedPreferences ( "Data" , Context.MODE_PRIVATE );
        SharedPreferences.Editor editor = pref.edit ( );

        Intent intent = getIntent ( );
        String move_main2_2 = intent.getStringExtra ( Main2_2.MOVE_2_2 );


        if ( !(move_main2_2 == null) ) {
            set_digit = intent.getIntExtra ( Main2_2.SET_DIGIT , 0 );
            set_nod = intent.getIntExtra ( Main2_2.SET_NOD , 0 );
            set_second = intent.getIntExtra ( Main2_2.SET_SECOND , 0 );
            //保存した値を削除
            editor.remove ( "selected_Id" );
            //桁数、口数、秒数を保存
            editor.putInt ( "set_digit" , set_digit );
            editor.putInt ( "set_nod" , set_nod );
            editor.putInt ( "set_second" , set_second );
        }
        //やり直し
        else {
            //保存された値を読み出す
            set_digit = pref.getInt ( "set_digit" , 0 );
            set_nod = pref.getInt ( "set_nod" , 0 );
            set_second = pref.getInt ( "set_second" , 0 );
        }
        editor.apply ( );

        timerText = findViewById ( R.id.timerText );

        //効果音
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

        ok_btn = soundPool.load ( this , R.raw.num_bgm , 1 );

        //カウントダウン
        timer1.schedule ( new TimerTask ( ) {
            @Override
            public void run ( ) {
                handler.post ( new Runnable ( ) {
                    @Override
                    public void run ( ) {
                        //カウント音
                        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , ( float ) 0.5 );
                        if ( start_count == 1 ) {
                            timer1.cancel ( );
                        }
                        start_count++;
                    }
                } );
            }
        } , 1000 , 1000 );


        //フラッシュ暗算問題
        timer2.schedule ( new TimerTask ( ) {
            @Override
            public void run ( ) {
                handler.post ( new Runnable ( ) {
                    @Override
                    public void run ( ) {
                        if ( set_nod > count ) {
                            soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , ( float ) 1.2 );
                            blinkText ( timerText );
                            timerText.setText ( String.valueOf ( Random_mum ( ) ) );
                            count++;
                        } else {
                            timerText.setVisibility ( View.INVISIBLE );
                            //処理終了
                            timer2.cancel ( );
                        }
                    }
                } );
            }
        } , 3000 , Math.round ( set_second * 1000f / set_nod ) );

        r = new Runnable ( ) {
            @Override
            public void run ( ) {
                Intent intent_ = new Intent ( getApplication ( ) , Main4_2.class );
                //画面遷移アニメーション
                overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
                intent_.putExtra ( ANSWER , ans );
                startActivity ( intent_ );
                //アクティビティ終了
                finish ( );
            }
        };
        handler.postDelayed ( r , 3500 + set_second * 1000L );
    }

    //点滅アニメーション
    private void blinkText ( TextView txtView ) {
        Animation anm = new AlphaAnimation ( 0.0f , 1.0f );
        anm.setDuration ( 110 );
        txtView.startAnimation ( anm );
    }

    //乱数生成　桁数
    public int Random_mum ( ) {
        int dig;
        Random random = new Random ( );
        //20級〜13級
        if ( set_digit == 1 ) {
            //1桁
            dig = random.nextInt ( 9 ) + 1;
        }
        //12級〜初段
        else if ( set_digit == 2 ) {
            //2桁
            dig = random.nextInt ( 90 ) + 10;
        }
        //2段〜20段
        else if ( set_digit == 3 ) {
            //3桁
            dig = random.nextInt ( 900 ) + 100;
        } else if ( set_digit == 4 ) {
            //4桁
            dig = random.nextInt ( 9000 ) + 1000;
        } else {
            //5桁
            dig = random.nextInt ( 90000 ) + 10000;
        }
        ans += dig;
        return dig;
    }


    //画面非表示時 処理を終了しホームへ
    @Override
    public void onStop ( ) {
        super.onStop ( );
        //処理終了
        timer1.cancel ( );
        timer2.cancel ( );
        handler.removeCallbacks ( r );
        //アクティビティ終了
        finish ( );
    }

    //端末戻るボタン無効
    @Override
    public void onBackPressed ( ) {
    }

}
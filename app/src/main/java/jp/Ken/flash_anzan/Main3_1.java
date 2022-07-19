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

public class Main3_1 extends AppCompatActivity {

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
        String move_main2_1 = intent.getStringExtra ( Main2_1.MOVE_2_1 );

        if ( !(move_main2_1 == null) ) {
            selected_Id = intent.getIntExtra ( Main2_1.NUMBER_ID , 0 );
            //保存した値を削除
            editor.remove ( "selected_Id" );
            //selected_Idを保存
            editor.putInt ( "selected_Id" , selected_Id );
        }
        //やり直し
        else {
            //保存された値を読み出す
            selected_Id = pref.getInt ( "selected_Id" , 0 );
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
                        if ( Num ( selected_Id ) > count ) {
                            soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , ( float ) 1.2 );
                            blinkText ( timerText );
                            timerText.setText ( String.valueOf ( Random_mum ( selected_Id ) ) );
                            count++;
                        } else {
                            timerText.setVisibility ( View.INVISIBLE );
                            //処理終了
                            timer2.cancel ( );
                        }
                    }
                } );
            }
        } , 3000 , Time ( selected_Id ) );


        r = new Runnable ( ) {
            @Override
            public void run ( ) {

                Intent intent_ = new Intent ( getApplication ( ) , Main4_1.class );
                //画面遷移アニメーション
                overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
                intent_.putExtra ( ANSWER , ans );
                startActivity ( intent_ );
                //アクティビティ終了
                finish ( );
            }
        };
        handler.postDelayed ( r , 3500 + ( long ) Time ( selected_Id ) * Num ( selected_Id ) );
    }

    //点滅アニメーション
    private void blinkText ( TextView txtView ) {
        Animation anm = new AlphaAnimation ( 0.0f , 1.0f );
        anm.setDuration ( 100 );
        txtView.startAnimation ( anm );
    }


    //口数
    public int Num ( int i ) {
        int num = 0;
        //20級
        if ( i == 0 ) {
            num = 3;
        }
        //19級
        else if ( i == 1 ) {
            num = 3;
        }
        //18級
        else if ( i == 2 ) {
            num = 5;
        }
        //17級
        else if ( i == 3 ) {
            num = 8;
        }
        //16級
        else if ( i == 4 ) {
            num = 10;
        }
        //15級
        else if ( i == 5 ) {
            num = 12;
        }
        //14級
        else if ( i == 6 ) {
            num = 15;
        }
        //13級
        else if ( i == 7 ) {
            num = 20;
        }
        //12級
        else if ( i == 8 ) {
            num = 10;
        }
        //11級
        else if ( i == 9 ) {
            num = 15;
        }
        //10級
        else if ( i == 10 ) {
            num = 2;
        }
        //9級
        else if ( i == 11 ) {
            num = 3;
        }
        //8級
        else if ( i == 12 ) {
            num = 4;
        }
        //7級
        else if ( i == 13 ) {
            num = 5;
        }
        //6級
        else if ( i == 14 ) {
            num = 6;
        }
        //5級
        else if ( i == 15 ) {
            num = 7;
        }
        //4級
        else if ( i == 16 ) {
            num = 8;
        }
        //3級
        else if ( i == 17 ) {
            num = 10;
        }
        //2級
        else if ( i == 18 ) {
            num = 12;
        }
        //1級
        else if ( i == 19 ) {
            num = 15;
        }
        //初段
        else if ( i == 20 ) {
            num = 15;
        }
        //2段
        else if ( i == 21 ) {
            num = 4;
        }
        //3段
        else if ( i == 22 ) {
            num = 6;
        }
        //4段
        else if ( i == 23 ) {
            num = 8;
        }
        //5段
        else if ( i == 24 ) {
            num = 10;
        }
        //6段
        else if ( i == 25 ) {
            num = 12;
        }
        //7〜20段
        else if ( i >= 26 ) {
            num = 15;
        }
        return num;
    }

    //タイム
    public int Time ( int i ) {
        int time = 0;

        //20級 5
        if ( i == 0 ) {
            time = 1667;
        }
        //19級 5
        else if ( i == 1 ) {
            time = 1667;
        }
        //18級 7
        else if ( i == 2 ) {
            time = 1400;
        }
        //17級 10
        else if ( i == 3 ) {
            time = 1250;
        }
        //16級 12
        else if ( i == 4 ) {
            time = 1200;
        }
        //15級 15
        else if ( i == 5 ) {
            time = 1250;
        }
        //14級 15
        else if ( i == 6 ) {
            time = 1000;
        }
        //13級 15
        else if ( i == 7 ) {
            time = 750;
        }
        //12級 10
        else if ( i == 8 ) {
            time = 1000;
        }
        //11級 15
        else if ( i == 9 ) {
            time = 1000;
        }
        //10級 4
        else if ( i == 10 ) {
            time = 2000;
        }
        //9級 6
        else if ( i == 11 ) {
            time = 2000;
        }
        //8級 7
        else if ( i == 12 ) {
            time = 1750;
        }
        //7級 8
        else if ( i == 13 ) {
            time = 1600;
        }
        //6級 9
        else if ( i == 14 ) {
            time = 1500;
        }
        //5級 11
        else if ( i == 15 ) {
            time = 1429;
        }
        //4級
        else if ( i == 16 ) {
            time = 1375;
        }
        //3級 12
        else if ( i == 17 ) {
            time = 1200;
        }
        //2級 12
        else if ( i == 18 ) {
            time = 1000;
        }
        //1級 13
        else if ( i == 19 ) {
            time = 867;
        }
        //初段 10
        else if ( i == 20 ) {
            time = 667;
        }
        //2段 4
        else if ( i == 21 ) {
            time = 1000;
        }
        //3段 5
        else if ( i == 22 ) {
            time = 833;
        }
        //4段 6
        else if ( i == 23 ) {
            time = 750;
        }
        //5段 7
        else if ( i == 24 ) {
            time = 700;
        }
        //6段 8
        else if ( i == 25 ) {
            time = 667;
        }
        //7段 8
        else if ( i == 26 ) {
            time = 533;
        }
        //8段 6
        else if ( i == 27 ) {
            time = 400;
        }
        //9段 4.5
        else if ( i == 28 ) {
            time = 300;
        }
        //10段 3
        else if ( i == 29 ) {
            time = 200;
        }
        //11段 2.8
        else if ( i == 30 ) {
            time = 187;
        }
        //12段 2.6
        else if ( i == 31 ) {
            time = 173;
        }
        //13段 2.4
        else if ( i == 32 ) {
            time = 160;
        }
        //14段 2.2
        else if ( i == 33 ) {
            time = 147;
        }
        //15段 2.0
        else if ( i == 34 ) {
            time = 133;
        }
        //16段 1.9
        else if ( i == 35 ) {
            time = 127;
        }
        //17段 1.8
        else if ( i == 36 ) {
            time = 120;
        }
        //18段 1.7
        else if ( i == 37 ) {
            time = 113;
        }
        //19段 1.6
        else if ( i == 38 ) {
            time = 107;
        }
        //20段 1.5
        else if ( i == 39 ) {
            time = 100;
        }
        return time;
    }

    //乱数生成　桁数
    public int Random_mum ( int i ) {
        int dig;
        Random random = new Random ( );
        //20級〜13級
        if ( i < 8 ) {
            //1桁
            dig = random.nextInt ( 9 ) + 1;
        }
        //12級〜初段
        else if ( i < 21 ) {
            //2桁
            dig = random.nextInt ( 90 ) + 10;
        }
        //2段〜20段
        else {
            //3桁
            dig = random.nextInt ( 900 ) + 100;
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
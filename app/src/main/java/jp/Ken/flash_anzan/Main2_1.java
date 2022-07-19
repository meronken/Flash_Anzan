package jp.Ken.flash_anzan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main2_1 extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SoundPool soundPool;
    private int ok_btn;
    public static final String NUMBER_ID = "selected_number";
    public static final String MOVE_2_1 = "main2_1";
    private static final String[] number = {
            "   20級    1桁   3口  約5秒   " ,
            "   19級    1桁   3口  約5秒   " ,
            "   18級    1桁   5口  約7秒   " ,
            "   17級    1桁   8口  約10秒 " ,
            "  16級    1桁 10口  約12秒" ,
            "  15級    1桁 12口  約15秒" ,
            "  14級    1桁 15口  約15秒" ,
            "  13級    1桁 20口  約15秒" ,
            "  12級    2桁 10口  約10秒" ,
            "  11級    2桁 15口  約15秒" ,
            "   10級    2桁   2口  約4秒   " ,
            "   9級    2桁   3口  約6秒 " ,
            "   8級    2桁   4口  約8秒 " ,
            "   7級    2桁   5口  約8秒 " ,
            "   6級    2桁   6口  約9秒 " ,
            "    5級    2桁   7口  約10秒" ,
            "    4級    2桁   8口  約11秒" ,
            "    3級    2桁 10口  約12秒" ,
            "    2級    2桁 12口  約12秒" ,
            "    1級    2桁 15口  約13秒" ,
            "   初段    2桁 15口  約10秒" ,
            "    2段    3桁   4口  約4秒  " ,
            "    3段    3桁   6口  約5秒  " ,
            "    4段    3桁   8口  約6秒  " ,
            "  5段    3桁 10口  約7秒" ,
            "  6段    3桁 12口  約8秒" ,
            "  7段    3桁 15口  約8秒" ,
            "  8段    3桁 15口  約6秒" ,
            "      9段    3桁 15口  約4.5秒 " ,
            " 10段    3桁 15口  約3秒  " ,
            "  11段    3桁 15口  約2.8秒" ,
            "  12段    3桁 15口  約2.6秒" ,
            "  13段    3桁 15口  約2.4秒" ,
            "  14段    3桁 15口  約2.2秒" ,
            "  15段    3桁 15口  約2.0秒" ,
            "  16段    3桁 15口  約1.9秒" ,
            "  17段    3桁 15口  約1.8秒" ,
            "  18段    3桁 15口  約1.7秒" ,
            "  19段    3桁 15口  約1.6秒" ,
            "  20段    3桁 15口  約1.5秒" ,
    };

    @SuppressLint ( "ResourceAsColor" )
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        //setTheme(R.style.SplashTheme);
        setContentView ( R.layout.main2_1 );

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

        List < String > itemNumber = new ArrayList <> ( Arrays.asList ( number ) );

        // ListViewのインスタンスを生成
        ListView listView = findViewById ( R.id.listview );

        // BaseAdapter を継承したadapterのインスタンスを生成
        // inflate するためにadapterに引数として渡す
        BaseAdapter adapter = new ListViewAdapter ( this.getApplicationContext ( ) ,
                R.layout.list , itemNumber );

        // ListViewにadapterをセット
        listView.setAdapter ( adapter );
        listView.setOnItemClickListener ( this );
    }

    @Override
    public void onItemClick ( AdapterView < ? > parent , View view , int position , long id ) {
        //クリック音
        soundPool.play ( ok_btn , 1.0f , 1.0f , 0 , 0 , 1 );
        //画面遷移アニメーション
        overridePendingTransition ( android.R.anim.fade_in , android.R.anim.fade_out );
        Intent intent = new Intent ( getApplication ( ) , Main3_1.class );
        //インテントにセット
        intent.putExtra ( MOVE_2_1 , "move_main2_1" );
        intent.putExtra ( NUMBER_ID , position );
        //遷移
        startActivity ( intent );
        //アクティビティ終了
        finish ( );
    }
}
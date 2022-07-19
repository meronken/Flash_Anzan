package jp.Ken.flash_anzan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;


import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    static class ViewHolder {
        TextView textView;
    }

    private final LayoutInflater inflater;
    private final int itemLayoutId;
    private final List < String > titles;

    ListViewAdapter ( Context context , int itemLayoutId ,
                      List < String > itemNumber ) {
        super ( );
        this.inflater = ( LayoutInflater ) context.getSystemService (
                Context.LAYOUT_INFLATER_SERVICE );
        this.itemLayoutId = itemLayoutId;
        this.titles = itemNumber;
    }

    @Override
    public int getCount ( ) {
        // texts 配列の要素数
        return titles.size ( );
    }

    @Override
    public Object getItem ( int position ) {
        return null;
    }

    @Override
    public long getItemId ( int position ) {
        return 0;
    }

    @SuppressLint ( "ResourceAsColor" )
    @Override
    public View getView ( int position , View convertView , ViewGroup parent ) {
        ViewHolder holder;

        // 最初だけ View を inflate して、それを再利用する
        if ( convertView == null ) {
            // activity_main.xml に list.xml を inflate して convertView とする
            convertView = inflater.inflate ( itemLayoutId , parent , false );
            // ViewHolder を生成
            holder = new ViewHolder ( );
            holder.textView = convertView.findViewById ( R.id.textView );
            convertView.setTag ( holder );


            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams (
                    ViewGroup.LayoutParams.MATCH_PARENT ,140);

            convertView.setLayoutParams ( params );
        } else {
            holder = ( ViewHolder ) convertView.getTag ( );
        }

        // 現在の position にあるファイル名リストを holder の textView にセット
        holder.textView.setText ( titles.get ( position ) );
        return convertView;
    }

}

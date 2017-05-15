package com.example.truon.gametonho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by truon on 3/7/2017.
 */

public class ListViewAdapter extends ArrayAdapter<Song> implements View.OnClickListener {
    ArrayList<Song> dataSet;
    Context mContext;

    private static class ViewHolder {
        TextView title;
        TextView artist;
        ImageButton imgbtn;
    }

    public ListViewAdapter(ArrayList<Song> data, Context context) {
        super(context, R.layout.song, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        Song song = (Song) object;


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Song song = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag


        final View result;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.song, parent, false);

            viewHolder.title = (TextView) convertView.findViewById(R.id.song_title);
            viewHolder.artist = (TextView) convertView.findViewById(R.id.song_artist);
            viewHolder.imgbtn = (ImageButton) convertView.findViewById(R.id.lovebtn);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.title.setText(song.getTitle());
        viewHolder.artist.setText(song.getArtist());
        viewHolder.imgbtn.setImageResource(song.getLove()?R.drawable.loveiconprees:R.drawable.notloveicon);
//        ImageButton imgbtn = (ImageButton) convertView.findViewById(R.id.love);
        viewHolder.imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //o day em if(song.getLove){ thi e luu vao database,nguoc lai xoa trong database di,roi qua ben fragment }
                song.setLove(!song.getLove());
                notifyDataSetChanged();
//                if (song.getLove() == false) {
//                    Song newsong = new Song();
//                    song.setLove(true);
//                    newsong = song;
//                    dataSet.remove(song);
//                    dataSet.add(newsong);
//                }
//                else song.setLove(false);
            }
        });

//        if (song.getLove()== true) {
//            viewHolder.imgbtn.setImageResource(R.drawable.loveiconprees);
//        } else viewHolder.imgbtn.setImageResource(R.drawable.notloveicon);
//         notifyDataSetChanged();
        return convertView;
    }
}

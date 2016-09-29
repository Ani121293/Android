package com.example.playlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.video_proj_example.R;

/**
 * Created by disney on 9/27/16.
 */
public class PlaylistViewHolder extends RecyclerView.ViewHolder {

    View itemView;
    ImageView videoPicture;
    ImageButton favIcon;
    TextView videoName;


    public PlaylistViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        videoPicture = (ImageView) itemView.findViewById(R.id.video_picture_play);
        favIcon = (ImageButton) itemView.findViewById(R.id.fav_icon);
        videoName = (TextView) itemView.findViewById(R.id.video_name_play);
    }
}

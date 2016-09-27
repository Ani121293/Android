package com.example.favorite;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.video_proj_example.R;

/**
 * Created by disney on 9/27/16.
 */
public class FavoriteViewHolder extends RecyclerView.ViewHolder {

    private View itemView;
    private TextView videoNumber;
    private TextView videoNameFav;
    private ImageView showDetails;

    public FavoriteViewHolder(View itemView) {

        super(itemView);
        this.itemView = itemView;
        videoNumber = (TextView) itemView.findViewById(R.id.video_number);
        videoNameFav = (TextView) itemView.findViewById(R.id.video_name_fav);
        showDetails = (ImageView) itemView.findViewById(R.id.show_details);
    }

    public TextView getmNumber() {
        return videoNumber;
    }

    public void setmNumber(Integer number) {
        this.videoNumber.setText(number.toString());
    }

    public TextView getmVideoName() {
        return videoNameFav;
    }

    public void setmVideoName(String videoName) {
        this.videoNameFav.setText(videoName);
    }

    public ImageView getmShowDetails() {
        return showDetails;
    }

    public void setmShowDetails(Integer mShowDetails) {
        this.showDetails.setImageResource(mShowDetails);
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }
}

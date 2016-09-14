package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disney.myapplication.VideoActivity;
import com.example.test.video_proj_example.R;
import com.example.disney.myapplication.Video;

/**
 * Created by disney on 9/6/16.
 */
public class DetailsFragment extends Fragment {
    private Video video;
    private View view;

    public DetailsFragment(Video video) {
        this.video = video;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((VideoActivity) getActivity()).getSupportActionBar().setTitle("Details");
        ((VideoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((VideoActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        view = inflater.inflate(R.layout.details_fragment, container, false);


        TextView videoName = (TextView) view.findViewById(R.id.video_title);
        videoName.setText(video.getmVideoName());

        ImageView videoImage = (ImageView) view.findViewById(R.id.video_image);
        if (video.getmImage() != null) {
            videoImage.setImageResource(video.getmImageId());
        }
        TextView describtionTextView = (TextView) view.findViewById(R.id.detailed_info);
        describtionTextView.setText(video.getmDescription());

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(false).setEnabled(false);
        menu.setGroupVisible(R.id.main_menu_group, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

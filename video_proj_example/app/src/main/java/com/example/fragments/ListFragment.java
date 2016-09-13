package com.example.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.disney.myapplication.VideoActivity;
import com.example.test.video_proj_example.R;
import com.example.disney.myapplication.Video;
import com.example.disney.myapplication.VideoAdapter;

import java.util.ArrayList;

/**
 * Created by disney on 9/6/16.
 */
public class ListFragment extends Fragment {

    public static ArrayList<Video> videoList;
    private View view;

    public VideoAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(VideoAdapter adapter) {
        this.adapter = adapter;
    }

    private  VideoAdapter adapter = null;

    public Button getSeeMoreBtn() {
        return seeMoreBtn;
    }

    public void setSeeMoreBtn(Button seeMoreBtn) {
        this.seeMoreBtn = seeMoreBtn;
    }

    private Button seeMoreBtn;
    private ListView listView;

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.d("myLog", "-----> ListFragment ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((VideoActivity) getActivity()).getSupportActionBar().setTitle("List");
        ((VideoActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((VideoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        view = inflater.inflate(R.layout.list_fragment, container, false);
        videoList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            videoList.add(new Video("Video", i, "Here will be the decription of video", R.mipmap.ic_video_image));
        }

        for (int j = 10; j < 17; ++j) {
            videoList.add(new Video("Prestige", j, "Here will be the decription of video", R.mipmap.ic_video_image));
        }

        listView = (ListView) view.findViewById(R.id.listview);

        seeMoreBtn = new Button(getActivity());
        seeMoreBtn.setText(R.string.button_more_info);
        seeMoreBtn.setTextSize(20);
        seeMoreBtn.setGravity(Gravity.CENTER_HORIZONTAL);
        listView.addFooterView(seeMoreBtn);


        onClickEachItem();
        onClickButton();
        return view;
    }

    private void onClickButton() {
        seeMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DetailsFragment(videoList.get(1));
                getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                        .addToBackStack("Details").commit();
            }
        });
    }

    private void onClickEachItem() {
        adapter = new VideoAdapter(this.getActivity(),R.layout.list_fragment, videoList, getFragmentManager());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new DetailsFragment(videoList.get(position));
                getFragmentManager().beginTransaction().addToBackStack("details").replace(R.id.fragment_container, fragment).commit();

            }
        });

    }


//    @Override
//    public void onPrepareOptionsMenu(Menu menu) {
//        menu.findItem(android.R.id.home).setVisible(false).setEnabled(false);
//    }
}

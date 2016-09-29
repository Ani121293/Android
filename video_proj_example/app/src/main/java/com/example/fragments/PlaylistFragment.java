package com.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.disney.videoApp.Video;
import com.example.playlist.PlaylistRecyclerAdapter;
import com.example.test.video_proj_example.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by disney on 9/26/16.
 */
public class PlaylistFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    public static ArrayList<Video> videoList;
    private RecyclerView recyclerView;

    public static PlaylistFragment newInstance(int sectionNumber) {
        PlaylistFragment fragment = new PlaylistFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaylistFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (videoList == null) {
            videoList = new ArrayList<>();
            //starting to parse 'data.xml'
            XmlPullParser parser = getResources().getXml(R.xml.data);
            try {
                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    switch (parser.getEventType()) {
                        case XmlPullParser.START_TAG:
                            if (parser.getAttributeCount() > 2) {
                                videoList.add(new Video(parser.getAttributeValue(1),
                                        parser.getAttributeValue(2), R.drawable.comedy1,
                                        android.R.drawable.btn_star_big_off));
                            }
                        default:
                            break;
                    }
                    parser.next();
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        View view = inflater.inflate(R.layout.playlist_fragment, container, false);
        System.out.println("--------PlaylistFragment " + getActivity().getSupportFragmentManager().getFragments());
        recyclerView = (RecyclerView) view.findViewById(R.id.playlist_recyclerView);
        PlaylistRecyclerAdapter adapter = new PlaylistRecyclerAdapter(this.getActivity(), videoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeView:
                if (recyclerView.getLayoutManager().getClass().equals(new LinearLayoutManager(getContext()).getClass())) {
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


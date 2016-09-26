package com.example.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.disney.myapplication.Video;
import com.example.disney.myapplication.VideoAdapter;
import com.example.test.video_proj_example.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by disney on 9/26/16.
 */
public class GenreFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<Video> videoList;
    private LayoutInflater inflater;

    public static GenreFragment newInstance(int sectionNumber) {
        GenreFragment fragment = new GenreFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public GenreFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                                        Integer.valueOf(parser.getAttributeValue(0)),
                                        parser.getAttributeValue(2), R.drawable.comedy1,
                                        android.R.drawable.btn_star_big_on));
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
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        VideoAdapter adapter = new VideoAdapter(this.getActivity(), videoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }
}


package com.example.disney.myapplication;


import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fragments.DetailsFragment;
import com.example.fragments.ListFragment;
import com.example.test.video_proj_example.R;


public class VideoActivity extends AppCompatActivity {

    static final String LIST_FRAGMENT = "list fragment";
    static final String DETAILS_FRAGMENT = "details fragment";
    static final String key = "lastSinglePaneFragment";
    public static final String PREFERENCE = "com.example.disney.myapplication.VideoActivity.PREFERENCE";
    boolean isDetailsOPened = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        boolean mDualPane = findViewById(R.id.dual_pane) != null;
        if (savedInstanceState != null) {
            isDetailsOPened = isDetailsOpened();
        }
        FragmentManager fm = getSupportFragmentManager();
        if (!mDualPane && fm.findFragmentById(R.id.fragment_container) == null) {
            if (isDetailsOPened) {
                openSinglePaneDetailFragment();
            } else {
                ListFragment singleListFragment = getDetatchedMasterFragment(false);
                fm.beginTransaction().add(R.id.fragment_container, singleListFragment, LIST_FRAGMENT).addToBackStack(LIST_FRAGMENT).commit();
            }
        }
        if (mDualPane && fm.findFragmentById(R.id.list_container) == null) {
            ListFragment listFragment = getDetatchedMasterFragment(true);
            fm.beginTransaction().add(R.id.list_container, listFragment, LIST_FRAGMENT).addToBackStack(LIST_FRAGMENT).commit();
        }
        if (mDualPane && fm.findFragmentById(R.id.details_container) == null) {
            DetailsFragment detailFragment = getDetatchedDetailFragment();
            fm.beginTransaction().add(R.id.details_container, detailFragment, DETAILS_FRAGMENT).addToBackStack(DETAILS_FRAGMENT).commit();
        }
    }

    public boolean isDetailsOpened() {
        return getSharedPreferences(PREFERENCE, MODE_PRIVATE).getBoolean(key, false);
    }


    private ListFragment getDetatchedMasterFragment(boolean popBackStack) {
        FragmentManager fmx = getSupportFragmentManager();
        ListFragment masterFragment = (ListFragment) fmx.findFragmentByTag(LIST_FRAGMENT);
        if (masterFragment == null) {
            masterFragment = new ListFragment();
        } else {
            if (popBackStack) {
                fmx.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            fmx.beginTransaction().remove(masterFragment).commit();
            fmx.executePendingTransactions();
        }
        return masterFragment;
    }

    private DetailsFragment getDetatchedDetailFragment() {
        FragmentManager fm = getSupportFragmentManager();
        DetailsFragment detailFragment = (DetailsFragment) fm.findFragmentByTag(DETAILS_FRAGMENT);
        if (detailFragment == null) {
            detailFragment = new DetailsFragment(new Video("", 0, "", 0));
        } else {
            fm.beginTransaction().remove(detailFragment).commit();
            fm.executePendingTransactions();
        }
        return detailFragment;
    }

    private void openSinglePaneDetailFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        DetailsFragment detailFragment = getDetatchedDetailFragment();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailFragment, DETAILS_FRAGMENT);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_video, menu);
        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search your lovely video");


// Listener for the query in search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
// Reload listView corresponding to filtering of query
                VideoAdapter listAdapter = ((ListFragment) getSupportFragmentManager().
                        findFragmentByTag(LIST_FRAGMENT)).getAdapter();
                listAdapter.getFilter().filter(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ListFragment singleListFragment = getDetatchedMasterFragment(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, singleListFragment, LIST_FRAGMENT).addToBackStack(LIST_FRAGMENT).commit();
        } else {
            super.onBackPressed();
        }
    }
}



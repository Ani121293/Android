package com.example.disney.videoApp;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.favorite.FavoriteRecyclerAdapter;
import com.example.fragments.FavoriteFragment;
import com.example.test.video_proj_example.R;

public class FilmGenre extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    GenrePagerAdapter mPagerAdapter;

    public static ViewPager mViewPager;
    NavigationView navigationView = null;
    public static final String FAVORIT_FRAGMENT = "favorite_fragment";
    public static final String DETAILS_FRAGMENT = "details_fragment";
    private FragmentManager fm = getSupportFragmentManager();
    public static ActionBarDrawerToggle toggle;
    public static  Integer isFavorit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);isFavorit = 2;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            mPagerAdapter = new GenrePagerAdapter(getSupportFragmentManager(), this);
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mPagerAdapter);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        System.out.println("-------------------onNavigationItemSelected");
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.playlist) {
            if (fm.findFragmentByTag(FAVORIT_FRAGMENT) != null) {
                fm.beginTransaction().remove(fm.findFragmentByTag(FAVORIT_FRAGMENT)).commit();
            }
            if (fm.findFragmentByTag(DETAILS_FRAGMENT) != null) {
                System.out.println("---------from PLAYLIST TO DETAILS");
                fm.beginTransaction().remove(fm.findFragmentByTag(DETAILS_FRAGMENT)).commit();
                fm.executePendingTransactions();
            }

            this.getSupportActionBar().setTitle("PLAYLIST");
            this.mViewPager.setVisibility(View.VISIBLE);

        } else if (id == R.id.favorits) {
            System.out.println("---------isFAvorite Selected");
            isFavorit =1;
            this.mViewPager.setVisibility(View.INVISIBLE);
            if (fm.findFragmentByTag(DETAILS_FRAGMENT) != null) {
                fm.beginTransaction().remove(fm.findFragmentByTag(DETAILS_FRAGMENT)).commit();
                fm.executePendingTransactions();
            }
            fm.beginTransaction().add(R.id.main_fragment_container, new FavoriteFragment(), FAVORIT_FRAGMENT)
                    .addToBackStack(FAVORIT_FRAGMENT).commit();
            this.getSupportActionBar().setTitle("FAVORITES");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_film_genre, menu);
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
                if (R.id.main_fragment_container != 0) {
                    FavoriteRecyclerAdapter listAdapter = ((FavoriteFragment) getSupportFragmentManager().
                            findFragmentByTag(FAVORIT_FRAGMENT)).getAdapter();
                    listAdapter.getFilter().filter(query);
                } else {
                    mPagerAdapter.getFilter().filter(query);
                }
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if(fm.getBackStackEntryCount() == 1 && 2 != isFavorit){
            System.out.println("-----------------------THE LAST FRAGMENT" + fm.getFragments());
            finish();
        }else if(1 == isFavorit){
            System.out.println("-----------------------BACK TO FAVORITE" + fm.getFragments());
            fm.popBackStackImmediate();
            if(!toggle.isDrawerIndicatorEnabled()){
                toggle.setDrawerIndicatorEnabled(true);
            }
        }else if(2 == isFavorit){
            System.out.println("------------BAck to PLAYLIST");
            FilmGenre.mViewPager.setVisibility(View.VISIBLE);
            fm.beginTransaction().remove(fm.findFragmentByTag(DETAILS_FRAGMENT)).commit();
            if(!toggle.isDrawerIndicatorEnabled()){
                toggle.setDrawerIndicatorEnabled(true);
            }
        }
        else {
            super.onBackPressed();
        };
    }
}

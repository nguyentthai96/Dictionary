package com.example.nguyenthanhthai.dictionaryandroid.presentation;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyenthanhthai.dictionaryandroid.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements ActionBar.OnNavigationListener{

    // action bar
    private ActionBar actionBar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setActionBar_();
    }

    private void setActionBar_() {
        actionBar = getSupportActionBar();

        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
       // actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);
        actionBar.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_actions, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchWordActivity.class)));

        return super.onCreateOptionsMenu(menu);
    }

    /**
     * On selecting action bar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_search:
                // search action
                return true;
//            case R.id.action_refresh:
//                // refresh
//                refreshMenuItem = item;
//                // load the data from server
//                new SyncData().execute();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return false;
    }
}

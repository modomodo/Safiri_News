package com.example.michael.the_one;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import Adapters.ViewPagerAdapter;
import tabs.SlidingTabLayout;

public class MainActivity extends ActionBarActivity {

    ViewPager pager;
    ViewPagerAdapter adapter; //Page adapter for the tabs
    SlidingTabLayout tabs;
    String Title;
    String Icon;
    CharSequence Titles[]={"News","Wikipedia","Weather"};
    int TabNos = 3;
    int imageResource;
    SharedPreferences sharedPreferences; //Storage area for app
    SharedPreferences.Editor editor; //editor for shared preferences

    //Method and toast to provide feedback for country selection
    private Toast mToast;
    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Creating The Toolbar and setting it as the Toolbar for the activity. Also getting rid of the app title for the Spinner
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        sharedPreferences= getSharedPreferences("Safiri", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        setSupportActionBar(toolbar);
        //Get the title of the app
        Title = sharedPreferences.getString("query", "Kenya");
        //Set Icon directory as string from selected value
        Icon = "drawable/flag_" + Title.toString().toLowerCase().replaceAll("\\s+", "");
        //Obtain imageResource in int to set and change ActionBar Icon
        imageResource = getResources().getIdentifier(Icon, null, getPackageName());
        getSupportActionBar().setTitle(" " + Title);
        getSupportActionBar().setIcon(imageResource);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,TabNos);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorWhite);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as a parent activity in AndroidManifest.xml is specified.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.search){
            displayPopup();
        }

        return super.onOptionsItemSelected(item);
    }

    //Instantiate the popup window and its layout
    private void displayPopup() {
        new MaterialDialog.Builder(this)
                .title("Select your country")
                .items(R.array.country_titles)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        Title = text.toString();
                        showToast("Showing content for: " + Title);
                        getSupportActionBar().setTitle(" " + Title);

                        //Set Icon directory as string from selected value
                        Icon = "drawable/flag_" + Title.toLowerCase().replaceAll("\\s+", "");
                        //Obtain imageResource in int to set and change ActionBar Icon
                        imageResource = getResources().getIdentifier(Icon, null, getPackageName());
                        getSupportActionBar().setIcon(imageResource);

                        //Save the country chosen for the fragments and other activities to customise their content
                        sharedPreferences = getSharedPreferences("Safiri", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("query", Title);
                        editor.commit();

                        //Update the viewpager which refreshes the content for all fragments
                        pager.getAdapter().notifyDataSetChanged();
                    }
                })
                .show();
    }
}

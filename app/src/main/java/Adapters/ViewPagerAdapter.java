package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.michael.the_one.NewsTab;
import com.example.michael.the_one.WeatherTab;
import com.example.michael.the_one.WikipediaTab;

/**

/**
 * Created by Michael on 01/04/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int TabNos; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mTabNos) {
        super(fm);

        this.Titles = mTitles;
        this.TabNos = mTabNos;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            NewsTab newsTab = new NewsTab();
            return newsTab;
        }
        else if (position == 1)
        {
            WikipediaTab wikipediaTab = new WikipediaTab();
            return wikipediaTab;
        }
        else{
            WeatherTab weatherTab = new WeatherTab();
            return weatherTab;
        }
    }

    public int getItemPosition(Object obj){
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return TabNos;
    }
}



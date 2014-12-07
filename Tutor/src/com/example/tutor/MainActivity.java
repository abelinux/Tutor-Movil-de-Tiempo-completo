package com.example.tutor;

import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

public class MainActivity extends ActionBarActivity  implements ActionBar.TabListener, OnPageChangeListener {


	private ViewPager mViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);
        
        mViewPager.setOnPageChangeListener(this);
        
        ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("BIENVENIDO");
		actionBar.setSubtitle("TUTOR MOVIL DE TIEMPO COMPLETO");
	    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		Tab tab = actionBar.newTab().setText(R.string.main_mensajes).setTabListener(this);
		actionBar.addTab(tab);

		tab = actionBar.newTab().setText(R.string.main_mejoras).setTabListener(this);
		actionBar.addTab(tab);

		tab = actionBar.newTab().setText(R.string.main_monitoreo).setTabListener(this);
		actionBar.addTab(tab);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int arg0) {
			switch (arg0) {
	            case 0:
	                return new Fragment_Mensajes();
	            case 1:
	                return new Fragment_Mejoras();
	            case 2:
	                return new Fragment_Monitoreo();
	            default:
	            	return null;
			}
		}

		public int getCount() {
			return 3;
		}

    }






	/** INTERFACE ONPAGECHANGELISTENER **/
	
	public void onPageSelected(int arg0) {
		getSupportActionBar().setSelectedNavigationItem(arg0);
	}
	
	public void onPageScrollStateChanged(int arg0) {
		
	}
	
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}
	
	
	/** INTERFACE ACTIONBAR.TABLISTENER **/
	
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		mViewPager.setCurrentItem(arg0.getPosition());
	}
	
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		
	}
	
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
	}
}

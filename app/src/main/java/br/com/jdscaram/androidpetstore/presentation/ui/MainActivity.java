package br.com.jdscaram.androidpetstore.presentation.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import br.com.jdscaram.androidpetstore.R;
import br.com.jdscaram.androidpetstore.presentation.ui.search.SearchFragment;
import br.com.jdscaram.androidpetstore.presentation.ui.home.HomeFragment;
import br.com.jdscaram.androidpetstore.presentation.ui.register.RegisterFragment;

/**
 * {Created by Jonatas Caram on 29/05/2017}.
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mViewPager;
    private FloatingActionButton mFab;
    private ImageButton mEdit, mHistory;
    private PagerAdapter mAdapter;
    private FrameLayout mFrameLayout;
    private int oldPosition;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mHistory = (ImageButton) findViewById(R.id.history);
        mEdit = (ImageButton) findViewById(R.id.edit);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_layout);

        afterViews();
    }

    private void afterViews() {

        initFragments();
        mEdit.setOnClickListener(this);
        mHistory.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(this);
    }

    private void initFragments() {
        mAdapter = new PagerAdapter(getSupportFragmentManager());


        mAdapter.addFrag(new PagerAdapter.PagerItem(new HomeFragment(),
                getString(R.string.tab_home)));


        mAdapter.addFrag(new PagerAdapter.PagerItem(new SearchFragment(),
                getString(R.string.tab_history)));


        mFragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position > oldPosition) {
            //Scrolling to the right
            oldPosition = position;
            mViewPager.setCurrentItem(2, true);
        } else if (position < oldPosition) {
            //Scrolling to the left
            oldPosition = position;
            mViewPager.setCurrentItem(0, true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
                if (mFrameLayout.getVisibility() == View.VISIBLE)
                    showRegister(false);
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.history:
                if (mFrameLayout.getVisibility() == View.VISIBLE)
                    showRegister(false);
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.fab:
                if (mViewPager.getVisibility() == View.VISIBLE)
                    showRegister(true);

                Fragment fragment = mFragmentManager.findFragmentById(R.id.frame_layout);
                if (fragment == null)
                    mFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, new RegisterFragment())
                            .commit();

                break;
        }
    }

    private void showRegister(boolean show) {
        if (show) {
            mViewPager.setVisibility(View.GONE);
            mFrameLayout.setVisibility(View.VISIBLE);
        } else {
            mViewPager.setVisibility(View.VISIBLE);
            mFrameLayout.setVisibility(View.GONE);
        }
    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        private final List<PagerItem> mPagerItems = new ArrayList<>();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mPagerItems.get(position).fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mPagerItems.get(position).title;
        }

        @Override
        public int getItemPosition(Object object) {
            for (int i = 0; i < mPagerItems.size(); i++) {
                PagerItem pager = mPagerItems.get(i);
                if (pager.getFragment() == object)
                    return i;
            }
            return -1;
        }

        @Override
        public int getCount() {
            return mPagerItems.size();
        }

        public PagerItem getPagerItem(int positon) {
            if (mPagerItems.size() > positon)
                return mPagerItems.get(positon);

            return null;
        }

        void addFrag(PagerItem item) {
            mPagerItems.add(item);
        }

        static class PagerItem {
            private Fragment fragment;
            private String title;
            private int icon;

            PagerItem(Fragment fragment, String title) {
                this.fragment = fragment;
                this.title = title;
            }

            public PagerItem(Fragment fragment, String title, int icon) {
                this.fragment = fragment;
                this.title = title;
                this.icon = icon;
            }

            public Fragment getFragment() {
                return fragment;
            }

            public void setFragment(Fragment fragment) {
                this.fragment = fragment;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getIcon() {
                return icon;
            }

            public void setIcon(int icon) {
                this.icon = icon;
            }

        }

    }


}

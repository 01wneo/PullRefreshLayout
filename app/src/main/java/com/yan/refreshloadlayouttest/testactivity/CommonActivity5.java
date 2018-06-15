package com.yan.refreshloadlayouttest.testactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.yan.pullrefreshlayout.PullRefreshLayout;
import com.yan.refreshloadlayouttest.R;

import java.util.ArrayList;
import java.util.List;

public class CommonActivity5 extends BaseActivity {
    private List<SimpleItem> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity5);
        final ViewPager vp = findViewById(R.id.vp_pager);
        final PullRefreshLayout prl = findViewById(R.id.prl);


        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                prl.setTargetView(getTargetView(vp).getView());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DataFragment());
        fragments.add(new DataFragment());
        fragments.add(new DataFragment());
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        prl.setOnRefreshListener(new PullRefreshLayout.OnRefreshListenerAdapter() {
            @Override
            public void onRefresh() {
                if (getTargetView(vp) instanceof DataFragment) {
                    ((DataFragment) getTargetView(vp)).reload();
                    prl.refreshComplete();
                }
            }
        });

        prl.postDelayed(new Runnable() {
            @Override
            public void run() {
                prl.setTargetView(getTargetView(vp).getView());
            }
        }, 250);

    }

    private Fragment getTargetView(ViewPager vp) {
        return getSupportFragmentManager().findFragmentByTag(makeFragmentName(vp.getId(), vp.getCurrentItem()));
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }
}
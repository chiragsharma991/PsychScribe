package com.psychscribe.developmentalHistory;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityDevelopmentHistoryBinding;
import com.psychscribe.developmentalHistory.fragment.ChildCareHistoryFragment;
import com.psychscribe.developmentalHistory.fragment.DevelopmentMilestonesFragment;
import com.psychscribe.developmentalHistory.fragment.EarlyHistoryFragment;
import com.psychscribe.developmentalHistory.fragment.HabitsFragment;
import com.psychscribe.developmentalHistory.fragment.MedicalHistoryFragment;
import com.psychscribe.patients.fragment.CollateralFragment;
import com.psychscribe.patients.fragment.EmergencyFragment;
import com.psychscribe.patients.fragment.InsuranceFragment;
import com.psychscribe.patients.fragment.PersonalFragment;
import com.psychscribe.utiz.AnimationUtils;
import com.psychscribe.utiz.AppUtils;

public class DevelopmentHistoryActivity extends BaseActivity {

    ActivityDevelopmentHistoryBinding binding;
    private AnimationUtils animationUtils;
    private EarlyHistoryFragment earlyHistoryFragment;
    private DevelopmentMilestonesFragment developmentMilestonesFragment;
    private ChildCareHistoryFragment childCareHistoryFragment;
    private HabitsFragment habitsFragment;
    private MedicalHistoryFragment medicalHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_development_history);
        binding.setSelectedPosition(0);
        setupSlideWindowAnimations(Gravity.BOTTOM, Gravity.BOTTOM);
        init();
        setPagerAdapter();
    }

    private void init() {
        setSupportActionBar(binding.includeToolbar.toolbar);
        binding.includeToolbar.toolbarTitle.setText("Development History");
        binding.includeToolbar.toolbar.setNavigationIcon(R.drawable.ic_back);
        setTitle("");
        initTabAnim();
        binding.includeToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initTabAnim() {
        animationUtils = new AnimationUtils();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                animationUtils.animateIn(binding.linTab);
            }
        }, ANIM_TIME);
    }

    private void setPagerAdapter(){
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 5;
            }
            @Override
            public Fragment getItem(int position) {
                switch (position){
                    case 0:
                        earlyHistoryFragment = new EarlyHistoryFragment();
                        return earlyHistoryFragment;
                    case 1:
                        developmentMilestonesFragment = new DevelopmentMilestonesFragment();
                        return developmentMilestonesFragment;
                    case 2:
                        medicalHistoryFragment = new MedicalHistoryFragment();
                        return medicalHistoryFragment;
                    case 3:
                        childCareHistoryFragment = new ChildCareHistoryFragment();
                        return childCareHistoryFragment;
                    case 4:
                        habitsFragment = new HabitsFragment();
                        return habitsFragment;
                }
                return null;
            }
            @Override
            public Parcelable saveState() {return null;}
        };
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(4);
        binding.viewPager.setCurrentItem(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_patient, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_next:
                changeFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void changeFragment() {
        binding.viewPager.setCurrentItem((binding.viewPager.getCurrentItem()+1));
        switch (binding.viewPager.getCurrentItem()){
            case 0:
                onClickTab(binding.imgEarlyHistory);
                break;
            case 1:
                onClickTab(binding.imgDevelopment);
                break;
            case 2:
                onClickTab(binding.imgMedical);
                break;
            case 3:
                onClickTab(binding.imgChildCare);
                break;
            case 4:
                onClickTab(binding.imgHabit);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClickTab(View view){
        switch (view.getTag().toString()){
            case "0":
                binding.setSelectedPosition(0);
                binding.viewPager.setCurrentItem(0);
                break;
            case "1":
                binding.viewPager.setCurrentItem(1);
                binding.setSelectedPosition(1);
                break;
            case "2":
                binding.viewPager.setCurrentItem(2);
                binding.setSelectedPosition(2);
                break;
            case "3":
                binding.viewPager.setCurrentItem(3);
                binding.setSelectedPosition(3);
                break;
            case "4":
                binding.viewPager.setCurrentItem(4);
                binding.setSelectedPosition(4);
                break;

        }
    }

}

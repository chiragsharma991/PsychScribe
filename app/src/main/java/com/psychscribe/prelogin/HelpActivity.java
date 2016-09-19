package com.psychscribe.prelogin;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityHelpBinding;
import com.psychscribe.databinding.ActivityPlanBinding;
import com.psychscribe.inappbill.util.IabHelper;
import com.psychscribe.inappbill.utils.Navigator;
import com.psychscribe.prelogin.fragment.HelpPagerFragment;
import com.psychscribe.prelogin.fragment.PlanPreviewFragment;
import com.psychscribe.prelogin.model.SignInModel;
import com.psychscribe.profile.ProfileActivity;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.AnimationUtils;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.IntroPageTransformer;
import com.psychscribe.utiz.ZoomOutPageTransformer;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 21/7/16.
 */
public class HelpActivity extends BaseActivity {

    private ActivityHelpBinding binding;
    private final static String Position = "pos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        mIsFromPreLogin = true;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_help);
        binding.setInitPos(0);
        setupExlpodeWindowAnimations(Gravity.RIGHT);
        setPagerAdapter();
        setPagerListner();
    }

    private void setPagerListner() {
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                    changeIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void changeIndicator(int pos) {
        switch (pos){
            case 0:
                AnimationUtils.animateScaleOut(binding.txtIndicatorOne);
                binding.setInitPos(0);
              break;
            case 1:
                AnimationUtils.animateScaleOut(binding.txtIndicatorTwo);
                binding.setInitPos(1);
                break;
            case 2:
                AnimationUtils.animateScaleOut(binding.txtIndicatorThree);
                binding.setInitPos(2);
                break;
        }
    }

    private void setPagerAdapter(){
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }
            @Override
            public Fragment getItem(int position) {
                HelpPagerFragment fragment = new HelpPagerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Position, position);
                fragment.setArguments(bundle);
                return fragment;
            }
            @Override
            public Parcelable saveState() {return null;}
        };
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setPageTransformer(true, new IntroPageTransformer());
        binding.viewPager.setCurrentItem(0);
    }

    protected void setupExlpodeWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
            getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    AnimationUtils.animateIn(binding.linIndicator);
                }
            });
        }else{
            AnimationUtils.animateIn(binding.linIndicator);
        }
    }

    public void onGetStartedClick(View view){
        SharedPreferenceUtil.putValue(Constants.KEY_IS_HELP_SCREEN_DONE, true);
        SharedPreferenceUtil.save();
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra(Constants.ExtraFromHelp, true);
        moveActivity(intent, this);
        finishWithHandler();
    }

    public void onLoginClick(View view){
        SharedPreferenceUtil.putValue(Constants.KEY_IS_HELP_SCREEN_DONE, true);
        SharedPreferenceUtil.save();
        moveActivity(new Intent(this, SignInActivity.class), this);
        finishWithHandler();
    }
}

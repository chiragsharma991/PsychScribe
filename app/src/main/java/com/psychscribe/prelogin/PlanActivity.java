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
import android.transition.Slide;
import android.view.Gravity;

import com.psychscribe.R;
import com.psychscribe.base.BaseActivity;
import com.psychscribe.databinding.ActivityPlanBinding;
import com.psychscribe.inappbill.util.IabHelper;
import com.psychscribe.inappbill.utils.Navigator;
import com.psychscribe.patients.HomeActivity;
import com.psychscribe.prelogin.fragment.PlanPreviewFragment;
import com.psychscribe.prelogin.model.SignInModel;
import com.psychscribe.profile.ProfileActivity;
import com.psychscribe.rest.APIRequest;
import com.psychscribe.rest.ResponseInterface;
import com.psychscribe.storage.SharedPreferenceUtil;
import com.psychscribe.utiz.AppUtils;
import com.psychscribe.utiz.Constants;
import com.psychscribe.utiz.ZoomOutPageTransformer;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ubuntu on 21/7/16.
 */
public class PlanActivity extends BaseActivity {

    private ActivityPlanBinding binding;
    private final static String Position = "pos";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statusBarColor(ContextCompat.getColor(this, R.color.colorAppAccent));
        mIsFromPreLogin = true;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plan);
        setupExlpodeWindowAnimations(Gravity.RIGHT);
        setPagerAdapter();
    }

    private void setPagerAdapter(){
        final FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return 3;
            }
            @Override
            public Fragment getItem(int position) {
                PlanPreviewFragment fragment = new PlanPreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Position, position);
                fragment.setArguments(bundle);
                return fragment;
            }
            @Override
            public Parcelable saveState() {return null;}
        };
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        binding.indicator.setViewPager(binding.viewPager);
        binding.viewPager.setCurrentItem(0);
    }

    protected void setupExlpodeWindowAnimations(int gravity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(gravity);
            slide.setDuration(ANIM_TIME);
            getWindow().setReturnTransition(slide);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Navigator.REQUEST_PASSPORT_PURCHASE == requestCode) {
            if (RESULT_OK == resultCode) {
                successfulPurchase();
            } else {
                failedPurchase();
            }
        }
    }

    private void successfulPurchase() {
        showSnackbar(binding.getRoot(), getString(R.string.congratus_purchase));
        SharedPreferenceUtil.putValue(Constants.KEY_IN_APP_PURCHASE, "1");
        SharedPreferenceUtil.save();
    }

    private void failedPurchase() {
        if (IabHelper.isAlreadyPurchesd) {
            showSnackbar(binding.getRoot(), getString(R.string.item_already_owned));
            IabHelper.isAlreadyPurchesd = false;
        } else if (IabHelper.isInAppDialogClose) {
            showSnackbar(binding.getRoot(), getString(R.string.press_back_cancel_dialog));
            IabHelper.isInAppDialogClose = false;
        } else {
            showSnackbar(binding.getRoot(), getString(R.string.failed_purchase));
        }
        attemptRegistration();
    }

    private void attemptRegistration() {
        showProgressDialog();
        ResponseInterface userDataRequest = APIRequest.provideInterface();
        HashMap<String, String> data = new HashMap<>();
        data.put(Constants.Name, getIntent().getExtras().getString(Constants.ExtraName));
        data.put(Constants.Email, getIntent().getExtras().getString(Constants.ExtraEmail));
        data.put(Constants.Password, getIntent().getExtras().getString(Constants.ExtraPassword));
        data.put(Constants.DeviceToken, "619");
        data.put(Constants.DeviceType, "android");
        data.put(Constants.PackageId, getPackageName());
        data.put(Constants.Month, "1");
        data.put(Constants.Amount, "100");
        data.put(Constants.PlanStartDate, AppUtils.dateConvert("MM-dd-yyyy", "yyyy-MM-dd", AppUtils.getCurrantDate()));
        data.put(Constants.IsSuccess, "true");
        data.put(Constants.TransactionId, "");
        data.put(Constants.PaymentId, "");
        data.put(Constants.ResponseMessage, "");
        showErrorLog("sign Up : "+data.toString());

        Call<SignInModel> getUserCall = userDataRequest.signUp(data);

        getUserCall.enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call,
                                   Response<SignInModel> response) {
                stopProgressDialog();
                if (response.isSuccessful()) {
                    showErrorLog(response.body().toString());
                    SharedPreferenceUtil.putValue(Constants.KEY_IS_LOGIN, true);
                    SharedPreferenceUtil.putValue(Constants.KEY_EMAIL, response.body().getData().get(0).getEmail());
                    SharedPreferenceUtil.putValue(Constants.KEY_IS_PAID, response.body().getData().get(0).getIsPaid());
                    SharedPreferenceUtil.putValue(Constants.KEY_PASSWORD, response.body().getData().get(0).getPasscode());
                    SharedPreferenceUtil.putValue(Constants.KEY_USER_ID, response.body().getData().get(0).getId());
                    SharedPreferenceUtil.putValue(Constants.KEY_USER_NAME, response.body().getData().get(0).getName());
                    SharedPreferenceUtil.putValue(Constants.KEY_TOKEN, response.body().getData().get(0).getToken());
                    SharedPreferenceUtil.save();
                    Intent intent = new Intent(PlanActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    moveActivity(intent, PlanActivity.this);
                }
            }
            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {
                showErrorLog("Error "+t.getMessage());
                stopProgressDialog();
            }
        });
    }

}

package com.example.surveyappwithfragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        SurveyFragment.ButtonClickedListener,
        ResultsFragment.ResultButtonClickedListener{

    private static final String BUNDLE_KEY_SURVEY = "SURVEY INFO";

    private static final String TAG_SURVEY_FRAG = "SURVEY FRAG";
    private static final String TAG_RESULTS_FRAG = "RESULTS FRAG";
    private static final String TAG_CONFIGURE_FRAG = "CONFIGURE FRAG";

    Survey mSurvey;
    int mCountOne = 0;
    int mCountTwo = 0;

    private static final String TAG = "MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            Log.d(TAG, "onCreate has no instance state. Setting up ArrayList, adding List Fragment and Add Fragment");

            mSurvey = new Survey("Do you like ice cream?", "Yes","No",0,0);

            SurveyFragment surveyFragment = SurveyFragment.newInstance(mSurvey);
            ResultsFragment resultsFragment = ResultsFragment.newInstance(mSurvey);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.add(R.id.survey_container, surveyFragment);
            ft.add(R.id.results_container, resultsFragment);

            ft.commit();
        } else {
            mSurvey = savedInstanceState.getParcelable(BUNDLE_KEY_SURVEY);
            Log.d(TAG, "onCreate has saved instance state Survey = " + mSurvey);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outBundle){
        super.onSaveInstanceState(outBundle);
        outBundle.putParcelable(BUNDLE_KEY_SURVEY, mSurvey);
    }

    @Override
    public void buttonClicked(View button){
        int buttonID = button.getId();
        switch (buttonID){
            case R.id.yes_button:
                mCountOne = mSurvey.getCountOne();
                mCountOne += 1;
                mSurvey.setCountOne(mCountOne);
            case R.id.no_button:
                mCountTwo = mSurvey.getCountTwo();
                mCountTwo += 1;
                mSurvey.setCountTwo(mCountTwo);
        }

        FragmentManager fm = getSupportFragmentManager();
        ResultsFragment resultFragment = (ResultsFragment) fm.findFragmentByTag(TAG_RESULTS_FRAG);
        resultFragment.notifyCountChange(mSurvey);
    }


    @Override
    public void resultClick(View button){

    }
}

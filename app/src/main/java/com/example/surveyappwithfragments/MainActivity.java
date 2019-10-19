package com.example.surveyappwithfragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        SurveyFragment.ButtonClickedListener,
        ResultsFragment.ResultButtonClickedListener,
        ConfigurationFragment.MarkSurveyDoneListener { //listener interfaces which allow communication with the main activity

    private static final String BUNDLE_KEY_SURVEY = "SURVEY INFO"; //bundle for instance state saving

    private static final String TAG_SURVEY_FRAG = "SURVEY FRAG"; //tags for fragment management
    private static final String TAG_RESULTS_FRAG = "RESULTS FRAG";
    private static final String TAG_CONFIGURE_FRAG = "CONFIGURE FRAG";

    Survey mSurvey;
    int mCountOne = 0; //variables for passing counts to Survey instance
    int mCountTwo = 0;

    private static final String TAG = "MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            Log.d(TAG, "onCreate has no instance state. Setting up ArrayList, adding List Fragment and Add Fragment");

            mSurvey = new Survey("Do you like ice cream?", "Yes","No",0,0); //sample survey

            SurveyFragment surveyFragment = SurveyFragment.newInstance(mSurvey); //instantiate fragments
            ResultsFragment resultsFragment = ResultsFragment.newInstance(mSurvey);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ft.add(R.id.survey_container, surveyFragment, TAG_SURVEY_FRAG); //I forgot the tags here originally, which caused problems later on
            ft.add(R.id.results_container, resultsFragment, TAG_RESULTS_FRAG);

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
        int buttonID = button.getId(); //listener sends buttons; this translates into an int
        switch (buttonID) { //depending on button id, add to survey answer counts
            case R.id.yes_button:
                mCountOne = mSurvey.getCountOne();
                mCountOne += 1;
                mSurvey.setCountOne(mCountOne);
                break;
            case R.id.no_button:
                mCountTwo = mSurvey.getCountTwo();
                mCountTwo += 1;
                mSurvey.setCountTwo(mCountTwo);
                break;
            case R.id.edit_button:
                edit();
                break;

        }

        FragmentManager fm = getSupportFragmentManager();
        ResultsFragment resultFragment = (ResultsFragment) fm.findFragmentByTag(TAG_RESULTS_FRAG);
        resultFragment.notifyCountChange(mSurvey);
    }

    private void edit() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ConfigurationFragment configurationFragment = ConfigurationFragment.newInstance(mSurvey);

        ft.replace(android.R.id.content, configurationFragment, TAG_CONFIGURE_FRAG); //regardless of fragments on screen, sends configure fragment to display

        ft.addToBackStack(TAG_CONFIGURE_FRAG); //allows back button

        ft.commit();
    }


    @Override
    public void resetClick(){
        mSurvey.setCountOne(0); //sets numbers to 0
        mSurvey.setCountTwo(0);

        FragmentManager fm = getSupportFragmentManager();
        ResultsFragment resultFragment = (ResultsFragment) fm.findFragmentByTag(TAG_RESULTS_FRAG);
        resultFragment.notifyCountChange(mSurvey); //updates in fragment
    }

    @Override
    public void surveyDone(Survey survey){
        mSurvey = survey;
        FragmentManager fm = getSupportFragmentManager();
        SurveyFragment surveyFragment = (SurveyFragment) fm.findFragmentByTag(TAG_SURVEY_FRAG);
        surveyFragment.setSurveyInfo(mSurvey); //updates text in survey fragment

        ResultsFragment resultsFragment = (ResultsFragment) fm.findFragmentByTag(TAG_RESULTS_FRAG);
        resultsFragment.notifyCountChange(mSurvey); //updates counts in results fragment

        FragmentTransaction ft = fm.beginTransaction();
        ConfigurationFragment configurationFragment = (ConfigurationFragment) fm.findFragmentByTag(TAG_CONFIGURE_FRAG);
        if(configurationFragment != null){
            ft.remove(configurationFragment); //removes configuration fragment
        }

        ft.commit();
    }
}

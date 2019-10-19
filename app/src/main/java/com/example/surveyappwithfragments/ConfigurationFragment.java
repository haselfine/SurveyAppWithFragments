package com.example.surveyappwithfragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationFragment extends Fragment {

    interface MarkSurveyDoneListener {
        void surveyDone(Survey survey); //interface method to respond to "Ready" click
    }

    private MarkSurveyDoneListener mSurveyDoneListener; //instantiation of listener


    private static final String TAG = "CONFIGURATION FRAGMENT";
    private static final String ARGS_CONFIGURATION = "CONFIGURATION ARGUMENTS";

    EditText mUserQuestion;
    EditText mUserAnswerOne;
    EditText mUserAnswerTwo;

    Button mReadyButton;


    public static ConfigurationFragment newInstance(Survey survey) {
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_CONFIGURATION, survey); //sends data from survey
        final ConfigurationFragment fragment = new ConfigurationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach");

        if (context instanceof MarkSurveyDoneListener) { //attaches listener
            mSurveyDoneListener = (MarkSurveyDoneListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MarkSurveyDoneListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_configuration, container, false);

        if(getArguments() != null && getArguments().getParcelable(ARGS_CONFIGURATION)!= null){ //as long as arguments/bundle have information...

            final Survey survey = getArguments().getParcelable(ARGS_CONFIGURATION);

            mUserQuestion = view.findViewById(R.id.user_question);
            mUserAnswerOne = view.findViewById(R.id.user_answer_one);
            mUserAnswerTwo = view.findViewById(R.id.user_answer_two);
            mReadyButton = view.findViewById(R.id.ready_button);

            configureSurvey(survey); //sends names to edit texts to make things clearer for user

        } else {
            Log.d(TAG, "Did not receive survey.");
        }
        // Inflate the layout for this fragment
        return view;
    }

    private void configureSurvey(final Survey survey) {
        final Survey newSurvey = survey;

        mUserQuestion.setText(survey.getSurveyQuestion());
        mUserAnswerOne.setText(survey.getOptionOne());
        mUserAnswerTwo.setText(survey.getOptionTwo());



        mReadyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newSurvey.setSurveyQuestion(mUserQuestion.getText().toString()); //after user says they're ready, takes info from edit texts, adds to Survey instance
                newSurvey.setOptionOne(mUserAnswerOne.getText().toString());
                newSurvey.setOptionTwo(mUserAnswerTwo.getText().toString());
                newSurvey.setCountOne(0); //clear counts for new survey
                newSurvey.setCountTwo(0);
                mSurveyDoneListener.surveyDone(newSurvey); //sends new survey to main activity
            }
        });
    }

}

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
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment implements View.OnClickListener{

    public interface ButtonClickedListener{
        void buttonClicked(View button);
    }

    private ButtonClickedListener mButtonClickedListener;

    private static final String TAG = "SURVEY FRAGMENT";
    private static final String ARGS_SURVEY = "SURVEY ARGUMENTS";

    Button mYesButton;
    Button mNoButton;
    Button mEditButton;

    TextView mQuestionTextView;

    Survey mSurvey;


    public static SurveyFragment newInstance(Survey survey) {
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_SURVEY, survey);
        final SurveyFragment fragment = new SurveyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        Log.d(TAG, "onAttach");

        super.onAttach(context);

        if(context instanceof ButtonClickedListener){
            mButtonClickedListener = (ButtonClickedListener) context;
            Log.d(TAG, "On attach configured listener " + mButtonClickedListener);
        } else {
            throw new RuntimeException(context.toString() + " must implement ButtonSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_survey, container, false);

        mQuestionTextView = view.findViewById(R.id.question_textview);

        mYesButton = view.findViewById(R.id.yes_button);
        mYesButton.setOnClickListener(this);

        mNoButton = view.findViewById(R.id.no_button);
        mNoButton.setOnClickListener(this);

        mEditButton = view.findViewById(R.id.edit_button);
        mEditButton.setOnClickListener(this);

        mSurvey = new Survey();

        if(getArguments() != null){
            mSurvey = getArguments().getParcelable(ARGS_SURVEY);
        }

        if(mSurvey != null){
            setSurveyInfo(mSurvey);
        }

        return view;
    }
    @Override
    public void onClick(View button){
        mButtonClickedListener.buttonClicked(button);
    }

    public void setSurveyInfo(Survey mSurvey){
        mQuestionTextView.setText(mSurvey.getSurveyQuestion());
        mYesButton.setText(mSurvey.getOptionOne());
        mNoButton.setText(mSurvey.getOptionTwo());
    }
}

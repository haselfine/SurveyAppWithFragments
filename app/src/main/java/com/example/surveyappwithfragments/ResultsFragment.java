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

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment implements View.OnClickListener{


    public interface ResultButtonClickedListener{
        void resultClick(View button);
    }

    private ResultButtonClickedListener mResultButtonClickedListener;

    private static final String TAG = "RESULTS FRAGMENT";
    private static final String ARGS_RESULTS = "RESULT ARGUMENTS";

    TextView mOptionOne;
    TextView mOptionTwo;
    TextView mCountOne;
    TextView mCountTwo;

    Button mResetButton;

    Survey mSurvey;

    public static ResultsFragment newInstance(Survey survey) {
        final Bundle args = new Bundle();
        args.putParcelable(ARGS_RESULTS, survey);
        final ResultsFragment fragment = new ResultsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        Log.d(TAG, "onAttach");

        super.onAttach(context);

        if(context instanceof ResultsFragment.ResultButtonClickedListener){
            mResultButtonClickedListener = (ResultsFragment.ResultButtonClickedListener) context;
            Log.d(TAG, "On attach configured listener " + mResultButtonClickedListener);
        } else {
            throw new RuntimeException(context.toString() + " must implement ButtonSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_results, container, false);

        mOptionOne = view.findViewById(R.id.optionOneLabel);
        mOptionTwo = view.findViewById(R.id.optionTwoLabel);
        mCountOne = view.findViewById(R.id.countOneLabel);
        mCountTwo = view.findViewById(R.id.countTwoLabel);

        mResetButton = view.findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(this);

        mSurvey = new Survey();

        if(getArguments() != null){
            mSurvey = getArguments().getParcelable(ARGS_RESULTS);
        }

        if(mSurvey != null){
            setSurveyInfo(mSurvey);
        }

        return view;
    }
    @Override
    public void onClick(View button){
        mResultButtonClickedListener.resultClick(button);
    }

    private void setSurveyInfo(Survey survey) {
        mOptionOne.setText(survey.getOptionOne());
        mOptionTwo.setText(survey.getOptionTwo());
        mCountOne.setText(Integer.toString(survey.getCountOne()));
        mCountTwo.setText(Integer.toString(survey.getCountTwo()));
    }

    public void notifyCountChange(Survey survey){
        setSurveyInfo(survey);
    }
}

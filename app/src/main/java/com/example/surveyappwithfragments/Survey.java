package com.example.surveyappwithfragments;

import android.os.Parcel;
import android.os.Parcelable;

public class Survey implements Parcelable {

    private String surveyQuestion;
    private String optionOne;
    private String optionTwo;
    private int countOne;
    private int countTwo;




    public Survey(String surveyQuestion, String optionOne, String optionTwo,
                  int countOne, int countTwo){
        this.surveyQuestion = surveyQuestion;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.countOne = countOne;
        this.countTwo = countTwo;
    }

    public Survey(){}

    public String getSurveyQuestion(){
        return surveyQuestion;
    }

    public void setSurveyQuestion(String surveyQuestion){
        this.surveyQuestion = surveyQuestion;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public int getCountOne() {
        return countOne;
    }

    public void setCountOne(int countOne) {
        this.countOne = countOne;
    }

    public int getCountTwo() {
        return countTwo;
    }

    public void setCountTwo(int countTwo) {
        this.countTwo = countTwo;
    }


    public int describeContents(){
        return 0;
    }

    protected Survey(Parcel in) {
        surveyQuestion = in.readString();
        optionOne = in.readString();
        optionTwo = in.readString();
        countOne = in.readInt();
        countTwo = in.readInt();
    }

    public static final Creator<Survey> CREATOR = new Creator<Survey>() {
        @Override
        public Survey createFromParcel(Parcel in) {
            return new Survey(in);
        }

        @Override
        public Survey[] newArray(int size) {
            return new Survey[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(surveyQuestion);
        dest.writeString(optionOne);
        dest.writeString(optionTwo);
        dest.writeInt(countOne);
        dest.writeInt(countTwo);
    }
}

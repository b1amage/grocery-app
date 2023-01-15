package com.example.myapplication.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends BaseAdapter {
    private List<Feedback> feedbacks;

    public FeedbackAdapter(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    @Override
    public int getCount() {
        return feedbacks.size();
    }

    @Override
    public Object getItem(int i) {
        return feedbacks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View feedbackView;
        if (convertView == null) {
            feedbackView = View.inflate(parent.getContext(), R.layout.feedback_row, null);
        } else feedbackView = convertView;

        Feedback feedback = (Feedback) getItem(i);
        ((TextView) feedbackView.findViewById(R.id.feedback_email)).setText(feedback.getEmail());
        ((TextView) feedbackView.findViewById(R.id.feedback_title)).setText(feedback.getTitle());
        ((TextView) feedbackView.findViewById(R.id.feedback_description)).setText(feedback.getDescription());
        return feedbackView;
    }
}

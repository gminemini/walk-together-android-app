package com.custu.project.walktogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.NumberQuestion;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.StoreAnswerTmse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class QuestionOneActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Spinner answerSpinner;
    private TextView titleTextView;
    private ArrayList<String> answerArray = new ArrayList<String>();
    private Button nextBtn;

    private NumberQuestion numberQuestion;
    private JsonObject answerTmse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_one);
        getData();
        setUI();
        setListener();
        createSpinnerData();
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, answerArray);
        answerSpinner.setAdapter(adapterArray);
    }

    @Override
    public void initValue() {

    }

    public void setUI() {
        answerSpinner = (Spinner) findViewById(R.id.answer_day);
        nextBtn = (Button) findViewById(R.id.next);
        titleTextView = findViewById(R.id.title);

        titleTextView.setText(numberQuestion.getQuestion().getTitle());
    }

    @Override
    public void getData() {
        answerTmse = new JsonObject();
        numberQuestion = EvaluationModel.getInstance().getEvaluationByNumber("1", QuestionOneActivity.this);
    }

    @Override
    public void initProgressDialog() {

    }

    private void createSpinnerData() {
        answerArray.add("วันอาทิตย์");
        answerArray.add("วันจันทร์");
        answerArray.add("วันอังคาร");
        answerArray.add("วันพุธ");
        answerArray.add("วันพฤหัสบดี");
        answerArray.add("วันศุกร์");
        answerArray.add("วันเสาร์");
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                StoreAnswerTmse.getInstance().storeAnswer("no1", numberQuestion.getQuestion().getId(), answerSpinner.getSelectedItem().toString());
                Intent intent = new Intent(QuestionOneActivity.this, QuestionTwoActivity.class);
                startActivity(intent);
            }

        }
    }
}

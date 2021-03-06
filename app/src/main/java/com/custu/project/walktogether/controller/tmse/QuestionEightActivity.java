package com.custu.project.walktogether.controller.tmse;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.adapter.HintAdapter;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.ConfigService;
import com.custu.project.walktogether.util.DialogUtil;
import com.custu.project.walktogether.util.StoreAnswerTmse;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class QuestionEightActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Spinner answerSpinnerOne;
    private Spinner answerSpinnerTwo;
    private Spinner answerSpinnerThree;
    private Spinner answerSpinnerFour;
    private Spinner answerSpinnerFive;
    private List<String> answerArray = new ArrayList<String>();
    private Button nextBtn;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.question_eight);
        getData();
        setUI();
        setListener();
        createSpinnerData();
        setAdapter();
        countDownTime();
    }

    private CountDownTimer countDownTimer;

    private void countDownTime() {
        long timeInterval = ConfigService.TIME_INTERVAL;
        final int[] time = {31};
        final ProgressBar progress;
        progress = findViewById(R.id.progress);
        progress.setMax(time[0]);
        progress.setProgress(time[0]);
        countDownTimer = new CountDownTimer(timeInterval, 1000) {
            public void onTick(long millisUntilFinished) {
                progress.setProgress(--time[0]);
            }

            public void onFinish() {
                progress.setProgress(0);
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no8", question.getId(), "");
                Intent intent = new Intent(QuestionEightActivity.this, QuestionNineActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        DialogUtil.getInstance().showDialogExitEvaluation(this);
    }

    @Override
    public void initValue() {

    }

    public void setUI() {
        answerSpinnerOne = (Spinner) findViewById(R.id.answer_day_one);
        answerSpinnerTwo = (Spinner) findViewById(R.id.answer_day_two);
        answerSpinnerThree = (Spinner) findViewById(R.id.answer_day_three);
        answerSpinnerFour = (Spinner) findViewById(R.id.answer_day_four);
        answerSpinnerFive = (Spinner) findViewById(R.id.answer_day_five);
        nextBtn = (Button) findViewById(R.id.next);
        TextView title = findViewById(R.id.title);
        TextView description = findViewById(R.id.description);
        title.setText("(8) " + question.getTitle());
        description.setText(question.getDescription());
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance().getEvaluationByNumber("8", QuestionEightActivity.this).getQuestion();
    }

    private boolean isCorrect() {
        boolean isCorrect = true;
        if (!answerSpinnerOne.getSelectedItem().toString().equalsIgnoreCase(answerArray.get(4)))
            isCorrect = false;

        if (!answerSpinnerTwo.getSelectedItem().toString().equalsIgnoreCase(answerArray.get(5)))
            isCorrect = false;

        if (!answerSpinnerThree.getSelectedItem().toString().equalsIgnoreCase(answerArray.get(2)))
            isCorrect = false;

        if (!answerSpinnerFour.getSelectedItem().toString().equalsIgnoreCase(answerArray.get(3)))
            isCorrect = false;

        if (!answerSpinnerFive.getSelectedItem().toString().equalsIgnoreCase(answerArray.get(0)))
            isCorrect = false;

        Log.d("isCorrect: ", "isCorrect: " + isCorrect);
        return isCorrect;
    }

    @Override
    public void initProgressDialog() {

    }

    public void setAdapter() {
        HintAdapter adapter = new HintAdapter(QuestionEightActivity.this, answerArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        answerSpinnerOne.setAdapter(adapter);
        answerSpinnerTwo.setAdapter(adapter);
        answerSpinnerThree.setAdapter(adapter);
        answerSpinnerFour.setAdapter(adapter);
        answerSpinnerFive.setAdapter(adapter);

        answerSpinnerOne.setSelection(adapter.getCount());
        answerSpinnerTwo.setSelection(adapter.getCount());
        answerSpinnerThree.setSelection(adapter.getCount());
        answerSpinnerFour.setSelection(adapter.getCount());
        answerSpinnerFive.setSelection(adapter.getCount());
    }

    private void createSpinnerData() {
        answerArray.add("วันจันทร์");
        answerArray.add("วันอาทิตย์");
        answerArray.add("วันพุธ");
        answerArray.add("วันอังคาร");
        answerArray.add("วันศุกร์");
        answerArray.add("วันพฤหัสบดี");
        answerArray.add("วันเสาร์");
        answerArray.add("วัน...");
    }

    private void setListener() {
        nextBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next: {
                countDownTimer.cancel();
                StoreAnswerTmse.getInstance().storeAnswer("no8", question.getId(), String.valueOf(isCorrect()));
                Intent intent = new Intent(QuestionEightActivity.this, QuestionNineActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}




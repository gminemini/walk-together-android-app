package com.custu.project.walktogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.custu.project.project.walktogether.R;
import com.custu.project.walktogether.data.Evaluation.Question;
import com.custu.project.walktogether.model.EvaluationModel;
import com.custu.project.walktogether.util.BasicActivity;
import com.custu.project.walktogether.util.StoreAnswerTmse;

public class QuestionSixteenActivity extends AppCompatActivity implements BasicActivity, View.OnClickListener {
    private Button nextBtn;
    private ImageView topLeft;
    private ImageView bottomLeft;
    private ImageView topRight;
    private ImageView bottomRight;

    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.question_sixteen);
        getData();
        setUI();
        setListener();

    }

    @Override
    public void initValue() {

    }

    @Override
    public void setUI() {
        nextBtn = (Button) findViewById(R.id.next);
        topLeft = findViewById(R.id.top_left);
        bottomLeft = findViewById(R.id.bottom_left);
        topRight = findViewById(R.id.top_right);
        bottomRight = findViewById(R.id.bottom_right);
        TextView titleTextView = (TextView) findViewById(R.id.question_text);
        titleTextView.setText("(16) "+question.getTitle());
    }

    @Override
    public void getData() {
        question = EvaluationModel.getInstance()
                .getEvaluationByNumber("16", QuestionSixteenActivity.this)
                .getQuestion();
    }

    @Override
    public void initProgressDialog() {

    }

    private void setListener() {
        nextBtn.setOnClickListener(this);
        topLeft.setOnClickListener(this);
        bottomLeft.setOnClickListener(this);
        topRight.setOnClickListener(this);
        bottomRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_left: {
                StoreAnswerTmse.getInstance().storeAnswer("no16", question.getId(), "2");
                Intent intent = new Intent(QuestionSixteenActivity.this, QuestionSeventeenActivity.class);
                startActivity(intent);
            }
            case R.id.bottom_left: {
                StoreAnswerTmse.getInstance().storeAnswer("no16", question.getId(), "4");
                Intent intent = new Intent(QuestionSixteenActivity.this, QuestionSeventeenActivity.class);
                startActivity(intent);
            }
            case R.id.top_right: {
                StoreAnswerTmse.getInstance().storeAnswer("no16", question.getId(), "1");
                Intent intent = new Intent(QuestionSixteenActivity.this, QuestionSeventeenActivity.class);
                startActivity(intent);
            }
            case R.id.bottom_right: {
                StoreAnswerTmse.getInstance().storeAnswer("no16", question.getId(), "3");
                Intent intent = new Intent(QuestionSixteenActivity.this, QuestionSeventeenActivity.class);
                startActivity(intent);
            }

        }
    }
}

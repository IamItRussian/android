package com.example.notepadby.russiancourse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;

public class LevelActivity extends Activity {

    boolean taskFinished = false;
    private int mTouchX;
    private int mTouchY;
    private final static int DEFAULT_SELECTION_LEN = 5;
    ArrayList<Integer> starts = new ArrayList<>();
    ArrayList<Integer> ends = new ArrayList<>();
    boolean firstIteration = true;
    ArrayList<Boolean> colored = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        ((TextView) findViewById(R.id.taskNumber)).setText("Задание 1");
        ((TextView) findViewById(R.id.taskView)).setText("Выделите в тексте слова с ошибками");
        ((TextView) findViewById(R.id.taskText)).setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        ((TextView) findViewById(R.id.taskText)).setText("12 34 567 890");
        init();
    }


    public String getUserAnswers() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < colored.size(); i++) {
            if (colored.get(i)) {
                sb.append('1');
            } else {
                sb.append('0');
            }
        }
        return sb.toString();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Назад");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (taskFinished) {
            finish();
        } else {
            createDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    DialogInterface.OnClickListener alertListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case AlertDialog.BUTTON_POSITIVE:
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    finish();
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (taskFinished) {
                finish();
            } else {
                createDialog();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    JSONArray getTasks(){
        //method to download tasks from server
        JSONArray tasks = new JSONArray();
        //getting from server
        return tasks;
    }

    void createDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(LevelActivity.this);
        dialog.setTitle("Внимание");
        dialog.setMessage("Вы не завершили задание. Если вы выйдете, прогресс не сохранится. Вы уверены, что хотите выйти?");
        dialog.setPositiveButton("Нет", alertListener);
        dialog.setNegativeButton("Да", alertListener);
        dialog.show();
    }

    private void init() {
        String definition = ((TextView) findViewById(R.id.taskText)).getText().toString();
        TextView definitionView = (TextView) findViewById(R.id.taskText);
        definitionView.setMovementMethod(LinkMovementMethod.getInstance());
        definitionView.setText(definitionView.getText(), TextView.BufferType.SPANNABLE);
        Spannable spans = (Spannable) definitionView.getText();
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(definition);

        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
                .next()) {
            String possibleWord = definition.substring(start, end);
            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {

                Toast.makeText(LevelActivity.this,start+"   "+end,Toast.LENGTH_LONG).show();
                if(firstIteration) {
                    starts.add(start);
                    ends.add(end);
                    ClickableSpan clickSpan = getClickableSpan(possibleWord, start, end);
                    TextPaint ds = new TextPaint();
                    ds.setUnderlineText(false);
                    ds.setColor(Color.BLACK);
                    clickSpan.updateDrawState(ds);
                    spans.setSpan(clickSpan, start, end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }else{

                }
            }
        }
        firstIteration=false;
        for(int i=0; i < starts.size();i++){
            colored.add(false);
        }
        Toast.makeText(LevelActivity.this,starts.size()+"",Toast.LENGTH_LONG).show();
    }


    private ClickableSpan getClickableSpan(final String word, final int start, final int end) {
        return new ClickableSpan() {
            final String mWord;
            final int mStart;
            final int mEnd;

            {
                mEnd = end;
                mStart = start;
                mWord = word;
            }

            @Override
            public void onClick(View widget) {
                int count = 0;
                for (int i = 0; i < starts.size(); i++) {
                    if (starts.get(i) == start) {
                        count = i;
                        break;
                    }
                }
                if (!colored.get(count)) {
                    TextView textView = (TextView) findViewById(R.id.taskText);
                    Spannable WordtoSpan = new SpannableString(textView.getText());
                    WordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(WordtoSpan);
                    colored.set(count,true);
                } else if (colored.get(count)) {
                    TextView textView = (TextView) findViewById(R.id.taskText);
                    Spannable WordtoSpan = new SpannableString(textView.getText());
                    WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(WordtoSpan);
                    colored.set(count, false);
                }
            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
            }
        };
    }
}
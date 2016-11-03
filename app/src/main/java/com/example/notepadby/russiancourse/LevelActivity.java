package com.example.notepadby.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.text.BreakIterator;
import java.util.Locale;

public class LevelActivity extends Activity {

    boolean taskFinished = false;
    private int mTouchX;
    private int mTouchY;
    private final static int DEFAULT_SELECTION_LEN = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        ((TextView) findViewById(R.id.taskNumber)).setText("Задание 1");
        ((TextView) findViewById(R.id.taskView)).setText("Выделите в тексте слова с ошибками");
        ((TextView) findViewById(R.id.taskText)).setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
       // ((EditText) findViewById(R.id.editText)).setSelection(10,20);
        init();
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
                
                spans.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                /*Spannable spannable = new SpannableString("1234567890");
                spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                ((TextView) findViewById(R.id.taskText)).setText(spannable);*/
            }
        }
    }


    private ClickableSpan getClickableSpan(final String word) {
        return new ClickableSpan() {
            final String mWord;
            {
                mWord = word;
            }

            @Override
            public void onClick(View widget) {
                Toast.makeText(LevelActivity.this,"tapped on:"+ mWord, Toast.LENGTH_LONG).show();
            }

            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
            }
        };
    }
}
package secondapp.bignerdranch.com.commonsharingapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = (EditText) findViewById(R.id.main_activity_text_box);
        mButton = (Button) findViewById(R.id.main_activity_button);
        mTextView = (TextView) findViewById(R.id.main_activity_text_view);

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditText.getText().toString();
                mButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0)
                {
                    mButton.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Need some character to save", Toast.LENGTH_SHORT).show();
                }

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile(v);
            }
        });

        if(mEditText.getText().length()==0)
        {

            mButton.setEnabled(false);
            Toast.makeText(MainActivity.this,"Pl enter some text", Toast.LENGTH_SHORT).show();
        }
    }


    public void saveFile(View v)
    {
        String fileName = "myFile.txt";
        File file = null;
        final String editTextMessage = mEditText.getText().toString();
        mTextView.append(editTextMessage);
        FileOutputStream fileOutputStream = null;

        try
        {
            file= getFilesDir();
            fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(editTextMessage.getBytes());

            mTextView.setTextColor(Color.GREEN);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException io)
        {
            io.printStackTrace();
        }
        finally
        {
            try
            {
                fileOutputStream.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }

        }
        Toast.makeText(this, "Saved successfully to :" + file.toString(), Toast.LENGTH_SHORT).show();
    }
}

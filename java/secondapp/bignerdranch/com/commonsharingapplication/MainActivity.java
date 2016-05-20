package secondapp.bignerdranch.com.commonsharingapplication;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity  {

    private TextView mTextView;
    private Button mButton;

    String packageName = "secondapp.bignerdranch.com.commonsharingapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=(TextView)findViewById(R.id.load_text_view);
        mButton =(Button)findViewById(R.id.load_file_button);
        loadFile(getCurrentFocus()); // getCurrentFocus() gets you to pass the current View instead of trying to inflate it from layout
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void loadFile(View v)
    {
        PackageManager pm = getPackageManager();
        try
        {
          ApplicationInfo applicationInfo =  pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            String fullPath = applicationInfo.dataDir +"/files/myFile.txt";
            mTextView.setText(fullPath);
            readFile(fullPath);

        } catch (PackageManager.NameNotFoundException nme)
        {
            nme.printStackTrace();
            mTextView.setTextColor(Color.RED);
            mTextView.setText(nme + "");
        }


    }

    public void readFile(String fullPath)
    {
        FileInputStream fileInputStream = null;
        try
        {
            fileInputStream = new FileInputStream(new File(fullPath));
            int read=-1;
            StringBuffer stringBuffer = new StringBuffer();
            while((read = fileInputStream.read()) !=-1)
            {
                stringBuffer.append((char)read);
            }
            Log.i("Logging", "" + stringBuffer);
            mTextView.setText(stringBuffer);
            mTextView.setTextColor(Color.BLUE);
            mTextView.setText(stringBuffer + "\n was read succesffully from "+ fullPath);
        } catch (FileNotFoundException fe)
        {
          mTextView.setTextColor(Color.RED);
           mTextView.setText(fe + "");

        } catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        finally {
            if(fileInputStream !=null) {
                try {
                    fileInputStream.close();
                } catch (IOException ie) {
                    mTextView.setTextColor(Color.RED);
                    mTextView.setText(ie + "");
                }
            }

        }

    }
}

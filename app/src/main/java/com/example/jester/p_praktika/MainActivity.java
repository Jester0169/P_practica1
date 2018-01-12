package com.example.jester.p_praktika;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int READ_REQUEST_CODE = 42;
    private static final String TAG ="file_app_tag" ;



    Uri uri = null;

    Button btnSelect,btnSearch,btnInsert;
    TextView textView;
    EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSelect=(Button) findViewById(R.id.btnSelect);
        btnSearch=(Button) findViewById(R.id.btnSearch);
        btnInsert=(Button) findViewById(R.id.btnInsert);
        editText=(EditText)findViewById(R.id.edit);
        textView=(TextView)findViewById(R.id.text);

        btnSearch.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnInsert.setOnClickListener(this);





        //performFileSearch();


    }

    String filePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            //Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                String path = uri.getEncodedPath();
                Log.i(TAG, "Uri: " + uri.toString());
                Log.i(TAG,"----path-----:"+path);
                //showImage(uri);
            }
        }
    }





    public void performFileSearch(){

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("text/plane");

        startActivityForResult(intent, READ_REQUEST_CODE);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSelect:
                performFileSearch();
                break;
           case R.id.btnSearch:
               try {
                   searchWordFromUri(uri);
               } catch (IOException e) {
                   e.printStackTrace();
               }


               // textView.setText(searchWordFromUri(uri));
              break;
            case R.id.btnInsert:
                try {
                    insert(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    private void searchWordFromUri(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        CharSequence cs = editText.getText();

        String word1= getResources().getString(R.string.searchRes);
        String word2= getResources().getString(R.string.searchRes2);
        String notFound= getResources().getString(R.string.NotFound);


        while ((line = reader.readLine()) != null) {
           //if(line  editText){
              // stringBuilder.append(line+"/n");
            if(line.contains(cs)){
                stringBuilder.append( line + System.getProperty ("line.separator"));
               // textView.setText(line);
            }


           //}
        }
        //InputStream.close();
       // parcelFileDescriptor.close();
        //return stringBuffer.toString();


        textView.setText(word1+" "+cs+" "+word2 + System.getProperty("line.separator")+  stringBuilder.toString());
      // Log.i(TAG,);
    }

    //добавление файла
    public void insert(Uri uri) throws FileNotFoundException {
        Log.d(TAG,"----------------------------------------------------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------------------------------------------------------" +
                "---------------------------------------------------------------------------------------------------------------------------------" +
                "------------------------------------------------------------------------------------------------------------------------------------" +
                "-------------------------------------------------------------------------------------------------------------------------------------" +
                "______________________INSERT______________INSERT_________________INSERT_________________INSERT______________________________________" +
                "_____________________________________________________________________________________________________________________________________" +
                "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        OutputStream outputStream = getContentResolver().openOutputStream(uri);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));

        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
       // StringBuilder stringBuilder = new StringBuilder();
        //String mline="";
        String text = textView.getText().toString();

       try {
           /// if ((mline = reader.readLine()) == null) {

           // }
           // else {

                writer.append(text);

           writer.flush();
           writer.close();
           outputStream.flush();
           outputStream.close();
            }


        catch (IOException e) {
            e.printStackTrace();
        }
        Log.i(TAG,editText.getText().toString());
    }

        //удаление
   /*private void  delete(Uri uri){
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        CharSequence cs = editText.getText();

        String word1= getResources().getString(R.string.searchRes);
        String word2= getResources().getString(R.string.searchRes2);
        String notFound= getResources().getString(R.string.NotFound);


        while ((line = reader.readLine()) != null) {
            //if(line  editText){
            // stringBuilder.append(line+"/n");
            if(line.contains(cs)){
               // stringBuilder.append( line + System.getProperty ("line.separator"));
                // textView.setText(line);
            }


            //}
        }
        //InputStream.close();
        // parcelFileDescriptor.close();
        //return stringBuffer.toString();


        textView.setText(word1+" "+cs+" "+word2 + System.getProperty("line.separator")+  stringBuilder.toString());
        // Log.i(TAG,);
    }*/


}




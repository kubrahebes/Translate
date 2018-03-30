package com.example.recodedharran.translate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recodedharran.translate.models.Adapter;
import com.example.recodedharran.translate.models.Translate;
import com.example.recodedharran.translate.models.WordDbHelper;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.recodedharran.translate.models.WordContact.WordEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
EditText editText=findViewById(R.id.search_edit_text);
editText.setOnKeyListener(new View.OnKeyListener() {
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

       ArrayList<Translate>arrayList=new ArrayList<>();
       Adapter adapter=new Adapter(MainActivity.this,R.layout.list_item,arrayList);
        ListView listView = findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        return true;
    }
});
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void getMethod(String select) {
        Ion.with(MainActivity.this)
                .load("https://wordsapiv1.p.mashape.com/words/" + select)
                .setHeader("X-Mashape-Key", "Ku67PIwfgamshFGp5Jt5DXCP2qcFp1T39oAjsn9JG25XBqyYHg")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e == null) {
                            JSONArray array = null;
                            String selectworld = "";
                            try {
                                selectworld = result.get("word").getAsString();
                            } catch (Exception ewed) {
                                return;
                            }

                            ArrayList<Translate> arrayList = new ArrayList<>();
                            try {


                                array = new JSONArray(result.getAsJsonArray("results").toString());
                                for (int i = 0; i < array.length(); i++) {
                                    String definition = array.getJSONObject(i).getString("definition");
                                    String synonyms = array.getJSONObject(i).optString("synonyms");
                                    arrayList.add(new Translate(selectworld, definition, synonyms));
                                }


                                updateui(arrayList);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "There is a problem getting information from the API.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

    }

    public void updateui(ArrayList<Translate> arrayList) {

        ListView listView = findViewById(R.id.list_item);
        TextView textView = findViewById(R.id.textview);
        ImageView res = findViewById(R.id.add);
        listView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.GONE);
        res.setVisibility(View.GONE);

        Adapter adapter = new Adapter(MainActivity.this, R.layout.list_item, arrayList);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_item, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            EditText select = findViewById(R.id.search_edit_text);
            getMethod(select.getText().toString());

            return true;
        } else if (id == R.id.action_favorites) {
            EditText select = findViewById(R.id.search_edit_text);
            select.setText(" ");
            Intent i = new Intent(this, FavoriteActivity.class);
            startActivity(i);
        } else if (id == R.id.action_delete) {
            deletee();
            Toast.makeText(MainActivity.this, "All favorite words has been deleted.", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    public void deletee() {
        WordDbHelper dataBaseHelper = new WordDbHelper(MainActivity.this, WordDbHelper.DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
      sqLiteDatabase.execSQL("delete from "+ TABLE_NAME);
    }
}


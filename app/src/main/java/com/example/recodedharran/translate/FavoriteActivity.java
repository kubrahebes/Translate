package com.example.recodedharran.translate;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.recodedharran.translate.models.Adapter;
import com.example.recodedharran.translate.models.AdapterFavorite;
import com.example.recodedharran.translate.models.Translate;
import com.example.recodedharran.translate.models.WordContact;
import com.example.recodedharran.translate.models.WordDbHelper;

import java.util.ArrayList;

import static android.app.DownloadManager.COLUMN_ID;
import static com.example.recodedharran.translate.models.WordContact.WordEntry.TABLE_NAME;

/**
 * Created by Recodedharran on 13.1.2018.
 */

public class FavoriteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_main);
        ListView listView = findViewById(R.id.favorite_list);
        ArrayList<Translate> arrayList = SelectWord();
        AdapterFavorite adapterFavorite = new AdapterFavorite(FavoriteActivity.this, R.layout.favorite_list_item, arrayList);
        listView.setAdapter(adapterFavorite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Toast.makeText(this, arrayList.get(1).getSelect(), Toast.LENGTH_SHORT).show();

    }

    public ArrayList<Translate> SelectWord() {
        String query = "Select * FROM " + TABLE_NAME;

        WordDbHelper dbHelper = new WordDbHelper(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        Translate translate = new Translate();
        ArrayList<Translate> liste = new ArrayList<>();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                int index;
                index = cursor.getColumnIndexOrThrow("word");
                String wordname = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow("definition");
                String definition = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow("synonym");
                String synonym = cursor.getString(index);

                index = cursor.getColumnIndexOrThrow(WordContact.WordEntry.ID );
                int  id_ = cursor.getInt(index);

                liste.add(new Translate(wordname,id_,definition,synonym));


            }
            cursor.close();

        } else {
            translate = null;
            liste.add(translate);
        }
        db.close();
        return liste;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        {

        }
        getMenuInflater().inflate(R.menu.favorite_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

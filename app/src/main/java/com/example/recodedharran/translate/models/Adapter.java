package com.example.recodedharran.translate.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recodedharran.translate.R;

import java.util.List;

import static com.example.recodedharran.translate.models.WordContact.WordEntry.COLUMN_ID;
import static com.example.recodedharran.translate.models.WordContact.WordEntry.TABLE_NAME;

/**
 * Created by Recodedharran on 13.1.2018.
 */

public class Adapter extends ArrayAdapter<Translate> {
    public Adapter(@NonNull Context context, int resource, @NonNull List<Translate> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_item, null);
            final Translate obje = getItem(position);
            TextView selectword = v.findViewById(R.id.text1);
            TextView definition = v.findViewById(R.id.text2);
            TextView synonyms = v.findViewById(R.id.text3);
            Button favorite = v.findViewById(R.id.favorite);
            selectword.setText(obje.getSelect());
            definition.setText(obje.getDefinition());
            synonyms.setText(obje.getSynonyms());
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertWord(obje.getSelect(), obje.getDefinition(), obje.getSynonyms());
                }
            });
        }

        return v;
    }

    public void insertWord(String select, String definition, String synonyms) {
        WordDbHelper mDbHelper = new WordDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WordContact.WordEntry.COLUMN_DEFINITION, definition);
        values.put(WordContact.WordEntry.COLUMN_SELECT, select);
        values.put(WordContact.WordEntry.COLUMN_SYNONYM, synonyms);

        if (db.insert(TABLE_NAME, null, values) == -1) {
            Toast.makeText(getContext(), "Error!!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Word added to favorites!", Toast.LENGTH_SHORT).show();
        }
    }
}


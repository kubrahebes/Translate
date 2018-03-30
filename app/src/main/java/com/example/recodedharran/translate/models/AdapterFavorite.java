package com.example.recodedharran.translate.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recodedharran.translate.FavoriteActivity;
import com.example.recodedharran.translate.R;

import java.util.List;

import static com.example.recodedharran.translate.models.WordContact.WordEntry.TABLE_NAME;

/**
 * Created by Recodedharran on 13.1.2018.
 */

public class AdapterFavorite extends ArrayAdapter<Translate> {
    public AdapterFavorite(@NonNull Context context, int resource, @NonNull List<Translate> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.favorite_list_item, null);
            final Translate obje = getItem(position);
            RelativeLayout relativeLayout = v.findViewById(R.id.relativ);
            TextView selectword = v.findViewById(R.id.text1);
            TextView definition = v.findViewById(R.id.text2);
            TextView synonyms = v.findViewById(R.id.text3);
            Button favorite = v.findViewById(R.id.favorite);
            try {
                selectword.setText(obje.getSelect());
                definition.setText(obje.getDefinition());
                synonyms.setText(obje.getSynonyms());
            } catch (Exception e) {
                relativeLayout.setVisibility(View.GONE);
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (favoriteDelete(obje.getId())) {
                        Toast.makeText(getContext(), "Word deleted.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), FavoriteActivity.class);
                        getContext().startActivity(intent);

                    } else
                        Toast.makeText(getContext(), "Can't delete the word.", Toast.LENGTH_SHORT).show();

                }
            });
        }

        return v;
    }

    public boolean favoriteDelete(int id) {
        WordDbHelper mDbHelper = new WordDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        return db.delete(TABLE_NAME, BaseColumns._ID + "=" + id, null) > 0;
    }


}


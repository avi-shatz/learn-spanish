package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter <Word> {

    private int colorResourceId;

    //@param colorResourceId is the resource ID for the background color for this list of words
    public WordAdapter (Context activity, ArrayList<Word> words, int colorResourceId) {
        //I'm not going to use resource, hence the value 0.
        super(activity, 0, words);
        this.colorResourceId = colorResourceId;
    }

    // override get view so we can get two text views
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }

        // Get the Word object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_items.xml layout with the ID foreignText
        TextView foreignTextView = (TextView) listItemView.findViewById(R.id.foreignText);
        // set this text on the foreignTextView
        foreignTextView.setText(currentWord.getForeignWord());

        // Find the TextView in the list_items.xml layout with the ID defaultText
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.defaultText);
        // set this text on the defaultTextView
        defaultTextView.setText(currentWord.getDefaultWord());


        // Find the imageView in the list_items.xml layout with the ID image_side
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_side);
        // if there is an image view set imageres, else don't show image
        if (currentWord.hasImage())
            imageView.setImageResource(currentWord.getImageSrc());
        else
            imageView.setVisibility(View.GONE);

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), colorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Return the whole list_items layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}

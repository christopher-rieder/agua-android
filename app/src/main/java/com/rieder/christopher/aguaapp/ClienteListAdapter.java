package com.rieder.christopher.aguaapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.rieder.christopher.aguaapp.DomainClasses.Cliente;

import java.util.List;

public class ClienteListAdapter extends ArrayAdapter<Cliente> {

    public ClienteListAdapter(Context ctx, List<Cliente> clientes) {
        super(ctx, 0, clientes);
    }

/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        final Word currentWord = getItem(position);

        // Set Background color
        int color = ContextCompat.getColor(getContext(), backgroundColor);
        listItemView.setBackgroundColor(color);

        // Find the TextView in the list_item.xml layout with the ID miwokWord
        // and set the actual text.
        TextView nameTextView = listItemView.findViewById(R.id.miwokWord);
        nameTextView.setText(currentWord.getMiwookTranslation());

        // Find the TextView in the list_item.xml layout with the ID englishWord
        // and set the actual text.
        TextView numberTextView = listItemView.findViewById(R.id.englishWord);
        numberTextView.setText(currentWord.getDefaultTranslation());

        // Find the ImageView in the list_item.xml layout with the ID imageWord
        ImageView iconView = listItemView.findViewById(R.id.imageWord);

        // Check if the word has an image associated.
        if (currentWord.hasImageAsociated()) {
            iconView.setImageResource(currentWord.getImageResourceID());
            // Necessary if the view was previously put in View.GONE mode.
            iconView.setVisibility(View.VISIBLE);
        } else
            // Hides the ImageView from the activity if there isn't an image associated.
            iconView.setVisibility(View.GONE);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }*/

}

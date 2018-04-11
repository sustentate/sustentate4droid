package com.sustentate.app.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sustentate.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetalle extends Fragment {

    public static final String CLAVE_TIPID = "id";
    public static final String CLAVE_TIPDATE = "date";
    public static final String CLAVE_TIPTITLE = "title";
    public static final String CLAVE_TIPTEXT = "text";
    public static final String CLAVE_TIPIMAGE = "image";

    public FragmentDetalle() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        Bundle bundle = getArguments();

        String title = bundle.getString(CLAVE_TIPTITLE);
        String text = bundle.getString(CLAVE_TIPTEXT);
        String imageURL = bundle.getString(CLAVE_TIPIMAGE);
        Long id = bundle.getLong(CLAVE_TIPID);
        Long date = bundle.getLong(CLAVE_TIPDATE);

        TextView textDetalle = view.findViewById(R.id.textView_textDetalle);
        TextView titleDetalle = view.findViewById(R.id.textView_titleDetalle);
        ImageView imageDetalle = view.findViewById(R.id.imageView_detalle);

        Picasso.with(getContext()).load(imageURL).placeholder(R.drawable.placeholder).error(R.drawable.placeholder).into(imageDetalle);
        textDetalle.setText(text);
        titleDetalle.setText(title);

        return view;
    }

}

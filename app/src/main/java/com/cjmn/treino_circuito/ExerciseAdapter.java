package com.cjmn.treino_circuito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cjmn.treino_circuito.model.Exercicio;

import java.util.List;

public class ExerciseAdapter extends ArrayAdapter<Exercicio> {
    public ExerciseAdapter(Context context, List<Exercicio> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Exercicio item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_exercises, parent, false);
        }

        TextView nome = convertView.findViewById(R.id.nome);
        TextView tempo = convertView.findViewById(R.id.tempo);

        nome.setText(item.getNome());
        tempo.setText("Tempo: " + item.getSeconds() + "segs");

        return convertView;
    }
}

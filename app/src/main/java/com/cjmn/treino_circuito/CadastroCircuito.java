package com.cjmn.treino_circuito;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cjmn.treino_circuito.model.Exercicio;

import java.util.ArrayList;
import java.util.List;

public class CadastroCircuito extends AppCompatActivity {
    private List<Exercicio> exercicios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_circuito);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button addButton = findViewById(R.id.addExercise);
        addButton.setOnClickListener(this::addExercise);
    }

    private void addExercise(View event) {
        EditText nameEditText = findViewById(R.id.exerciseName);
        EditText timeEditText = findViewById(R.id.exerciseTime);

        Exercicio exercicio = new Exercicio();
        exercicio.setNome(nameEditText.getText().toString());
        exercicio.setSeconds(Integer.parseInt(timeEditText.getText().toString()));

        exercicios.add(exercicio);
        ListView exerciseList = findViewById(R.id.listExercises);

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, exercicios);

        // Conectar o adapter ao ListView
        exerciseList.setAdapter(exerciseAdapter);
    }

}
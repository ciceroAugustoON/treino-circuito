package com.cjmn.treino_circuito;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cjmn.treino_circuito.db.DatabaseHelper;
import com.cjmn.treino_circuito.model.Exercicio;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private List<Exercicio> exercicios = new ArrayList<>();
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        exercicios = databaseHelper.getAllExercicios();

        if (exercicios.isEmpty()) {
            Intent intent = new Intent(MainActivity.this, CadastroCircuito.class);
            startActivity(intent);
        }

        ListView exerciseList = findViewById(R.id.exercicios);

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, exercicios);

        // Conectar o adapter ao ListView
        exerciseList.setAdapter(exerciseAdapter);
    }
}
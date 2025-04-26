package com.cjmn.treino_circuito;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    private long tempoRestanteMilisegundos;

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
            editarTreino(null);
        }

        ListView exerciseList = findViewById(R.id.exercicios);

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, exercicios);

        // Conectar o adapter ao ListView
        exerciseList.setAdapter(exerciseAdapter);

        Button iniciarTreino = findViewById(R.id.init_treino);
        iniciarTreino.setOnClickListener(this::iniciarTreino);

        Button editarTreino = findViewById(R.id.edit_treino);
        editarTreino.setOnClickListener(this::editarTreino);
    }

    private void editarTreino(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroCircuito.class);
        startActivity(intent);
    }

    private void iniciarTreino(View v) {
        if (exercicios.isEmpty()) {
            return;
        }
        TextView nomeExercicioText = findViewById(R.id.nomeExercicio);
        TextView tempoExercicioText = findViewById(R.id.tempoExercicio);

        rodarExercicio(exercicios.get(0), nomeExercicioText, tempoExercicioText);
    }

    private void rodarExercicio(Exercicio e, TextView nomeExercicioText, TextView tempoExercicioText) {
        nomeExercicioText.setText(e.getNome());
        tempoRestanteMilisegundos = e.getSeconds().longValue() * 1000;
        var countDownTimer = new CountDownTimer(tempoRestanteMilisegundos, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempoRestanteMilisegundos = millisUntilFinished;
                int minutes = (int) tempoRestanteMilisegundos / 60000;
                int seconds = (int) tempoRestanteMilisegundos % 60000 / 1000;

                String timeLeftText = String.format("%02d:%02d", minutes, seconds);
                tempoExercicioText.setText(timeLeftText);
            }

            @Override
            public void onFinish() {
                int index = exercicios.indexOf(e) + 1;
                if (index >= exercicios.size()) {
                    nomeExercicioText.setText("Treino Finalizado");
                    tempoExercicioText.setText("");
                } else {
                    rodarExercicio(exercicios.get(index), nomeExercicioText, tempoExercicioText);
                }
            }
        }.start();
    }
}
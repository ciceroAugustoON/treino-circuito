package com.cjmn.treino_circuito.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cjmn.treino_circuito.model.Exercicio;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Informações do banco de dados
    private static final String DATABASE_NAME = "exercicios.db";
    private static final int DATABASE_VERSION = 1;

    // Nome da tabela e colunas
    private static final String TABLE_EXERCICIOS = "exercicios";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOME = "nome";
    private static final String COLUMN_SECONDS = "seconds";

    // SQL para criar a tabela
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_EXERCICIOS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NOME + " TEXT," +
                    COLUMN_SECONDS + " INTEGER" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCICIOS);
        onCreate(db);
    }

    public long addExercicio(Exercicio exercicio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, exercicio.getNome());
        values.put(COLUMN_SECONDS, exercicio.getSeconds());

        // Inserir linha
        long id = db.insert(TABLE_EXERCICIOS, null, values);
        db.close();

        return id;
    }

    public Exercicio getExercicio(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_EXERCICIOS,
                new String[]{COLUMN_ID, COLUMN_NOME, COLUMN_SECONDS},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        @SuppressLint("Range") Exercicio exercicio = new Exercicio(
                cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_NOME)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_SECONDS)));

        cursor.close();
        return exercicio;
    }

    @SuppressLint("Range")
    public List<Exercicio> getAllExercicios() {
        List<Exercicio> exercicios = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_EXERCICIOS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Exercicio exercicio = new Exercicio();
                exercicio.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                exercicio.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_NOME)));
                exercicio.setSeconds(cursor.getInt(cursor.getColumnIndex(COLUMN_SECONDS)));

                exercicios.add(exercicio);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return exercicios;
    }

    public int updateExercicio(Exercicio exercicio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, exercicio.getNome());
        values.put(COLUMN_SECONDS, exercicio.getSeconds());

        // Atualizando linha
        return db.update(TABLE_EXERCICIOS, values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(exercicio.getId())});
    }

    public void deleteExercicio(Exercicio exercicio) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXERCICIOS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(exercicio.getId())});
        db.close();
    }

    public int getExerciciosCount() {
        String countQuery = "SELECT * FROM " + TABLE_EXERCICIOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}

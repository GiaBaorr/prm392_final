package com.giabao.finalproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.giabao.finalproject.model.ToyEntity;

import java.util.ArrayList;
import java.util.List;

//https://www.youtube.com/watch?v=9t8VVWebRFM
public class PRMDatabase extends SQLiteOpenHelper {

    private static PRMDatabase instance;

    public static synchronized PRMDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new PRMDatabase(context.getApplicationContext());
        }
        return instance;
    }

    public PRMDatabase(Context context) {
        super(context, "prm.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateToyTableDDL());

        // Sample data
        populateToys(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(getDropToyTableDDL());
    }

    private String getCreateToyTableDDL() {
        return "create table toy (" + "id INTEGER primary key autoincrement, " + "name TEXT, " + "description TEXT, " + "imageUrl TEXT, " + "quantity INTEGER, " + "price INTEGER " + ")";
    }

    private String getDropToyTableDDL() {
        return "drop Table if exists toy";
    }

    public Boolean insertToy(ToyEntity toyEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", toyEntity.getName());
        values.put("description", toyEntity.getDescription());
        values.put("imageUrl", toyEntity.getImageUrl());
        values.put("quantity", toyEntity.getQuantity());
        values.put("price", toyEntity.getPrice());

        long result = db.insert("toy", null, values);
        db.close();
        return result != -1;
    }

    public Boolean updateToy(ToyEntity toyEntity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", toyEntity.getName());
        values.put("description", toyEntity.getDescription());
        values.put("imageUrl", toyEntity.getImageUrl());
        values.put("quantity", toyEntity.getQuantity());
        values.put("price", toyEntity.getPrice());

        int rowsAffected = db.update("toy", values, "id = ?", new String[]{String.valueOf(toyEntity.getId())});

        db.close();
        return rowsAffected > 0;
    }

    public boolean deleteToy(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRow = db.delete("toy", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return affectedRow > 0;
    }

    private void populateToys(SQLiteDatabase db) {
        List<ToyEntity> toyList = new ArrayList<>();
        toyList.add(new ToyEntity(0, "Toy Car", "A fast toy car", 50, 999, "https://media.istockphoto.com/id/1162308617/vi/anh/xe-t%E1%BA%A3i-%C4%91%E1%BB%93-ch%C6%A1i-tr%C3%AAn-n%E1%BB%81n-tr%E1%BA%AFng.jpg?s=2048x2048&w=is&k=20&c=y0NufL6PRblIbQf88536dIsKYb1osaROkncvQsyu5W0="));
        toyList.add(new ToyEntity(0, "Doll", "A beautiful doll", 30, 1999, "https://media.istockphoto.com/id/172342794/vi/anh/m%E1%BB%99t-%C4%91%E1%BA%A7u-b%C3%BAp-b%C3%AA-n%E1%BB%AF-%C4%91%C3%A1ng-s%E1%BB%A3-v%E1%BB%9Bi-%C4%91%C3%B4i-m%E1%BA%AFt-v%C3%A0ng.jpg?s=2048x2048&w=is&k=20&c=Yutrt4_NVEPqTfkP9PSfB4IyIiMKYwich1weYXiJPLI="));
        toyList.add(new ToyEntity(0, "Action Figure", "A superhero action figure", 20, 1499, "https://media.istockphoto.com/id/1266038662/photo/tyrannosaurus-rex-a-huge-reptile-from-the-jurassic-period-a-childrens-toy.webp?b=1&s=612x612&w=0&k=20&c=_ZpfFvtdY5Y5tLWbSS3zNxF4rAO6ve1JMZ1Z0ht7m4A="));
        toyList.add(new ToyEntity(0, "Building Blocks", "Colorful building blocks", 100, 2999, "https://media.istockphoto.com/id/1696755613/photo/final-step.webp?b=1&s=612x612&w=0&k=20&c=-K_tPCrWtIL4j8BdkK7o8tsj7TMZ4VAbqwWFagKmBMg="));
        for (ToyEntity toy : toyList) {
            String sql = "INSERT INTO toy (name, description, quantity, price, imageUrl) VALUES ('" + toy.getName() + "', '" + toy.getDescription() + "', " + toy.getQuantity() + ", " + toy.getPrice() + ", '" + toy.getImageUrl() + "');";

            db.execSQL(sql); // Use execSQL for raw SQL statement
        }

    }

    public List<ToyEntity> getAllToys() {
        List<ToyEntity> toys = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM toy", null);


        if (cursor.moveToFirst()) {
            do {
                ToyEntity toy = new ToyEntity();
                toy.setId(cursor.getInt(0));
                toy.setName(cursor.getString(1));
                toy.setDescription(cursor.getString(2));
                toy.setImageUrl(cursor.getString(3));
                toy.setQuantity(cursor.getInt(4));
                toy.setPrice(cursor.getInt(5));

                toys.add(toy);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return toys;
    }


}

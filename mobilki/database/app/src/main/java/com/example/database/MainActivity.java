package com.example.database;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener{
    Button buttonDel, buttonClear, buttonShow, buttonAdd;
    EditText ET_name, ET_phone, ET_city;
    TextView text;
    DatabaseHelper DatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = (Button)findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        buttonShow = (Button)findViewById(R.id.buttonShow);
        buttonShow.setOnClickListener(this);
        buttonDel = (Button)findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(this);
        buttonClear = (Button)findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);
        ET_name = (EditText)findViewById(R.id.ET_name);
        ET_phone = (EditText)findViewById(R.id.ET_phone);
        ET_city = (EditText)findViewById(R.id.ET_city);
        text = (TextView)findViewById(R.id.text);
        DatabaseHelper = new DatabaseHelper(this);
    }
    @Override
    public void onClick(View v) {
        String input_name = ET_name.getText().toString();
        String input_phone = ET_phone.getText().toString();
        String input_city = ET_city.getText().toString();
        SQLiteDatabase db = DatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        int but = v.getId();
        if (but == R.id.buttonShow){
            text.setText("");
            String[] projection = {
                    DBContract.DBEntry.COLUMN_NAME_NAME,
                    DBContract.DBEntry.COLUMN_NAME_PHONE,
                    DBContract.DBEntry.COLUMN_NAME_CITY
            };
            Cursor cursor = db.query(
                    DBContract.DBEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            int index_name = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_NAME);
            int index_phone = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_PHONE);
            int index_city = cursor.getColumnIndex(DBContract.DBEntry.COLUMN_NAME_CITY);
            while (cursor.moveToNext()) {
                String value_name = cursor.getString(index_name);
                String value_phone = cursor.getString(index_phone);
                String value_city = cursor.getString(index_city);
                text.append("\n" + value_name + " \n " + value_phone + " \n " + value_city + " \n");
            }
            cursor.close();
        }
        else if (but == R.id.buttonAdd){
            values.put(DBContract.DBEntry.COLUMN_NAME_NAME, input_name);
            values.put(DBContract.DBEntry.COLUMN_NAME_PHONE, input_phone);
            values.put(DBContract.DBEntry.COLUMN_NAME_CITY, input_city);
            db.insert(DBContract.DBEntry.TABLE_NAME, null, values);
            buttonShow.callOnClick();
        }
        else if (but == R.id.buttonDel){
            String selection = DBContract.DBEntry.COLUMN_NAME_NAME + "=?";
            String[] selectionArgs = {input_name};
            db.delete(DBContract.DBEntry.TABLE_NAME, selection,
                    selectionArgs);
            buttonShow.callOnClick();
        }
        else if (but == R.id.buttonClear){
            db.delete(DBContract.DBEntry.TABLE_NAME, null, null);
            buttonShow.callOnClick();
        }

        DatabaseHelper.close();
    }
}
package com.example.chatting;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMilkInfo extends AppCompatActivity {
    android.widget.RadioGroup RadioGroup;
    EditText Milkquantity, Milkprice;
    TextView cat, heading;
    String str,vall,str1;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    Resources resources;
    RadioButton goatmilk, cowmilk, baffalomilk;
    Button savedetails;
    MenuItem SearchOrders, OtherMilkMans,Reviews;
    String languages;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_milk_info);


        Intent inten=getIntent();
       str= inten.getStringExtra("val1");
       languages = inten.getExtras().getString("language");
       dbHelper=new DatabaseHelper(this);
       heading=findViewById(R.id.heading);
       savedetails=findViewById(R.id.savedetails);
       cat=findViewById(R.id.cat);
        RadioGroup=findViewById(R.id.radiogroup);
        Milkquantity=findViewById(R.id.milkquantitiy);
        Milkprice=findViewById(R.id.milkprice);
        cowmilk=findViewById(R.id.cowmilk);
        goatmilk=findViewById(R.id.goatmilk);
        baffalomilk=findViewById(R.id.baffalomilk);




        db=dbHelper.getReadableDatabase();
        String[] columns = {DatabaseContract.MilkMan._ID};
        Cursor c = db.query(DatabaseContract.MilkMan.TABLE_NAME, columns, DatabaseContract.MilkMan.COL_EMAIL + "=?", new String[]{str}
                , null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            vall=String.valueOf(c.getLong(0));
        }
        else{
            vall="0";
        }

        if(languages.equals("ENGLISH"))
        {

            context = LocalHelper.setLocale(AddMilkInfo.this, "en");
            resources = context.getResources();
            cat.setText(resources.getString(R.string.cat));
            Milkprice.setText(resources.getString(R.string.price));
            Milkquantity.setText(resources.getString(R.string.quantity));
            heading.setText(resources.getString(R.string.heading1));
            cowmilk.setText(resources.getString(R.string.cowmilk));
            goatmilk.setText(resources.getString(R.string.goatmilk));
            baffalomilk.setText(resources.getString(R.string.baffalomillk));
            savedetails.setText(resources.getString(R.string.savedetails));

          /*  SearchOrders.setTitle(resources.getString(R.string.SearchOrders));
            OtherMilkMans.setTitle(resources.getString(R.string.OtherMilkMans));
            Reviews.setTitle(resources.getString(R.string.Reviews));*/

            str1="ENGLISH";
        }

        if(languages.equals("اردو"))
        {
            context = LocalHelper.setLocale(AddMilkInfo.this, "an");
            resources = context.getResources();
            cat.setText(resources.getString(R.string.cat));
            Milkprice.setText(resources.getString(R.string.price));
            Milkquantity.setText(resources.getString(R.string.quantity));
            heading.setText(resources.getString(R.string.heading1));
            cowmilk.setText(resources.getString(R.string.cowmilk));
            goatmilk.setText(resources.getString(R.string.goatmilk));
            baffalomilk.setText(resources.getString(R.string.baffalomillk));
            savedetails.setText(resources.getString(R.string.savedetails));

           /* SearchOrders.setTitle(resources.getString(R.string.SearchOrders));
            OtherMilkMans.setTitle(resources.getString(R.string.OtherMilkMans));
            Reviews.setTitle(resources.getString(R.string.Reviews));
*/

            str1="اردو";


        }


    }




    public void PostDetails (View view) {
        db=dbHelper.getWritableDatabase();
        int quantity=Integer.parseInt(Milkquantity.getText().toString());
        int price=Integer.parseInt(Milkprice.getText().toString());
        int checkid=RadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton=findViewById(checkid);
        String category= radioButton.getText().toString();

        if(quantity==0 || price==0 )
        {
            Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ContentValues args = new ContentValues();
            args.put(DatabaseContract.MilkMan.COL_QUANTITY,quantity);
            args.put(DatabaseContract.MilkMan.COL_PRICE,price);
            args.put(DatabaseContract.MilkMan.COL_QUALITY,category);
            String[] wherearg={str};
            Integer count= db.update(DatabaseContract.MilkMan.TABLE_NAME, args, DatabaseContract.MilkMan.COL_EMAIL + "=?",wherearg);
            if (count > 0) {
                Toast.makeText(this, count+"  Records updated: " , Toast.LENGTH_SHORT).show();
            }
            db.close();

        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        SearchOrders = menu.findItem(R.id.SearchOrders);
        OtherMilkMans = menu.findItem(R.id.OtherMilkMans);
        Reviews= menu.findItem(R.id.Reviews);
        if(languages.equals("ENGLISH"))
        {

            context = LocalHelper.setLocale(AddMilkInfo.this, "en");
            resources = context.getResources();
            SearchOrders.setTitle(resources.getString(R.string.SearchOrders));
            OtherMilkMans.setTitle(resources.getString(R.string.OtherMilkMans));
            Reviews.setTitle(resources.getString(R.string.Reviews));
        }

        if(languages.equals("اردو"))
        {

            context = LocalHelper.setLocale(AddMilkInfo.this, "an");
            resources = context.getResources();
            SearchOrders.setTitle(resources.getString(R.string.SearchOrders));
            OtherMilkMans.setTitle(resources.getString(R.string.OtherMilkMans));
            Reviews.setTitle(resources.getString(R.string.Reviews));
        }


        return true;


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.SearchOrders:


                Intent intent=new Intent(this,CustomerList1.class);
                intent.putExtra("var",vall);
                startActivity(intent);
                return true;
            case R.id.OtherMilkMans:
                Intent inten=new Intent(this,MilkManList2.class);
                inten.putExtra("val",vall);
                startActivity(inten);

                return true;
            case R.id.Reviews:
                Intent inte=new Intent(this,showReview.class);
                inte.putExtra("val",vall);
                startActivity(inte);

                return true;
            default:
                return super.onOptionsItemSelected(item);


        }





    }
}
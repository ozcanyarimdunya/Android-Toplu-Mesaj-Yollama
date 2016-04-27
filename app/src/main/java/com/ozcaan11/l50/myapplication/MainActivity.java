package com.ozcaan11.l50.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    MyCustomAdapter dataAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        displayListView();

        checkButtonClick();

    }

    private void checkButtonClick() {
        Button myButton = (Button) findViewById(R.id.btnSend);
        final EditText editText = (EditText) findViewById(R.id.editText);

        myButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<People> peopleList = dataAdapter.peopleList;
                String text = editText.getText().toString();
                if (text.contains("[ISIM]")) {
                    for (int i = 0; i < peopleList.size(); i++) {
                        People people = peopleList.get(i);
                        if (people.isSelected()) {
                            String gonderilecekMesaj = String.valueOf(text).replace("[ISIM]", people.getAd());

                            /*
                            * Bu bazı durumlarda hata verebilir
                            * Güvenlik önlemi olsun diye böyle birşey yapmışlar
                            * Ama eğer maxSdk ve targetSdk düşürülürse
                            * herhangi bir sıkıntı vermeden çalışabilir
                            * Veya build.gradle ile hiç uğraşmadan checkSelfPermission
                            * fonksiyonu tanımlanıp kullanırsa bu sefer kullanıcıdan izin isteyip
                            * bu şekilde toplu mesaj atacaktır
                            * */

                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(people.getTelefon(), null, gonderilecekMesaj, null, null);
                            Toast.makeText(MainActivity.this, "Tamamdır!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "En az bir kişi seçin ..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

    }

    private void displayListView() {
        ArrayList<People> peopleList = new ArrayList<>();
        People people;
        people = new People("Özcan", "Yarımdünya", "+905556667786", false);
        peopleList.add(people);
        people = new People("Hazal", "Kaya", "+905556667788", true);
        peopleList.add(people);
        people = new People("Meriç", "Keskin", "+905556667787", true);
        peopleList.add(people);


        dataAdapter = new MyCustomAdapter(this, R.layout.people_info, peopleList);
        ListView listView = (ListView) findViewById(R.id.lvPeople);
        listView.setAdapter(dataAdapter);
    }

    private class MyCustomAdapter extends ArrayAdapter<People> {
        private ArrayList<People> peopleList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<People> peopleList) {
            super(context, textViewResourceId, peopleList);
            this.peopleList = new ArrayList<>();
            this.peopleList.addAll(peopleList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.people_info, null);

                holder = new ViewHolder();
                holder.ad = (TextView) convertView.findViewById(R.id.txtAd);
                holder.soyad = (TextView) convertView.findViewById(R.id.txtSoyad);
                holder.telefon = (TextView) convertView.findViewById(R.id.txtTelefon);
                holder.checkedName = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.checkedName.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        People people = (People) cb.getTag();
                        people.setSelected(cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            People people = peopleList.get(position);
            holder.checkedName.setText(people.getAd() + " " + people.getSoyad() + " (" + people.getTelefon() + ")");
            holder.checkedName.setChecked(people.isSelected());
            holder.checkedName.setTag(people);

            return convertView;

        }

        private class ViewHolder {

            TextView ad, soyad, telefon;
            CheckBox checkedName;
        }
    }


}

package com.shahdat.ledger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {

    LinearLayout main;
    TextView balanceText;
    ArrayList<String> entries = new ArrayList<>();
    double balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    TextView title(String text) {
        TextView t = new TextView(this);
        t.setText(text);
        t.setTextSize(24);
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(10, 25, 10, 25);
        t.setBackgroundColor(Color.rgb(46,125,50));
        return t;
    }

    Button button(String text) {
        Button b = new Button(this);
        b.setText(text);
        b.setTextSize(17);
        return b;
    }

    void base(String heading) {
        main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setPadding(20, 20, 20, 20);
        main.setBackgroundColor(Color.rgb(245,245,245));
        main.addView(title(heading));
        setContentView(main);
    }

    void showHome() {
        base("Shahdat Ledger");

        TextView account = new TextView(this);
        account.setText("\nDelta Energy\nAccount No: 9028C");
        account.setTextSize(20);
        account.setPadding(20,30,20,30);
        main.addView(account);

        balanceText = new TextView(this);
        balanceText.setText("Balance: Rs. " + balance);
        balanceText.setTextSize(24);
        balanceText.setPadding(20,20,20,30);
        main.addView(balanceText);

        Button entry = button("➕ New Entry");
        main.addView(entry);
        entry.setOnClickListener(v -> showNewEntry());

        Button ledger = button("📒 Ledger");
        main.addView(ledger);
        ledger.setOnClickListener(v -> showLedger());
    }

    void showNewEntry() {
        base("New Entry");

        EditText detail = new EditText(this);
        detail.setHint("Details");
        main.addView(detail);

        EditText amount = new EditText(this);
        amount.setHint("Amount");
        amount.setInputType(2);
        main.addView(amount);

        RadioGroup type = new RadioGroup(this);
        RadioButton debit = new RadioButton(this);
        debit.setText("Payment Diya");
        RadioButton credit = new RadioButton(this);
        credit.setText("Bill Liya");
        type.addView(debit);
        type.addView(credit);
        debit.setChecked(true);
        main.addView(type);

        Button save = button("Save Entry");
        main.addView(save);

        save.setOnClickListener(v -> {
            String d = detail.getText().toString();
            String a = amount.getText().toString();

            if (d.isEmpty() || a.isEmpty()) {
                Toast.makeText(this, "Details aur amount likhein", Toast.LENGTH_SHORT).show();
                return;
            }

            double value = Double.parseDouble(a);

            if (debit.isChecked()) {
                balance += value;
                entries.add("Payment Diya | " + d + " | Rs. " + value);
            } else {
                balance -= value;
                entries.add("Bill Liya | " + d + " | Rs. " + value);
            }

            Toast.makeText(this, "Entry save ho gayi", Toast.LENGTH_SHORT).show();
            showHome();
        });

        Button back = button("← Back");
        main.addView(back);
        back.setOnClickListener(v -> showHome());
    }

    void showLedger() {
        base("Ledger");

        if (entries.isEmpty()) {
            TextView empty = new TextView(this);
            empty.setText("Abhi koi entry nahi hai.");
            empty.setTextSize(18);
            empty.setPadding(10,30,10,30);
            main.addView(empty);
        } else {
            for (String e : entries) {
                TextView row = new TextView(this);
                row.setText(e);
                row.setTextSize(17);
                row.setPadding(10,20,10,20);
                main.addView(row);
            }
        }

        TextView total = new TextView(this);
        total.setText("\nCurrent Balance: Rs. " + balance);
        total.setTextSize(21);
        main.addView(total);

        Button back = button("← Back");
        main.addView(back);
        back.setOnClickListener(v -> showHome());
    }
                            }

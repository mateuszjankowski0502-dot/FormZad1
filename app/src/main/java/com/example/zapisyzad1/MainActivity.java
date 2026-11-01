package com.example.zapisyzad1;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etEmail;
    private Button btnBirthDate;
    private Button btnSubmit;
    private Spinner spCourse;
    private RadioGroup rgMode;
    private SeekBar sbLevel;
    private TextView tvLevelValue;
    private TextView tvSummary;
    private MaterialSwitch swNewsletter;
    private CheckBox cbTerms;

    private String birthDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnBirthDate = findViewById(R.id.btnBirthDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        spCourse = findViewById(R.id.spCourse);
        rgMode = findViewById(R.id.rgMode);
        sbLevel = findViewById(R.id.sbLevel);
        tvLevelValue = findViewById(R.id.tvLevelValue);
        tvSummary = findViewById(R.id.tvSummary);
        swNewsletter = findViewById(R.id.swNewsletter);
        cbTerms = findViewById(R.id.cbTerms);

        setUpListeners();
        tvLevelValue.setText(String.valueOf(sbLevel.getProgress()));
    }

    private void setUpListeners() {
        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) ->
                btnSubmit.setEnabled(isChecked));

        btnBirthDate.setOnClickListener(v -> showDatePicker());

        btnSubmit.setOnClickListener(v -> showSummary());

        sbLevel.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress, boolean fromUser) {
                        tvLevelValue.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

        spCourse.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {
                        if (position > 0) {
                            tvSummary.setText("");
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    private void showSummary() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        String mode = "Nie wybrano";
        int checkedId = rgMode.getCheckedRadioButtonId();

        if (checkedId == R.id.rbOnsite) {
            mode = getString(R.string.mode_onsite);
        } else if (checkedId == R.id.rbRemote) {
            mode = getString(R.string.mode_remote);
        }

        String course = spCourse.getSelectedItem().toString();

        String newsletter = swNewsletter.isChecked()
                ? getString(R.string.yes_short)
                : getString(R.string.no_short);

        String summary = getString(
                R.string.summary_format,
                name,
                email,
                birthDate.isEmpty() ? "Nie wybrano" : birthDate,
                course,
                mode,
                sbLevel.getProgress(),
                newsletter
        );

        tvSummary.setText(summary);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    birthDate = String.format(
                            Locale.US,
                            "%04d-%02d-%02d",
                            year,
                            month + 1,
                            dayOfMonth
                    );

                    btnBirthDate.setText(birthDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }
}
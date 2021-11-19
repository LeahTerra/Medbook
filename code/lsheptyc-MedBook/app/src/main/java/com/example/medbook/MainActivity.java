////////////////////////////////////////////////////////////////////////
//
//  Name: Leah Sheptycki
//  Class: CMPUT 301 Section D01
//  SID: 1547619
//
////////////////////////////////////////////////////////////////////////
package com.example.medbook;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/*
    MainActivity Class:
     \- onCreate(): Constructor creates the main view and all the onClickListeners
     \- sanitizer(): Sanitizes all inputs, raises errors and doesn't proceed until solved
     \- clearInputs(): Cleans inputs after window is closed so it is clean for the next one
     \- clearBackground(): Cleans error notifications
     \- toggleAddMenu(): Opens and closes the add menu

    The purpose of MainActivity is... well... to be the main activity. It is a dynamic interface,
    with two main parts: MedList and the Add Menu.

    MedList is an object that contains all the medicine objects added, edited, or removed.
    The Add Menu is a pop up of sorts, that doesn't require a complete activity change.

    Issues: possibly adding more appropriate forms of inputs such as date picker or radio
    button group. Date can still be above the limit for days and months. And possibly having a more
    organized system for names.
 */

public class MainActivity extends AppCompatActivity {

    // Main Activity Initializations
    ListView medView;
    LinearLayout buttonBox;
    TextView dailyDoseView;

    // Medication Initializations
    MedicineList medList;
    MedAdapter medAdapter;
    ArrayList<Medication> medArray;
    Integer selected;

    // Button Box Initializations
    Button addButton;
    Button editButton;
    Button delButton;

    // Add Menu Initializations
    LinearLayout addMenu;
    TextView addTitle;
    TextView editTitle;
    // \- Add Menu Data Entry Initializations
    EditText addName;
    EditText addDate;
    EditText addDose;
    EditText addUnit;
    EditText addFrequency;
    // \- Add Menu Button Initializations
    Button addConfirm;
    Button addCancel;
    Button editConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initial Screen
        medView = findViewById(R.id.medView);
        buttonBox = findViewById(R.id.buttonBox);
        dailyDoseView = findViewById(R.id.doseView);

        // The button box with add, edit, and delete
        addButton = findViewById(R.id.Add);
        editButton = findViewById(R.id.Edit);
        delButton = findViewById(R.id.Delete);

        // The add a medication menu
        addMenu = findViewById(R.id.addMenu);
        addTitle = findViewById(R.id.addTitle);
        editTitle = findViewById(R.id.addTitle);
        // \- The add menu input fields
        addName = findViewById(R.id.addName);
        addDate = findViewById(R.id.addDate);
        addDose = findViewById(R.id.addDose);
        addUnit = findViewById(R.id.addUnit);
        addFrequency = findViewById(R.id.addFrequency);
        // \- The add menu buttons
        addConfirm = findViewById(R.id.addConfirm);
        editConfirm = findViewById(R.id.editConfirm);
        addCancel = findViewById(R.id.addCancel);

        // Creating our medicine list object, then retrieving it's array though getList()
        medList = new MedicineList();
        medArray = medList.getList();

        // Here we set our custom adapter, which refers to a child of AdapterArray I made
        // and can be found in MedAdapter.java
        medAdapter = new MedAdapter(this, medArray);
        medView.setAdapter(medAdapter);

        // Here we get our initial total doses that gets updated on any add, edit, or delete
        dailyDoseView.setText("Total Daily Doses: " + medList.getTotalDailyDoses());

        // Here is a makeshift selection mechanism, that stores the position of a selected item
        // in a list. I'm sure there are more efficient built in ways though.
        medView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView medAdapter, View v, int position, long m) {
                if (selected == null) {
                    selected = position;
                }
                else if (selected != null && selected == medAdapter.getItemAtPosition(position)) {
                    selected = null;
                }
                else if (selected != null && selected != medAdapter.getItemAtPosition(position)) {
                    selected = position;
                }
            }
        });
        // Here we set the onClickListeners for our add button. This opens up the add view
        // as well as double checks that it's in add mode.
        // Add and Edit reuse the same view, save for the title at the top, and the confirm button
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toggleAddMenu();
                addConfirm.setVisibility(View.VISIBLE);
                editConfirm.setVisibility(View.GONE);
            }
        });
        // Edit button onClickListener. Opens up add view, but fills values with selected items
        // information to allow for edits. Inputs are still sanitized upon hitting confirm.
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selected != null) {
                    ArrayList<String> medA = medList.getMed(selected);

                    addDate.setText(medA.get(0));
                    addName.setText(medA.get(1));
                    addDose.setText(medA.get(2));
                    addUnit.setText(medA.get(3));
                    addFrequency.setText(medA.get(4));

                    addConfirm.setVisibility(View.GONE);
                    editConfirm.setVisibility(View.VISIBLE);
                    addTitle.setVisibility(View.GONE);
                    editTitle.setVisibility(View.VISIBLE);
                    toggleAddMenu();
                }
            }
        });
        // Delete button onClick Listener. It removes the medicine from the medList and the
        // adapter, and then refreshes the total number of doses in a day.
        delButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                medList.delMed(selected);
                medAdapter.remove(medAdapter.getItem(selected));
                dailyDoseView.setText("Total Daily Doses: " + medList.getTotalDailyDoses());
                medAdapter.notifyDataSetChanged();
            }
        });
        // Cancel button onClickListener that discards inputs and closes add menu
        addCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearInputs();
                toggleAddMenu();
            }
        });

        // Confirm button onClickListener that sanitizes inputs, closes menu, and adds new medication
        // object to our medList.
        addConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearBackground();
                int clear = sanitizer();
                if (clear == 0) {
                    medList.addMed(String.valueOf(addDate.getText()), String.valueOf(addName.getText()),
                            Integer.parseInt(String.valueOf(addDose.getText())), String.valueOf(addUnit.getText()),
                            Integer.parseInt(String.valueOf(addFrequency.getText())));

                    clearInputs();
                    toggleAddMenu();
                    dailyDoseView.setText("Total Daily Doses: " + medList.getTotalDailyDoses());
                    medAdapter.notifyDataSetChanged();

                }

            }

        });
        // The "editConfirm" onClickListener. To save time, instead of creating a new window for edit,
        // I reused the structure of the add menu and just switched out the title and the button.
        // The button has to be different as it changes whether its updates existing or adds.
        editConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearBackground();
                int clear = sanitizer();
                if (clear == 0) {

                    medList.editMed(selected, String.valueOf(addDate.getText()), String.valueOf(addName.getText()),
                            Integer.parseInt(String.valueOf(addDose.getText())), String.valueOf(addUnit.getText()),
                            Integer.parseInt(String.valueOf(addFrequency.getText())));

                    clearInputs();
                    toggleAddMenu();
                    dailyDoseView.setText("Total Daily Doses: " + medList.getTotalDailyDoses());
                    medAdapter.notifyDataSetChanged();
                }

            }

        });
    }

    // Sanitize input method. It goes through each input field and assures that it is following
    // the proper format. Could be improved with a date picker and a radio button group.
    // Currently the date can be 9999-99-99 so it unfortunately doesn't check each input specifically
    public int sanitizer() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int clear = 0;
        if ((addName.getText().length() > 40 ) || (addName.getText().length() == 0 )) {
            addName.setBackgroundColor(Color.argb( 45, 179, 41, 41));
            clear++;
        }
        try {
            dateFormat.parse(String.valueOf(addDate.getText()));
        } catch (ParseException e) {
            addDate.setBackgroundColor(Color.argb( 45, 179, 41, 41));
            clear++;
        }
        if ((String.valueOf(addDose.getText()).length() == 0) || (Integer.parseInt(String.valueOf(addDose.getText())) <= 0)) {
            addDose.setBackgroundColor(Color.argb( 45, 179, 41, 41));
            clear++;
        }

        if ((!String.valueOf(addUnit.getText()).equals("mg")) && (!String.valueOf(addUnit.getText()).equals("mcg")) && (!String.valueOf(addUnit.getText()).equals("drops"))) {
            addUnit.setBackgroundColor(Color.argb( 45, 179, 41, 41));
            clear++;
        }
        if ((String.valueOf(addDose.getText()).length() == 0) || Integer.parseInt(String.valueOf(addDose.getText())) <= 0) {
            addFrequency.setBackgroundColor(Color.argb( 45, 179, 41, 41));
            clear++;
        }
        return clear;
    }
    // As the name suggests, this clears the input so that when the add menu is opened, it is clean.
    public void clearInputs() {
        addName.getText().clear();
        addDate.getText().clear();
        addDose.getText().clear();
        addUnit.getText().clear();
        addFrequency.getText().clear();

        addButton.setVisibility(View.VISIBLE);
        editConfirm.setVisibility(View.GONE);
        addTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);
        clearBackground();

    }
    // Upon error, the error field has background changed to red. This cleans it all up.
    public void clearBackground() {
        addName.setBackgroundColor(Color.TRANSPARENT);
        addDate.setBackgroundColor(Color.TRANSPARENT);
        addDose.setBackgroundColor(Color.TRANSPARENT);
        addUnit.setBackgroundColor(Color.TRANSPARENT);
        addFrequency.setBackgroundColor(Color.TRANSPARENT);
    }
    // A toggle for showing either the main menu or the add menu.
    public void toggleAddMenu() {

        if (addMenu.isShown() == true) {
            addMenu.setVisibility(View.GONE);
            medView.setVisibility(View.VISIBLE);
            dailyDoseView.setVisibility(View.VISIBLE);
            buttonBox.setVisibility(View.VISIBLE);
        }
        else if (addMenu.isShown() == false) {
            addMenu.setVisibility(View.VISIBLE);
            medView.setVisibility(View.GONE);
            dailyDoseView.setVisibility(View.GONE);
            buttonBox.setVisibility(View.GONE);
        }

    }

}


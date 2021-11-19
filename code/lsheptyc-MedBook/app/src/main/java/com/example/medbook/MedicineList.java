////////////////////////////////////////////////////////////////////////
//
//  Name: Leah Sheptycki
//  Class: CMPUT 301 Section D01
//  SID: 1547619
//
////////////////////////////////////////////////////////////////////////

package com.example.medbook;

import java.util.ArrayList;

/*
    MedicineList Class
    \- MedicineList(): Constructor, creates the initial ArrayList
    \- addMed(dateStart, name, dose, doseUnit, frequency): As the name implies, it adds a med into the array
    \- editMed(position, dateStart, name, dose, doseUnit, frequency): Takes position to pick which medication,
               then calls the medication setter function setInfo()
    \- delMed(position): deletes medication at position'
    \- getMed(position): returns string format array of information from the medication for ease of use in editing
    \- getList(): returns raw object list for use in array adapter
    \- getTotalDailyDoses(): cycles through medicine in list and adds total dose for display

    The idea of this class is to be a container for medicine. While its purpose could theoretically
    be made within the main activity class, I created it separately for any methods that work on the many
    instead of the one, as well as adding the is the possibility for multiple lists within the app.


 */

public class MedicineList {
    private ArrayList<Medication> medList;

    // Constructor
    public MedicineList() {
        this.medList = new ArrayList<>();
    }

    // Takes input, creates new medicine object, then adds to the array
    public void addMed(String dateStart, String name, Integer dose, String doseUnit, Integer frequency) {
        Medication med = new Medication(dateStart, name, dose, doseUnit, frequency);
        medList.add(med);
    }

    // Gets position of item in array, then uses object method .setInfo()
    public void editMed(Integer position, String dateStart, String name, Integer dose, String doseUnit, Integer frequency) {
        medList.get(position).setInfo(dateStart, name, dose, doseUnit, frequency);
    }

    // Removes from given position
    public void delMed(Integer position) {
        this.medList.remove(position);
    }
    // Returns specific med object at position
    public ArrayList<String> getMed(Integer position) {
        return medList.get(position).getInfo();
    }

    // Returns raw list of objects
    public ArrayList<Medication> getList() {
        return medList;
    }

    // Interates and adds up all the frequency values and returns them
    public Integer getTotalDailyDoses() {
        Integer totalDoses = 0;
        int size = medList.size();
        for (int i = 0; i < size; i++) {
            totalDoses = totalDoses + medList.get(i).getFrequency();
        }
        return totalDoses;
    }
}

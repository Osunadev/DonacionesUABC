package com.example.donacionesuabc;

/**
 * Esta clase tambien es para los spinners, disculpa que no sepa
 * bien como funciona, pero fue de las primeras que hice
 */

public class CustomItems {
    private String spinnerText;
    private int spinnerImage;

    public CustomItems(String spinnerText, int spinnerImage) {
        this.spinnerText = spinnerText;
        this.spinnerImage = spinnerImage;
    }

    public String getSpinnerText() {
        return spinnerText;
    }

    public int getSpinnerImage() {
        return spinnerImage;
    }
}

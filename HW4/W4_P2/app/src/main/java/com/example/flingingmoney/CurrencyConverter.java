package com.example.flingingmoney;

public class CurrencyConverter {
    private final double Euro=1,
            Dollar=1.08,
            Yen=119.01,
            Yuan=7.56,
            Pound=0.83;
    private double currency;

    public CurrencyConverter(){
        currency = 0;
    }

    public void setCurrency(double money){
        currency = money;
    }
    public void addDollar(){
        currency++;
    }
    public void minDollar(){
        currency--;
        if (currency<0)
            currency = 0;
    }
    public void addCent(){
        currency+=0.05;
    }
    public void minCent(){
        currency-=0.05;
        if (currency<0)
            currency = 0;
    }

    public String getEuro(){
        return String.format("%.2f", currency);
    }
    public String getDollar(){
        return String.format("%.2f", currency*Dollar);
    }
    public String getYen(){
        return String.format("%.2f", currency*Yen);
    }
    public String getYuan(){
        return String.format("%.2f", currency*Yuan);
    }
    public String getPound(){
        return String.format("%.2f", currency*Pound);
    }
}

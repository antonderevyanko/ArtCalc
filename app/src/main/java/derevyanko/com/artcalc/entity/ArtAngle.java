package derevyanko.com.artcalc.entity;

import derevyanko.com.artcalc.exception.WrongFormatException;

/**
 * Created by anton on 9/22/15.
 */
public final class ArtAngle {

    private int valueRough;
    private int valuePrecise;
    private boolean isNegative;


    public ArtAngle(int valueRough, int valuePrecise) throws WrongFormatException{
        this.valueRough = valueRough;
        this.valuePrecise = valuePrecise;

        isNegative = valueRough < 0;
    }

    public ArtAngle(int intValue) {
        valueRough = Math.abs(intValue / 100);
        valuePrecise = Math.abs(intValue - intValue / 100 * 100);
        isNegative = intValue < 0;
    }

    public boolean isGreaterZero() {
        return !isNegative;
    }

    public int getAsInt() {
        if (isNegative) {
            return - valueRough * 100 + valuePrecise;
        } else {
            return valueRough * 100 + valuePrecise;
        }
    }

    public void setFromInt(int input) {
        valueRough = input / 100;
        valuePrecise = input - valueRough * 100;

        isNegative = input < 0;
    }

    public int getValueRough() {
        return valueRough;
    }

    public int getValuePrecise() {
        return valuePrecise;
    }

    public String getOnlyNumbers() {
        return String.valueOf(valueRough) + "-" + String.valueOf(valuePrecise);
    }

    @Override
    public String toString() {
        return "Value rough= " + valueRough +
                ", value precise= " + valuePrecise;
    }
}

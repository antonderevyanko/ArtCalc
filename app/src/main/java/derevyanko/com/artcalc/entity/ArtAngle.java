package derevyanko.com.artcalc.entity;

import derevyanko.com.artcalc.exception.WrongFormatException;

/**
 * Created by anton on 9/22/15.
 */
public final class ArtAngle {

    private int valueRough;
    private int valuePrecise;

    public ArtAngle(int valueRough, int valuePrecise) throws WrongFormatException{
        this.valueRough = valueRough;
        this.valuePrecise = valuePrecise;
    }

    public boolean isGreaterZero() {
        if (getValueRough() == 0) {
            return getValuePrecise() > 0;
        } else {
            return getValueRough() > 0;
        }
    }

    public int getValueRough() {
        return valueRough;
    }

    public void setValueRough(int valueRough) {
        this.valueRough = valueRough;
    }

    public int getValuePrecise() {
        return valuePrecise;
    }

    public void setValuePrecise(int valuePrecise) {
        this.valuePrecise = valuePrecise;
    }
}

package derevyanko.com.artcalc.entity;

import android.content.Context;
import derevyanko.com.artcalc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anton on 9/23/15.
 */
public final class DistanceTable {

    private final List<DistanceData> distanceDatas;

    public DistanceTable(Context context) {
        // prepare data
        int[] distancies = context.getResources().getIntArray(R.array.distancies);
        int[] angle_rough = context.getResources().getIntArray(R.array.angles_rough);
        int[] angle_precise = context.getResources().getIntArray(R.array.angles_precise);

        distanceDatas = new ArrayList<>();

        for (int i = 0; i < distancies.length; i++) {
            distanceDatas.add(new DistanceData(distancies[i], new ArtAngle(angle_rough[i], angle_precise[i])));
        }
    }

    public ArtAngle getProperAngle(int distance) {
        ArtAngle result = distanceDatas.get(0).getArtAngle();
        int minDelta = Math.abs(distanceDatas.get(0).distance - distance);
        int minIndex = 0;
        // finding nearest index position
        for (int i = 0; i < distanceDatas.size(); i++) {
            if (Math.abs(distanceDatas.get(i).distance - distance) < minDelta) {
                result = distanceDatas.get(i).getArtAngle();
                minDelta = Math.abs(distanceDatas.get(i).distance - distance);
                minIndex = i;
            }
        }

        if (minIndex == 0 || minIndex == distanceDatas.size() - 1) {
            return result;
        }

        int delta = distanceDatas.get(minIndex).distance - distance;
        DistanceData firstPoint;
        DistanceData secondPoint;
        // we need two points to calculate middle value
        if (delta >= 0) {
            firstPoint = distanceDatas.get(minIndex);
            secondPoint = distanceDatas.get(minIndex - 1);
        } else {
            firstPoint = distanceDatas.get(minIndex - 1);
            secondPoint = distanceDatas.get(minIndex);
        }

        // all formula
        // x-x0/x1-x0 = y-y0/y1-y0

        // x-x0/x1-x0
        float x = (float) (distance - firstPoint.distance) / (secondPoint.distance - firstPoint.distance);
        int intResult = (int) (firstPoint.getArtAngle().getAsInt() + x *
                        (secondPoint.getArtAngle().getAsInt() - firstPoint.getArtAngle().getAsInt()));
        result.setFromInt(intResult);

        return result;
    }

    private class DistanceData {
        int distance;
        ArtAngle artAngle;

        public DistanceData(int distance, ArtAngle artAngle) {
            this.distance = distance;
            this.artAngle = artAngle;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public ArtAngle getArtAngle() {
            return artAngle;
        }

        public void setArtAngle(ArtAngle artAngle) {
            this.artAngle = artAngle;
        }
    }
}

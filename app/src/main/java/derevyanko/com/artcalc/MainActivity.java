package derevyanko.com.artcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import derevyanko.com.artcalc.entity.ArtAngle;
import derevyanko.com.artcalc.entity.DistanceTable;
import derevyanko.com.artcalc.exception.WrongFormatException;
import derevyanko.com.artcalc.view.AngleInputView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {

    private static final int VERT_CORRECTION = 3000;
    @Bind(R.id.angleMainDirection)
    AngleInputView inputMainDirection;
    @Bind(R.id.angleTargetAzimuth)
    AngleInputView inputTargetAzimuth;
    @Bind(R.id.edtDistance)
    EditText inputDistance;
    @Bind(R.id.tvResults)
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inputMainDirection.setTitle(getResources().getString(R.string.main_direction_angle));
        inputMainDirection.setViewToFocus(inputTargetAzimuth);
        inputTargetAzimuth.setTitle(getResources().getString(R.string.target_azimuth_angle));
        inputTargetAzimuth.setViewToFocus(inputDistance);
        inputDistance.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onCalculate();
                    return true;
                }
                return false;
            }
        });
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnCalculate)
    void onCalculate() {
        try {
            ArtAngle angleMainDirection = inputMainDirection.getAngleData();
            ArtAngle angleTargetAzimuth = inputTargetAzimuth.getAngleData();

            // todo re-check minus operation
            int minusOperation = angleTargetAzimuth.getAsInt() - angleMainDirection.getAsInt();
            ArtAngle angleWeaponRotation = new ArtAngle(minusOperation);

            StringBuilder stringBuilder = new StringBuilder(getResources().getString(R.string.results));
            stringBuilder.append("\n");

            if (angleMainDirection.isGreaterZero()) {
                stringBuilder
                        .append(getString(R.string.to_right))
                        .append(angleWeaponRotation.getOnlyNumbers())
                        .append("\n");
            } else {
                stringBuilder
                        .append(getString(R.string.to_left))
                        .append(angleWeaponRotation.getOnlyNumbers())
                        .append("\n");
            }

            DistanceTable distanceTable = new DistanceTable(this);
            int distance;
            try {
                distance = Integer.decode(inputDistance.getText().toString());
            } catch (NumberFormatException e) {
                makeText(this, "Wrong distance input", LENGTH_SHORT).show();
                distance = 0;
            }

            ArtAngle angleDistance = distanceTable.getProperAngle(distance);
            ArtAngle angleWithCorrection = new ArtAngle(angleDistance.getAsInt() + VERT_CORRECTION);
            stringBuilder
                    .append(getString(R.string.level))
                    .append(angleWithCorrection.getOnlyNumbers());

            textResult.setText(stringBuilder.toString());
        } catch (WrongFormatException exception) {
            makeText(this, exception.getErrorCause(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

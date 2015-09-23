package derevyanko.com.artcalc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import derevyanko.com.artcalc.entity.ArtAngle;
import derevyanko.com.artcalc.exception.WrongFormatException;
import derevyanko.com.artcalc.view.AngleInputView;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.angleMainDirection)
    AngleInputView inputMainDirection;
    @Bind(R.id.angleTargetAzimuth)
    AngleInputView inputTargetAzimuth;
    @Bind(R.id.tvResults)
    TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        inputMainDirection.setTitle("Main direction angle");
        inputTargetAzimuth.setTitle("Target azimuth angle");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnCalculate)
    void onCalculate() {
        try {
            ArtAngle angleMainDirection = inputMainDirection.getAngleData();
            ArtAngle angleTargetAzimuth = inputTargetAzimuth.getAngleData();

            // todo re-check minus operation
            ArtAngle angleWeaponRotation = new ArtAngle(
                    angleTargetAzimuth.getValueRough() - angleMainDirection.getValueRough(),
                    angleTargetAzimuth.getValuePrecise() - angleMainDirection.getValuePrecise());

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
            stringBuilder.append("\n");

            textResult.setText(stringBuilder.toString());
        } catch (WrongFormatException exception) {
            Toast.makeText(this, exception.getErrorCause(), Toast.LENGTH_SHORT).show();
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

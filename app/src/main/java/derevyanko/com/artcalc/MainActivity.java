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
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnCalculate)
    void onCalculate() {
        try {
            ArtAngle angleMainDirection = inputMainDirection.getAngleData();
            ArtAngle angleTargetAzimuth = inputTargetAzimuth.getAngleData();

            ArtAngle angleWeaponRotation = new ArtAngle(
                    angleTargetAzimuth.getValueRough() - angleMainDirection.getValueRough(),
                    angleTargetAzimuth.getValuePrecise() - angleMainDirection.getValuePrecise());

            StringBuilder stringBuilder = new StringBuilder(getResources().getString(R.string.results));
            stringBuilder
                    .append("\n")
                    .append(getString(R.string.main_direction_angle))
                    .append(angleMainDirection.toString())
                    .append("\n")
                    .append(getString(R.string.target_azimuth_angle))
                    .append(angleTargetAzimuth.toString())
                    .append("\n")
                    .append(getString(R.string.weapon_rotation_angle))
                    .append(angleWeaponRotation.toString())
                    .append("\n");
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

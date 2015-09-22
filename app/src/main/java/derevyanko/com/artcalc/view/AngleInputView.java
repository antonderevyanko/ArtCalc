package derevyanko.com.artcalc.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import derevyanko.com.artcalc.R;
import derevyanko.com.artcalc.entity.ArtAngle;
import derevyanko.com.artcalc.exception.WrongFormatException;

/**
 * Created by anton on 9/22/15.
 */
public final class AngleInputView extends RelativeLayout {

    private static final int MAX_ROUGH = 59;
    private static final int MAX_PRECISE = 99;
    @Bind(R.id.tvAngleViewTitle)
    TextView title;
    @Bind(R.id.inputRough)
    EditText rough;
    @Bind(R.id.inputPrecise)
    EditText precise;

    public AngleInputView(Context context) {
        super(context);
        initView(context);
    }

    public AngleInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AngleInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setValue(ArtAngle artAngle) {
        rough.setText(String.valueOf(artAngle.getValueRough()));
        precise.setText(String.valueOf(artAngle.getValuePrecise()));
    }

    public ArtAngle getAngleData() throws WrongFormatException {
        try {
            int dataRough = 0;
            int dataPrecise = 0;

            if (!rough.getText().toString().isEmpty()) {
                dataRough = Integer.getInteger(rough.getText().toString());
            }

            if (!precise.getText().toString().isEmpty()) {
                dataPrecise = Integer.getInteger(precise.getText().toString());
            }

            if (Math.abs(dataRough) > MAX_ROUGH) {
                throw new WrongFormatException(String.format(getContext().getString(R.string.error_wrong_rough),
                        MAX_ROUGH, dataRough));
            }

            if (Math.abs(dataPrecise) > MAX_PRECISE) {
                throw new WrongFormatException(String.format(getContext().getString(R.string.error_wrong_precise),
                        MAX_PRECISE, dataPrecise));
            }

            return new ArtAngle(dataRough, dataPrecise);
        } catch (NullPointerException e) {
            throw new WrongFormatException(getContext().getString(R.string.error_read_data));
        }
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_angle_input, this);
        ButterKnife.bind(this);
    }
}
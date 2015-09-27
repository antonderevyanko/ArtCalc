package derevyanko.com.artcalc.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import derevyanko.com.artcalc.R;
import derevyanko.com.artcalc.entity.ArtAngle;
import derevyanko.com.artcalc.exception.WrongFormatException;

import static java.lang.String.valueOf;

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

    private View viewToFocus;

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
        rough.setText(valueOf(artAngle.getValueRough()));
        precise.setText(valueOf(artAngle.getValuePrecise()));
    }

    public void setViewToFocus(View view) {
        this.viewToFocus = view;
        precise.addTextChangedListener(new GenericTextWatcher(viewToFocus));
    }

    public ArtAngle getAngleData() throws WrongFormatException {
        try {
            int dataRough = 0;
            int dataPrecise = 0;

            if (!rough.getText().toString().isEmpty()) {
                dataRough = Integer.decode(rough.getText().toString());
            }

            if (!precise.getText().toString().isEmpty()) {
                dataPrecise = Integer.decode(precise.getText().toString());
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
        rough.setOnEditorActionListener(actionListener);
        precise.setOnEditorActionListener(secondaryListener);
        rough.addTextChangedListener(new GenericTextWatcher(precise));
    }

    private final TextView.OnEditorActionListener actionListener = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                precise.requestFocus();
                return true;
            }
            return false;
        }
    };

    private final TextView.OnEditorActionListener secondaryListener = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_NEXT && viewToFocus != null) {
                viewToFocus.requestFocus();
                return true;
            }
            return false;
        }
    };

    private class GenericTextWatcher implements TextWatcher{

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        public void afterTextChanged(Editable editable) {
            if (editable.length() == 2) {
                view.requestFocus();
            }
        }
    }

}

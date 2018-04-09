package hu.ait.android.wheresthepartyat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.wheresthepartyat.data.Party;

public class CreatePartyActivity extends FragmentActivity {

    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etDescription)
    EditText etDescription;
    @BindView(R.id.cbBYOB)
    CheckBox cbBYOB;
    @BindView(R.id.cbBar)
    CheckBox cbBar;

    private static final String TAG = "CreatePartyActivity";
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);

        ButterKnife.bind(this);

        SupportPlaceAutocompleteFragment autocompleteFragment = (
                SupportPlaceAutocompleteFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                latLng = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                Log.i(TAG, getString(R.string.error_occurred) + status);
            }
        });

    }

    @OnClick(R.id.btnShare)
    void shareClick(){

        if(etName.getText().toString().equals("")) {
            Toast.makeText(CreatePartyActivity.this, R.string.named_error,
                    Toast.LENGTH_SHORT).show();
        }else if (etDescription.getText().toString().equals("")){
                Toast.makeText(CreatePartyActivity.this, R.string.description_error,
                        Toast.LENGTH_SHORT).show();
        }else if (latLng==null){
            Toast.makeText(CreatePartyActivity.this, R.string.location_error,
                    Toast.LENGTH_SHORT).show();
        }else {

            //save all the info in firebase
            String newKey = FirebaseDatabase.getInstance().
                    getReference().child(getString(R.string.parties)).push().getKey();

            Party newParty = new Party(
                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                    etName.getText().toString(),
                    etDescription.getText().toString(),
                    cbBYOB.isChecked(),
                    cbBar.isChecked(),
                    latLng
            );

            FirebaseDatabase.getInstance().getReference().
                    child("parties").child(newKey).setValue(newParty).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(CreatePartyActivity.this, R.string.success,
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CreatePartyActivity.this,
                                        "Error: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }
}

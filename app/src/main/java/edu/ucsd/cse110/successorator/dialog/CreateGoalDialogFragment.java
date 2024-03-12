package edu.ucsd.cse110.successorator.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringOneTime;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;

public class CreateGoalDialogFragment extends DialogFragment {
    private FragmentDialogCreateGoalBinding view;
    private MainViewModel activityModel;

    CreateGoalDialogFragment() {}

    public static CreateGoalDialogFragment newInstance() {
        var fragment = new CreateGoalDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());

        view.focusOptions.setOnClickListener(v -> {
            Log.i("s", "ss");
        });
        view.focusOptions.setOnCheckedChangeListener((group, checkedId) -> {
            Log.i("sdff", "clicked");
            view.HButton.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.unselected_color));
            view.WButton.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.unselected_color));
            view.SButton.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.unselected_color));
            view.EButton.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.unselected_color));

            RadioButton rb = (RadioButton) group.findViewById(checkedId);
            switch (rb.getText().toString()) {
                case "H":
                    rb.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.H_color));
                    break;
                case "W":
                    rb.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.W_color));
                    break;
                case "S":
                    rb.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.S_color));
                    break;
                case "E":
                    rb.setBackgroundTintList( ContextCompat.getColorStateList(getContext(), R.color.E_color));
                    break;
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setTitle("New Goal")
                .setMessage("Please type in your goal (MIT).")
                .setView(view.getRoot())
                .setPositiveButton("Create", this::onPositiveButtonClick)
                .setNegativeButton("Cancel", this::onNegativeButtonClick)
                .create();
    }

    private Date getDateWithCurrentTime(SuccessDate setToTime, Date currentTimeDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTimeDate);
        calendar.set(Calendar.YEAR, setToTime.getYear());
        calendar.set(Calendar.MONTH, setToTime.getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, setToTime.getDay());
        return calendar.getTime();
    }

    private void onPositiveButtonClick(DialogInterface dialog, int which) {
        var front = view.cardEditText.getText().toString();
        Date date = getDateWithCurrentTime(activityModel.getTodayDate().getValue(), new Date());
        Log.e("date", date.toString());

        if (front.strip().length() <= 0) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    getContext());
            alertDialogBuilder.setMessage("Goal title cannot be empty");
            alertDialogBuilder.create().show();
            return;
        }

        RadioButton selectedFocusRb = view.focusOptions.findViewById(view.focusOptions.getCheckedRadioButtonId());
        String selectedFocusString = selectedFocusRb.getTag().toString();

        RadioButton selectedRecurringRb = view.RecurringOptions.findViewById(view.RecurringOptions.getCheckedRadioButtonId());
        String selectedRecurringString = selectedRecurringRb.getTag().toString();

        var goal = new Goal(front, null, false, false, date, date, RepeatType.valueOf(selectedRecurringString), selectedFocusString);

        activityModel.putGoal(goal);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}

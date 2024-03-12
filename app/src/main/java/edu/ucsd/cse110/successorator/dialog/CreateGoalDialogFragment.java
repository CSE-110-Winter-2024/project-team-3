package edu.ucsd.cse110.successorator.dialog;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import edu.ucsd.cse110.successorator.DisplayGoalType;
import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.FocusType;
import edu.ucsd.cse110.successorator.lib.domain.Goal;
import edu.ucsd.cse110.successorator.lib.domain.RecurringDaily;
import edu.ucsd.cse110.successorator.lib.domain.RecurringMonthly;
import edu.ucsd.cse110.successorator.lib.domain.RecurringOneTime;
import edu.ucsd.cse110.successorator.lib.domain.RecurringWeekly;
import edu.ucsd.cse110.successorator.lib.domain.RecurringYearly;
import edu.ucsd.cse110.successorator.lib.domain.RepeatType;
import edu.ucsd.cse110.successorator.lib.domain.SuccessDate;

public class CreateGoalDialogFragment extends DialogFragment {
    private FragmentDialogCreateGoalBinding view;
    private MainViewModel activityModel;
    final Calendar myCalendar = Calendar.getInstance();

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

    private SuccessDate createSuccessDate() {
        return SuccessDate.fromJavaDate(myCalendar.getTime());
    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        view.assignDatePicker.setText(dateFormat.format(myCalendar.getTime()));

        SuccessDate createAssignDateTemp = createSuccessDate();

        view.dailyOption.setText(RecurringDaily.getStaticDescription(createAssignDateTemp));
        view.weeklyOption.setText(RecurringWeekly.getStaticDescription(createAssignDateTemp));
        view.monthlyOption.setText(RecurringMonthly.getStaticDescription(createAssignDateTemp));
        view.yearlyOption.setText(RecurringYearly.getStaticDescription(createAssignDateTemp));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        this.view = FragmentDialogCreateGoalBinding.inflate(getLayoutInflater());

        view.focusOptions.setOnCheckedChangeListener((group, checkedId) -> {
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

        SuccessDate createAssignDateTemp = activityModel.getTodayDate().getValue();


        view.datePickerWrapper.setVisibility(View.GONE);
        switch (Objects.requireNonNull(activityModel.getDisplayGoalType().getValue())) {
            case TOMORROW:
                createAssignDateTemp = createAssignDateTemp.nextDay();
            case TODAY:
                break;
            case PENDING:
                view.dailyOption.setVisibility(View.GONE);
                view.weeklyOption.setVisibility(View.GONE);
                view.monthlyOption.setVisibility(View.GONE);
                view.yearlyOption.setVisibility(View.GONE);
                break;
            case RECURRING:
                view.datePickerWrapper.setVisibility(View.VISIBLE);
                view.oneTimeOption.setVisibility(View.GONE);
                break;
        }



        myCalendar.set(Calendar.YEAR, createAssignDateTemp.getYear());
        myCalendar.set(Calendar.MONTH, createAssignDateTemp.getMonth() - 1);
        myCalendar.set(Calendar.DAY_OF_MONTH, createAssignDateTemp.getDay());
        updateLabel();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH,month);
            myCalendar.set(Calendar.DAY_OF_MONTH,day);
            updateLabel();
        };

        view.assignDatePicker.setOnClickListener(v -> {
            new DatePickerDialog(
                    getContext(),
                    dateSetListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show();
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

        Date date = getDateWithCurrentTime(createSuccessDate(), new Date());
        if (activityModel.getDisplayGoalType().getValue() == DisplayGoalType.PENDING) {
            date = null;
        }


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

        var goal = new Goal(front, null, false, false, date, date, RepeatType.valueOf(selectedRecurringString), FocusType.valueOf(selectedFocusString));

        activityModel.putGoal(goal);

        dialog.dismiss();
    }

    private void onNegativeButtonClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }
}

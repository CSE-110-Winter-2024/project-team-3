package edu.ucsd.cse110.successorator;

import static android.app.PendingIntent.getActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.successorator.databinding.ActivityMainBinding;
import edu.ucsd.cse110.successorator.databinding.FragmentDialogCreateGoalBinding;
import edu.ucsd.cse110.successorator.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.dialog.CreateSelectGoalTypeFragment;
import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;

    ImageButton typeMenuButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        // view.placeholderText.setText(R.string.hello_world);

        setContentView(view.getRoot());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, GoalListFragment.newInstance())
                .commit();

        setContentView(R.layout.activity_main);
        typeMenuButton = (ImageButton) findViewById(R.id.type_menu);
  ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        var itemId = item.getItemId();
//        if (itemId == R.id.add_task_button) {
            var dialogFragment = CreateGoalDialogFragment.newInstance();
            dialogFragment.show( getSupportFragmentManager(), "CreateGoalDialogFragment");
//        }

        return super.onOptionsItemSelected(item);
    }
}

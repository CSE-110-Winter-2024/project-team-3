package edu.ucsd.cse110.successorator;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import edu.ucsd.cse110.successorator.dialog.CreateGoalDialogFragment;
import edu.ucsd.cse110.successorator.ui.goallist.GoalListFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding view;

    ImageButton typeMenuButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.view = ActivityMainBinding.inflate(getLayoutInflater(), null, false);
        // view.placeholderText.setText(R.string.hello_world);

        setContentView(R.layout.fragment_goal_list);

        typeMenuButton = (ImageButton) findViewById(R.id.type_menu);

        typeMenuButton.setOnClickListener(listener);

        setContentView(view.getRoot());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, GoalListFragment.newInstance())
                .commit();

//        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        var itemID = item.getItemId();
//        if (itemID == R.id.add_task_button) {
//            var dialogFragment = CreateGoalDialogFragment.newInstance();
//            dialogFragment.show(getSupportFragmentManager(), "CreateGoalDialogFragment");
//        }
//        else if (itemID == R.id.type_menu) {
////            showTypeMenu();
//
//            var dialogFragment = CreateGoalDialogFragment.newInstance();
//            dialogFragment.show(getSupportFragmentManager(), "CreateGoalDialogFragment");
//        }
//        else if(itemID == R.id.focusMenuButton) {
////            showFocusMenu();
//            var dialogFragment = CreateGoalDialogFragment.newInstance();
//            dialogFragment.show(getSupportFragmentManager(), "CreateGoalDialogFragment");
//        }
//        return super.onOptionsItemSelected(item);
//
//    }

    @NonNull
    private void showTypeMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, typeMenuButton);
        popupMenu.getMenuInflater().inflate(R.menu.goal_type_menu, popupMenu.getMenu());
        popupMenu.show();

    }

    private void showFocusMenu() {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // Initializing the popup menu and giving the reference as current context
            PopupMenu popupMenu = new PopupMenu(MainActivity.this, typeMenuButton);

            // Inflating popup menu from popup_menu.xml file
            popupMenu.getMenuInflater().inflate(R.menu.goal_type_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    // Toast message on menu item clicked
                    Toast.makeText(MainActivity.this, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
        }
    };
}


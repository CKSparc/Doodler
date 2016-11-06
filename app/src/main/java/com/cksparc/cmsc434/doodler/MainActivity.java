package com.cksparc.cmsc434.doodler;

import android.content.DialogInterface;
import android.graphics.Path;
import android.support.design.widget.FloatingActionButton;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class MainActivity extends AppCompatActivity {

    private DoodleView canvasView;
    public static List<Stroke> strokes;
    public static Stack<Stroke> undoedStrokes;

    int brush_size = 10;
    int brush_opacity = 255;

    // RGB Colors - for when users change colors
    int r = 0;
    int g = 0;
    int b = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = (DoodleView) findViewById(R.id.doodle_view); // Canvas reference

        // Initializing the list of strokes and undo strokes
        strokes = new ArrayList<>();
        undoedStrokes = new Stack<>();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(); // FAB that creates the dialog view that users can change colors
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                changeOpacity();
                return true;

            case R.id.action_delete:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                strokes.clear();
                DoodleView._path = new Path();
                canvasView.invalidate();
                return true;

            case R.id.action_undo:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                if(strokes.size() > 0) {
                    undoedStrokes.add(strokes.get(strokes.size() - 1));
                    strokes.remove(strokes.size() - 1);
                    canvasView.invalidate();
                }
                return true;

            case R.id.action_redo:
                    if(undoedStrokes.size() > 0){
                        strokes.add(undoedStrokes.pop());
                        canvasView.invalidate();
                    }
                return true;

            case R.id.action_edit_size:
                changeBrushSize();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void changeBrushSize() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Edit Brush Size");
        alert.setMessage("Slide left or right to change brush size");

        LinearLayout linear = new LinearLayout(this);

        linear.setOrientation(1);

        SeekBar seek = new SeekBar(this);
        seek.setMax(50);
        seek.setProgress(brush_size);

        final TextView txt = new TextView(this);
        txt.setText("Brush Size: 10mm");
        txt.setPadding(32, 16, 10, 10);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brush_size = progress;
                txt.setText("Brush Size: " + progress + "mm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        linear.addView(seek);
        linear.addView(txt);

        alert.setView(linear);


        alert.setPositiveButton("Ok",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id)
            {
                DoodleView._paintDoodle = new MyPaint(r,g,b);
                DoodleView._paintDoodle.setBrushSize(brush_size);
            }
        });

        alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id)
            {
                Toast.makeText(getApplicationContext(), "Canceled",Toast.LENGTH_LONG).show();
            }
        });

        alert.show();
    }

    private void changeOpacity() {

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Edit Brush Opacity");
        alert.setMessage("Slide left or right to change brush opacity");

        LinearLayout linear = new LinearLayout(this);

        linear.setOrientation(1);

        SeekBar seek = new SeekBar(this);
        seek.setMax(255);
        seek.setProgress(brush_opacity);

        final TextView text = new TextView(this);
        text.setText("Brush Opacity: 255");
        text.setPadding(32, 16, 10, 10);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                brush_opacity = progress;
                text.setText("Brush Opacity: " + progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        linear.addView(seek);
        linear.addView(text);

        alert.setView(linear);


        alert.setPositiveButton("Ok",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id)
            {
                DoodleView._paintDoodle = new MyPaint(brush_opacity,r,g,b);
            }
        });

        alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id)
            {
                Toast.makeText(getApplicationContext(), "Cancelled",Toast.LENGTH_LONG).show();
            }
        });

        alert.show();
    }

    private void changeColor() {
        final ColorPicker cp = new ColorPicker(this,r,g,b);

        cp.show();

                 /* On Click listener for the dialog, when the user select the color */
        Button okColor = (Button)cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* You can get single channel (value 0-255) */

                    r = cp.getRed();
                    g = cp.getGreen();
                    b = cp.getBlue();


                DoodleView._paintDoodle = new MyPaint(r,g,b);
                //DoodleView._path = new Path();

                /* Or the android RGB Color (see the android Color class reference) */
//                 selectedColorRGB = cp.getColor();

                cp.dismiss();
            }
        });
    }
}

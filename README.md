# Doodler

##### A simple android application that lets you draw any image with your fingertips. You can change the brush size, color, and opaciy.
### Features:
  1. Draw any image'(s) with your fingertips
  2. Set color using RGB values and get HEX codes
  3. Set color using HEX value
  4. Ability to undo/redo any drawn line
  5. Set different sizes for brush
  6. Set opacity level for brush as well
  
  
# How To Use It
##### Download or clone the project, and open project in android studios. Once loaded select run to load app onto emulator or phone.

  * Once the application is ran on emulator or phone, you can immediatly start drawing with your fingertips. 
  * If you tap the Floating Action Button (FAB) a color picker dialog should appear, allowing you to select a color by RGB or by HEX #
  * The X (button) on the Top App Bar allows you the clear the Canvas
  * The 2 arrows at the top allows you to undo or redo a drawing
  * The settings icon, once tapped, allows you to edit brush size or opacity

#### Work Cited

``` 
Color picker functionality: Android Material Color Picker Dialog - A simple, minimalistic and beautiful dialog color 
picker for Android 4.1+ devices. This color picker is easy-to-use and easy-to-integrate in your application to let 
users of your app choose color in a simple way. by: https://github.com/Pes8/android-material-color-picker-dialog
```
``` Java
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
        
         linear.addView(seek);
        linear.addView(text);

        alert.setView(linear);


        alert.setPositiveButton("Ok",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int id)
            {
            Toast.makeText(getApplicationContext(), "OK",Toast.LENGTH_LONG).show();

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

```

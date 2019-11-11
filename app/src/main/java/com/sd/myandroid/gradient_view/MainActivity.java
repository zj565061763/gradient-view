package com.sd.myandroid.gradient_view;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sd.lib.gradientview.FGradientView;

public class MainActivity extends AppCompatActivity
{
    private FGradientView view_gradient;
    private SeekBar seekbar;
    private TextView tv_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view_gradient = findViewById(R.id.view_gradient);
        seekbar = findViewById(R.id.seekbar);
        tv_progress = findViewById(R.id.tv_progress);

        // 设置正常状态颜色
        view_gradient.setColorNormal(getResources().getColor(R.color.colorNormalStart), getResources().getColor(R.color.colorNormalEnd));
        // 设置进度状态颜色
        view_gradient.setColorProgress(getResources().getColor(R.color.colorProgressStart), getResources().getColor(R.color.colorProgressEnd));
        // 设置进度[0-1]
        view_gradient.setProgress(0.5f);
        // 设置渐变方向，默认水平渐变
        view_gradient.setOrientation(FGradientView.Orientation.Horizontal);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b)
            {
                final float percent = ((float) progress) / seekBar.getMax();
                view_gradient.setProgress(percent);
                tv_progress.setText(String.valueOf(percent));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        seekbar.setProgress((int) (view_gradient.getProgress() * seekbar.getMax()));
    }
}

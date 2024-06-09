package com.example.health;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.domain.Exercise;
import com.example.health.domain.Train;
import com.example.health.webclient.client.Client;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class DefaultTrain extends AppCompatActivity {
    private Train train;

    private GifImageView gifView;
    private TextView countdownView;
    private TextView exerciseName;
    private Button startBtn;
    private Button pauseBtn;
    private ProgressBar progressBar;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<?> currentTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_default_train);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton imageButton = findViewById(R.id.ibBackBtn);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String jsonTrain = extras.getString("TRAIN");
            Gson gson = new Gson();
            train = gson.fromJson(jsonTrain, Train.class);
        }

        gifView = findViewById(R.id.gifView);
        countdownView = findViewById(R.id.countdown);
        exerciseName = findViewById(R.id.ExerciseName);
        startBtn = findViewById(R.id.startBtn);
        pauseBtn = findViewById(R.id.pauseBtn);
        progressBar = findViewById(R.id.progressBar);

        new MyAsyncTask(this).executeOnExecutor(executor, train.Exercises);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    private class MyAsyncTask extends AsyncTask<List<Exercise>, Integer, Void> {
        Context context;

        private boolean isPause = false;
        private CountDownLatch pauseLatch = new CountDownLatch(1);

        private int remainingTime = 0;

        public MyAsyncTask(Context context)
        {
            this.context = context;
            pauseBtn.setOnClickListener(V -> {
                pauseTimer();
            });

            startBtn.setOnClickListener(v -> {
                resumeTimer();
            });
        }

        public void pauseTimer() {
            isPause = true;
            pauseLatch = new CountDownLatch(1);
        }

        public void resumeTimer() {
            isPause = false;
            pauseLatch.countDown();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            int userId = Integer.parseInt(PrefManager.getString(context, "id", "0"));
            Log.d("UpdateUserScore", "after get userId");
            Client client = new Client();
            try {
                client.AddPointsToUser(userId, train.Points, context);
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }


            String scoreValue = train.Points + " ";
            String message = context.getString(R.string.Congratulations___) + scoreValue + context.getString(R.string.points);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setPositiveButton("OK", (dialog, which) -> {
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        dialog.dismiss();
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        @Override
        protected Void doInBackground(List<Exercise>... lists) {
            Log.d("problem with default train", "doInBackground starts");
            List<Exercise> exercises = lists[0];

            for (int j = 0; j < exercises.size(); j++) {
                Exercise exercise = exercises.get(j);

                if (isPause) {
                    try {
                        pauseLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int gifResource = getGifResourceId(exercise, context);
                final GifDrawable gifDrawable = GifDrawable.createFromResource(getResources(), gifResource);
                gifView.setImageDrawable(gifDrawable);

                //rest part
                exerciseName.setText(context.getString(R.string.GetReady));

                int restTime = getRestValue();
                for (int i = restTime; i >= 0; i--) {
                    if (isPause) {
                        try {
                            pauseLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    final int finalI = i;
                    final int progress = (finalI * 100) / restTime;
                    publishProgress(progress, finalI);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //exercise part
                exerciseName.setText(exercise.Name);

                int countdownTime = getCountdownTime();
                for (int i = countdownTime; i >= 0; i--) {
                    if (isPause) {
                        try {
                            pauseLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    final int finalI = i;
                    final int progress = (finalI * 100) / countdownTime;
                    publishProgress(progress, finalI);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progress = values[0];
            int countdownValue = values[1];
            progressBar.setProgress(progress);
            countdownView.setText(String.valueOf(countdownValue));
        }

        private int getGifResourceId(Exercise exercise, Context context)
        {
            if (Objects.equals(exercise.Name, context.getString(R.string.LyingLegRaises))) {
                String gifName = "lying_leg_raises_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.Crunches)))
            {
                String gifName = "crunches_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.MountainClimber)))
            {
                String gifName = "mountain_climber_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.Bicycle)))
            {
                String gifName = "bicycle_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.Burpees)))
            {
                String gifName = "burpees_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.SquatJumps)))
            {
                String gifName = "squat_jumps_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.Squats)))
            {
                String gifName = "squats_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.bridge_and_twist)))
            {
                String gifName = "bridge_and_twist_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.bulgarian_split_squat)))
            {
                String gifName = "bulgarian_split_squat_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.cobra_lat_pulldown)))
            {
                String gifName = "cobra_lat_pulldown_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.diamond_kicks)))
            {
                String gifName = "diamond_kicks_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.dumbell_punches)))
            {
                String gifName = "dumbell_punches_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.forward_lunges)))
            {
                String gifName = "forward_lunges_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.high_knees)))
            {
                String gifName = "high_knees_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.jumping_jacks)))
            {
                String gifName = "jumping_jacks_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.jumping_lunges)))
            {
                String gifName = "jumping_lunges_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.lying_hamstring_curls)))
            {
                String gifName = "lying_hamstring_curls_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.oblique_crunch)))
            {
                String gifName = "oblique_crunch_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.pilates_swimming)))
            {
                String gifName = "pilates_swimming_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.plank_bird_dog)))
            {
                String gifName = "plank_bird_dog_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.plank_hip_dips)))
            {
                String gifName = "plank_hip_dips_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.reverse_crunches)))
            {
                String gifName = "reverse_crunches_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.reverse_plank_leg_raises)))
            {
                String gifName = "reverse_plank_leg_raises_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.rolling_squat)))
            {
                String gifName = "rolling_squat_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.seated_knee_tucks)))
            {
                String gifName = "seated_knee_tucks_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.side_lunges)))
            {
                String gifName = "side_lunges_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.side_plank_rotation)))
            {
                String gifName = "side_plank_rotation_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.single_leg_tricep_dips)))
            {
                String gifName = "single_leg_tricep_dips_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.skaters)))
            {
                String gifName = "skaters_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.spiderman_push_ups)))
            {
                String gifName = "spiderman_push_ups_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.squat_kickback)))
            {
                String gifName = "squat_kickback_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.standing_side_crunch)))
            {
                String gifName = "standing_side_crunch_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.tricep_dips)))
            {
                String gifName = "tricep_dips_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            if (Objects.equals(exercise.Name, context.getString(R.string.up_down_plank)))
            {
                String gifName = "up_down_plank_gif";
                return getResources().getIdentifier(gifName, "raw", getPackageName());
            }
            String gifName = "lying_leg_raises_gif";
            return getResources().getIdentifier(gifName, "raw", getPackageName());
        }

        private int getCountdownTime()
        {
            return 1;
        }

        private int getRestValue()
        {
            return 1;
        }
    }
}
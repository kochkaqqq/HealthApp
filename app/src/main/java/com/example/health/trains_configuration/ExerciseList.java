package com.example.health.trains_configuration;

import com.example.health.R;
import com.example.health.domain.Exercise;

public class ExerciseList {
    public final Exercise[] ExerciseList = {
            new Exercise(R.string.Bicycle, R.raw.bicycle_gif),
            new Exercise(R.string.bridge_and_twist, R.raw.bridge_and_twist_gif),
            new Exercise(R.string.bulgarian_split_squat, R.raw.bulgarian_split_squat_gif),
            new Exercise(R.string.Burpees, R.raw.burpees_gif),
            new Exercise(R.string.cobra_lat_pulldown, R.raw.cobra_lat_pulldown_gif),
            new Exercise(R.string.Crunches, R.raw.crunches_gif),
            new Exercise(R.string.diamond_kicks, R.raw.diamond_kicks_gif),
            new Exercise(R.string.dumbell_punches, R.raw.dumbbell_punches_gif),
            new Exercise(R.string.forward_lunges, R.raw.forward_lunges_gif),
            new Exercise(R.string.high_knees, R.raw.high_knees_gif),
            new Exercise(R.string.jumping_jacks, R.raw.jumping_jacks_gif),
            new Exercise(R.string.jumping_lunges, R.raw.jumping_lunges_gif),
            new Exercise(R.string.lying_hamstring_curls, R.raw.lying_hamstring_curls_gif),
            new Exercise(R.string.LyingLegRaises, R.raw.lying_leg_raises_gif),
            new Exercise(R.string.MountainClimber, R.raw.mountain_climber_gif),
            new Exercise(R.string.oblique_crunch, R.raw.oblique_crunch_gif),
            new Exercise(R.string.pilates_swimming, R.raw.pilates_swimming_gif),
            new Exercise(R.string.plank_bird_dog, R.raw.plank_bird_dog_gif),
            new Exercise(R.string.plank_hip_dips, R.raw.plank_hip_dips_gif),
            new Exercise(R.string.reverse_crunches, R.raw.reverse_crunches_gif),
            new Exercise(R.string.reverse_plank_leg_raises, R.raw.reverse_plank_leg_raises_gis),
            new Exercise(R.string.rolling_squat, R.raw.rolling_squat_gif),
            new Exercise(R.string.seated_knee_tucks, R.raw.seated_knee_tucks_gif),
            new Exercise(R.string.side_lunges, R.raw.side_lunges_gif),
            new Exercise(R.string.side_plank_rotation, R.raw.side_plank_rotation_gif),
            new Exercise(R.string.single_leg_tricep_dips, R.raw.single_leg_tricep_dips_gif),
            new Exercise(R.string.skaters, R.raw.skaters_gif),
            new Exercise(R.string.spiderman_push_ups, R.raw.spiderman_push_ups_gif),
            new Exercise(R.string.SquatJumps, R.raw.squat_jumps_gif),
            new Exercise(R.string.squat_kickback, R.raw.squat_kickback_gif),
            new Exercise(R.string.Squats, R.raw.squats_gif),
            new Exercise(R.string.standing_side_crunch, R.raw.standing_side_crunch_gif),
            new Exercise(R.string.tricep_dips, R.raw.tricep_dips_gif),
            new Exercise(R.string.up_down_plank, R.raw.up_down_plank_gif)
    };

    public Exercise[] GetList()
    {
        return this.ExerciseList;
    }
}

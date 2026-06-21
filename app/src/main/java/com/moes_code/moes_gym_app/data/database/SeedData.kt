package com.moes_code.moes_gym_app.data.database

import androidx.sqlite.db.SupportSQLiteDatabase

object SeedData {
    fun populate(db: SupportSQLiteDatabase) {
        // Exercises
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (1, 'Incline Dumbbell Press', 'Bench at level 4', null, null, null, null, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (2, 'Bench Press', 'Flat Bench', null, null, null, null, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (3, 'Decline Press', 'Seat height at level 7', null, null, null, null, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (4, 'Pendlay Rows', 'Try to stay close to parallel to the ground with your upper body', null, null, null, null, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (5, 'Chest-Supported Rows', 'Bench at level 4 as support for your chest. Then do a T-bar row movement', null, null, null, null, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (6, 'Single-Arm Cable Rows', 'Go forward until your shoulder tucks out and your elbow moves toward your hip', null, 4, 11, 60, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (7, 'Pull-Ups', null, null, 3, 11, 180, 1)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (8, 'Romanian Deadlifts', null, null, 3, 11, 120, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (9, 'Single-Leg Seated Leg Curls', 'Lean forward to get more hip flexion and keep your back straight', null, 4, 11, 60, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (10, 'Squats', null, null, null, null, null, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (11, 'Single-Leg Leg Extensions', 'Use the mat for space between your foot and the machine to extend your range of motion', null, 4, 11, 60, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (12, 'Single-Arm Lateral Raises', null, null, 4, 11, 60, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (13, 'Seated Shoulder Press', null, null, 3, 11, 120, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (14, 'Triceps Pushdowns', null, null, 3, 11, 120, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (15, 'Single-Arm Seated Incline Curls', null, null, 4, 11, 60, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (16, 'Single-Arm Preacher Curls', null, null, 4, 11, 60, 0)")
        db.execSQL("INSERT INTO exercises (id, name, note, last_trained_at, default_sets, default_reps, default_rest_time, is_bodyweight) VALUES (17, 'Planks', 'Add some variety with side planks', null, 3, null, 120, 1)")

        // Set templates
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (1, 1, 7, 180)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (1, 2, 14, 120)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (2, 1, 7, 180)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (2, 2, 14, 120)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (3, 1, 7, 180)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (3, 2, 14, 120)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (4, 1, 7, 180)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (4, 2, 14, 120)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (5, 1, 7, 180)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (5, 2, 14, 120)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (10, 1, 7, 180)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (10, 2, 14, 120)")
        db.execSQL("INSERT INTO workout_set_templates (exercise_id, set_number, reps, rest_time) VALUES (10, 3, 14, 120)")

        // Exercise alternatives
        db.execSQL("INSERT INTO exercise_alternatives (exercise_id, alternative_id) VALUES (2, 3)")
        db.execSQL("INSERT INTO exercise_alternatives (exercise_id, alternative_id) VALUES (4, 5)")
        db.execSQL("INSERT INTO exercise_alternatives (exercise_id, alternative_id) VALUES (6, 7)")
        db.execSQL("INSERT INTO exercise_alternatives (exercise_id, alternative_id) VALUES (8, 9)")
        db.execSQL("INSERT INTO exercise_alternatives (exercise_id, alternative_id) VALUES (10, 11)")
        db.execSQL("INSERT INTO exercise_alternatives (exercise_id, alternative_id) VALUES (12, 13)")
        db.execSQL("INSERT INTO exercise_alternatives (exercise_id, alternative_id) VALUES (15, 16)")

        // Workout plan
        db.execSQL("INSERT INTO workout_plans (id, name, description) VALUES (1, 'Full Body', 'Triathlon Workout Plan')")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 1, 1)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 2, 2)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 3, 4)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 4, 6)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 5, 8)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 6, 10)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 7, 12)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 8, 14)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 9, 15)")
        db.execSQL("INSERT INTO workout_plan_entries (plan_id, position, exercise_id) VALUES (1, 10, 17)")
    }
}

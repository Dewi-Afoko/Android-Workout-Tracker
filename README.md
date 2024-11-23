First iteration of frontend for Android workout tracker app in Kotlin.

Currently has landing page and workout tracker.

Functionality: 
- Add an exercise name, reps and loading
- Add more reps to previous exercise, increasing sets value and display
- Validation of exercise name (must be a string) and rep count (cannot be zero)
- Returns "Body weight" as loading if value is left emppty or is zero

TODO:
- Persist data locally
- Create exercise repository, allowing search rather than entering name string
- Create pages for exercises with details on muscles, how to perform them, equipment, etc.
- Create distinct "Planning" and "Live" modes
- Offer creation of workout split, based on workouts per week (upper/lower, PPL, antagonist supersets, etc.)
- Add rest interval recommendations based on loading and reps

Data:
- Make comparisons to previous workouts/lifts and review performance
- Recommend new lifts/deloads/rests
- Show personal bests

User:
- Track weight
- Add notes on session performance
- Add notes on session condition (tired, recovering, achey, etc.)
  

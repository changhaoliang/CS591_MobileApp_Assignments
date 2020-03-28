We cannot count on onDestroy() method being called as a place for saving data!

For example, if an activity is editing data in a content provider, those edits should be committed in either onPause() or onSaveInstanceState(Bundle), not here. This method is usually implemented to free resources like threads that are associated with an activity, so that a destroyed activity does not leave such things around while the rest of its application is still running.

There are situations where the system will simply kill the activity's hosting process without calling this method (or any others) in it, so it should not be used to do things that are intended to remain around after the process goes away.
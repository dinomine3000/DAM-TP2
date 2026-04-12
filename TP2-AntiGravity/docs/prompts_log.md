## Prompt 1

Goal: 
Confirm AI can see the project.

Prompt used:
Read the markdown files in the docs folder. Do not lose these from the context window. I want you to confirm step 1 of the implementation plan is done.

Result:
It did.

## Prompt 2

Goal: 
Continue implementation plan.

Prompt used:
Continue with step 2. Do it in parts, as they were designed.

Result:
Created the base class files.


## Prompt 3

Goal: 
Make sure the image item class is going to work. I'm unsure how android can show images from a URL, so I want to confirm with the AI that it can show the images in a reasonable way.

Prompt used:
Can you confirm that the ImageItem data class, holding onto a byte array, can show that as an image in an activity? Is there an alternative way to go about it?

Result:
It correctly pointed out that step 3 would touch on that subject, being a different implementation, highlighting the contradiction in the plan.

## Prompt 4

Goal:
Fix the oversight highlighted with the previous prompt. Since caching is handled by Coil, we'll forego that part of the data class and keep it simpler.

Prompt used:
Remove the byte array caching. From now on, the image data class will not have the byte array field. Modify the relevant docs to reflect this change, and ensure the project is ready for the next step.

Result:
Tweaked the project nicely. Removed redundant manual caching and imported coil.

## Prompt 5

Goal:
Implement step 3.

Prompt used:
Everything looks fine. Implement step 3 to connect to the API. No displaying images yet, nor having any activities.

Result:
Implemented API service calls and abstraction functions.

## Prompt 6

Goal:
Implementing step 4, as well as roughly testing the code until now

Prompt used:
Make any necessary changes for part 4, the "ViewModel" part of the MVVM, to connect the data to the UI. You can design an activity xml for a simple test, but we are not designing the main screens yet.

Result:
Test activity seems to work fine, and viewmodel was decently implemented.

## Prompt 7

Goal:
Implementing step 5.1, designing the activity feed.

Prompt used:
Now let's implemente step 5.1, the activity feed layout. Check the docs again, but in abstract terms, it has to have a recycler view to show images asynchronously (not freezing the application while loading), a refresh button, a toolbar to switch screens and the ability to open an image. Don't do code, just design the layout, but don't forget to prepare it for connecting it to the code later.

Result:
Designed the feed layout, but used something wrong for the swipe refresh functionality.

## Prompt 8

Goal:
Fixing problem from above, removing swipe to refresh functionality, replacing it with just a button.

Prompt used:
"Class referenced in the layout file, androidx.swiperefreshlayout.widget.SwipeRefreshLayout, was not found in the project or the libraries" let's remove the swipe to refresh functionality, just keep it simple with a button.

Result:
Cleaned up the layout, but now project has an error:
Android resource linking failed
dam_A51728.imageapiapp-mergeDebugResources-42:/layout/activity_feed.xml:15: error: 'Surface' is incompatible with attribute background (attr) reference|color.
dam_A51728.imageapiapp-mergeDebugResources-42:/layout/activity_feed.xml:42: error: resource drawable/ic_feed (aka dam_A51728.imageapiapp:drawable/ic_feed) not found.
error: failed linking file resources.

## Prompt 9

Goal:
Fix the above problem. If it persists, go back to prompt 7

Prompt used:
Android resource linking failed
dam_A51728.imageapiapp-mergeDebugResources-42:/layout/activity_feed.xml:15: error: 'Surface' is incompatible with attribute background (attr) reference|color.
dam_A51728.imageapiapp-mergeDebugResources-42:/layout/activity_feed.xml:42: error: resource drawable/ic_feed (aka dam_A51728.imageapiapp:drawable/ic_feed) not found.
error: failed linking file resources.
Review the project to fix these errors and try to catch any others that might pop up. The current project seems to have problems.

Result:
App runs fine. Proceeding...

## Prompt 10

Goal:
Design the favorites list layout, as well as the the image item layout (to display a single image)

Prompt used:
Add the toolbar to the feed layout, to access the favorite list screen.
Then proceed with designing the favorite list, as well as the layout to show a single image.
The favorite should show the images that the user has favorited, as well as the toolbar to allow to return to the feed screen. The image item layout show show the image using the biggest area possible (keeping proportions) with a button to return to the previous screen.

Result:
Made the designs for the remaning layouts nicely.

## Prompt 11

Goal:
Make the screens connect to one another. No logic just yet.

Prompt:
Now make the app start on the feed screen now (instead of the test activity) and connect it to the favorite list screen and vice versa, as well as the image focus screen. We'll handle step 7 afterwards.

Result:
App now starts on feed screen.
Problem is that the toolbar, being on the very top, can't be interacted with.

## Prompt 12

Goal: Move toolbar down.

Prompt:
The toolbar is too high up, android doesn't recognize touch that high up. Bring it to the bottom, on all screens.

Result:
It's interactible now, but the refresh button is a bit on the way.

## Prompt 13

Goal: Move refresh button up a bit and implement step 7 to display images.

Prompt:
The refresh button is in the way of the favorite button.
After fixing that, implement step 7 to display the images in the feed and favorites list.

Result:
Button moved, but its behind the toolbar now (sigh).
Also, the refresh button is already implemented, as well as the breed filtering.
...

## Prompt 14

Goal: Finish the favorite list.

Prompt:
Now finish implementing the logic for the favorite list, as well as making the buttons to favorite images work.

Result:
App is complete. Works nicely.
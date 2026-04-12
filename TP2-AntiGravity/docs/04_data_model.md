Data structures to be used by the app

Images
 - Object to represent and store each image.
 - Contains the URL from the API, as a String. It serves as the Unique Resource Locator, and its ID.
 - Also contains an Long attribute, Favorite Order. It is a value that is incremented globally by one each time an image is favorited, and stored on the image object. Used for ordering images.

Favorite List.
 - Object to store all the favorited images.
 - Composed of a list of the image ids (URL in this case) for each image that the user has favorited. All sorted by Favorite Order attribute, with most recent (biggest number) first.

Dog List.
 - Object to cache the breed list, so there's no API abuse. Only used for the drop-down breed menu.
 
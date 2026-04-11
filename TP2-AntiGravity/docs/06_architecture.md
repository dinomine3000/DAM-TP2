MVVM based architecture.
View consists of the aforementioned screens.
ViewModel will deal with connecting the locally stored data with the View layer and vice-versa.
Data Repo will store all the relevant data, including favorite list (kept through app restarts), image data and dog breed list caching.
API service will supply new URLs based on provided arguments. Images themselves are a URL that must be accessed online.

Layers:
UI -> ViewModel -> Data Repo -> API service
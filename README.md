Welcome!

This app is a picture-drawing/saving app.

The app is composed of two fragments - RecycleFragment & EditFragment, both of which are in a ViewPager in MainActivity.

The RecyclerFragment basically is a menu for all of your images. There is a recyclerview in which each item is the title of your image. If you click on an item, it will take you to EditFragment, which will allow you to edit your image by drawing on it and saving it. Also, in RecycleFragment, there is a floating action button that will allow you to create a new image, which will also take you to an EditFragment.

Description of each class:

MainActivity.java:
Has both of the fragments under a ViewPager and sets a fragment active. It also has interface methods that allow switches to happen between fragments.

PictureText.java:
PictureText.java is a custom storage class. It holds two strings - the title of the picture, and the filename of the picture.

PicturesContainer.java:
PicturesContainer.java is also a custom class which storages an ArrayList of PictureTexts. You can also add PictureTexts to the PictureContainer.

RecycleFragment.java:
RecycleFragment.java has a recyclerview, with each element being a PictureText. It has a method called preparePictureData(), which takes the contents of the db.json file, converts it to a PicturesContainer class, changes the picturelist arraylist within the class, and notifies the recyclerview's adapter of the change. When you click on a certain item in the recyclerview, it will switch to the EditFragment with the item's PictureText class. RecycleFragment also has a FloatingActionButton that allows you to create a new image (it creates a new PictureText with a white background color).

PictureAdapter.java:
Adapter for the recyclerview

RecycleTouchListener.java:
TouchListener for the recyclerview.

ClickListener.java:
Interface with two methods - onClick and onLongClick. RecycleTouchListener uses the ClickListener.

SimpleDrawingView:
Class that allows you to paint on a view.

EditFragment:
EditFragment is composed of a simpledrawing view that covers the entire screen, an edittext that allows you to name your picture (set the title), and a button to save. EditFragment first has a setPicture method which takes in an arguement of a PictureText. This allows you to "edit" your picture by setting the background of the SimpleDrawingView to the picture file of the picturetext. The user can draw on the SimpleDrawingView and type in text in the edittext. When the user clicks save, it reads the contents of the db.json file into a PicturesContainer, takes the current drawingView, gets the bitmap of the image, and saves the file to internal storage, takes the title from edittext and the filename, creates a PictureText, adds the PictureText to the PicturesContainer, and finally writes the new pictures container into the db.json and switches back to the recyclefragment. When the recyclefragment is called, it will read the json file that has just been updated.

XML files are also in the GitHub repo.

Problems:
I ran into a few issues throughout the process, many of which I ended up fixing.
One fix that I unfortunately could not make on time was the problem with the db.json file. As far as I (with Mr. Kosek's help) can tell, the db.json file is only readable and not writable because it is in internal storage. So, the entire purpose of writing and reading to it is gone. I tried replacing the json reading/writing with text file storage, but I got the same proble.

Solutions:
I need to fix this problem as soon as possible. If I can't use json, I could potentially use Firebase as a storage unit. I want to do this in the future not only to fix this project, but also learn about Firebase for future applications.

Logs:

Day 1, 1/9/18:

-I created the project
-I integrated the movies recyclerview lab and the fragment camera lab together

Day 2, 1/11/18:
-I created the three fragments for the project (RecyclerViewFragment, CameraFragment, PictureFragment)
-I created the xmls for each of the three projects.
-I ran into some problems regarding my gradle, which I resolved.

Day 3, 1/16/18:
-I realized that I did not need the camera fragment, decided to integrate the picture editing fragment and the camera fragment together.
-I researched on how to lay a picture in the background of a drawing view.
-I also started to configure the JSON that stores the images/titles.
-I also decided as a bonus to add date storage to the app (storing the dates of the last time a picture was edited).
-Picture source control?

Day 4, 1/18/18
-I added the dispatch take picture intent method
-I finished the xmls for each of the fragments
-I ran into a few debugging issues that I eventually fixed.

Day 5, 1/23/18
-I made the SimpleDrawingView work with the fragment
-I made the container class for the list of Pictures and Texts
-I tried to save the images to json file, but it did not work.

Day 6, 1/25/18
-I could not get the json file to end up working.
-I tried with a text file, but it gave similar errors.
-I explored Firebase a little bit but did not implement it.
-Created GitHub repo/readme.

Thank you for reading!

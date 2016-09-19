# ImagePicker
> A library help to support multiple selection image from phone gallery

Android OS provides select multiple images only available in Android API 18 and higher, and the invoke api likes this:
```java
Intent intent = new Intent();
intent.setType("image/*");
intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
intent.setAction(Intent.ACTION_GET_CONTENT);
startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
```

If you want to support `minSdkVersion` lower than API 18, **ImagePicker** may be helpful.

### Usage:

```xml
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    tools:context="com.zac4j.imagepicker.sample.MainActivity"
    >

  <Button
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:text="@string/select_photo"
      android:onClick="selectPhoto"
      style="@style/Widget.AppCompat.Button.Colored"
      />
</LinearLayout>
```
Java Code:

```java
private static final String TAG = "MainActivity";
private static final int REQUEST_IMAGES_CODE = 0x110;

  // Select 3 photos from gallery
  private static final int SELECT_PHOTO_NUM = 3;

  public void selectPhoto(View view) {
    Intent intent = new Intent(this, GalleryActivity.class);
    intent.putExtra(GalleryActivity.EXTRA_SELECT_NUM, SELECT_PHOTO_NUM);
    startActivityForResult(intent, REQUEST_IMAGES_CODE);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode != REQUEST_IMAGES_CODE || resultCode != RESULT_OK || data == null) {
      return;
    }

    String[] images = data.getStringArrayExtra(GalleryActivity.EXTRA_IMAGE_CONTAINER);

    if (images == null || images.length == 0) {
      return;
    }

    String showText = "";
    for (String image : images) {
      showText += image + "\n";
    }
    Log.i(TAG, "select image addresses: " + showText);
  }
}
```

# ImagePicker
> A library for picking lots of image from Android gallery.

Android OS provides select multiple images only available in Android API 18++, and the invoke api likes this:
```java
private static final int REQUEST_IMAGES_CODE = 0xaa;

Intent intent = new Intent();
intent.setType("image/*");
intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
intent.setAction(Intent.ACTION_GET_CONTENT);
startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_IMAGES_CODE);
```

If you want to support `minSdkVersion` API 18--, **ImagePicker** should be helpful.

### Main Usage:
-------
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
public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  // Request code for start image picker activity.
  private static final int REQUEST_IMAGES_CODE = 0xaa;

  // Set default number of photo to select.
  private static final int SELECT_PHOTO_NUM = 3;

  private void startPicker() {
    ImagePicker.with(this)
      .engine(ImageLoader.GLIDE) // Set load image engine as Glide, or Picasso
      .number(SELECT_PHOTO_NUM) // Set select number for picking photo
      .code(REQUEST_IMAGES_CODE) // Set request code for retrieving data
      .build();
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode != REQUEST_IMAGES_CODE || resultCode != RESULT_OK || data == null) {
      return;
    }

    List<String> images = data.getStringArrayListExtra(ImagePicker.DATA);

    if (images == null || images.isEmpty()) {
      return;
    }

    String showText = "";
    for (String image : images) {
      showText += image + "\n";
    }

    mTextView.setText(showText);
  }
}
```
License
-------

    Copyright (C) 2016 Zaccccccccccccccc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# ImagePicker
A library help to multi-select image from mobile gallery

Since Android os provides select multiple images only available in Android API 18 and higher, and the invoke api likes this:
```java
Intent intent = new Intent();
intent.setType("image/*");
intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
intent.setAction(Intent.ACTION_GET_CONTENT);
startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
```

If we want to support `minSdkVersion` lower than 18, this time **ImagePicker** will be helpful.

Usage:
```java

```
package com.zac4j.imagepicker;

import java.io.Serializable;
import java.util.List;

public interface PickerListener extends Serializable {
  void onPickComplete(List<String> photoList);
}
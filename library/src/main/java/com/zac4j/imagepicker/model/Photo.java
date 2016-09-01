package com.zac4j.imagepicker.model;

/**
 * Photo Model
 * Created by zac on 16-6-25.
 */

public class Photo {
  private int id;
  private String path;
  private boolean checked; // Photo select status

  public Photo() {}

  public Photo(String path, boolean checked) {
    this.path = path;
    this.checked = checked;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}

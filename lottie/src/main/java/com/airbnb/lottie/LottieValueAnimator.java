package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

class LottieValueAnimator extends ValueAnimator {
  private boolean isReversed = false;
  private float startProgress = 0f;
  private float endProgress = 1f;
  private long duration;

  LottieValueAnimator() {
    setFloatValues(0f, 1f);

    /*
      This allows us to reset the values if they were temporarily reset by
      updateValues(float, float, long, boolean)
     */
    addListener(new AnimatorListenerAdapter() {
      @Override public void onAnimationEnd(Animator animation) {
        updateValues();
      }

      @Override public void onAnimationCancel(Animator animation) {
        updateValues();
      }
    });
  }

  @Override public ValueAnimator setDuration(long duration) {
    this.duration = duration;
    updateValues();
    return this;
  }

  @Override public long getDuration() {
    return duration;
  }

  void setIsReversed(boolean isReversed) {
    this.isReversed = isReversed;
    updateValues();
  }

  void setStartProgress(float startProgress) {
    this.startProgress = startProgress;
    updateValues();
  }

  void setEndProgress(float endProgress) {
    this.endProgress = endProgress;
    updateValues();
  }

  void updateValues(float startProgress, float endProgress) {
    float minValue = Math.min(startProgress, endProgress);
    float maxValue = Math.max(startProgress, endProgress);
    setFloatValues(
        isReversed ? maxValue : minValue,
        isReversed ? minValue : maxValue
    );
    super.setDuration((long) (duration * (maxValue - minValue)));
  }

  private void updateValues() {
    updateValues(startProgress, endProgress);
  }
}

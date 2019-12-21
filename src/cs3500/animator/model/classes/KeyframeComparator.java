package cs3500.animator.model.classes;

import java.util.Comparator;

/**
 * Comparator for the Key frame class. Determines comparisons by checking the keyframe's
 * time values.
 */
public class KeyframeComparator implements Comparator<Keyframe> {
  @Override
  public int compare(Keyframe o1, Keyframe o2) {
    if (!(o1 instanceof Keyframe && o2 instanceof Keyframe)) {
      throw new IllegalArgumentException("Not comparing two keyframes.");
    }
    if (o1.getEndTime() > o2.getEndTime()) {
      return 1;
    }
    if (o1.getEndTime() == o2.getEndTime()) {
      return 0;
    }
    if (o1.getEndTime() < o2.getEndTime()) {
      return -1;
    }
    return 0;
  }
}

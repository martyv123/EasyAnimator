package cs3500.animator.provider.view;

import java.util.OptionalInt;

/**
 * Helper class to help working with {@link Integer} values. Need not be instantiated as all of its
 * methods are static.
 */
public class IntTools {

  /**
   * Parse an {@link Integer} from a {@link String}. This is done by creating an {@link OptionalInt}
   * if possible: if calling {@link Integer#parseInt(String)} is successful, then the {@link
   * OptionalInt} will contain the resulting value. If calling {@link Integer#parseInt(String)}
   * throws an {@link NumberFormatException}, the {@link OptionalInt} will be empty. Thus, this
   * method is guaranteed to return without throwing exceptions.
   *
   * @param stringToParse the string to parse
   * @return the {@link OptionalInt} of the form described above
   */
  public static OptionalInt parseToInt(String stringToParse) {
    try {
      return OptionalInt.of(Integer.parseInt(stringToParse));
    } catch (NumberFormatException e) {
      return OptionalInt.empty();
    }
  }
}

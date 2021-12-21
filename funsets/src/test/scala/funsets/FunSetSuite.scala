package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one".ignore) {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("Union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("Set intersection contains only common elements of two sets") {
    new TestSets {
      val seta = union(s1, s2)
      val setb = union(s2, s3)
      val inter = intersect(seta, setb)
      assert(contains(inter, 2), "Inter 2")
      assert(!contains(inter, 3), "Inter 3")
    }
  }

  test("Set difference contains only A - B") {
    new TestSets {
      val seta = union(s1, s2)
      val setb = union(s2, s3)
      val inter = diff(seta, setb)
      assert(contains(inter, 1), "Diff 1")
      assert(!contains(inter, 2), "Diff 2")
    }
  }
  test("Filter contains only the predicate element") {
  new TestSets {
    val seta = union(s1, s2)
    val setb = union(s2, s3)
    val setc = union(s3,s4)
    val setd=  union(s2,s4)
    val inter1 = filter(seta, setb)
    val inter2 = filter(setc,setd)
    assert(!contains(inter1, 1), "Filter 1")
    assert(contains(inter1, 2), "Filter 2")
    assert(!contains(inter1, 3), "Filter 3")
    assert(contains(inter2,4), "Filter4")

  }
}
  test("Forall predicate test ") {
    new TestSets {
      val seta = union(s1, s2)
      val setb=  union(s3,s4)
      assert(forall(seta,  s => s < 3), "Forall 1")
      assert(!forall(seta, s => s < 2), "Forall 2")
      assert(forall(setb, s=> s < 5 ),  "Forall 3")
      assert(!forall(setb, s=> s < 4 ),  "Forall 4")
    }
  }

   test("The exists predicate test") {
  new TestSets {
    val seta = union(s1, s2)
    val setb = union(s3,s4)
    assert(exists(seta, s => s < 2), "Exists 1")
    assert(!exists(seta, s => s < 1), "Exists 2")
    assert(exists(setb, s => s < 4),  "Exists 3")
    assert(!exists(setb, s => s < 3),  "Exists 4")
    assert(!exists(setb, s => s > 4),  "Exists 5")
    assert(exists(union(s1, s4), x => x > 3))



  }
  }

  test("Map predicate test") {
    new TestSets {
      val seta = union(s1, s2)
      val setb = union(s2, s3)
      val mapvalue = map(seta, s => s + 2)
      assert(contains(mapvalue, 3), "Map 1")
      assert(contains(mapvalue, 4), "Map 2")
      assert(!contains(mapvalue, 2), "Map 3")
      assert(exists(map(s3, x => x * x), x => x == 9))
    }
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds

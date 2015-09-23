package org.sectorzero.servizio.utils.misc;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongestPrefixStringMatcherTest {

  LongestPrefixStringMatcher<String> m;

  @Before
  public void setup() {
    m = new LongestPrefixStringMatcher<>("DEFAULT");
  }

  @Test
  public void insert_valid_1() {
    assertTrue(m.insert("abc", "ONE"));
    assertTrue(m.insert("abd", "TWO"));
  }

  @Test
  public void insert_does_not_allow_overwrite() {
    assertTrue(m.insert("abc", "ONE"));
    assertFalse(m.insert("abc", "TWO"));
  }

  @Test
  public void insert_or_update_valid_1() {
    assertTrue(m.insertOrUpdate("abc", "ONE"));
    assertTrue(m.insertOrUpdate("abc", "TWO"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void insert_invalid_key_1() {
    i("foo*bar*", "ONE");
  }

  @Test(expected = IllegalArgumentException.class)
  public void insert_invalid_key_2() {
    i("foo*bar", "ONE");
  }

  @Test
  public void insert_verify_1() {
    insertAndVerifyValues(
        r("hola", "amigo"),
        r("صديقي", "مرحبا يا"),
        r("hello", "friend"),
        r("bonjour", "ami"),
        r("你好", "朋友"));
  }

  @Test
  public void insert_verify_2() {
    insertAndVerifyValues(
        r("foo/bar", "R1"),
        r("foo/bar/*", "R2"),
        r("foo/bar*", "R3"));
  }

  @Test
  public void match_set_1() {
    i("foo/bar/*", "R1");

    assertEquals("DEFAULT", m.match("foo/bar"));
    assertEquals("R1", m.match("foo/bar/"));
    assertEquals("R1", m.match("foo/bar/goo"));
  }


  @Test
  public void match_set_2() {
    // Arrange Rules
    i("foo/bar", "R1");
    i("foo/bar/*", "R2");
    i("foo/bar*", "R3");

    // Act & Assert on Matches
    t("R1", "foo/bar");
    t("R2", "foo/bar/");
    t("R2", "foo/bar/hola");
    t("R2", "foo/bar/*");
    t("R3", "foo/bar?q=someparam");
    t("R3", "foo/bare");
    t("R3", "foo/bar*");
    t("DEFAULT", "foo/ba");
    t("DEFAULT", "foobar");
  }


  @Test
  public void match_set_3() {
    // Arrange Rules
    i("foo/bar", "R1");
    i("foo/bar/*", "R2");
    i("foo/bar*", "R3");

    // Act & Assert on Matches
    t("DEFAULT", "");
    t("DEFAULT", "googone");
  }

  @Test
  public void match_set_4() {
    // Arrange Rules
    i("foo/bar", "R1");
    i("foo/bar/*", "R2");
    i("foo/bar/goo", "R3");
    i("foo/bar/goo/*", "R3");

    // Act & Assert on Matches
    t("R1", "foo/bar");
    t("R2", "foo/bar/");
    t("R2", "foo/bar/xoo");
    t("R2", "foo/bar/go");
    t("R2", "foo/bar/g");
    t("R2", "foo/bar/xoo/goo");
    t("R3", "foo/bar/goo");
    t("R3", "foo/bar/goo/");
    t("R3", "foo/bar/goo/zoo");
  }

  @Test
  public void match_set_5() {
    // Arrange Rules
    i("foo/bar", "R1");
    i("foo/bar/", "R2");
    i("foo/bar*", "R3");
    i("foo/bar/*", "R4");
    i("foo/bar/zoo", "R5");
    i("foo/bar/zoo/*", "R6");
    i("foo/goo/*", "R7");
    i("foo/g", "R8");

    t("DEFAULT", "foo/ba");
    t("DEFAULT", "foo/baz");
    t("R1", "foo/bar");
    t("R2", "foo/bar/");
    t("R4", "foo/bar/hola");
    t("R4", "foo/bar/blah");
    t("R4", "foo/bar/blah/a");
    t("R4", "foo/bar/a");
    t("R4", "foo/bar/*");
    t("R4", "foo/bar/z");
    t("R4", "foo/bar/zo");
    t("R5", "foo/bar/zoo");
    t("R6", "foo/bar/zoo/");
    t("R6", "foo/bar/zoo/xoo");
    t("R3", "foo/bar?q=someparam");
    t("R3", "foo/bare");
    t("R7", "foo/goo/");
    t("R7", "foo/goo/bam");
    t("DEFAULT", "foo/go");
    t("R8", "foo/g");
  }

  /**
   * Take a set and insert all, and validate all are there
   */
  void insertAndVerifyValues(String[]... records) {
    List<String> expectedValues = new ArrayList<>();
    expectedValues.add("DEFAULT");
    for(String[] record : records) {
      i(record[0], record[1]);
      expectedValues.add(record[1]);
    }
    List<String> values = m.values();
    assertEquals(expectedValues.size(), values.size());
    assertEquals(Sets.newHashSet(expectedValues), Sets.newHashSet(values));
  }

  void t(String expectedValue, String queryString) {
    assertEquals(expectedValue, m.match(queryString));
  }

  void i(String key, String value) {
    m.insert(key, value);
  }

  String[] r(String k, String v) {
    return new String[]{k, v};
  }
}
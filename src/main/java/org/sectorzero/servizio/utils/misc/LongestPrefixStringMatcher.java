package org.sectorzero.servizio.utils.misc;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.concurrent.NotThreadSafe;

import lombok.Getter;
/**
 * Longest Prefix String Matching with Wildcard Support
 * - This is a 'prefix trie' implementation for longest prefix matching on a dictionary of strings
 * - Arbitrary values can be stored for the prefix-key matches which are returned for a match
 * - Supports only '*' ( zero or more ) wildcard match as of now
 * - Support 'default' value for no match
 * - This is not thread-safe and intended to be used in immutable scenarios as rule-matching etc ( need to provide method to return immutable copy )
 *
 * Matching Use Cases :
 * a. exact string
 * c. wildcards ( keys have to end with * ), ( Cannot contain multiple wildcards per key )
 *
 * Example :
 * Given Key-Map :
 *  foo/bar
 *  foo/bar/
 *  foo/bar*
 *  foo/bar/*
 *  foo/bar/zoo
 *  foo/bar/zoo/*
 *  foo/goo/*
 *  foo/g
 *
 * Matches :
 *  foo/ba -> ( no match )
 *  foo/baz -> ( no match )
 *  foo/bar -> foo/bar
 *  foo/bar/ -> foo/bar/
 *  foo/bar/hola -> foo/bar/*
 *  foo/bar/blah -> foo/bar/*
 *  foo/bar/blah/a -> foo/bar/*
 *  foo/bar/a -> foo/bar/*
 *  foo/bar/* -> foo/bar/*
 *  foo/bar/z -> foo/bar/*
 *  foo/bar/zo -> foo/bar/*
 *  foo/bar/zoo -> foo/bar/zoo
 *  foo/bar/zoo/ -> foo/bar/zoo/*
 *  foo/bar/zoo/xoo -> foo/bar/zoo/*
 *  foo/bar?q=someparam -> foo/bar*
 *  foo/bare -> foo/bar*
 *  foo/goo/ -> foo/goo/*
 *  foo/goo/bam -> foo/goo/*
 *  foo/go -> ( no match )
 *  foo/g -> foo/g
 *
 * How to use this for Auth Matcher :
 * - Generate the keys strings as follows :
 *  foo/bar -> foo/bar
 *  foo/bar/{} -> foo/bar/*
 *  foo/wild* -> foo/wild*
 * - Make 'Rule' objects and store them as values
 *
 * @param <V>
 */
@NotThreadSafe
public class LongestPrefixStringMatcher<V> {

  final static Character wildcard = '*'; // Supports only zero or more

  final Node<V> prefixTrie;

  public LongestPrefixStringMatcher(V defaultValue) {
    prefixTrie = Node.terminalOf(defaultValue);
  }

  /**
   * Match the longest prefix available in the key-space and return the associated value, or
   * the default value if no match. Matching uses tail-wildcard ( zero or more )
   */
  public V match(String s) {
    if(StringUtils.isEmpty(s)) {
      return prefixTrie.value();
    }

    Node<V> n = prefixTrie;
    V v = n.value();
    for (char c : s.toCharArray()) {
      if(n.get(wildcard) != null) {
        v = n.get(wildcard).value();
      }
      Node<V> next = n.get(c);
      if(next == null) {
        return v;
      }
      n = next;
    }

    if(n.isTerminal()) {
      return n.value();
    }

    if(n.get(wildcard) != null) {
      v = n.get(wildcard).value();
    }

    return v;
  }

  public boolean insert(String s, V value) {
    Validate.isTrue(isValid(s));

    Node<V> n = getOrCreateNodeForInsert(s);
    if(n.isTerminal()) {
      return false;
    }
    n.updateValue(value);
    return true;
  }

  public boolean insertOrUpdate(String s, V value) {
    Validate.isTrue(isValid(s));

    Node<V> n = getOrCreateNodeForInsert(s);
    n.updateValue(value);
    return true;
  }

  // !! USE WITH CAUTION WITH LARGE NUMBER OF KEYS !!
  // - Not a streaming impl
  // - Uses Stack Recursion for DFS
  public List<V> values() {
    List<V> values = new ArrayList<>();
    traverse(prefixTrie, values);
    return values;
  }

  void traverse(Node<V> n, List<V> accumulator) {
    Validate.notNull(n);
    if(n.isTerminal()) {
      accumulator.add(n.value());
    }
    n.children().forEach(c -> traverse(c, accumulator));
  }

  Node<V> getOrCreateNodeForInsert(String s) {
    if(StringUtils.isEmpty(s)) {
      return prefixTrie;
    }
    char[] chars = s.toCharArray();
    Node<V> n = prefixTrie;
    for (char aChar : chars) {
      Node<V> next = n.get(aChar);
      if (next == null) {
        next = n.createFor(aChar);
      }
      n = next;
    }
    return n;
  }

  boolean isValid(String s) {
    return
        (s.indexOf(wildcard) == -1) ||
        (s.indexOf(wildcard) == (s.length() - 1));   // wildcard has to be the last char and should contain only one wildcard
  }

  static private class Node<T> {
    final private HashMap<Character, Node<T>> children = new HashMap<>();
    @Getter private Optional<T> value;
    @Getter private boolean isTerminal = false;

    public Node(T value, boolean isTerminal) {
      this.value = (value == null) ? Optional.empty() : Optional.of(value);
      this.isTerminal = isTerminal;
    }

    public Node<T> get(Character c) {
      return children.get(c);
    }

    public Node<T> createFor(Character c) {
      Node<T> n = new Node<>(null, false);
      children.put(c, n);
      return n;
    }

    public void updateValue(T value) {
      this.value = Optional.of(value);
      this.isTerminal = true;
    }

    public T value() {
      return value.isPresent() ? value.get() : null;
    }

    public Stream<Node<T>> children() {
     return children.values().stream();
    }

    static public <T> Node<T> terminalOf(T value) {
      return new Node<>(value, true);
    }
  }

}

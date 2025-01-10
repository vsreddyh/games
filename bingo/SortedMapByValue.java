package bingo;
import java.util.*;

public class SortedMapByValue<K, V extends Comparable<V>> {
  private final Map<K, V> map =
      new TreeMap<>(); // Internal TreeMap to store key-value pairs
  private final Comparator<Map.Entry<K, V>>
      valueComparator; // Comparator for sorting
  private Map<K, V> sortedMap =
      new LinkedHashMap<>(); // Maintains sorted entries

  // Constructor
  public SortedMapByValue() {
    this.valueComparator = (e1, e2) -> {
      int cmp = e2.getValue().compareTo(e1.getValue()); // Descending order
      return (cmp == 0)
          ? e1.getKey().toString().compareTo(e2.getKey().toString())
          : cmp; // Break ties using keys
    };
  }

  // Put method to add or update key-value pairs
  public void put(K key, V value) {
    map.put(key, value);
    sortMap();
  }

  // Put method to add or update key-value pairs
  public void set(K key, V value) {
    map.put(key, value);
    sortMap();
  }

  // Get method to retrieve value based on key
  public V get(K key) { return map.get(key); }

  // Remove method to delete a key-value pair
  public V remove(K key) {
    V removedValue = map.remove(key);
    sortMap();
    return removedValue;
  }

  public V getNthValue(int n) {
    if (n < 0 || n >= sortedMap.size()) {
      throw new IndexOutOfBoundsException("Index " + n + " is out of bounds");
    }
    int currentIndex = 0;
    for (V value : sortedMap.values()) {
      if (currentIndex == n) {
        return value;
      }
      currentIndex++;
    }
    return null; // This should never happen if bounds are checked
  }

  // Method to retrieve the entire sorted map
  public Map<K, V> getSortedMap() { return sortedMap; }

  // Internal method to sort the map based on values
  private void sortMap() {
    List<Map.Entry<K, V>> entryList = new ArrayList<>(map.entrySet());
    entryList.sort(valueComparator);

    // Rebuild the sorted LinkedHashMap
    sortedMap = new LinkedHashMap<>();
    for (Map.Entry<K, V> entry : entryList) {
      sortedMap.put(entry.getKey(), entry.getValue());
    }
  }
}

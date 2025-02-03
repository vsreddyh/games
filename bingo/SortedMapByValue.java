import java.util.*;

public class SortedMapByValue{
  HashMap<String,Integer> UnsortedMap = new HashMap<>();
  ArrayList<String> SortedKeys = new ArrayList<>();

  public void put(String key, Integer value) {
    UnsortedMap.put(key, value);
    SortedKeys.add(key);
    sort();
  }

  public SortedMapByValue(){
    UnsortedMap = new HashMap<>();
    SortedKeys = new ArrayList<>();
  }
  
  public SortedMapByValue(SortedMapByValue other){
    UnsortedMap = new HashMap<>(other.UnsortedMap);
    SortedKeys = new ArrayList<>(other.SortedKeys);
  }
  
  public void sort() {
    Collections.sort(SortedKeys,new Comparator<String>() {
      @Override
      public int compare(String o1, String o2) {
        return UnsortedMap.get(o2).compareTo(UnsortedMap.get(o1));
      }
    });
  }
  
  public void set(String key, Integer value) {
    UnsortedMap.put(key, value);
    sort();
  }
  
  public Integer get(String key) {
    return UnsortedMap.get(key);
  }
  public Integer getNthValue(int n){
    return UnsortedMap.get(SortedKeys.get(n));
  }
  public void print(){
      System.out.println(UnsortedMap);
  }
  
  public static void main(String[] args){
		SortedMapByValue s = new SortedMapByValue();
    s.put( "a", 1 );
    s.put( "b", 2 );
    s.put( "c", 3 );
    s.put( "d", 4 );
    s.print();
    s.set("b", 15);
    s.print();
    System.out.println(s.getNthValue(1));
  }
}
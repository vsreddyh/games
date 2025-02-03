import java.util.*;
import java.util.stream.*;

public class Bingov2 {
  static ArrayList<Integer> originalIndexValue =
      new ArrayList<>(IntStream.rangeClosed(1, 25).boxed().toList());
  static ArrayList<Integer> indexPriority =
      new ArrayList<>(Collections.nCopies(25, 0));
  static Scanner sc = new Scanner(System.in);
  static boolean showBingo = false;
  static String bingo = "BINGO";
  static SortedMapByValue marked = new SortedMapByValue();
  static SortedMapByValue init = new SortedMapByValue();

  public static void main(String[] args) {
    indexPriority.set(12, 2);
    indexPriority.set(0, 1);
    indexPriority.set(4, 1);
    indexPriority.set(6, 1);
    indexPriority.set(8, 1);
    indexPriority.set(16, 1);
    indexPriority.set(18, 1);
    indexPriority.set(20, 1);
    indexPriority.set(24, 1);
    init.put("vertical", 0);
    init.put("horizantal", 0);
    init.put("diagonal1", 0);
    init.put("diagonal2", 0);
    marked.put("vertical", -1);
    marked.put("horizantal", -1);
    marked.put("diagonal1", -1);
    marked.put("diagonal2", -1);
    startBingo();
    sc.close();
  }

  public static void clearScreen() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void listCompleted(HashSet<Integer> completed) {
    System.out.println("List of completed");
    for (int i : completed) {
      System.out.print(i + " ");
    }
    System.out.println("\n");
  }

  public static void startBingo() {
    Collections.shuffle(originalIndexValue);
    ArrayList<Integer> indexValue = new ArrayList<>(originalIndexValue);
    HashSet<Integer> arrayOfCompleted = new HashSet<>();
    HashMap<Integer, Integer> valueIndex = new HashMap<>();
    for (int i = 0; i < 25; i++) {
      valueIndex.put(indexValue.get(i), i);
    }
    ArrayList<SortedMapByValue> indexWeight =new ArrayList<>();
    for (int i = 0; i < 25; i++){
      indexWeight.add(new SortedMapByValue(init));
    }
    askShowBingo();
    int count = 0;
    clearScreen();
    while (true) {
      int bingocount = checkBingo(indexValue);
      if (bingocount >= 5) {
        System.out.println("I won LOL Loser");
        newGame();
      } else {
        System.out.println(bingo.substring(0, bingocount));
      }
      printGrid(indexValue);//Debug
      printPriorityGrid(indexValue,indexWeight);//Debug
      int myChoice = codeTurn(indexWeight, 0);
      count++;
      int value = indexValue.get(myChoice);
      System.out.println("My choice is " + value);
      if (count >= 17) {
        askBingo();
      }
      checkNumber(myChoice, arrayOfCompleted, indexWeight, indexValue);
      listCompleted(arrayOfCompleted);
      if (showBingo) {
        printGrid(indexValue);
      }
      printPriorityGrid(indexValue,indexWeight);//Debug
      System.out.print("Enter your number:-");
      int num = sc.nextInt();
      //clearScreen();
      if (arrayOfCompleted.contains(num)) {
        System.out.println("We already crossed this one. Try another");
        continue;
      }
      if (num > 25 || num < 1) {
        System.out.println("Go learn counting first");
        continue;
      }
      count++;
      int index = valueIndex.get(num);
      if (count >= 17) {
        askBingo();
      }
      System.out.println("Your choice is " + index);
      checkNumber(index, arrayOfCompleted, indexWeight, indexValue);
    }
  }

  public static void
  checkNumber(int index, HashSet<Integer> arrayOfCompleted,
              ArrayList<SortedMapByValue> indexWeight,
              ArrayList<Integer> indexValue) {
    int num = indexValue.get(index);
    arrayOfCompleted.add(num);
    indexWeight.set(index, marked);
    indexValue.set(index, -1);
    addWeight(indexWeight, index);
  }

  public static int
  codeTurn(ArrayList<SortedMapByValue> indexWeight,
           int level) {
    int max = -1;
    int maxindex = 0;
    for (int i = 0; i < indexWeight.size(); i++) {
      if (max < getNthMax(level, indexWeight.get(i))) {
        maxindex = i;
        max = getNthMax(level, indexWeight.get(i));
      } else if (max == getNthMax(level, indexWeight.get(i))) {
        if (level <=2) {
          maxindex = codeTurn(indexWeight,level+1);
          max = getNthMax(level, indexWeight.get(i));
        } else if (indexPriority.get(maxindex) <= indexPriority.get(i)) {
          max = getNthMax(level, indexWeight.get(i));
          maxindex = i;
        }
      }
    }
    return maxindex;
  }

  public static int getNthMax(int n,
                              SortedMapByValue indexes) {
    return indexes.getNthValue(n);
  }

  public static void newGame() {
    System.out.println("Would you like to start a new game?(yes/no)");
    String yesNo = sc.next();
    if (!yesNo.equals("yes") && (!yesNo.equals("no"))) {
      System.out.println("You are so shaken up that you can't even answer a simple question");
      askBingo();
    } else if (yesNo.equals("no")) {
      System.out.println("Loser");
      System.exit(0);
    } else {
      startBingo();
    }
  }

  public static int checkBingo(ArrayList<Integer> indexValue) {
    int count = 0;
    boolean flag = true;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (indexValue.get(i * 5 + j) != -1) {
          flag = false;
          break;
        }
      }
      if (flag) {
        count++;
      }
      flag = true;
    }
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (indexValue.get(j * 5 + i) != -1) {
          flag = false;
          break;
        }
      }
      if (flag) {
        count++;
      }
      flag = true;
    }
    for (int j = 0; j < 5; j++) {
      if (indexValue.get(j * 5 + j) != -1) {
        flag = false;
        break;
      }
    }
    if (flag) {
      count++;
    }
    flag = true;
    for (int j = 0; j < 5; j++) {
      if (indexValue.get(j * 5 + (4 - j)) != -1) {
        flag = false;
        break;
      }
    }
    if (flag) {
      count++;
    }
    return count;
  }

  public static void askBingo() {
    System.out.println("Bingo yet?(yes/no)");
    String yesNo = sc.next();
    if (!yesNo.equals("yes") && (!yesNo.equals("no"))) {
      System.out.println("Learn how to read");
      askBingo();
    } else if (yesNo.equals("yes")) {
      System.out.println("Cheater");
      newGame();
    }
  }

  public static void askShowBingo() {
    System.out.println("Would you like to see the Grid?(yes/no)");
    String yesNo = sc.next();
    if (!yesNo.equals("yes") && (!yesNo.equals("no"))) {
      System.out.println("Learn how to read");
      askShowBingo();
    } else if (yesNo.equals("yes")) {
      showBingo = true;
    } else {
      showBingo = false;
    }
  }

  public static void
  addWeight(ArrayList<SortedMapByValue> indexWeight,
            int index) {
    for (int i = index; i < 25; i = i + 5) {
      update(indexWeight, i, "vertical");
    }
    for (int i = index; i > -1; i = i - 5) {
      update(indexWeight, i, "vertical");
    }
    for (int i = index + 1; i % 5 != 0; i++) {
      update(indexWeight, i, "horizantal");
    }
    for (int i = index - 1; i > -1 && i % 5 != 4; i--) {
      update(indexWeight, i, "horizantal");
    }
    if (index == 0 || index == 6 || index == 12 || index == 18 || index == 24) {
      update(indexWeight, 0, "diagonal1");
      update(indexWeight, 6, "diagonal1");
      update(indexWeight, 12, "diagonal1");
      update(indexWeight, 18, "diagonal1");
      update(indexWeight, 24, "diagonal1");
    }
    if (index == 4 || index == 8 || index == 12 || index == 16 || index == 20) {
      update(indexWeight, 4, "diagonal2");
      update(indexWeight, 8, "diagonal2");
      update(indexWeight, 12, "diagonal2");
      update(indexWeight, 16, "diagonal2");
      update(indexWeight, 20, "diagonal2");//
    }
  }

  public static void
  update(ArrayList<SortedMapByValue> indexWeight, int index,
         String type) {
    if (indexWeight.get(index).get(type) != -1) {
      SortedMapByValue toChange = new SortedMapByValue(indexWeight.get(index));
      //Debug
      System.out.println(index);
      //toChange.set(type, (indexWeight.get(index).get(type) + 1));
      System.out.println("Before");
      indexWeight.get(index).print();
      indexWeight.get(index).set(type, indexWeight.get(index).get(type) + 1);
      //System.out.println("New");
  //    toChange.print();
      //indexWeight.set(index, toChange);
      System.out.println("After");
      indexWeight.get(index).print();
    }
  }

  public static void printGrid(ArrayList<Integer> grid) {
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          if (grid.get(i * 5 + j) < 10 && grid.get(i * 5 + j) > -1) {
            System.out.print(0);
          }
          System.out.print(grid.get(i * 5 + j) + "  ");
        }
        System.out.println();
      }
      System.out.println();
    }

  
  public static void printPriorityGrid(ArrayList<Integer> grid, ArrayList<SortedMapByValue> indexWeight) {
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        int a = getNthMax(0,indexWeight.get(i * 5 +j));
        if (a > -1) {
          System.out.print(" ");
        }
        System.out.print(a+ "  ");
      }
      System.out.println();
    }
    System.out.println();
  }
}

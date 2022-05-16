package work.cib.excel.excelUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransformTable {
    public static void main(String[] args) {
        List<List<Integer>> list = generateTable();
        list.forEach(System.out::println);
        System.out.println("111111111111111111111");
        removeColumn(list, 1);
        list.forEach(System.out::println);
        System.out.println("2222222222222222222222");
        removeColumn(list, 1, 3);
        list.forEach(System.out::println);

    }

    public static List<List<Integer>> removeColumn(List<List<Integer>> list, int column) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = list.get(i).size() - 1; j >= 0; j--) {
                if (j == column) {
                    list.get(i).remove(column);
                }
            }
        }
        return list;
    }

    public static List<List<Integer>> removeColumn(List<List<Integer>> list, int startColumn, int endColumn) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).subList(startColumn, endColumn).clear();
        }
        return list;
    }


    public static List<List<Integer>> generateTable() {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        Collections.addAll(list1, 1, 2, 3, 4);
        Collections.addAll(list2, 5, 6, 7, 8);
        Collections.addAll(list3, 9, 1, 2, 3);
        Collections.addAll(list, list1, list2, list3);
        return list;
    }
}

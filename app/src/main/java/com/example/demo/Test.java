package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int c = scanner.nextInt();

        int [][]colors = new int[n][51];
        for (int i = 0; i < n; i++) {
            colors[i][0] = scanner.nextInt();
            for (int j = 1; j <= colors[i][0]; j++) {
                colors[i][j]= scanner.nextInt();
            }
        }
        scanner.close();

        Solution solution = new Solution();

        int result = solution.function(n, m, colors);

        System.out.println(result);

    }

    static class Solution{

        public int function(int n, int m, int[][] colors) {
            Map<Integer, List<Integer>> posMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                for (int j = 1; j <= colors[i][0]; j++) {
                    int colorType = colors[i][j];
                    if (colorType != 0) {
                        if (!posMap.containsKey(colorType)) {
                            List<Integer> list = new ArrayList<>();
                            list.add(i);
                            posMap.put(colorType, list);
                        } else {
                            List<Integer> list = posMap.get(colorType);
                            list.add(i);
                        }
                    }
                }
            }

            Set<Integer> set = posMap.keySet();
            Iterator<Integer> iterator = set.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                Integer integer = iterator.next();
                List<Integer> list = posMap.get(integer);
                for (int i = 0; i < list.size(); i++) {
                    int pos = list.get(i) + m - 1;
                    if (list.contains(pos) || list.contains(pos % n)) {
                        count++;
                    }
                }
            }
            return count;
        }
    }
}

package com.fast_build_auth;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MyTest {

    static class ListNode {
        private int value;
        List<ListNode> childNode;

        public ListNode(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        ListNode root = new ListNode(0);
        List list = dfs(root, 10, 0);
    }

    public static List dfs(ListNode root, int sum, int depth) {
        List<List<Integer>> list = new ArrayList();
        while (root != null && sum > 0) {
            if (list.size() == depth) {
                list.add(new ArrayList<>());
            }
            List<ListNode> chileNode = root.childNode;
            if (chileNode.size() != 0) {
                for (ListNode child : chileNode) {
                    list.get(depth).add(child.value);
                    dfs(child, sum - root.value, depth + 1);
                }
            }
        }
        return list;
    }
}

package de.tuberlin.dima.dbt.exercises.bplustree;

import org.junit.Test;

import static de.tuberlin.dima.dbt.grading.BPlusTreeMatcher.isTree;
import static de.tuberlin.dima.dbt.exercises.bplustree.BPlusTreeUtilities.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class BPlusTreeTest {

    private BPlusTree tree;

    ///// Lookup tests

    @Test
    public void findKeyInLeaf() {
        // given
        tree = newTree(newLeaf(keys(1, 2, 3), values("a", "b", "c")));
        // when
        String value = tree.lookup(2);
        // then
        assertThat(value, is("b"));
    }

    @Test
    public void findNoKeyInLeaf() {
        // given
        tree = newTree(newLeaf(keys(1, 3), values("a", "c")));
        // when
        String value = tree.lookup(2);
        // then
        assertThat(value, is(nullValue()));
    }

    @Test
    public void findKeyInChild1() {
        // given
        tree = newTree(newNode(keys(3),
                               nodes(newLeaf(keys(1, 2), values("a", "b")),
                                     newLeaf(keys(3, 4), values("c", "d")))));
        // when
        String value = tree.lookup(3);
        // then
        assertThat(value, is("c"));
    }

    @Test
    public void findKeyInChild() {
        // given
        tree = newTree(newNode(keys(111,125,150,163),
                nodes(newLeaf(keys(85,98), values("qbV", "pfg")),
                      newLeaf(keys(114,124), values("OfK", "ZCc")),
                      newLeaf(keys(125,132), values("Lhp","Zws")),
                      newLeaf(keys(150,160), values("Odc","BkD")),
                      newLeaf(keys(163,177), values("TGh","kYn")) )));
        // when
        String value = tree.lookup(177);
        // then
        assertThat(value, is("kYn"));
    }

    @Test
    public void findNoKeyInChild() {
        // given
        tree = newTree(newNode(keys(3),
                               nodes(newLeaf(keys(1, 3), values("a", "c")),
                                     newLeaf(keys(5, 7), values("e", "g")))));
        // when
        String value = tree.lookup(6);
        // then
        assertThat(value, is(nullValue()));
    }


    ///// Insertion tests

    @Test
    public void insertIntoLeaf() {
        // given
        tree = newTree(newLeaf(keys(1, 3), values("a", "c")));
        // when
        tree.insert(2, "b");
        // then
        assertThat(tree, isTree(
                newTree(newLeaf(keys(1, 2, 3), values("a", "b", "c")))));
    }




    @Test
    public void splitLeafs() {
        // given
        tree = newTree(newNode(keys(3),
                               nodes(newLeaf(keys(1, 2), values("a", "b")),
                                     newLeaf(keys(3, 4, 5, 6),
                                             values("c", "d", "e", "f")))));
        // when
        tree.insert(7, "g");
        // then
        assertThat(tree, isTree(newTree(newNode(
                keys(3, 5),
                nodes(newLeaf(keys(1, 2), values("a", "b")),
                      newLeaf(keys(3, 4), values("c", "d")),
                      newLeaf(keys(5, 6, 7), values("e", "f", "g")))))));
    }

//    ///// Deletion tests

    @Test
    public void deleteFromLeaf() {
        // given
        tree = newTree(newLeaf(keys(102,118,130,140), values("qPt", "ONm", "ujW", "IvH")));
        // when
        String value = tree.delete(140);
        // then
        assertThat(value, is("IvH"));
        assertThat(tree, isTree(
                newTree(newLeaf(keys(102,118,130), values("qPt", "ONm", "ujW")))));
    }
//
//    @Test
//    public void deleteFromChild() {
//        // given
//        tree = newTree(newNode(
//                keys(4), nodes(newLeaf(keys(1, 2, 3), values("a", "b", "c")),
//                               newLeaf(keys(4, 5), values("d", "e")))));
//        // when
//        String value = tree.delete(1);
//        // then
//        assertThat(value, is("a"));
//        assertThat(tree, isTree(newTree(newNode(
//                keys(4), nodes(newLeaf(keys(2, 3), values("b", "c")),
//                               newLeaf(keys(4, 5), values("d", "e")))))));
//    }
//
//    @Test
//    public void deleteFromChildStealFromSibling() {
//        // given
//        tree = newTree(newNode(
//                keys(3), nodes(newLeaf(keys(1, 2), values("a", "b")),
//                               newLeaf(keys(3, 4, 5), values("c", "d", "e")))));
//        // when
//        String value = tree.delete(1);
//        // then
//        assertThat(value, is("a"));
//        assertThat(tree, isTree(newTree(newNode(
//                keys(4), nodes(newLeaf(keys(2, 3), values("b", "c")),
//                               newLeaf(keys(4, 5), values("d", "e")))))));
//
//    }
//
//    @Test
//    public void deleteFromChildMergeWithSibling() {
//        // given
//        tree = newTree(newNode(keys(3, 5),
//                               nodes(newLeaf(keys(1, 2), values("a", "b")),
//                                     newLeaf(keys(3, 4), values("c", "d")),
//                                     newLeaf(keys(5, 6), values("e", "f")))));
//        // when
//        String value = tree.delete(2);
//        // then
//        assertThat(value, is("b"));
//        assertThat(tree, isTree(newTree(newNode(
//                keys(5), nodes(newLeaf(keys(1, 3, 4), values("a", "c", "d")),
//                               newLeaf(keys(5, 6), values("e", "f")))))));
//    }

}

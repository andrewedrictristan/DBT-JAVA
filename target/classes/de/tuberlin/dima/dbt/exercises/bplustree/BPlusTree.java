package de.tuberlin.dima.dbt.exercises.bplustree;

import javax.sound.midi.Soundbank;
import java.sql.SQLOutput;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Implementation of a B+ tree.
 * <p>
 * The capacity of the tree is given by the capacity argument to the
 * constructor. Each node has at least {capacity/2} and at most {capacity} many
 * keys. The values are strings and are stored at the leaves of the tree.
 * <p>
 * For each inner node, the following conditions hold:
 * <p>
 * {pre}
 * Integer[] keys = innerNode.getKeys();
 * Node[] children = innerNode.getChildren();
 * {pre}
 * <p>
 * - All keys in {children[i].getKeys()} are smaller than {keys[i]}.
 * - All keys in {children[j].getKeys()} are greater or equal than {keys[i]}
 * if j > i.
 */
public class BPlusTree {

    ///// Implement these methods

    private LeafNode findLeafNode(Integer key, Node node,
                                  Deque<InnerNode> parents) {

        if (node instanceof LeafNode) {
            return (LeafNode) node;
        } else {
            InnerNode innerNode = (InnerNode) node;
            if (parents != null) {
                parents.push(innerNode);
            }
            // TODO: traverse inner nodes to find leaf node
            System.out.println("findLeafNode Function!!");
            Integer[] keys = innerNode.getKeys();
            Node[] children = innerNode.getChildren();
            int nodeIndex = -1;
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] != null) {
                    nodeIndex += 1;
                    if (key < keys[i]) {

                        System.out.println("key " + key + " is LESS than " + keys[i]);
                        //nodeIndex = i;
                         return findLeafNode(key, children[i], parents);
                    }
                } else {
                    break;
                }
            }
            nodeIndex += 1;
            return findLeafNode(key, children[nodeIndex], parents);
//            if (nodeIndex == keys.length -1) {
//                nodeIndex += 1;
//                return findLeafNode(key, children[nodeIndex], parents);
//            }else  {
//
//            }

        }
    }

    private String lookupInLeafNode(Integer key, LeafNode node) {
        // TODO: lookup value in leaf node
        String value = null;
        for (int i = 0; i < node.getValues().length; i++) {
            if (node.keys[i] != null) {
                if (key.intValue() == node.keys[i].intValue()) {
                    value = node.getValues()[i];
                    break;
                }
            }
        }
        return value;
    }

    private void insertIntoLeafNode(Integer key, String value,
                                    LeafNode node, Deque<InnerNode> parents) {
        // TODO: insert value into leaf node (and propagate changes up)
        LeafNode leafNode = findLeafNode(key, node);

        for (int i = 0; i < leafNode.getKeys().length; i++) {
            String[] leafNodeValues = leafNode.getValues();
            Integer[] leafNodeKeys = leafNode.getKeys();
            if (i<leafNode.getKeys().length-1){
                if(leafNode.getKeys()[i]!=null ){}
                else  { //to make sure there is space
                    if(i == 0 ) {
                        leafNodeValues[i] = value;
                        leafNodeKeys[i] = key;
                        break;
                    } else {
                        if (key > leafNode.getKeys()[i - 1]) {
                            leafNodeValues[i] = value;
                            leafNodeKeys[i] = key;
                            break;
                        } else {
                            String tempVal;
                            Integer tempKey;
                            tempVal = leafNodeValues[i - 1];
                            tempKey = leafNodeKeys[i - 1];
                            leafNodeValues[i] = tempVal;
                            leafNodeKeys[i] = tempKey;
                            leafNodeValues[i - 1] = value;
                            leafNodeKeys[i - 1] = key;
                            break;
                        }
                    }
                }
            }
           else {
                if (leafNode.getKeys()[i] == null) {
                    leafNodeValues[i] = value;
                    leafNodeKeys[i] = key;
                }else{
                //there is no space, then split and propagate
                Integer[] newKeysArray = new Integer[capacity + 1];
                String[] newValuesArray = new String[capacity + 1];
                Integer[] newLeftKeysForNewNode = new Integer[capacity];
                String[] newLeftValuesForNewNode = new String[capacity];
                Integer[] defaultKeyNewNode = new Integer[capacity];
                String[] defaultValueNewNode = new String[capacity];
                Integer[] newRightKeysForNewNode = new Integer[capacity];
                String[] newRightValuesForNewNode = new String[capacity];
                LeafNode newRightLeafNode = new LeafNode(capacity);
                LeafNode newLeftLeafNode = new LeafNode(capacity);
                int median = 0;
                int medianKey = 0;
                String medianValue = null;
                //create a new array for all keys included the new key and sort them
                for (int x = 0; x < newKeysArray.length - 1; x++) {
                    newKeysArray[x] = leafNodeKeys[x];
                    newValuesArray[x] = leafNodeValues[x];
                    if (key > newKeysArray[x]) {
                        newKeysArray[x + 1] = key;
                        newValuesArray[x + 1] = value;
                    } else {
                        String tempVal;
                        Integer tempKey;
                        tempVal = leafNodeValues[x - 1];
                        tempKey = newKeysArray[x - 1];
                        newKeysArray[x] = tempKey;
                        newValuesArray[x] = tempVal;
                        newKeysArray[x - 1] = key;
                        newValuesArray[x - 1] = value;
                    }
                }


                //calculate the median of the newKeysArray
                if (newKeysArray.length % 2 == 0) {
                    median = newKeysArray[newKeysArray.length / 2];
                } else {
                     medianKey = newKeysArray[((newKeysArray.length-1) / 2)];
                     medianValue = newValuesArray[((newKeysArray.length-1) / 2)];
                }
                //split the new array to two arrays and push the half keys to both, then update the original leafNode and the new one
                for (int y = 0, count = (capacity / 2); y < newKeysArray.length; y++, count++) {

                    newLeftKeysForNewNode[y] = newKeysArray[y];
                    newLeftValuesForNewNode[y] = newValuesArray[y];

                    if(count==5){

                        break;
                    }else{
                        newRightKeysForNewNode[y] = newKeysArray[count];
                        newRightValuesForNewNode[y] = newValuesArray[count];
                        newKeysArray[count] = null;
                        newValuesArray[count] = "";
                    }




                }
                    newLeftLeafNode.setKeys(newLeftKeysForNewNode);
                    newLeftLeafNode.setValues(newLeftValuesForNewNode);
                    newRightLeafNode.setKeys(newRightKeysForNewNode);
                    newRightLeafNode.setValues(newRightValuesForNewNode);
                    defaultKeyNewNode[0]=medianKey;
                    defaultValueNewNode[0]=medianValue;



                    for (int j = 0; j < parents.element().keys.length; j++) {
                        if (parents.element().keys[j] == null) {
                            Integer[] parentKeys = parents.element().getKeys();
                            for (int h = 0; h < parentKeys.length; h++) {
                                if (medianKey > parentKeys[h]) {
                                    parentKeys[h + 1] = medianKey;
                                    break;
                                } else {
                                    Integer tempKey;
                                    tempKey = parentKeys[h - 1];
                                    parentKeys[h] = tempKey;
                                    parentKeys[h - 1] = medianKey;
                                    break;
                                }
                            }
                            parents.element().setKeys(parentKeys);
                            Node[] newLeafNodes = parents.element().getChildren();
                            for (int m = 0; m < newLeafNodes.length; m++) {
                                if(m==1){
                                    newLeafNodes[m] = newLeftLeafNode;

                                }
                                if (newLeafNodes[m] == null) {
                                    newLeafNodes[m] = newRightLeafNode;
                                    break;
                                }
                            }
                            parents.element().setChildren(newLeafNodes);
                            break;
                        }
                    }



            }
            }
            //break;
        }
    }



    private String deleteFromLeafNode(Integer key, LeafNode node,
                                      Deque<InnerNode> parents) {
        // TODO: delete value from leaf node (and propagate changes up)
        String value = null;
        Integer[] keys = node.getKeys();
        String[] values = node.getValues();
        int index = 0;
        boolean found = false;


        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null) break;
            if (keys[i].equals(key)) {
                index = i;
                value = values[i];
                found = true;
                break;
            }
        }


        if (found) {
            //shifting to left
            if(index != keys.length-1) {
                for (int i = index; i < keys.length - 1; i++) {
                    keys[i] = keys[i + 1];
                    values[i] = values[i + 1];
                    keys[i+1]= null;
                    values[i+1]= null;
                }

            }
            else {
                keys[index] = null;
                values[index]= null;
            }
        }
        return value;
    }

    ///// Public API
    ///// These can be left unchanged

    /**
     * Lookup the value stored under the given key.
     *
     * @return The stored value, or {null} if the key does not exist.
     */
    public String lookup(Integer key) {
        LeafNode leafNode = findLeafNode(key, root);
        return lookupInLeafNode(key, leafNode);
    }

    /**
     * Insert the key/value pair into the B+ tree.
     */
    public void insert(int key, String value) {
        Deque<InnerNode> parents = new LinkedList<>();
        LeafNode leafNode = findLeafNode(key, root, parents);
        insertIntoLeafNode(key, value, leafNode, parents);
    }

    /**
     * Delete the key/value pair from the B+ tree.
     *
     * @return The original value, or {null} if the key does not exist.
     */
    public String delete(Integer key) {
        Deque<InnerNode> parents = new LinkedList<>();
        LeafNode leafNode = findLeafNode(key, root, parents);
        return deleteFromLeafNode(key, leafNode, parents);
    }

    ///// Leave these methods unchanged

    private int capacity = 0;

    private Node root;

    public BPlusTree(int capacity) {
        this(new LeafNode(capacity), capacity);
    }

    public BPlusTree(Node root, int capacity) {
        assert capacity % 2 == 0;
        this.capacity = capacity;
        this.root = root;
    }

    public Node rootNode() {
        return root;
    }

    public String toString() {
        return new BPlusTreePrinter(this).toString();
    }

    private LeafNode findLeafNode(Integer key, Node node) {
        return findLeafNode(key, node, null);
    }

}

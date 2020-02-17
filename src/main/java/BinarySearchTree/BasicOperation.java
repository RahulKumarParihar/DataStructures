package BinarySearchTree;

import Abstract.BinarySearchTree.Operation;
import Structure.TreeNode;

public class BasicOperation implements Operation {
    /**
     * Insert a node to binary search tree
     *
     * @param root     root of the binary search tree
     * @param nodeData data of the node to insert
     * @return root of the binary search tree
     */
    @Override
    public TreeNode insert(TreeNode root, int nodeData) {
        if (root == null) {
            root = new TreeNode(nodeData);
        } else {
            TreeNode position = root;
            position = findInsertPosition(position, nodeData);
            if (position.data > nodeData) {
                position.leftChild = new TreeNode(nodeData);
            } else {
                position.rightChild = new TreeNode(nodeData);
            }
        }
        return root;
    }

    /**
     * delete a node from binary search tree
     *
     * @param root             root of the binary search tree
     * @param nodeDateToDelete data of the node to delete
     * @return root of the binary search tree
     */
    @Override
    public TreeNode delete(TreeNode root, int nodeDateToDelete) {
        if (root != null) {
            TreeNode previousNode = findDeletePrevious(root, nodeDateToDelete);
            TreeNode deleteNode;
            boolean isLeftChild = false;

            if (previousNode.leftChild != null && previousNode.leftChild.data == nodeDateToDelete) {
                deleteNode = previousNode.leftChild;
                isLeftChild = true;
            } else {
                deleteNode = previousNode.rightChild;
            }

            //If delete node has two childes
            if (deleteNode.leftChild != null && deleteNode.rightChild != null) {
                TreeNode inOrderSuccessor = inOrderSuccessor(root, nodeDateToDelete);
                if (inOrderSuccessor != null) {
                    //inOrderSuccessor of the node is not the leaf node
                    if (inOrderSuccessor.rightChild != null) {
                        if (isLeftChild) {
                            if (previousNode.leftChild.leftChild != null) {
                                inOrderSuccessor.leftChild = previousNode.leftChild.leftChild;
                            }
                            previousNode.leftChild = inOrderSuccessor;
                        } else {
                            previousNode.rightChild = inOrderSuccessor;
                        }
                        //inOrderSuccessor of the node is the leaf node
                    } else {
                        deleteNode.data = inOrderSuccessor.data;
                        TreeNode iterate = deleteNode.rightChild;
                        previousNode = iterate;
                        isLeftChild = false;
                        while (iterate.data != inOrderSuccessor.data) {
                            isLeftChild = true;
                            previousNode = iterate;
                            iterate = iterate.leftChild;
                        }
                        if (isLeftChild)
                            previousNode.leftChild = null;
                        else
                            deleteNode.rightChild = null;
                    }
                }

                //If delete node has one child
            } else if (deleteNode.leftChild != null || deleteNode.rightChild != null) {
                if (isLeftChild) {
                    previousNode.leftChild = deleteNode.leftChild != null ? deleteNode.leftChild : deleteNode.rightChild;
                } else {
                    previousNode.rightChild = deleteNode.leftChild != null ? deleteNode.leftChild : deleteNode.rightChild;
                }

                // if delete node has no child
            } else {
                if (isLeftChild) {
                    previousNode.leftChild = null;
                } else {
                    previousNode.rightChild = null;
                }
            }
        }
        return root;
    }

    /**
     * Find in-order successor of the node
     *
     * @param root     root of the binary search tree
     * @param nodeData data of the node to find in order successor
     * @return in order successor of the node
     */
    @Override
    public TreeNode inOrderSuccessor(TreeNode root, int nodeData) {
        TreeNode successorNode = null;

        // successorNode of a left node
        while (root != null && root.data != nodeData) {
            if (root.data > nodeData) {
                successorNode = root;
                root = root.leftChild;
            } else {
                root = root.rightChild;
            }
        }

        // finding successorNode of a element which doesn't exits
        if (root == null)
            return null;

        if (root.rightChild != null) {
            root = root.rightChild;
            while (root.leftChild != null) {
                root = root.leftChild;
            }
            successorNode = root;
        }

        return successorNode;
    }

    /**
     * Find in-order predecessor of the node
     *
     * @param root     root of the binary search tree
     * @param nodeData data of the node to find in order predecessor
     * @return in order predecessor of the node
     */
    @Override
    public TreeNode inOrderPredecessor(TreeNode root, int nodeData) {
        TreeNode predecessor = null;

        // Predecessor of a leaf node
        while (root != null && root.data != nodeData) {
            if (root.data > nodeData) {
                root = root.leftChild;
            } else {
                predecessor = root;
                root = root.rightChild;
            }
        }

        // Find predecessor of a element which doesn't exists
        if (root == null)
            return null;

        if (root.leftChild != null) {
            root = root.leftChild;

            while (root.rightChild != null) {
                root = root.rightChild;
            }
            predecessor = root;
        }

        return predecessor;
    }

    /**
     * Check if Binary Search tree is valid or not
     *
     * @param root root of the binary search tree
     * @return True if is valid binary search tree else false
     */
    @Override
    public boolean validBST(TreeNode root) {
        return isValidHelper(root, null, null);
    }

    //<editor-fold desc="Private methods">
    private TreeNode findInsertPosition(TreeNode root, int nodeData) {
        TreeNode previousNode = root;
        while (root != null) {
            if (root.data > nodeData) {
                previousNode = root;
                root = root.leftChild;

            } else {
                previousNode = root;
                root = root.rightChild;
            }
        }
        return previousNode;
    }

    private TreeNode findDeletePrevious(TreeNode root, int nodeDataToDelete) {
        TreeNode previousNode = null;
        while (root != null) {
            if (root.data == nodeDataToDelete) {
                return previousNode;
            } else if (root.data > nodeDataToDelete) {
                previousNode = root;
                root = root.leftChild;
            } else {
                previousNode = root;
                root = root.rightChild;
            }
        }
        return null;
    }

    private boolean isValidHelper(TreeNode node, Integer maxRange, Integer minRange) {
        if (node == null)
            return true;

        if (maxRange != null && node.data >= maxRange) {
            return false;
        }

        if (minRange != null && minRange >= node.data) {
            return false;
        }

        return isValidHelper(node.leftChild, node.data, minRange) && isValidHelper(node.rightChild, maxRange, node.data);
    }
    //</editor-fold>
}

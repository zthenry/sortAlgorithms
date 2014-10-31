/*
 * 文 件 名:  BinaryTreeWalk.java
 * 描    述:  <描述>
 * 修 改 人:  张涛
 * 修改时间:  2014-10-29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.tree;

/**
 * 二叉树遍历
 * 二叉树属性:左节点的key<父节点 < 右节点
 * 
 * @author  root
 * @version  [版本号, 2014-10-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BinaryTreeWalk
{
    /**
     * 中序遍历
     * 整棵树按照从小到大的顺序输出
     * @param x
     * @see [类、类#方法、类#成员]
     */
    public void inOrderTreeWalk(Node x)
    {
        if (x != null)
        {
            inOrderTreeWalk(x.getLeft());
            System.out.println(x.getKey());
            inOrderTreeWalk(x.getRight());
        }
    }
    
    /**
     * 二叉树的检索
     * <功能详细描述>
     * @param root
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Node treeSearch(Node root, int key)
    {
        if (root == null)
        {
            return null;
        }
        if (key == root.getKey())
        {
            return root;
        }
        else if (key < root.getKey())
        {
            return treeSearch(root.getLeft(), key);
        }
        else
        {
            return treeSearch(root.getRight(), key);
        }
        
    }
    
    /**
     * 树中最小的节点，也就是最左节点
     * <功能详细描述>
     * @param root
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Node treeMiniMum(Node root)
    {
        if (root == null)
        {
            return null;
        }
        Node left = root.getLeft();
        if (left == null)
        {
            return root;
        }
        else
        {
            return treeMiniMum(left);
        }
    }
    
    /**
     * 树中最大的节点，也就是最右节点
     * <功能详细描述>
     * @param root
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Node treeMaxiMum(Node root)
    {
        if (root == null)
        {
            return null;
        }
        Node right = root.getRight();
        if (right == null)
        {
            return root;
        }
        else
        {
            return treeMaxiMum(right);
        }
    }
    
    /**
     * 向树中插入节点
     * 设置左节点，右节点，父节点
     * 按照z的值 遍历树，找到叶节点
     * @param root
     * @param z
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Node treeInsert(Node root, Node z)
    {
        if (root == null || z == null)
        {
            //空树，z是根节点
            root = z;
            return root;
        }
        Node x = root;
        Node y = null;
        while (x != null)
        {
            y = x;
            if (x.getKey() > z.getKey())
            {
                x = x.getLeft();
            }
            else
            {
                x = x.getRight();
            }
        }
        z.setParent(y);
        if (z.getKey() > y.getKey())
        {
            y.setRight(z);
        }
        else
        {
            y.setLeft(z);
        }
        return root;
    }
    
    /**
     * 删除树中某个节点
     * 节点的状态有四种:
     * 1.叶节点
     * 2.有一个左子树的节点
     * 3.有一个右子树的节点
     * 4.有左右子树的节点
     * 删除节点后，需要保持二叉树的特性，即左右子树与父节点的大小关系。
     * 删除前三种节点，不会对树的结构产生很大影响，只需要将删除节点(z)的父节点的左/右子树指向z的子树节点即可
     * 但是删除第四种节点就需要做额外的工作，即删除了这个节点后，哪个节点补充上来？
     * 按照二叉检索树的性质，补充上来的节点，一定要比他的左子树大，比右子树小:那么只能在右子树中寻找最小的节点了
     * 
     * @param root
     * @param z
     * @return
     * @see [类、类#方法、类#成员]
     */
    public void treeDeleteNode(Node z)
    {
        //y是要真正删除的节点，y肯定是一个只有一个子树或者没有子树的节点
        Node y = null;
        if (z.getLeft() == null || z.getRight() == null)
        {
            //前三种节点
            y = z;
        }
        else
        {
            //右树的最小节点，最左节点
            y = treeSuccessor(z);
        }
        //x是y节点后继节点，需要重新指定父节点的
        Node x = null;
        if (y.getLeft()!=null)
        {
            x=y.getLeft();
        }else {
            x=y.getRight();
        }
        if (x!=null)
        {
            x.setParent(y.getParent());
        }
        if (y.getParent()==null)
        {
            //y是根节点
            //x此时是根节点，不需要做处理
        }else {
            Node parent = y.getParent();
            if (y==parent.getLeft())
            {
                parent.setLeft(x);
            }else {
                parent.setRight(x);
            }
        }
        if (y!=z)
        {
            /**
             * has right and left subtree
             * y point to min of right subtree
             * 只需要将此时y的值赋予到z上
             */
            
            z.setKey(y.getKey());
            
        }
    }
    
    /**
     * 求二叉检索树中某个节点的后续节点
     * 按照中序遍历的规则进行
     * @param x
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Node treeSuccessor(Node x)
    {
        if (x.getRight() != null)
        {
            //在x节点的右子树中寻找最小节点，即最左节点
            return treeMiniMum(x.getRight());
        }
        else
        {
            //没有右子树，向上找父节点
            while (x != null)
            {
                Node parent = x.getParent();
                //既没有右子树，也没有父节点，那么他就是最后的输出节点了
                if (parent == null)
                {
                    return null;
                }
                else
                {
                    if (x == parent.getLeft())
                    {
                        //如果是父节点的左子树，那么找到了
                        return parent;
                    }
                    else
                    {
                        x = parent.getParent();
                    }
                }
            }
            return null;
        }
        
    }
}

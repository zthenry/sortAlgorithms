/*
 * 文 件 名:  RedBlackTree.java
 * 描    述:  <描述>
 * 修 改 人:  henry
 * 修改时间:  2014-10-29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.tree;

/**
 * 红黑树，红黑树也是二叉检索树，是一种特殊的二叉检索树
 * 属性:
 * 1.所有节点都是有颜色的，red or black
 * 2.root节点是black
 * 3.叶节点都是black的
 * 4.red node 的左右子树节点的颜色必须是black
 * 5.从任何一个节点开始 到他的子树的 的任何一个叶节点，所有路径上的黑色节点数量相等
 * 
 * @author  root
 * @version  [版本号, 2014-10-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class RedBlackTree
{
    /**
     * 向红黑树插入节点
     * 插入的节点颜色首先设置成red，然后根节点设置颜色为black
     * 然后再去考虑维持红黑树的性质
     * 考虑一下这样插入红色节点后，会违反哪些属性
     * 1.所有节点都是有颜色的，red or black (不会违反)
     * 2.root节点是black (不会违反)
     * 3.叶节点都是black的 (不会违反)
     * 4.red node 的左右子树节点的颜色必须是black(会违反)
     * 5.从任何一个节点开始 到他的子树的 的任何一个叶节点，所有路径上的黑色节点数量相等(不会违反)
     * @param root
     * @param z
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Node redBlackTreeInsert(Node root, Node z)
    {
        /**
         * 会违反 red node 的左右子树节点的颜色必须是black
         * 那么就针对这个进行调整
         */
        //z节点颜色为red
        z.setColor(RedBlack.RED.getValue());
        /**
         * 还是按照二叉检索树进行节点插入
         */
        Node y = null;
        Node x = root;
        //找插入节点的父节点
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
        if (y == null)
        {
            root = z;
        }
        else
        {
            if (z.getKey() > y.getKey())
            {
                y.setRight(z);
            }
            else
            {
                y.setLeft(z);
            }
        }
        //设置根节点颜色为black
        root.setColor(RedBlack.BLACK.getValue());
        
        /**
         * 调整红黑树，因为可能违反第四条属性：
         * red node 的左右子树节点的颜色必须是black
         */
        
        redBlackTreeInsertFixUp(z);
        return root;
    }
    
    /**
     * 对插入新节点的红黑树进行节点调整。保持红黑树的5个特性
     * 分析:
     * 1.如果新插入节点的父节点是黑色的，那么这个红黑树就没有必要进行调整。属性没有违反
     *   所以调整的前提是父节点的color是red
     * @param z
     * @see [类、类#方法、类#成员]
     */
    public void redBlackTreeInsertFixUp(Node z)
    {
        /**
         * 父节点的color是红色才有必要进行调整
         * 根据红黑树的特性，有如下几种场景需要调整
         * case1: 前提:z节点是red，p(z)也是red,此时p(p(z))肯定是black
         *        假设p(z)是p(p(z))的左子树
         *        那么右子树的节点能否是黑色呢？假设是黑色，可以想象一下此刻的树结构
         *        在没有插入新节点前，p(p(z))到叶节点路径上的黑色节点数量就不相等了。
         *        所以右子树的根节点肯定是红色的。
         *        综上所述，case1的情况如下:z节点是red，p(z)也是red,此时p(p(z))肯定是black，p(p(z))的右子树的节点是red,用y表示右子树根节点
         *        这种情况下的调整方案:将p(z)的color设置为black,y的color设置为black,p(p(z))的颜色设置为red,这么调整的目的是保持原有的路径黑色节点数量，同时不违反red节点下都是黑节点
         *        如此调整后，将z指向p(p(z)),既然已经将z的color设置为red了，那么z的父节点是否为red(还是属性4),黑色的话就不用管，red的话就会有第二种场景case2。
         * case2: 经过case1的调整，z指向了新的节点,可以画图看看,如果p(z)的color是red,就需要调整了。
         *        此时，z是red,p(z)是red，那么p(p(z))肯定是black。
         *        假设p(z)是p(p(z))的左子树，那么y为p(p(z))的右子树，如果y的color是red，那么就和场景1一致了，继续采用case1的算法进行调整，
         *        所以case2的情况应该是这样的：z是red,p(z)是red,p(p(z))是black,y是black.
         */
        while (z.getParent().getColor().equals(RedBlack.RED.getValue()))
        {
            Node zParent = z.getParent();
            Node zGrandPa = zParent.getParent();
            if (zParent == zGrandPa.getLeft())
            {
                //y指向祖父节点的右节点
                Node y = zGrandPa.getRight();
                if (y.getColor().equals(RedBlack.RED.getValue()))
                {
                    //场景1出来了
                    //父节点设置为black,祖父节点设置为red,父节点的兄弟节点设置为black
                    zParent.setColor(RedBlack.BLACK.getValue());
                    y.setColor(RedBlack.BLACK.getValue());
                    zGrandPa.setColor(RedBlack.RED.getValue());
                    
                    //z指针上移，指向祖父节点
                    z = zGrandPa;
                }
                else if (z == zParent.getRight())
                {
                    //case2出现了
                    z = zParent;
                    //左旋
                    leftNotationTree(z);
                }
                else
                {
                    //case3场景
                    zParent.setColor(RedBlack.BLACK.getValue());
                    zParent.setColor(RedBlack.RED.getValue());
                    rightNotationTree(zGrandPa);
                }
            }
            else
            {
                //同左树的算法，调换left和right即可
            }
        }
    }
    
    /**
     * 左旋
     * x是y的父节点，y是x的右子树
     * 左旋算法:
     * y的父节点设置  x的父节点设置，x的右子树设置，y的左子树设置
     * y的父节点=x的父节点
     * x的父节点=y
     * x右子树=y左子树
     * y左子树=x
     * @param x
     * @see [类、类#方法、类#成员]
     */
    public void leftNotationTree(Node x)
    {
        Node y = x.getRight();
        y.setParent(x.getParent());
        x.setParent(y);
        x.setRight(y.getLeft());
        y.setLeft(x);
        
    }
    
    /**
     * 右旋
     * x是y的父节点，y是x的左子树
     * 右旋算法:
     * y的父节点设置，x的父节点设置，x的左子树设置，y的右子树设置
     * @param x
     * @see [类、类#方法、类#成员]
     */
    public void rightNotationTree(Node x)
    {
        Node y = x.getLeft();
        y.setParent(x.getParent());
        x.setParent(y);
        x.setLeft(y.getRight());
        y.setRight(x);
        
    }
    
    /**
     * 删除红黑树中的节点
     * 根据删除节点的左右子树情况，可以分为4种情况
     * 1.叶节点
     * 2.有一个左子树的节点
     * 3.有一个右子树的节点
     * 4.有左右子树的节点
     * 还是按照普通的二叉查找树进行删除
     * 假设被删除的节点是red，那么与他相关的路径的黑色节点数量在他被删除后不会受影响，他的子节点重新设置父节点后也不会违背红黑树属性
     * 只有当真正删除的节点颜色是black时，则需要进行重新对红黑树进行重构
     * @param x
     * @see [类、类#方法、类#成员]
     */
    public void redBlackTreeDelete(Node z)
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
        if (y.getLeft() != null)
        {
            x = y.getLeft();
        }
        else
        {
            x = y.getRight();
        }
        if (x != null)
        {
            x.setParent(y.getParent());
        }
        if (y.getParent() == null)
        {
            //y是根节点
            //x此时是根节点，不需要做处理
        }
        else
        {
            Node parent = y.getParent();
            if (y == parent.getLeft())
            {
                parent.setLeft(x);
            }
            else
            {
                parent.setRight(x);
            }
        }
        if (y != z)
        {
            /**
             * has right and left subtree
             * y point to min of right subtree
             * 只需要将此时y的值赋予到z上
             */
            
            z.setKey(y.getKey());
            
        }
        
        if (y.getColor().equals(RedBlack.BLACK.getValue()))
        {
            //如果删除的节点是黑色的，需要对红黑树进行重构
            /**
             * 违背的属性:
             * 1.red node 下只能是黑色子节点
             * 2.black-height不同了
             * 四种场景需要处理
             */
            
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
     * 删除y后，需要对红黑树进行重构
     * 属性:
    * 1.所有节点都是有颜色的，red or black
    * 2.root节点是black
    * 3.叶节点都是black的
    * 4.red node 的左右子树节点的颜色必须是black
    * 5.从任何一个节点开始 到他的子树的 的任何一个叶节点，所有路径上的黑色节点数量相等
    * 
     * y是黑节点，x是y的子节点
     * 三个问题:
     * 1.如果y是根节点，x是red，那么y被删除后，x是根节点。违背了属性2
     * 2.如果y不是根节点，父节点p(y)和x都是red，违背了属性4
     * 3.y是黑色节点，从路径中被删除后，肯定导致父节点的bh发生变化，违背属性5
     * @param x
     * @see [类、类#方法、类#成员]
     */
    public void redBlackTreeDeleteFixUp(Node x)
    {
        
        while (x.getParent() != null && x.getColor().equals(RedBlack.BLACK.getValue()))
        {
            //x为非根节点，且x是黑
            
        }
        //针对问题1和问题2，直接设置x的颜色为黑色，就解决了问题。所以在循环中不用考虑x是红色情况
        x.setColor(RedBlack.BLACK.getValue());
    }
}

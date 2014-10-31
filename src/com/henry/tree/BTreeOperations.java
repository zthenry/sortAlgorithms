/*
 * 文 件 名:  BTreeOperations.java
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.tree;

import com.henry.tree.BTreeNode.SearchResult;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  root
 * @version  [版本号, 2014-10-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BTreeOperations
{
    
    /**
     * 在某个B 树上查找key
     * 返回节点，已经index
     * @param node
     * @param k
     * @return
     * @see [类、类#方法、类#成员]
     */
    public SearchResult searchBTree(BTreeNode node,int k){
        return node.searchKey(k);
    }
    
    /**
     * 将一个key插入到B-tree
     * 肯定是将key插入到一个已经存在的node中
     * 但是不要忘记B-tree关于key数量的限制，t-1~2t-1
     * 所以在一个node 中key数量已经==2t-1的情况下，需要对它进行拆分
     * @param k
     * @return
     * @see [类、类#方法、类#成员]
     */
    public BTreeNode insertBTree(BTree bt,int k){
        BTreeNode root = bt.getRoot();
        if (root.isFull())
        {
           /**
            * 根节点满了
            * 申请新的节点s
            * 将s的子树指向root
            * 对root进行拆分
            * 拆分完成后进行插入操作
            */
            BTreeNode s = new BTreeNode(bt.getT());
            s.getSubTreeNodeList()[0]=root;
            spiltChildBTree(s, 0, root);
            insertBTreeNonFull(s, k);
        }else {
            insertBTreeNonFull(root, k);
        }
        return null;
    }
    
    /**
     * key插入到叶节点中
     * 如果是叶节点，那么将key从后开始移动，将key插入到合适的位置
     * 如果是内部节点,遍历key，找到合适的子节点
     * 如果子节点是full，那么对子节点进行拆分
     * 拆分完成后，重新调用该方法
     * @param currentNode
     * @param k
     * @see [类、类#方法、类#成员]
     */
    public void insertBTreeNonFull(BTreeNode currentNode,int k){
        int size = currentNode.getSize();
        int[] currKeys = currentNode.getKeyList();
        if (currentNode.isLeaf())
        {
            //将key放到叶节点中
            //key后移
            int[] newBTreKeys = new int[size+1];
            
            for (int i = size-1; i >=0; i--)
            {
                if (currKeys[i]>k || (currKeys[i]<k && currKeys[i+1]>k))
                {
                    newBTreKeys[i+1]=k;
                    newBTreKeys[i]=currKeys[i];
                }else{
                    newBTreKeys[i]=currKeys[i];
                }
                
            }
            currentNode.setKeyList(newBTreKeys);
        }else {
            int index = 0;
            for (int i = size-1; i >=0; i--)
            {
                if (currKeys[i]<k)
                {
                    index = i;
                    break;
                }
                
            }
            //第index个子节点
            index++;
            BTreeNode subBTreeNode = currentNode.getSubTreeNodeList()[index];
            if (subBTreeNode.isFull())
            {
                spiltChildBTree(currentNode, index, subBTreeNode);
                if (k>currentNode.getKeyList()[index])
                {
                    index++;
                }
                insertBTreeNonFull(currentNode.getSubTreeNodeList()[index], k);
            }
        }
    }
    /**
     * 对节点进行拆分
     * 也就是将一个已经是full 的节点进行拆分，用中间的key进行分解，即索引是t-1, 拆分成 2个 t-1的节点，
     * 并将这个中间key提升到父节点中，同时将key的左右指向这两个子节点
     * @param parent
     * @param index
     * @param child
     * @see [类、类#方法、类#成员]
     */
    public void spiltChildBTree(BTreeNode parent,int index,BTreeNode child){
        BTreeNode newNode = new BTreeNode(child.getT());
        newNode.setLeaf(child.isLeaf());
        child.splitBTreeKey(parent, index, newNode);
        
    }
    
    /**
     * 分析:
     * 这个key可能存在于内部节点，也有可能在叶节点，所有删除操作在任何节点都有可能发生
     * 在不做任何判断的情况下，在一个节点内，将一个key删除，我们考虑一下，是否违反b-tree的性质
     * 有可能违反:除根节点外，其他节点的key数量不能小于t-1
     * 所以从节点内删除一个key的时候必须保证节点内key的数量删除之前必须大于t-1
     * case：
     * 1.k在节点x中，且x是leaf，x的key的数量>t-1,直接删除k
     * 2.k在节点x中，且x是内部节点，按照如下流程进行:
     *   a.在x节点中,假设k的前继子树是y，y的key的数量至少有t,那么就可以将y中最大的k,我们记作ky=maxkey(y);
     *     将ky从节点y中删除，然后用ky代替k即可
     *   b.如果前继子树key数量<t,那么查看k的后续子树的z,如果z的key的数量至少有t，那么就可以将z中最小的kz=minKey(z),
     *     将kz从z中删除，用kz代替k即可
     *   c.如果k的前继，后继子树的key数量都是t-1，那么将z merge到y,x节点中的k也将下移到y,x将失去k和指向z的子树指针，然后从y中将k删除
     *     
     * 3.如果x中不包含k,那么继续在子树中寻找，假设y肯定在这个B-tree中。
     *   假设我们在w节点中找到了k,并且w是叶节点,且w的key数量=t-1(如果不是叶节点,那么就可以按照case2进行处理，如果是leaf,并且key num>t-1,按照case1处理)
     *   a.如果w的相邻的右兄弟节点A的key数量>t-1,将指向这个子树相关位置的key从w的父节点p(w)移动到w中，然后从A中将最小的key替换p(w)中下移的key。
     *     没有右兄弟节点，查看左兄弟节点，相关操作类似
     *   b.如果w的相邻的右兄弟节点的key数量=t-1，那么合并两个兄弟节点，并且将对应的key从父节点下移到merge的中间节点。
     * <功能详细描述>
     * @param tree
     * @param k
     * @see [类、类#方法、类#成员]
     */
    public void deleteKeyFromBTree(BTreeNode root,int k){
        SearchResult result = root.searchKey(k);
        BTreeNode node = result.getNode();
        int t = root.getT();
        if (node.isLeaf() && node.getSize() >= t)
        {
            node.deleteKForLeaf(result.getIndex(), k);
            return;
        }else if (node.isLeaf() && node.getSize() == t - 1)
        {
            BTreeNode parent = node.getParentNode();
            BTreeNode[] subTreeNodeList = parent.getSubTreeNodeList();
            int indexForSubTree = 0;
            for (int i = 0; i < subTreeNodeList.length; i++)
            {
                if (subTreeNodeList[i] == node)
                {
                    indexForSubTree = i;
                    break;
                }
                
            }
            //有右兄弟节点
            if (indexForSubTree < parent.getSize())
            {
                BTreeNode rightBrotherNode = subTreeNodeList[indexForSubTree + 1];
                if (rightBrotherNode.getSize() > t - 1)
                {
                    int indexForKey = result.getIndex();
                    int moveLowerKey = parent.getKeyList()[indexForSubTree];
                    int moveUpKey = rightBrotherNode.getKeyList()[0];
                    parent.getKeyList()[indexForSubTree] = moveUpKey;
                    node.replace(indexForKey, moveLowerKey);
                }
                else
                {
                    //右兄弟节点key==t-1,合并两个节点，并将父节点对应的key下移,判断父节点此时的key的数量>t-1,如果不满足条件，则将合并后的节点最大key上移
                    if (!parent.isSizeMin())
                    {
                        //t-1 + t-1 + 1 + -1 
                        int[] newkeyList = new int[2*t-2];
                        for (int i = 0; i < t-1; i++)
                        {
                            if (node.getKeyList()[i]!=k)
                            {
                                newkeyList[i]=node.getKeyList()[i];
                            }else
                            {
                                newkeyList[i]=parent.getKeyList()[indexForSubTree];
                            }
                            
                        }
                        
                        for (int i = 0; i < t-1; i++)
                        {
                            //合并的key
                            newkeyList[i+t-1]=rightBrotherNode.getKeyList()[i];
                            
                        }
                        node.setKeyList(newkeyList);
                        //删除父节点下移的key，父节点子树列表更新，删除指向右兄弟的指针
                        parent.delete(indexForSubTree);
                        
                    }else {
                        
                    }
                }
            }
            else if (indexForSubTree > 0)
            {
                //有左兄弟节点   
            }
            
            return;
        }
        else if (!node.isLeaf())
        {
            //case2 node是内部节点
            int indexForK = result.getIndex();
            BTreeNode successor = node.getSubTreeNodeList()[indexForK+1];
            BTreeNode preNode = node.getSubTreeNodeList()[indexForK];
            if (preNode.getSize()>t-1)
            {
                int maxKey = preNode.getKeyList()[preNode.getSize()-1];
                node.getKeyList()[indexForK]=maxKey;
                deleteKeyFromBTree(preNode, maxKey);
            }else if (successor.getSize()>t-1){
                int minKey = successor.getKeyList()[successor.getSize()-1];
                node.getKeyList()[indexForK]=minKey;
                deleteKeyFromBTree(successor, minKey);
            }else {
                //合并两个子树
                int[] preKeyList = preNode.getKeyList();
                int[] successorKeyList = successor.getKeyList();
                int[] newKeyList = new int[2*t-1];
                //key列表合并
                for (int i = 0; i < t-1; i++)
                {
                    newKeyList[i]=preKeyList[i];
                    newKeyList[2*t-2-i]=successorKeyList[t-2-i];
                }
                newKeyList[t-1]=k;
                
                //子树列表合并
                BTreeNode[] preSubBTreeNode = preNode.getSubTreeNodeList();
                BTreeNode[] successorSubBTreeNode = successor.getSubTreeNodeList();
                BTreeNode[] newSubTreeList = new BTreeNode[2*t];
                for (int i = 0; i < t; i++)
                {
                    newSubTreeList[i]=preSubBTreeNode[i];
                    newSubTreeList[2*t-1-i]=successorSubBTreeNode[t-1-i];
                }
                
                preNode.setKeyList(newKeyList);
                preNode.setSubTreeNodeList(newSubTreeList);
                
                node.delete(indexForK);
                
                deleteKeyFromBTree(node.getSubTreeNodeList()[indexForK], k);
            }
            
        }
    }
}

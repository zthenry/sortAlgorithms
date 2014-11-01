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
    public SearchResult searchBTree(BTreeNode node, int k)
    {
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
    public BTreeNode insertBTree(BTree bt, int k)
    {
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
            s.getSubTreeNodeList()[0] = root;
            spiltChildBTree(s, 0, root);
            insertBTreeNonFull(s, k);
        }
        else
        {
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
    public void insertBTreeNonFull(BTreeNode currentNode, int k)
    {
        int size = currentNode.getSize();
        int[] currKeys = currentNode.getKeyList();
        if (currentNode.isLeaf())
        {
            //将key放到叶节点中
            //key后移
            int[] newBTreKeys = new int[size + 1];
            
            for (int i = size - 1; i >= 0; i--)
            {
                if (currKeys[i] > k || (currKeys[i] < k && currKeys[i + 1] > k))
                {
                    newBTreKeys[i + 1] = k;
                    newBTreKeys[i] = currKeys[i];
                }
                else
                {
                    newBTreKeys[i] = currKeys[i];
                }
                
            }
            currentNode.setKeyList(newBTreKeys);
        }
        else
        {
            int index = 0;
            for (int i = size - 1; i >= 0; i--)
            {
                if (currKeys[i] < k)
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
                if (k > currentNode.getKeyList()[index])
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
    public void spiltChildBTree(BTreeNode parent, int index, BTreeNode child)
    {
        BTreeNode newNode = new BTreeNode(child.getT());
        newNode.setLeaf(child.isLeaf());
        child.splitBTreeKey(parent, index, newNode);
        
    }
    
    
    public void delete(BTreeNode root, int k)
    {
        SearchResult result = root.searchKey(k);
        //k不在这个b-tree中，不需要进入算法
        if (result == null)
        {
            return;
        }
        
        //root
        deleteFromBtree(root, k);
        
        
    }
    
    /**
     * 分析:
     * 这个key可能存在于内部节点，也有可能在叶节点，所有删除操作在任何节点都有可能发生
     * 在不做任何判断的情况下，在一个节点内，将一个key删除，我们考虑一下，是否违反b-tree的性质
     * 有可能违反:除根节点外，其他节点的key数量不能小于t-1
     * 所以从节点内删除一个key的时候必须保证节点内key的数量删除之前必须大于t-1
     * case：
     * 1.k在节点x中，且x是leaf，直接删除k
     * 2.k在节点x中，且x是内部节点，按照如下流程进行:
     *   a.在x节点中,假设k的前继子树是y，y的key的数量至少有t,那么就可以将y中最大的k,我们记作ky=maxkey(y);
     *     在x中用ky代替k，然后重新进入算法，入参是节点y,删除ky.
     *   b.如果前继子树key数量<t,那么查看k的后续子树的z,如果z的key的数量至少有t，那么就可以将z中最小的kz=minKey(z),
     *     用kz代替k,重新进入算法，入参:节点z,删除kz，
     *   c.如果k的前继，后继子树的key数量都是t-1，那么将z merge到y,x节点中的k也将下移到y,x将失去k和指向z的子树指针，然后从y中将k删除
     *     
     * 3.如果x中不包含k,那么继续在子树中寻找，假设y肯定在这个B-tree中。
     *   假设k的下一个搜索路径在在x的第i个子节点Ci[x]中，如果Ci[x]key的size=t-1,则进入我们这个case3
     *   我们用w表示Ci[x],size(w)=t-1,w肯定是有兄弟节点的。i为第i个子节点
     *   a.如果w节点的相邻节点A是右节点，并且A的key数量>t-1,将A中最小的key,记作min(A)从A中删除，然后将min(A)replace他们父节点的第i个key，然后将第i个key转移到w
     *     同时w新增的子节点是A的第一个子节点。现在明确了w和A的key和子树的更新关系。更新w和A的key和子树即可
     *     
     *     没有右兄弟节点，查看左兄弟节点B，并且B的key数量>t-1,将B中最大的key,记作max(B)从B中删除，然后将max(B)replace他们父节点的第i-1个key，然后将第i-1个key转移到w
     *     同时w新增的子节点是B的最后一个子节点。现在明确了w和B的key和子树的更新关系。更新w和B的key和子树即可
     *   b.如果w的相邻的兄弟节点的key数量都是t-1，那么合并两个兄弟节点，并且将对应的x中第i个key从父节点下移到merge的中间节点。
     *   重新进入算法,参数:节点w,需要删除的k
     * 
     * 
     * 这个算法的基本思想就是在进入到下一级的子节点时候，保证子节点的key数量至少是t,
     * 这是为了在删除key的时候，保证每个node删除节点后，或者节点中的一个key下移后，节点key的数量依然不小于t-1
     * @param tree
     * @param k
     * @see [类、类#方法、类#成员]
     */
    public void deleteFromBtree(BTreeNode node, int k)
    {
        //case1:包含k，并且node是叶节点
        if (node.contains(k) != -1 && node.isLeaf())
        {
            //直接删除k
            int index = node.contains(k);
            node.deleteKForLeaf(index, k);
            return;
        }
        
        //case2：
        int indexOfK = node.contains(k);
        if (indexOfK != -1 && !node.isLeaf())
        {
            //k后继节点
            BTreeNode successor = node.getSubTreeNodeList()[indexOfK + 1];
            
            //k前继节点
            BTreeNode preNode = node.getSubTreeNodeList()[indexOfK];
            if (!preNode.isSizeMin())
            {
                int maxKey = preNode.getKeyList()[preNode.getSize() - 1];
                node.replace(indexOfK, maxKey);
                preNode.replace(preNode.getSize()-1, k);
                
            }else if(!successor.isSizeMin()){
                int minKey = successor.getKeyList()[0];
                node.replace(indexOfK, minKey);
                successor.replace(0, k);
            }else {
                merge(preNode, successor, k);
                //父节点删除key，删除子树
                node.delete(indexOfK, 1);
            }
            indexOfK = node.contains(k);
            BTreeNode newSubNode = node.getSubTreeNodeList()[indexOfK];
            deleteFromBtree(newSubNode, k);
            
        }
        
       
        //获取下一级搜索的节点
        int subNodeIndex = node.next(k);
        BTreeNode[] subNodesList = node.getSubTreeNodeList();
        BTreeNode subNode = subNodesList[subNodeIndex];
        if (subNode.isSizeMin())
        {
            //进入case3
            
            //一定有左兄弟
            if (subNodeIndex > 0)
            {
                /**
                 * 最后一个子树,所有没有右兄弟节点
                 * 获取左兄弟节点
                 */
                
                int[] nodeKeys = node.getKeyList();
                BTreeNode leftNode = subNodesList[subNodeIndex - 1];
                if (!leftNode.isSizeMin())
                {
                    int leftNodeMaxKey = leftNode.getKeyList()[leftNode.getSize() - 1];
                    BTreeNode leftNodeSubNode = leftNode.getSubTreeNodeList()[leftNode.getSize()];
                    int key = nodeKeys[subNodeIndex];
                    
                    //将左节点的最大key上移到父节点
                    node.getKeyList()[subNodeIndex] = leftNodeMaxKey;
                    
                    //node节点的key下移到subNode节点
                    subNode.add(0, key, leftNodeSubNode, 0);
                    
                    //左兄弟节点删除移动的key和移动的子树
                    leftNode.delete(leftNode.getSize(), 1);
                    
                }
                else
                {
                    merge(leftNode, subNode, nodeKeys[subNodeIndex]);
                    //父节点删除key，删除子树
                    node.delete(subNodeIndex, 1);
                }
                
            }
            else if (subNodeIndex == 0)
            {
                //只有有右兄弟节点
                
                BTreeNode[] nodeSubNodes = node.getSubTreeNodeList();
                int[] nodeKeys = node.getKeyList();
                BTreeNode rightNode = nodeSubNodes[subNodeIndex + 1];
                if (!rightNode.isSizeMin())
                {
                    int rightNodeMinKey = rightNode.getKeyList()[rightNode.getSize() - 1];
                    BTreeNode rightNodeSubNode = rightNode.getSubTreeNodeList()[rightNode.getSize()];
                    int key = nodeKeys[subNodeIndex];
                    
                    //将右节点的最小key上移到父节点
                    node.getKeyList()[subNodeIndex] = rightNodeMinKey;
                    
                    //node节点的key下移到subNode节点
                    subNode.add(subNode.getSize(), key, rightNodeSubNode, 1);
                    
                    //右兄弟节点删除移动的key和移动的子树
                    rightNode.delete(0, 0);
                    
                }
                else
                {
                    //两个兄弟节点进行merge,右兄弟merge到左兄弟
                    merge(subNode, rightNode, nodeKeys[subNodeIndex]);
                    //父节点删除key，删除子树
                    node.delete(subNodeIndex, 1);
                }
            }
            int newIndex = node.next(k);
            
            deleteFromBtree(node.getSubTreeNodeList()[newIndex], k);
        }
        
    }
    
    /**
     * 将右节点合并到左
     * 并且在新的节点insert key
     * <功能详细描述>
     * @param lefNode
     * @param right
     * @param k
     * @see [类、类#方法、类#成员]
     */
    private void merge(BTreeNode leftNode,BTreeNode rightNode,int k){
        int t = leftNode.getT();
        int[] mergekeyList = new int[2 * t - 1];
        int[] leftKeys = leftNode.getKeyList();
        int[] rightKeys = rightNode.getKeyList();
        //key合并
        for (int i = 0; i < mergekeyList.length; i++)
        {
            if (i < t - 1)
            {
                mergekeyList[i] = leftKeys[i];
            }
            else if (i == t - 1)
            {
                mergekeyList[i] = k;
            }
            else
            {
                mergekeyList[i] = rightKeys[i - t + 2];
            }
            
        }
        
        //子树合并
        BTreeNode[] mergeBTreeNodes = new BTreeNode[2*t];
        BTreeNode[] leftSubNodes = leftNode.getSubTreeNodeList();
        BTreeNode[] rightSubNode = rightNode.getSubTreeNodeList();
        for (int i = 0; i < mergeBTreeNodes.length; i++)
        {
            if (i < t)
            {
                mergeBTreeNodes[i] = leftSubNodes[i];
            }else
            {
                mergeBTreeNodes[i] = rightSubNode[i - t];
            }
            
        }
        
        leftNode.setKeyList(mergekeyList);
        leftNode.setSubTreeNodeList(mergeBTreeNodes);
        
    }
}

/*
 * 文 件 名:  BTreeNode.java
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.tree;

/**
 * B树节点数据结构定义
 * B树性质:
 * 1.除了根节点，其他节点中key的数量size,满足条件 t-1<=size<=2t-1
 * 2.节点内部key是按照顺序存储
 * 3.子节点的key必须在父节点key的范围之内
 * B树节点需要保存的数据:
 * 1.所有的key
 * 2.key的数量
 * 3.子树节点
 * 4.子树节点与key的关系
 * 5.是否叶节点
 * @author  root
 * @version  [版本号, 2014-10-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BTreeNode
{
    //是否是叶节点
    private boolean isLeaf;
    
    //key的数量
    private volatile int size;
    
    private int t;
    
    /**
     * 因为B树在进行插入或者删除操作时
     * 内部节点的key经常进行节点拆分，或者进行节点合并
     * 所以保存key的数据结构最好是用爽端链表
     * 首尾操作比较方便
     */
    private int[] keyList;
    
    //LinkedList<BTreeKey> keyList = new LinkedList<BTreeKey>();
    /**
     * 子节点的保存
     * 通常情况下，进行节点插入的时候，对full节点进行拆分时，伴随着key的迁移，对应的子树也会被迁移
     * 
     */
    private BTreeNode[] subTreeNodeList;
    
    private BTreeNode parentNode;
    
    public BTreeNode(int t)
    {
        this.isLeaf = true;
        size = 0;
        this.keyList = new int[this.t - 1];
        this.t = t;
        this.subTreeNodeList = new BTreeNode[this.t - 1];
    }
    
    public boolean isLeaf()
    {
        return isLeaf;
    }
    
    public void setLeaf(boolean isLeaf)
    {
        this.isLeaf = isLeaf;
    }
    
    public int getSize()
    {
        return size;
    }
    
    public boolean isFull()
    {
        return size == 2 * t - 1;
    }
    
    public int getT()
    {
        return this.t;
    }
    
    public BTreeNode[] getSubTreeNodeList()
    {
        return subTreeNodeList;
    }
    
    public void setSubTreeNodeList(BTreeNode[] subTreeNodeList)
    {
        this.subTreeNodeList = subTreeNodeList;
    }
    
    public int[] getKeyList()
    {
        return keyList;
    }
    
    public void setKeyList(int[] keyList)
    {
        this.keyList = keyList;
        this.size = keyList.length;
    }
    
    public BTreeNode getParentNode()
    {
        return parentNode;
    }
    
    public void setParentNode(BTreeNode parentNode)
    {
        this.parentNode = parentNode;
    }
    
    /**
     * 在一个节点中寻找key，找打则返回非负数
     * 没找到，如果是叶节点，返回-1
     * 如果是内部节点，继续寻找子节点
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public SearchResult searchKey(int key)
    {
        //节点不存在任何key
        if (size == 0)
        {
            return null;
        }
        int index = 0;
        for (index = 0; index < size; index++)
        {
            int value = keyList[index];
            if (value >= key)
            {
                break;
            }
        }
        int current = keyList[index];
        if (current == key)
        {
            return new SearchResult(this, index);
        }
        
        //叶节点没有子树，没有找到
        if (isLeaf)
        {
            return null;
        }
        
        return subTreeNodeList[index].searchKey(key);
    }
    
    class SearchResult
    {
        private BTreeNode node;
        
        private int index;
        
        public SearchResult(BTreeNode node, int index)
        {
            this.node = node;
            this.index = index;
        }
        
        public BTreeNode getNode()
        {
            return node;
        }
        
        public int getIndex()
        {
            return index;
        }
        
    }
    
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param parent 父节点
     * @param index  parent的第index个子节点是 这个节点
     * @param toChild 新创建的节点
     * @return
     * @see [类、类#方法、类#成员]
     */
    public void splitBTreeKey(BTreeNode parent, int index, BTreeNode toChild)
    {
        int middleKey = keyList[t - 1];
        
        int[] moveBTreeKey = new int[t - 1];
        
        for (int i = t; i < keyList.length; i++)
        {
            moveBTreeKey[i - t] = keyList[i];
            keyList[i] = Integer.MAX_VALUE;
        }
        //迁移key,设置size
        toChild.setKeyList(moveBTreeKey);
        BTreeNode[] toChildSubNode = new BTreeNode[t];
        if (!isLeaf)
        {
            //移动子节点
            for (int i = 0; i < t; i++)
            {
                BTreeNode subNode = subTreeNodeList[i + t];
                toChildSubNode[i] = subNode;
                subTreeNodeList[i + t] = null;
            }
            toChild.setSubTreeNodeList(toChildSubNode);
        }
        this.size = t - 1;
        
        //父节点调整，将中间key middleKey提升到父节点
        int[] newKeyList = new int[parent.getSize() + 1];
        
        int[] parentKeys = parent.getKeyList();
        for (int i = 0; i < parentKeys.length; i++)
        {
            if (i < index)
            {
                newKeyList[i] = parentKeys[i];
            }
            else if (i >= index)
            {
                newKeyList[i + 1] = parentKeys[i];
            }
        }
        newKeyList[index] = middleKey;
        BTreeNode[] newSuBTreeNode = new BTreeNode[size + 1];
        BTreeNode[] parentNodes = parent.getSubTreeNodeList();
        for (int i = 0; i < parentNodes.length; i++)
        {
            if (i <= index)
            {
                newSuBTreeNode[i] = parentNodes[i];
            }
            else if (i > index)
            {
                newSuBTreeNode[i + 1] = parentNodes[i];
            }
        }
        newSuBTreeNode[index + 1] = toChild;
        
        parent.setSubTreeNodeList(newSuBTreeNode);
        parent.setKeyList(newKeyList);
    }
    
    public boolean isSizeMin()
    {
        return size == t - 1;
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
     *   c.如果k的前继，后继子树的key数量都是t-1，那么将y,与z进行merge,父节点删除k
     *     如果x中的原key数量=t-1，在z合并到y后，将merge后最大的key代替x中的k。
     * 3.如果x中不包含k,那么继续在子树中寻找，假设y肯定在这个B-tree中。
     *   假设我们在w节点中找到了k,并且w是叶节点,且w的key数量=t-1(如果不是叶节点,那么就可以按照case2进行处理，如果是leaf,并且key num>t-1,按照case1处理)
     *   a.如果w的相邻的右兄弟节点A的key数量>t-1,将指向这个子树相关位置的key从w的父节点p(w)移动到w中，然后从A中将最小的key替换p(w)中下移的key。
     *     没有右兄弟节点，查看左兄弟节点，相关操作类似
     *   b.如果w的相邻的右兄弟节点的key数量=t-1，那么合并两个兄弟节点，并且将对应的key从父节点下移到merge的中间节点。如果父节点数量此刻小于t-1,那么将merge后的
     *     节点的最大key放到相关位置即可  
     */
    public void delete(int index)
    {
        //删除节点中的index位置的key
        for (int i = index; i < size-1; i++)
        {
            keyList[index]=keyList[i+1];
        }
        keyList[size-1]=Integer.MAX_VALUE;
        //删除key，对应的右子树的指针删除
        for (int i = index+1; i < size-1; i++)
        {
            subTreeNodeList[i]=subTreeNodeList[i+1];
        }
    }
    
    public void deleteKForLeaf(int index, int k)
    {
        if (isLeaf && index > -1)
        {
            for (int i = index; i < size; i++)
            {
                keyList[i] = keyList[i + 1];
            }
            keyList[size - 1] = Integer.MAX_VALUE;
            size--;
        }
    }
    
    public void replace(int index, int desKey)
    {
        if (index > -1 && index < size)
        {
            if (index == 0 && keyList[index + 1] > desKey)
            {
                keyList[index] = desKey;
            }
            else if (index == size - 1 && keyList[index - 1] < desKey)
            {
                keyList[index] = desKey;
                
            }
            else if (index > 0 && index < size - 1 && keyList[index + 1] > desKey && keyList[index - 1] < desKey)
            {
                keyList[index] = desKey;
            }
            
        }
    }
    
}

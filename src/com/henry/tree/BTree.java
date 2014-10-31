/*
 * 文 件 名:  BTree.java
 * 描    述:  B树数据结构算法实现
 * 修 改 人:  root
 * 修改时间:  2014-10-30
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.tree;

/**
 * 
 * <功能详细描述>
 * 
 * @author  root
 * @version  [版本号, 2014-10-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BTree
{
    //树的根节点
    private BTreeNode root;
    
    private int t;
    
    public BTree(int t){
        this.t=t;
        BTreeNode x = new BTreeNode(this.t);
        this.root=x;
    }

    public BTreeNode getRoot()
    {
        return root;
    }

    public void setRoot(BTreeNode root)
    {
        this.root = root;
    }

    public int getT()
    {
        return t;
    }

    
    
    
}

/*
 * 文 件 名:  Node.java
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-29
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.tree;


/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  root
 * @version  [版本号, 2014-10-29]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Node
{
    int key;
    private Node left;
    private Node right;
    private Node Parent;
    //红黑树所需要
    private String color;
    public int getKey()
    {
        return key;
    }
    public void setKey(int key)
    {
        this.key = key;
    }
    public Node getLeft()
    {
        return left;
    }
    public void setLeft(Node left)
    {
        this.left = left;
    }
    public Node getRight()
    {
        return right;
    }
    public void setRight(Node right)
    {
        this.right = right;
    }
    public Node getParent()
    {
        return Parent;
    }
    public void setParent(Node parent)
    {
        Parent = parent;
    }
    public String getColor()
    {
        return color;
    }
    public void setColor(String color)
    {
        this.color = color;
    }
    
    
}

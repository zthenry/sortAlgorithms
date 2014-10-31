/*
 * 文 件 名:  BTreeKey.java
 * 描    述:  B-Tree key结构
 * 修 改 人:  root
 * 修改时间:  2014-10-30
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
 * @version  [版本号, 2014-10-30]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BTreeKey
{
    //key值
    private final int key;
    
    //下一个key
    private BTreeKey next;
    
    //上一个key
    private BTreeKey previous;

    public BTreeKey(int key){
        this.key=key;
        this.next=null;
        this.previous=null;
    }
    
    public int getKey()
    {
        return key;
    }

   
    public BTreeKey getNext()
    {
        return next;
    }

    public void setNext(BTreeKey next)
    {
        this.next = next;
    }

    public BTreeKey getPrevious()
    {
        return previous;
    }

    public void setPrevious(BTreeKey previous)
    {
        this.previous = previous;
    }
    
    
}

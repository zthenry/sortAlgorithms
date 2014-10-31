/*
 * 文 件 名:  SortTest.java
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.sort;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  root
 * @version  [版本号, 2014-10-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SortTest
{
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param args
     * @see [类、类#方法、类#成员]
     */
    public static void main(String[] args)
    {
        int[] a = new int[]{1,4,3,9,5,7,10,30,20,11};
        //InsertionSort sort = new InsertionSort();
        //BubbleSort sort = new BubbleSort();
//        HeapSort sort = new HeapSort();
        QuickSort sort = new QuickSort();
        sort.sort(a);
        for (int i = 0; i < a.length; i++)
        {
            System.out.println(a[i]);
            
        }
        
    }
    
}

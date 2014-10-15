/*
 * 文 件 名:  QuickSort.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
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
public class QuickSort
{
    
    
    public int partition(int[] a,int begin,int end){
        int x = a[end];
        int i = begin-1;
        for (int j = begin; j < end; j++)
        {
            if (a[j]<=x)
            {
                i++;
                swap(a, i, j);
            }
        }
        i++;
        swap(a, i, end);
        return i;
    }
    
    
    public void quickSort(int[] a, int begin,int end){
        
        if (begin<end)
        {
            int index = partition(a, begin, end);
            quickSort(a, begin, index-1);
            quickSort(a, index+1, end);   
        }
    }
    private void swap(int[] a ,int indexFrom,int indexTo){
        int temp = a[indexFrom];
        a[indexFrom]=a[indexTo];
        a[indexTo]=temp;
    }
    
    public void sort(int[] a){
        quickSort(a, 0, a.length-1);
    }
}

/*
 * 文 件 名:  SelectionProblem.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-18
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.sort;


/**
 * 求第i小个数
 * 求第i大数就等同于求第n-i小数
 * 
 * @author  root
 * @version  [版本号, 2014-10-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SelectionProblem
{
    public int findiThMin(int[] a,int i){
        return randomizedSelect(a, 0, a.length-1, i);
    }
    
    public int randomizedSelect(int[] a,int begin,int end ,int i){
        if (begin==end)
        {
            return a[begin];
        }
        int q = partition(a, begin, end);
        int k = q-begin+1;
        if (i==k)
        {
            return a[q];
        }else if (i<k) {
            return randomizedSelect(a, begin, q-1, i);
        }else {
            return randomizedSelect(a, q+1, end, i-k);
        }
        
    }
    
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
    
    private void swap(int[] a ,int indexFrom,int indexTo){
        int temp = a[indexFrom];
        a[indexFrom]=a[indexTo];
        a[indexTo]=temp;
    }
}

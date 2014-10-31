/*
 * 文 件 名:  CountingSort.java
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-17
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.sort;

/**
 * 计数排序:间接寻址，实现排序
 * 时间复杂度O(n)
 * 这种算法适用于那些确定了待排序数组元素的取值范围
 * 需要额外的内存空间
 * @author  root
 * @version  [版本号, 2014-10-17]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CountingSort
{
    public int[] countingSort(int[] a,int k){
        int[] c = new int[k];
        int[] b = new int[a.length];
        for (int i = 0; i < k; i++)
        {
            c[i]=0;
        }
        for (int i = 0; i < a.length; i++)
        {
            c[i]=c[a[i]]+1;
        }
        for (int i = 1; i < k; i++)
        {
            c[i]=c[i]+c[i-1];
        }
        for (int j = 0; j < a.length; j++)
        {
            b[c[a[j]]] = a[j];
            c[a[j]]=c[a[j]]-1;
        }
        return b;
    }
}

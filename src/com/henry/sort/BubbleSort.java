/*
 * 文 件 名:  BubbleSort.java
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
 * 冒泡排序
 * O(n^2)
 * 
 * @author  root
 * @version  [版本号, 2014-10-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class BubbleSort
{
    public void sort(int[] a){
        if (a==null || a.length==0)
        {
            return;
        }
        int length = a.length;
        for (int i = 0; i < length; i++)
        {
            for (int j = length-1; j>=i+1; j--)
            {
                if (a[j]<a[j-1])
                {
                    int temp = a[j];
                    a[j]=a[j-1];
                    a[j-1]=temp;
                }
            }
        }
    }
}

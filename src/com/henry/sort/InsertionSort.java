/*
 * 文 件 名:  InsertionSort.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-14
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
 * @version  [版本号, 2014-10-14]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class InsertionSort
{
    
    /**
     * 插入排序
     * 时间复杂度 O(n^2)
     * @param unsortedArray
     * @return
     * @see [类、类#方法、类#成员]
     */
    public void sort(int[] unsortedArray){
        if (unsortedArray==null || unsortedArray.length<2)
        {
            return;
        }else {
            int length = unsortedArray.length;
            for (int i = 1; i < length; i++)
            {
                int key = unsortedArray[i];
                for (int j = i-1; j>=0; j--)
                {
                    
                    if (unsortedArray[j]>key)
                    {
                        unsortedArray[j+1]=unsortedArray[j];
                    }else {
                        unsortedArray[j+1]=key;
                        break;
                    }
                    
                }
            }
        }
       
    }
    
    public static void main(String[] args)
    {
        
    }
}

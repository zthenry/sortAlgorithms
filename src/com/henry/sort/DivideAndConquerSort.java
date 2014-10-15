/*
 * 文 件 名:  DivideAndConquerSort.java
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
public class DivideAndConquerSort
{
    /**
     * merge两个已排序的的数组
     * <功能详细描述>
     * @param a
     * @param start
     * @param middle
     * @param end
     * @return
     * @see [类、类#方法、类#成员]
     */
    public int[] merge(int[] a,int start, int middle,int end){
        int length1 = middle-start+1;
        int length2 = end-middle;
        int[] left = new int[length1+1];
        int[] right = new int[length2+1];
        for (int i = 0; i < left.length-1; i++)
        {
            left[i]=a[start+i];
        }
        left[left.length-1]=Integer.MAX_VALUE;
        for (int i = 0; i < right.length-1; i++)
        {
            right[i]=a[middle+i+1];
        }
        right[right.length-1]=Integer.MAX_VALUE;
        
        int leftIndex=0;
        int rightIndex=0;
        for (int i = start; i < end+1; i++)
        {
            
            if (left[leftIndex]<=right[rightIndex])
            {
                if(left[leftIndex]!=Integer.MAX_VALUE){
                    a[i]=left[leftIndex];
                    leftIndex++;
                }
                
            }else {
                if(right[rightIndex]!=Integer.MAX_VALUE){
                    a[i]=right[rightIndex];
                    rightIndex++;
                }
                
            }
            
        }
        return a;
    }
    
    public void mergeSort(int[] a, int start, int end){
        if(start<end){
            int middle = (start+end)/2;
            mergeSort(a, start, middle);
            mergeSort(a, middle+1, end);
            merge(a, start, middle, end);
        }
    }
    
    public static void main(String[] args)
    {
        int[] a = new int[]{1,4,3,9,5,7,10,30,20,11};
        DivideAndConquerSort ds = new DivideAndConquerSort();
        ds.mergeSort(a, 0, a.length-1);
        for (int i = 0; i < a.length; i++)
        {
            System.out.println(a[i]);
        }
    }
}

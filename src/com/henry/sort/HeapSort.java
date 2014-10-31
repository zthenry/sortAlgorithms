/*
 * 文 件 名:  HeapSort.java
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-15
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.henry.sort;

/**
 * heap-sort
 * <功能详细描述>
 * 
 * @author  root
 * @version  [版本号, 2014-10-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class HeapSort
{
    
    
    /**
     * <一句话功能简述>
     * O(lgN)
     * @param a
     * @param index
     * @see [类、类#方法、类#成员]
     */
    public void maxheapify(int[] a,int index,int heapSize){
        
        int leftIndex = 2*index;
        int rightIndex = 2*index+1;
        int largest = index;
        if (leftIndex<=heapSize && a[leftIndex-1]>a[index-1])
        {
            largest=leftIndex;
        }
        if (rightIndex<heapSize && a[rightIndex-1]>a[largest-1])
        {
            largest=rightIndex;
        }
        
        if (largest!=index)
        {
            int temp = a[index-1];
            a[index-1]=a[largest-1];
            a[largest-1]=temp;
            maxheapify(a, largest,heapSize);
        }
    }
    
    /**
     * O(n)
     * <功能详细描述>
     * @param a
     * @see [类、类#方法、类#成员]
     */
    public void buildMaxHeap(int[] a){
        int begin = (a.length)/2;
        for (int i = begin; i>0; i--)
        {
            maxheapify(a, i,a.length);
        }
    }
    
    
    public void maxHeapSort(int[] a){
        buildMaxHeap(a);
        int length = a.length;
        int heapSize = length;
        /**
         * n*O(lgN)=O(n*lgN)
         */
        for (int i = length; i >0; i--)
        {
            swap(a, i-1, 0);
            heapSize=i-1;
            maxheapify(a, 1,heapSize);
        }
    }
    
    private void swap(int[] a ,int indexFrom,int indexTo){
        int temp = a[indexFrom];
        a[indexFrom]=a[indexTo];
        a[indexTo]=temp;
    }
    
    public void sort(int[] a){
        maxHeapSort(a);
    }
}

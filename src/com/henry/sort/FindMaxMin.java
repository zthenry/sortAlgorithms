package com.henry.sort;

/**
 * 
 * 查找最大最小数
 * 用最短的时间的算法找出这两个数(3n/2)
 * 
 * @author  root
 * @version  [版本号, 2014-10-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FindMaxMin
{
    public int[] findMaxMin(int[] a)
    {
        int max = 0;
        int min = 0;
        for (int i = 0; i < a.length-1; i=i+2)
        {
            int index = 0;
            if (i+1<a.length)
            {
                index = a[i]>a[i+1]?i:i+1;
                if (index%2==0)
                {
                    min=min<=a[index-1]?min:a[index-1];
                }else {
                    min=min<=a[index+1]?min:a[index+1];
                }
            }
            else {
                index = i;
                min=min<=a[index]?min:a[index];
            }
            max=max>=a[index]?max:a[index];
            
            
        }
        return new int[]{min,max};
    }
}

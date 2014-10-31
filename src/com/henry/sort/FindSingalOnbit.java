/*
 * 文 件 名:  FindSingalOnbit.java
 * 描    述:  <描述>
 * 修 改 人:  root
 * 修改时间:  2014-10-16
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
 * @version  [版本号, 2014-10-16]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class FindSingalOnbit
{
    /**
     * 一个整型数组，数组中每个元素出现2次，但是有一个元素出现一次，找出该数据
     * 扩展:只有出现偶数次都都可以这么计算
     * @param a
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int findSingalNum(int[] a){
        int total = 0;
        for (int i = 0; i < a.length; i++)
        {
            total ^=a[i];
        }
        return total;
    }
    
    
    /**
     * 一个整型数组，数组中每个元素出现3次，但是有一个元素出现一次，找出该数据
     * 扩展:出现奇数次应该也可以这么算，得重新设计一下
     * @param a
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int findSingalNumForOddTimes(int[] a,int repeatTimes){
        int result=0;
        for (int i = 0; i < 32; i++)
        {
            int c=0;
            int mask=1<<i;
            for (int j = 0; j < a.length; j++)
            {
                int value = a[j]&mask;
                if (value!=0)
                {
                    c++;
                }
            }
            
            if(c%repeatTimes>0){
                result = result | mask;
            }
        }
        return result;
    }
    public static void main(String[] args)
    {
        int[] a=new int[]{1,1,2,2,5,6,8,5,6,9,9};
        int result = findSingalNum(a);
        int[] b=new int[]{1,1,2,2,5,6,8,5,6,9,9,1,2,6,5,9};
        int result2 = findSingalNumForOddTimes(b,3);
        System.out.println(result);
        System.out.println(result2);
        
        int[] c=new int[]{1,1,2,2,5,6,8,5,6,9,9,1,2,6,5,9,2,1,5,6,9,1,2,5,6,9};
        
        int result5 = findSingalNumForOddTimes(c,5);
        System.out.println(result5);
    }
}

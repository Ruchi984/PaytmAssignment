package com;

public class first100PrimeNumbers {

	public  static void main(String[] args) {
		// TODO Auto-generated method stub

		int count=0,number=0,i=1,j=1;
		
		System.out.println("First 100 Prime Numbers are:");
		while(number<100)
		{
			j=1;
			count=0;
			while(j<=i)
			{
				if(i%j==0)
				{
					count++;
				}
			j++;
			}
			
			if(count==2)
			{
				System.out.println(i);
				number++;
			}
			
		i++;	
		}	
	}

}

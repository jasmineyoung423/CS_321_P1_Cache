import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**Test is a test class for Cache.java object. It takes in <1|2> <size of first cache> <OPTIONAL: size of second cache> <file>
 * @author Jasmine Young*/

public class Test
{
	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		Test test = new Test(args); // to avoid making all methods static
		
	}
	
	/**Constructor
	 * @param String[] args (passed from main)*/
	public Test(String[] args)
	{
		if(args.length == 3)
		{
			if(!args[0].equals("1"))
			{
				System.out.println("Please specify 1 or 2 level cache as first argument. See README for usage instructions");
			}
			else
			{
				oneLevelCache(args);
			}
		}
		else if (args.length == 4)
		{
			if(!args[0].equals("2"))
			{
				System.out.println("Please specify 1 or 2 level cache as first argument. See README for usage instructions");
			}
			else
			{
				twoLevelCache(args);
			}
		}
		else
		{
			System.out.println("Invalid number of arguments. See README for usage instructions");
		}
	}
	
	/**oneLevelCache tests a one level implementation of cache and prints the results
	 * @param String[] args (passed from constructor)*/
	private void oneLevelCache(String[] args)
	{
		try
		{
			Cache<String> cache= new Cache<String>(Integer.parseInt(args[1]));
			System.out.println(String.format("First level cache with entries has been created",args[1]));
			File file = new File(args[2]);
			Scanner scan = new Scanner(file);
			String line;
			double numRefs = 0;
			double numHits = 0;
			double hitRatio = 0;
			int count = 0;
			while(scan.hasNextLine())
			{
				line = scan.nextLine();
				StringTokenizer strTkn = new StringTokenizer(line);
				while(strTkn.hasMoreTokens())
				{
					String word = strTkn.nextToken();
					if(cache.getObject(word) == null)
					{
						cache.addObject(word);
					}
					else
					{
						numHits++;
					}
					numRefs++;
					count++;
				}
				
				if(count == 100000)
				{
					System.out.println(numRefs + " words processed...");
					count = 0;
				}
			}
			hitRatio = numHits/numRefs;
			System.out.println(String.format("The number of global references: %s",(numRefs)));
			System.out.println(String.format("The number of global cache hits: %s",(numHits+numHits)));
			System.out.println(String.format("The global hit ratio: %s",(hitRatio)));
			scan.close();
		}
		catch(NumberFormatException nfe)
		{
			System.out.println("Please enter integer digits for cache sizes. See README for usage instructions.");
		}
		catch(IOException ioe)
		{
			System.out.println("Error reading file.");
		}
	}
	
	/**oneLevelCache tests a two level implementation of cache and prints the results
	 * @param String[] args (passed from constructor)*/
	private void twoLevelCache(String[] args)
	{
		Scanner scan = new Scanner("");
		try
		{
			int firstSize = Integer.parseInt(args[1]);
			int secondSize =  Integer.parseInt(args[2]);
			if(firstSize >= secondSize)
			{
				System.out.println("First Level cache size must be less than second level cache size. See README for usage instructions.");
			}
			else
			{
				Cache<String> firstCache = new Cache<String>(firstSize);
				Cache<String> secondCache = new Cache<String>(firstSize,secondSize);
				System.out.println(String.format("First level cache with %s entries has been created",firstSize));
				System.out.println(String.format("Second level cache with %s entries has been created",secondSize));
				File file = new File(args[3]);
				scan.close();
				scan = new Scanner(file);
				String line;
				double num1Refs = 0;
				double num2Refs = 0;
				double num1Hits = 0;
				double num2Hits = 0;
				double hitRatio = 0;
				int count = 0;
				while(scan.hasNextLine())
				{
					line = scan.nextLine();
					StringTokenizer strTkn = new StringTokenizer(line);
					while(strTkn.hasMoreTokens())
					{
						String word = strTkn.nextToken();
						String temp = firstCache.getObject(word);
						if(temp == null)
						{
							temp = secondCache.getObject(word);
							if(temp != null)
							{
								num2Hits++;
							}
							num2Refs++;
						}
						else
						{
							secondCache.getObject(word);
							num1Hits++;
						}
						num1Refs++;
						count++;
					}
					
					if(count == 100000)
					{
						System.out.println(num1Refs + " words processed...");
						count = 0;
					}
				}
				hitRatio = (num1Hits+num2Hits)/num1Refs;
				System.out.println(String.format("The number of global references: %s",(num1Refs)));
				System.out.println(String.format("The number of global cache hits: %s",(num1Hits+num2Hits)));
				System.out.println(String.format("The global hit ratio: %s",(hitRatio)));
				System.out.println(String.format("\nThe number of 1st-level references: %s",num1Refs));
				System.out.println(String.format("The number of 1st-level hits: %s",num1Hits));
				System.out.println(String.format("The 1st-level cache hit ratio: %s",(num1Hits/num1Refs)));
				System.out.println(String.format("\nThe number of 2nd-level references: %s",num2Refs));
				System.out.println(String.format("The number of 2nd-level hits: %s",num2Hits));
				System.out.println(String.format("The 2nd-level cache hit ratio: %s",(num2Hits/num2Refs)));				
			}
		}
		catch(NumberFormatException nfe)
		{
			scan.close();
			System.out.println("Please enter integer digits for cache sizes. See README for usage instructions.");
		}
		catch(IOException ioe)
		{
			scan.close();
			System.out.println("Error reading file.");
		}
	}
}

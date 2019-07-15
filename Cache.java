import java.util.LinkedList;

/**Cache is a Linked-List implementation of a cache. Can be one or two levels.
 * @author Jasmine Young*/

public class Cache<T>
{
	private int firstSize; // size of the first cache in 2 cache structure
	private LinkedList<T> memory; // holds what's in the cache
	private int secondSize; // size of the entire second cache in 2 cache structure, or just size in 1 cache structure
	private boolean is2Level; // true if cache implementation is level 2 cache
	private int cache1Hit; // number of hits in level 1 cache
	private int cache2Hit; // number of hits in level 2 cache (if at all)
	private int cache1Refs; // number of references to level 1 cache
	private int cache2Refs; // number of references to level 2 cache (if at all)
	
	/** Constructor for level 1 cache
	 * @param int size (size of cache)*/
	public Cache(int size)
	{
		this(0, size);
		is2Level = false;
	}
	
	/**Constructor for level 2 cache
	 * @param int firstSize (size of level 1 cache)
	 * @param int secondSize (size of level 2 cache)*/
	public Cache(int firstSize, int secondSize)
	{
		this.firstSize = firstSize;
		this.secondSize = secondSize;
		this.memory = new LinkedList<T>();
		is2Level = true;
	}
	
	/**addObject adds an object to the top of the cache (first position in the linked list)
	 * @param T element (element to be added)*/
	public void addObject(T element)
	{
		if(memory.size() > secondSize)
		{
			memory.removeLast();
		}
		memory.addFirst(element);
	}
	
	/**getObject takes in an object, checks to see if it is stored in the available cache, and at which level.
	 * if object is found it is moved to the top of the cache (first position in linked list)
	 * if object is not found it is added to the top of the cache
	 * @param T element (object to be found)
	 * @return T (same object from top of the cache)*/
	public T getObject(T element)
	{
		int index = memory.indexOf(element);
		if(index >= 0 && index < secondSize)
		{
			if(is2Level)
			{
				if(index < secondSize && index >= firstSize)
				{
					
					cache2Hit++;	
					cache2Refs++;
				}
				else
				{
					cache1Hit++;
				}
				
			}
			else
			{
				cache1Hit++;
			}
			cache1Refs++;
			removeObject(element);
			addObject(element);
			return memory.getFirst();
		}	
		else
		{
			addObject(element);
			cache1Refs++;
			cache2Refs++;
			return null;
		}
	}
	
	/**getStats returns an int array of (in order) the number of cache level 1 hits,
	 *  number of cache level 1 references,
	 *  number of cache level 2 hits,
	 *  number of cache level 2 references
	 *  @return int[]*/
	public int[] getStats()
	{
		int[] temp = new int[4];
		temp[0] = cache1Hit;
		temp[1] = cache1Refs;
		temp[2] = cache2Hit;
		temp[3] = cache2Refs;
		return temp;
	}
	
	/** removeObject removes the param given object from the linked list if in the list
	 * @param T element (element to be removed from the list)*/
	public void removeObject(T element)
	{
		if(memory.indexOf(element)!= -1)
		{
			memory.remove(element);
		}
	}
	
	/**clearCache removes everything from the cache*/
	public void clearCache()
	{
		for(T element : memory)
		{
			removeObject(element);
		}
	}
}

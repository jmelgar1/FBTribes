package org.thefruitbox.fbtribes.utilities;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GeneralUtilities {
	 public static <K, V> Map<K, V> sortByComparator(Map<K, V> unsortMap, final boolean order)
	    {

        List<Entry<K, V>> list = new LinkedList<Entry<K, V>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<K, V>>()
        {
            @SuppressWarnings("unchecked")
			public int compare(Entry<K, V> o1,
                    Entry<K, V> o2)
            {
                if (order)
                {
                    return ((Comparable<V>) ((Map.Entry<K, V>) (o1)).getValue()).compareTo(((Map.Entry<K, V>) (o2)).getValue());
                }
                else
                {
                    return ((Comparable<V>) ((Map.Entry<K, V>) (o2)).getValue()).compareTo(((Map.Entry<K, V>) (o1)).getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        for (Entry<K, V> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
   }
}

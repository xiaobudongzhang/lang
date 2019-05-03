package Jvm;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

/**
 *  An example of a canonicalizing map for strings, using a WeakHashMap to
 *  ensure that memory will be released when there are no other references
 *  in the program.
 */
public class WeakCanonicalizingMap {
    private Map<String,WeakReference<String>> _map = new WeakHashMap<String,WeakReference<String>>();
    /**
     *  Returns a canonical instance of the passed string: the first string
     *  interned by this map (which may be the passed string).
     */
    public synchronized String intern(String str)
    {
        WeakReference<String> ref = _map.get(str);
        String s2 = (ref != null) ? ref.get() : null;
        if (s2 != null)
            return s2;

        // as-of 1.5, still possible for a string to reference a much larger
        // shared buffer; creating a new string will trim the buffer
        str = new String(str);
        _map.put(str, new WeakReference<String>(str));
        return str;
    }
    /**
     *  Returns the number of entries in the map. This is useful for a demo,
     *  probably not so much in real life (except as a debugging tool).
     */
    public synchronized int size()
    {
        return _map.size();
    }
}

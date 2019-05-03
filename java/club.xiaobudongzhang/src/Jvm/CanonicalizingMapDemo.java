package Jvm;

import java.util.ArrayList;

/**
 *  Demonstrates a replacement for <code>String.intern()</code> that uses
 *  weak references to hold the interned strings. Once any original strong
 *  references are gone, and the GC runs, the interned strings will be
 *  discarded.
 *  <p>
 *  Also demonstrates some of the hoops that you have to jump through to
 *  test code that uses reference objects.
 */
public class CanonicalizingMapDemo
{
    public static void main(String[] argv) throws Exception
    {
        WeakCanonicalizingMap canon = new WeakCanonicalizingMap();

        // we have to create these strings from character arrays, because the
        // JVM would intern any string literals, keeping a strong reference
        String s1 = new String(new char[] {'a', 'b', 'c'});
        String s2 = new String(new char[] {'a', 'b', 'c'});

        System.out.println("pre-intern, map size = " + canon.size());
        System.out.println("pre-intern: s1 ?= s1: " + (s1 == s2));

        s1 = canon.intern(s1);
        s2 = canon.intern(s2);

        System.out.println("post-intern, map size = " + canon.size());
        System.out.println("post-intern: s1 ?= s2: " + (s1 == s2));

        s1 = s2 = null;
        tryToGC();

        System.out.println("post-GC, map size = " + canon.size());
    }


    /**
     *  A utility method that attempts to get the JVM to collect garbage.
     *  Not 100% guaranteed, but we're pretty close.
     */
    private static void tryToGC()
    {
        // a megabyte of memory is generally enough to fill the young generation
        ArrayList<byte[]> foo = new ArrayList<byte[]>();
        for (int ii = 0 ; ii < 1024 ; ii++)
            foo.add(new byte[1024]);

        // now we allow it to be GC'd, and ask the JVM to do so
        foo = null;
        System.gc();
    }
}

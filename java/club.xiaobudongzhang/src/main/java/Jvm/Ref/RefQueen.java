package Jvm.Ref;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.lang.ref.ReferenceQueue;
import java.util.Set;
import java.lang.ref.Reference;

public class RefQueen {
    public static void main(String[] argv) throws Exception
    {
        Set<WeakReference<byte[]>> refs = new HashSet<WeakReference<byte[]>>();
        ReferenceQueue<byte[]> queue = new ReferenceQueue<byte[]>();

        for (int ii = 0 ; ii < 1000 ; ii++)
        {
            WeakReference<byte[]> ref = new WeakReference<byte[]>(new byte[1000000], queue);
            System.err.println(ii + ": created " + ref);
            refs.add(ref);

            Reference<? extends byte[]> r2;
            while ((r2 = queue.poll()) != null)
            {
                System.err.println("cleared " + r2);
                refs.remove(r2);
            }
        }
    }
}

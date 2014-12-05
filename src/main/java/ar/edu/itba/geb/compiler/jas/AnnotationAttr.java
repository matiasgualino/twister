/**
 * This attribute can associated with a method, field or class.
 *
 * @author $Author: Iouri Kharon $
 * @version $Revision: 1.0 $
 */

package ar.edu.itba.geb.compiler.jas;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class AnnotationAttr
{
  CP attr;
  Vector anns;

  public AnnotationAttr(boolean visible)
  {
    attr = new AsciiCP(visible ? "RuntimeVisibleAnnotations" :
                                 "RuntimeInvisibleAnnotations");
    anns = new Vector();
  }

  public void add(Annotation annotation)
  { anns.add(annotation); }

  void resolve(ClassEnv e)
  {
    e.addCPItem(attr);
    for(Enumeration en = anns.elements(); en.hasMoreElements(); )
      ((Annotation)en.nextElement()).resolve(e);
  }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(e.getCPIndex(attr));
    int len = 2;
    for(Enumeration en = anns.elements(); en.hasMoreElements(); )
      len += ((Annotation)en.nextElement()).size();
    out.writeInt(len);
    out.writeShort((short)anns.size());
    for(Enumeration en = anns.elements(); en.hasMoreElements(); )
      ((Annotation)en.nextElement()).write(e, out);
  }
}

package ar.edu.itba.geb.compiler.jas;

import java.io.DataOutputStream;
import java.io.IOException;


/**
 * Wrap an Float constant reference with this CPE.
 *
 * @author $Author: jonmeyerny $
 * @version $Revision: 1.1 $
 */

public class FloatCP extends CP implements RuntimeConstants
{
  float val;

  /**
   * @param n Value for Float constant
   */
  public FloatCP(float n)
  {
    uniq = ("Float: @#$" + n).intern();
    val = n;
  }
  void resolve(ClassEnv e) { return; }
  void write(ClassEnv e, DataOutputStream out)
    throws IOException
  {
    out.writeByte(CONSTANT_FLOAT);
    out.writeFloat(val);
  }
}
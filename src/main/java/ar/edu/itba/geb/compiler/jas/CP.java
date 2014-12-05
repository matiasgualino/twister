/**
 * this is an abstraction to contain all the CPE items
 * that can be created.
 *
 * @see AsciiCP
 * @see ClassCP
 * @see ar.edu.itba.geb.compiler.jas.NameTypeCP
 * @see FieldCP
 * @see ar.edu.itba.geb.compiler.jas.InterfaceCP
 * @see MethodCP
 * @see IntegerCP
 * @see LongCP
 * @see FloatCP
 * @see ar.edu.itba.geb.compiler.jas.DoubleCP
 * @see StringCP
 *
 * @author $Author: jonmeyerny $
 * @version $Revision: 1.1 $
 */


package ar.edu.itba.geb.compiler.jas;
                                // one class to ring them all...

import java.io.DataOutputStream;
import java.io.IOException;

public abstract class CP
{
  String uniq;

  String getUniq() { return uniq; }

  abstract void resolve(ClassEnv e);

  abstract void write(ClassEnv e, DataOutputStream out)
   throws IOException, jasError;
}

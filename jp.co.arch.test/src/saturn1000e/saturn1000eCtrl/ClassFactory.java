package saturn1000e.saturn1000eCtrl  ;

import com4j.*;

/**
 * Defines methods to create COM objects
 */
public abstract class ClassFactory {
  private ClassFactory() {} // instanciation is not allowed


  /**
   * Saturn1000E Control
   */
  public static _DSaturn1000E createSaturn1000E() {
    return COM4J.createInstance( _DSaturn1000E.class, "{97D880D7-45B4-4EE5-BDD2-526DBF4F1D3B}" );
  }
}

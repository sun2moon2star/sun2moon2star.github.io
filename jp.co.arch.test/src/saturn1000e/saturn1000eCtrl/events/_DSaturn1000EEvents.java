package saturn1000e.saturn1000eCtrl.events;

import com4j.*;

/**
 * Saturn1000E Control のイベント インターフェイスです。
 */
@IID("{19C42465-0106-430A-9EF5-724A2C26C1CC}")
public abstract class _DSaturn1000EEvents {
  // Methods:
  /**
   * @param messageBody Mandatory com4j.Com4jObject parameter.
   * @param messageType Mandatory int parameter.
   */

  @DISPID(1)
  public void newMessage(
    com4j.Com4jObject messageBody,
    int messageType) {
        throw new UnsupportedOperationException();
  }


  // Properties:
}

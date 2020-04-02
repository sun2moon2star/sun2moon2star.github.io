package saturn1000e.saturn1000eCtrl  ;

import com4j.*;

/**
 * Saturn1000E Control のディスパッチ インターフェイスです。
 */
@IID("{00020400-0000-0000-C000-000000000046}")
public interface _DSaturn1000E extends Com4jObject {
  // Methods:
  /**
   * <p>
   * メソッド HealthCheck
   * </p>
   * @param messageInput Mandatory com4j.Com4jObject parameter.
   * @param messageOutput Mandatory Holder<com4j.Com4jObject> parameter.
   */

  @DISPID(1)
  int healthCheck(
    com4j.Com4jObject messageInput,
    Holder<com4j.Com4jObject> messageOutput);


  /**
   * <p>
   * メソッド GetArchIPAddress
   * </p>
   */

  @DISPID(2)
  java.lang.String getArchIPAddress();


  /**
   * <p>
   * メソッド SetArchIPAddress
   * </p>
   * @param archIPAddress Mandatory java.lang.String parameter.
   */

  @DISPID(3)
  void setArchIPAddress(
    java.lang.String archIPAddress);


  /**
   * <p>
   * メソッド GetArchPort
   * </p>
   */

  @DISPID(4)
  int getArchPort();


  /**
   * <p>
   * メソッド SetArchPort
   * </p>
   * @param archPort Mandatory int parameter.
   */

  @DISPID(5)
  void setArchPort(
    int archPort);


  /**
   * <p>
   * メソッド GetReceivePort
   * </p>
   */

  @DISPID(6)
  int getReceivePort();


  /**
   * <p>
   * メソッド SetReceivePort
   * </p>
   * @param receivePort Mandatory int parameter.
   */

  @DISPID(7)
  void setReceivePort(
    int receivePort);


  /**
   * <p>
   * メソッド GetConnectionTimeout
   * </p>
   */

  @DISPID(8)
  int getConnectionTimeout();


  /**
   * <p>
   * メソッド SetConnectionTimeout
   * </p>
   * @param connectionTimeout Mandatory int parameter.
   */

  @DISPID(9)
  void setConnectionTimeout(
    int connectionTimeout);


  /**
   * <p>
   * メソッド Abort
   * </p>
   */

  @DISPID(12)
  int abort();


  /**
   * <p>
   * メソッド GetSendTimeout
   * </p>
   */

  @DISPID(13)
  int getSendTimeout();


  /**
   * <p>
   * メソッド SetSendTimeout
   * </p>
   * @param sendTimeout Mandatory int parameter.
   */

  @DISPID(14)
  void setSendTimeout(
    int sendTimeout);


  /**
   * <p>
   * メソッド GetSummary
   * </p>
   * @param messageInput Mandatory com4j.Com4jObject parameter.
   * @param messageOutput Mandatory Holder<com4j.Com4jObject> parameter.
   */

  @DISPID(15)
  int getSummary(
    com4j.Com4jObject messageInput,
    Holder<com4j.Com4jObject> messageOutput);


  /**
   * <p>
   * メソッド RePrint
   * </p>
   * @param messageInput Mandatory com4j.Com4jObject parameter.
   * @param messageOutput Mandatory Holder<com4j.Com4jObject> parameter.
   */

  @DISPID(16)
  int rePrint(
    com4j.Com4jObject messageInput,
    Holder<com4j.Com4jObject> messageOutput);


  /**
   * <p>
   * メソッド CreditSales
   * </p>
   * @param messageInput Mandatory com4j.Com4jObject parameter.
   * @param messageOutput Mandatory Holder<com4j.Com4jObject> parameter.
   */

  @DISPID(17)
  int creditSales(
    com4j.Com4jObject messageInput,
    Holder<com4j.Com4jObject> messageOutput);


  /**
   * <p>
   * メソッド CreditCancel
   * </p>
   * @param messageInput Mandatory com4j.Com4jObject parameter.
   * @param messageOutput Mandatory Holder<com4j.Com4jObject> parameter.
   */

  @DISPID(18)
  int creditCancel(
    com4j.Com4jObject messageInput,
    Holder<com4j.Com4jObject> messageOutput);


  /**
   * <p>
   * メソッド CreditSummary
   * </p>
   * @param messageInput Mandatory com4j.Com4jObject parameter.
   * @param messageOutput Mandatory Holder<com4j.Com4jObject> parameter.
   */

  @DISPID(19)
  int creditSummary(
    com4j.Com4jObject messageInput,
    Holder<com4j.Com4jObject> messageOutput);


  /**
   * <p>
   * メソッド ReleaseMessageBody
   * </p>
   * @param message Mandatory com4j.Com4jObject parameter.
   * @param messageType Mandatory int parameter.
   */

  @DISPID(21)
  void releaseMessageBody(
    com4j.Com4jObject message,
    int messageType);


  /**
   * <p>
   * メソッド GetHealthCheckTimeout
   * </p>
   */

  @DISPID(22)
  int getHealthCheckTimeout();


  /**
   * <p>
   * メソッド GetGetSummaryTimeout
   * </p>
   */

  @DISPID(23)
  int getGetSummaryTimeout();


  /**
   * <p>
   * メソッド GetRePrintTimeout
   * </p>
   */

  @DISPID(24)
  int getRePrintTimeout();


  /**
   * <p>
   * メソッド GetCreditSalesTimeout
   * </p>
   */

  @DISPID(25)
  int getCreditSalesTimeout();


  /**
   * <p>
   * メソッド GetCreditCancelTimeout
   * </p>
   */

  @DISPID(26)
  int getCreditCancelTimeout();


  /**
   * <p>
   * メソッド GetCreditSummaryTimeout
   * </p>
   */

  @DISPID(27)
  int getCreditSummaryTimeout();


  /**
   * <p>
   * メソッド SetHealthCheckTimeout
   * </p>
   * @param healthCheckTimeout Mandatory int parameter.
   */

  @DISPID(28)
  void setHealthCheckTimeout(
    int healthCheckTimeout);


  /**
   * <p>
   * メソッド SetGetSummaryTimeout
   * </p>
   * @param getSummaryTimeout Mandatory int parameter.
   */

  @DISPID(29)
  void setGetSummaryTimeout(
    int getSummaryTimeout);


  /**
   * <p>
   * メソッド SetRePrintTimeout
   * </p>
   * @param rePrintTimeout Mandatory int parameter.
   */

  @DISPID(30)
  void setRePrintTimeout(
    int rePrintTimeout);


  /**
   * <p>
   * メソッド SetCreditSalesTimeout
   * </p>
   * @param creditSalesTimeout Mandatory int parameter.
   */

  @DISPID(31)
  void setCreditSalesTimeout(
    int creditSalesTimeout);


  /**
   * <p>
   * メソッド SetCreditCancelTimeout
   * </p>
   * @param creditCancel Mandatory int parameter.
   */

  @DISPID(32)
  void setCreditCancelTimeout(
    int creditCancel);


  /**
   * <p>
   * メソッド SetCreditSummaryTimeout
   * </p>
   * @param creditSummaryTimeout Mandatory int parameter.
   */

  @DISPID(33)
  void setCreditSummaryTimeout(
    int creditSummaryTimeout);


  /**
   * <p>
   * メソッド GetMaxQueueLength
   * </p>
   */

  @DISPID(34)
  int getMaxQueueLength();


  /**
   * <p>
   * メソッド SetMaxQueueLength
   * </p>
   * @param maxLength Mandatory int parameter.
   */

  @DISPID(35)
  void setMaxQueueLength(
    int maxLength);


  /**
   * <p>
   * メソッド GetHealthCheckReceiveTimeout
   * </p>
   */

  @DISPID(36)
  int getHealthCheckReceiveTimeout();


  /**
   * <p>
   * メソッド GetGetSummaryReceiveTimeout
   * </p>
   */

  @DISPID(37)
  int getGetSummaryReceiveTimeout();


  /**
   * <p>
   * メソッド GetRePrintReceiveTimeout
   * </p>
   */

  @DISPID(38)
  int getRePrintReceiveTimeout();


  /**
   * <p>
   * メソッド GetCreditCancelReceiveTimeout
   * </p>
   */

  @DISPID(40)
  int getCreditCancelReceiveTimeout();


  /**
   * <p>
   * メソッド GetCreditSummaryReceiveTimeout
   * </p>
   */

  @DISPID(41)
  int getCreditSummaryReceiveTimeout();


  /**
   * <p>
   * メソッド GetCreditSalesReceiveTimeout
   * </p>
   */

  @DISPID(42)
  int getCreditSalesReceiveTimeout();


  /**
   * <p>
   * メソッド SetHealthCheckReceiveTimeout
   * </p>
   * @param healthCheckReceiveTimeout Mandatory int parameter.
   */

  @DISPID(43)
  void setHealthCheckReceiveTimeout(
    int healthCheckReceiveTimeout);


  /**
   * <p>
   * メソッド SetGetSummaryReceiveTimeout
   * </p>
   * @param getSummaryReceiveTimeout Mandatory int parameter.
   */

  @DISPID(44)
  void setGetSummaryReceiveTimeout(
    int getSummaryReceiveTimeout);


  /**
   * <p>
   * メソッド SetRePrintReceiveTimeout
   * </p>
   * @param rePrintReceiveTimeout Mandatory int parameter.
   */

  @DISPID(45)
  void setRePrintReceiveTimeout(
    int rePrintReceiveTimeout);


  /**
   * <p>
   * メソッド SetCreditSalesReceiveTimeout
   * </p>
   * @param creditSalesReceiveTimeout Mandatory int parameter.
   */

  @DISPID(46)
  void setCreditSalesReceiveTimeout(
    int creditSalesReceiveTimeout);


  /**
   * <p>
   * メソッド SetCreditCancelReceiveTimeout
   * </p>
   * @param creditCancelReceiveTimeout Mandatory int parameter.
   */

  @DISPID(47)
  void setCreditCancelReceiveTimeout(
    int creditCancelReceiveTimeout);


  /**
   * <p>
   * メソッド SetCreditSummaryReceiveTimeout
   * </p>
   * @param creditSummaryReceiveTimeout Mandatory int parameter.
   */

  @DISPID(48)
  void setCreditSummaryReceiveTimeout(
    int creditSummaryReceiveTimeout);


  // Properties:
}

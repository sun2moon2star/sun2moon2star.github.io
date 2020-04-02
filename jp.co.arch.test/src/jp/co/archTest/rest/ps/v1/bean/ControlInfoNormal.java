package jp.co.archTest.rest.ps.v1.bean;

import org.eclipse.persistence.annotations.Struct;

import lombok.Data;

@Data
@Struct(name = "ControlInfoNormal")
public class ControlInfoNormal {
	
	//モード
	public int mode;
    
    //サービス
    public int service;
    
    //業務
    public int biz;
    
    //処理結果
    public int procResult;
    
    //アウトプットID
    public long outputId;
    
    //端末固有ID
    public long termIdentityNo;
    
    //取引日時
    //String dateTime;

}

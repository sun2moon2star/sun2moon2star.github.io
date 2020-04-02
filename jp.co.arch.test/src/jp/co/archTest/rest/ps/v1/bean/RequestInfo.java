package jp.co.archTest.rest.ps.v1.bean;

import org.eclipse.persistence.annotations.Struct;

import lombok.Data;

@Data
@Struct(name = "RequestInfo")
public class RequestInfo {

	//モード
	public int mode = 1;
    
    //印字モード
	public int  printMode = 1;

}

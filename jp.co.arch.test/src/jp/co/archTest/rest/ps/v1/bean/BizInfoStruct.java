package jp.co.archTest.rest.ps.v1.bean;

import org.eclipse.persistence.annotations.Struct;

import lombok.Data;

@Data
@Struct(name = "BizInfoStruct")
public class BizInfoStruct {

	//Arch状態
	public int posWaitingStatus;

}

package jp.co.archTest.rest.ps.v1.bean;

import org.eclipse.persistence.annotations.Struct;

import lombok.Data;

@Data

@Struct(name = "HealthCheckNormalResponse")
public class HealthCheckNormalResponse {

	// 制御情報
	public ControlInfoNormal controlInfo;
	
	// 業務情報
	public BizInfoStruct bizInfo;

}

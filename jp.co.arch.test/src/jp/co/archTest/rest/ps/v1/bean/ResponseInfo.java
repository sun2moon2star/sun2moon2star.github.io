package jp.co.archTest.rest.ps.v1.bean;

import org.eclipse.persistence.annotations.Struct;

import lombok.Data;

@Data
@Struct(name = "ResponseInfo")
public class ResponseInfo {
	
	private HealthCheckNormalResponse response;

}

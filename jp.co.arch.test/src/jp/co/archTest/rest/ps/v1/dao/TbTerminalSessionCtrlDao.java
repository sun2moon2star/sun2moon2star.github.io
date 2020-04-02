/**
 * --------+-----------------+---------+----------------------------------------
 *  DATE   |NAME(Inc)        |GUIDE    |GUIDANCE
 * --------+-----------------+---------+----------------------------------------
 * 20200326 suxl(bbasoft)     G001.00.0 新規作成
 */
package jp.co.archTest.rest.ps.v1.dao;

import org.springframework.data.repository.CrudRepository;

import jp.co.archTest.rest.ps.v1.entity.TbTerminalSessionCtrl;

public interface TbTerminalSessionCtrlDao extends CrudRepository<TbTerminalSessionCtrl, String> {
	

}
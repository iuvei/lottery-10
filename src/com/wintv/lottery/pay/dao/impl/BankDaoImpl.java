package com.wintv.lottery.pay.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.wintv.framework.common.hibernate.BaseHibernate;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.PaymentException;
import com.wintv.framework.pojo.Bank;
import com.wintv.framework.pojo.User;
import com.wintv.lottery.pay.dao.BankDao;
import com.wintv.lottery.pay.vo.BankVo;

/**
 * 
 * @author Hikin Yao
 *
 * @version 1.0.0
 */

@Repository("bankDao")
@SuppressWarnings("unchecked")
public class BankDaoImpl extends BaseHibernate<Bank, Long> implements BankDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<BankVo> findChargeBank() throws PaymentException, DaoException {
		StringBuilder sql = new StringBuilder("select t.name as bankName,t.code as bankCode,t.img as imgUrl,t.url as bankUrl from t_bank t where t.charge_status = '1'");
		
		final List resultList = new ArrayList();
		
		jdbcTemplate.query(sql.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						BankVo bankVo = new BankVo();
						bankVo.setBankName(rs.getString("bankName"));
						bankVo.setBankCode(rs.getString("bankCode"));
						bankVo.setImgUrl(rs.getString("imgUrl"));
						bankVo.setBankUrl(rs.getString("bankUrl"));
						resultList.add(bankVo);
					}
				});
		
		return resultList;
	}

	@Override
	public List<BankVo> findWithdrawBank() throws PaymentException,
			DaoException {
		StringBuilder sql = new StringBuilder("select t.name as bankName,t.code as bankCode,t.img as imgUrl,t.url as bankUrl from t_bank t where t.withdraw_status = '1'");
		
		final List resultList = new ArrayList();
		
		jdbcTemplate.query(sql.toString(), resultList.toArray(),
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						BankVo bankVo = new BankVo();
						bankVo.setBankName(rs.getString("bankName"));
						bankVo.setBankCode(rs.getString("bankCode"));
						bankVo.setImgUrl(rs.getString("imgUrl"));
						bankVo.setBankUrl(rs.getString("bankUrl"));
						resultList.add(bankVo);
					}
				});
		
		return resultList;
	}
	/**
	 * 更改充值银行状态
	 * 
	 * checkedIds 用户Id
	 * @param status
	 *            '1':可用  '2':不可用
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateChargeBankStatus(String checkedIds, String status)
			throws DaoException {
		String sqlStr="update t_bank t set t.charge_status="+status+" where t.id in ( "+checkedIds+" )";
		Integer result=jdbcTemplate.update(sqlStr);
		return result;
	}
	/**
	 * 更改取款银行状态
	 * 
	 * checkedIds 用户Id
	 * @param status
	 *            '1':可用  '2':不可用
	 * @throws DaoException：数据操作异常
	 * @return 返回操作结果
	 */
	public int updateDrawBankStatus(String checkedIds, String status)
			throws DaoException {
		String sqlStr="update t_bank t set t.withdraw_status="+status+" where t.id in ( "+checkedIds+" )";
		Integer result=jdbcTemplate.update(sqlStr);
		return result;
	}
	/**
	 * 获取所有银行
	 * @return
	 */
	@Override
	public List<Bank> findAllBank() {
		// TODO Auto-generated method stub
		return this.getSession().createQuery("from Bank").list();
	}

}

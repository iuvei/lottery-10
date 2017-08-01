package com.wintv.lottery.index.action;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.wintv.framework.common.BaseAction;
import com.wintv.framework.exception.DaoException;
import com.wintv.framework.exception.LotteryBizException;
import com.wintv.lottery.bet.service.BetService;
import com.wintv.lottery.bet.vo.BetTop10Vo;;
@SuppressWarnings("unchecked")
public class RankingAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8386084552177858083L;
	@Autowired
	private BetService betService;
	/****
	 * 获取c2c心水top10
	 * @return
	 */
	public String searchRankingList() {
		try {
			Map params = new HashMap();
			params.put("flg", (String)request.getParameter("flg"));
			
			//查询排行
			List<BetTop10Vo> C2CTop10VoList = betService.findHmTop10List(params);
			
			if (isNotNull(C2CTop10VoList)) {
				Map result = new HashMap();
				result.put("C2CTop10VoList", C2CTop10VoList);

				generateResult(1, MSG_SUCCESS, result);
			} else {
				generateResult(0, MSG_FAILURE, "errors");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LotteryBizException(e.getLocalizedMessage());
		}
		return SUCCESS;
	}
}

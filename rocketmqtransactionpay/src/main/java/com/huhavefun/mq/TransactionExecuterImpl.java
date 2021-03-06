package com.huhavefun.mq;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
import com.huhavefun.entity.Pay;
import com.huhavefun.service.PayService;
import com.huhavefun.util.FastJsonConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by HuHaifan on 2017/6/15.
 */
@Component("transactionExecuterImpl")
public class TransactionExecuterImpl implements LocalTransactionExecuter {

    @Autowired
    private PayService payService;

    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
        try {
            //Message Body
            JSONObject messageBody = FastJsonConvert.convertJSONToObject(new String(msg.getBody(), "utf-8"), JSONObject.class);
            //Transaction MapArgs
            Map<String, Object> mapArgs = (Map<String, Object>) arg;

            // --------------------IN PUT---------------------- //
            System.out.println("message body = " + messageBody);
            System.out.println("message mapArgs = " + mapArgs);
            System.out.println("message tag = " + msg.getTags());
            // --------------------IN PUT---------------------- //

            //userid
            String userid = messageBody.getString("userid");
            //money
            double money = messageBody.getDouble("money");
            //mode
            String pay_mode = messageBody.getString("pay_mode");
            //pay
            Pay pay = this.payService.selectByPrimaryKey(userid);
            //持久化数据
            this.payService.updateAmount(pay, pay_mode, money);
            //成功通知MQ消息变更 该消息变为：<确认发送>

            return LocalTransactionState.COMMIT_MESSAGE;

            //return LocalTransactionState.UNKNOW;

        } catch (Exception e) {
            e.printStackTrace();
            //失败则不通知MQ 该消息一直处于：<暂缓发送>
            return LocalTransactionState.ROLLBACK_MESSAGE;

        }

    }
}

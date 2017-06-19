package com.huhavefun.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.huhavefun.entity.Pay;
import com.huhavefun.mq.MQProducer;
import com.huhavefun.util.FastJsonConvert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by HuHaifan on 2017/6/19.
 */
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PayTest {
    @Autowired
    private PayService payService;

    @Test
    public void testSave() throws Exception {
        Pay pay = new Pay();
        pay.setUserid("z3");
        pay.setUsername("张三");
        pay.setAmount(5000d);
        pay.setDetail("0");
        pay.setUpdateBy("z3");
        pay.setUpdateTime(new Date());
        this.payService.insert(pay);
    }
    @Test
    public void testUpdate() throws Exception {
        System.out.println(this.payService);
        Pay pay = this.payService.selectByPrimaryKey("z3");
        pay.setAmount(pay.getAmount() - 1000d);
        pay.setUpdateTime(new Date());
        this.payService.updateByPrimaryKey(pay);
    }

    @Autowired
    private MQProducer mQProducer;

    @Autowired
    private LocalTransactionExecuter transactionExecuterImpl;

    @Test
    public void test1() {
        try {
            System.out.println(this.mQProducer);
            System.out.println(this.transactionExecuterImpl);

            //构造消息数据
            Message message = new Message();
            //主题
            message.setTopic("pay");
            //子标签
            message.setTags("tag");
            //key
            String uuid = UUID.randomUUID().toString();
            System.out.println("key: " + uuid);
            message.setKeys(uuid);
            JSONObject body = new JSONObject();
            body.put("userid", "z3");
            body.put("money", "1000");
            body.put("pay_mode", "OUT");
            body.put("balance_mode", "IN");
            message.setBody(FastJsonConvert.convertObjectToJSON(body).getBytes());

            //添加参数
            Map<String, Object> transactionMapArgs = new HashMap<String, Object>();

            this.mQProducer.sendTransactionMessage(message, this.transactionExecuterImpl, transactionMapArgs);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() throws Exception {
        long end = new Date().getTime();
        long begin = end - 60 * 1000 * 60 * 24;
        QueryResult qr = this.mQProducer.queryMessage("pay", "6e4cefbb-5216-445f-9027-d96dd54a4afb", 10, begin, end);
        List<MessageExt> list = qr.getMessageList();
        for(MessageExt me : list){

            Map<String, String> m = me.getProperties();
            System.out.println(m.keySet().toString());
            System.out.println(m.values().toString());
            System.out.println(me.toString());
            System.err.println("内容: " + new String(me.getBody(), "utf-8"));
            System.out.println("Prepared :" + me.getPreparedTransactionOffset());
            LocalTransactionState ls = this.mQProducer.check(me);
            System.out.println(ls);
            //this.mQProducer.getTransactionCheckListener()
        }
        //System.out.println("qr: " + qr.toString());
        //C0A8016F00002A9F0000000000034842
    }

}
package com.sunlights.account.web;

import com.sunlights.BaseTest;
import com.sunlights.account.AccountConstant;
import com.sunlights.account.service.RewardFlowService;
import com.sunlights.account.service.impl.RewardFlowServiceImpl;
import com.sunlights.common.MsgCode;
import com.sunlights.common.vo.MessageVo;
import models.RewardFlow;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.db.jpa.JPA;
import play.libs.F;
import play.mvc.Http;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

public class ActivityControllerTest extends BaseTest{
    private static Http.Cookie cookie;

    @Before
    public void getCookie(){
        final String mobilePhoneNo = "13811599307";
        final String password = "1";
        running(fakeApplication(), new Runnable() {
            public void run() {
                cookie = getCookieAfterLogin(mobilePhoneNo, password);
            }
        });
    }

    @Test
    public void testSignInObtainReward() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("============testSignInObtainReward start====");
                JPA.withTransaction(new F.Callback0() {
                    @Override
                    public void invoke() throws Throwable {
                        Map<String, String> formParams = new HashMap<String, String>();
                        play.mvc.Result result = null;

                        RewardFlowService rewardFlowService = new RewardFlowServiceImpl();
                        RewardFlow rewardFlows = rewardFlowService.findTodayFlowByCustIdAndScene("20141119102210010000000029", AccountConstant.ACTIVITY_SIGNIN_SCENE_CODE);

                        //2:签到获取金豆正常测试
                        formParams = new HashMap<String, String>();
                        formParams.put("scene", AccountConstant.ACTIVITY_SIGNIN_SCENE_CODE);
                        result = getResult("/account/activity/signin", formParams, cookie);
                        assertThat(status(result)).isEqualTo(OK);

                        final MessageVo message = toMessageVo(result);

                        if(rewardFlows != null) {
                            assertThat(message.getMessage().getCode()).isEqualTo(MsgCode.ALREADY_SIGN.getCode());
                            assertThat("20141119102210010000000029").isEqualTo(rewardFlows.getCustId());
                        } else {
                            assertThat(message.getMessage().getCode()).isEqualTo(MsgCode.OBTAIN_SUCC.getCode());
                        }

                        Logger.info("============testSignInObtainReward result====\n" + contentAsString(result));
                    }
                });

            }
        });
    }

    @Test
    public void testGetActivityList() throws Exception {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("============testGetActivityList start====");
                String index = "0";
                String pageSize = "3";


                Map<String, String> formParams = new HashMap<>();
                formParams.put("index", index);
                formParams.put("pageSize", pageSize);


                play.mvc.Result result = getResult("/account/activity/list", formParams);
                Logger.info("============testGetActivityList result====\n" + contentAsString(result));

            }
        });
    }

    @Test
    public void testInviteObtainReward() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("============testInviteObtainReward start====");
                //1：邀请好友获取金豆测试
                Map<String, String> formParams = new HashMap<String, String>();
                formParams.put("scene", AccountConstant.ACTIVITY_INVITE_SCENE_CODE);
                play.mvc.Result result = getResult("/account/activity/invite", formParams, cookie);
                assertThat(status(result)).isEqualTo(OK);
                MessageVo message = toMessageVo(result);
                assertThat(message.getMessage().getCode()).isEqualTo(MsgCode.OBTAIN_SUCC.getCode());

                Logger.info("============testInviteObtainReward result====\n" + contentAsString(result));
            }
        });
    }

    @Test
    public void testRegisterObtainReward() {
        running(fakeApplication(), new Runnable() {
            public void run() {
                Logger.info("============testRegisterObtainReward start====");
                //1：注册获取金豆测试
                Map<String, String> formParams = new HashMap<String, String>();
                formParams.put("scene", AccountConstant.ACTIVITY_REGISTER_SCENE_CODE);
                play.mvc.Result result = getResult("/account/activity/register", formParams, cookie);
                assertThat(status(result)).isEqualTo(OK);
                MessageVo message = toMessageVo(result);
                assertThat(message.getMessage().getCode()).isEqualTo(MsgCode.OBTAIN_SUCC.getCode());

                Logger.info("============testRegisterObtainReward result====\n" + contentAsString(result));
            }
        });
    }


}
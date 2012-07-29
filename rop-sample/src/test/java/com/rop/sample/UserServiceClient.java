/**
 * 版权声明：中图一购网络科技有限公司 版权所有 违者必究 2012 
 * 日    期：12-6-30
 */
package com.rop.sample;

import com.rop.client.CompositeResponse;
import com.rop.client.DefaultRopClient;
import com.rop.client.RopClient;
import com.rop.response.ErrorResponse;
import com.rop.sample.request.CreateUserRequest;
import com.rop.sample.request.LogonRequest;
import com.rop.sample.response.CreateUserResponse;
import com.rop.sample.response.LogonResponse;
import com.rop.validation.MainErrorType;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * <pre>
 * 功能说明：
 * </pre>
 *
 * @author 陈雄华
 * @version 1.0
 */
public class UserServiceClient {

    public static final String SERVER_URL = "http://localhost:8088/router";
    public static final String APP_KEY = "00001";
    public static final String APP_SECRET = "abcdeabcdeabcdeabcdeabcde";
    private RopClient ropClient = new DefaultRopClient(SERVER_URL, APP_KEY, APP_SECRET);

    @Test
    public void createSession() {
        LogonRequest ropRequest = new LogonRequest();
        ropRequest.setUserName("tomson");
        ropRequest.setPassword("123456");
        CompositeResponse response = ropClient.get(ropRequest, LogonResponse.class, "user.getSession", "1.0");
        assertNotNull(response);
        assertTrue(response.isSuccessful());
        assertNotNull(response.getSuccessResponse());
        assertTrue(response.getSuccessResponse() instanceof LogonResponse);
        assertEquals(((LogonResponse) response.getSuccessResponse()).getSessionId(), "mockSessionId1");
    }

//    /**
//     * 在一切正确的情况下，返回正确的服务报文 (user.add + 3.0）
//     */
//    @Test(invocationCount = 10000,threadPoolSize = 20)
//    public void testAddUserByVersion1() {
//        CreateUserRequest ropRequest = new CreateUserRequest();
//        ropRequest.setUserName("tomson");
//        ropRequest.setSalary(2500L);
//        CompositeResponse response = ropClient.post(ropRequest, CreateUserResponse.class,
//                "user.add", "1.0", "mockSessionId1");
//        assertNotNull(response);
//        assertTrue(response.isSuccessful());
//        assertNotNull(response.getSuccessResponse());
//        assertTrue(response.getSuccessResponse() instanceof CreateUserResponse);
//    }

    /**
     * 在一切正确的情况下，返回正确的服务报文 (user.add + 3.0）
     */
    @Test
    public void testAddUserByVersion3() {
        CreateUserRequest ropRequest = new CreateUserRequest();
        ropRequest.setUserName("tomson");
        ropRequest.setSalary(2500L);
        CompositeResponse response = ropClient.post(ropRequest, CreateUserResponse.class,
                "user.add", "3.0", "mockSessionId1");
        assertNotNull(response);
        assertFalse(response.isSuccessful());
        assertNull(response.getSuccessResponse());
        assertNotNull(response.getErrorResponse());
        assertTrue(response.getErrorResponse() instanceof ErrorResponse);
        assertEquals(response.getErrorResponse().getCode(), MainErrorType.UNSUPPORTED_VERSION.value());
    }
}


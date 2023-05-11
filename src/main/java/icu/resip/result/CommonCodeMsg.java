package icu.resip.result;

/**
 * @Author Peng
 * @Date 2022/1/28 14:47
 * @Version 1.0
 */
public class CommonCodeMsg extends CodeMsg {

    private CommonCodeMsg(Integer code, String msg) {
        super(code,msg);
    }

    public static final CommonCodeMsg TOKEN_INVALID = new CommonCodeMsg(-2,"登录超时,请重新登录");

    public static final CommonCodeMsg LOGIN_IP_CHANGE = new CommonCodeMsg(-3,"登录IP发生改变,请重新登录");

    public static final CommonCodeMsg NULL_PARAM = new CommonCodeMsg(6001,"参数不能为空");

    public static final CommonCodeMsg GET_OPENID_ERROR = new CommonCodeMsg(6002, "获取openid失败");

    public static final CommonCodeMsg ILLEGAL_TOKEN = new CommonCodeMsg(6003,"token不合法");

    public static final CommonCodeMsg LOGOUT_ERROR = new CommonCodeMsg(6004,"注销失败");

    public static final CommonCodeMsg ILLEGAL_OPERATION = new CommonCodeMsg(6005,"权限不足，非法操作");

    public static final CommonCodeMsg ERROR_ENCRYPTED_DATA = new CommonCodeMsg(6006, "解密EncryptedData失败");

    public static final CommonCodeMsg ILLEGAL_EMAIL = new CommonCodeMsg(7001, "email不合法");

    public static final CommonCodeMsg EMAIL_REGISTERED = new CommonCodeMsg(7002, "email已被注册");

    public static final CommonCodeMsg EMAIL_SEND_ERROR = new CommonCodeMsg(7003, "邮件发送失败");

    public static final CommonCodeMsg VERIFY_CODE_ERROR = new CommonCodeMsg(7004, "验证码不正确或已过期");

    public static final CommonCodeMsg REGISTER_ERROR = new CommonCodeMsg(7005, "注册失败");

    public static final CommonCodeMsg LOGIN_ERROR = new CommonCodeMsg(7006, "登录失败，邮箱号或密码有误");

    public static final CommonCodeMsg FILE_ONLY_IMG= new CommonCodeMsg(7007, "仅允许上传图片");

    public static final CommonCodeMsg FILE_TOO_LARGE= new CommonCodeMsg(7008, "请上传10M以内的图片");

    public static final CommonCodeMsg FILE_UPLOAD_ERROR= new CommonCodeMsg(7009, "图片上传失败");

    public static final CommonCodeMsg EDIT_AVATAR_ERROR = new CommonCodeMsg(7010, "头像更新失败");

    public static final CommonCodeMsg ADD_ADDRESS_ERROR = new CommonCodeMsg(7011, "添加地址失败");

    public static final CommonCodeMsg EDIT_ADDRESS_ERROR = new CommonCodeMsg(7012, "编辑地址失败");

    public static final CommonCodeMsg DEL_ADDRESS_ERROR = new CommonCodeMsg(7013, "删除地址失败");

    public static final CommonCodeMsg WEBSOCKET_CONNECT_ERROR = new CommonCodeMsg(7014, "websocket连接异常");

    public static final CommonCodeMsg CHAT_WEIGHT_ERROR = new CommonCodeMsg(7015, "消息置顶失败");

    public static final CommonCodeMsg CHAT_LIST_DEL_ERROR = new CommonCodeMsg(7016, "移除消息失败");

    public static final CommonCodeMsg AUTH_SUBMIT_ERROR = new CommonCodeMsg(7017, "发起认证失败");

    public static final CommonCodeMsg AUTH_RESUBMIT_ERROR = new CommonCodeMsg(7018, "已发起认证，请勿重复提交");

    public static final CommonCodeMsg MONEY_ERROR = new CommonCodeMsg(7019, "非法金额");

    public static final CommonCodeMsg PAY_ERROR = new CommonCodeMsg(7020, "调用微信支付创建预订单失败");

    public static final CommonCodeMsg NO_THIS_ORDER = new CommonCodeMsg(7021, "无此订单");

    public static final CommonCodeMsg PAY_SUCCESSED = new CommonCodeMsg(7022, "订单已完成");

    public static final CommonCodeMsg ORDER_EDIT_ERROR = new CommonCodeMsg(7023, "订单修改失败");

    public static final CommonCodeMsg NOT_SET_WALLET_PASSWD = new CommonCodeMsg(7024, "未设置钱包密码，请设置");

    public static final CommonCodeMsg PASSWD_VERIFY_ERROR = new CommonCodeMsg(7025, "钱包密码校验失败");

    public static final CommonCodeMsg MONEY_NOT_ENOUGH = new CommonCodeMsg(7026, "余额不足");

    public static final CommonCodeMsg CREATE_ORDER_ERROR = new CommonCodeMsg(7027, "下单失败");

    public static final CommonCodeMsg GET_PHONE_ERROR = new CommonCodeMsg(7028, "获取手机号失败");

    public static final CommonCodeMsg ILLEGAL_CONDITION = new CommonCodeMsg(7029, "条件不合法");

    public static final CommonCodeMsg UN_AUTH = new CommonCodeMsg(7030, "未通过跑腿认证");

    public static final CommonCodeMsg RECEIVE_ORDER_ERROR = new CommonCodeMsg(7031, "接单失败");

    public static final CommonCodeMsg HUMAN_VERIFY_ERROR = new CommonCodeMsg(7032, "人机校验失败");

    public static final CommonCodeMsg CANCEL_ORDER_ERROR = new CommonCodeMsg(7033, "取消订单失败");

    public static final CommonCodeMsg ORDER_EXPIRE = new CommonCodeMsg(7034, "订单已过期");

    public static final CommonCodeMsg ILLEGAL_RECEIVE_CODE = new CommonCodeMsg(7035, "收货码不合法");

    public static final CommonCodeMsg CONFIRM_ORDER_ERROR = new CommonCodeMsg(7036, "确认送达失败");

    public static final CommonCodeMsg PERMISSION_RELOAD_ERROR = new CommonCodeMsg(8001, "权限加载失败");

}

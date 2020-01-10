/**
 * 系统中使用到的公共js函数库
 *
 * @author Kival
 */

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

/**
 * 获取URL地址栏中的参数值
 * @param name 参数名称
 */
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);

    if(r != null)
        return unescape(r[2]);

    return "";
}


// '登录状态'列表
let LOGIN_RESULT = { 0:'失败', 1:'成功' };

// '用户类型'列表
let ACCOUNT_TYPE = { 1:'普通用户', 2:'运营人员' };

// '还款类型'列表
let REPAYMENT_TYPE = { 1:'等额本息', 2:'先息后本' };

// '充值审核状态'列表
let RECHARGE_STATE = { 0:'审核拒绝', 1:'审核中', 2:'审核通过'};
// '充值审核状态'常量表
let RECHARGE_STATE_CONST = {

    REJECT: 0, // 审核拒绝
    PENDING: 1, // 审核中
    PASS: 2 // 审核通过
};

// '借款审核状态'列表
let BORROW_STATE = {
    0:'已撤销',
    10:'发布审核中',
    11:'发布审核拒绝',
    12:'发布审核通过',
    20:'招标中',
    21:'流标',
    30:'放款审核中',
    31:'放款审核拒绝',
    32:'放款审核通过',
    40:'还款中',
    50:'已完成'
};
// '借款审核状态'常量表
let BORROW_STATE_CONST = {

    UNDO: 0, // '已撤销',
    PUBLISH_PENDING: 10, // '发布审核中',
    PUBLISH_AUDIT_REJECT: 11, // '发布审核拒绝',
    PUBLISH_AUDIT_PASS: 12, // '发布审核通过',
    BIDDING: 20, // '招标中',
    OVERDUE: 21, // 流标
    LOAN_PENDING: 30, // '放款审核中',
    LOAN_AUDIT_REJECT: 31, // '放款审核拒绝',
    LOAN_AUDIT_PASS: 32, // '放款审核通过',
    REPAYMENT: 40, // '还款中'
    REPAYMENT_CLEARED: 50 // 已完成
};

// '还款状态'列表
let REPAYMENT_STATE = {
    1: '逾期',
    2: '待还',
    3: '已还',
    4: '逾期已还'
};
// '还款状态'常量表
let REPAYMENT_STATE_CONST = {

    OVERDUE: 1,  // 逾期
    WAIT: 2, // 待还
    PAID: 3, // 已还
    OVERDUE_PAID: 4 // 逾期已还
};


// '信息完善状态'常量表
let INFO_COMPLETE_STATE = {

    NONE: 0, // 未完善
    COMPLETE: 1 // 已完善
};


// 后台头像图片地址
let IMAGE_AVATAR_URL = 'http://localhost:8080/avatar/';

// 货币单位：元(1元 = 100分)
let MONEY_UNIT = 100;

// 系统最小投资金额
let MINIMUM_BID_AMOUNT = 50 * MONEY_UNIT;

/************************************
 *
 * 数据字典组id
 *
 * PS:在系统后台设置好'数据字典组'后，
 * 需要在这里配置数据字典组！！！
 *
 * @key: 数据字典组编码
 * @value: 数据字典组id
 *
 ************************************/
let SYS_DICTIONARY_CODE = {

    sys_edu_background: 1, // 教育背景

    sys_income_level: 2,   // 收入等级

    sys_marriage: 3, // 婚姻状况

    sys_house_condition: 4 //住房情况

};// SYS_DICTIONARY_CODE;
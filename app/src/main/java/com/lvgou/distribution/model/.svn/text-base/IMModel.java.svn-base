/*
 * {EasyGank}  Copyright (C) {2015}  {CaMnter}
 *
 * This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
 * This is free software, and you are welcome to redistribute it
 * under certain conditions; type `show c' for details.
 *
 * The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License.  Of course, your program's commands
 * might be different; for a GUI interface, you would use an "about box".
 *
 * You should also get your employer (if you work as a programmer) or school,
 * if any, to sign a "copyright disclaimer" for the program, if necessary.
 * For more information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 *
 * The GNU General Public License does not permit incorporating your program
 * into proprietary programs.  If your program is a subroutine library, you
 * may consider it more useful to permit linking proprietary applications with
 * the library.  If this is what you want to do, use the GNU Lesser General
 * Public License instead of this License.  But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */

package com.lvgou.distribution.model;


import com.lvgou.distribution.api.ICallBackListener;
import com.lvgou.distribution.bean.AddUser;
import com.lvgou.distribution.bean.GetGroupMessage;
import com.lvgou.distribution.bean.GetMemberList;
import com.lvgou.distribution.bean.JoinChatGroupBean;
import com.lvgou.distribution.bean.SmsBean;

/**
 * Description：IDataModel
 * 定义DataModel要实现的功能
 * Created by：CaMnter
 * Time：2016-01-06 17:50
 */
public interface IMModel {
    /**
     * 新增用户接口
     * @param id:请求参数：封装为bean
     * @param mICallBackListener:回调接口
     */
void addUser(String userName,AddUser id, ICallBackListener mICallBackListener);
    /**
     * 群关联用户
     * @param id:请求参数：封装为bean
     * @param mICallBackListener:回调接口
     */
    void JoinChatGroup(String access_token,JoinChatGroupBean id, ICallBackListener mICallBackListener);
    /**
     * 获取聊天服务器地址
     * @param access_token:请求参数：
     * @param mICallBackListener:回调接口
     */
    void getServer(String access_token, ICallBackListener mICallBackListener);
    /**
     * 群成员列表
     * @param id:请求参数：封装为bean
     * @param mICallBackListener:回调接口
     */
    void member_list(String access_token,GetMemberList id, ICallBackListener mICallBackListener);
    /**
     * 获取历史消息
     * @param id:请求参数：封装为bean
     * @param mICallBackListener:回调接口
     */
    void GetGroupMessageExt(String access_token,GetGroupMessage id, ICallBackListener mICallBackListener);

    /**
     * 获取直播观看人数接口
     * @param groupid:请求参数：封装为bean
     * @param mICallBackListener:回调接口
     */

    void GetGroupInfo(String access_token,String groupid, ICallBackListener mICallBackListener);
    /**
     * 群组中移除用户
     * @param mICallBackListener:回调接口
     */
    void RemoveMemberGroup(String access_token, JoinChatGroupBean bean, ICallBackListener mICallBackListener);


    void GetNickName(String distributorid, String sign,ICallBackListener mICallBackListener);
    /**
     *学员禁言列表
     * @param sign
     * @return
     */
    void Prohibitlist(String studyid,String sign, ICallBackListener mICallBackListener);
    /**
     *解除禁言
     * @param kdbdistributorid
     * @param sign
     * @return
     */
    void Releaseshutup(String kdbdistributorid,String studyid,String distributorid,String sign, ICallBackListener mICallBackListener);

    /**
     *直播间禁言
     * @param kdbdistributorid
     * @param sign
     * @return
     */
    void Bannedtopost(String kdbdistributorid,String studyid,String distributorid,String sign, ICallBackListener mICallBackListener);

}

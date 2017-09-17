package com.lvgou.distribution.driect.entity;

import android.content.Intent;
import android.util.Log;

import com.lvgou.distribution.base.BaseApplication;
import com.lvgou.distribution.bean.GroupMessageExtData;

import java.text.SimpleDateFormat;

/**
 * 消息处理程序 Created by Echofool on 2015/12/25.
 */
public class MessageHandler {
	private static MessageHandler messageHandler = null;

//	public void receive(OfflineReasonMessage message){
//		
//		if(null!=message.getMessage()){
//			Log.i("打印异常信息",message.getMessage());
//			switch(message.getReason()){
//			case 0:
//				break;
//			case 1:
//				break;
//			case 2:
//				Intent intent=new Intent();
//			    
//			    
//			
//			
//			
//			}
//	
//			
//			
//		}
//		
//		
//	}
//	

	/**
	 * 接收群组消息
	 *
	 * @param message
	 *            群组消息
	 */
	public void receive(GroupMessage message) {
		if (null != message.getMessageType()) {
			switch (message.getMessageType()) {
			case Text:
				this.receive((TextGroupMessage) message);
				break;
			case Image:
				this.receive((DirectRowPictureEntity) message);
				break;
			case Voice:
				this.receive((DirectRowVoiceEntity) message);
				break;
			case ShortVideo:
				this.receive((ShortVideoGroupMessage) message);
				break;
			case ActionNotify:
				break;
			}
		} else {
			if (message instanceof TextGroupMessage) {
				this.receive((TextGroupMessage) message);
			}
			if (message instanceof DirectRowPictureEntity) {
				this.receive((DirectRowPictureEntity) message);
			} else if (message instanceof DirectRowVoiceEntity) {
				this.receive((DirectRowVoiceEntity) message);
			} else if (message instanceof ShortVideoGroupMessage) {
				this.receive((ShortVideoGroupMessage) message);
			}
		}
	}


	/**
	 * 接收单聊消息
	 *
	 * @param message
	 *            单聊消息
	 */
	public void receive(GroupMessage message,int a) {
		Log.e("kashksjahfd", "------------接收单聊消息");
		if (null != message.getMessageType()) {
			switch (message.getMessageType()) {
				case Text:
					this.singleReceive((TextGroupMessage) message,a);
					break;
				/*case Image:
					this.receive((DirectRowPictureEntity) message);
					break;
				case Voice:
					this.receive((DirectRowVoiceEntity) message);
					break;
				case ShortVideo:
					this.receive((ShortVideoGroupMessage) message);
					break;
				case ActionNotify:
					break;*/
			}
		} else {
			if (message instanceof TextGroupMessage) {
				this.singleReceive((TextGroupMessage) message,1);
			}
		/*	if (message instanceof DirectRowPictureEntity) {
				this.receive((DirectRowPictureEntity) message);
			} else if (message instanceof DirectRowVoiceEntity) {
				this.receive((DirectRowVoiceEntity) message);
			} else if (message instanceof ShortVideoGroupMessage) {
				this.receive((ShortVideoGroupMessage) message);
			}*/
		}
	}

	/**
	 * 接收文本单聊消息
	 *
	 * @param message
	 *            文本群组消息
	 */
	public void singleReceive(TextGroupMessage message,int a) {
		Log.e("kashksjahfd", "------------接收单聊消息"+a);
		//数据判断 发送广播
		Intent i = new Intent("com.distribution.single.message");
		GroupMessageExtData messageExtData=new GroupMessageExtData();
		messageExtData.setSI(message.getSenderId());
//		messageExtData.setGI(message.getGroupId());
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageExtData.setCT(simpleDateFormat.format(message.getCreationTime()));
		messageExtData.setMT(0);
		messageExtData.setI(message.getId());
		messageExtData.setC(message.getContent());
		i.putExtra("message",messageExtData);
		BaseApplication.context.sendBroadcast(i);
//		String myId = Utils.getValue(App.getInstance(), Constants.ID);
//		if (!TextUtils.isEmpty(message.getContent())) {
//			if (!message.getSenderId().equals(myId)) {
//				try {
//					DataHelper.getDbutilsInstance().saveOrUpdate(message);
//				} catch (DbException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 保存接受消息目录和具体消息
//				MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//						message.getId(), message.getSenderId(),
//						message.getGroupId(), Constants.TXT, Constants.GROUP,
//						Constants.RECEIVE, Constants.UNREAD,
//						message.getCreationTime(), null, null,null);
//				// conversation?
//				if (messagebody != null)
//					ConversationsDao.updateConversation(message.getGroupId(),
//							message.getCreationTime(), Constants.GROUP, true);
//			} else {
//				try {
//					DataHelper.getDbutilsInstance().saveOrUpdate(message);
//				} catch (DbException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 保存接受消息目录和具体消息
//				MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//						message.getId(), message.getSenderId(),
//						message.getGroupId(), Constants.TXT, Constants.GROUP,
//						Constants.SEND, Constants.SUCCESS,
//						message.getCreationTime(), null, null,null);
//				// conversation?
//				if (messagebody != null)
//					ConversationsDao.updateConversation(message.getGroupId(),
//							message.getCreationTime(), Constants.GROUP, false);
//			}
//
//		}
	}
	/**
	 * 接收文本群组消息
	 *
	 * @param message
	 *            文本群组消息
	 */
	public void receive(TextGroupMessage message) {
		//数据判断 发送广播
		Intent i = new Intent("com.distribution.group.message");
		GroupMessageExtData messageExtData=new GroupMessageExtData();
		messageExtData.setSI(message.getSenderId());
		messageExtData.setGI(message.getGroupId());
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageExtData.setCT(simpleDateFormat.format(message.getCreationTime()));
		messageExtData.setMT(0);
		messageExtData.setI(message.getId());
		messageExtData.setC(message.getContent());
                            i.putExtra("message",messageExtData);
		BaseApplication.context.sendBroadcast(i);
//		String myId = Utils.getValue(App.getInstance(), Constants.ID);
//		if (!TextUtils.isEmpty(message.getContent())) {
//			if (!message.getSenderId().equals(myId)) {
//				try {
//					DataHelper.getDbutilsInstance().saveOrUpdate(message);
//				} catch (DbException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 保存接受消息目录和具体消息
//				MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//						message.getId(), message.getSenderId(),
//						message.getGroupId(), Constants.TXT, Constants.GROUP,
//						Constants.RECEIVE, Constants.UNREAD,
//						message.getCreationTime(), null, null,null);
//				// conversation?
//				if (messagebody != null)
//					ConversationsDao.updateConversation(message.getGroupId(),
//							message.getCreationTime(), Constants.GROUP, true);
//			} else {
//				try {
//					DataHelper.getDbutilsInstance().saveOrUpdate(message);
//				} catch (DbException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 保存接受消息目录和具体消息
//				MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//						message.getId(), message.getSenderId(),
//						message.getGroupId(), Constants.TXT, Constants.GROUP,
//						Constants.SEND, Constants.SUCCESS,
//						message.getCreationTime(), null, null,null);
//				// conversation?
//				if (messagebody != null)
//					ConversationsDao.updateConversation(message.getGroupId(),
//							message.getCreationTime(), Constants.GROUP, false);
//			}
//
//		}
	}

	/**
	 * 接收图片群组消息
	 *
	 * @param message
	 *            图片群组消息
	 */
	public void receive(DirectRowPictureEntity message) {
		Intent i = new Intent("com.distribution.group.message");
		GroupMessageExtData messageExtData=new GroupMessageExtData();
		messageExtData.setSI(message.getSenderId());
		messageExtData.setGI(message.getGroupId());
		messageExtData.setMT(1);
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageExtData.setCT(simpleDateFormat.format(message.getCreationTime()));
		messageExtData.setI(message.getId());
		messageExtData.setO(message.getUrl());
		messageExtData.setT(message.getThumbnailUrl());
		i.putExtra("message",messageExtData);
		BaseApplication.context.sendBroadcast(i);
//		String myId = Utils.getValue(App.getInstance(), Constants.ID);
//		// 本地原始图片缓冲路径
//		String remotePath = message.getOriginal();
//		String filePath = MediaUtils.getLocalUrlPath(remotePath);
//
//		// 本地缩略图片缓冲路径
//		String thumbRemoteUrl = message.getThumbnail();
//		String thumbnailPath = MediaUtils.getThumbnailUrlPath(thumbRemoteUrl);
//		if (!message.getSenderId().equals(myId)) {
//			try {
//				DataHelper.getDbutilsInstance().saveOrUpdate(message);
//			} catch (DbException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// 保存接受消息目录和具体消息
//			MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//					message.getId(), message.getSenderId(),
//					message.getGroupId(), Constants.IMAGE, Constants.GROUP,
//					Constants.RECEIVE, Constants.WAITDOWN,
//					message.getCreationTime(), filePath, thumbnailPath,null);
//			// conversation?
//			if (messagebody != null)
//				ConversationsDao.updateConversation(message.getGroupId(),
//						message.getCreationTime(), Constants.GROUP, true);
//		} else {
//			try {
//				DataHelper.getDbutilsInstance().saveOrUpdate(message);
//			} catch (DbException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// 保存接受消息目录和具体消息
//			MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//					message.getId(), message.getSenderId(),
//					message.getGroupId(), Constants.IMAGE, Constants.GROUP,
//					Constants.SEND, Constants.SUCCESS,
//					message.getCreationTime(), filePath, thumbnailPath,null);
//			// conversation?
//			if (messagebody != null)
//				ConversationsDao.updateConversation(message.getGroupId(),
//						message.getCreationTime(), Constants.GROUP, false);
//		}
	}

	/**
	 * 接收语音群组消息
	 *
	 * @param message
	 *            语音群组消息
	 */
	public void receive(DirectRowVoiceEntity message) {
		Intent i = new Intent("com.distribution.group.message");
		GroupMessageExtData messageExtData=new GroupMessageExtData();
		messageExtData.setSI(message.getSenderId());
		messageExtData.setGI(message.getGroupId());
		messageExtData.setI(message.getId());
		messageExtData.setL(message.getVoicetime());
		messageExtData.setMT(2);
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageExtData.setCT(simpleDateFormat.format(message.getCreationTime()));
		messageExtData.setU(message.getUrl());
		i.putExtra("message",messageExtData);
		BaseApplication.context.sendBroadcast(i);
//		String myId = Utils.getValue(App.getInstance(), Constants.ID);
//		if (!message.getSenderId().equals(myId)) {
//			try {
//				DataHelper.getDbutilsInstance().saveOrUpdate(message);
//			} catch (DbException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// 保存接受消息目录和具体消息
//			MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//					message.getId(), message.getSenderId(),
//					message.getGroupId(), Constants.VOICE, Constants.GROUP,
//					Constants.RECEIVE, Constants.WAITDOWN,
//					message.getCreationTime(), null,
//					String.valueOf(message.getLength()),null);
//			// conversation?
//			if (messagebody != null)
//				ConversationsDao.updateConversation(message.getGroupId(),
//						message.getCreationTime(), Constants.GROUP, true);
//		} else {
//			try {
//				DataHelper.getDbutilsInstance().saveOrUpdate(message);
//			} catch (DbException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// 保存接受消息目录和具体消息
//			MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//					message.getId(), message.getSenderId(),
//					message.getGroupId(), Constants.VOICE, Constants.GROUP,
//					Constants.SEND, Constants.SUCCESS,
//					message.getCreationTime(), null,
//					String.valueOf(message.getLength()),null);
//			// conversation?
//			if (messagebody != null)
//				ConversationsDao.updateConversation(message.getGroupId(),
//						message.getCreationTime(), Constants.GROUP, false);
//		}
	}

	/**
	 * 接收小视频群组消息
	 *
	 * @param message
	 *            小视频群组消息
	 */
	public void receive(ShortVideoGroupMessage message) {
		Intent i = new Intent("com.distribution.group.message");
		GroupMessageExtData messageExtData=new GroupMessageExtData();
		messageExtData.setSI(message.getSenderId());
		messageExtData.setGI(message.getGroupId());
		messageExtData.setI(message.getId());
		messageExtData.setMT(3);
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		messageExtData.setCT(simpleDateFormat.format(message.getCreationTime()));
		messageExtData.setU(message.getUrl());
		messageExtData.setCI(message.getCoverImage());
		i.putExtra("message",messageExtData);
		BaseApplication.context.sendBroadcast(i);
//		String myId = Utils.getValue(App.getInstance(), Constants.ID);
//		if (message.getSenderId() != null) {
//			// 本地视屏缓冲路径
//			String remotePath = message.getUrl();
//			String filePath = MediaUtils.getLocalUrlPath(remotePath);
//
//			// 本地视屏首页缓冲路径
//			String thumbRemoteUrl = message.getCoverImage();
//			String thumbnailPath = "";
//			if (!TextUtils.isEmpty(thumbRemoteUrl)) {
//				thumbnailPath = MediaUtils.getThumbnailUrlPath(thumbRemoteUrl);
//			}
//
//			if (!message.getSenderId().equals(myId)) {
//				try {
//					DataHelper.getDbutilsInstance().saveOrUpdate(message);
//				} catch (DbException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 保存接受消息目录和具体消息
//				MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//						message.getId(), message.getSenderId(),
//						message.getGroupId(), Constants.SHORTVIDEO,
//						Constants.GROUP, Constants.RECEIVE,
//						Constants.WAITDOWN, message.getCreationTime(),
//						filePath, thumbnailPath,null);
//				// conversation?
//				if (messagebody != null)
//					ConversationsDao.updateConversation(message.getGroupId(),
//							message.getCreationTime(), Constants.SHORTVIDEO,
//							true);
//			} else {
//				try {
//					DataHelper.getDbutilsInstance().saveOrUpdate(message);
//				} catch (DbException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// 保存接受消息目录和具体消息
//				MessageBody messagebody = SaveMessageUtils.saveMessageBody(
//						message.getId(), message.getSenderId(),
//						message.getGroupId(), Constants.SHORTVIDEO,
//						Constants.GROUP, Constants.SEND, Constants.SUCCESS,
//						message.getCreationTime(), filePath, thumbnailPath,null);
//				// conversation?
//				if (messagebody != null)
//					ConversationsDao.updateConversation(message.getGroupId(),
//							message.getCreationTime(), Constants.SHORTVIDEO,
//							false);
//			}
//
//		}
	}


	/**
	 * 接收离线异常退出的消息
	 * 
	 * 
	 * 
	 * 
	 * 
	 * **/
//	public void receive(OfflineReasonMessage message){
//	     if(message.getOfflineReason()!=null){
//	    	 switch(message.getOfflineReason()){
//	    	 case AccessTokenMiss:
//	    		 Intent intent=new Intent();
//	    	
//	    		 
//	    	 }
//	    	 
//	    	 
//	     };
//			
//			
//			
//			
//		}
		
		

	public synchronized static MessageHandler getMessageHandler() {
		if (null == messageHandler) {
			messageHandler = new MessageHandler();
		}
		return messageHandler;
	}
}

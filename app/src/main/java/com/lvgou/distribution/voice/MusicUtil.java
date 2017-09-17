package com.lvgou.distribution.voice;

import java.io.IOException;
import java.util.ArrayList;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Audio.AudioColumns;


public class MusicUtil {
	
	public static ArrayList<Music> list = null;
	public static MediaPlayer mediaPlayer = null ;
	public static int musicCount = 0 ;
	public static String mode = "顺序播放";
	/**
	 * 
	 * 获取所有音乐
	 * 
	 */
	public static ArrayList<Music> getDataMusic(Context context){
		list = new ArrayList<Music>();
		ContentResolver cr = context.getContentResolver();
		if(cr != null){
			//获取所有歌曲
			Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
					null, null, null, null);
			
			while(cursor.moveToNext()){
				
				//歌曲id
				int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
				//歌曲title
				String title = cursor.getString(cursor
						.getColumnIndex(MediaColumns.TITLE));
				//歌曲名称
				String name = cursor.getString(cursor.
						getColumnIndex(MediaColumns.DISPLAY_NAME));
				//歌手
				String singer = cursor.getString(cursor
						.getColumnIndex(AudioColumns.ARTIST));
				if(singer.equals("<unknown>")){
					singer = "未知歌手";
				}
				//专辑
				String album = cursor.getString(cursor
						.getColumnIndex(AudioColumns.ALBUM));
				//大小
				long size = cursor.getLong(cursor
						.getColumnIndex(MediaColumns.SIZE));
				//时间
				long time = cursor.getLong(cursor
						.getColumnIndex(AudioColumns.DURATION));
				//路径
				String url = cursor.getString(cursor
						.getColumnIndex(MediaColumns.DATA));
				//判断是不是mp3格式的歌曲
				if(name.endsWith(".mp3") || name.endsWith(".MP3")){
					Music m = new Music();//创建歌曲对象
					m.setId(id);
					m.setName(name);
					m.setSinger(singer);
					m.setSize(size);
					m.setTime(time);
					m.setTitle(title);
					m.setAlbum(album);
					m.setUrl(url);
					list.add(m);
				}
			}
		}
		musicCount = list.size();
		return list;
	}

	/**
	 * 从数据库中删除歌曲
	 */
	public static void delete(Context context,int position){
		ContentResolver cr = context.getContentResolver();
		Uri uri = MediaStore.Audio.Media.getContentUriForPath(list.get(position).getUrl());
		long s = cr.delete(uri, BaseColumns._ID+"="+list.get(position).getId(),null);
		System.out.println("data..........ok"+s);
	}
	
	/**
	 * 播放音乐的方法
	 * @param position 歌曲在list中的下标
	 * @return
	 */
	
	public static MediaPlayer play(int position){
		Music music = list.get(position);
		if(mediaPlayer==null){
    		try {
    			mediaPlayer = new MediaPlayer();
    			
				mediaPlayer.setDataSource(music.getUrl());
				mediaPlayer.prepare();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	mediaPlayer.start();
    	return mediaPlayer;
	}
	
	/**
	 * 
	 * 暂停
	 * 
	 */
	public static void pause(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
    		mediaPlayer.pause();
    	}
	}

	/**
	 * 
	 * 停止播放
	 * 
	 */
	public static void stop(){
		if (mediaPlayer != null) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}
	
	/**
	 * 
	 * 改变时间样式
	 * 
	 */
	public static String formatTime(long time){
		String m = "";
		String n = "";
		//得到所有秒
		long t = time/1000;
		long mm = t/60;
		long ss = t%60;
		if(mm<10){
			m = "0"+mm;
		}else{
			m = mm+"";
		}
		if(ss<10){
			n = "0"+ss;
		}else{
			n = ss+"";
		}
		return m+":"+n;
	}
}

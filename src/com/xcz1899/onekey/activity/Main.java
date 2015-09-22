/**
 * 主界面
 * @author xcz1899
 */
package com.xcz1899.onekey.activity;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.xcz1899.onekey.R;
import com.xcz1899.onekey.data.User;
import com.xcz1899.onekey.view.RoundedImageView;

public class Main extends Activity implements OnClickListener {
    private SlidingMenu mSMMenu;

    private ImageButton mBtnLeft;// 主页左边按钮
    private ImageView mIVLogin;// 用户登录
    private ImageView mIVRegister;// 用户注册

    private RoundedImageView mRIVSMImage;// 头像
    private TextView mTVSMName;// 名称
    private RelativeLayout mRLSMUpload;// 我的上传
    private RelativeLayout mRLSMQuestion;// 我的问题
    private RelativeLayout mRLSMAsk;// 我的回答
    private RelativeLayout mRLSMCollectPlant;// 我收藏的植物
    private RelativeLayout mRLSMFollowQuestion;// 我关注的问题
    private RelativeLayout mRLSMSetting;// 我的设置
    private RelativeLayout mRLSMExit;// 关于

    private EditText mETLoginName;// 登录 用户名
    private EditText mETLoginPassword;// 登录 密码

    private EditText mETRegisterName;// 注册 用户名
    private EditText mETRegisterPassword;// 注册 密码
    private EditText mETRegisterEmail;// 注册 密码
    private RoundedImageView mRIVRegisterImg;// 注册 头像
    private Uri uri;// 头像的uri

    private EditText mETSettingName;// 注册 用户名
    private EditText mETSettingPassword;// 注册 密码
    private EditText mETSettingEmail;// 注册 密码
    private RoundedImageView mRIVSettingImg;// 注册 头像

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Bmob.initialize(this, "b16e060d6e83c9849b6ce3e72c0a351b");// 注册Bmob
	initView();// 初始化控件
	initListener();// 设置监听器
	setInfo();// 设置初始状态
    }

    /**
     * 初始化控件
     */
    private void initView() {

	/*---设置SlidingMenu菜单---*/
	mSMMenu = new SlidingMenu(this);// 设置SlidingMenu
	initSlidingMenu(mSMMenu);
	mRIVSMImage = (RoundedImageView) findViewById(R.id.riv_sm_image);// 头像
	mTVSMName = (TextView) findViewById(R.id.tv_sm_name);// 用户名
	mRLSMUpload = (RelativeLayout) findViewById(R.id.rl_sm_upload);// 我的上传
	mRLSMQuestion = (RelativeLayout) findViewById(R.id.rl_sm_question);// 我的问题
	mRLSMAsk = (RelativeLayout) findViewById(R.id.rl_sm_ask);// 我的回答
	mRLSMCollectPlant = (RelativeLayout) findViewById(R.id.rl_sm_collect_plant);// 我收藏的植物
	mRLSMFollowQuestion = (RelativeLayout) findViewById(R.id.rl_sm_follow_question);// 我关注的问题
	mRLSMSetting = (RelativeLayout) findViewById(R.id.rl_sm_setting);// 个人设置
	mRLSMExit = (RelativeLayout) findViewById(R.id.rl_sm_exit);// 退出登录

	/*---主界面---*/
	mBtnLeft = (ImageButton) findViewById(R.id.ib_main_left_button);// 设置单击top左边的按钮
	mIVLogin = (ImageView) findViewById(R.id.iv_login);// 单击登录
	mIVRegister = (ImageView) findViewById(R.id.iv_register);// 用户注册

    }

    /**
     * 初始化SlidingMenu
     * 
     * @param sm
     */
    private void initSlidingMenu(SlidingMenu sm) {
	sm.setMode(SlidingMenu.LEFT);// 设置在左边显示menu
	sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 滑动显示SlidingMenu的范围
	sm.setBehindWidth(getResources().getDimensionPixelSize(R.dimen.menu_width));// 菜单的宽度
	sm.setFadeDegree(0.35f);// 设置淡入淡出的比例
	sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置手势模式
	sm.setShadowDrawable(R.drawable.shadow);// 设置左菜单阴影图片
	sm.setFadeEnabled(true);// 设置滑动时菜单的是否淡入淡出
	sm.setBehindScrollScale(0.333f);// 设置滑动时拖拽效果
	sm.setMenu(R.layout.sm);// 菜单的布局文件
	sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);// 把SlidingMenu附加在Activity上
	// SlidingMenu.SLIDING_CONTENT:菜单拉开后高度是不包含Title/ActionBar的内容区域
    }

    /**
     * 设置各种控件的监听事件
     */
    private void initListener() {
	/*---设置SlidingMenu菜单---*/
	mRIVSMImage.setOnClickListener(this);
	mTVSMName.setOnClickListener(this);
	mRLSMUpload.setOnClickListener(this);
	mRLSMQuestion.setOnClickListener(this);
	mRLSMAsk.setOnClickListener(this);
	mRLSMCollectPlant.setOnClickListener(this);
	mRLSMFollowQuestion.setOnClickListener(this);
	mRLSMSetting.setOnClickListener(this);
	mRLSMExit.setOnClickListener(this);
	/*---主界面---*/
	mBtnLeft.setOnClickListener(this);
	mIVLogin.setOnClickListener(this);
	mIVRegister.setOnClickListener(this);
    }

    /**
     * 点击事件
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.ib_main_left_button:
	    mSMMenu.showMenu();
	    break;
	case R.id.riv_sm_image:
	    exit();
	    break;
	case R.id.iv_login:
	    login();
	    break;
	case R.id.iv_register:
	    register();
	    break;
	case R.id.rl_sm_exit:
	    exit();
	    break;
	case R.id.rl_sm_setting:
	    personSetting();
	    break;
	}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == RESULT_OK) {
	    uri = data.getData();
	    ContentResolver cr = this.getContentResolver();
	    try {
		Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
		/* 将Bitmap设定到ImageView */
		if (mRIVRegisterImg != null) {
		    mRIVRegisterImg.setImageBitmap(bitmap);
		} else if (mRIVSettingImg != null) {
		    mRIVSettingImg.setImageBitmap(bitmap);
		}

	    } catch (FileNotFoundException e) {
		Log.e("Exception", e.getMessage(), e);
	    }
	}
	super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 设置状态
     */
    private void setInfo() {
	if (User.getCurrentUser(this, User.class) != null) {
	    User.getCurrentUser(this, User.class).getImg().loadImage(this, mRIVSMImage);
	    mTVSMName.setText(User.getCurrentUser(this, User.class).getUsername());
	} else {
	    mRIVSMImage.setImageResource(R.drawable.default_img);
	    mTVSMName.setText("未登录");
	}

    }

    /**
     * 退出登录
     */
    public void exit() {
	User.logOut(this); // 清除缓存用户对象
	setInfo();
    }

    /**
     * 用户登录
     */
    private void login() {
	LayoutInflater inflaterLogin = getLayoutInflater();
	View layoutLogin = inflaterLogin.inflate(R.layout.dialog_login, null);
	mETLoginName = (EditText) layoutLogin.findViewById(R.id.et_dialog_login_register_name);
	mETLoginPassword = (EditText) layoutLogin.findViewById(R.id.et_dialog_login_register_password);
	new AlertDialog.Builder(this).setTitle("用户登录").setView(layoutLogin).setPositiveButton("确定", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		String name = mETLoginName.getText().toString();
		String password = mETLoginPassword.getText().toString();
		User user = new User();
		user.setUsername(name);
		user.setPassword(password);
		user.login(Main.this, new SaveListener() {
		    @Override
		    public void onSuccess() {
			// TODO Auto-generated method stub
			setInfo();
			toast("登录成功:");
		    }

		    @Override
		    public void onFailure(int code, String msg) {
			// TODO Auto-generated method stub
			toast("登录失败:" + msg);
		    }
		});
	    }
	}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
	    }

	}).show();
    }

    /**
     * 用户注册
     */
    private void register() {
	LayoutInflater inflaterRegister = getLayoutInflater();
	View layoutRegister = inflaterRegister.inflate(R.layout.dialog_register, null);
	mRIVRegisterImg = (RoundedImageView) layoutRegister.findViewById(R.id.riv_dialog_register_img);
	mETRegisterName = (EditText) layoutRegister.findViewById(R.id.et_dialog_login_register_name);
	mETRegisterPassword = (EditText) layoutRegister.findViewById(R.id.et_dialog_login_register_password);
	mETRegisterEmail = (EditText) layoutRegister.findViewById(R.id.et_dialog_register_email);
	mRIVRegisterImg.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		startActivityForResult(intent, 1);
	    }
	});
	new AlertDialog.Builder(this).setTitle("用户注册").setView(layoutRegister).setPositiveButton("确定", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		User user = new User();
		String name = mETRegisterName.getText().toString();
		String password = mETRegisterPassword.getText().toString();
		String email = mETRegisterEmail.getText().toString();
		
		BmobFile img = new BmobFile(new File(UriToString(uri)));
		Log.i("zxc",UriToString(uri));///storage/emulated/0/ShareSDK/cn.sharesdk.socialization.sample/cache/pic_glance_back.jpg
		if (name.length() >= 2) {
		    if (password.length() >= 6) {
			user.setImg(img);
			user.setUsername(name);
			user.setPassword(password);
			user.setEmail(email);
			user.signUp(Main.this, new SaveListener() {
			    @Override
			    public void onSuccess() {
				// TODO Auto-generated method stub
				toast("注册成功:");
			    }

			    @Override
			    public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
				toast("注册失败:" + msg);
			    }
			});
		    } else {
			toast("密码长度必须大于或等于6");
		    }
		} else {
		    toast("账号长度必须大于或等于2");
		}
	    }
	}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	    }

	}).show();
    }

    private void personSetting() {
	User person = User.getCurrentUser(this, User.class);
	LayoutInflater inflaterRegister = getLayoutInflater();
	View layoutRegister = inflaterRegister.inflate(R.layout.dialog_register, null);
	mRIVSettingImg = (RoundedImageView) layoutRegister.findViewById(R.id.riv_dialog_register_img);
	// person.getImg().loadImage(this, mRIVSettingImg);
	mETSettingName = (EditText) layoutRegister.findViewById(R.id.et_dialog_login_register_name);
	mETSettingName.setText(person.getUsername());
	mETSettingName.setEnabled(false);
	mETSettingPassword = (EditText) layoutRegister.findViewById(R.id.et_dialog_login_register_password);
	mETSettingEmail = (EditText) layoutRegister.findViewById(R.id.et_dialog_register_email);
	mETSettingEmail.setText(person.getEmail());
	mETSettingEmail.setEnabled(false);
	mRIVSettingImg.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		/* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
		/* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
		startActivityForResult(intent, 2);
	    }
	});
	new AlertDialog.Builder(this).setTitle("个人设置").setView(layoutRegister).setPositiveButton("确定", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		User user = User.getCurrentUser(Main.this, User.class);
		String password = mETSettingPassword.getText().toString();
		BmobFile img = new BmobFile(new File(UriToString(uri)));

		if (password.length() >= 6) {
		    user.setImg(img);
		    user.setPassword(password);
		    user.update(Main.this, new UpdateListener() {
			@Override
			public void onSuccess() {
			    // TODO Auto-generated method stub
			    toast("更新用户信息成功:");
			}

			@Override
			public void onFailure(int code, String msg) {
			    // TODO Auto-generated method stub
			    toast("更新用户信息失败:" + msg);
			}
		    });

		} else {
		    toast("密码长度必须大于或等于6");
		}
	    }
	}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub

	    }

	}).show();
    }

    /**
     * Uri转为File
     * 
     * @param uri
     * @return
     */
    private String UriToString(Uri uri) {
	if (uri != null) {
	    String[] proj = { MediaStore.Images.Media.DATA };
	    Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
	    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    actualimagecursor.moveToFirst();
	    String img_path = actualimagecursor.getString(actual_image_column_index);
	    return img_path;
	}
	return null;
    }

    /**
     * 统一的Toast
     * 
     * @param string
     */
    private void toast(String string) {
	Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
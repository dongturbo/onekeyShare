/**
 * 自定义View，显示圆形图片
 */
package com.xcz1899.onekey.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundedImageView extends ImageView {

	public RoundedImageView(Context context) {
		super(context);
	}

	public RoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		Drawable drawable = getDrawable();// 返回view的Drawable

		if (drawable == null) {
			return;
		}
		if (getWidth() == 0 || getHeight() == 0) {// view的长宽
			return;
		}

		Bitmap b = ((BitmapDrawable) drawable).getBitmap();// 从drawable中获取bitmap

		Bitmap roundBitmap = getCroppedBitmap(b, getWidth());// 得到圆形的bitmap对象
		canvas.drawBitmap(roundBitmap, 0, 0, null);//在当前控件上绘制出圆形控件

	}

	/**
	 * 将bmp转化为圆，半径为radius
	 * 
	 * @param bmp
	 * @param radius
	 * @return
	 */
	public Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
		Bitmap sbmp;// 最后得到的是一个长宽相同的Bitmap
		if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
			sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		} else {
			sbmp = bmp;
		}

		Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
				Config.ARGB_8888);// 该函数创建一个带有特定宽度、高度和颜色格式的位图(正方形)
		Canvas canvas = new Canvas(output);// 以output对象创建一个画布
		canvas.drawARGB(0, 0, 0, 0);// 透明色 设置画布的颜色
		
		final Paint paint = new Paint();// 定义画笔
		paint.setAntiAlias(true);// 设置画笔为无锯齿
		paint.setFilterBitmap(true);// 用来对位图进行滤波处理
		paint.setDither(true);// 设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰

		
		canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2,
				sbmp.getWidth() / 2, paint);// 画圆,此时canvas上面有一个圆

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 取两层绘制交集。显示前景色。
		final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());// 正方形
		canvas.drawBitmap(sbmp, rect, rect, paint);// 这里正方形和圆形画在一起，取交集，即现在画布上就是一个圆形的sbmp图像

		return output;
	}

}

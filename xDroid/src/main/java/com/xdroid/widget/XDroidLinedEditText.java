/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xdroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

//TODO: Auto-generated Javadoc

/**
 * 描述：带下横线的EditText
 * @version v1.0
 */
public  class XDroidLinedEditText extends EditText {
  
  /** The m rect. */
  private Rect mRect;
  
  /** The m paint. */
  private Paint mPaint;

  /**
   * Instantiates a new ab lined edit text.
   *
   * @param context the context
   * @param attrs the attrs
   */
  public XDroidLinedEditText(Context context, AttributeSet attrs) {
      super(context, attrs);
      mRect = new Rect();
      mPaint = new Paint();
      mPaint.setStyle(Paint.Style.STROKE);
      mPaint.setColor(0x800000FF);
  }
  
  /* (non-Javadoc)
   * @see android.widget.TextView#onDraw(android.graphics.Canvas)
   */
  @Override
  protected void onDraw(Canvas canvas) {
      int count = getLineCount();
      Rect r = mRect;
      Paint paint = mPaint;

      for (int i = 0; i < count; i++) {
          int baseline = getLineBounds(i, r);
          canvas.drawLine(r.left, baseline+10, r.right, baseline + 10, paint);
      }

      super.onDraw(canvas);
  }
}
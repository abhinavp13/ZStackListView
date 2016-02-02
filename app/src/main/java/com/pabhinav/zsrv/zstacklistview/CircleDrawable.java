/*
 * Copyright (C) 2016 Abhinav Puri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pabhinav.zsrv.zstacklistview;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Custom {@link Drawable} object which draws oval/circle shape.
 * <p>
 *     The main purpose of using this drawable is to set its
 *     background color dynamically during runtime.
 * </p>
 *
 * @author pabhinav (pabhinav@iitrpr.ac.in)
 */
public class CircleDrawable extends Drawable {

    /**
     * {@link Paint} object used to draw on {@link Canvas}.
     */
    Paint mPaint;

    /**
     * {@link RectF} holds four float coordinates for a rectangle. The rectangle is
     * represented by the coordinates of its 4 edges (left, top, right bottom).
     */
    RectF mRectF;

    /**
     * Constructor for this class.
     *
     * <p>
     *     Used to initialize {@link Paint} object and {@link RectF} object.
     * </p>
     * @param backgroundColor is the color resource used to render background
     *                        of this circle/oval drawable object.
     */
    public CircleDrawable(int backgroundColor) {

        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(backgroundColor);

        this.mRectF = new RectF();
    }

    /**
     * Draws on {@link Canvas} object.
     * <p>
     *     Identifies the {@link Canvas} bounds, and detect whether width and
     *     height is identical or not. If width is not equal to height, this
     *     means one need to draw oval, else circle. It uses drawOval and
     *     drawCircle methods of {@link Canvas} object.
     * </p>
     *
     * @param canvas The canvas to draw into
     */
    @Override
    public void draw(Canvas canvas) {

        /** Fetch the width and height of bounds of canvas **/
        int height = getBounds().height();
        int width = getBounds().width();

        /** Draw Oval if height and width are not equal **/
        if (width != height) {
            mRectF.left = 0.0f;
            mRectF.top = 0.0f;
            mRectF.right = width;
            mRectF.bottom = height;
            canvas.drawOval(mRectF, mPaint);
        }
        /** Else draw circle **/
        else {
            canvas.drawCircle(height / 2, width / 2, width / 2, mPaint);
        }
    }

    /**
     * Specify an alpha value for the drawable.
     * This alpha is used to set alpha value for {@link Paint} object.
     */
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    /**
     * Specify an optional color filter for the drawable.
     * The color filter is set for {@link Paint} object.
     *
     * @param colorFilter The color filter to apply, or {@code null} to remove the
     *            existing color filter
     */
    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    /**
     * Return the opacity/transparency of this Drawable.
     * Opacity is set to {@link android.graphics.PixelFormat#OPAQUE}.
     */
    @Override
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}

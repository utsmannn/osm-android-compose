package com.utsman.osmandcompose.extendedosm;


import android.graphics.Canvas;
import android.graphics.Point;


import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import android.graphics.Paint;

import com.utsman.osmandcompose.model.LabelProperties;

public class MarkerWithLabel extends Marker {
    Paint textPaint = null;
    String mLabel = null;

    float mTextOffsetY;
    public MarkerWithLabel(MapView mapView, String label, LabelProperties labelProperties) {
        super( mapView);
        mLabel = label;
        textPaint = new Paint();
        textPaint.setColor(labelProperties.getLabelColor());
        textPaint.setTextSize(labelProperties.getLabelTextSize());
        textPaint.setAntiAlias(labelProperties.getLabelAntiAlias());
        textPaint.setTextAlign(labelProperties.getLabelAlign());
        mTextOffsetY = labelProperties.getLabelTextOffset();
    }
    public void draw(final Canvas c, final MapView osmv, boolean shadow) {
        draw( c, osmv);
    }
    public void draw( final Canvas c, final MapView osmv) {
        super.draw( c, osmv, false);
        Point p = this.mPositionPixels;  // already provisioned by Marker
        c.drawText( mLabel, p.x, p.y+mTextOffsetY, textPaint);
    }
}
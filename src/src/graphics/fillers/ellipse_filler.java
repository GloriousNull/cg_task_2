package src.graphics.fillers;

import src.graphics.drawers.*;
import src.utils.coordinate_2d;

import java.awt.*;

public interface ellipse_filler
{
    default void fill_ellipse(Graphics2D graphics, coordinate_2d center, int width, int height, Color color, int zoom_multiplayer)
    {
        ellipse_graphics eg = new ellipse_graphics(false);

        int x = 0, y = 0;

        int error_0 = width-height;

        while (x != width || y != height)
        {
            eg.draw_ellipse_WUlike(graphics, center, Math.round(x), Math.round(y), color, zoom_multiplayer);

            final int error_1 = error_0 * 2;

            if (error_1 >= -height)
            {
                error_0 -= height;
                ++x;
            }

            if (error_1 <= width)
            {
                error_0 += width;
                ++y;
            }
        }

        eg = new ellipse_graphics(true);
        eg.draw_ellipse_WUlike(graphics, center, Math.round(x), Math.round(y), color, zoom_multiplayer);
    }

    default void fill_circle(Graphics2D graphics, coordinate_2d center, int radius, Color color, int zoom_multiplayer)
    {
        ellipse_graphics eg = new ellipse_graphics(false);

        for (int it = 0; it < radius; ++it)
            eg.draw_circle_WUlike(graphics, center, it, color, zoom_multiplayer);

        eg = new ellipse_graphics(true);
        eg.draw_circle_WUlike(graphics, center, radius, color, zoom_multiplayer);
    }
}

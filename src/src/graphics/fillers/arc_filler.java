package src.graphics.fillers;

import src.graphics.drawers.arc_graphics;
import src.graphics.drawers.line_drawer;
import src.utils.coordinate_2d;

import java.awt.*;

public interface arc_filler extends line_drawer
{
    default void fill_arc(Graphics2D graphics, coordinate_2d center, int width, int height, int begin_angle, int end_angle, Color color, int zoom_multiplayer)
    {
        arc_graphics ag = new arc_graphics(false);

        int x = 0, y = 0;

        int error_0 = width-height;

        while (x != width || y != height)
        {
            ag.draw_arc(graphics, center, Math.round(x), Math.round(y), begin_angle, end_angle, color, zoom_multiplayer);

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

        ag = new arc_graphics(true);
        ag.draw_arc(graphics, center, Math.round(x), Math.round(y), begin_angle, end_angle, color, zoom_multiplayer);
    }
}
